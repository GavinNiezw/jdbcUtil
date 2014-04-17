package cn.wen.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.we.dao.IJDBCBaseDao;
import cn.wen.util.ConnectionUtil;

public class JDBCBaseDaoImpl<T> implements IJDBCBaseDao<T> {
	private Connection conn;
	private StringBuffer sql = null;

	// 构造函数
	public JDBCBaseDaoImpl() {
		initConnection();
	}

	// 初始化链接对象
	private void initConnection() {
		conn = new ConnectionUtil().getConnection();
	}

	@Override
	public void save(T obj) {
		/* 1.组装sql ： 目标:insert into TABLE values(?,?,?...,?) */
		// 该步骤 组装成 "insert into T_TABLE_NAME values(" :
		sql = new StringBuffer("insert into t_"
				+ obj.getClass().getSimpleName().toLowerCase() + " values("); // obj.getClass().getSimpleName().toLowerCase()
																				// 返回一个小写的类名
		// 获得带有字符串get的所有方法的对象
		Method[] method = obj.getClass().getMethods();
		List<Method> methods = new ArrayList<Method>();
		/* 循环获取get方法 */
		for (Method met : method) {
			
			  ///met.getName().indexOf("get") >= 0 获取实体中以get开头的所有方法
			 // met.getName().indexOf("getClass")!= 0 去掉getClass（）这个方法；
			 
			if (met.getName().indexOf("get") >= 0
					&& met.getName().indexOf("getClass") != 0) {
				// 得到所有方法的名称 getID,getName;
				methods.add(met);
			}
		}
		/* 2.组装sql ： 目标:insert into TABLE values(?,?,?...,?) */
		// 该步骤 组装成 "insert into T_TABLE_NAME values(?,?,...?," :
		Iterator<Method> iter = methods.iterator();
		while (iter.hasNext()) {
			iter.next();
			sql.append("?, ");
		}
		// 该步骤 去除最后的“，”
		// 生成目标sql insert into t_student values(?, ?, ?, ?, ?)
		String targetSQL = sql.substring(0, sql.lastIndexOf(",")).trim() + ")";
		
		//传递参数
		try {
			int i = 0;
			//获得预编译对象的引用
			PreparedStatement statement = conn.prepareStatement(targetSQL);
			//获取计数器
			iter = methods.iterator();
			while(iter.hasNext()){
				Method met = iter.next();
				System.out.println(met.invoke(obj));
				statement.setObject(++i, met.invoke(obj));
			}
			
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*生成的sql语句*/
		System.out.println(targetSQL);

	}

	@Override
	public void update(T obj) {

	}

	@Override
	public void delete(T obj) {

	}

	@Override
	public T findById(Object object) {
		return null;
	}

}
