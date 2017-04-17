package com.uws.common.util;

import java.util.HashMap;
import java.util.Map;

import com.uws.core.util.DataUtil;
import com.uws.log.Logger;
import com.uws.log.LoggerFactory;
import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

/**
 * 共青团常量类
 */
public class CYLeagueUtil {
	
	/**
	 * 数据字典工具类
	 */
	private static DicUtil dicUtil=DicFactory.getDicUtil();
	
	/**
	 * 【报名审核-通过】
	 */
	public static final Dic APPROVE_PASS=dicUtil.getDicInfo("APPLY_APPROVE", "PASS");
	
	/**
	 * 【报名审核-拒绝】
	 */
	public static final Dic APPROVE_REJECT=dicUtil.getDicInfo("APPLY_APPROVE", "REJECT");
	
	/**
	 * 【报名审核-待审核】
	 */
	public static final Dic APPROVE_NOT_APPROVE=dicUtil.getDicInfo("APPLY_APPROVE", "NOT_APPROVE");
	
	/**
	 * 获取报名审批状态
	 */
	public static final Map<String,String> getApproveStatusMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put(APPLY_APPROVE_STATUS.PASS.toString(),"通过");
		map.put(APPLY_APPROVE_STATUS.NOT_APPROVE.toString(),"待审核");
		return  map;
	}
	
	/**
	 * 报名审核状态枚举
	 */
	public static enum APPLY_APPROVE_STATUS{
		/**
		 * 审核中
		 */
		CURRENT_APPROVE,
		/**
		 * 通过
		 */
		PASS,
		/**
		 * 拒绝
		 */
		REJECT,
		/**
		 * 待审核
		 */
		NOT_APPROVE
	}
	
	/**
	 * 获取查询条件
	 * @param ids						ID字符串
	 * @return								in(......)查询条件
	 */
	public static String getCondition(String ids) {
		Logger logger = new LoggerFactory(CYLeagueUtil.class);
		StringBuffer sbff = new StringBuffer();
		if(DataUtil.isNotNull(ids)){
			sbff.append(" (");
			String stuArray[] = ids.split(",");
			for(int i=0;i<stuArray.length;i++){
				String stuId = stuArray[i];
				if(stuArray.length-1==i){
					sbff.append("'"+stuId+"'");
				}else{
					sbff.append("'"+stuId+"'").append(",");
				}
			}
			sbff.append(")");
		}else{
			logger.error("选人控件获取的 ids为空 !");
		}
		return sbff.toString();
	}
	
	/**
	 * 共青团-特有角色
	 */
	public static enum CYL_ROLES{
		/**
		 * 社团负责人
		 */
		HKY_ASSOCIATION_MANAGER,
		/**
		 * 校社联领导
		 */
		HKY_ASSOCIATION_UNION_LEADER,
		/**
		 * 学院团委领导
		 */
		HKY_COLLEGE_LEAGUE_LEADER,
		/**
		 * 学院青协领导
		 */
		HKY_COLLEGE_YOUTH_CLUB_LEADER,
		/**
		 * 团支部书记
		 */
		HKY_LEAGUE_SECRETARY,
		/**
		 * 党委宣传部领导
		 */
		HKY_PARTY_PUBLICITY_LEADER,
		/**
		 * 学校青协领导
		 */
		HKY_SCHOLL_YOUTHCLUB_LEADER,
		/**
		 * 学校团委领导
		 */
		HKY_SCHOOL_LEAGUE_LEADER,
		/**
		 * 教师
		 */
		HKY_TEACHER
	}
	
	public static enum OPERATOR_FLAG{
		/**
		 * 加法运算
		 */
		PLUS,
		/**
		 * 减法运算
		 */
		MINUS
		
	}

}
