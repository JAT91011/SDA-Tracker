package controladores;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import interfaz.Ventana;
import utilidades.Propiedades;

public class ControladoraConfiguracion {

	private HashMap<String, String>	lookNFeelHashMap;
	private String					currentLookAndFeel;

	private static String			IPADDRESS_PATTERN	= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public ControladoraConfiguracion() {

	}

	/**
	 * Funcion para guardar los datos de configuracion
	 * 
	 * @param ip
	 *            Direccion IP multicast
	 * @param puerto
	 *            Puerto del servidor
	 * @param lookandfeel
	 *            Apariencia grafica
	 * @return Mensaje de error si hubiese
	 */
	public String guardar(final String ip, final int puerto,
			final Object lookandfeel) {

		String mensajeError = "";

		if (!ip.matches(IPADDRESS_PATTERN)) {
			mensajeError += " - La dirección IP no es válida.\n";
		}

		if (puerto > 65535 || puerto < 1) {
			mensajeError += " - El puerto debe ser un valor comprendido entre 0 y 65535.";
		}

		if (mensajeError.isEmpty()) {
			Propiedades.setIP(ip);
			Propiedades.setPort(puerto);
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

	/**
	 * Funcion para obtener los look and feel disponibles en el equipo
	 * 
	 * @return Vector con todos los look and feel disponibles
	 */
	public Vector<String> getAvailableLF() {
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

	/**
	 * Funcion para obtener el lookandfeel actual
	 * 
	 * @return Look and feel actual
	 */
	public String getCurrentLookAndFeel() {
		return this.currentLookAndFeel;
	}
}