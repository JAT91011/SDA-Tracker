package interfaz;

import javax.swing.JTabbedPane;

public class PanelPestanas extends JTabbedPane {

	private static final long	serialVersionUID	= 8155818731609154350L;
	private PanelConfiguracion	panelConfiguracion;
	private PanelTrackers		panelTrackers;
	private PanelPeers		panelPeers;

	public PanelPestanas() {
		panelConfiguracion = new PanelConfiguracion();
		addTab("Configuraci\u00F3n", null, panelConfiguracion, null);

		panelTrackers = new PanelTrackers();
		addTab("Trackers", null, panelTrackers, null);

		panelPeers = new PanelPeers();
		addTab("Peers", null, panelPeers, null);
	}
}