package com.purple.ams.ssm.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.purple.ams.ssm.exception.AMSException;
import com.purple.ams.ssm.pojo.ActiveUser;
import com.purple.ams.ssm.pojo.SysPermission;
import com.purple.ams.ssm.pojo.SysRole;
import com.purple.ams.ssm.pojo.SysUser;
import com.purple.ams.ssm.service.SysUserService;
/**
 * 
 * @ClassName: AMSRealm 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:52:30
 */
public class AMSRealm extends AuthorizingRealm {

	@Autowired
	private SysUserService sysUserService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		
			//先从Token中获取用户名
		    String account = (String) token.getPrincipal();
		    
		    SysUser sysUser;
			ActiveUser activeUser = new ActiveUser();
			List<SysPermission> menuList;
			List<SysRole> roles;
			String password = "";
			String salt = "";
			String roleName = "";
		try {	
			sysUser= sysUserService.findUserByAccount(account);
			
			if(sysUser == null){
				return null;
			}else{
				roles = sysUserService.finUserRolesByAccount(account);
				if(roles!=null){
					for(SysRole role : roles){
						roleName += role.getRolename();
						roleName +="/";
					}
				}
				if(roleName !="" && roleName !=null){
					roleName = roleName.substring(0,roleName.length()-1);
				}else{
					roleName = "暂无角色";
				}
				password = sysUser.getPassword();
				salt = sysUser.getSalt();
				activeUser.setUserid(sysUser.getUserid());
				activeUser.setAccount(sysUser.getAccount());
				activeUser.setPassword(sysUser.getPassword());
				activeUser.setUsername(sysUser.getUsername());
				activeUser.setSalt(sysUser.getSalt());
				activeUser.setRole(roleName);
				activeUser.setStatus(sysUser.getStatus());
				menuList = sysUserService.findUserMenusByAccount(account);
				activeUser.setMenus(menuList);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//查询到返回认证信息SimpleAuthenticationInfo
	    SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password,ByteSource.Util.bytes(salt), this.getName());
	    
	    return simpleAuthenticationInfo;
	}
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
		
		ActiveUser activeUser = (ActiveUser) token.getPrimaryPrincipal();
		List<String> permissions = new ArrayList<String>();
		try {
			List<SysPermission> hasSysPermissions = sysUserService.findPermissionsByUser(activeUser.getUserid());
			if(hasSysPermissions !=null){
				for(SysPermission sysPermission : hasSysPermissions){
					if(sysPermission.getPerCode()!=null && sysPermission.getPerCode() != ""){
						permissions.add(sysPermission.getPerCode());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}

    /** 
     * 清理权限缓存 
     */  
    public void clearCachedAuthorization(){  
        //清空权限缓存  
        super.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());  
    }  
}
