package cn.wen.dao.impl;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	/*
	 * 获得带有字符串get的所有方法的对象
	 */
	public List<Method> getMethods(T obj) {
		Method[] method = obj.getClass().getMethods();
		List<Method> methods = new ArrayList<Method>();
		/* 循环获取get方法 */
		for (Method met : method) {
			// /met.getName().indexOf("get") >= 0 获取实体中以get开头的所有方法
			// met.getName().indexOf("getClass")!= 0 去掉getClass（）这个方法；
			if (met.getName().indexOf("get") >= 0
					&& met.getName().indexOf("getClass") != 0) {
				// 得到所有方法的名称 getID,getName;
				methods.add(met);
			}
		}
		return methods;
	}

	/*
	 * 获得带有字段串set的所有方法的对象
	 * */
	public List<Method> setMethods(T obj){
		Method[] method = obj.getClass().getMethods();
		List<Method> methods = new ArrayList<Method>();
		/* 循环获取get方法 */
		for (Method met : method) {
			// /met.setName().indexOf("get") >= 0 获取实体中以get开头的所有方法
			// met.setName().indexOf("getClass")!= 0 去掉getClass（）这个方法；
			if (met.getName().indexOf("set") >= 0) {
				// 得到所有方法的名称 getID,getName;
				methods.add(met);
			}
		}
		return methods;
	}
	
	/* 去除sql中最后的“，” */
	public String removeComma(StringBuffer sql) {
		return sql.substring(0, sql.lastIndexOf(",")).trim();
	}

	
	@Override
	public void save(T obj) {
		/* 1.组装sql ： 目标:insert into TABLE values(?,?,?...,?) */
		// 该步骤 组装成 "insert into T_TABLE_NAME (" :
		sql = new StringBuffer("INSERT INTO t_"
				+ obj.getClass().getSimpleName().toLowerCase() + "("); // obj.getClass().getSimpleName().toLowerCase()
																		// 返回一个小写的类名
		/* 获得get的方法 */
		List<Method> methods = getMethods(obj);

		/* 组装中间的字段名 */
		Iterator<Method> iter = methods.iterator();
		while (iter.hasNext()) {
			Method met = iter.next();
			sql.append(met.getName().substring(3).toLowerCase() + ",");
		}

		String targetSQL = this.removeComma(sql) + ") Values(";
		sql = new StringBuffer(targetSQL);

		/* 2.组装sql ： 目标:insert into TABLE（id,name,...） values(?,?,?...,?) */
		// 该步骤 组装成 "insert into T_TABLE_NAME（id,name,...） values(?,?,...?," :
		iter = methods.iterator();
		while (iter.hasNext()) {
			iter.next();
			sql.append("?, ");
		}
		// 该步骤 去除最后的“，”
		// 生成目标sql insert into t_student values(?, ?, ?, ?, ?)
		targetSQL = this.removeComma(sql) + ")";

		// 传递参数
		try {
			int i = 1;
			// 获得预编译对象的引用
			PreparedStatement statement = conn.prepareStatement(targetSQL);
			// 获取计数器
			iter = methods.iterator();
			while (iter.hasNext()) {
				Method met = iter.next();
				System.out.println("i=" + i + ":" + met.getName() + "   "
						+ met.invoke(obj));
				statement.setObject(i++, met.invoke(obj));
				/*
				 * System.out.println(i+" : "+met.getName() +":"+
				 * met.invoke(obj));
				 */
			}

			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* 生成的sql语句 */
		System.out.println(targetSQL);

	}

	@Override
	public void update(T obj) throws Exception {
		/*
		 * 目标sql
		 * :"UPDATE t_student SET name = ?,MAC = ?,phone_number = ?,stu_no = ?"
		 * + "WHERE id = ?";
		 */
		sql = new StringBuffer("UPDATE t_"
				+ obj.getClass().getSimpleName().toLowerCase() + " SET ");

		/* 获得类中所有get方法 */
		List<Method> methods = this.getMethods(obj);

		/* 组装sql */
		Iterator<Method> iter = methods.iterator();
		while (iter.hasNext()) {
			Method method = iter.next();
			String str = method.getName().substring(3).toLowerCase();
			if (str.equals("id"))
				continue;
			sql.append(str + " = ?,");
		}

		// 该步骤 去除最后的“，”
		// 生成目标sql insert into t_student values(?, ?, ?, ?, ?)
		String targetSQL = this.removeComma(sql) + " WHERE id = ?";

		// 传递参数

		int i = 1;
		// 获得预编译对象的引用
		PreparedStatement statement = conn.prepareStatement(targetSQL);
		// 获取计数器
		iter = methods.iterator();
		Method method = null;
		Method IDMethod = null;

		// 用过id来update 所以 id属性放到最后
		while (iter.hasNext()) {
			method = iter.next();
			String str = method.getName().substring(3).toLowerCase();
			if (str.equals("id")) {
				IDMethod = method; // 记录getId的方法
				continue;
			}
			/*
			 * System.out.println("i:" + i + ":" + method.getName() + ":" +
			 * method.invoke(obj));
			 */
			statement.setObject(i++, method.invoke(obj));
		}

		/*
		 * System.out.println("i:" + i + ":" + IDMethod.getName() + ":" +
		 * IDMethod.invoke(obj));
		 */
		statement.setObject(i, IDMethod.invoke(obj));

		// System.out.println(targetSQL);
		statement.execute();

	}

	@Override
	public void delete(T obj) {
		/*
		 * 目标sql:"DELETE FROM t_student WHERE id = ?";
		 */
		sql = new StringBuffer("DELETE FROM t_"
				+ obj.getClass().getSimpleName().toLowerCase()
				+ " WHERE id = ? ");
		try {
			PreparedStatement statement = conn.prepareStatement(sql.toString());
			// 获取getID方法
			Method method = obj.getClass().getMethod("getID", null);
			// 设置对象
			statement.setObject(1, method.invoke(obj));
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public T findById(T obj) {
		// 目标SQL : "SELECT * FROM T_TABLENAME WHERE id = ?"
		sql = new StringBuffer("SELECT * FROM t_"
				+ obj.getClass().getSimpleName().toLowerCase()
				+ " WHERE id = ? ");

		try {
			PreparedStatement statement = conn.prepareStatement(sql.toString());
			// 获取getID方法
			Method method = obj.getClass().getMethod("getID", null);
			// 设置对象
			statement.setObject(1, method.invoke(obj));
			///获取set方法
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				///创建对象
				T entity = (T) obj.getClass().newInstance();
				//获取调用对象的所有set方法
				List<Method> set_methods = this.setMethods(entity);
				for(Method met:set_methods){
					//相当于调用 met.setName(name); met.getName().substring(3).toLowerCase()作用是返回该方法的方法名  例如setName 则返回 name
					met.invoke(entity, resultSet.getObject(met.getName().substring(3).toLowerCase()));
				//	System.out.println(resultSet.getObject(met.getName().substring(3).toLowerCase()));
				}
				System.out.println(entity.toString());
				return entity;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
