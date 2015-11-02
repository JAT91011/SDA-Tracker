package modelos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

import javax.swing.Timer;

import entidades.Tracker;
import utilidades.LogErrores;

/**
 * Implementa la funcionalidad específica del protocolo UDP de un tracker
 * bitTorrent.
 */

public class GestorTrackers extends Observable implements Runnable {

	private String					ip;
	private int						port;
	private static GestorTrackers	instance;

	private boolean					enable;
	private boolean					isWriting;
	private Tracker					currentTracker;
	private Vector<Tracker>			trackers;

	private Thread					readingThread;
	private Timer					timerKeepAlive;

	private MulticastSocket			socket;
	private InetAddress				group;
	private DatagramPacket			messageIn;
	private byte[]					buffer;

	private GestorTrackers() {
		this.enable = false;
		this.trackers = new Vector<Tracker>();

		this.timerKeepAlive = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (isWriting)
					;
				isWriting = true;
				sendData(createDatagram((byte) 0x06, ByteBuffer.allocate(4).putInt(currentTracker.getId()).array())[0]);
				isWriting = false;
			}
		});
	}

	/**
	 * Metodo para conectarse con los demas trackers
	 * 
	 * @param ip
	 *            IP Multicast
	 * @param port
	 *            Puerto de conexion
	 * @return Si se ha conectado o no
	 */
	public boolean connect(final String ip, final int port) {
		try {
			this.ip = ip;
			this.port = port;

			this.currentTracker = new Tracker(getAvailableId(), this.trackers.size() == 0);
			addTracker(currentTracker);

			this.socket = new MulticastSocket(port);
			this.group = InetAddress.getByName(this.ip);
			this.socket.joinGroup(group);
			this.enable = true;
			return this.socket.isConnected();
		} catch (IOException e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Metodo para cerrar la conexion
	 * 
	 * @return Si se ha desconectado o no
	 */
	public boolean disconnect() {
		try {
			this.timerKeepAlive.stop();
			this.socket.leaveGroup(group);
			this.enable = false;
			this.removeTrackers();
			return this.socket.isClosed();
		} catch (IOException e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Funcion para obtener la primera ID disponible
	 * 
	 * @return La primera ID disponible
	 */
	public int getAvailableId() {
		int id = 1;
		boolean enc = false;
		for (id = 1; id < Integer.MAX_VALUE; id++) {
			for (Tracker tracker : trackers) {
				if (tracker.getId() == id) {
					enc = true;
					break;
				}
			}
			if (!enc && (currentTracker == null || currentTracker.getId() != id)) {
				// if (currentTracker == null) {
				// return id;
				// } else if (currentTracker.getId() != id) {
				// return id;
				// }
				return id;
			}
		}
		return -1;
	}

	/**
	 * Funcion para notificar que se ha insertado un nuevo tracker
	 */
	public void addTracker(Tracker tracker) {
		this.trackers.addElement(tracker);
		setChanged();
		notifyObservers();
	}

	/**
	 * Funcion para notificar que se ha desconectado un tracker
	 * 
	 * @param tracker
	 *            Tracker que se ha desconectado
	 */
	public void removeTracker(Tracker tracker) {
		this.trackers.remove(tracker);
		setChanged();
		notifyObservers();
	}

	/**
	 * Funcion para notificar que se ha desconectado el tracker operativo
	 * tracker
	 * 
	 * @param tracker
	 *            Tracker que se ha desconectado
	 */
	public void removeTrackers() {
		this.trackers.removeAllElements();
		setChanged();
		notifyObservers();
	}

	/**
	 * Función para notificar que se han alterado los datos de un tracker
	 * 
	 * @param tracker
	 */
	public void setTracker(Tracker tracker) {
		this.trackers.set(this.trackers.indexOf(tracker), tracker);
		setChanged();
		notifyObservers();
	}

	/**
	 * Funcion para obtener los trackers activos
	 * 
	 * @return Vector con los trackers activos
	 */
	public Vector<Tracker> getTrackers() {
		return instance.trackers;
	}

	/**
	 * Se procesan los datos recibidos
	 * 
	 * @param datos
	 *            Datos recibidos
	 */
	public void processData(final byte[] data) {
		int code = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, 4)).getInt();
		System.out.println("Codigo recibido: " + code);
		switch (code) {
			case 0: // OK

				break;

			case 1: // GET_DATA

				break;

			case 2: // REPLICATION

				break;

			case 3: // READY_TO_SAVE

				break;

			case 4: // SAVE_DATA

				break;

			case 5: // DO_NOT_SAVE_DATA

				break;

			case 6: // KEEP_ALIVE
				int id = ByteBuffer.wrap(Arrays.copyOfRange(data, 16, 20)).getInt();
				trackers.firstElement().setLastKeepAlive(new Date());
				setTracker(trackers.firstElement());
				System.out.println("ID Recibida: " + id);
				break;

			case 99: // ERR

				break;
		}

		System.out.println("Datos recibidos: " + Arrays.toString(data));
	}

	/**
	 * Se crea la trama de datos para enviar con el formato correcto
	 * 
	 * @param codigo
	 *            Codigo de la trama
	 * @param datos
	 *            Datos que se van a enviar
	 * @return Array con la trama formateada se utiliza un array bidimensional
	 *         dado que la trama puede estar particionada
	 */
	public byte[][] createDatagram(int code, byte[] data) {
		int length = data.length;
		int partitions = 0;
		if (length < 48) {
			partitions = 1;
		} else {
			partitions = length / 48;
		}

		System.out.println("Data: " + Arrays.toString(data));

		byte[][] datagrams = new byte[partitions][64];
		for (int i = 0; i < partitions; i++) {

			// CODE
			byte[] codeArray = ByteBuffer.allocate(4).putInt(code).array();
			datagrams[i][0] = codeArray[0];
			datagrams[i][1] = codeArray[1];
			datagrams[i][2] = codeArray[2];
			datagrams[i][3] = codeArray[3];

			// PARTITIONS
			byte[] codePartitions = ByteBuffer.allocate(4).putInt(partitions).array();
			datagrams[i][4] = codePartitions[0];
			datagrams[i][5] = codePartitions[1];
			datagrams[i][6] = codePartitions[2];
			datagrams[i][7] = codePartitions[3];

			// CURRENT PARTITION
			byte[] codeCurrentPartition = ByteBuffer.allocate(4).putInt(i + 1).array();
			datagrams[i][8] = codeCurrentPartition[0];
			datagrams[i][9] = codeCurrentPartition[1];
			datagrams[i][10] = codeCurrentPartition[2];
			datagrams[i][11] = codeCurrentPartition[3];

			// LENGTH
			if (i + 1 == partitions) {
				byte[] lengthArray = ByteBuffer.allocate(4).putInt(length).array();
				datagrams[i][12] = lengthArray[0];
				datagrams[i][13] = lengthArray[1];
				datagrams[i][14] = lengthArray[2];
				datagrams[i][15] = lengthArray[3];
				for (int j = 0; j < length; j++) {
					datagrams[i][16 + j] = data[j];
				}
			} else {
				byte[] lengthArray = ByteBuffer.allocate(4).putInt(48).array();
				datagrams[i][12] = lengthArray[0];
				datagrams[i][13] = lengthArray[1];
				datagrams[i][14] = lengthArray[2];
				datagrams[i][15] = lengthArray[3];
				for (int j = 0; j < 48; j++) {
					datagrams[i][16 + j] = data[j];
				}
			}

		}

		System.out.println("Datagrama creado");
		for (int i = 0; i < partitions; i++) {
			System.out.println("Datagrama " + (i + 1) + ": " + Arrays.toString(datagrams[i]));
		}

		return datagrams;
	}

	/**
	 * Metodo para enviar un datagrama
	 * 
	 * @param data
	 *            Datagrama a enviar
	 */
	public void sendData(byte[] data) {
		try {
			DatagramPacket message = new DatagramPacket(data, data.length, group, this.port);
			socket.send(message);
		} catch (IOException e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			this.timerKeepAlive.start();
			this.readingThread = new Thread(this);
			this.readingThread.start();
		} catch (Exception e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Hilo principal para procesar y enviar informacion a los trackers
	 */
	@Override
	public void run() {
		try {
			while (this.enable) {
				this.buffer = new byte[64];
				this.messageIn = new DatagramPacket(buffer, buffer.length);
				this.socket.receive(messageIn);
				processData(this.buffer);
			}
		} catch (Exception e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
		}
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getTotalTrackers() {
		return trackers.size();
	}

	public static GestorTrackers getInstance() {
		if (instance == null) {
			instance = new GestorTrackers();
		}
		return instance;
	}

	public static void main(String[] strings) {
		boolean connect = GestorTrackers.getInstance().connect("228.5.6.7", 5000);
		GestorTrackers.getInstance().start();
		// if (!connect) {
		// System.out.println("No conectado");
		// } else {
		// GestorTrackers.getInstance().start();
		// GestorTrackers.getInstance().sendData(new String("hola").getBytes());
		// }
		// GestorTrackers.getInstance().disconnect();
	}
}