package com.uws.webservice;

import com.uws.core.base.IBaseService;

/**
 * 
* @ClassName: IBookService 
* @Description: 图书馆接口
* @author 联合永道
* @date 2016-5-20 下午1:29:14 
*
 */
public interface IBookService extends IBaseService
{
	/**
	 * 
	 * @Title: getBookStatus
	 * @Description: 同步图书借阅情况
	 * @param studentId
	 * @return
	 * @throws
	 */
	public String getBookReturnStatus(String studentId,boolean flag);
	
	/**
	 * 
	 * @Title: checkConnect
	 * @Description:检查连接
	 * @throws
	 */
	public void checkConnect();
}
