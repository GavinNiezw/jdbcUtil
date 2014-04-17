package cn.we.dao;

import cn.wen.model.Student;

public interface IStudentDao {
	public boolean save(Student stu);
	public boolean delete(Student stu);
	public boolean update(Student stu);
	public Student  getStudent(String id);
	
}	
