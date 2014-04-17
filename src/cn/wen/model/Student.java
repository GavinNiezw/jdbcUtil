package cn.wen.model;

public class Student {
	private String ID;
	private String Name;
	private String MAC;
	private String PhoneNumber;
	private String STU_NO;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getSTU_NO() {
		return STU_NO;
	}

	public void setSTU_NO(String sTU_NO) {
		STU_NO = sTU_NO;
	}

	@Override
	public String toString() {
		return "Student [ID=" + ID + ", Name=" + Name + ", MAC=" + MAC
				+ ", PhoneNumber=" + PhoneNumber + ", STU_NO=" + STU_NO + "]";
	}
	
	

}
