package vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana extends JFrame {

	private static final long	serialVersionUID	= -8641413596663241575L;
	private static Ventana		instance;
	private JPanel				container;

	private Ventana() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(null);
		setSize(600, 400);
		setIconImage((new ImageIcon("icons/app-icon.png")).getImage());
		setMinimumSize(new Dimension(650, 450));
		setTitle("Tracker");
		setLocationRelativeTo(null);

		JPanel container = new JPanel();
		getContentPane().add(container, BorderLayout.CENTER);
	}

	public void setContainer(JPanel panel) {
		if (container != null) {
			instance.getContentPane().remove(container);
		}
		container = panel;
		getContentPane().add(container, BorderLayout.CENTER);
	}

	public static Ventana getInstance() {
		if (instance == null) {
			instance = new Ventana();
		}
		return instance;
	}
}