package com.uws.common.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uws.common.service.IMessageInfoService;
import com.uws.common.util.MessageConstants;
import com.uws.core.base.BaseController;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.session.SessionFactory;
import com.uws.core.session.SessionUtil;
import com.uws.domain.message.MessageInfo;
import com.uws.log.LoggerFactory;
import com.uws.sys.model.Dic;
import com.uws.sys.model.UploadFileRef;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;
import com.uws.user.model.User;


//信息管理控制类
@Controller
public class MessageInfoController extends BaseController {
	// 日志
    private LoggerFactory log = new LoggerFactory(MessageInfoController.class);
    // sessionUtil工具类
    private SessionUtil sessionUtil = SessionFactory.getSession(MessageConstants.MENUKEY_MESSAGE_MANAGE);
    //数据字典工具类
    private DicUtil dicUtil = DicFactory.getDicUtil();
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    @Autowired
    IMessageInfoService messageInfoService;
    
    /**
     * 信息查询列表页面
     * @param model
     * @param request
     * @param messageInfo
     * @return
     */
    @RequestMapping(value={MessageConstants.MENUKEY_MESSAGE_MANAGE+"/opt-query/listMessageInfo"}) 
    public String listMessageInfo(ModelMap model, HttpServletRequest request, MessageInfo messageInfo) {
    	log.info("信息查询列表  管理页面");
    	Integer pageNo = request.getParameter("pageNo")!=null?Integer.valueOf(request.getParameter("pageNo")):1;
    	Page page = messageInfoService.qeuryMessageInfoPage(Page.DEFAULT_PAGE_SIZE, pageNo, messageInfo, null);
    	Dic dic = dicUtil.getDicInfo("MESSAGE_TYPE", "MESSAGE_ORIENTATION");
    	if(messageInfo!=null && messageInfo.getMessageType()!=null && messageInfo.getMessageType().getId()!=null &&
    			messageInfo.getMessageType().getId().equals(dic.getId())) {
    		messageInfo.setMessageType(dic);
    	}
    	model.addAttribute("page", page);
    	model.addAttribute("messageInfo", messageInfo);
    	//栏目
    	model.addAttribute("listMesaageColumn", dicUtil.getDicInfoList("MESSAGE_COLUMN"));
    	//类型
    	model.addAttribute("listMessageType", dicUtil.getDicInfoList("MESSAGE_TYPE"));
    	//发布状态
    	model.addAttribute("listPostState", dicUtil.getDicInfoList("POST_STATE"));
    	return MessageConstants.MENUKEY_MESSAGE_MANAGE + "/messageManageList";
    }
    /**
     * 进入编辑页面方法
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value={MessageConstants.MENUKEY_MESSAGE_MANAGE+"/opt-add/editMessage",
    		MessageConstants.MENUKEY_MESSAGE_MANAGE+"/opt-edit/editMessage",
    		MessageConstants.MENUKEY_MESSAGE_MANAGE+"/nsm/viewMessage"}) 
    public String editMessage(ModelMap model, HttpServletRequest request, String flag) {
    	String id = request.getParameter("id");
    	MessageInfo messageInfo = new MessageInfo();
    	List<UploadFileRef> listFile = new ArrayList<UploadFileRef>();
    	
    	if(StringUtils.isNotEmpty(id)) { 
    		messageInfo = messageInfoService.getMessageInfoById(id);
    		listFile = messageInfoService.queryFileById(id);
    	}
    	//附件
    	model.addAttribute("listFile", listFile);
    	model.addAttribute("messageInfo", messageInfo);
    	//栏目
    	model.addAttribute("listMesaageColumn", dicUtil.getDicInfoList("MESSAGE_COLUMN"));
    	//类型
    	model.addAttribute("listMessageType", dicUtil.getDicInfoList("MESSAGE_TYPE"));
    	
    	if(flag!=null && flag.equals("view")) {
    		log.info("信息查看页面");
    		return MessageConstants.MENUKEY_MESSAGE_MANAGE + "/viewMessageInfo";
    	}else {
    		log.info("信息编辑页面");
    		
    		return MessageConstants.MENUKEY_MESSAGE_MANAGE + "/editMessage";
    	}
        	
    }
    
    /**
     * 新增 修改信息
     * @param model
     * @param request
     * @param messageInfo
     * @param fileId
     * @return 
     */
    @RequestMapping(value={MessageConstants.MENUKEY_MESSAGE_MANAGE+"/opt-save/saveMessageInfo"}) 
    public String saveMessageInfo(ModelMap model, HttpServletRequest request, MessageInfo messageInfo, String[] fileId) {
    	User creator = new User(sessionUtil.getCurrentUserId());
    	messageInfoService.editMessageInfo(messageInfo, fileId, creator);
    	
    	return "redirect:" + MessageConstants.MENUKEY_MESSAGE_MANAGE + "/opt-query/listMessageInfo.do";
    }
    
    /**
     * 发布信息与批量发布信息
     * @param model
     * @param request
     * @param ids
     * @param isUp
     * @param isRed
     * @return
     */
    @ResponseBody
    @RequestMapping(value={MessageConstants.MENUKEY_MESSAGE_MANAGE+"/nsm/postMessage", 
    		MessageConstants.MENUKEY_MESSAGE_MANAGE+"/nsm/cancelMessage"}) 
    public String postMessage(ModelMap model, HttpServletRequest request, String ids, String isUp, String isRed, String isPost) {
    	Integer n=0;
    	if(ids!=null && !ids.equals(""))
    		for(String id:ids.split(","))
    			if(id!=null && !id.equals("")) {
    				n++;
    				messageInfoService.postMessageInfo(id, isUp, isRed, isPost);
    			}
    	return n.toString();
    }
    /**
     * 删除信息操作
     * @param model
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value={MessageConstants.MENUKEY_MESSAGE_MANAGE+"/opt-del/deleteMessageInfo"}) 
    public String deleteMessageInfo(ModelMap model, HttpServletRequest request, String id) {
    	log.info("删除id号为 "+id+" 的信息");
    	messageInfoService.deleteMessageInfoById(id);
    	return "redirect:" + MessageConstants.MENUKEY_MESSAGE_MANAGE + "/opt-query/listMessageInfo.do";
    }
    /**
     * 信息发布发放
     * @param model
     * @param request
     * @param messageInfo
     * @param fileId
     * @param isUp
     * @param isRed
     * @return
     */
    @RequestMapping(value={MessageConstants.MENUKEY_MESSAGE_MANAGE+"/opt-post/postMessage"})
    public String postMessage(ModelMap model, HttpServletRequest request, MessageInfo messageInfo, 
    		String[] fileId, String isUp, String isRed) {

    	User creator = new User(sessionUtil.getCurrentUserId());
    	messageInfoService.postMessageInfo(messageInfo, fileId, creator);
    	
    	return "redirect:" + MessageConstants.MENUKEY_MESSAGE_MANAGE + "/opt-query/listMessageInfo.do";
    }
    /**
     * 信息查询页面
     * @param model
     * @param request
     * @param messageInfo
     * @return
     */
    @RequestMapping(value={MessageConstants.MENUKEY_MESSAGE_INFO_MANAGE+"/opt-query/listMessageInfo"})
    public String listMessage(ModelMap model, HttpServletRequest request, MessageInfo messageInfo ) {
		
    	log.info("信息查询列表 ");
    	Integer pageNo = request.getParameter("pageNo")!=null?Integer.valueOf(request.getParameter("pageNo")):1;
    		
    	Page page = messageInfoService.qeuryMessageInfoPage(Page.DEFAULT_PAGE_SIZE, pageNo, messageInfo, "post");
    	Dic dic = dicUtil.getDicInfo("MESSAGE_TYPE", "MESSAGE_ORIENTATION");
    	if(messageInfo!=null && messageInfo.getMessageType()!=null && messageInfo.getMessageType().getId()!=null &&
    			messageInfo.getMessageType().getId().equals(dic.getId())) {
    		messageInfo.setMessageType(dic);
    	}
    	model.addAttribute("page", page);
    	model.addAttribute("messageInfo", messageInfo);
    	//栏目
    	model.addAttribute("listMesaageColumn", dicUtil.getDicInfoList("MESSAGE_COLUMN"));
    	//类型
    	model.addAttribute("listMessageType", dicUtil.getDicInfoList("MESSAGE_TYPE"));
    	//发布状态
    	model.addAttribute("listPostState", dicUtil.getDicInfoList("POST_STATE"));
    	
    	
    	return MessageConstants.MENUKEY_MESSAGE_MANAGE + "/messageList";
    }
}
