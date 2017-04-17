package com.uws.common.service;

import java.util.List;

import com.uws.core.base.IBaseService;
import com.uws.domain.common.CommonApproveComments;

/**
 * 
* @ClassName: IProjectService 
* @Description: 审核 Service 接口
* @author IvanHsu
* @date 2015-4-16 下午2:09:51 
*
 */
public interface ICommonApproveService extends IBaseService
{

	/**
	 * 
	 * @Title: saveApproveComments
	 * @Description: 保存审核意见
	 * @param approveComments
	 * @throws
	 */
	public void saveApproveComments(CommonApproveComments approveComments);
	
	
	/**
	 * 
	 * @Title: queryCommentsList
	 * @Description:项目所有审核意见
	 * @param projectId
	 * @return
	 * @throws
	 */
	public List<CommonApproveComments> queryCommentsList(String projectId);
	
	
	
}
