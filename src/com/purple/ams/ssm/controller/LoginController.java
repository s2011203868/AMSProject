package com.purple.ams.ssm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement.Else;
import com.purple.ams.ssm.constant.ReturnJsonConstantCollection;
import com.purple.ams.ssm.exception.AMSException;
import com.purple.ams.ssm.util.ExecuteResult;
/**
 * @ClassName: LoginController 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:49:32
 */
@Controller
public class LoginController {

	
	
	@RequestMapping("login")
	public String login(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
		if(shiroLoginFailure!=null){
			if(shiroLoginFailure.equals(UnknownAccountException.class.getName())){
				throw new AMSException(ReturnJsonConstantCollection.RETURN_JSON_MESSAGE_UNKNOWNACCOUNT);
			}else if (shiroLoginFailure.equals(IncorrectCredentialsException.class.getName())) {
			    throw new AMSException(ReturnJsonConstantCollection.RETURN_JSON_MESSAGE_INCORRECTCREDENTIALS);
			}else if(shiroLoginFailure.equals(ExcessiveAttemptsException.class.getName())){
			    throw new AMSException(ReturnJsonConstantCollection.RETURN_JSON_MESSAGE_EXCESSIVEATTEMPTSEXCEPTION);
			}else {
				throw new Exception();
			}
		}
		//登陆失败
		return "login";
	}
	
}
