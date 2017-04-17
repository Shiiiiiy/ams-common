package com.uws.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.ICommonSponsorDao;
import com.uws.common.service.ICommonSponsorService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.domain.sponsor.DifficultStudentInfo;

@Service("com.uws.common.service.impl.CommonSponsorServiceImpl")
public class CommonSponsorServiceImpl extends BaseServiceImpl implements ICommonSponsorService
{
	@Autowired
	private ICommonSponsorDao commonSponsorDao;
    
	/**
	 * 
	* @Description: 根据学号查询所有的困难生
	* @author LiuChen  
	* @date 2015-12-3 下午6:27:50
	 */
	@Override
    public List<DifficultStudentInfo> queryDifficultStudentList(String stuNumber)
    {
	    return this.commonSponsorDao.queryDifficultStudentList(stuNumber);
    }

}
