package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controladores.ControladoraConfiguracion;
import utilidades.Propiedades;

public class PanelConfiguracion extends JPanel {

	private static final long			serialVersionUID	= 4959247560481979942L;

	private JTextField					txtIP;
	private JTextField					txtPuerto;
	private JLabel						lblDatabase;
	private JButton						btnExaminar;
	private JTextField					txtDatabase;
	private JButton						btnGuardar;
	private JLabel						lblApariencia;
	private JComboBox<String>			cboApariencia;

	private ControladoraConfiguracion	controladoraConfiguracion;

	public PanelConfiguracion() {

		this.controladoraConfiguracion = new ControladoraConfiguracion();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
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

		JLabel lblPuerto = new JLabel("Puerto:");
		lblPuerto.setForeground(Color.BLACK);
		lblPuerto.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPuerto = new GridBagConstraints();
		gbc_lblPuerto.anchor = GridBagConstraints.WEST;
		gbc_lblPuerto.insets = new Insets(5, 15, 5, 5);
		gbc_lblPuerto.gridx = 0;
		gbc_lblPuerto.gridy = 1;
		add(lblPuerto, gbc_lblPuerto);

		txtPuerto = new JTextField(Integer.toString(Propiedades.getPort()));
		txtPuerto.setForeground(Color.BLACK);
		txtPuerto.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtPuerto.setColumns(20);
		GridBagConstraints gbc_txtPuerto = new GridBagConstraints();
		gbc_txtPuerto.insets = new Insets(5, 5, 5, 15);
		gbc_txtPuerto.anchor = GridBagConstraints.WEST;
		gbc_txtPuerto.gridx = 1;
		gbc_txtPuerto.gridy = 1;
		add(txtPuerto, gbc_txtPuerto);

		lblDatabase = new JLabel("Base de datos:");
		lblDatabase.setForeground(Color.BLACK);
		lblDatabase.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDatabase = new GridBagConstraints();
		gbc_lblDatabase.anchor = GridBagConstraints.EAST;
		gbc_lblDatabase.insets = new Insets(20, 15, 5, 5);
		gbc_lblDatabase.gridx = 0;
		gbc_lblDatabase.gridy = 2;
		add(lblDatabase, gbc_lblDatabase);

		txtDatabase = new JTextField();
		txtDatabase.setForeground(Color.BLACK);
		txtDatabase.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtDatabase.setColumns(20);
		GridBagConstraints gbc_txtDatabase = new GridBagConstraints();
		gbc_txtDatabase.insets = new Insets(20, 5, 5, 5);
		gbc_txtDatabase.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDatabase.gridx = 1;
		gbc_txtDatabase.gridy = 2;
		add(txtDatabase, gbc_txtDatabase);

		btnExaminar = new JButton("Examinar");
		btnExaminar.setForeground(Color.BLACK);
		btnExaminar.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_btnExaminar = new GridBagConstraints();
		gbc_btnExaminar.insets = new Insets(20, 5, 5, 15);
		gbc_btnExaminar.gridx = 2;
		gbc_btnExaminar.gridy = 2;
		add(btnExaminar, gbc_btnExaminar);

		lblApariencia = new JLabel("Apariencia:");
		lblApariencia.setForeground(Color.BLACK);
		lblApariencia.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblApariencia = new GridBagConstraints();
		gbc_lblApariencia.anchor = GridBagConstraints.WEST;
		gbc_lblApariencia.insets = new Insets(5, 15, 5, 5);
		gbc_lblApariencia.gridx = 0;
		gbc_lblApariencia.gridy = 3;
		add(lblApariencia, gbc_lblApariencia);

		cboApariencia = new JComboBox<String>(
				controladoraConfiguracion.getAvailableLF());
		cboApariencia.setForeground(Color.BLACK);
		cboApariencia.setFont(new Font("Dialog", Font.PLAIN, 14));
		cboApariencia.setSelectedItem(
				controladoraConfiguracion.getCurrentLookAndFeel());
		GridBagConstraints gbc_cboApariencia = new GridBagConstraints();
		gbc_cboApariencia.anchor = GridBagConstraints.WEST;
		gbc_cboApariencia.insets = new Insets(5, 5, 5, 15);
		gbc_cboApariencia.gridx = 1;
		gbc_cboApariencia.gridy = 3;
		add(cboApariencia, gbc_cboApariencia);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelConfiguracion.this.controladoraConfiguracion.guardar(
						txtIP.getText().trim(),
						Integer.parseInt(txtPuerto.getText().trim()),
						cboApariencia.getSelectedItem());
			}
		});
		btnGuardar.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGuardar.insets = new Insets(15, 0, 15, 15);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 5;
		add(btnGuardar, gbc_btnGuardar);
	}
}