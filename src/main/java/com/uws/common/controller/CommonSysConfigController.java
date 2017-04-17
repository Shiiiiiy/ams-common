package com.uws.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uws.common.util.Constants;
import com.uws.core.base.BaseController;
import com.uws.sys.model.SysConfig;
import com.uws.sys.service.ISysConfigService;

/**
 * 
* @ClassName: CommonSysConfigController 
* @Description:通用设置controller
* @author 联合永道
* @date 2015-8-19 上午11:46:45 
*
 */
@Controller
public class CommonSysConfigController extends BaseController
{
	@Autowired
	private ISysConfigService sysConfigService;
	
	/**
	 * 系统设置编辑 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping({ "/common/sysConfig/opt-update/editSysConfig" })
	public String editSysConfig(ModelMap model, HttpServletRequest request)
	{
		model.addAttribute("studyWorkSalary", sysConfigService.getSysConfig(Constants.COMMON_SYSTEM_CONFIG_ENUM.STUDY_WORK_SALARY.toString()));
		model.addAttribute("tempWorkSalary", sysConfigService.getSysConfig(Constants.COMMON_SYSTEM_CONFIG_ENUM.TEMP_WORK_SALARY.toString()));
		model.addAttribute("webUrl", sysConfigService.getSysConfig(Constants.EVALUATE_WEB_URL_CODE));
		model.addAttribute("sponsorTimeConfig", sysConfigService.getSysConfig(Constants.SPONSOR_AWARD_TIME_CONFIG_CODE));
		model.addAttribute("studentEvaluateUrl", sysConfigService.getSysConfig(Constants.STUDENT_EVALUATE_WEB_CODE));
		return "/common/config/editCommonConfig";
	}

	/**
	 * 系统设置 保存信息 
	 * @param request
	 * @return
	 */
	@RequestMapping({ "/common/sysConfig/opt-update/submitSysConfig" })
	public String submitSysConfig(HttpServletRequest request)
	{
		//勤工助学岗位工资
		SysConfig studyWorkSalary = this.sysConfigService.getSysConfig(Constants.COMMON_SYSTEM_CONFIG_ENUM.STUDY_WORK_SALARY.toString());
		studyWorkSalary.setValue(request.getParameter(Constants.COMMON_SYSTEM_CONFIG_ENUM.STUDY_WORK_SALARY.toString()).trim());
		this.sysConfigService.updateSysConfig(studyWorkSalary);
		
		//临时勤工助学岗位工资
		SysConfig tempWorkSalary = this.sysConfigService.getSysConfig(Constants.COMMON_SYSTEM_CONFIG_ENUM.TEMP_WORK_SALARY.toString());
		tempWorkSalary.setValue(request.getParameter(Constants.COMMON_SYSTEM_CONFIG_ENUM.TEMP_WORK_SALARY.toString()).trim());
		this.sysConfigService.updateSysConfig(tempWorkSalary);

		//综合测评网站
		SysConfig webUrl = this.sysConfigService.getSysConfig(Constants.EVALUATE_WEB_URL_CODE);
		webUrl.setValue(request.getParameter(Constants.EVALUATE_WEB_URL_CODE).trim());
		this.sysConfigService.updateSysConfig(webUrl);
		
		//勤工奖助设置时间编码
		SysConfig sponsorTimeConfig = this.sysConfigService.getSysConfig(Constants.SPONSOR_AWARD_TIME_CONFIG_CODE);
		sponsorTimeConfig.setValue(request.getParameter(Constants.SPONSOR_AWARD_TIME_CONFIG_CODE).trim());
		this.sysConfigService.updateSysConfig(sponsorTimeConfig);
		
		//学生测评网址
		SysConfig studentEvaluateUrl = this.sysConfigService.getSysConfig(Constants.STUDENT_EVALUATE_WEB_CODE);
		studentEvaluateUrl.setValue(request.getParameter(Constants.STUDENT_EVALUATE_WEB_CODE).trim());
		this.sysConfigService.updateSysConfig(studentEvaluateUrl);
		
		return "redirect:/common/sysConfig/opt-update/editSysConfig.do";
	}
	
}
