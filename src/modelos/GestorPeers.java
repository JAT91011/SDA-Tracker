package modelos;

import java.util.Observable;

public class GestorPeers extends Observable implements Runnable {

	private static GestorPeers instance;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public static GestorPeers ObtenerInstancia() {
		if (instance == null) {
			instance = new GestorPeers();
		}
		return instance;
	}

}
