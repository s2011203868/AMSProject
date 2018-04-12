package com.purple.ams.ssm.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import com.purple.ams.ssm.constant.ReturnJsonConstantCollection;
import com.purple.ams.ssm.util.ExecuteResult;

public class SessionExpiredFilter extends PathMatchingFilter{

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {

        if(!((HttpServletRequest) request).getRequestURI().endsWith("login.action")){
            if(!SecurityUtils.getSubject().isAuthenticated()){
                if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.setContentType("application/json; charset=utf-8");  
                    httpResponse.setCharacterEncoding("UTF-8");  
                    httpResponse.getWriter().write(ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS,ReturnJsonConstantCollection.RETURN_JSON_MESSAGE_SESSIONtIMEOUT));
                    return false;
                }else{
                     return true;   
                }
            } 
        }
        return true;
    }
}
