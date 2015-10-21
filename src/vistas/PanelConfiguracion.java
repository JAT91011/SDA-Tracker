package vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import modelos.GestorRedundancia;
import utilidades.Propiedades;

public class PanelConfiguracion extends JPanel implements Observer {

	private static final long		serialVersionUID	= 4959247560481979942L;

	private JTextField				txtIP;
	private JTextField				txtPuertoTrackers;
	private JLabel					lblDatabase;
	private JButton					btnExaminar;
	private JTextField				txtDatabase;
	private JButton					btnGuardar;
	private JLabel					lblApariencia;
	private JComboBox<String>		cboApariencia;

	private HashMap<String, String>	lookNFeelHashMap;
	private String					currentLookAndFeel;

	private GestorRedundancia		gr;

	private static String			IPADDRESS_PATTERN	= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private JFileChooser			fc;
	private JLabel					lblPrueba;
	private JButton					btnCambiarValor;
	private JLabel					lblPuertoPeers;
	private JTextField				txtPuertoPeers;

	public PanelConfiguracion() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblIP = new JLabel("Direcci\u00F3n IP:");
		lblIP.setForeground(Color.BLACK);
		lblIP.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblIP = new GridBagConstraints();
		gbc_lblIP.anchor = GridBagConstraints.WEST;
		gbc_lblIP.insets = new Insets(15, 15, 5, 5);
		gbc_lblIP.gridx = 0;
		gbc_lblIP.gridy = 0;
		add(lblIP, gbc_lblIP);

		txtIP = new JTextField(Propiedades.getIP());
		txtIP.setForeground(Color.BLACK);
		txtIP.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_txtIP = new GridBagConstraints();
		gbc_txtIP.anchor = GridBagConstraints.WEST;
		gbc_txtIP.insets = new Insets(15, 5, 5, 15);
		gbc_txtIP.gridx = 1;
		gbc_txtIP.gridy = 0;
		add(txtIP, gbc_txtIP);
		txtIP.setColumns(20);

		JLabel lblPuertoTrackers = new JLabel("Puerto trackers:");
		lblPuertoTrackers.setForeground(Color.BLACK);
		lblPuertoTrackers.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPuertoTrackers = new GridBagConstraints();
		gbc_lblPuertoTrackers.anchor = GridBagConstraints.WEST;
		gbc_lblPuertoTrackers.insets = new Insets(5, 15, 5, 5);
		gbc_lblPuertoTrackers.gridx = 0;
		gbc_lblPuertoTrackers.gridy = 1;
		add(lblPuertoTrackers, gbc_lblPuertoTrackers);

		txtPuertoTrackers = new JTextField(
				Integer.toString(Propiedades.getPuertoTracker()));
		txtPuertoTrackers.setForeground(Color.BLACK);
		txtPuertoTrackers.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtPuertoTrackers.setColumns(20);
		GridBagConstraints gbc_txtPuertoTrackers = new GridBagConstraints();
		gbc_txtPuertoTrackers.insets = new Insets(5, 5, 5, 15);
		gbc_txtPuertoTrackers.anchor = GridBagConstraints.WEST;
		gbc_txtPuertoTrackers.gridx = 1;
		gbc_txtPuertoTrackers.gridy = 1;
		add(txtPuertoTrackers, gbc_txtPuertoTrackers);

		lblPuertoPeers = new JLabel("Puerto peers:");
		lblPuertoPeers.setForeground(Color.BLACK);
		lblPuertoPeers.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPuertoPeers = new GridBagConstraints();
		gbc_lblPuertoPeers.anchor = GridBagConstraints.WEST;
		gbc_lblPuertoPeers.insets = new Insets(5, 15, 5, 5);
		gbc_lblPuertoPeers.gridx = 0;
		gbc_lblPuertoPeers.gridy = 2;
		add(lblPuertoPeers, gbc_lblPuertoPeers);

		txtPuertoPeers = new JTextField(
				Integer.toString(Propiedades.getPuertoPeer()));
		txtPuertoPeers.setForeground(Color.BLACK);
		txtPuertoPeers.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtPuertoPeers.setColumns(20);
		GridBagConstraints gbc_txtPuertoPeers = new GridBagConstraints();
		gbc_txtPuertoPeers.anchor = GridBagConstraints.WEST;
		gbc_txtPuertoPeers.insets = new Insets(5, 5, 5, 15);
		gbc_txtPuertoPeers.gridx = 1;
		gbc_txtPuertoPeers.gridy = 2;
		add(txtPuertoPeers, gbc_txtPuertoPeers);

		lblDatabase = new JLabel("Base de datos:");
		lblDatabase.setForeground(Color.BLACK);
		lblDatabase.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDatabase = new GridBagConstraints();
		gbc_lblDatabase.anchor = GridBagConstraints.WEST;
		gbc_lblDatabase.insets = new Insets(20, 15, 5, 5);
		gbc_lblDatabase.gridx = 0;
		gbc_lblDatabase.gridy = 3;
		add(lblDatabase, gbc_lblDatabase);

		txtDatabase = new JTextField(Propiedades.getDatabasePath());
		txtDatabase.setForeground(Color.BLACK);
		txtDatabase.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtDatabase.setColumns(20);
		GridBagConstraints gbc_txtDatabase = new GridBagConstraints();
		gbc_txtDatabase.insets = new Insets(20, 5, 5, 5);
		gbc_txtDatabase.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDatabase.gridx = 1;
		gbc_txtDatabase.gridy = 3;
		add(txtDatabase, gbc_txtDatabase);
		txtDatabase.setEnabled(false);

		btnExaminar = new JButton("Examinar");
		btnExaminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelConfiguracion.this.elegirArchivoBD();
			}
		});
		btnExaminar.setForeground(Color.BLACK);
		btnExaminar.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_btnExaminar = new GridBagConstraints();
		gbc_btnExaminar.insets = new Insets(20, 5, 5, 15);
		gbc_btnExaminar.gridx = 2;
		gbc_btnExaminar.gridy = 3;
		add(btnExaminar, gbc_btnExaminar);

		lblApariencia = new JLabel("Apariencia:");
		lblApariencia.setForeground(Color.BLACK);
		lblApariencia.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblApariencia = new GridBagConstraints();
		gbc_lblApariencia.anchor = GridBagConstraints.WEST;
		gbc_lblApariencia.insets = new Insets(5, 15, 5, 5);
		gbc_lblApariencia.gridx = 0;
		gbc_lblApariencia.gridy = 4;
		add(lblApariencia, gbc_lblApariencia);

		cboApariencia = new JComboBox<String>(getAvailableLF());
		cboApariencia.setForeground(Color.BLACK);
		cboApariencia.setFont(new Font("Dialog", Font.PLAIN, 14));
		cboApariencia.setSelectedItem(currentLookAndFeel);
		GridBagConstraints gbc_cboApariencia = new GridBagConstraints();
		gbc_cboApariencia.anchor = GridBagConstraints.WEST;
		gbc_cboApariencia.insets = new Insets(5, 5, 5, 15);
		gbc_cboApariencia.gridx = 1;
		gbc_cboApariencia.gridy = 4;
		add(cboApariencia, gbc_cboApariencia);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelConfiguracion.this.guardar();
			}
		});

		// Inicio prueba Observable/Observer
		gr = new GestorRedundancia("Endika");
		gr.addObserver(PanelConfiguracion.this);

		lblPrueba = new JLabel("Prueba");
		GridBagConstraints gbc_lblPrueba = new GridBagConstraints();
		gbc_lblPrueba.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrueba.gridx = 0;
		gbc_lblPrueba.gridy = 6;
		add(lblPrueba, gbc_lblPrueba);
		lblPrueba.setText(gr.getValue());

		btnCambiarValor = new JButton("cambiar valor");
		btnCambiarValor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gr.setValue("Jordan");
			}
		});
		GridBagConstraints gbc_btnCambiarValor = new GridBagConstraints();
		gbc_btnCambiarValor.insets = new Insets(0, 0, 0, 5);
		gbc_btnCambiarValor.gridx = 0;
		gbc_btnCambiarValor.gridy = 7;
		add(btnCambiarValor, gbc_btnCambiarValor);
		// Fin prueba Observable/Observer

		btnGuardar.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGuardar.insets = new Insets(15, 0, 15, 15);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 7;
		add(btnGuardar, gbc_btnGuardar);
	}

	private Vector<String> getAvailableLF() {
		final LookAndFeelInfo lfs[] = UIManager.getInstalledLookAndFeels();

		lookNFeelHashMap = new HashMap<>(lfs.length);
		final Vector<String> v = new Vector<>(lfs.length);

		for (final LookAndFeelInfo lf2 : lfs) {
			lookNFeelHashMap.put(lf2.getName(), lf2.getClassName());
			v.add(lf2.getName());
			if (Propiedades.getLookAndFeel().equals(lf2.getClassName())) {
				currentLookAndFeel = lf2.getName();
			}
		}
		return v;
	}

	private String guardar() {
		String mensajeError = "";
		String rutaBD = txtDatabase.getText().trim();
		String ip = txtIP.getText().trim();
		int puertoTracker;
		int puertoPeer;

		if (!txtPuertoTrackers.getText().trim().equals("")) {
			if (isNumeric(txtPuertoTrackers.getText().trim()))
				puertoTracker = Integer
						.parseInt(txtPuertoTrackers.getText().trim());
			else
				puertoTracker = 0;
		} else {
			puertoTracker = 0;
		}

		if (!txtPuertoPeers.getText().trim().equals("")) {
			if (isNumeric(txtPuertoPeers.getText().trim()))
				puertoPeer = Integer.parseInt(txtPuertoPeers.getText().trim());
			else
				puertoPeer = 0;
		} else {
			puertoPeer = 0;
		}

		Object lookandfeel = cboApariencia.getSelectedItem();
		if (!ip.matches(IPADDRESS_PATTERN)) {
			mensajeError += " - La direccion IP no es valida.\n";
		}

		if (puertoTracker > 65535 || puertoTracker < 1) {
			mensajeError += " - El puerto para los trackers debe ser un valor numerico comprendido entre 0 y 65535.\n";
		}

		if (puertoPeer > 65535 || puertoPeer < 1) {
			mensajeError += " - El puerto para los peer debe ser un valor numerico comprendido entre 0 y 65535.\n";
		}

		if (puertoPeer == puertoTracker) {
			mensajeError += " - El puerto para los peer y debe de ser distinto del de los trackers.\n";
		}

		if (rutaBD.equals(""))
			mensajeError += " - No has seleccionado ninguna ruta para la base de datos.\n";

		if (mensajeError.isEmpty()) {
			Propiedades.setIP(ip);
			Propiedades.setPuertoTracker(puertoTracker);
			Propiedades.setPuertoPeer(puertoPeer);
			Propiedades.setDatabasePath(txtDatabase.getText().trim());

			Propiedades.setLookAndFeelClass(lookNFeelHashMap.get(lookandfeel));
			try {
				UIManager.setLookAndFeel(lookNFeelHashMap.get(lookandfeel));
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(Ventana.getInstance());
		} else {
			mensajeError = "Error en los siguientes campos:\n" + mensajeError;
			JOptionPane.showMessageDialog(Ventana.getInstance(), mensajeError,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		return mensajeError;
	}

	private static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private void elegirArchivoBD() {
		// Creamos un objeto FileChooser
		fc = new JFileChooser();

		/*
		 * Mostrar la ventana para abrir archivo y recoger la respuesta En el
		 * par�metro del showOpenDialog se indica la ventana al que estar�
		 * asociado. Con el valor this se asocia a la ventana que la abre.
		 */
		int respuesta = fc.showOpenDialog(PanelConfiguracion.this);
		// Comprobar si se ha pulsado Aceptar
		if (respuesta == JFileChooser.APPROVE_OPTION) {
			// Crear un objeto File con el archivo elegido
			File archivoElegido = fc.getSelectedFile();
			// Mostrar el nombre del archvivo en un campo de texto
			txtDatabase.setText(archivoElegido.getPath());
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o == gr) {
			lblPrueba.setText(gr.getValue());
		}
	}
}