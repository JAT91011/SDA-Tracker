package controladores;

import modelos.GestorTrackers;

public class ControladorConfiguracion {

	public ControladorConfiguracion() {

	}

	public void conectar(final String ip, final int port) {
		boolean connected = GestorTrackers.getInstance().connect(ip, port);
		GestorTrackers.getInstance().start();
	}

	public void desconectar() {
		GestorTrackers.getInstance().disconnect();
	}

	public boolean estaConectado() {
		return GestorTrackers.getInstance().isEnable();
	}

	public int numeroTrackers() {
		return GestorTrackers.getInstance().getTotalTrackers();
	}
}