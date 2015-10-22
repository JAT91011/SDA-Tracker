package modelos;

/**
 * Encapsula la funcionalidad relacionada con el almacenamiento de información
 * persistente.
 */

public class GestorDatos {

	private static GestorDatos instance;

	private GestorDatos() {

	}

	public static GestorDatos ObtenerInstancia() {
		if (instance == null) {
			instance = new GestorDatos();
		}
		return instance;
	}
}