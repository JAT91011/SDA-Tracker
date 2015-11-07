package vistas.componentes;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import utilidades.Properties;
import vistas.Ventana;

public class IFileChooser extends JPanel {

	private static final long	serialVersionUID	= 8561716082218295502L;
	private JFileChooser		fileChooser;
	private ITextField			textFieldPath;
	private File				file;
	private JButton				btnExaminar;

	public IFileChooser() {
		setOpaque(false);

		this.fileChooser = new JFileChooser();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		file = new File(Properties.getRutaBaseDatos());
		textFieldPath = new ITextField("");
		textFieldPath.setFont(new Font("Calibri", Font.PLAIN, 14));
		textFieldPath.setText(Properties.getRutaBaseDatos());
		textFieldPath.setFocusable(false);
		textFieldPath.setEditable(false);
		textFieldPath.setErrorIcon(new ImageIcon("icons/error-icon.png"));
		GridBagConstraints gbc_textFieldRuta = new GridBagConstraints();
		gbc_textFieldRuta.insets = new Insets(3, 0, 3, 0);
		gbc_textFieldRuta.fill = GridBagConstraints.BOTH;
		gbc_textFieldRuta.gridx = 0;
		gbc_textFieldRuta.gridy = 0;
		add(textFieldPath, gbc_textFieldRuta);
		textFieldPath.setColumns(10);

		btnExaminar = new JButton("Examinar");
		btnExaminar.setToolTipText(
				"Haz click para buscar la ruta de la base de datos");
		btnExaminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int respuesta = fileChooser
						.showOpenDialog(Ventana.getInstance());
				if (respuesta == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					textFieldPath.setText(file.getPath());
				}
			}
		});
		btnExaminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_btnExaminar = new GridBagConstraints();
		gbc_btnExaminar.fill = GridBagConstraints.VERTICAL;
		gbc_btnExaminar.gridx = 1;
		gbc_btnExaminar.gridy = 0;
		add(btnExaminar, gbc_btnExaminar);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		this.textFieldPath.setText(file.getPath());
	}

	public void setButtonIcon(ImageIcon icon) {
		btnExaminar.setText("");
		btnExaminar.setIcon(icon);
	}

	public void setButtonText(String text) {
		btnExaminar.setText(text);
	}

	public void reset() {
		textFieldPath.setText("");
		file = null;
	}

	public void setEnable(boolean enable) {
		btnExaminar.setEnabled(enable);
	}

	public void setErrorVisible(boolean visible) {
		if (visible) {
			textFieldPath.showError();
		} else {
			textFieldPath.hideError();
		}
	}
}