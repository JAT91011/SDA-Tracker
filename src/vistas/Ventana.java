package vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import vistas.componentes.JSlidePanel;

public class Ventana extends JFrame {

	private static final long	serialVersionUID	= -8641413596663241575L;
	private static Ventana		instance;
	private JPanel				container;
	private JSlidePanel<JFrame>	slider;

	private Ventana() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(null);
		setSize(600, 400);
		setIconImage((new ImageIcon("icons/app-icon.png")).getImage());
		setMinimumSize(new Dimension(700, 500));
		setTitle("Tracker");
		setLocationRelativeTo(null);

		this.slider = new JSlidePanel<JFrame>(this);
		this.container = slider.getBasePanel();
		getContentPane().add(this.container, BorderLayout.CENTER);
	}

	public JSlidePanel<JFrame> getSlider() {
		return this.slider;
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