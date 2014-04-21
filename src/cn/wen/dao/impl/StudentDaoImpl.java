package cn.wen.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.org.apache.bcel.internal.util.Class2HTML;

import cn.we.dao.IStudentDao;
import cn.wen.model.Student;
import cn.wen.util.ConnectionUtil;

public class StudentDaoImpl implements IStudentDao {
	private Connection conn;
	private String sql = null;

	public StudentDaoImpl() {
		initConnection();
	}

	// 初始化链接对象
	private void initConnection() {
		conn = new ConnectionUtil().getConnection();
	}

	@Override
	public boolean save(Student stu) {
		this.sql = "insert into t_student(id,mac,name,phone_number,stu_no) values(?,?,?,?,?)";

		try {
			PreparedStatement statement = conn.prepareStatement(this.sql);
			statement.setString(1, stu.getID());
			statement.setString(2, stu.getMAC());
			statement.setString(3, stu.getName());
			statement.setString(4, stu.getPhone_Number());
			statement.setString(5, stu.getSTU_NO());

			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Student stu) {
		this.sql = "DELETE FROM t_student WHERE id = ?";

		try {
			PreparedStatement statement = conn.prepareStatement(this.sql);
			statement.setString(1, stu.getID());
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean update(Student stu) {
		this.sql = "UPDATE t_student SET name = ?,MAC = ?,phone_number = ?,stu_no = ?"
				+ "WHERE id = ?";

		try {
			PreparedStatement statement = conn.prepareStatement(this.sql);
			statement.setString(1, stu.getName());
			statement.setString(2, stu.getMAC());
			statement.setString(3, stu.getPhone_Number());
			statement.setString(4, stu.getSTU_NO());
			statement.setString(5, stu.getID());

			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Student getStudent(String id) {
		this.sql = "select * from t_student where id = ?";

		try {
			PreparedStatement statement = conn.prepareStatement(this.sql);
			statement.setString(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Student stu = new Student();
				stu.setID(result.getString("id"));
				stu.setMAC(result.getString("MAC"));
				stu.setName(result.getString("name"));
				stu.setPhone_Number(result.getString("phone_number"));
				stu.setSTU_NO(result.getString("stu_no"));
				return stu;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
