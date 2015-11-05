package entidades;

import java.util.Date;

public class Tracker {

	private int		id;
	private boolean	master;
	private Date	lastKeepAlive;
	private long	differenceBetweenKeepAlive;

	public Tracker() {

	}

	public Tracker(int id, boolean master) {
		this.id = id;
		this.master = master;
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

	public Date getLastKeepAlive() {
		return lastKeepAlive;
	}

	public void setLastKeepAlive(Date lastKeepAlive) {
		this.lastKeepAlive = lastKeepAlive;
	}

	public long getDifferenceBetweenKeepAlive() {
		if (this.lastKeepAlive != null) {
			return (new Date().getTime() - this.lastKeepAlive.getTime()) / 1000;
		} else {
			return 0;
		}
	}

	public void setDifferenceBetweenKeepAlive(long differenceBetweenKeepAlive) {
		this.differenceBetweenKeepAlive = differenceBetweenKeepAlive;
	}
}