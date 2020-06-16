package com.horizon.sm.user.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.horizon.sm.user.service.ISysUserManagerService;
import com.horizon.sm.user.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping("sys/user")
public class SysUserController<T> {

	/**
	 * 用于输出log
	 */
	private static Logger log = Logger.getLogger(SysUserController.class);
	/**
	 * 注入service对象
	 */
	@Autowired
	private ISysUserManagerService sysUserService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(User user, HttpServletRequest req, HttpServletResponse response) throws Exception {
		Map<String, String> result = sysUserService.checkUser(user);
		if(result.get("code").equals("0")) {
			req.getSession().setAttribute("userID",result.get("desc"));
			req.getSession().setAttribute("userName",result.get("userName"));
			return "redirect:/";
		}
		req.setAttribute("code",result.get("code"));
		req.setAttribute("desc",result.get("desc"));
		return "errorPage";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Object register(User user, HttpServletRequest req)  {
		try{
			sysUserService.addUser(user);
		} catch(Exception e){
		  log.error(e);
		  return "errorPage";
		}
		return "redirect:/login";
	}
}
