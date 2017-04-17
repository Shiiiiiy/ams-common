package com.uws.common.util;

import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

public class QuestionNaireConstants {

	/**
	 * 数据字典工具类
	 */
	private static DicUtil dicUtil=DicFactory.getDicUtil();
	
	/**
	 * 【系统数据字典_问卷状态_保存】
	 */
	public static final Dic PAPER_STATUS_SAVED=dicUtil.getDicInfo("PAPER_STATUS", "STATUS_SAVE");
	
	/**
	 * 【系统数据字典_问卷状态_启用】
	 */
	public static final Dic PAPER_STATUS_ENABLE=dicUtil.getDicInfo("PAPER_STATUS", "STATUS_ENABLE");
	
	/**
	 * 【系统数据字典_问卷状态_禁用】
	 */
	public static final Dic PAPER_STATUS_DISABLE=dicUtil.getDicInfo("PAPER_STATUS", "STATUS_DISABLE");
	
	/**
	 * 杭科院学工系统-问卷管理-分隔符
	 */
	public static final String AMS_SPLIT_FLAG_QUESTIONNAIRE="402891114f072c60014f072c613f0000";
	
	/**
	 * 问卷管理接口session
	 */
	public static final String COMMON_SESSION_SCOPE_QUESTIONNAIRE="/common/questionNaire";
	
	/**
	 * 【系统数据字典_答题状态_已提交】
	 */
	public static final Dic ANSWER_STATUS_COMMITED=dicUtil.getDicInfo("ANSWER_STATUS", "COMMITED");
	
	/**
	 * 【系统数据字典_答题状态_未提交】
	 */
	public static final Dic ANSWER_STATUS_UNCOMMITED=dicUtil.getDicInfo("ANSWER_STATUS", "UNCOMMITED");
	
	public static enum INPUT_FLAG_ENUMS{
		/**
		 * 单选，复选选中标志
		 */
		CHECKED,
		/**
		 * 下拉列表选择状态
		 */
		SELECTED,
		/**
		 * 选中
		 */
		TRUE,
		/**
		 * 不选中
		 */
		FALSE
	}
	
	/**
	 * 【系统数据字典_题型_单选】
	 */
	public static final Dic ITEM_TYPE_SINGLE=dicUtil.getDicInfo("ITME_TYPE", "SINGLE");
	
	/**
	 * 【系统数据字典_题型_多选】
	 */
	public static final Dic ITEM_TYPE_MULTIPLE=dicUtil.getDicInfo("ITME_TYPE", "MULTIPLE");
	
	/**
	 * 【系统数据字典_题型_问答】
	 */
	public static final Dic ITEM_TYPE_MULESSAY_QUESTION=dicUtil.getDicInfo("ITME_TYPE", "ESSAY_QUESTION");
	
	/**
	 *	问卷类型
	 */
	public static enum QUESTIONNAIRE_TYPE_ENUMS{
		/**
		 * 迎新
		 */
		QUES_ORIENTATION,
		/**
		 * 毕业
		 */
		QUES_GRADUATION,
		/**
		 * 公共
		 */
		QUES_COMMON
	}
	
	/**
	 *	答题人类型
	 */
	public static enum RESPONDENT_TYPE_ENUMS{
		/**
		 * 学生
		 */
		STUDENT,
		/**
		 * 新生
		 */
		NEW_STUDENT,
		/**
		 * 教职工
		 */
		TEACHER
	}
}
