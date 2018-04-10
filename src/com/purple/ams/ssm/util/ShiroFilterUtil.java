package com.purple.ams.ssm.util;

import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName: ShiroFilterUtil 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:54:56
 */
public class ShiroFilterUtil {

    /**
     * 判断ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString()));
    }
}
