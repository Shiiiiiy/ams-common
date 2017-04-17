package com.uws.util; 

import org.springframework.util.StringUtils;

import com.uws.common.service.IBaseDataService;
import com.uws.core.util.SpringBeanLocator;
import com.uws.domain.base.BaseAcademyModel;

/**
 * 
* @ClassName: CheckUtils 
* @Description: 项目级别的检查工具类
* @author 联合永道
* @date 2015-9-22 下午5:51:38 
*
 */
public class CheckUtils
{
	
	/**
	 * 
	 * @Title: isCurrentOrgEqCollege
	 * @Description: 判断当前登录人所在组织机构是不是否是二级学院
	 * @param orgId
	 * @return
	 * @throws
	 */
	public static boolean isCurrentOrgEqCollege(String orgId)
	{
		boolean bol = false;
		IBaseDataService baseDataService = (IBaseDataService)SpringBeanLocator.getBean("com.uws.common.service.impl.BaseDataServiceImpl");
		if(null!=baseDataService && StringUtils.hasText(orgId) )
		{
			BaseAcademyModel college = baseDataService.findAcademyById(orgId);
			if(null!=college && StringUtils.hasText(college.getId()))
				bol = true;
		}
		return bol;
	}
}
