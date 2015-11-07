package aplicacion;

import java.awt.EventQueue;

import javax.swing.UIManager;

import utilidades.LogErrores;
import vistas.PanelPestanas;
import vistas.StartPanel;
import vistas.Ventana;

public class Aplicacion {

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					StartPanel panel = new StartPanel();
					Ventana.getInstance().getSlider().addComponent(panel);
					Ventana.getInstance().getSlider().addComponent(new PanelPestanas());
					Ventana.getInstance().setVisible(true);
					panel.getNextButton().requestFocus();
				} catch (Exception e) {
					LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
					}.getClass().getEnclosingMethod().getName(), e.toString());
					e.printStackTrace();
				}
			}
		});
	}
}