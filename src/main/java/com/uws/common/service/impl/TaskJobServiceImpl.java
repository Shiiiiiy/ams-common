package com.uws.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.ITaskJobDao;
import com.uws.common.service.ITaskJobService;
import com.uws.core.base.BaseServiceImpl;

/**
 * 
* @ClassName: CommonServiceImpl 
* @Description:  系统管理的扩展service接口 实现
* @author 联合永道
* @date 2015-7-13 上午11:45:38 
*
 */
@Service("com.uws.common.service.impl.TaskJobServiceImpl")
public class TaskJobServiceImpl extends BaseServiceImpl implements ITaskJobService
{
	@Autowired
	private ITaskJobDao taskJobDao;

}
