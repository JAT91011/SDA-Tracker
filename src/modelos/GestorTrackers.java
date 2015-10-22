package modelos;

import java.util.Observable;
import java.util.Vector;

import entidades.Tracker;

/**
 * Implementa la funcionalidad específica del protocolo UDP de un tracker
 * bitTorrent.
 */

public class GestorTrackers extends Observable implements Runnable {

	private static GestorTrackers	instance;

	private boolean					enable;
	private Vector<Tracker>			trackers;

	private GestorTrackers() {
		this.enable = false;
		this.trackers = new Vector<Tracker>();
	}

	public boolean Conectar(final String ip, final int puerto) {
		return false;
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
	public void ProcesarDatos(String datos) {

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
	 * Hilo principal para procesar y enviar informacion a los trackers
	 */
	@Override
	public void run() {
		while (true) {
			while (this.enable) {

			}
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

	public static GestorTrackers ObtenerInstancia() {
		if (instance == null) {
			instance = new GestorTrackers();
		}
		return instance;
	}
}