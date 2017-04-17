package com.uws.common.service;

import java.util.List;

import com.uws.core.base.IBaseService;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.config.TermConfigModel;
import com.uws.domain.config.TimeConfigModel;
import com.uws.sys.model.Dic;


/**
 * 
* @ClassName: ICommonConfigService 
* @Description:  通用设置Service接口定义
* @author 联合永道
* @date 2015-8-13 上午9:43:32 
*
 */
public interface ICommonConfigService extends IBaseService
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
	 * @Title: getById
	 * @Description: 按主键查找
	 * @param id
	 * @return
	 * @throws
	 */
	public TimeConfigModel getById(String id);
	
	/**
	 * 
	 * @Title: saveTimeConfig
	 * @Description: 保存
	 * @param timeConfigModel
	 * @throws
	 */
	public void saveTimeConfig(TimeConfigModel timeConfigModel);
	
	
	/**
	 * 
	 * @Title: upadteTimeConfig
	 * @Description: 更新
	 * @param timeConfigModel
	 * @throws
	 */
	public void updateTimeConfig(TimeConfigModel timeConfigModel);
	
	/**
	 * 
	 * @Title: deleteById
	 * @Description: 删除 物理删除
	 * @param id
	 * @throws
	 */
	public void deleteById(String id);
	
	/**
	 * 
	 * @Title: listTermConfigList
	 * @Description: 学期设置的集合
	 * @return
	 * @throws
	 */
	public List<TermConfigModel> listTermConfigList();
	
	/**
	 * 
	 * @Title: getTermConfigById
	 * @Description: 学期设置的按照ID查找
	 * @return
	 * @throws
	 */
	public TermConfigModel getTermConfigById(String id);
	
	/**
	 * 
	 * @Title: saveOrUpdateTermConfig
	 * @Description: 学期设置保存或更新
	 * @return
	 * @throws
	 */
	public void saveOrUpdateTermConfig(TermConfigModel termConfig);
	
	/**
	 * 
	 * @Title: queryTermConfigList
	 * @Description: 按照学年学期查询设置信息
	 * @param yearDic
	 * @param termDic
	 * @return
	 * @throws
	 */
	public List<TermConfigModel> queryTermConfigList(Dic yearDic,Dic termDic);
	
	/**
	 * 
	 * @Title: findByCondition
	 * @Description:提供接口， 按照 CODE 查找对应时间设置
	 * @param code
	 * @return
	 * @throws
	 */
	public TimeConfigModel findByCondition( String code);
	
	/**
	 * 
	 * @Title: findByCondition
	 * @Description: 提供接口，多个code值返回时间设置列表
	 * @param codes
	 * @return
	 * @throws
	 */
	public List<TimeConfigModel> findByCondition( String[] codes);
	
	/**
	 * 
	 * @Title: checkCurrentDateByCode
	 * @Description: 对外接口 判断当前日期是否在定义的时间范围内
	 * @param code
	 * @return
	 * @throws
	 */
	public boolean checkCurrentDateByCode(String code);
	
	/**
	 * 
	 * @Title: getStudyWorkSalary
	 * @Description: 勤工助学岗位信息  对外接口
	 * @return
	 * @throws
	 */
	public String getStudyWorkSalary();
	
	/**
	 * 
	 * @Title: getTempWorkSalary
	 * @Description: 临时 勤工助学岗位信息  对外接口
	 * @return
	 * @throws
	 */
	public String getTempWorkSalary();
	
	/**
	 * 
	 * @Title: getSponsorAwardTimeConfigCode
	 * @Description: 奖助申请时间设置编码 对外接口
	 * @return
	 * @throws
	 */
	public String getSponsorAwardTimeConfigCode();
	
	
	/**
	 * 
	 * @Title: getCurrentTermConfig
	 * @Description: 判断当前学期配置
	 * @return
	 * @throws
	 */
	public TermConfigModel getCurrentTermConfig();
	
}
