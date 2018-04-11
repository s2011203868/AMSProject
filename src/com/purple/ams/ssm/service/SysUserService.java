package com.purple.ams.ssm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.executor.ErrorContext;

import com.purple.ams.ssm.pojo.ExceptionRecord;
import com.purple.ams.ssm.pojo.SysDept;
import com.purple.ams.ssm.pojo.SysPermission;
import com.purple.ams.ssm.pojo.SysRole;
import com.purple.ams.ssm.pojo.SysUser;
/**
 * 
 * @ClassName: SysUserService 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:52:19
 */
public interface SysUserService {
	
	
	int findhasMacheineCode(String dESMac);
	
	int saveMacheineCode(String dESMac);
	
	

	SysUser findUserByAccount(String account) throws Exception;

	List<SysPermission> findUserMenusByAccount(String account)throws Exception;

	List<SysRole> finUserRolesByAccount(String account)throws Exception;

	String findLeftMenu(String account, String menuid)throws Exception;

	String getRoleList()throws Exception;

	int rolelistAddSave(String roleid,String rolename, String status,String duty)throws Exception;

	int rolelisteditSave(String roleid, String rolename, String status)throws Exception;

	String getRoleDistributeList(String pageSize, String pageCurrent)throws Exception;

	List<SysRole> finAllRoles()throws Exception;

	int deleteOldRoleByAccount(String account)throws Exception;

	int saveUserRole(String uuid, String userid, String ro)throws Exception;

	String getSysUserList(String pageSize, String pageCurrent)throws Exception;

	int setLastLoginTime(String nowTime,String userid)throws Exception;

	int changePassword(String changedPassword, String userid)throws Exception;

	int checkAccount(String account)throws Exception;

	int createSysUser(String userid,String account, String md5Password, String username, String nowTime, String salt,
			String status)throws Exception;

	int EditUserStatus(String userid, String status)throws Exception;

	void addExceptionInfo(String uri, String account, String realIp, String executeTime, String exceptionType,
			String exceptionCause);

	String findSystemExceptionRecord(String pageSize, String pageCurrent) throws Exception;

	List<ExceptionRecord> querySystemExceptionRecord(String pageSize, String pageCurrent) throws Exception;

	void addExecuteRecord(String description, String url, String usetime, String realIp, String account,
			String executeTime);

	String findSystemExecuteRecord(String pageSize, String pageCurrent)throws Exception;

    //添加角色-权限之间关系  
    int correlationPermissions(String roleId, String permissionid)throws Exception;  

	String findPermissionList()throws Exception;

	int permissionEidtSave(String id, int status)throws Exception;

	String findRoleList()throws Exception;

	String findIsPermissionAuthorization(String roleid)throws Exception;

	int removePermissionForRole(String roleid, String permissionid)throws Exception;

	List<SysPermission> findPermissionsByUser(String userid)throws Exception;

	int deleteRolePermission(String id)throws Exception;

    int deleteUserRoleByRoleid(String roleid)throws Exception;

    String findOnlineUser()throws Exception;

	String findDeptList()throws Exception;
	
    String findAllDept() throws Exception;

    int addDept(SysDept sysDept)throws Exception;

    int editDept(SysDept sysDept)throws Exception;

    String getPermissionTree()throws Exception;

    int initPermissionForRole(String id,String roleid,String permission)throws Exception;

    String findDeptIsPermissionAuthorization(String deptid)throws Exception;

    Map<String, String> findAllDeptDuty() throws Exception;

    int addDeptForUser(String id, String userid, String deptid)throws Exception;

    String findDeptDutyByUserid(String userid)throws Exception;

    int deleteOldDeptByUserid(String userid)throws Exception;

    int updateRoleStatus(String roleid, String status) throws Exception;

    int deleteOldUserByDeptId(String id)throws Exception;

    int finddeptStatusByRoleid(String roleid)throws Exception;

}
