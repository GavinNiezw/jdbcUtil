package cn.wen.dao.impl;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;

import cn.we.dao.IJDBCBaseDao;
import cn.wen.model.CheckIn;
import cn.wen.model.ClassRoom;
import cn.wen.model.Student;
@SuppressWarnings("unchecked")
public class TestJDBACBaseDaoImpl {
	IJDBCBaseDao jdbc = new JDBCBaseDaoImpl();
	
	
	@Test
	public void testSave() {
		Student stu = new Student();
		stu.setID("1");
		stu.setMAC("df:df:sa:dd:df:ff");
		stu.setName("文");
		stu.setPhone_Number("02323");
		stu.setSTU_NO("12344");
		
		ClassRoom cla = new ClassRoom();
		cla.setID("11");
		cla.setMAC("adfsdf");
		cla.setName("A301");
		
		CheckIn cke = new CheckIn();
		cke.setID("1");
		cke.setC_mac(cla.getMAC());
		cke.setStu_mac(stu.getMAC());
		cke.setTime(new Date());
		
		jdbc.save(cke);
		
		//jdbc.save(stu);
		//jdbc.save(cla);
	}

	@Test
	public void testUpdate() throws Exception {
		Student stu = new Student();
		stu.setID("1");
		stu.setMAC("sdf:djfs:dskfj:dskf");
		stu.setName("文");
		stu.setPhone_Number("0757-11111");
		stu.setSTU_NO("04110425");
		
		ClassRoom cla = new ClassRoom();
		cla.setID("11");
		cla.setMAC("asdjkfl;sdfjdslkf;dsfkdslf");
		cla.setName("B230");
		
		jdbc.update(stu);
		jdbc.update(cla);
	}

	@Test
	public void testDelete() {
		Student stu = new Student();
		stu.setID("1");
		
		ClassRoom cla = new ClassRoom();
		cla.setID("11");
		
		jdbc.delete(stu);
		jdbc.delete(cla);
	}

	@Test
	public void testFindById() {
		Student stu = new Student();
		stu.setID("2");
		jdbc.findById(stu);
	}

}
