package cn.wen.model;

import java.util.Date;

public class CheckIn {
	private String ID;
	private String c_mac;
	private String stu_mac;
	private Date time;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}


	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getC_mac() {
		return c_mac;
	}

	public void setC_mac(String c_mac) {
		this.c_mac = c_mac;
	}

	public String getStu_mac() {
		return stu_mac;
	}

	public void setStu_mac(String stu_mac) {
		this.stu_mac = stu_mac;
	}

	
}
