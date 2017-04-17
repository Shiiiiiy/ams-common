package com.uws.common.service.impl;

import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.ICommonRoleDao;
import com.uws.common.service.ICommonRoleService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.user.model.Role;
import com.uws.user.model.UserRole;

/**
 * 
* @ClassName: CommonRoleServiceImpl 
* @Description: 角色信息维护的扩展接口 
* @author 联合永道
* @date 2015-9-28 下午2:27:53 
*
 */
@Service("com.uws.common.service.impl.CommonRoleServiceImpl")
public class CommonRoleServiceImpl extends BaseServiceImpl implements ICommonRoleService
{

	@Autowired
	private ICommonRoleDao commonRoleDao;
	
	@Override
	public boolean checkUserIsExist(String userId, String roleCode) {
		return this.commonRoleDao.checkUserIsExist(userId,roleCode);
	}

	/**
	 * 描述信息:保存用户角色
	 * @param userId
	 * @param roleCode
	 * 2016-2-2 上午10:58:33
	 */
	@Override
    public void saveUserRole(String userId, String roleCode)
    {
		if(!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(roleCode))
			commonRoleDao.saveUserRole(userId, roleCode);
    }

	@Override
    public UserRole getRoleByCode(String userId,String roleCode)
    {
		//UserRole userRole = new UserRole();
		//Role role = userRole.getRole();
	    return commonRoleDao.getRoleByCode(userId,roleCode);
    }
	
}
