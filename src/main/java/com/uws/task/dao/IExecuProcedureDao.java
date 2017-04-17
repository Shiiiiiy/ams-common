package com.uws.task.dao;

import com.uws.core.hibernate.dao.IBaseDao;

/**
 * 
* @ClassName: IExecuProcedureDao 
* @Description: 调用存储过程 
* @author 联合永道
* @date 2015-7-29 下午2:42:15 
*
 */
public interface IExecuProcedureDao extends IBaseDao
{
	/**
	 * 
	 * @Title: execProcedure
	 * @Description: 无参数调用
	 * @param procedureName
	 * @throws
	 */
	public void execProcedure(String procedureName);
	
	/**
	 * 
	 * @Title: execProcedure
	 * @Description:带参数调用执行存储过程
	 * @param procedureName
	 * @param params
	 * @throws
	 */
	public void execProcedure(String procedureName,Object[] params);
	
	/**
	 * 
	 * @Title: getStudentReportProcedureResult
	 * @Description: 学生报到进度的统计
	 * @param yearDicId
	 * @param queryType
	 * @param queryObjectId
	 * @return
	 * @throws
	 */
	public String getStudentReportProcedureResult(String yearDicId,String queryType,String queryObjectId);

}
