package com.uws.common.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uws.common.service.IBaseDataService;
import com.uws.common.service.ICommonConfigService;
import com.uws.core.base.BaseController;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.session.SessionFactory;
import com.uws.core.session.SessionUtil;
import com.uws.core.util.IdUtil;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.config.TermConfigModel;
import com.uws.domain.config.TimeConfigModel;
import com.uws.log.Logger;
import com.uws.log.LoggerFactory;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;
import com.uws.user.model.User;

/**
 * 
* @ClassName: CommonConfigController 
* @Description: 通用的设置定义Controller类
* @author 联合永道
* @date 2015-8-13 上午9:36:14 
*
 */
@Controller("com.uws.common.controller.CommonConfigController")
public class CommonConfigController extends BaseController
{
	@Autowired
	private ICommonConfigService commonConfigService;
	@Autowired
	private IBaseDataService baseDataService;
	
	private Logger logger = new LoggerFactory(this.getClass());
	private DicUtil dicUtil = DicFactory.getDicUtil();
	private SessionUtil sessionUtil = SessionFactory.getSession("/common/config/");
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	/**
	 * 
	 * @Title: qeuryTimeConfigList
	 * @Description: 时间设置列表查询
	 * @param request
	 * @param response
	 * @param configModel
	 * @return
	 * @throws
	 */
	@RequestMapping("/common/config/opt-query/listTimeConfig")
	public String qeuryTimeConfigList(HttpServletRequest request, HttpServletResponse response ,  Model model , TimeConfigModel configModel)
	{

		Integer pageNo = request.getParameter("pageNo")!=null?Integer.valueOf(request.getParameter("pageNo")):1;
		Page page = commonConfigService.listTimeConfigPage(Page.DEFAULT_PAGE_SIZE,pageNo,configModel);
		logger.debug("time config query page ...");
		List<BaseAcademyModel> collageList = baseDataService.listBaseAcademy();
		model.addAttribute("configModel",configModel);
		model.addAttribute("collageList",collageList);
		model.addAttribute("page",page);
		
		return "/common/config/commonConfigList";
	}
	
	/**
	 * 
	 * @Title: editTimeConfig
	 * @Description: 新增和修改跳转方法
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws
	 */
	@RequestMapping("/common/config/opt-edit/editTimeConfig")
	public String editTimeConfig(HttpServletRequest request, HttpServletResponse response ,Model model ,String id)
	{
		TimeConfigModel configModel = null;
		if( StringUtils.hasText(id))
			configModel = commonConfigService.getById(id);
		else
			configModel = new TimeConfigModel();
		logger.debug("time config edit method ...");
		List<BaseAcademyModel> collageList = baseDataService.listBaseAcademy();
		model.addAttribute("configModel",configModel);
		model.addAttribute("collageList",collageList);
		return "/common/config/commonConfigEdit";
	}
	
	/**
	 * 
	 * @Title: submitTimeConfig
	 * @Description: 保存更新 
	 * @param request
	 * @param response
	 * @param model
	 * @param configModel
	 * @return
	 * @throws
	 */
	@RequestMapping("/common/config/opt-submit/submitTimeConfig")
	public String submitTimeConfig(HttpServletRequest request, HttpServletResponse response ,ModelMap model , TimeConfigModel configModel)
	{
		User user = new User();
		String userId = sessionUtil.getCurrentUserId();
		user.setId(userId);
		
		if(StringUtils.hasText(configModel.getId()))
		{
			logger.debug("修改，更新操作...");
			configModel.setUpdateUser(user);
			commonConfigService.updateTimeConfig(configModel);
		}
		else
		{
			logger.debug("新增，保存操作...");
			configModel.setUpdateUser(user);
			configModel.setCreator(user);
			commonConfigService.saveTimeConfig(configModel);
		}
		return "redirect:/common/config/opt-query/listTimeConfig.do";
	}
	
	/**
	 * 
	 * @Title: deleteTImeConfig
	 * @Description: 删除  物理删除  开发用 ，部署后 屏蔽删除操作
	 * @param request
	 * @param id
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/common/config/opt-delete/deleteTimeConfig"}, produces={"text/plain;charset=UTF-8"})
	public String deleteTimeConfig(HttpServletRequest request,String id)
	{
		logger.debug("物理删除，开发过程使用");
		if(StringUtils.hasText(id))
			commonConfigService.deleteById(id);
		return "success";
	}
	
	
	/**
	 * 
	 * @Title: timeConfigClientEdit
	 * @Description: 接口调用弹出层显示
	 * @param request
	 * @param id
	 * @return
	 * @throws
	 */
	@RequestMapping(value={"/common/config/nsm/timeConfigClientEdit"})
	public String timeConfigClientEdit(HttpServletRequest request,Model model,String code)
	{
		logger.debug("接口调用 显示时间设置信息");
		TimeConfigModel configModel = null;
		if(StringUtils.hasText(code))
			configModel = commonConfigService.findByCondition(code);
		else
			configModel = new TimeConfigModel();
		model.addAttribute("configModel", configModel);
		return "/common/config/timeConfigPopEdit";
	}
	
	/**
	 * 
	 * @Title: ajaxSubmitTimeConfig
	 * @Description: 异步提交 客户端调用
	 * @param request
	 * @param response
	 * @param configModel
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/common/config/opt-submit/ajaxSubmitTimeConfig"}, produces={"text/plain;charset=UTF-8"})
	public String ajaxSubmitTimeConfig(HttpServletRequest request, HttpServletResponse response , TimeConfigModel configModel)
	{
		User user = new User();
		String userId = sessionUtil.getCurrentUserId();
		user.setId(userId);
		StringBuffer sbuffer = new StringBuffer();
		if(StringUtils.hasText(configModel.getId()))
		{
			logger.debug("修改，异步提交 客户端调用...");
			configModel.setUpdateUser(user);
			commonConfigService.updateTimeConfig(configModel);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			sbuffer
				.append(dateFormat.format(configModel.getBeginDate())).append(",")
				.append(dateFormat.format(configModel.getEndDate())).append(",")
				.append(configModel.getCode());
		}
		return sbuffer.toString();
	}
	
	
	/**
	 * 
	 * @Title: qeuryTermConfigList
	 * @Description: 学期设置 列表
	 * @param request
	 * @param response
	 * @param model
	 * @param termModel
	 * @return
	 * @throws
	 */
	@RequestMapping("/common/term/opt-query/listTermConfig")
	public String qeuryTermConfigList(HttpServletRequest request, HttpServletResponse response ,  ModelMap model , TermConfigModel termModel)
	{
		logger.debug("学期设置列表查询 ...");
		
		List<TermConfigModel> termList = commonConfigService.listTermConfigList();
		
		model.addAttribute("termModel",termModel);
		model.addAttribute("termList",termList);
		
		return "/common/term/termConfigList";
	}
	
	/**
	 * 
	 * @Title: editTermConfig
	 * @Description: 新增 修改 学期设置 编辑页面
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws
	 */
	@RequestMapping("/common/term/nsm/termConfigEdit")
	public String editTermConfig(HttpServletRequest request, HttpServletResponse response, ModelMap model ,String id)
	{
		logger.debug("学期设置编辑或新增...");
		TermConfigModel termModel = new TermConfigModel();
		if(StringUtils.hasText(id))
			termModel = commonConfigService.getTermConfigById(id);

		model.addAttribute("termModel",termModel);
		model.addAttribute("yearList",dicUtil.getDicInfoList("YEAR"));
		model.addAttribute("termList",dicUtil.getDicInfoList("TERM"));
		
		return "/common/term/termConfigEdit";
	}
	
	/**
	 * 
	 * @Title: ajaxSubmitTermConfig
	 * @Description: 提交学期配置信息
	 * @param request
	 * @param response
	 * @param configModel
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/common/term/opt-submit/ajaxSubmitTermConfig"}, produces={"text/plain;charset=UTF-8"})
	public String ajaxSubmitTermConfig(HttpServletRequest request, HttpServletResponse response , TermConfigModel termModel)
	{
		//判断是否存在
		String id = termModel.getId();
		//新增时候  判断是否够存在;
		if(!StringUtils.hasText(id))
		{
			List<TermConfigModel> list = commonConfigService.queryTermConfigList(termModel.getYearDic(),termModel.getTermDic());
			int len = null == list ? 0 : list.size();
			if(len > 0)
				return "exist";
		}
		commonConfigService.saveOrUpdateTermConfig(termModel);
		return "success";
	}
	
	/**
	 * 
	 * @Title: generateUUIDStr
	 * @Description: 生成32位的主键ID
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/common/config/nsm/getUuid"}, produces={"text/plain;charset=UTF-8"})
	public String generateUUIDStr(HttpServletRequest request, HttpServletResponse response)
	{
		return IdUtil.getUUIDHEXStr();
	}
	
}
