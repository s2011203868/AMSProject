package com.purple.ams.ssm.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.purple.ams.ssm.pojo.SysUser;
import com.purple.ams.ssm.service.SysUserService;
/**
 * 
 * @ClassName: AMSFormAuthenticationFilter 
 * @Description: 自定义验证码校验及账号校验过滤器
 * @author: PurpleSoft@一禅
 * @date: 2018年3月16日 上午11:25:52
 */
public class AMSFormAuthenticationFilter extends FormAuthenticationFilter {
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		
		// TODO Auto-generated method stub
		return super.onAccessDenied(request, response);
	}

}
