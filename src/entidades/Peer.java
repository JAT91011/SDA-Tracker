package entidades;

import java.util.Vector;

public class Peer {

	private int				id;
	private String			ip;
	private int				puerto;
	private Vector<String>	hashmapContenidos;

	public Peer() {

	}

	public Peer(int id, String ip, int puerto,
			Vector<String> hashmapContenidos) {
		super();
		this.id = id;
		this.ip = ip;
		this.puerto = puerto;
		this.hashmapContenidos = hashmapContenidos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public Vector<String> getHashmapContenidos() {
		return hashmapContenidos;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public void setHashmapContenidos(Vector<String> hashmapContenidos) {
		this.hashmapContenidos = hashmapContenidos;
	}
}