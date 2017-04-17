package com.uws.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.ICommonApproveCommentsDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.domain.common.CommonApproveComments;

/**
* @ClassName: ProjectApproveCommentsDaoImpl 
* @Description: 审批意见处理DAO接口 实现 
* @author IvanHsu
* @date 2015-4-22 下午4:19:49 
*
 */
@Repository("com.uws.common.dao.impl.CommonApproveCommentsDaoImpl")
public class CommonApproveCommentsDaoImpl extends BaseDaoImpl implements ICommonApproveCommentsDao
{

	/**
	 * 描述信息: 审核历史 
	 * @param objectId
	 * @return
	 * @see com.uws.ICommonApproveCommentsDao.dao.IProjectApproveCommentsDao#queryCommentsList(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<CommonApproveComments> queryCommentsList(String objectId)
    {
	    return this.query(" from CommonApproveComments where objectId = ? order by approveTime desc ", new Object[]{objectId});
    }
	
}
