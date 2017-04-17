package com.uws.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.uws.core.base.IBaseService;
//import com.uws.test.Demo;

/**
 * 
* @ClassName: IStudentWebService 
* @Description:  学工系统的 WebService 接口 
* @author 联合永道
* @date 2015-10-21 上午10:59:44 
*
 */
@WebService
public interface IStudentWebService extends IBaseService
{
	/**
	 * 
	 * @Title: sayHi
	 * @Description: 测试例子
	 * @param demo
	 * @return
	 * @throws
	 */
//	@WebMethod
//	public String sayHi(@WebParam(name = "demo") Demo demo);
	
	/**
	 * 
	 * @Title: queryPendingItemsCount
	 * @Description: 待办的数量 -- 审批流程中 
	 * @param numberId
	 * @return
	 * @throws
	 */
	@WebMethod
	public int queryPendingItemsCount(@WebParam(name = "numberId") String numberId);
	
}
