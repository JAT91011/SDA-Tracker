package aplicacion;

import java.awt.EventQueue;
import java.util.Date;

import javax.swing.UIManager;

import utilidades.LogErrores;
import utilidades.Propiedades;
import vistas.PanelPestanas;
import vistas.Ventana;

public class Aplicacion {

	public static void main(final String[] args) {
		System.out.println("Tiempo1: " + new Date().getTime());
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Tiempo2: " + new Date().getTime());
		System.out.println("MAX: " + Integer.toString(Integer.MAX_VALUE));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(Propiedades.getLookAndFeel());
					Ventana.getInstance().setContentPane(new PanelPestanas());
					Ventana.getInstance().setVisible(true);
				} catch (Exception e) {
					LogErrores.getInstance().writeLog(this.getClass().getName(), new Object() {
					}.getClass().getEnclosingMethod().getName(), e.toString());
					e.printStackTrace();
				}
			}
		});
	}
}