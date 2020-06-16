package com.horizon.sm.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.horizon.common.base.BaseDao;
import com.horizon.sm.user.dao.ISysUserDAO;
import com.horizon.sm.user.sql.ISysUserSqlTemplate;

@Component("sysUserDAO")
public class SysUserDAOImpl extends BaseDao implements ISysUserDAO {
	
	public List<?> queryList(String sql ,Class<?> elementType){
		return super.getJdbcTemplate().queryForList(sql, elementType);
	}
	/**
	 * 获取访问地址
	 */
	public List<String> getAllUrl() {
		return (List<String>)super.getJdbcTemplate().queryForList(ISysUserSqlTemplate.URL_INFO, String.class);
	}
}
