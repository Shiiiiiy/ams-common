package com.uws.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uws.common.service.ICommonApproveService;
import com.uws.domain.common.CommonApproveComments;

/**
 * 
* @ClassName: CommonApproveCommentController 
* @Description: 审核历史查询
* @author 联合永道
* @date 2015-9-24 上午11:37:41 
*
 */
@Controller
public class CommonApproveCommentController
{
	@Autowired
	private ICommonApproveService commonApproveServcie;
	
	/**
	 * 
	 * @Title: viewProject
	 * @Description: 查看审核意见信息
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 * @throws
	 */
	@RequestMapping("/common/approve/nsm/viewApproveComments")
	public String viewApproveComments(ModelMap model,HttpServletRequest request,String id)
	{
		if(StringUtils.isNotEmpty(id))
		{
			List<CommonApproveComments> commentsList = commonApproveServcie.queryCommentsList(id);
			model.addAttribute("commentsList",commentsList);
		}
		return "/common/approve/approveCommentsView";
	}
	
}
