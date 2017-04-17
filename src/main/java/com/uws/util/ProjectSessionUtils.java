package com.uws.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.uws.core.session.UserSession;

/**
* @ClassName: ProjectSessionUtils 
* @Description: 项目级别的 session 工具类  
* @author 联合永道
* @date 2015-8-7 下午2:18:31 
*
 */
public class ProjectSessionUtils
{
	/**
	 * 
	 * @Title: getCurrentStudentClassId
	 * @Description: 当前登录学生的对应的班级
	 * @param request
	 * @return
	 * @throws
	 */
	public static String getCurrentStudentClassId(HttpServletRequest request)
	{
		String classId = "" ;
		if( null != request )
			classId = (String) request.getSession().getAttribute("_student_classId");
		return classId;
	}
	
	/**
	 * 
	 * @Title: getCurrentStudentMajorId
	 * @Description: 当前登录学生的对应的专业
	 * @param request
	 * @return
	 * @throws
	 */
	public static String getCurrentStudentMajorId(HttpServletRequest request)
	{
		String majorId = "" ;
		if( null != request )
			majorId = (String) request.getSession().getAttribute("_student_majorId");
		return majorId;
	}
	
	/**
	 * 
	 * @Title: getCurrentStudentMajorId
	 * @Description: 当前登录学生的对应的学院
	 * @param request
	 * @return
	 * @throws
	 */
	public static String getCurrentStudentCollegeId(HttpServletRequest request)
	{
		String collegeId = "" ;
		if( null != request )
			collegeId = (String) request.getSession().getAttribute("_student_collegeId");
		return collegeId;
	}
	
	/**
	 * 
	 * @Title: getCurrentTeacherOrgId
	 * @Description: 当前登录教职工的部门ID
	 * @param request
	 * @return
	 * @throws
	 */
	public static String getCurrentTeacherOrgId(HttpServletRequest request)
	{
		String orgId = "" ;
		if( null != request )
			orgId = (String) request.getSession().getAttribute("_teacher_orgId");
		return orgId;
	}
	
	/**
	 * 
	 * @Title: checkIsStudent
	 * @Description: 判断登录人是不是学生
	 * @param request
	 * @return
	 * @throws
	 */
	public static  boolean checkIsStudent(HttpServletRequest request)
	{
		boolean bol = false;
		if( null != request )
		{
			if(null!=request.getSession().getAttribute("_current_user_type")
					&& ProjectConstants.LOGIN_USER_TYPE.STUDENT.equals(request.getSession().getAttribute("_current_user_type"))){
				bol = true;
			}
		}
		return bol;
	}
	
	/**
	 * 
	 * @Title: checkIsStudent
	 * @Description: 判断登录人是不是教职工
	 * @param request
	 * @return
	 * @throws
	 */
	public static  boolean checkIsTeacher(HttpServletRequest request)
	{
		boolean bol = false;
		if( null != request )
		{
			if(null!= request.getSession().getAttribute("_current_user_type") 
					&& ProjectConstants.LOGIN_USER_TYPE.TEANCHER.equals(request.getSession().getAttribute("_current_user_type"))){
				bol = true;
			}
		}
		return bol;
	}
	
	
	/**
	 * 
	 * @Title: getCurrentUserName
	 * @Description: 获取当前登录人的姓名
	 * @param request
	 * @return
	 * @throws
	 */
	public static String getCurrentUserName(HttpServletRequest request)
	{
		if( null != request )
		{
			UserSession userSession = (UserSession) request.getSession().getAttribute("user_key");
			if(null != userSession)
				return userSession.getUserName();
		}
		return "";
	}
	
	/**
	 * 
	 * @Title: getApproveProcessStatus
	 * @Description: 公用的状态MAP 
	 * @return
	 * @throws
	 */
	public static Map<String,String> getApproveProcessStatus()
	{
		Map<String,String> statusMap = new HashMap<String,String>();
		statusMap.put("CURRENT_APPROVE", "待审核");
		statusMap.put("APPROVEING", "审核中");
		statusMap.put("PASS", "审核通过");
		statusMap.put("REJECT", "审核拒绝");
		return statusMap;
	}
	
	/**
	 * 审核状态-待审核
	 */
	public static final String APPROVE_STATUS_NOTAPPROVE="待审核";
	/**
	 * 审核状态-审核中
	 */
	public static final String APPROVE_STATUS_APPROVING="审核中";
	/**
	 * 审核状态-审核通过
	 */
	public static final String APPROVE_STATUS_PASS="审核通过";
	/**
	 * 审核拒绝
	 */
	public static final String APPROVE_STATUS_REJECT="审核拒绝";
	
	/**
	 * 根据审批流程状态编码获取【状态名称】
	 * @param code【PASS、REJECT、APPROVEING、CURRENT_APPROVE】
	 * @return【审核通过、审核拒绝、审核中、待审核】
	 */
	public static String getApproveProcessStatusByCode(String code){
		Map<String,String> processSatusMap = getApproveProcessStatus();
		return processSatusMap.get(code);
	}
	
}
