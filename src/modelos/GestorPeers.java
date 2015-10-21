package modelos;

import java.util.Observable;
import java.util.Vector;

import entidades.Peer;

public class GestorPeers extends Observable implements Runnable {

	private static GestorPeers	instance;

	private boolean				enable;
	private Vector<Peer>		peers;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
