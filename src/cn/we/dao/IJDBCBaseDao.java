package cn.we.dao;

public interface IJDBCBaseDao<T> {
	public void save(T obj);
	public void update(T obj);
	public void delete(T obj);
	public T findById(Object object);
}
