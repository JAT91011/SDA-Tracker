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

	private static final long serialVersionUID = -7184695896737258947L;

	private static Propiedades propiedades;

	private String	ip;
	private int		port;
	private String	databasePath;
	private String	lookAndFeelClass;

	private Propiedades(final String ip, final int port, final String databasePath,
			String lookAndFeelClass) {

		this.ip = ip;
		this.port = port;
		this.databasePath = databasePath;
		this.lookAndFeelClass = lookAndFeelClass;
	}

	private void update() {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("data/config.properties"));
			oos.writeObject(propiedades);
			oos.close();
		} catch (final IOException e) {
			e.printStackTrace();
			propiedades = new Propiedades("", 0, "", UIManager.getSystemLookAndFeelClassName());
		}
	}

	private static void init() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("data/config.properties"));
			propiedades = (Propiedades) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			if (!(e instanceof FileNotFoundException)) {
				e.printStackTrace();
			}
			propiedades = new Propiedades("", 0, "", UIManager.getSystemLookAndFeelClassName());
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

	public static int getPort() {
		if (propiedades == null) {
			init();
		}
		return propiedades.port;
	}

	public static void setPort(final int port) {
		if (propiedades == null) {
			init();
		}
		propiedades.port = port;
		propiedades.update();
	}

	public static String getDatabasePath() {
		if (propiedades == null) {
			init();
		}
		return propiedades.databasePath;
	}

	public static void setDatabasePath(final String databasePath) {
		if (propiedades == null) {
			init();
		}
		propiedades.databasePath = databasePath;
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
