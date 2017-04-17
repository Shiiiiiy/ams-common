package com.uws.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* @ClassName: ProjectConstants 
* @Description: 系统定义级的常量 
* @author 联合永道
* @date 2015-8-6 下午2:15:33 
*
 */
public class ProjectConstants
{
	/**
	 * 是否启用审批流程
	 */
	public static final Boolean IS_APPROVE_ENABLE = true;
	
	/**
	 * 学生处的组织结构编号
	 */
	public static final String STUDNET_OFFICE_ORG_ID = "04";
	 /** 招就处ID
	 */
	public static final String JOBOFFICEID = "05";
	
	/**
	 * 默认的通过的审批意见
	 */
	public static final String APPROVE_PASS_COMMENTS = "通过";
	
	/**
	 * 综合信息服务模块 审核班长 的角色CODE名称
	 */
	public static final String AUDIT_CLASS_MONITOR_ROLE_NAME = "HKY_APPROVE_MONITOR";
	
	/**
	 * 综合测评 测评员 的角色CODE名称
	 */
	public static final String EVALUATION_ROLE_NAME = "HKY_EVALUATION_USER";
	
	/**
	 * 
	* @ClassName: LOGIN_USER_TYPE 
	* @Description: 当前登录人的类型
	* @author 联合永道
	* @date 2015-8-26 下午2:13:55 
	*
	 */
	public enum LOGIN_USER_TYPE
	{
		STUDENT,
		TEANCHER,
	}
	
	//用于查询和显示
	public static final String CURRENT_APPROVE_USER_PROCESS_CODE = "CURRENT_APPROVE";
	
	//学生状态的类型
	public static Map<String, String> getStatusMap()
	{
		Map<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("1", "有");
		statusMap.put("2", "无");
		statusMap.put("3", "转出");
		statusMap.put("4", "未知");
		return statusMap;
	}
	
	//学生状态有效字符串拼接
	public static final String STUDENT_NORMAL_STAUTS_STRING="1";
	
	/**
	 * 班级状态的标识，从教务同步过来数据标志
	 */
	public static final String BASE_CLASS_STAUTS_USE = "1";
	public static final String BASE_CLASS_STAUTS_UNUSE = "0";
	
}
