package vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entidades.Tracker;
import modelos.GestorTrackers;

public class PanelTrackers extends JPanel implements Observer {

	private static final long		serialVersionUID	= 1276595089834953384L;
	private JTable					tablaTrackers;
	private final DefaultTableModel	modelTable;
	private String[]				header;

	public PanelTrackers() {
		setOpaque(false);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		header = new String[3];
		header[0] = "ID";
		header[1] = "Master";
		header[2] = "Ultimo keep alive";
		final String[][] content = new String[1][header.length];

		modelTable = new DefaultTableModel();
		modelTable.setDataVector(content, header);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(10, 10, 10, 10);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		tablaTrackers = new JTable(modelTable);
		scrollPane.setViewportView(tablaTrackers);

		tablaTrackers.getTableHeader().setReorderingAllowed(false);
		tablaTrackers.setShowVerticalLines(true);
		tablaTrackers.setShowHorizontalLines(true);
		tablaTrackers.setDragEnabled(false);
		tablaTrackers.setSelectionForeground(Color.WHITE);
		tablaTrackers.setSelectionBackground(Color.BLUE);
		tablaTrackers.setForeground(Color.BLACK);
		tablaTrackers.setBackground(Color.WHITE);
		tablaTrackers.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		tablaTrackers.setRowHeight(30);

		tablaTrackers.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 15));

		GestorTrackers.getInstance().addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == GestorTrackers.getInstance()) {
			Vector<Tracker> trackers = GestorTrackers.getInstance().getTrackers();
			String[][] contenido = new String[trackers.size()][header.length];
			for (int i = 0; i < trackers.size(); i++) {
				contenido[i][0] = Integer.toString(trackers.get(i).getId());
				if (trackers.get(i).isMaster()) {
					contenido[i][1] = "Maestro";
				} else {
					contenido[i][1] = "Esclavo";
				}
				contenido[i][2] = Long.toString(trackers.get(i).getDifferenceBetweenKeepAlive()) + " segundos";
			}
			modelTable.setDataVector(contenido, header);
			tablaTrackers.setModel(modelTable);
		}
	}
}