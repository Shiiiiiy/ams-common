package com.uws.common.service;

import java.util.List;

import com.uws.core.base.IBaseService;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.message.MessageInfo;
import com.uws.sys.model.UploadFileRef;
import com.uws.user.model.User;

public interface IMessageInfoService extends IBaseService {
	
	/**
	 * 信息分页查询方法
	 * @param pageSize
	 * @param pageNo
	 * @param messageInfo
	 * @return Page
	 */
	public Page qeuryMessageInfoPage(int pageSize, int pageNo, MessageInfo messageInfo, String flag);
	/**
	 * 通过id查找信息实体类
	 * @param id
	 * @return MessageInfo
	 */
	public MessageInfo getMessageInfoById(String id);
	/**
	 * 保存或更新信息，以及附件
	 * @param messageInfo
	 */
	public void editMessageInfo(MessageInfo messageInfo, String[] fileId, User creator);
	
	/**
	 * 通过信息id查找附件信息
	 * @param id
	 * @return List<UploadFileRef>
	 */
	public List<UploadFileRef> queryFileById(String id);
	
	/**
	 * 更新实体类
	 * @param messageInfo
	 */
	public void postMessageInfo(String id, String isUp, String isRed, String isPost);
	/**
	 * 通过id删除对象
	 * @param id
	 */
	public void deleteMessageInfoById(String id);
	/**
	 * 编辑页面 点击发布按钮调用的方法
	 * @param messageInfo
	 * @param fileId
	 * @param creator
	 * @param isUp
	 * @param isRed
	 */
	public void postMessageInfo(MessageInfo messageInfo, String[] fileId,
			User creator);
}
