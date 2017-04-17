package com.uws.common.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.IMessageInfoDao;
import com.uws.common.service.IMessageInfoService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.message.MessageInfo;
import com.uws.sys.model.UploadFileRef;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.FileUtil;
import com.uws.sys.service.impl.DicFactory;
import com.uws.sys.service.impl.FileFactory;
import com.uws.user.model.User;

@Service("com.uws.common.service.impl.MessageInfoServiceImpl")
public class MessageInfoServiceImpl extends BaseServiceImpl implements IMessageInfoService {
	@Autowired 
	IMessageInfoDao messageInfoDao;
	
	//附件工具类
    private FileUtil fileUtil = FileFactory.getFileUtil();
    //数据字典工具类
    private DicUtil dicUtil = DicFactory.getDicUtil();
    
    
	@Override
	public Page qeuryMessageInfoPage(int pageSize, int pageNo, MessageInfo messageInfo, String flag) {

		return messageInfoDao.qeuryMessageInfoPage(pageSize, pageNo, messageInfo, flag);
	}

	@Override
	public MessageInfo getMessageInfoById(String id) {
		return (MessageInfo)messageInfoDao.get(MessageInfo.class, id);
	}

	@Override
	public void editMessageInfo(MessageInfo messageInfo, String[] fileId, User creator) {
		if(messageInfo!=null) {
			if(messageInfo.getId()!= null && !messageInfo.getId().equals("")) {
				MessageInfo po = (MessageInfo) messageInfoDao.get(MessageInfo.class, messageInfo.getId());
				//标题
				if(messageInfo.getTitle()!=null)
					po.setTitle(messageInfo.getTitle());
				//内容
				if(messageInfo.getComments()!=null)
					po.setComments(messageInfo.getComments());
				//创建人
				po.setCreator(creator);
				//类型
				if(messageInfo.getMessageType()!=null && messageInfo.getMessageType().getId()!=null &&
						!messageInfo.getMessageType().getId().equals(""))
					po.setMessageType(messageInfo.getMessageType());
				//栏目
				if(messageInfo.getMessageColumn()!=null && messageInfo.getMessageColumn().getId()!=null &&
						!messageInfo.getMessageColumn().getId().equals(""))
					po.setMessageColumn(messageInfo.getMessageColumn());
				else
					po.setMessageColumn(null);
				
				po.setUpdatePerson(creator);
				//设置为未发布状态
				po.setPostState(dicUtil.getDicInfo("POST_STATE", "UNPOST"));
				messageInfoDao.update(po);
			}else {
				messageInfo.setCreator(creator);
				messageInfo.setUpdatePerson(creator);
				//设置为未发布状态
				messageInfo.setPostState(dicUtil.getDicInfo("POST_STATE", "UNPOST"));
				messageInfoDao.save(messageInfo);
			}
			if(ArrayUtils.isEmpty(fileId))
			    fileId=new String[0];
			 	List<UploadFileRef> list = fileUtil.getFileRefsByObjectId(messageInfo.getId());     
			 	for(UploadFileRef ufr:list)
			 		if(!ArrayUtils.contains(fileId, ufr.getUploadFile().getId()))
			 			fileUtil.deleteFormalFile(ufr);
				for(String id:fileId)
					fileUtil.updateFormalFileTempTag(id, messageInfo.getId());
		}
	}

	@Override
	public List<UploadFileRef> queryFileById(String id) {
		return fileUtil.getFileRefsByObjectId(id);
	}

	@Override
	public void postMessageInfo(String id, String isUp, String isRed, String isPost) {
		
		MessageInfo messageInfo = getMessageInfoById(id);
    	if(isUp!=null)
    		messageInfo.setIsUp(isUp);
    	if(isRed!=null)
    		messageInfo.setIsRed(isRed);
    	
    	if(isPost!=null && isPost.equals("yes")) {
//    		if(messageInfo.getPostState()!=null && messageInfo.getPostState().getCode().equals("POSTED"))
    			messageInfo.setPostDate(new Date());
    		messageInfo.setPostState(dicUtil.getDicInfo("POST_STATE", "POSTED"));
    	}
    	if(isPost!=null && isPost.equals("no")) {
    		messageInfo.setPostDate(null);
    		messageInfo.setPostState(dicUtil.getDicInfo("POST_STATE", "UNPOST"));
    	}
		messageInfoDao.update(messageInfo);
	}

	@Override
	public void deleteMessageInfoById(String id) {
		messageInfoDao.deleteById(MessageInfo.class, id);
		
		List<UploadFileRef> uploadfilerefs = fileUtil.getFileRefsByObjectId(id);
			for(UploadFileRef uploadfileref:uploadfilerefs) {
				fileUtil.deleteFormalFile(uploadfileref);
			}
	}

	@Override
	public void postMessageInfo(MessageInfo messageInfo, String[] fileId,
			User creator) {
		if(messageInfo!=null) {
			if(messageInfo.getId()!= null && !messageInfo.getId().equals("")) {
				MessageInfo po = (MessageInfo) messageInfoDao.get(MessageInfo.class, messageInfo.getId());
				//标题
				if(messageInfo.getTitle()!=null)
					po.setTitle(messageInfo.getTitle());
				//内容
				if(messageInfo.getComments()!=null)
					po.setComments(messageInfo.getComments());
				//创建人
				po.setCreator(creator);
				//类型
				if(messageInfo.getMessageType()!=null)
					po.setMessageType(messageInfo.getMessageType());
				//栏目
				if(messageInfo.getMessageColumn()!=null)
					po.setMessageColumn(messageInfo.getMessageColumn());
				else
					po.setMessageColumn(null);
				//设置为发布状态
				po.setPostState(dicUtil.getDicInfo("POST_STATE", "POSTED"));
				//保存发布日期
				po.setPostDate(new Date());
				po.setUpdatePerson(creator);
				if(messageInfo.getIsUp()!=null)
					po.setIsUp(messageInfo.getIsUp());
				if(messageInfo.getIsRed()!=null)
					po.setIsRed(messageInfo.getIsRed());
				
				messageInfoDao.update(po);
			}else {
				messageInfo.setCreator(creator);
				messageInfo.setUpdatePerson(creator);
				
				//设置为发布状态
				messageInfo.setPostState(dicUtil.getDicInfo("POST_STATE", "POSTED"));
				//保存发布日期
				messageInfo.setPostDate(new Date());
				
				messageInfoDao.save(messageInfo);
			}
			if(ArrayUtils.isEmpty(fileId))
			    fileId=new String[0];
			 	List<UploadFileRef> list = fileUtil.getFileRefsByObjectId(messageInfo.getId());     
			 	for(UploadFileRef ufr:list)
			 		if(!ArrayUtils.contains(fileId, ufr.getUploadFile().getId()))
			 			fileUtil.deleteFormalFile(ufr);
				for(String id:fileId)
					fileUtil.updateFormalFileTempTag(id, messageInfo.getId());
		}
		
	}

}
