package com.uws.task.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uws.common.dao.ITaskJobDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.task.dao.IExecuProcedureDao;
import com.uws.task.dao.IExecuWebDao;
import com.uws.webservice.IFeeServcie;

@Repository("com.uws.task.dao.impl.ExecuWebDaoImpl")
public class ExecuWebDaoImpl extends BaseDaoImpl implements IExecuWebDao
{
	
	/**
	 * 描述信息: 按照学年查询所有学生信息
	 * @param 按学年查询
	 * @return
	 */
	@Override
    public List<Object>  queryStudentBySchoolYear(String year)
    {
		return  (List<Object>) this.queryUnique("from StudentInfoModel s where s.enterYearDic=?", year);
    }
	
	/**
	 * 
	 * @Title: queryQuesInfo
	 * @Description: TODO(根据学生Id，修改缴费状态)
	 * @param id
	 *            学生基本信息记录号
	 * @param 缴费状态
	 *            costState
	 * @return void
	 * @author wangcl
	 */
	@Override
	public void updateStudentCostState(String id,String costState) {
		// TODO Auto-generated method stub
		String hql = "update StudentInfoModel stu set stu.costState = ? where stu.id = ?";
		this.executeHql(hql, new String[] { costState, id });
	}

}
