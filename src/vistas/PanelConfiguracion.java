package vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;

import controladores.ControladorConfiguracion;
import entidades.Tracker;
import utilidades.Propiedades;
import vistas.componentes.IFileChooser;
import vistas.componentes.ITextField;

public class PanelConfiguracion extends JPanel {

	private static final long				serialVersionUID	= 4959247560481979942L;
	private ITextField						txtIP;
	private ITextField						txtPuertoTrackers;
	private ITextField						txtPuertoPeers;
	private JButton							btnConectar;
	private JLabel							lblApariencia;
	private JComboBox<String>				cboApariencia;

	private HashMap<String, String>			lookNFeelHashMap;
	private String							currentLookAndFeel;

	private static String					IPADDRESS_PATTERN	= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private JLabel							lblPuertoPeers;
	private JPanel							panAjustes;
	private JButton							btnResetear;
	private JLabel							lblEstado;
	private JLabel							lblEstadoActual;
	private JLabel							lblRutaBaseDatos;
	private vistas.componentes.IFileChooser	panFileChooser;

	private ControladorConfiguracion		cc;

	public PanelConfiguracion() {
		setOpaque(false);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 500, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 102, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		panAjustes = new JPanel();
		panAjustes.setBackground(SystemColor.inactiveCaption);
		panAjustes.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_panAjustes = new GridBagConstraints();
		gbc_panAjustes.insets = new Insets(0, 0, 5, 5);
		gbc_panAjustes.fill = GridBagConstraints.BOTH;
		gbc_panAjustes.gridx = 1;
		gbc_panAjustes.gridy = 1;
		add(panAjustes, gbc_panAjustes);
		GridBagLayout gbl_panAjustes = new GridBagLayout();
		gbl_panAjustes.columnWidths = new int[] { 0, 0, 0 };
		gbl_panAjustes.rowHeights = new int[] { 0, 45, 45, 45, 45, 52, 0 };
		gbl_panAjustes.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panAjustes.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panAjustes.setLayout(gbl_panAjustes);

		lblEstado = new JLabel("Estado:");
		lblEstado.setForeground(Color.BLACK);
		lblEstado.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblEstado = new GridBagConstraints();
		gbc_lblEstado.anchor = GridBagConstraints.WEST;
		gbc_lblEstado.insets = new Insets(15, 15, 5, 5);
		gbc_lblEstado.gridx = 0;
		gbc_lblEstado.gridy = 0;
		panAjustes.add(lblEstado, gbc_lblEstado);

		lblEstadoActual = new JLabel("Desconectado");
		lblEstadoActual.setForeground(new Color(220, 20, 60));
		lblEstadoActual.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblEstadoActual = new GridBagConstraints();
		gbc_lblEstadoActual.anchor = GridBagConstraints.WEST;
		gbc_lblEstadoActual.insets = new Insets(15, 7, 5, 15);
		gbc_lblEstadoActual.gridx = 1;
		gbc_lblEstadoActual.gridy = 0;
		panAjustes.add(lblEstadoActual, gbc_lblEstadoActual);

		JLabel lblIP = new JLabel("Direcci\u00F3n IP:");
		GridBagConstraints gbc_lblIP = new GridBagConstraints();
		gbc_lblIP.anchor = GridBagConstraints.WEST;
		gbc_lblIP.insets = new Insets(5, 15, 5, 5);
		gbc_lblIP.gridx = 0;
		gbc_lblIP.gridy = 1;
		panAjustes.add(lblIP, gbc_lblIP);
		lblIP.setForeground(Color.BLACK);
		lblIP.setFont(new Font("Dialog", Font.PLAIN, 14));

		txtIP = new ITextField(Propiedades.getIP());
		txtIP.setErrorIcon(new ImageIcon("icons/error-icon.png"));
		GridBagConstraints gbc_txtIP = new GridBagConstraints();
		gbc_txtIP.insets = new Insets(5, 5, 5, 15);
		gbc_txtIP.fill = GridBagConstraints.BOTH;
		gbc_txtIP.gridx = 1;
		gbc_txtIP.gridy = 1;
		panAjustes.add(txtIP, gbc_txtIP);
		txtIP.setFont(new Font("Dialog", Font.PLAIN, 14));

		JLabel lblPuertoTrackers = new JLabel("Puerto trackers:");
		GridBagConstraints gbc_lblPuertoTrackers = new GridBagConstraints();
		gbc_lblPuertoTrackers.anchor = GridBagConstraints.WEST;
		gbc_lblPuertoTrackers.insets = new Insets(5, 15, 5, 5);
		gbc_lblPuertoTrackers.gridx = 0;
		gbc_lblPuertoTrackers.gridy = 2;
		panAjustes.add(lblPuertoTrackers, gbc_lblPuertoTrackers);
		lblPuertoTrackers.setForeground(Color.BLACK);
		lblPuertoTrackers.setFont(new Font("Dialog", Font.PLAIN, 14));

		txtPuertoTrackers = new ITextField(
				Integer.toString(Propiedades.getPuertoTracker()));
		txtPuertoTrackers.setErrorIcon(new ImageIcon("icons/error-icon.png"));
		txtPuertoTrackers.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		GridBagConstraints gbc_txtPuertoTrackers = new GridBagConstraints();
		gbc_txtPuertoTrackers.fill = GridBagConstraints.BOTH;
		gbc_txtPuertoTrackers.insets = new Insets(5, 5, 5, 15);
		gbc_txtPuertoTrackers.gridx = 1;
		gbc_txtPuertoTrackers.gridy = 2;
		panAjustes.add(txtPuertoTrackers, gbc_txtPuertoTrackers);
		txtPuertoTrackers.setForeground(Color.BLACK);
		txtPuertoTrackers.setFont(new Font("Dialog", Font.PLAIN, 14));

		lblPuertoPeers = new JLabel("Puerto peers:");
		GridBagConstraints gbc_lblPuertoPeers = new GridBagConstraints();
		gbc_lblPuertoPeers.anchor = GridBagConstraints.WEST;
		gbc_lblPuertoPeers.insets = new Insets(5, 15, 5, 5);
		gbc_lblPuertoPeers.gridx = 0;
		gbc_lblPuertoPeers.gridy = 3;
		panAjustes.add(lblPuertoPeers, gbc_lblPuertoPeers);
		lblPuertoPeers.setForeground(Color.BLACK);
		lblPuertoPeers.setFont(new Font("Dialog", Font.PLAIN, 14));

		txtPuertoPeers = new ITextField(
				Integer.toString(Propiedades.getPuertoPeer()));
		txtPuertoPeers.setErrorIcon(new ImageIcon("icons/error-icon.png"));
		txtPuertoPeers.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		GridBagConstraints gbc_txtPuertoPeers = new GridBagConstraints();
		gbc_txtPuertoPeers.fill = GridBagConstraints.BOTH;
		gbc_txtPuertoPeers.insets = new Insets(5, 5, 5, 15);
		gbc_txtPuertoPeers.gridx = 1;
		gbc_txtPuertoPeers.gridy = 3;
		panAjustes.add(txtPuertoPeers, gbc_txtPuertoPeers);
		txtPuertoPeers.setForeground(Color.BLACK);
		txtPuertoPeers.setFont(new Font("Dialog", Font.PLAIN, 14));

		lblRutaBaseDatos = new JLabel("Ruta base de datos:");
		lblRutaBaseDatos.setForeground(Color.BLACK);
		lblRutaBaseDatos.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblRutaBaseDatos = new GridBagConstraints();
		gbc_lblRutaBaseDatos.anchor = GridBagConstraints.WEST;
		gbc_lblRutaBaseDatos.insets = new Insets(5, 15, 5, 5);
		gbc_lblRutaBaseDatos.gridx = 0;
		gbc_lblRutaBaseDatos.gridy = 4;
		panAjustes.add(lblRutaBaseDatos, gbc_lblRutaBaseDatos);

		panFileChooser = new IFileChooser();
		panFileChooser.setButtonIcon(new ImageIcon("icons/file-icon.png"));
		GridBagConstraints gbc_panFileChooser = new GridBagConstraints();
		gbc_panFileChooser.insets = new Insets(5, 5, 5, 15);
		gbc_panFileChooser.fill = GridBagConstraints.BOTH;
		gbc_panFileChooser.gridx = 1;
		gbc_panFileChooser.gridy = 4;
		panAjustes.add(panFileChooser, gbc_panFileChooser);

		lblApariencia = new JLabel("Apariencia:");
		GridBagConstraints gbc_lblApariencia = new GridBagConstraints();
		gbc_lblApariencia.anchor = GridBagConstraints.WEST;
		gbc_lblApariencia.insets = new Insets(5, 15, 15, 5);
		gbc_lblApariencia.gridx = 0;
		gbc_lblApariencia.gridy = 5;
		panAjustes.add(lblApariencia, gbc_lblApariencia);
		lblApariencia.setForeground(Color.BLACK);
		lblApariencia.setFont(new Font("Dialog", Font.PLAIN, 14));

		cboApariencia = new JComboBox<String>(getAvailableLF());
		GridBagConstraints gbc_cboApariencia = new GridBagConstraints();
		gbc_cboApariencia.fill = GridBagConstraints.BOTH;
		gbc_cboApariencia.insets = new Insets(5, 5, 15, 15);
		gbc_cboApariencia.gridx = 1;
		gbc_cboApariencia.gridy = 5;
		panAjustes.add(cboApariencia, gbc_cboApariencia);
		cboApariencia.setForeground(Color.BLACK);
		cboApariencia.setFont(new Font("Dialog", Font.PLAIN, 14));
		cboApariencia.setSelectedItem(currentLookAndFeel);

		JPanel panBotonera = new JPanel();
		panBotonera.setOpaque(false);
		GridBagConstraints gbc_panBotonera = new GridBagConstraints();
		gbc_panBotonera.insets = new Insets(10, 0, 5, 5);
		gbc_panBotonera.fill = GridBagConstraints.BOTH;
		gbc_panBotonera.gridx = 1;
		gbc_panBotonera.gridy = 2;
		add(panBotonera, gbc_panBotonera);
		GridBagLayout gbl_panBotonera = new GridBagLayout();
		gbl_panBotonera.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panBotonera.rowHeights = new int[] { 0, 0 };
		gbl_panBotonera.columnWeights = new double[] { 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panBotonera.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panBotonera.setLayout(gbl_panBotonera);

		btnResetear = new JButton("Resetear");
		btnResetear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtIP.setText("127.0.0.1");
				txtIP.showAsHint(true);
				txtIP.hideError();
				txtPuertoPeers.setText("2000");
				txtPuertoPeers.showAsHint(true);
				txtPuertoPeers.hideError();
				txtPuertoTrackers.setText("1000");
				txtPuertoTrackers.showAsHint(true);
				txtPuertoTrackers.hideError();
			}
		});
		btnResetear.setForeground(Color.BLACK);
		btnResetear.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_btnResetear = new GridBagConstraints();
		gbc_btnResetear.anchor = GridBagConstraints.WEST;
		gbc_btnResetear.insets = new Insets(0, 0, 0, 5);
		gbc_btnResetear.gridx = 0;
		gbc_btnResetear.gridy = 0;
		panBotonera.add(btnResetear, gbc_btnResetear);

		cc = new ControladorConfiguracion();

		btnConectar = new JButton("Establecer conexi\u00F3n");
		GridBagConstraints gbc_btnConectar = new GridBagConstraints();
		gbc_btnConectar.anchor = GridBagConstraints.EAST;
		gbc_btnConectar.gridx = 2;
		gbc_btnConectar.gridy = 0;
		panBotonera.add(btnConectar, gbc_btnConectar);
		btnConectar.setForeground(Color.BLACK);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Pulsa");
				System.out.println(cc.estaConectado());
				if (!cc.estaConectado()) {
					if (PanelConfiguracion.this.guardar().equals("")) {
						// Ejemplo de interacci�n entre clase y uso de los
						// patrones
						// Observable/Observer.
						cc.conectarTracker(new Tracker(cc.numeroTrackers() + 1,
								cc.numeroTrackers() == 0,
								txtIP.getText().trim(), Integer.parseInt(
										txtPuertoTrackers.getText().trim())));

						/*
						 * Modificamos el estado de la conexi�n y el nombre del
						 * bot�n a desconectar (para poder forzar el fallo desde
						 * la aplicaci�n)
						 */
						lblEstadoActual.setText("Conectado");
						lblEstadoActual.setForeground(new Color(0, 153, 0));

						btnConectar.setText("Desconectar");
					}
				} else {
					JOptionPane.showMessageDialog(Ventana.getInstance(),
							"Se acaba de desconectar el tracker.",
							"Tracker desconectado",
							JOptionPane.INFORMATION_MESSAGE);

					// Continuamos con el ejemplo en el que se desconectar al
					// tracker conectado con los
					// valores de los campos del formulario al darle a
					// desconectar (es un ejemplo de
					// prueba).

					Tracker t = new Tracker(cc.numeroTrackers(), true,
							txtIP.getText().trim(), Integer.parseInt(
									txtPuertoTrackers.getText().trim()));

					cc.desconectarTracker(t);

					// Restauramos los valores del boton y del label de estado
					// actual.
					lblEstadoActual.setText("Desconectado");
					lblEstadoActual.setForeground(new Color(220, 20, 60));

					btnConectar.setText("Establecer conexi\u00F3n");
				}
			}
		});

		btnConectar.setFont(new Font("Dialog", Font.PLAIN, 14));
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
		String ip = txtIP.getText().trim();
		int puertoTracker = 0;
		int puertoPeer = 0;
		String rutaBaseDatos = "";

		if (!txtPuertoTrackers.getText().trim().isEmpty()) {
			puertoTracker = Integer
					.parseInt(txtPuertoTrackers.getText().trim());
			txtPuertoTrackers.hideError();
		} else {
			txtPuertoTrackers.showError();
		}

		if (!txtPuertoPeers.getText().trim().isEmpty()) {
			puertoPeer = Integer.parseInt(txtPuertoPeers.getText().trim());
			txtPuertoPeers.hideError();
		} else {
			txtPuertoPeers.showError();
		}

		Object lookandfeel = cboApariencia.getSelectedItem();
		if (!ip.matches(IPADDRESS_PATTERN)) {
			mensajeError += " - La direccion IP no es valida.\n";
			txtIP.showError();
		} else {
			txtIP.hideError();
		}

		if (puertoTracker > 65535 || puertoTracker < 1) {
			mensajeError += " - El puerto para los trackers debe ser un valor numerico comprendido entre 0 y 65535.\n";
			txtPuertoTrackers.showError();
		} else {
			txtPuertoTrackers.hideError();
		}

		if (puertoPeer > 65535 || puertoPeer < 1) {
			mensajeError += " - El puerto para los peer debe ser un valor numerico comprendido entre 0 y 65535.\n";
			txtPuertoPeers.showError();
		} else {
			txtPuertoPeers.hideError();
		}

		if (puertoPeer == puertoTracker) {
			mensajeError += " - El puerto para los peer y debe de ser distinto del de los trackers.\n";
			txtPuertoTrackers.showError();
			txtPuertoPeers.showError();
		} else {
			if (puertoPeer != 0) {
				txtPuertoPeers.hideError();
			}
			if (puertoTracker != 0) {
				txtPuertoTrackers.hideError();
			}
		}

		if (panFileChooser.getFile() == null) {
			mensajeError += " - No has seleccionado ninguna ruta para la base de datos.\n";
			panFileChooser.setErrorVisible(true);
		} else if (!panFileChooser.getFile().exists()) {
			mensajeError += " - No existe el fichero seleccionado.\n";
			panFileChooser.setErrorVisible(true);
		} else {
			panFileChooser.setErrorVisible(false);
		}

		if (mensajeError.isEmpty()) {
			Propiedades.setIP(ip);
			Propiedades.setPuertoTracker(puertoTracker);
			Propiedades.setPuertoPeer(puertoPeer);
			Propiedades.setRutaBaseDatos(rutaBaseDatos);
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
}