package utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Properties implements Serializable {

	private static final long serialVersionUID = -7184695896737258947L;

	private static Properties properties;

	private String	ip;
	private int		portTracker;
	private int		portPeer;
	private String	databasePath;

	private Properties(final String ip, final int portTracker, final int portPeer, String databasePath) {

		this.ip = ip;
		this.portTracker = portTracker;
		this.portPeer = portPeer;
		this.databasePath = databasePath;
	}

	private void update() {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("data/config.properties"));
			oos.writeObject(properties);
			oos.close();
		} catch (final IOException e) {
			e.printStackTrace();
			properties = new Properties("", 0, 0, "");
		}
	}

	private static void init() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("data/config.properties"));
			properties = (Properties) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			if (!(e instanceof FileNotFoundException)) {
				e.printStackTrace();
			}
			properties = new Properties("", 0, 0, "");
			properties.update();
		}
	}

	public static String getIp() {
		if (properties == null) {
			init();
		}
		return properties.ip;
	}

	public static void setIp(final String ip) {
		if (properties == null) {
			init();
		}
		properties.ip = ip;
		properties.update();
	}

	public static int getPuertoTracker() {
		if (properties == null) {
			init();
		}
		return properties.portTracker;
	}

	public static void setPuertoTracker(final int portTracker) {
		if (properties == null) {
			init();
		}
		properties.portTracker = portTracker;
		properties.update();
	}

	public static int getPuertoPeer() {
		if (properties == null) {
			init();
		}
		return properties.portPeer;
	}

	public static void setPuertoPeer(final int portPeer) {
		if (properties == null) {
			init();
		}
		properties.portPeer = portPeer;
		properties.update();
	}

	public static String getRutaBaseDatos() {
		if (properties == null) {
			init();
		}
		return properties.databasePath;
	}

	public static void setRutaBaseDatos(final String databasePath) {
		if (properties == null) {
			init();
		}
		properties.databasePath = databasePath;
		properties.update();
	}
}