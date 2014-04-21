package cn.wen.dao.impl;

import org.junit.Test;

import cn.we.dao.IStudentDao;
import cn.wen.model.Student;

public class TestStudentDaoImpl {
	IStudentDao stuDao = new StudentDaoImpl(); 
	
	@Test
	public void testSave() {
		Student stu = new Student();
		stu.setID("1");
		stu.setMAC("df:df:sa:dd:df:ff");
		stu.setName("文");
		stu.setPhone_Number("02323");
		stu.setSTU_NO("12344");
		stuDao.save(stu);
	}
	@Test
	public void testDelete() {
		Student stu = new Student();
		stu.setID("1");
		stuDao.delete(stu);
	}
	
	@Test
	public void testUpdate(){
		Student stu = new Student();
		stu.setID("1");
		stu.setMAC("11111111111111111");
		stu.setName("11");
		stu.setPhone_Number("1111");
		stu.setSTU_NO("1111111");
		stuDao.update(stu);
	}
	
	@Test
	public void testGetStudent(){
		System.out.println(stuDao.getStudent("1"));
	}
	
	
	

}
