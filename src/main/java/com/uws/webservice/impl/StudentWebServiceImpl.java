package com.uws.webservice.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.uws.common.dao.ICommonWebServiceDao;
import com.uws.core.base.BaseServiceImpl;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;
//import com.uws.test.Demo;
import com.uws.webservice.IStudentWebService;

/**
 * 
* @ClassName: StudentWebServiceImpl 
* @Description: 学工管理管理系统 接口 实现 
* @author 联合永道
* @date 2015-10-21 上午11:01:34 
* 注释： 所有的查询和逻辑 将定义在 service 中， 没有创建 webservice下的dao包
*
 */
@WebService(serviceName="xgWebService",endpointInterface = "com.uws.webservice.IStudentWebService")
public class StudentWebServiceImpl extends BaseServiceImpl implements IStudentWebService 
{
	private DicUtil dicUtil = DicFactory.getDicUtil();
	@Autowired
	private ICommonWebServiceDao webServiceDao;
	
	/**
	 * 描述信息: 测试例子
	 * @param demo
	 * @return
	 * @see com.uws.webservice.IStudentWebService#sayHi(com.uws.test.Demo)
	 */
//	public String sayHi(Demo demo)
//	{
//		return "Hello " + demo.getName();
//	}

	
	/**
	 * 描述信息: 待办的数量 -- 审批流程中 
	 * @param numberId
	 * @return
	 * @see com.uws.webservice.IStudentWebService#queryPendingItemsCount(java.lang.String)
	 */
	@Override
    public int queryPendingItemsCount(String numberId)
    {
		return webServiceDao.queryPendingItemsCount(numberId, dicUtil.getDicInfo("Y&N", "Y"), "AVAILABLE");
    }
}
