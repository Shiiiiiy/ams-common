package com.uws.common.util;

import java.util.ArrayList;
import java.util.List;

import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

/**
* @ClassName: Constants 
* @Description: 通用 常量 
* @date 2015-7-17 下午14:45:48 
 */
public class Constants {
	
	/**
	 * 数据字典工具类
	 */
	private static DicUtil dicUtil=DicFactory.getDicUtil();
	
	/**
	 * excel导入默认最大条数
	 */
	public static  int DEFAULT_EXCEL_MAX_COUNT = 1000;
	
	/**
	 * 报名审批状态下拉列表
	 */
	public static List<Dic> applyApproveStatusList = new ArrayList<Dic>();
	static{
		applyApproveStatusList.add(dicUtil.getDicInfo("APPLY_APPROVE","PASS"));
		applyApproveStatusList.add(dicUtil.getDicInfo("APPLY_APPROVE","REJECT"));
	}
	
	/**
	 * 审批状态【数据字典】
	 */
	public static final List<Dic> approveStatusList = dicUtil.getDicInfoList("APPLY_APPROVE");

	/**
	 * 【系统数据字典_逻辑判断_是】
	 */
	public static final Dic STATUS_YES=dicUtil.getDicInfo("Y&N", "Y");
	
	/**
	 * 【系统数据字典_逻辑判断_否】
	 */
	public static final Dic STATUS_NO=dicUtil.getDicInfo("Y&N", "N");
	
	/**
	 * 【系统逻辑判断：是、否】
	 */
	public static enum STATUS_Y_N{
		/**
		 * 是
		 */
		Y,
		/**
		 * 否
		 */
		N
	}
	
	/**
	 * 【系统数据字典_逻辑删除_正常状态】
	 */
	public static final Dic STATUS_NORMAL=dicUtil.getDicInfo("STATUS_NORMAL_DELETED", "NORMAL");
	
	/**
	 * 【系统数据字典_逻辑删除_删除状态】
	 */
	public static final Dic STATUS_DELETED=dicUtil.getDicInfo("STATUS_NORMAL_DELETED", "DELETED");
	
	/**
	 * 【系统数据字典_状态_启用状态】
	 */
	public static final Dic STATUS_ENABLE=dicUtil.getDicInfo("STATUS_ENABLE_DISABLE", "ENABLE");
	
	/**
	 * 【系统数据字典_状态_禁用状态】
	 */
	public static final Dic STATUS_DISABLE=dicUtil.getDicInfo("STATUS_ENABLE_DISABLE", "DISABLE");

	// 系统通用系统配置常量 
	public static enum COMMON_SYSTEM_CONFIG_ENUM
	{
	   STUDY_WORK_SALARY, //勤工助学岗位工资
	   TEMP_WORK_SALARY, //临时勤工助学岗位工资	  
	}
	
	/**
	 * 测评网站的地址链接
	 */
	public static final String EVALUATE_WEB_URL_CODE = "EVALUATE_WEB_URL";
	/**
	 * 资助时间按设置编码
	 */
	public static final String SPONSOR_AWARD_TIME_CONFIG_CODE = "SPONSOR_AWARD_TIME";
	
	/**
	 * 学生测评网址编码
	 */
	public static final String STUDENT_EVALUATE_WEB_CODE = "STUDENT_EVALUATE_WEB";
	
	/**
	 * 操作状态【保存、提交】
	 */
	public static enum OPERATE_STATUS{
		/**
		 * 保存
		 */
		SAVE,
		/**
		 * 提交
		 */
		SUBMIT}
	
	/**
	 * 操作状态
	 */
	public static final List<Dic> operateStatusList = dicUtil.getDicInfoList("OPERATE_STATUS");
	
	/**
	 * 操作状态-保存
	 */
	public static final Dic OPERATE_STATUS_SAVE = dicUtil.getDicInfo("OPERATE_STATUS", "SAVE");
	
	/**
	 * 操作状态-提交
	 */
	public static final Dic OPERATE_STATUS_SUBMIT = dicUtil.getDicInfo("OPERATE_STATUS", "SUBMIT");
	
	/**
	 * 性别数据字典
	 */
	public static final List<Dic> genderList = dicUtil.getDicInfoList("GENDER");
	
	/**
	 * 性别-男
	 */
	public static final Dic GENDER_MALE = dicUtil.getDicInfo("GENDER","MALE");
	
	/**
	 * 性别-女
	 */
	public static final Dic GENDER_FEMALE = dicUtil.getDicInfo("GENDER","FEMALE");
	
	/**
	 * 常用角色
	 */
	public static enum HKY_ROLE{
		/**
		 * 学生
		 */
		HKY_SUTDENT,
		/**
		 * 教师
		 */
		HKY_TEACHER
	}
	
	/**
	 * 【数据字典_政治面貌_党员】
	 */
	public static final Dic STATUS_PARTY_DICS=dicUtil.getDicInfo("SCH_POLITICAL_STATUS","01");
	/**
	 * 【数据字典_政治面貌_预备党员】
	 */
	public static final Dic STATUS_PROBATIONARY_DICS=dicUtil.getDicInfo("SCH_POLITICAL_STATUS","02");
	/**
	 * 【数据字典_政治面貌_团员】
	 */
	public static final Dic STATUS_LEAGUEMEMBER_DICS=dicUtil.getDicInfo("SCH_POLITICAL_STATUS","03");
	
	public static final String STUDENT_FEE_STATUS_STR = "缴清";
	
}
