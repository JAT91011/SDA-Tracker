package aplicacion;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import interfaz.PanelPestaņas;
import interfaz.Ventana;

public class Aplicacion {

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Ventana.getInstance().setContentPane(new PanelPestaņas());
		Ventana.getInstance().setVisible(true);
	}
}