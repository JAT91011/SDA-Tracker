package modelos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.Observable;
import java.util.Vector;

import entidades.Tracker;
import utilidades.LogErrores;

/**
 * Implementa la funcionalidad específica del protocolo UDP de un tracker
 * bitTorrent.
 */

public class GestorTrackers extends Observable implements Runnable {

	private static GestorTrackers	instance;

	private boolean					enable;
	private boolean					isWriting;
	private Tracker					currentTracker;
	private Vector<Tracker>			trackers;

	private Thread					readingThread;

	private MulticastSocket			socket;
	private InetAddress				group;
	private DatagramPacket			messageIn;
	private byte[]					buffer;

	private GestorTrackers() {
		this.enable = false;
		this.trackers = new Vector<Tracker>();
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
			this.socket = new MulticastSocket(port);
			this.group = InetAddress.getByName(ip);
			this.socket.joinGroup(group);
			this.enable = true;
			return this.socket.isConnected();
		} catch (IOException e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(),
					new Object() {
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
			this.socket.leaveGroup(group);
			this.enable = false;
			return this.socket.isClosed();
		} catch (IOException e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(),
					new Object() {
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
		int id = 0;
		boolean enc = false;
		for (id = 0; id < Integer.MAX_VALUE; id++) {
			for (Tracker tracker : trackers) {
				if (tracker.getId() == id) {
					enc = true;
					break;
				}
			}
			if (!enc && (currentTracker == null
					|| currentTracker.getId() != id)) {
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
	public void NuevoTracker(Tracker tracker) {
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
	public void BorrarTracker(Tracker tracker) {
		this.trackers.remove(tracker);
		setChanged();
		notifyObservers();
	}

	/**
	 * Función para notificar que se han alterado los datos de un tracker
	 * 
	 * @param tracker
	 */
	public void ModificarTracker(Tracker tracker) {
		this.trackers.set(this.trackers.indexOf(tracker), tracker);
		setChanged();
		notifyObservers();
	}

	/**
	 * Funcion para obtener los trackers activos
	 * 
	 * @return Vector con los trackers activos
	 */
	public Vector<Tracker> ObtenerTrackers() {
		return instance.trackers;
	}

	/**
	 * Se procesan los datos recibidos
	 * 
	 * @param datos
	 *            Datos recibidos
	 */
	public void processData(final byte[] data) {
		System.out.println("Datos recividos: " + Arrays.toString(data));
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
	public byte[][] CrearTrama(int codigo, String datos) {
		return new byte[0][0];
	}

	/**
	 * Metodo para enviar un datagrama
	 * 
	 * @param data
	 *            Datagrama a enviar
	 */
	public void sendData(byte[] data) {
		try {
			DatagramPacket message = new DatagramPacket(data, data.length,
					group, this.socket.getPort());
			socket.send(message);
		} catch (IOException e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(),
					new Object() {
					}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			readingThread = new Thread(this);
			readingThread.start();
		} catch (Exception e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(),
					new Object() {
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
				this.buffer = new byte[32];
				this.messageIn = new DatagramPacket(buffer, buffer.length);
				this.socket.receive(messageIn);
				processData(this.buffer);
			}
		} catch (Exception e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(),
					new Object() {
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
		boolean connect = GestorTrackers.getInstance().connect("228.5.6.7",
				9000);
		// if (!connect) {
		// System.out.println("No conectado");
		// } else {
		// GestorTrackers.getInstance()
		// .sendData(new String("hola").getBytes());
		// }

		GestorTrackers.getInstance().start();
		GestorTrackers.getInstance().sendData(new String("hola").getBytes());
		GestorTrackers.getInstance().disconnect();
	}
}