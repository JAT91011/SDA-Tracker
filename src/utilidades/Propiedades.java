package utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Propiedades implements Serializable {

	private static final long	serialVersionUID	= -7184695896737258947L;

	private static Propiedades	propiedades;

	private String				ip;
	private int					puertoTracker;
	private int					puertoPeer;
	private String				rutaBaseDatos;
	private String				lookAndFeelClass;

	private Propiedades(final String ip, final int puertoTracker,
			final int puertoPeer, String rutaBaseDatos,
			String lookAndFeelClass) {

		this.ip = ip;
		this.puertoTracker = puertoTracker;
		this.puertoPeer = puertoPeer;
		this.rutaBaseDatos = rutaBaseDatos;
		this.lookAndFeelClass = lookAndFeelClass;
	}

	private void update() {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(
					new FileOutputStream("data/config.properties"));
			oos.writeObject(propiedades);
			oos.close();
		} catch (final IOException e) {
			e.printStackTrace();
			propiedades = new Propiedades("", 0, 0, "",
					"javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
	}

	private static void init() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(
					new FileInputStream("data/config.properties"));
			propiedades = (Propiedades) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			if (!(e instanceof FileNotFoundException)) {
				e.printStackTrace();
			}
			propiedades = new Propiedades("", 0, 0, "",
					"javax.swing.plaf.nimbus.NimbusLookAndFeel");
			propiedades.update();
		}
	}

	public static String getIP() {
		if (propiedades == null) {
			init();
		}
		return propiedades.ip;
	}

	public static void setIP(final String ip) {
		if (propiedades == null) {
			init();
		}
		propiedades.ip = ip;
		propiedades.update();
	}

	public static int getPuertoTracker() {
		if (propiedades == null) {
			init();
		}
		return propiedades.puertoTracker;
	}

	public static void setPuertoTracker(final int puertoTracker) {
		if (propiedades == null) {
			init();
		}
		propiedades.puertoTracker = puertoTracker;
		propiedades.update();
	}

	public static int getPuertoPeer() {
		if (propiedades == null) {
			init();
		}
		return propiedades.puertoPeer;
	}

	public static void setPuertoPeer(final int puertoPeer) {
		if (propiedades == null) {
			init();
		}
		propiedades.puertoPeer = puertoPeer;
		propiedades.update();
	}

	public static String getRutaBaseDatos() {
		if (propiedades == null) {
			init();
		}
		return propiedades.rutaBaseDatos;
	}

	public static void setRutaBaseDatos(final String rutaBaseDatos) {
		if (propiedades == null) {
			init();
		}
		propiedades.rutaBaseDatos = rutaBaseDatos;
		propiedades.update();
	}

	public static String getLookAndFeel() {
		if (propiedades == null) {
			init();
		}
		return propiedades.lookAndFeelClass;
	}

	public static void setLookAndFeelClass(final String lookAndFeelClass) {
		if (propiedades == null) {
			init();
		}
		if (isLFAvailable(lookAndFeelClass)) {
			propiedades.lookAndFeelClass = lookAndFeelClass;
		}
		propiedades.update();
	}

	private static boolean isLFAvailable(final String lf) {
		final LookAndFeelInfo lfs[] = UIManager.getInstalledLookAndFeels();
		for (final LookAndFeelInfo lf2 : lfs) {
			if (lf2.getClassName().equals(lf)) {
				return true;
			}
		}
		return false;
	}
}
