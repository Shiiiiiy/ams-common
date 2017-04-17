package com.uws.common.service.impl;

import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.uws.common.dao.ICommonConfigDao;
import com.uws.common.dao.ITermConfigDao;
import com.uws.common.service.ICommonConfigService;
import com.uws.common.util.Constants;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.DateUtil;
import com.uws.domain.config.TermConfigModel;
import com.uws.domain.config.TimeConfigModel;
import com.uws.sys.dao.ISysConfigDao;
import com.uws.sys.model.Dic;
import com.uws.sys.model.SysConfig;

/**
 * 
 * @ClassName: CommonConfigServiceImpl
 * @Description: 通用设置Service接口实现
 * @author 联合永道
 * @date 2015-8-13 上午9:44:23
 * 
 */
@Service("com.uws.common.service.impl.CommonConfigServiceImpl")
public class CommonConfigServiceImpl extends BaseServiceImpl implements ICommonConfigService
{
	@Autowired
	private ICommonConfigDao commonConfigDao;
	@Autowired
	private ISysConfigDao sysConfigDao;
	@Autowired
	private ITermConfigDao termConfigDao;

	/**
	 * 描述信息: 查询时间设置列表
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param configModel
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#listTimeConfigPage(java.lang.Integer,
	 *      java.lang.Integer, com.uws.domain.config.TimeConfigModel)
	 */
	@Override
	public Page listTimeConfigPage(Integer pageSize, Integer pageNo,TimeConfigModel configModel)
	{
		return commonConfigDao.listTimeConfigPage(pageSize, pageNo, configModel);
	}

	/**
	 * 描述信息: 按主键查找
	 * 
	 * @param id
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#getById(java.lang.String)
	 */
	@Override
	public TimeConfigModel getById(String id)
	{
		if (StringUtils.hasText(id))
			return (TimeConfigModel) commonConfigDao.get(TimeConfigModel.class,id);
		return null;
	}

	/**
	 * 描述信息: 保存
	 * 
	 * @param timeConfigModel
	 * @see com.uws.common.service.ICommonConfigService#saveTimeConfig(com.uws.domain.config.TimeConfigModel)
	 */
	@Override
	public void saveTimeConfig(TimeConfigModel timeConfigModel)
	{
		if (null != timeConfigModel)
			commonConfigDao.save(timeConfigModel);
	}

	/**
	 * 描述信息: 更新
	 * 
	 * @param timeConfigModel
	 * @see com.uws.common.service.ICommonConfigService#upadteTimeConfig(com.uws.domain.config.TimeConfigModel)
	 */
	@Override
	public void updateTimeConfig(TimeConfigModel timeConfigModel)
	{
		if (null != timeConfigModel)
			commonConfigDao.update(timeConfigModel);
	}

	/**
	 * 描述信息: 物理删除 
	 * @param id
	 * @see com.uws.common.service.ICommonConfigService#deleteById(java.lang.String)
	 */
	@Override
    public void deleteById(String id)
    {
	   if(StringUtils.hasText(id))
		   commonConfigDao.deleteById(TimeConfigModel.class, id);
    }

	/**
	 * 描述信息: 学期设置的列表集合
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#listTermConfigList()
	 */
	@Override
    public List<TermConfigModel> listTermConfigList()
    {
		return termConfigDao.listTermConfigList();
    }
	

	/**
	 * 描述信息:  学期设置的按照ID查找
	 * @param id
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#getTermConfigById(java.lang.String)
	 */
	@Override
    public TermConfigModel getTermConfigById(String id)
    {
		if(StringUtils.hasText(id))
			return (TermConfigModel) termConfigDao.get(TermConfigModel.class, id);
		
		return null;
    }
	
	/**
	 * 描述信息: 学期设置保存或更新
	 * @param termConfig
	 * @see com.uws.common.service.ICommonConfigService#saveOrUpdateTermConfig(com.uws.domain.config.TermConfigModel)
	 */
	@Override
	public void saveOrUpdateTermConfig(TermConfigModel termConfig)
	{
		if(null!=termConfig)
		{
			if(StringUtils.hasText(termConfig.getId()))
				termConfigDao.update(termConfig);	
			else
				termConfigDao.save(termConfig);	
		}
	}
	
	/**
	 * 描述信息: 根据学年学期查询学期时间设置
	 * @param yearDic
	 * @param termDic
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#queryTermConfigList(com.uws.sys.model.Dic, com.uws.sys.model.Dic)
	 */
	@Override
    public List<TermConfigModel> queryTermConfigList(Dic yearDic, Dic termDic)
    {
		return termConfigDao.queryTermConfigList(yearDic,termDic);	
    }

	
	/**
	 * 描述信息: 接口提供 按照CODE 查询配置
	 * @param code
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#findByCode(java.lang.String)
	 */
	@Override
    public TimeConfigModel findByCondition(String code)
    {
	    return commonConfigDao.findByCondition(code);
    }
	/**
	 * 描述信息: 提供接口，多个code值返回时间设置列表
	 * @param code
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#findByCode(java.lang.String)
	 */
	@Override
    public List<TimeConfigModel> findByCondition(String[] codes)
    {
		 return commonConfigDao.findByCondition(codes);
    }
	
	/**
	 * 
	 * @Title: checkCurrentDateByCode
	 * @Description: 对外接口 判断当前日期是否在定义的时间范围内
	 * @param code
	 * @return
	 * @throws
	 */
	@Override
	public boolean checkCurrentDateByCode(String code)
	{
		TimeConfigModel configModel = commonConfigDao.findByCondition(code);
		if(null!=configModel)
		{
			return configModel.getBeginDate().compareTo(DateUtil.getDate()) <=0 
					&& DateUtils.addDays(configModel.getEndDate(), 1).compareTo(DateUtil.getDate())>=0;
		}
		return false;
	}

	/**
	 * 描述信息: 勤工助学薪资
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#getStudyWorkSalary()
	 */
	@Override
    public String getStudyWorkSalary()
    {
		SysConfig sysConfig = sysConfigDao.getSysConfig(Constants.COMMON_SYSTEM_CONFIG_ENUM.STUDY_WORK_SALARY.toString());
		if(null!=sysConfig)
			return sysConfig.getValue();
	    return "";
    }

	/**
	 * 描述信息: 临时岗位薪资
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#getTempWorkSalary()
	 */
	@Override
    public String getTempWorkSalary()
    {
		SysConfig sysConfig = sysConfigDao.getSysConfig(Constants.COMMON_SYSTEM_CONFIG_ENUM.TEMP_WORK_SALARY.toString());
		if(null!=sysConfig)
			return sysConfig.getValue();
	    return "";
    }


	/**
	 * 描述信息: 奖助申请时间设置编码 对外接口
	 * @return
	 * 2016-1-6 上午11:03:25
	 */
	@Override
    public String getSponsorAwardTimeConfigCode()
    {
		SysConfig sysConfig = sysConfigDao.getSysConfig(Constants.SPONSOR_AWARD_TIME_CONFIG_CODE);
		if(null!=sysConfig)
			return sysConfig.getValue();
	    return "";
    }
	
	/**
	 * 描述信息: 判断当前学期配置
	 * @return
	 * @see com.uws.common.service.ICommonConfigService#getCurrentTermConfig()
	 */
	@Override
    public TermConfigModel getCurrentTermConfig()
    {
		return termConfigDao.getCurrentTermConfig();	
    }

}
