package com.uws.task.service;

import com.uws.core.base.IBaseService;

/**
 * 
* @ClassName: IExecuProcedureService 
* @Description: 调用webservice接口
* @author 联合永道
* @date 2015-7-29 下午2:40:41 
*
 */

public interface IExecuWebService extends IBaseService{
	
	/**
	 * 描述信息：同步财务的是否缴清费用
	 * @param procedureName
	 * @see com.uws.task.service.IExecuProcedureService#execProcedure(java.lang.String)
	 */
    public void execCont(String year);
    

}
