package com.purple.ams.ssm.util;

import com.purple.ams.ssm.pojo.SysPermission;
/**
 * @ClassName: ExecuteResult 
 * @Description: 请求响应状态类
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:48:49
 */
public class ExecuteResult {
	
	
	
	public static String jsonReturn(int statusCode,String message){
		
		return "{\"statusCode\":\""+statusCode+"\",\"message\":\""+message+"\"}";
	}
	
	public static String leftMenuJson (SysPermission permission){
		
		String json = "{\"id\":\""+permission.getId()+"\",\"name\":\""+permission.getName()+"\",\"target\":\""
				+ ""+permission.getTarget()+"\",\"url\":\""+permission.getUrl()+"\"}";
		
		return json;
		
	}
	
}
