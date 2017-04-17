package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.uws.common.dao.IMessageInfoDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.HqlEscapeUtil;
import com.uws.domain.message.MessageInfo;

@Repository("com.uws.common.dao.impl.MessageInfoDaoImpl")
public class MessageInfoDaoImpl extends BaseDaoImpl implements IMessageInfoDao {

	@Override
	public Page qeuryMessageInfoPage(int pageSize, int pageNo, MessageInfo messageInfo, String flag) {
		List<Object> values = new ArrayList<Object>();
        StringBuffer hql = new StringBuffer(" from MessageInfo t where 1=1 ");
        if( null != messageInfo ) {
            
            if (StringUtils.isNotBlank(messageInfo.getTitle())) {
                hql.append(" and t.title like ? ");
                if (HqlEscapeUtil.IsNeedEscape(messageInfo.getTitle())) {
                    values.add("%" + HqlEscapeUtil.escape(messageInfo.getTitle().trim()) + "%");
                    hql.append(HqlEscapeUtil.HQL_ESCAPE);
                } else
                    values.add("%" + messageInfo.getTitle().trim() + "%");
            }
            if (messageInfo.getMessageColumn()!=null && messageInfo.getMessageColumn().getId()!=null && !messageInfo.getMessageColumn().getId().equals("")) {
                hql.append(" and  t.messageColumn.id = ? ");
                values.add(messageInfo.getMessageColumn().getId());
            }
            if (messageInfo.getMessageType()!=null && messageInfo.getMessageType().getId()!=null && !messageInfo.getMessageType().getId().equals("")) {
                hql.append(" and  t.messageType.id = ? ");
                values.add(messageInfo.getMessageType().getId());
            }
            if (messageInfo.getPostState()!=null && messageInfo.getPostState().getId()!=null && !messageInfo.getPostState().getId().equals("")) {
                hql.append(" and  t.postState.id = ? ");
                values.add(messageInfo.getPostState().getId());
            }
            if (messageInfo.getStartDate()!=null) {
            	hql.append(" and  t.postDate > ? ");
                values.add(messageInfo.getStartDate());
            }
            if (messageInfo.getEndDate()!=null) {
            	hql.append(" and  t.postDate < ? ");
                values.add(messageInfo.getEndDate());
            }
        }
        
        if(flag!=null && flag.equals("post")) {
        	hql.append(" and  t.postState.code = ? ");
            values.add("POSTED");
        }
        
        //排序条件
        hql.append(" order by updateTime desc ");
        
        if (values.size() == 0)
            return this.pagedQuery(hql.toString(), pageNo, pageSize);
        else
            return this.pagedQuery(hql.toString(), pageNo, pageSize, values.toArray());
		
	}
	
}
