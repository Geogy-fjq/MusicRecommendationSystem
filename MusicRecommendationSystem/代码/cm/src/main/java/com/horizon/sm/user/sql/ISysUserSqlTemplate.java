package com.horizon.sm.user.sql;

/**
 * 
 * Title:<br>
 * Description: 用户管理模块存放SQL语句的接口<br>
 * Date: 2012-10-20 <br>
 * Copyright (c) 2012 <br>
 * 
 * @author 
 */
public interface ISysUserSqlTemplate {

	static final String URL_INFO ="SELECT MENU_URL FROM TB_HORIZON_JURISDICTION_INFO " ;

	static final String QUERY_USER ="SELECT * FROM TB_SYS_USER_INFO U WHERE U.USER_NAME =:userName";

	static final String INSERT_USER ="INSERT INTO TB_SYS_USER_INFO (USER_NAME, USER_PWD) VALUES(:userName, :userPwd)";
}
