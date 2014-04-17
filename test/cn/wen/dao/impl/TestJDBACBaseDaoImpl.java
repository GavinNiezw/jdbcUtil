package cn.wen.dao.impl;

import static org.junit.Assert.fail;

import org.junit.Test;

import cn.we.dao.IJDBCBaseDao;
import cn.wen.model.ClassRoom;
import cn.wen.model.Student;

public class TestJDBACBaseDaoImpl {
	IJDBCBaseDao jdbc = new JDBCBaseDaoImpl();
	
	@Test
	public void testSave() {
		Student stu = new Student();
		stu.setID("1");
		stu.setMAC("df:df:sa:dd:df:ff");
		stu.setName("文");
		stu.setPhoneNumber("02323");
		stu.setSTU_NO("12344");
		
		jdbc.save(stu);
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

}
