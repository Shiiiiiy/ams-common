package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.domain.sponsor.DifficultStudentInfo;

public interface ICommonSponsorDao extends IBaseDao
{
    /**
     * 
    * @Title: ICommonSponsorDao.java 
    * @Package com.uws.common.dao 
    * @Description:根据学号查询所有的困难生
    * @author LiuChen 
    * @date 2015-12-3 下午6:29:06
     */
	public List<DifficultStudentInfo> queryDifficultStudentList(String stuNumber);

}
