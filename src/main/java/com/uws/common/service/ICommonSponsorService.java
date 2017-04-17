package com.uws.common.service;

import java.util.List;

import com.uws.core.base.IBaseService;
import com.uws.domain.sponsor.DifficultStudentInfo;

/**
 * 
 * @ClassName: ICommonSponsorService 
 * @Description: 资助困难生Service 接口定义
 * @author 联合永道
 * @date 2015-12-3 下午18:08:56 
 *
 */
public interface ICommonSponsorService  extends IBaseService 
{   
	/**
	* 
	* @Title: ICommonSponsorService.java 
	* @Package com.uws.common.service 
	* @Description: 根据学号查询所有的困难生
	* @author LiuChen 
	* @date 2015-12-3 下午6:28:15
	 */
	public List<DifficultStudentInfo> queryDifficultStudentList(String stuNumber);

}
