package entidades;

import java.util.Date;

public class Tracker {

	private int		id;
	private boolean	master;
	private String	ip;
	private int		puerto;
	private Date	lastKeepAlive;
	private long	differenceBetweenKeepAlive;

	public Tracker() {

	}

	public Tracker(int id, boolean master, String ip, int puerto) {
		this.id = id;
		this.master = master;
		this.ip = ip;
		this.puerto = puerto;
		this.lastKeepAlive = null;
		this.differenceBetweenKeepAlive = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
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

	public Date getLastKeepAlive() {
		return lastKeepAlive;
	}

	public void setLastKeepAlive(Date lastKeepAlive) {
		if (lastKeepAlive != null) {
			this.differenceBetweenKeepAlive = (lastKeepAlive.getTime()
					- this.lastKeepAlive.getTime()) / 1000;
		}
		this.lastKeepAlive = lastKeepAlive;
	}

	public long getDifferenceBetweenKeepAlive() {
		return differenceBetweenKeepAlive;
	}

	public void setDifferenceBetweenKeepAlive(long differenceBetweenKeepAlive) {
		this.differenceBetweenKeepAlive = differenceBetweenKeepAlive;
	}
}