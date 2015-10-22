package aplicacion;

import java.awt.EventQueue;

import javax.swing.UIManager;

import utilidades.LogErrores;
import utilidades.Propiedades;
import vistas.PanelPestanas;
import vistas.Ventana;

public class Aplicacion {

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(Propiedades.getLookAndFeel());
					Ventana.getInstance().setContentPane(new PanelPestanas());
					Ventana.getInstance().setVisible(true);
				} catch (Exception e) {
					LogErrores.getInstance().writeLog(this.getClass().getName(),
							new Object() {
					}.getClass().getEnclosingMethod().getName(),
							e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}