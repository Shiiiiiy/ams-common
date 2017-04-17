package com.uws.common.dao;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.sys.model.Dic;

/**
 * 
* @ClassName: ICommonWebServiceDao 
* @Description:  webservice 调用接口 
* @author 联合永道
* @date 2015-10-21 上午11:17:44 
*
 */
public interface ICommonWebServiceDao extends IBaseDao
{
	/**
	 * 
	 * @Title: queryPendingItemsCount
	 * @Description: 待办的数量 -- 审批流程中
	 * @param numberId
	 * @param isValid
	 * @param approveToken
	 * @return
	 * @throws
	 */
	public int queryPendingItemsCount(String numberId,Dic isValid,String approveToken);
}
