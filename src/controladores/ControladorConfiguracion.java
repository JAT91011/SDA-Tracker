package controladores;

import entidades.Tracker;
import modelos.GestorTrackers;

public class ControladorConfiguracion {

	public ControladorConfiguracion() {

	}

	public void conectar(Tracker t) {
		GestorTrackers.ObtenerInstancia().NuevoTracker(t);
		GestorTrackers.ObtenerInstancia().setEnable(true);
	}

	public void desconectar() {
		GestorTrackers.ObtenerInstancia().BorrarTracker(
				GestorTrackers.ObtenerInstancia().ObtenerTrackers().get(0));
		GestorTrackers.ObtenerInstancia().setEnable(false);
	}

	public boolean estaConectado() {
		return GestorTrackers.ObtenerInstancia().isEnable();
	}

	public int numeroTrackers() {
		return GestorTrackers.ObtenerInstancia().getTotalTrackers();
	}
}