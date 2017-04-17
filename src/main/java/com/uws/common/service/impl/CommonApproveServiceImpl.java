package com.uws.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.ICommonApproveCommentsDao;
import com.uws.common.service.ICommonApproveService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.domain.common.CommonApproveComments;

/**
 * 
* @ClassName: ProjectServiceImpl 
* @Description: 项目管理 Service 接口实现类
* @author IvanHsu
* @date 2015-4-16 下午2:10:48 
*
 */
@Service("com.uws.common.service.impl.CommonApproveServiceImpl")
public class CommonApproveServiceImpl extends BaseServiceImpl implements ICommonApproveService
{
	@Autowired
	private ICommonApproveCommentsDao commonApproveDao;
	/**
	 * 描述信息: 保存审核意见
	 * @param approveComments
	 * @see com.uws.project.service.IProjectApproveService#saveApproveComments(com.uws.project.model.ProjectApproveComments)
	 */
	@Override
	public void saveApproveComments(CommonApproveComments approveComments)
	{
		if( null!=approveComments )
			commonApproveDao.save(approveComments);
	}

	/**
	 * 描述信息: 项目对应的审核历史
	 * @param projectId
	 * @return
	 */
	@Override
    public List<CommonApproveComments> queryCommentsList(String objectId)
    {
		return commonApproveDao.queryCommentsList(objectId);
    }
	
}
