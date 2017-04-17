package com.uws.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.ICommonSponsorDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.domain.sponsor.DifficultStudentInfo;
@Repository("com.uws.common.dao.impl.CommonSponsorDaoImpl")
public class CommonSponsorDaoImpl extends BaseDaoImpl implements ICommonSponsorDao
{
    /**
     * 
    * @Description:根据学号查询所有的困难生
    * @author LiuChen  
    * @date 2015-12-3 下午6:35:29
     */
	@SuppressWarnings("unchecked")
    @Override
    public List<DifficultStudentInfo> queryDifficultStudentList(String stuNumber)
    {
		return this.query("from DifficultStudentInfo d where 1=1 and d.processStatus=? and d.student.id=? and d.delStatus.code=?", new Object[]{"PASS",stuNumber,"NORMAL"});
    }

}
