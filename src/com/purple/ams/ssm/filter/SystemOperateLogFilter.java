package com.purple.ams.ssm.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.purple.ams.ssm.constant.OtherConstants;
import com.purple.ams.ssm.pojo.ActiveUser;
import com.purple.ams.ssm.service.SysUserService;
import com.purple.ams.ssm.util.FunctionUtil;
import com.purple.ams.ssm.util.MacAddressApi;
/**
 * @ClassName: SystemOperateLog 
 * @Description: 记录操作日志
 * @author: 于庆辉
 * @date: 2018年3月24日 下午2:26:31
 */
public class SystemOperateLogFilter implements Filter {

	SysUserService sysUserService;
	
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
		String url = request.getRequestURI();
		long beginTime = 0 ;
		
		
		if(url.endsWith(".action")){
			beginTime = System.currentTimeMillis();
		}
		chain.doFilter(request, response);
		
		if(url.endsWith(".action")){
			String account = "";
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			ActiveUser activeUser= (ActiveUser) session.getAttribute("activeUser");
			if(activeUser!=null){
				account = activeUser.getAccount();
			}else{
				account = request.getParameter("username") == "" || request.getParameter("username") ==null ? "未知":request.getParameter("username");
			}
			long endTime = System.currentTimeMillis();
			long time = endTime - beginTime;
			String usetime = time+"ms";
			String realIp = MacAddressApi.getIpAddr(request);
			String executeTime = FunctionUtil.dateToStr(new Date());
			String description = "未设置操作描述";
			url = url.replace(OtherConstants.PROJECT_NAME, "");
			sysUserService.addExecuteRecord(description,url,usetime,realIp,account,executeTime);
		}
	}
}
