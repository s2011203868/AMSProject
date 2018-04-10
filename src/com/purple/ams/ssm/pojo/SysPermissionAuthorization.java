package com.purple.ams.ssm.pojo;

import java.io.Serializable;
/**
 * 
 * @ClassName: SysPermissionAuthorization 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:51:54
 */
public class SysPermissionAuthorization extends SysPermission implements Serializable {

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = 1L;

	private int isAuthorization;

	public int getIsAuthorization() {
		return isAuthorization;
	}

	public void setIsAuthorization(int isAuthorization) {
		this.isAuthorization = isAuthorization;
	}
	
	
}
