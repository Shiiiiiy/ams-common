package com.uws.common.service;

import com.uws.core.base.IBaseService;
import com.uws.user.model.UserRole;

/**
 * 
* @ClassName: ICommonRoleService 
* @Description: 
* @author 联合永道
* @date 2015-9-28 下午2:26:13 
*
 */
public interface ICommonRoleService extends IBaseService
{
	/**
	 * 
	 * @Title: checkUserIsExist
	 * @Description: 判断用户角色是否存在
	 * @param userId
	 * @param roleCode
	 * @return
	 * @throws
	 */
	public boolean checkUserIsExist(String userId,String roleCode);
	
	/**
	 * 
	 * @Title: saveUserRole
	 * @Description: 保存用户角色
	 * @param userId
	 * @param roleCode
	 * @throws
	 */
	public void saveUserRole(String userId,String roleCode);
	
	
	public UserRole getRoleByCode(String userId,String roleCode);
}
