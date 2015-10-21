package vistas;

import java.awt.Color;

import javax.swing.JTabbedPane;

public class PanelPestanas extends JTabbedPane {

	private static final long	serialVersionUID	= 8155818731609154350L;
	private PanelConfiguracion	panelConfiguracion;
	private PanelTrackers		panelTrackers;
	private PanelPeers			panelPeers;

	public PanelPestanas() {
		setOpaque(true);
		setBackground(new Color(112, 128, 144));

		panelConfiguracion = new PanelConfiguracion();
		addTab("Configuraci\u00F3n", null, panelConfiguracion, null);

		panelTrackers = new PanelTrackers();
		addTab("Trackers", null, panelTrackers, null);

		panelPeers = new PanelPeers();
		addTab("Peers", null, panelPeers, null);
	}
}