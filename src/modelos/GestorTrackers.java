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
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Timer;

import entidades.Tracker;
import utilidades.LogErrores;
import vistas.Ventana;

/**
 * Implementa la funcionalidad especifica del protocolo UDP de un tracker
 * bitTorrent.
 */

public class GestorTrackers extends Observable implements Runnable {

	private String								ip;
	private int									port;
	private static GestorTrackers				instance;

	private boolean								enable;
	private Tracker								currentTracker;
	private ConcurrentHashMap<Integer, Tracker>	trackers;

	private Thread								readingThread;
	private Timer								timerSendKeepAlive;
	private Timer								timerCheckKeepAlive;

	private MulticastSocket						socket;
	private InetAddress							group;
	private DatagramPacket						messageIn;
	private byte[]								buffer;

	private GestorTrackers() {
		this.enable = false;
		this.trackers = new ConcurrentHashMap<Integer, Tracker>();

		this.timerSendKeepAlive = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sendData(createDatagram(7, ByteBuffer.allocate(4).putInt(currentTracker.getId()).array())[0]);
				} catch (Exception ex) {
					LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
					}.getClass().getEnclosingMethod().getName(), ex.toString());
					ex.printStackTrace();
				}
			}
		});

		this.timerCheckKeepAlive = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					for (Map.Entry<Integer, Tracker> entry : trackers.entrySet()) {
						if (entry.getValue().getDifferenceBetweenKeepAlive() > 2) {
							System.out.println("Has tardado: " + entry.getValue().getId());
							removeTracker(entry.getValue().getId());
							if (entry.getValue().isMaster()) {
								updateMaster();
							}
						}
					}
				} catch (Exception ex) {
					LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
					}.getClass().getEnclosingMethod().getName(), ex.toString());
					ex.printStackTrace();
				}
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

			this.socket = new MulticastSocket(port);
			this.group = InetAddress.getByName(this.ip);
			this.socket.joinGroup(group);
			this.enable = true;
			return true;
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
			this.timerSendKeepAlive.stop();
			this.timerCheckKeepAlive.stop();
			this.socket.leaveGroup(group);
			this.enable = false;
			this.removeTrackers();
			return true;
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
	public synchronized int getAvailableId() {
		try {
			System.out.println("Num trackers: " + trackers.size());
			for (int id = 1; id < Integer.MAX_VALUE; id++) {
				if (!trackers.containsKey(id)) {
					return id;
				}
			}
			return -1;
		} catch (Exception ex) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), ex.toString());
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * Funcion para notificar que se ha insertado un nuevo tracker
	 */
	public synchronized void addTracker(Tracker tracker) {
		try {
			this.trackers.put(tracker.getId(), tracker);
			setChanged();
			notifyObservers();
		} catch (Exception ex) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * Funcion para notificar que se ha desconectado un tracker
	 * 
	 * @param tracker
	 *            Tracker que se ha desconectado
	 */
	public synchronized void removeTracker(int id) {
		try {
			this.trackers.remove(id);
			setChanged();
			notifyObservers();
		} catch (Exception ex) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * Funcion para notificar que se ha desconectado el tracker operativo
	 * tracker
	 * 
	 * @param tracker
	 *            Tracker que se ha desconectado
	 */
	public synchronized void removeTrackers() {
		try {
			this.trackers.clear();
			setChanged();
			notifyObservers();
		} catch (Exception ex) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * Funcion para notificar que se han alterado los datos de un tracker
	 * 
	 * @param tracker
	 */
	public synchronized void setTracker(Tracker tracker) {
		try {
			this.trackers.put(tracker.getId(), tracker);
			setChanged();
			notifyObservers();
		} catch (Exception ex) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * Funcion para obtener los trackers activos
	 * 
	 * @return Vector con los trackers activos
	 */
	public synchronized ConcurrentHashMap<Integer, Tracker> getTrackers() {
		return instance.trackers;
	}

	/**
	 * Se procesan los datos recibidos
	 * 
	 * @param datos
	 *            Datos recibidos
	 */
	public void processData(final byte[] data) {
		try {
			int code = ByteBuffer.wrap(Arrays.copyOfRange(data, 0, 4)).getInt();
			// System.out.println("Codigo recibido: " + code);
			switch (code) {
				case 0: // OK

					break;

				case 1: // GET_DATA

					break;

				case 2: // DB REPLICATION

					break;

				case 3: // Trackers IDs

					break;

				case 4: // READY_TO_SAVE

					break;

				case 5: // SAVE_DATA

					break;

				case 6: // DO_NOT_SAVE_DATA

					break;

				case 7: // KEEP_ALIVE
					updateTrackerKeepAlive(ByteBuffer.wrap(Arrays.copyOfRange(data, 16, 20)).getInt());
					break;

				case 99: // ERR

					break;
			}
		} catch (Exception ex) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), ex.toString());
			ex.printStackTrace();
		}
		// System.out.println("Datos recibidos: " + Arrays.toString(data));
	}

	public synchronized void updateTrackerKeepAlive(final int id) {
		try {
			System.out.println("ID Recibida: " + id);
			if (trackers.get(id) == null) {
				System.out.println("Nuevo tracker encontrado");
				Tracker t = new Tracker(id, false);
				t.setLastKeepAlive(new Date());
				addTracker(t);
			} else {
				trackers.get(id).setLastKeepAlive(new Date());
				setTracker(trackers.get(id));
			}
		} catch (Exception ex) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), ex.toString());
			ex.printStackTrace();
		}
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
		byte[][] datagrams = null;
		try {
			int length = data.length;
			int partitions = 0;
			if (length < 48) {
				partitions = 1;
			} else {
				partitions = length / 48;
			}

			// System.out.println("Data: " + Arrays.toString(data));

			datagrams = new byte[partitions][64];
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
		} catch (Exception e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
		}

		// System.out.println("Datagrama creado");
		// for (int i = 0; i < partitions; i++) {
		// System.out.println("Datagrama " + (i + 1) + ": " +
		// Arrays.toString(datagrams[i]));
		// }

		return datagrams;
	}

	/**
	 * Metodo para enviar un datagrama
	 * 
	 * @param data
	 *            Datagrama a enviar
	 */
	public synchronized void sendData(byte[] data) {
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
			this.timerCheckKeepAlive.start();
			this.readingThread = new Thread(this);
			this.readingThread.start();

			int wait = 1;
			while (wait <= 3) {
				System.out.println("Esperando " + wait);
				Thread.sleep(1000);
				wait++;
			}

			int min = getLowerId();
			if (min != 0) {
				trackers.get(min).setMaster(true);
			}
			this.currentTracker = new Tracker(getAvailableId(), this.trackers.size() == 0);
			if (this.currentTracker.isMaster()) {
				Ventana.getInstance().setTitle("Tracker [ID: " + this.currentTracker.getId() + "] [Mode: MASTER]");
			} else {
				Ventana.getInstance().setTitle("Tracker [ID: " + this.currentTracker.getId() + "] [Mode: SLAVE]");
			}
			addTracker(currentTracker);
			this.timerSendKeepAlive.start();

		} catch (Exception e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
		}
	}

	public synchronized int getLowerId() {
		try {
			int id = 0;
			if (!trackers.isEmpty()) {
				id = Integer.MAX_VALUE;
			}
			for (Map.Entry<Integer, Tracker> entry : trackers.entrySet()) {
				if (entry.getKey() < id) {
					id = entry.getKey();
				}
			}
			return id;
		} catch (Exception e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Funcion para actualizar y nombrar el nuevo Master
	 * 
	 */
	public synchronized void updateMaster() {
		try {
			int id = getLowerId();
			if (id == currentTracker.getId()) {
				currentTracker.setMaster(true);
				Ventana.getInstance().setTitle("Tracker [ID: " + this.currentTracker.getId() + "] [Mode: MASTER]");
			}
			trackers.get(getLowerId()).setMaster(true);
			setTracker(trackers.get(getLowerId()));
		} catch (Exception ex) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), ex.toString());
			ex.printStackTrace();
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
		GestorTrackers.getInstance().connect("228.5.6.7", 5000);
		GestorTrackers.getInstance().start();
	}
}