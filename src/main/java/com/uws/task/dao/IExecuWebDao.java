package com.uws.task.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;

/**
 * 
* @ClassName: IExecuProcedureDao 
* @Description: 调用webservice接口
* @author 联合永道
* @date 2015-9-06 下午3:42:15 
*
 */
public interface IExecuWebDao extends IBaseDao
{
	
	/**
	 * 描述信息: 按照学年查询所有学生信息
	 * @param 按学年查询
	 * @return
	 */
    public List<Object>  queryStudentBySchoolYear(String year);
    
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
	public void updateStudentCostState(String id,String costState);

}
