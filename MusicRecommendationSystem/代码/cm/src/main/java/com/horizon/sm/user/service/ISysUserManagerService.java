package com.horizon.sm.user.service;

import com.horizon.sm.user.vo.User;

import java.util.List;
import java.util.Map;


/**
 * 
 * Title:<br>
 * Description: 用户管理模块服务层接口<br>
 * Date: 2012-10-20 <br>
 * Copyright (c) 2012 <br>
 * 
 */
public interface ISysUserManagerService {
    /**
     * 获取访问地址
     * @return List<String>
     */
	public List<String> getAllUrl();

	public Map<String, String> checkUser(User user);

	public void addUser(User user);

}
