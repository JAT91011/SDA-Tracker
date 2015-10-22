package controladores;

import entidades.Tracker;
import modelos.GestorTrackers;

public class ControladorConfiguracion {

	private GestorTrackers gt = GestorTrackers.ObtenerInstancia();

	public ControladorConfiguracion() {

	}

	public void conectarTracker(Tracker t) {
		gt.NuevoTracker(t);
	}

	public void desconectarTracker(Tracker t) {
		gt.BorrarTracker(t);
	}
}