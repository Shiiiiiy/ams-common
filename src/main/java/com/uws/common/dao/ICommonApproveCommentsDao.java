package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.domain.common.CommonApproveComments;

/**
 * 
* @ClassName: IProjectApproveCommentsDao 
* @Description: 审批意见处理DAO接口
* @author IvanHsu
* @date 2015-4-22 下午4:18:59 
*
 */
public interface ICommonApproveCommentsDao extends IBaseDao
{
	/**
	 * 
	 * @Title: queryCommentsList
	 * @Description:项目所有审核历史
	 * @param objectId
	 * @return
	 * @throws
	 */
	public List<CommonApproveComments> queryCommentsList(String objectId);
}
