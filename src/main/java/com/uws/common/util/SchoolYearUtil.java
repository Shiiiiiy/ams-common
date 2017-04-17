package com.uws.common.util;

import java.util.List;

import com.uws.common.service.ICommonConfigService;
import com.uws.core.util.DateUtil;
import com.uws.core.util.SpringBeanLocator;
import com.uws.domain.config.TermConfigModel;
import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

/**
 * 
 * @ClassName: SchoolYearUtil
 * @Description: TODO(对学年的转换)
 * @author wangcl
 * @date 2015-08-10 12:46:48
 * 
 */
public class SchoolYearUtil {

	// 数据字典帮助对象
	public static DicUtil dicUtil = DicFactory.getDicUtil();
	
	/**
	 * 根据年份（2014）转换成学年
	 * @param value
	 * @return
	 */
	public static Dic yearToSchool(String year){
		//学年数据字典List
		List<Dic> dicYear = dicUtil.getDicInfoList("YEAR");
	        for (Dic dic : dicYear){
	          if (year.equals(dic.getCode())) {
	            return dic;
	         }
	        }
	        return null;  
	}
	
	/**
	 * 
	* @Title: Constants.java 
	* @Package com.uws.sponsor.util 
	* @Description: 获取当前学年数据字典
	* @author liuchen
	* @date 2015-8-7 下午3:51:50
	 */
	public static Dic getYearDic(){
		TermConfigModel termModel = getCurrentTermConfig();
		if(null!=termModel)
			return termModel.getYearDic();
		else
			return  dicUtil.getDicInfo("YEAR", String.valueOf(DateUtil.getCurYear()));
	}
	
	/**
	 * 
	 * @Title: getCurrentTermDic
	 * @Description: 获取当前学期数据字典
	 * @return
	 * @throws
	 */
	public static Dic getCurrentTermDic(){
		TermConfigModel termModel = getCurrentTermConfig();
		if(null!=termModel)
			return termModel.getTermDic();
		else
			return null;
	}
	
	/**
	 * 
	 * @Title: getCurrentTermConfig
	 * @Description:判断当前学期配置
	 * @return
	 * @throws
	 */
	public static TermConfigModel getCurrentTermConfig()
	{
		ICommonConfigService commonService = (ICommonConfigService)SpringBeanLocator.getBean("com.uws.common.service.impl.CommonConfigServiceImpl");
		return commonService.getCurrentTermConfig();
	}
	
}
