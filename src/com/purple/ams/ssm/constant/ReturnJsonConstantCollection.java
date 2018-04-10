package com.purple.ams.ssm.constant;
/**
 * @ClassName: ReturnJsonConstantCollection 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:49:06
 */
public class ReturnJsonConstantCollection {

    public static final Integer RETURN_JSON_OKSTATUS = 200;
    public static final Integer RETURN_JSON_ERRORSTATUS = 300;
    public static final Integer RETURN_JSON_TIMEOUTSTATUS = 301;
    
    public static final String RETURN_JSON_MESSAGE_GOTOFIRSTPAGE = "gotofirst";
    public static final String RETURN_JSON_MESSAGE_UNKNOWNACCOUNT = "用户名不存在";
    public static final String RETURN_JSON_MESSAGE_INCORRECTCREDENTIALS = "密码错误";
    public static final String RETURN_JSON_MESSAGE_EXCESSIVEATTEMPTSEXCEPTION = "错误次数过多";
    public static final String RETURN_JSON_MESSAGE_UNAUTHORIZED = "您没有操作权限,请联系管理员";
    public static final String RETURN_JSON_MESSAGE_DISABLEDACCOUNT = "账号被禁用";
    public static final String RETURN_JSON_MESSAGE_UNKNOWNEXCEPTION = "未知错误，请联系管理员";
    
    
}
