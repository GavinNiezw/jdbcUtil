package cn.wen.model;

import java.util.Date;

public class CheckIn {
	private String ID;
	private ClassRoom classRoom;
	private Student student;
	private Date time;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
