package com.uws.comp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uws.common.service.IBaseDataService;
import com.uws.common.service.IStudentCommonService;
import com.uws.comp.service.ICompService;
import com.uws.core.base.BaseController;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.tree.TreeBean;
import com.uws.core.util.CompUtil;
import com.uws.core.util.DataUtil;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.base.JsonModel;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.log.Logger;
import com.uws.log.LoggerFactory;

/**
 * 
* @ClassName: CompController 
* @Description: 公用组件存放调用
* @author 联合永道
* @date 2015-7-30 下午3:03:49 
*
 */
@Controller("com.uws.comp.controller.CompController")
public class CompController extends BaseController
{
	private Logger logger = new LoggerFactory(CompController.class);
	@Autowired
	private ICompService compService;
	@Autowired
	private IBaseDataService baseDataService;
	@Autowired
	private IStudentCommonService studentCommonServie;
	
	
	/**
	 * 
	 * @Title: queryMajorJsonList
	 * @Description: 学院级联专业
	 * @param model
	 * @param request
	 * @param collageId
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/comp/opt-query/initCollegeJson"}, produces={"text/plain;charset=UTF-8"})
	public String queryCollegeJsonList(ModelMap model, HttpServletRequest request,String key) 
	{
		List<BaseAcademyModel> list = baseDataService.listBaseAcademy();
		List<JsonModel> jsonList = new ArrayList<JsonModel>();
		if (list!= null && list.size() > 0) {
			JsonModel json = null;
			for (BaseAcademyModel c: list) 
			{
				json = new JsonModel();
				json.setId(c.getId());
				json.setName(c.getName());
				jsonList.add(json);
			}
		}
		String result = JSONArray.fromObject(jsonList).toString();
		logger.debug("学院信息结果封装成的JSON字符串信息输出："+ result);
		return result;
	}
	
	
	/**
	 * 
	 * @Title: queryMajorJsonList
	 * @Description: 学院级联专业
	 * @param model
	 * @param request
	 * @param collageId
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/comp/opt-query/initCascadeMajor"}, produces={"text/plain;charset=UTF-8"})
	public String queryMajorJsonList(ModelMap model, HttpServletRequest request,String key) 
	{
		List<BaseMajorModel> list = compService.queryMajorByCollage(key);
		List<JsonModel> jsonList = new ArrayList<JsonModel>();
		if (list!= null && list.size() > 0) {
			JsonModel json = null;
			for (BaseMajorModel c: list) 
			{
				json = new JsonModel();
				json.setId(c.getId());
				json.setName(c.getMajorName());
				jsonList.add(json);
			}
		}
		String result = JSONArray.fromObject(jsonList).toString();
		logger.debug("专业信息结果封装成的JSON字符串信息输出："+ result);
		return result;
	}
	
	/**
	 * 
	 * @Title: queryClassByCollegeId
	 * @Description: 学院级联班级的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value ={ "/comp/opt-query/initCascadeClassByCollege" }, produces ={ "text/plain;charset=UTF-8" })
	public String queryClassByCollegeId(HttpServletRequest request,HttpServletResponse response,String key){
		List<BaseClassModel> list = this.baseDataService.listBaseClass("", "", key);
		List<JsonModel> jsonList = new ArrayList<JsonModel>();
		if (list!= null && list.size() > 0) {
			JsonModel json = null;
			for (BaseClassModel c: list){
				json = new JsonModel();
				json.setId(c.getId());
				json.setName(c.getClassName());
				jsonList.add(json);
			}
		}
		String result = JSONArray.fromObject(jsonList).toString();
		logger.debug("班级信息结果封装成的JSON字符串信息输出："+ result);
		return result;
	}
	
	/**
	 * 
	 * @Title: queryClassJsonList
	 * @Description: 专业级联班级
	 * @param model
	 * @param request
	 * @param key
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value={"/comp/opt-query/initCascadeClass"}, produces={"text/plain;charset=UTF-8"})
	public String queryClassJsonList(ModelMap model, HttpServletRequest request,String key) 
	{
		List<BaseClassModel> list = compService.queryClassByMajor(key);
		List<JsonModel> jsonList = new ArrayList<JsonModel>();
		if (list!= null && list.size() > 0) {
			JsonModel json = null;
			for (BaseClassModel c: list) 
			{
				json = new JsonModel();
				json.setId(c.getId());
				json.setName(c.getClassName());
				jsonList.add(json);
			}
		}
		String result = JSONArray.fromObject(jsonList).toString();
		logger.debug("班级信息结果封装成的JSON字符串信息输出："+ result);
		return result;
	}
	
	/**
	 * 
	 * @Title: queryStudent
	 * @Description: 学生查询， 组件查询
	 * @param model
	 * @param request
	 * @param student
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping(value={"comp/student/nsm/queryRadioSteudent"})
	public String queryStudent(ModelMap model, HttpServletRequest request,StudentInfoModel student,String selectedStudentId,String formId,String queryFlag) 
	{
		int pageNo = request.getParameter("pageNo") != null ? Integer.valueOf(request.getParameter("pageNo")).intValue() : 1;
		Page page =  studentCommonServie.queryStudentPage(pageNo,5,student);
		model.addAttribute("page", page);
		Collection<StudentInfoModel> list = page.getResult();
		for( StudentInfoModel stu : list )
		{
			stu.setStudentInfo(
					new StringBuffer()
					.append("{id:'").append(stu.getId()).append("',")
					.append("name:'").append(stu.getName()).append("',")
					.append("bankCode:'").append(stu.getBankCode()).append("',")
					.append("bank:'").append(stu.getBank()).append("',")
					.append("className:'").append(stu.getClassId().getClassName()).append("',")
					.append("classId:'").append(stu.getClassId().getId()).append("',")
					.append("majorId:'").append(stu.getMajor().getId()).append("',")
					.append("majorName:'").append(stu.getMajor().getMajorName()).append("',")
					.append("genderId:'").append(stu.getGenderDic().getId()).append("',")
					.append("genderName:'").append(stu.getGenderDic().getName()).append("',")
					.append("sourceLandId:'").append(stu.getSourceLand()).append("',")
					.append("sourceLandName:'").append(stu.getSourceLand()).append("',")
					.append("nativeId:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getId()).append("',")
					.append("nativeName:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getName()).append("',")
					.append("birthDay:'").append(stu.getBrithDate()).append("',")
					.append("collegeId:'").append(stu.getCollege().getId()).append("',")
					.append("collegeName:'").append(stu.getCollege().getName()).append("',")
					.append("stuNumber:'").append(stu.getStuNumber()).append("',")
					.append("certificateCode:'").append(stu.getCertificateCode()).append("',")
					.append("schoolYear:'").append(null == stu.getMajor()? "" : stu.getMajor().getSchoolYear()).append("'}")
					.toString()
			);
		}
		model.addAttribute("selectedId", selectedStudentId);
		model.addAttribute("formId", formId);
		model.addAttribute("queryFlag", queryFlag);
		model.addAttribute("student", student);
		
		if("radio".equalsIgnoreCase(queryFlag))
			return "/comp/student/studentRadioTable";
		else
			return "/comp/student/studentCheckTable";
	}
	
	/** 
	* @Title: queryStudentAndTeacher 
	* @Description: 单选查询教师和学生
	* @param  @param model
	* @param  @param request
	* @param  @param student
	* @param  @param selectedId
	* @param  @param queryType
	* @param  @return    
	* @return String    
	* @throws 
	*/
	@RequestMapping(value={"comp/studentAndteacher/nsm/queryRadioStudentAndTeacher"})
	public String queryStudentAndTeacher(ModelMap model, HttpServletRequest request,StudentInfoModel student,
			BaseTeacherModel teacher,String studentId,String teacherId,String queryType,String formId) {
		int pageNo = request.getParameter("pageNo") != null ? Integer.valueOf(request.getParameter("pageNo")).intValue() : 1;
		if(queryType.equals("student")) {
			if(DataUtil.isNotNull(student.getNamePy())) {
				student.setName(student.getNamePy());
			}
			Page page =  studentCommonServie.queryStudentPage(pageNo,5,student);
			model.addAttribute("page", page);
			Collection<StudentInfoModel> list = page.getResult();
			for( StudentInfoModel stu : list )
			{
				stu.setStudentInfo(
						new StringBuffer()
						.append("{id:'").append(stu.getId()).append("',")
						.append("name:'").append(stu.getName()).append("',")
						.append("bankCode:'").append(stu.getBankCode()).append("',")
						.append("bank:'").append(stu.getBank()).append("',")
						.append("className:'").append(stu.getClassId().getClassName()).append("',")
						.append("classId:'").append(stu.getClassId().getId()).append("',")
						.append("majorId:'").append(stu.getMajor().getId()).append("',")
						.append("majorName:'").append(stu.getMajor().getMajorName()).append("',")
						.append("genderId:'").append(stu.getGenderDic().getId()).append("',")
						.append("genderName:'").append(stu.getGenderDic().getName()).append("',")
						.append("sourceLandId:'").append(stu.getSourceLand()).append("',")
						.append("sourceLandName:'").append(stu.getSourceLand()).append("',")
						.append("nativeId:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getId()).append("',")
						.append("nativeName:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getName()).append("',")
						.append("birthDay:'").append(stu.getBrithDate()).append("',")
						.append("collegeId:'").append(stu.getCollege().getId()).append("',")
						.append("collegeName:'").append(stu.getCollege().getName()).append("',")
						.append("stuNumber:'").append(stu.getStuNumber()).append("',")
						.append("certificateCode:'").append(stu.getCertificateCode()).append("',")
						.append("schoolYear:'").append(null == stu.getMajor()? "" : stu.getMajor().getSchoolYear()).append("'}")
						.toString()
						);
			}
			model.addAttribute("studentId", studentId);
			model.addAttribute("queryType", queryType);
			model.addAttribute("formId", formId);
			model.addAttribute("student", student);
			return "/comp/studentAndteacher/studentRadioTable";
		}else{
			Page page =  baseDataService.listBaseTeacherPage(5,pageNo,teacher);
			Collection<BaseTeacherModel> list = page.getResult();
			for( BaseTeacherModel baseTeacher : list )
			{
				baseTeacher.setTeacherInfo(
						new StringBuffer()
						.append("{id:'").append(baseTeacher.getId()).append("',")
						.append("name:'").append(baseTeacher.getName()).append("',")
						.append("genderId:'").append(baseTeacher.getGender().getId()).append("',")
						.append("genderName:'").append(baseTeacher.getGender().getName()).append("',")
						.append("orgId:'").append(baseTeacher.getOrg().getId()).append("',")
						.append("orgName:'").append(baseTeacher.getOrg().getName()).append("'}")
						.toString()
				);
			}
			model.addAttribute("teacherId", teacherId);
			model.addAttribute("queryType", queryType);
			model.addAttribute("formId", formId);
			model.addAttribute("teacher", teacher);
			model.addAttribute("page", page);
			return "/comp/studentAndteacher/teacherRadioTable";
		}
	}
	
	
	/**
	 * 
	 * @Title: queryStudent
	 * @Description: 学生查询， 组件查询
	 * @param model
	 * @param request
	 * @param student
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping(value={"comp/student/nsm/querySteudentByClass"})
	public String queryStudentByClass(ModelMap model, HttpServletRequest request,StudentInfoModel student,String selectedStudentId,String formId,String queryFlag) 
	{
		int pageNo = request.getParameter("pageNo") != null ? Integer.valueOf(request.getParameter("pageNo")).intValue() : 1;
		Page page =  studentCommonServie.queryStudentPage(pageNo,5,student);
		
		Collection<StudentInfoModel> list = page.getResult();
		for( StudentInfoModel stu : list )
		{
			stu.setStudentInfo(
				new StringBuffer()
				.append("{id:'").append(stu.getId()).append("',")
				.append("name:'").append(stu.getName()).append("',")
				.append("bankCode:'").append(stu.getBankCode()).append("',")
				.append("className:'").append(stu.getClassId().getClassName()).append("',")
				.append("classId:'").append(stu.getClassId().getId()).append("',")
				.append("majorId:'").append(stu.getMajor().getId()).append("',")
				.append("majorName:'").append(stu.getMajor().getMajorName()).append("',")
				.append("genderId:'").append(stu.getGenderDic().getId()).append("',")
				.append("genderName:'").append(stu.getGenderDic().getName()).append("',")
				.append("nativeId:'").append(null == stu.getNativeDic() ? "" :  stu.getNativeDic().getId()).append("',")
				.append("nativeName:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getName()).append("',")
				.append("birthDay:'").append(stu.getBrithDate()).append("',")
				.append("collegeId:'").append(stu.getCollege().getId()).append("',")
				.append("collegeName:'").append(stu.getCollege().getName()).append("'}")
				.toString()
			);
		}
		model.addAttribute("selectedId", selectedStudentId);
		model.addAttribute("formId", formId);
		model.addAttribute("queryFlag", queryFlag);
		model.addAttribute("student", student);
		model.addAttribute("page", page);
		if("radio".equalsIgnoreCase(queryFlag))
			return "/comp/student/classStudentRadioTable";
		else
			return "/comp/student/classStudentCheckTable";
	}
	
	/**
	 * 
	 * @Title: getCollegeTree
	 * @Description: 学院树初始化
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value ={ "/comp/opt-init/getCollegeTree" }, produces ={ "text/plain;charset=UTF-8" })
	public String getCollegeTree()
	{
		List<BaseAcademyModel> collegeList = baseDataService.listBaseAcademy();
		List<TreeBean> list = new ArrayList<TreeBean>();
		TreeBean bean = new TreeBean();
		bean.setId("1");
		bean.setName("杭州科技职业技术学院");
		list.add(bean);
		for (BaseAcademyModel college : collegeList)
		{
			bean = new TreeBean();
			bean.setId(college.getId());
			bean.setName(college.getName());
			bean.setpId("1");
			list.add(bean);
		}
		return CompUtil.treeSetValues(list);
	}
	
	/**
	 * 
	 * @Title: queryStudentsJson
	 * @Description: 多选控件选中值初始化
	 * @param ids
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value ={"/comp/opt-query/getStudentsJson"}, produces ={ "text/plain;charset=UTF-8" })
	public String queryStudentsJson(String ids)
	{
		if (StringUtils.isEmpty(ids))
			return "[]";
		StringBuffer sb = new StringBuffer("[");
		int i = 0;
		for (String id : ids.split(","))
		{
			if (i > 0)
				sb.append(",");
			StudentInfoModel stu = studentCommonServie.queryStudentById(id);
			if(null!=stu)
			{
			  sb.append("{id:'").append(stu.getId()).append("',")
		        .append("name:'").append(stu.getName()).append("',")
		        .append("className:'").append(stu.getClassId().getClassName()).append("',")
		        .append("classId:'").append(stu.getClassId().getId()).append("',")
		        .append("majorId:'").append(stu.getMajor().getId()).append("',")
		        .append("majorName:'").append(stu.getMajor().getMajorName()).append("',")
		        .append("birthDay:'").append(stu.getBrithDate()).append("',")
		        .append("genderId:'").append(null == stu.getGenderDic() ? "" : stu.getGenderDic().getId()).append("',")
		        .append("genderName:'").append(null == stu.getGenderDic() ? "" : stu.getGenderDic().getName()).append("',")
		        .append("nativeId:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getId()).append("',")
				.append("nativeName:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getName()).append("',")
		        .append("collegeId:'").append(stu.getCollege().getId()).append("',")
		        .append("collegeName:'").append(stu.getCollege().getName()).append("'}");
			  i++;
			}
		}
		
		return sb.append("]").toString();
	}
	
	/**
	 * 
	 * @Title: queryTeacher
	 * @Description: 教师查询组件
	 * @param model
	 * @param request
	 * @param student
	 * @param selectedStudentId
	 * @param formId
	 * @param queryFlag
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping(value={"comp/teacher/nsm/queryTeachers"})
	public String queryTeacher(ModelMap model, HttpServletRequest request,BaseTeacherModel teacher,String selectedTeacherId,String formId,String queryFlag) 
	{
		int pageNo = request.getParameter("pageNo") != null ? Integer.valueOf(request.getParameter("pageNo")).intValue() : 1;
		Page page =  baseDataService.listBaseTeacherPage(5,pageNo,teacher);
		Collection<BaseTeacherModel> list = page.getResult();
		for( BaseTeacherModel baseTeacher : list )
		{
			baseTeacher.setTeacherInfo(
					new StringBuffer()
					.append("{id:'").append(baseTeacher.getId()).append("',")
					.append("name:'").append(baseTeacher.getName()).append("',")
					.append("genderId:'").append(null == baseTeacher.getGender() ? "" : baseTeacher.getGender().getId()).append("',")
					.append("genderName:'").append(null == baseTeacher.getGender() ? "" :baseTeacher.getGender().getName()).append("',")
					.append("orgId:'").append(baseTeacher.getOrg().getId()).append("',")
					.append("orgName:'").append(baseTeacher.getOrg().getName()).append("'}")
					.toString()
			);
		}
		model.addAttribute("selectedTeacherId", selectedTeacherId);
		model.addAttribute("formId", formId);
		model.addAttribute("queryFlag", queryFlag);
		model.addAttribute("teacher", teacher);
		model.addAttribute("page", page);
		if("radio".equalsIgnoreCase(queryFlag))
			return "/comp/teacher/teacherRadioTable";
		else
			return "/comp/teacher/teacherCheckTable";
	}
	/**
	 * 
	 * @Title: queryStudentsJson
	 * @Description: 教职工多选控件选中值初始化
	 * @param ids
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value ={"/comp/opt-query/getTeadcherJson"}, produces ={ "text/plain;charset=UTF-8" })
	public String queryTeadchersJson(String ids)
	{
		if (StringUtils.isEmpty(ids))
			return "[]";
		StringBuffer sb = new StringBuffer("[");
		int i = 0;
		for (String id : ids.split(","))
		{
			if (i > 0)
				sb.append(",");
				BaseTeacherModel baseTeacher = baseDataService.findTeacherById(id);
				sb.append("{id:'").append(baseTeacher.getId()).append("',")
				.append("name:'").append(baseTeacher.getName()).append("',")
				.append("genderId:'").append(null == baseTeacher.getGender() ? "" : baseTeacher.getGender().getId()).append("',")
				.append("genderName:'").append(null == baseTeacher.getGender() ? "" :baseTeacher.getGender().getName()).append("',")
				.append("orgId:'").append(baseTeacher.getOrg().getId()).append("',")
				.append("orgName:'").append(baseTeacher.getOrg().getName()).append("'}");
			i++;
		}
		return sb.append("]").toString();
	}
	
	/**
	 * 
	 * @Title: queryRadioCollegeTeacher
	 * @Description: 学院教师查询
	 * @param model
	 * @param request
	 * @param teacher
	 * @param selectedTeacherId
	 * @param formId
	 * @param queryFlag
	 * @return
	 * @throws
	 */
	@RequestMapping(value ={"/comp/taecher/nsm/queryRadioCollegeTeacher"})
	public String queryRadioCollegeTeacher(ModelMap model, HttpServletRequest request,BaseTeacherModel teacher,String selectedTeacherId,String formId,String queryFlag)
	{
		int pageNo = request.getParameter("pageNo") != null ? Integer.valueOf(request.getParameter("pageNo")).intValue() : 1;
		Page page =  baseDataService.queryCollegeTeacherPage(pageNo,5,teacher);
		
		Collection<BaseTeacherModel> list = page.getResult();
		for( BaseTeacherModel baseTeacher : list )
		{
			baseTeacher.setTeacherInfo(
				new StringBuffer()
				.append("{id:'").append(baseTeacher.getId()).append("',")
				.append("name:'").append(baseTeacher.getName()).append("',")
				.append("code:'").append(baseTeacher.getCode()).append("',")
				.append("orgId:'").append(baseTeacher.getOrg().getId()).append("',")
				.append("orgName:'").append(baseTeacher.getOrg().getName()).append("'}")
				.toString()
			);
		}
		model.addAttribute("selectedTeacherId", selectedTeacherId);
		model.addAttribute("formId", formId);
		model.addAttribute("queryFlag", queryFlag);
		model.addAttribute("teacher", teacher);
		model.addAttribute("page", page);
		if("radio".equalsIgnoreCase(queryFlag))
			return "/comp/teacher/collegeTeacherRadioTable";
		else
			return "/comp/student/collegeTeacherCheckTable";
	}
	
	/** 
	* @Title: queryClassesJson 
	* @Description: 多选班级控件选中值初始化 
	* @param  @param ids
	* @param  @return    
	* @return String    
	* @throws 
	*/
	@ResponseBody
	@RequestMapping(value ={"/comp/opt-query/getClassesJson"}, produces ={ "text/plain;charset=UTF-8" })
	public String queryClassesJson(String ids) {
		if (StringUtils.isEmpty(ids))
			return "[]";
		StringBuffer sb = new StringBuffer("[");
		int i = 0;
		for (String id : ids.split(",")) {
			if (i > 0)
				sb.append(",");
			BaseClassModel classModel = this.baseDataService.findClassById(id);
			if(classModel != null) {
				sb.append("{id:'").append(classModel.getId()).append("',")
				.append("classCode:'").append(classModel.getCode()).append("',")
				.append("name:'").append(classModel.getClassName()).append("',")
				.append("grade:'").append(classModel.getGrade()).append("',")
				.append("majorId:'").append(classModel.getMajor().getId()).append("',")
				.append("majorName:'").append(classModel.getMajor().getMajorName()).append("',")
				.append("collegeId:'").append(classModel.getMajor().getCollage().getId()).append("',")
				.append("collegeName:'").append(classModel.getMajor().getCollage().getName()).append("',")
				.append("headerTeacherId:'").append(classModel.getHeadermaster() != null ? classModel.getHeadermaster().getId() : "").append("',")
				.append("headerTeacherName:'").append(classModel.getHeadermaster() != null ? classModel.getHeadermaster().getName() : "").append("'}");
				i++;
			}
		}
		return sb.append("]").toString();
	}
	
	/** 
	* @Title: queryClassCheckBox 
	* @Description: 班级查询  组件查询
	* @param  model
	* @param  request
	* @param  classModel
	* @param  selectedclassId
	* @param  formId
	* @param  queryFlag
	* @return String    
	* @throws 
	*/
	@RequestMapping(value={"comp/class/nsm/queryClassCheckBox"})
	public String queryClassCheckBox(ModelMap model, HttpServletRequest request,BaseClassModel baseClass,
			String selectedclassId,String formId,String queryFlag) {
		int pageNo = request.getParameter("pageNo") != null ? Integer.valueOf(request.getParameter("pageNo")).intValue() : 1;
		Page page = this.baseDataService.listBaseClassPage(5, pageNo, baseClass);
		@SuppressWarnings("unchecked")
		List<BaseClassModel> classList = (List<BaseClassModel>) page.getResult();
		for(BaseClassModel classModel : classList) {
			classModel.setClassInfo(new StringBuffer()
				.append("{id:'").append(classModel.getId()).append("',")
				.append("classCode:'").append(classModel.getCode()).append("',")
				.append("name:'").append(classModel.getClassName()).append("',")
				.append("grade:'").append(classModel.getGrade()).append("',")
				.append("majorId:'").append(classModel.getMajor().getId()).append("',")
				.append("majorName:'").append(classModel.getMajor().getMajorName()).append("',")
				.append("collegeId:'").append(classModel.getMajor().getCollage().getId()).append("',")
				.append("collegeName:'").append(classModel.getMajor().getCollage().getName()).append("',")
				.append("headerTeacherId:'").append(classModel.getHeadermaster() != null ? classModel.getHeadermaster().getId() : "").append("',")
				.append("headerTeacherName:'").append(classModel.getHeadermaster() != null ? classModel.getHeadermaster().getName() : "").append("'}")
				.toString());
		}
		model.addAttribute("selectedId", selectedclassId);
		model.addAttribute("formId", formId);
		model.addAttribute("queryFlag", queryFlag);
		model.addAttribute("classes", baseClass);
		model.addAttribute("page", page);
		return "/comp/class/classSelectedCheckTable";
	}
	
	
	/** 
	* @Title: queryClassesJson 
	* @Description: 多选班级控件选中值初始化 
	* @param  @param ids
	* @param  @return    
	* @return String    
	* @throws 
	*/
	@ResponseBody
	@RequestMapping(value ={"/comp/opt-query/getMajorsJson"}, produces ={ "text/plain;charset=UTF-8" })
	public String queryMajorsJson(String ids) {
		if (StringUtils.isEmpty(ids))
			return "[]";
		StringBuffer sb = new StringBuffer("[");
		int i = 0;
		for (String id : ids.split(",")) {
			if (i > 0)
				sb.append(",");
			BaseMajorModel majorModel = this.baseDataService.findMajorById(id);
			if(majorModel != null) {
				sb.append("{id:'").append(majorModel.getId()).append("',")
				  .append("name:'").append(majorModel.getMajorName()).append("',")
				  .append("collegeId:'").append(majorModel.getCollage().getId()).append("',")
				  .append("collegeName:'").append(majorModel.getCollage().getName()).append("'}");
				i++;
			}
		}
		return sb.append("]").toString();
	}
	
	/** 
	* @Title: queryClassCheckBox 
	* @Description: 专业查询  组件查询
	* @param  model
	* @param  request
	* @param  baseMajor
	* @param  selectedMajorId
	* @param  formId
	* @param  queryFlag
	* @return String    
	* @throws 
	*/
	@RequestMapping(value={"comp/major/nsm/queryMajorCheckBox"})
	public String queryMajorCheckBox(ModelMap model, HttpServletRequest request,BaseMajorModel baseMajor,
			String selectedMajorId,String queryMajorId,String formId,String queryFlag) {
		int pageNo = request.getParameter("pageNo") != null ? Integer.valueOf(request.getParameter("pageNo")).intValue() : 1;
		baseMajor.setId(queryMajorId);
		Page page = this.baseDataService.listBaseMajorPage(5, pageNo, baseMajor);
		@SuppressWarnings("unchecked")
		List<BaseMajorModel> majorList = (List<BaseMajorModel>) page.getResult();
		for(BaseMajorModel majorModel : majorList) {
			majorModel.setMajorInfo(new StringBuffer()
				.append("{id:'").append(majorModel.getId()).append("',")
				.append("name:'").append(majorModel.getMajorName()).append("',")
				.append("collegeId:'").append(majorModel.getCollage().getId()).append("',")
				.append("collegeName:'").append(majorModel.getCollage().getName()).append("'}")
				.toString());
		}
		model.addAttribute("selectedId", selectedMajorId);
		model.addAttribute("formId", formId);
		model.addAttribute("queryFlag", queryFlag);
		model.addAttribute("baseMajor", baseMajor);
		model.addAttribute("page", page);
		return "/comp/major/majorSelectedCheckTable";
	}
	
	
	
	/**
	 * 
	 * @Title: queryStudent
	 * @Description: 毕业生查询， 组件查询
	 * @param model
	 * @param request
	 * @param student
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping(value={"comp/student/nsm/queryCheckGraduateStudent"})
	public String queryCheckGraduateStudent(ModelMap model, HttpServletRequest request,StudentInfoModel student,String selectedStudentId,String formId,String queryFlag){
		int pageNo = request.getParameter("pageNo") != null ? Integer.valueOf(request.getParameter("pageNo")).intValue() : 1;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Page page =  studentCommonServie.queryCheckGraduateStudent(pageNo,5,student);
		model.addAttribute("page", page);
		Collection<StudentInfoModel> list = page.getResult();
		for( StudentInfoModel stu : list ){
			stu.setStudentInfo(
					new StringBuffer()
					.append("{id:'").append(stu.getId()).append("',")
					.append("name:'").append(stu.getName()).append("',")
					.append("bankCode:'").append(stu.getBankCode()).append("',")
					.append("className:'").append(stu.getClassId().getClassName()).append("',")
					.append("classId:'").append(stu.getClassId().getId()).append("',")
					.append("majorId:'").append(stu.getMajor().getId()).append("',")
					.append("majorName:'").append(stu.getMajor().getMajorName()).append("',")
					.append("genderId:'").append(stu.getGenderDic().getId()).append("',")
					.append("genderName:'").append(stu.getGenderDic().getName()).append("',")
					.append("sourceLandId:'").append(stu.getSourceLand()).append("',")
					.append("sourceLandName:'").append(stu.getSourceLand()).append("',")
					.append("nativeId:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getId()).append("',")
					.append("nativeName:'").append(null == stu.getNativeDic() ? "" : stu.getNativeDic().getName()).append("',")
					.append("birthDay:'").append(stu.getBrithDate()).append("',")
					.append("collegeId:'").append(stu.getCollege().getId()).append("',")
					.append("certificateCode:'").append(stu.getCertificateCode() == null ? "" : stu.getCertificateCode()).append("',")
					.append("collegeName:'").append(stu.getCollege().getName()).append("',")
					.append("political:'").append(stu.getPoliticalDic() == null ? "" : stu.getPoliticalDic().getName()).append("',")
					.append("enterDate:'").append(stu.getEnterDate() == null ? "" : dateFormat.format(stu.getEnterDate())).append("',")
					.append("homeTel:'").append(stu.getHomeTel() == null ? "" : stu.getHomeTel()).append("',")
					.append("phone:'").append(stu.getPhone1() == null ? "" : stu.getPhone1()).append("',")
					.append("qq:'").append(stu.getQq() == null ? "" : stu.getQq()).append("',")
					.append("email:'").append(stu.getEmail() == null ? "" : stu.getEmail()).append("',")
					.append("homePostCode:'").append(stu.getHomePostCode() == null ? "" : stu.getHomePostCode()).append("',")
					.append("homeAddress:'").append(stu.getHomeAddress() == null ? "" : stu.getHomeAddress()).append("'}")
					.toString()
			);
		}
		model.addAttribute("selectedId", selectedStudentId);
		model.addAttribute("formId", formId);
		model.addAttribute("queryFlag", queryFlag);
		model.addAttribute("student", student);
		
		if("radio".equalsIgnoreCase(queryFlag)){
			return "/comp/student/graduateStudentRadioTable";
		}else{
			return "/comp/student/graduateStudentCheckTable";
		}
	}
	
}
