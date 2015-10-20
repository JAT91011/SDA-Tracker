package modelos;

/**
 * Implementa la funcionalidad específica del protocolo UDP de un tracker
 * bitTorrent.
 */

public class GestorTracker implements Runnable {

	private boolean enable;

	public GestorTracker() {
		this.enable = false;
	}

	public boolean Conectar(final String ip, final int puerto) {
		return false;
	}

	@Override
	public void run() {
		while (true) {
			while (this.enable) {

			}
		}
	}
}