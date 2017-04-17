package com.uws.common.dao;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.message.MessageInfo;

public interface IMessageInfoDao extends IBaseDao {
	public Page qeuryMessageInfoPage(int pageSize, int pageNo, MessageInfo messageInfo, String flag);
}
