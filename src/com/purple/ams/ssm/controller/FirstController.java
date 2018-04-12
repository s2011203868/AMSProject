package com.purple.ams.ssm.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.purple.ams.ssm.constant.ReturnJsonConstantCollection;
import com.purple.ams.ssm.pojo.ActiveUser;
import com.purple.ams.ssm.service.SysUserService;
import com.purple.ams.ssm.util.ExecuteResult;
import com.purple.ams.ssm.util.FunctionUtil;
/**
 * @ClassName: FirstController 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:49:24
 */
@Controller
public class FirstController {

	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("first")
	@ResponseBody
	public String first(Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		//获取主体
		Subject currentUser = SecurityUtils.getSubject();
		//获取账户信息
		ActiveUser activeUser = (ActiveUser) currentUser.getPrincipal();
		String nowTime = FunctionUtil.dateToStr(new Date());
		sysUserService.setLastLoginTime(nowTime,activeUser.getUserid());
		//放入shiro管理的session域中
		//request.getSession().setAttribute("activeUser", activeUser);
		Session session = currentUser.getSession();
		session.setAttribute("activeUser", activeUser);
		return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, ReturnJsonConstantCollection.RETURN_JSON_MESSAGE_GOTOFIRSTPAGE);
	}
}
