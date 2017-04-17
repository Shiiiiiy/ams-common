package com.uws.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.ICommonRoleDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.util.StringUtils;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;
import com.uws.user.model.Role;
import com.uws.user.model.User;
import com.uws.user.model.UserRole;

/**
 * 
* @ClassName: CommonRoleDaoImpl 
* @Description: 角色列表扩展
* @author 联合永道
* @date 2015-9-28 下午2:42:37 
*
 */
@Repository("com.uws.common.dao.impl.CommonRoleDaoImpl")
public class CommonRoleDaoImpl extends BaseDaoImpl implements ICommonRoleDao
{
	private DicUtil dicUtil = DicFactory.getDicUtil();
	/**
	 * 描述信息: 更新角色信息
	 * @param orginUserId
	 * @param updateUserId
	 * @param roleCode
	 * @see com.uws.common.dao.ICommonRoleDao#updateUserRole(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
    public void updateUserRole(String orginUserId, String updateUserId, String roleCode)
    {
		/**
		 * 前面新增时没校验，下面修改也有问题，当原评测员 和原评测员 都有 评测员角色时，导致 现评测员 的评测员角色 出现重复
		 * 所有在前面 先直接删掉了园评测员 和 县评测员的 角色信息
		 */
		this.deleteUserRole(orginUserId, roleCode);
		this.deleteUserRole(updateUserId, roleCode);
		
		if(StringUtils.hasText(orginUserId) && StringUtils.hasText(updateUserId) && StringUtils.hasText(roleCode))
		{
			UserRole userRole = (UserRole) this.queryUnique("from UserRole where user.id = ? and role.code = ? ", new Object[]{orginUserId,roleCode});
			if(null == userRole)
			{
				userRole = (UserRole) this.queryUnique("from UserRole where user.id = ? and role.code = ? ", new Object[]{updateUserId,roleCode});
				if(null == userRole)
					saveUserRole(updateUserId,roleCode);
			}
			else
			{
				User user = new User();
				user.setId(updateUserId);
				userRole.setUser(user);
				this.update(userRole);
			}
		}
		
		
		
		
    }

	/**
	 * 描述信息: 保存角色信息
	 * @param userId
	 * @param roleCode
	 * @see com.uws.common.dao.ICommonRoleDao#saveUserRole(java.lang.String, java.lang.String)
	 */
	@Override
    public void saveUserRole(String userId, String roleCode)
    {
		if(StringUtils.hasText(userId) && StringUtils.hasText(roleCode))
		{
			Role role = (Role) this.queryUnique("from Role where code = ? and statusDic = ? ", new Object[]{roleCode ,dicUtil.getStatusEnable()});
			if(null!=role)
			{
				User user = new User();
				user.setId(userId);
				UserRole userRole = new UserRole();
				userRole.setRole(role);
				userRole.setUser(user);
				this.save(userRole);
			}
		}
    }

	/**
	 * 描述信息: 删除角色配置信息
	 * @param userId
	 * @param roleCode
	 * @see com.uws.common.dao.ICommonRoleDao#deleteUserRole(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
    public void deleteUserRole(String userId, String roleCode)
    {
		if(StringUtils.hasText(userId) && StringUtils.hasText(roleCode))
		{
			List<UserRole> userRoleList = this.query("from UserRole where user.id = ? and role.code = ? ", new Object[]{userId,roleCode});
			int len = null == userRoleList ? 0 : userRoleList.size();
			if(len > 0)
			{
				for(UserRole userRole : userRoleList)
					this.delete(userRole);
			}
		}
    }
	
	/**
	 * 描述信息: 判断有没有角色信息
	 * @param userId
	 * @param roleCode
	 * @return
	 * @see com.uws.common.dao.ICommonRoleDao#checkUserIsExist(java.lang.String, java.lang.String)
	 */
    @Override
    @SuppressWarnings("unchecked")
	public boolean checkUserIsExist(String userId,String roleCode)
	{  
		List<UserRole> userRoleList = this.query("from UserRole ur,Role r where ur.role.id=r.id and ur.user.id = ? and r.code = ? ", new Object[]{userId,roleCode});
		int len = null == userRoleList ? 0 : userRoleList.size();
		if(len > 0)
			return true;
		return false;
	}
    
    
    @Override
    public UserRole getRoleByCode(String userId, String roleCode)
    {
    	UserRole userRole = (UserRole) this.queryUnique("select ur from UserRole ur,Role r where ur.role.id=r.id and ur.user.id = ? and r.code = ? ", new Object[]{userId,roleCode});
        return userRole;
    }
	
}
