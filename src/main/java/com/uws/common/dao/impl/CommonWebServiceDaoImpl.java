package com.uws.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.ICommonWebServiceDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.sys.model.Dic;

/**
 * 
* @ClassName: CommonWebServiceDaoImpl 
* @Description: webservice 调用接口 
* @author 联合永道
* @date 2015-10-21 上午11:19:06 
*
 */
@Repository("com.uws.common.dao.impl.CommonWebServiceDaoImpl")
public class CommonWebServiceDaoImpl extends BaseDaoImpl implements ICommonWebServiceDao
{

	/**
	 * 描述信息: 待办的数量 -- 审批流程中
	 * @param numberId
	 * @param isValid
	 * @param approveToken
	 * @return
	 * @see com.uws.common.dao.ICommonWebServiceDao#queryPendingItemsCount(java.lang.String, com.uws.sys.model.Dic, java.lang.String)
	 */
	@Override
	@SuppressWarnings("rawtypes")
    public int queryPendingItemsCount(String numberId, Dic isValid, String approveToken)
    {
	    String hql = " from FlowInstancePo where approver.id = ? and isValid = ? and approveToken = ? ";
	    List list = this.query(hql, new Object[]{numberId,isValid,approveToken});
	    int len = null == list ? 0 : list.size(); 
	    return len;
    }

}
