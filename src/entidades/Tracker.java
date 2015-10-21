package entidades;

public class Tracker {

	private int		id;
	private String	ip;
	private int		puerto;

	public Tracker() {

	}

	public Tracker(int id, String ip, int puerto) {
		this.id = id;
		this.ip = ip;
		this.puerto = puerto;
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

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
}
