package com.horizon.sm.user.dao;

import java.util.List;

import com.horizon.common.dao.ISDAO;

public interface ISysUserDAO extends ISDAO<Object>{

	public List<?> queryList(String sql ,Class<?> elementType);
	/**
	 * 获取所有路径
	 * @return List<String>
	 */
	public List<String> getAllUrl();
}
