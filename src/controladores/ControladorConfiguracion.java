package controladores;

import modelos.GestorTrackers;
import utilidades.LogErrores;

public class ControladorConfiguracion {

	public ControladorConfiguracion() {

	}

	public boolean connect(final String ip, final int port) {
		try {
			GestorTrackers.getInstance().connect(ip, port);
			GestorTrackers.getInstance().start();
			return true;
		} catch (Exception e) {
			LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
			}.getClass().getEnclosingMethod().getName(), e.toString());
			e.printStackTrace();
			return false;
		}
	}

	public void disconnect() {
		GestorTrackers.getInstance().disconnect();
	}

	public boolean isConnected() {
		return GestorTrackers.getInstance().isEnable();
	}

	public int numberOfTrackers() {
		return GestorTrackers.getInstance().getTotalTrackers();
	}
}