package com.horizon.sm.user.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.horizon.sm.user.sql.ISysUserSqlTemplate;
import com.horizon.sm.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horizon.sm.user.dao.ISysUserDAO;
import com.horizon.sm.user.service.ISysUserManagerService;

@Service("sysUserService")
class SysUserManagerServiceImpl implements ISysUserManagerService {

 

	@Autowired
	private ISysUserDAO sysUserDAO;

	
	public void setSysUserDAO(ISysUserDAO sysUserDAO) {
		this.sysUserDAO = sysUserDAO;
	}
	/**
	 * 获取访问地址
	 */
	public List<String> getAllUrl() {
		return sysUserDAO.getAllUrl();
	}

	public Map<String, String> checkUser(User user){
		Map<String, String> res = new HashMap<String, String>();
		List<?> result = sysUserDAO.findByVO(user, ISysUserSqlTemplate.QUERY_USER, User.class);
		if(null != result && result.size() > 0) {
			User u = (User)result.get(0);
			if(user.getUserPwd().equals(u.getUserPwd())){
				res.put("code","0");//succ
				res.put("desc",u.getId());
				res.put("userName",u.getUserName());//succ
			} else {
				res.put("code","1");
				res.put("desc","password error");
			}
		} else {
			res.put("code","1");
			res.put("desc","userName error");
		}
		return res;
	}

	public void addUser(User user){
		sysUserDAO.save(ISysUserSqlTemplate.INSERT_USER,user);
	}

}
