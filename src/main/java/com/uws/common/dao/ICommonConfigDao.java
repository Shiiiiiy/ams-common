package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.config.TimeConfigModel;

/**
 * 
* @ClassName: ICommonConfigDao 
* @Description: 通用设置DAO接口定义
* @author 联合永道
* @date 2015-8-13 上午9:41:47 
*
 */
public interface ICommonConfigDao extends IBaseDao
{
	/**
	 * 
	 * @Title: listTimeConfigPage
	 * @Description: 查询时间设置列表
	 * @param pageSize
	 * @param pageNo
	 * @param configModel
	 * @return
	 * @throws
	 */
	public Page listTimeConfigPage(Integer pageSize , Integer pageNo , TimeConfigModel configModel);
	
	/**
	 * 
	 * @Title: findByCondition
	 * @Description:提供接口， 按照 CODE 查找对应时间设置
	 * @param code
	 * @return
	 * @throws
	 */
	public TimeConfigModel findByCondition( String code );
	
	/**
	 * 
	 * @Title: findByCondition
	 * @Description: 提供接口，多个code值返回时间设置列表
	 * @param codes
	 * @return
	 * @throws
	 */
	public List<TimeConfigModel> findByCondition( String[] codes);
}
