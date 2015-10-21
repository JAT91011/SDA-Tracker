package modelos;

import java.util.Observable;

/**
 * Implementa la funcionalidad relacionada con la redundancia de las diferentes
 * instancias de los trackers.
 */

public class GestorRedundancia extends Observable {

	private String nombre = "";

	public GestorRedundancia(String nombre) {
		this.nombre = nombre;
	}

	public void setValue(String s) {
		this.nombre = s;
		setChanged();
		notifyObservers();
	}

	public String getValue() {
		return nombre;
	}
}