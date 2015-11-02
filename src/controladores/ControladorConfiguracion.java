package controladores;

import modelos.GestorTrackers;

public class ControladorConfiguracion {

	public ControladorConfiguracion() {

	}

	public void conectar(final String ip, final int port) {
		boolean connected = GestorTrackers.getInstance().connect(ip, port);
		if (connected) {
			GestorTrackers.getInstance().start();
		} else {
			// TODO Mostrar aviso
		}
	}

	public void desconectar() {
		GestorTrackers.getInstance().removeTracker(GestorTrackers.getInstance().getTrackers().get(0));
		GestorTrackers.getInstance().setEnable(false);
	}

	public boolean estaConectado() {
		return GestorTrackers.getInstance().isEnable();
	}

	public int numeroTrackers() {
		return GestorTrackers.getInstance().getTotalTrackers();
	}
}