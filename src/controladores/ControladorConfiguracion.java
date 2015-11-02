package controladores;

import entidades.Tracker;
import modelos.GestorTrackers;

public class ControladorConfiguracion {

	public ControladorConfiguracion() {

	}

	public void conectar(Tracker t) {
		GestorTrackers.getInstance().NuevoTracker(t);
		GestorTrackers.getInstance().setEnable(true);
	}

	public void desconectar() {
		GestorTrackers.getInstance().BorrarTracker(
				GestorTrackers.getInstance().ObtenerTrackers().get(0));
		GestorTrackers.getInstance().setEnable(false);
	}

	public boolean estaConectado() {
		return GestorTrackers.getInstance().isEnable();
	}

	public int numeroTrackers() {
		return GestorTrackers.getInstance().getTotalTrackers();
	}
}