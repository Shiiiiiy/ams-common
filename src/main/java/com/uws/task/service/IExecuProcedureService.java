package com.uws.task.service;

import com.uws.core.base.IBaseService;

/**
 * 
* @ClassName: IExecuProcedureService 
* @Description: 存储过程调用接口
* @author 联合永道
* @date 2015-7-29 下午2:40:41 
*
 */
public interface IExecuProcedureService extends IBaseService
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
	 * @Title: execProcedureWithParam
	 * @Description: 带参数调用
	 * @param procedureName
	 * @throws
	 */
	public void execProcedure(String procedureName, Object[] params);
	
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
