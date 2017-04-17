package com.uws.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uws.common.service.IBaseDataService;
import com.uws.common.util.Constants;
import com.uws.comp.service.ICompService;
import com.uws.core.base.BaseController;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.StringUtils;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;
import com.uws.domain.base.BaseRoomModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.base.JsonModel;
import com.uws.log.Logger;
import com.uws.log.LoggerFactory;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

/**
 * 
* @ClassName: BaseDataController 
* @Description: 基础数据 包括同步和查询
* @author 联合永道
* @date 2015-7-13 下午2:25:08 
*
 */
@Controller
public class BaseDataController extends BaseController
{
	// 调试日志
	private Logger log = new LoggerFactory(BaseDataController.class);
	
	@Autowired
	private IBaseDataService baseDataService;
	@Autowired
	private ICompService compService;
	
	//数据字典
	private DicUtil dicUtil = DicFactory.getDicUtil();
	/**
	 * 
	 * @Title: queryBaseMajor
	 * @Description: 专业信息查询
	 * @param model
	 * @param request
	 * @param baseMajor
	 * @return
	 * @throws
	 */
	@RequestMapping(value={"/base/major/opt-query/listBaseMajor"})
	public String queryBaseMajor(ModelMap model,HttpServletRequest request,BaseMajorModel baseMajor)
	{
		log.debug("基础信息 专业信息查询...");

		Integer pageNo = request.getParameter("pageNo")!=null?Integer.valueOf(request.getParameter("pageNo")):1;
		Page page = baseDataService.listBaseMajorPage(Page.DEFAULT_PAGE_SIZE,pageNo,baseMajor);
		
		model.addAttribute("page",page);
		model.addAttribute("majorCateList",dicUtil.getDicInfoList("MAJOR_CATEGORY_CODE"));
		model.addAttribute("baseMajor", baseMajor);
		return "/common/basedata/baseMajorList";
	}
	
	/**
	 * @Title: queryBaseClass
	 * @Description: 班级信息查询
	 * @param model
	 * @param request
	 * @param baseClass
	 * @return
	 * @throws
	 */
	@RequestMapping(value={"/base/class/opt-query/listBaseClass"})
	public String queryBaseClass(ModelMap model,HttpServletRequest request,BaseClassModel baseClass)
	{
		Integer pageNo = request.getParameter("pageNo")!=null?Integer.valueOf(request.getParameter("pageNo")):1;
		Page page = baseDataService.listBaseClassPage(Page.DEFAULT_PAGE_SIZE,pageNo,baseClass);
		List<BaseAcademyModel> collageList = baseDataService.listBaseAcademy();
		List<BaseMajorModel> majorList = null;
		if(null!= baseClass && null != baseClass.getMajor() && null != baseClass.getMajor().getCollage() && baseClass.getMajor().getCollage().getId().length()>0)
		{
			majorList = compService.queryMajorByCollage(baseClass.getMajor().getCollage().getId());
			log.debug("若已经选择学院，则查询学院下的专业信息.");
		}

		model.addAttribute("page",page);
		model.addAttribute("collageList",collageList);
		model.addAttribute("majorList",majorList);
		model.addAttribute("baseClass", baseClass);
		
		return "/common/basedata/baseClassList";
	}
	
	/**
	 * 
	 * @Title: queryBaseTeahcer
	 * @Description: 教职工基础数据查询
	 * @param model
	 * @param request
	 * @param baseTeacher
	 * @return
	 * @throws
	 */
	@RequestMapping(value={"/base/teacher/opt-query/listBaseTeacher"})
	public String queryBaseTeahcer(ModelMap model,HttpServletRequest request,BaseTeacherModel baseTeacher)
	{
		Integer pageNo = request.getParameter("pageNo")!=null?Integer.valueOf(request.getParameter("pageNo")):1;
		Page page = baseDataService.listBaseTeacherPage(Page.DEFAULT_PAGE_SIZE,pageNo,baseTeacher);

		model.addAttribute("page",page);
		model.addAttribute("baseTeacher", baseTeacher);
		
		return "/common/basedata/baseTeacherList";
	}
	
	
	/**
	 * @Title: queryBaseClass
	 * @Description: 班级毕业班管理
	 * @param model
	 * @param request
	 * @param baseClass
	 * @return
	 * @throws
	 */
	@RequestMapping(value={"/base/classmanage/opt-query/listClassManage"})
	public String queryClassManage(ModelMap model,HttpServletRequest request,BaseClassModel baseClass)
	{
		Integer pageNo = request.getParameter("pageNo")!=null?Integer.valueOf(request.getParameter("pageNo")):1;
		Page page = baseDataService.listManageClassPage(Page.DEFAULT_PAGE_SIZE,pageNo,baseClass);
		List<BaseAcademyModel> collageList = baseDataService.listBaseAcademy();
		List<BaseMajorModel> majorList = null;
		if(null!= baseClass && null != baseClass.getMajor() && null != baseClass.getMajor().getCollage() && baseClass.getMajor().getCollage().getId().length()>0)
			majorList = compService.queryMajorByCollage(baseClass.getMajor().getCollage().getId());

		model.addAttribute("page",page);
		model.addAttribute("collageList",collageList);
		model.addAttribute("majorList",majorList);
		model.addAttribute("baseClass", baseClass);
		model.addAttribute("gradeList", baseDataService.listGradeList());
		model.addAttribute("idGraduatedStatus", dicUtil.getDicInfoList("Y&N"));
		model.addAttribute("idGraduatedNo", Constants.STATUS_NO);
		
		return "/common/basedata/baseClassManageList";
	}
	
	/**
	 * 
	 * @Title: classGraduateManage
	 * @Description: 标记是否毕业班
	 * @param model
	 * @param request
	 * @param baseClass
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/base/classmanage/opt-edit/markClassGraduate"})
	public String markClassGraduate(ModelMap model,HttpServletRequest request)
	{
		String[] ids =  request.getParameterValues("graduateClassId");
		if (!ArrayUtils.isEmpty(ids)) 
			baseDataService.updateGraduated(ids,Constants.STATUS_YES);;
		return "success";
	}
	
	/**
	 * 
	 * @Title: classGraduateManage
	 * @Description: 取消标记毕业班
	 * @param model
	 * @param request
	 * @param baseClass
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/base/classmanage/opt-edit/cancelClassGraduate"})
	public String cancelClassGraduate(ModelMap model,HttpServletRequest request)
	{
		String[] ids =  request.getParameterValues("graduateClassId");
		if (!ArrayUtils.isEmpty(ids)) 
			baseDataService.cancelClassGraduate(ids,Constants.STATUS_NO);;
		return "success";
	}
	
	/**
	 * 根据宿舍楼栋信息 查询宿舍楼层信息
	 * @param model
	 * @param request
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/dorm/dormBuilding/opt-query/initCascadeFloor"}, produces={"text/plain;charset=UTF-8"})
	public String queryFloorJsonList(ModelMap model, HttpServletRequest request, String key){
		String doormFloor = this.baseDataService.getdormFloorByBuildId(key);
		List<JsonModel> jsonList = new ArrayList<JsonModel>();
		if (StringUtils.hasText(doormFloor)) {
			//宿舍楼层
			int floorCount = Integer.parseInt(doormFloor);
			JsonModel json = null;
			for (int i = 1; i <= floorCount; i++){
				json = new JsonModel();
				json.setId(i+"");
				json.setName(i+"");
				jsonList.add(json);
			}
		}
		String result = JSONArray.fromObject(jsonList).toString();
		return result;
	}
	
	/**
	 * 
	 * @Title: DormAdjustController.java 
	 * @Package com.uws.stu.dorm.controller 
	 * @Description: 楼层查询宿舍
	 * @author LiuChen 
	 * @date 2016-7-15 下午2:01:53
	 */
	@ResponseBody
	@RequestMapping(value={"/dorm/dormInfo/opt-query/initCascadeDormNo"}, produces={"text/plain;charset=UTF-8"})
	public String queryDormNoJsonList(ModelMap model, HttpServletRequest request,String dormBuildingId,String dormFloor) 
	{   
		List<BaseRoomModel> list = baseDataService.queryDormNoByFloor(dormBuildingId,dormFloor);
		List<JsonModel> jsonList = new ArrayList<JsonModel>();
		if (list!= null && list.size() > 0) {
			JsonModel json = null;
			for (BaseRoomModel c: list) 
			{
				json = new JsonModel();
				json.setId(c.getId());
				json.setName(c.getName());
				jsonList.add(json);
			}
		}
		String result = JSONArray.fromObject(jsonList).toString();
		return result;
	}
	
}
