package com.uws.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.core.base.BaseServiceImpl;
import com.uws.task.dao.IExecuProcedureDao;
import com.uws.task.service.IExecuProcedureService;

/**
 * 
* @ClassName: ExecuProcedureServiceimpl 
* @Description: 存储过程调用接口 实现
* @author 联合永道
* @date 2015-7-29 下午2:41:18 
*
 */
@Service("com.uws.task.service.impl.ExecuProcedureServiceimpl")
public class ExecuProcedureServiceimpl extends BaseServiceImpl implements IExecuProcedureService
{
	@Autowired
	private IExecuProcedureDao procedureDao;

	/**
	 * 描述信息:无参数调用执行存储过程
	 * @param procedureName
	 * @see com.uws.task.service.IExecuProcedureService#execProcedure(java.lang.String)
	 */
	@Override
    public void execProcedure(String procedureName)
    {
		procedureDao.execProcedure(procedureName);
    }
	
	/**
	 * 描述信息:带参数调用执行存储过程
	 * @param procedureName
	 * @see com.uws.task.service.IExecuProcedureService#execProcedure(java.lang.String)
	 */
	public void execProcedure(String procedureName,Object[] params)
	{
		procedureDao.execProcedure(procedureName, params);
	}
	
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
	@Override
	public String getStudentReportProcedureResult(String yearDicId,String queryType,String queryObjectId)
	{
		return procedureDao.getStudentReportProcedureResult(yearDicId, queryType, queryObjectId);
	}
	
}
