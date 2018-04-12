package com.purple.ams.ssm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.purple.ams.ssm.service.SysUserService;
/**
 * 
 * @ClassName: SystemLicenseFilter 
 * @Description: TODO系统授权使用
 * @author: PurpleSoft@一禅
 * @date: 2018年3月23日 下午3:47:12
 */
public class SystemLicenseFilter implements Filter {

	private SysUserService sysUserService;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext sc = fConfig.getServletContext(); 
        XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
        
        if(cxt != null && cxt.getBean("sysUserService") != null && sysUserService == null){
        	sysUserService = (SysUserService) cxt.getBean("sysUserService");
        }
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		
	}
}
