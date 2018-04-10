package com.purple.ams.ssm.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.purple.ams.ssm.constant.ReturnJsonConstantCollection;
import com.purple.ams.ssm.pojo.ActiveUser;
import com.purple.ams.ssm.service.SysUserService;
import com.purple.ams.ssm.util.ExecuteResult;
import com.purple.ams.ssm.util.FunctionUtil;
import com.purple.ams.ssm.util.MacAddressApi;

/**
 * 
 * @ClassName: AMSExceptionResolver 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:50:02
 */
public class AMSExceptionResolver implements HandlerExceptionResolver {

	@Autowired
	private SysUserService sysUserService;

	//前端控制器DispatcherServlet在进行HandlerMapping、调用HandlerAdapter执行Handler过程中，如果遇到异常就会执行此方法
	//handler最终要执行的Handler，它的真实身份是HandlerMethod
	//Exception ex就是接收到异常信息
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//输出异常
		ex.printStackTrace();
		
		//统一异常处理代码
		//针对系统自定义的AMSException异常，就可以直接从异常类中获取异常信息，将异常处理在错误页面展示
		//异常信息
		String message = null;
		AMSException amsException = null;
		//如果ex是系统 自定义的异常，直接取出异常信息
		if(ex instanceof AMSException){
			amsException = (AMSException)ex;
		}else if(ex instanceof UnauthorizedException){
		    //没有操作权限
			amsException = new AMSException(ReturnJsonConstantCollection.RETURN_JSON_MESSAGE_UNAUTHORIZED);
		}else{
			
			//针对非AMSException异常，对这类重新构造成一个AMSException，异常信息为“未知错误”
			amsException = new AMSException(ReturnJsonConstantCollection.RETURN_JSON_MESSAGE_UNKNOWNEXCEPTION);
		}
		
		String exceptionType = ex.getClass().getName();
		String exceptionCause = ex.getMessage();
		
		if("".equals(exceptionCause) || exceptionCause==null){
			StringWriter sw=new StringWriter();  
	        PrintWriter pw=new PrintWriter(sw);  
	        ex.printStackTrace(pw);
	        String errmessage = sw.toString();
			String regEx = "Caused by:(.*)";  
			Pattern pat = Pattern.compile(regEx);  
			Matcher mat = pat.matcher(errmessage);  
			boolean rs = mat.find();
			if(rs){
			    exceptionCause = mat.group(1);  
			}
		}
		
		
		
		String uri = request.getRequestURI();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		ActiveUser activeUser= (ActiveUser) session.getAttribute("activeUser");
		String account = "";
		if(activeUser!=null){
			account = activeUser.getAccount();
		}else{
			account = request.getParameter("username") == "" || request.getParameter("username") ==null ? "":request.getParameter("username");
		}
		String realIp = MacAddressApi.getIpAddr(request);
		String executeTime = FunctionUtil.dateToStr(new Date());
		
		
		//错误 信息
		message = amsException.getMessage();
		
		try {
			response.setContentType("application/json; charset=utf-8");  
	        response.setCharacterEncoding("UTF-8");  
			response.getWriter().write(ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS,message));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sysUserService.addExceptionInfo(uri,account,realIp,executeTime,exceptionType,exceptionCause);
		
		return new ModelAndView();
	}
}
