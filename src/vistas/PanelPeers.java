package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelPeers extends JPanel implements Observer {

	private static final long		serialVersionUID	= 4986034677227823532L;
	private JTable					tablePeers;
	private final DefaultTableModel	modelTablePeers;
	private JTable					tableContent;
	private DefaultTableModel		modelTableContent;

	public PanelPeers() {

		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(5);
		splitPane.setResizeWeight(.7d);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPanePeers = new JScrollPane();
		scrollPanePeers.setSize(new Dimension(0, 300));
		scrollPanePeers.setPreferredSize(new Dimension(4, 300));
		splitPane.setLeftComponent(scrollPanePeers);

		final String[] headerPeers = { "ID", "IP", "Puerto" };

		modelTablePeers = new DefaultTableModel();
		modelTablePeers.setDataVector(new String[1][headerPeers.length],
				headerPeers);
		tablePeers = new JTable(modelTablePeers);
		tablePeers.getTableHeader().setReorderingAllowed(false);
		tablePeers.setShowVerticalLines(true);
		tablePeers.setShowHorizontalLines(true);
		tablePeers.setDragEnabled(false);
		tablePeers.setSelectionForeground(Color.WHITE);
		tablePeers.setSelectionBackground(Color.BLUE);
		tablePeers.setForeground(Color.BLACK);
		tablePeers.setBackground(Color.WHITE);
		tablePeers.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		tablePeers.setRowHeight(30);

		tablePeers.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 15));

		scrollPanePeers.setViewportView(tablePeers);

		JScrollPane scrollPaneContent = new JScrollPane();
		scrollPaneContent.setSize(new Dimension(0, 100));
		scrollPaneContent.setPreferredSize(new Dimension(4, 100));
		splitPane.setRightComponent(scrollPaneContent);

		final String[] headerContent = { "ID", "INFO_HASH", "Estado",
				"Completado" };

		modelTableContent = new DefaultTableModel();
		modelTableContent.setDataVector(new String[1][headerContent.length],
				headerContent);
		tableContent = new JTable(modelTableContent);
		tableContent.getTableHeader().setReorderingAllowed(false);
		tableContent.setShowVerticalLines(true);
		tableContent.setShowHorizontalLines(true);
		tableContent.setDragEnabled(false);
		tableContent.setSelectionForeground(Color.WHITE);
		tableContent.setSelectionBackground(Color.BLUE);
		tableContent.setForeground(Color.BLACK);
		tableContent.setBackground(Color.WHITE);
		tableContent.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
		tableContent.setRowHeight(30);
		tableContent.getTableHeader()
				.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneContent.setViewportView(tableContent);
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}