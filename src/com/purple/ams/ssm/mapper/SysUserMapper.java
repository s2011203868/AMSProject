package com.purple.ams.ssm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.ErrorContext;

import com.purple.ams.ssm.pojo.ActiveUser;
import com.purple.ams.ssm.pojo.ExceptionRecord;
import com.purple.ams.ssm.pojo.ExecuteRecord;
import com.purple.ams.ssm.pojo.RolePermission;
import com.purple.ams.ssm.pojo.SysDept;
import com.purple.ams.ssm.pojo.SysDuty;
import com.purple.ams.ssm.pojo.SysPermission;
import com.purple.ams.ssm.pojo.SysPermissionAuthorization;
import com.purple.ams.ssm.pojo.SysPermissionTree;
import com.purple.ams.ssm.pojo.SysRole;
import com.purple.ams.ssm.pojo.SysUser;
/**
 * 
 * @ClassName: SysUserMapper 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:50:32
 */
public interface SysUserMapper {

	SysUser findByUserAccount(String account)throws Exception;

	List<SysPermission> finUserMenusByAccount(String account)throws Exception;

	List<SysRole> finUserRolesByAccount(String account)throws Exception;

	List<SysPermission> findLeftMenu(@Param(value = "account") String account,
			@Param(value = "menuid") String menuid)throws Exception;

	List<SysDuty> getRoleList()throws Exception;

	int rolelistAddSave(@Param(value="rolename")String rolename,
			@Param(value="status") int status,@Param(value="uuid") String uuid,@Param(value="uri")String uri,
			@Param(value="target")String target,@Param(value="level")String level,@Param(value="parentid")String parentid,
			@Param(value="order")int order,@Param(value="duty")String duty)throws Exception;

	int rolelisteditSave(@Param(value="roleid")String roleid,
			@Param(value="rolename")String rolename,@Param(value="status") int status)throws Exception;

	int getRoleDistributeTotal()throws Exception;

	List<ActiveUser> getRoleDistributeList(@Param(value="begin")int begin,
			@Param(value="pagesize") int pagesize)throws Exception;

	List<SysRole> finAllRoles()throws Exception;

	int deleteOldRoleByAccount(String account)throws Exception;

	int saveUserRole(@Param(value="uuid")String uuid,@Param(value="userid") String userid,
			@Param(value="role")String role)throws Exception;

	int getSysUserTotal()throws Exception;

	List<SysUser> getSysUserList(@Param(value="begin")int begin, @Param(value="pagesize")int pagesize)throws Exception;

	int setLastLoginTime(@Param(value="nowTime")String nowTime,@Param(value="userid") String userid)throws Exception;

	int changePassword(@Param(value="changedPassword")String changedPassword,@Param(value="userid") String userid)throws Exception;

	int checkAccount(String account)throws Exception;

	int createSysUser(@Param(value="userid")String userid,@Param(value="account")String account,@Param(value="md5Password") String md5Password,
			@Param(value="username")String username,@Param(value="nowTime") String nowTime,@Param(value="salt") String salt,
			@Param(value="status")String status)throws Exception;

	int EditUserStatus(@Param(value="userid")String userid,@Param(value="status") String status)throws Exception;

	void addExceptionInfo(@Param(value="uri")String uri,@Param(value="executor") String executor,
			@Param(value="ipaddress")String ipaddress,@Param(value="executetime") String executetime,
			@Param(value="exceptiontype")String exceptiontype,@Param(value="exceptioncause")String exceptioncause);

	List<ExceptionRecord> findSystemExceptionRecord(@Param(value="begin")int begin,@Param(value="pagesize") int pagesize) throws Exception;

	int getExceptionRecordTotal()throws Exception;

	void addExecuteRecordInfo(@Param(value="description")String description,@Param(value="url") String url,@Param(value="usetime") String usetime,
			@Param(value="realIp")String realIp,@Param(value="account") String account,
			@Param(value="executeTime")String executeTime);

	int getExecuteRecordTotal()throws Exception;

	List<ExecuteRecord> findSystemExecuteRecord(@Param(value="begin")int begin,@Param(value="pagesize") int pagesize)throws Exception;

	List<SysPermission> findPermissionList()throws Exception;

	int permissionEditSave(@Param(value="id")String id,@Param(value="status") int status);

	int findRoleCount()throws Exception;

	List<SysPermissionAuthorization> findIsPermissionAuthorization() throws Exception;

	List<RolePermission> findHasPermissionsByRole(String roleid)throws Exception;

	int correlationPermissions(@Param(value="id")String id,@Param(value="roleid")String roleid,@Param(value="permissionid") String permissionid)throws Exception;

	int removePermissionForRole(@Param(value="roleid")String roleid,@Param(value="permissionid") String permissionid)throws Exception;

	List<SysPermission> findPermissionsByUser(String userid)throws Exception;

	int deleteRolePermission(String id)throws Exception;

	int findHasMacheineCode(String dESMac);

	int saveMacheineCode(String dESMac);

    int deleteUserRoleByRoleid(String roleid)throws Exception;

	List<SysDept> findDeptList()throws Exception;

    int addDept(@Param(value="dept")SysDept dept)throws Exception;

    int editDept(@Param(value="dept")SysDept dept) throws Exception;

    List<SysPermissionTree> getPermissionTree() throws Exception;

    int initPermissionForRole(@Param(value="id")String id,@Param(value="roleid")String roleid,
            @Param(value="permission")String permission)throws Exception;

    List<String> findRolesByDeptid(String deptid)throws Exception;

    List<SysDept> getSysDeptByUserid(String userid)throws Exception;

    List<SysUser> findAllDeptDuty()throws Exception;

    int addDeptForUser(@Param(value="id")String id, @Param(value="userid")String userid,@Param(value="deptid") String deptid)throws Exception;

    List<SysUser> findDeptDutyByUserid(String userid)throws Exception;

    int deleteOldDeptByUserId(String userid)throws Exception;
	
}
