package com.uws.common.dao;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.user.model.UserRole;

/**
 * 
* @ClassName: ICommonRoleDao 
* @Description: 角色操作扩展 
* @author 联合永道
* @date 2015-9-28 下午2:41:48 
*
 */
public interface ICommonRoleDao extends IBaseDao
{
	/**
	 * 
	 * @Title: updateUserRole
	 * @Description: 更新角色信息
	 * @param orginUserId
	 * @param updateUserId
	 * @param roleCode
	 * @throws
	 */
	public void updateUserRole(String orginUserId,String updateUserId,String roleCode);
	
	/**
	 * 
	 * @Title: saveUserRole
	 * @Description: 保存角色信息
	 * @param userId
	 * @param roleCode
	 * @throws
	 */
	public void saveUserRole(String userId,String roleCode);
	
	/**
	 * 
	 * @Title: saveUserRole
	 * @Description: 【物理删除】 删除角色信息
	 * @param userId
	 * @param roleCode
	 * @throws
	 */
	public void deleteUserRole(String userId,String roleCode);
	
	/**
	 * 
	 * @Title: checkUserIsExist
	 * @Description: 判断角色定义是否存在
	 * @param userId
	 * @param roleCode
	 * @return
	 * @throws
	 */
	public boolean checkUserIsExist(String userId,String roleCode);

	public UserRole getRoleByCode(String userId, String roleCode);
	
}
