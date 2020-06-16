package com.horizon.common.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.horizon.common.base.GenericVO;


public interface ISDAO<T> {
	
	/**
	 * <p>
	 * Discription: 批量执行方法(数组入参)
	 * </p>
	 */
	int[] batchUpdate(String[] sql);

	/**
	 * <p>
	 * Discription: 不带参数,返回SqlRowSet.SqlRowSet是对JDBC中ResultSet的简单封装
	 * </p>
	 */
	public SqlRowSet queryRowSet(String sql);

	/**
	 * <p>
	 * Discription: 带参数,返回SqlRowSet.SqlRowSet是对JDBC中ResultSet的简单封装
	 * </p>
	 */
	SqlRowSet queryRowSet(String sql, Object[] obj);

	/**
	 * <p>
	 * Discription: 不带参数的update方法
	 * </p>
	 */
	int executeUpdate(String sql);

	/**
	 * <p>
	 * Discription: 带参数的update方法
	 * </p>
	 */
	int executeUpdate(String sql, Object[] obj);

	/**
	 * <p>
	 * Discription:返回整型数据,不带参数
	 * </p>
	 */
	int queryForInt(String sql);

	/**
	 * <p>
	 * Discription:返回整型数据,带参数
	 * </p>
	 */
	int queryForInt(String sql, Object[] obj);

	/**
	 * <p>
	 * Discription:返回Long类型数据,不带参数
	 * </p>
	 */
	long queryForLong(String sql);

	/**
	 * <p>
	 * Discription:返回Long类型数据,带参数
	 * </p>
	 */
	long queryForLong(String sql, Object[] obj);

	/**
	 * <p>
	 * Discription:取回结果,封装成List
	 * </p>
	 */
	List<?> queryForList(String sql, Object[] obj);

	/**
	 * 
	 * <P>
	 * Function: findByPK
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public Object findByPK(Object instance, String sqlParam,
			Class<?> mappedClass);

	/**
	 * 
	 * <P>
	 * Function: findByPK
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public Object findByPK(Object instance, String sqlParam);

	/**
	 * 
	 * <P>
	 * Function: findByVO
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public List<?> findByVO(Object instance, String sqlParam);

	/**
	 * 
	 * <P>
	 * Function: findByVO
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public List<?> findByVO(Object instance, String sqlParam,
			Class<?> mappedClass);

	/**
	 * 
	 * <P>
	 * Function: findByVO4ParsedSql
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */

	public List<?> findByVO4ParsedSql(Object instance, String sqlParam);

	/**
	 * 
	 * <P>
	 * Function: findByVO4ParsedSql
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime: 2012-09-17
	 */
	public List<?> findByVO4ParsedSql(Object instance, String sqlParam,
			Class<?> mappedClass);
	/**
	 * 返回字符串对象
	 * 
	 * @param sql
	 * @param map
	 * @return
	 */
	public String findString(String sql, Map<String, ?> map);


	/**
	 * 此方法只适用于Mysql数据库，保存后返回主键ID
	 * <P> 
	 * Function: save
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime: 2012-10-16
	 */
	public Number save(GenericVO instance, String tableName, String KeyColumn);
	
	/**
	 * 
	 * <P>
	 * Function: save
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public void save(Object instance, String tableName);

	/**
	 * 
	 * <P>
	 * Function: save
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public int save(String sql, Object instance);

	/**
	 * 
	 * <P>
	 * Function: updateBatch
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public int[] saveBatch(String sql, List<?> list);
	
	/**
	 * 
	 * <P>
	 * Function: update
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public int update(Object instance, String sqlParam);

	/**
	 * 
	 * <P>
	 * Function: updateBatch
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public int[] updateBatch(Object instance, String sqlParam, List<?> list);

	/**
	 * 
	 * <P>
	 * Function: updateBatch
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	@SuppressWarnings("rawtypes")
	public int[] updateBatch4Map(Object instance, String sqlParam,
			List<Map> list);

	/**
	 * 
	 * <P>
	 * Function: updateBatch
	 * <P>
	 * Description:
	 * <P>
	 * Others:
	 * 
	 * @CreateTime:
	 */
	public int[] updateBatch(String sqlParam, List<?> list);

}
