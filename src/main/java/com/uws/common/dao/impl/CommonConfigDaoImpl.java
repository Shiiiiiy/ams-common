package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.uws.common.dao.ICommonConfigDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.HqlEscapeUtil;
import com.uws.domain.config.TimeConfigModel;

/**
 * 
* @ClassName: CommonConfigDaoImpl 
* @Description:  通用设置DAO接口实现
* @author 联合永道
* @date 2015-8-13 上午9:42:42 
*
 */
@Repository("com.uws.common.dao.impl.CommonConfigDaoImpl")
public class CommonConfigDaoImpl extends BaseDaoImpl implements ICommonConfigDao
{

	/**
	 * 描述信息: 时间设置分页查询
	 * @param pageSize
	 * @param pageNo
	 * @param configModel
	 * @return
	 * @see com.uws.common.dao.ICommonConfigDao#listTimeConfigPage(java.lang.Integer, java.lang.Integer, com.uws.domain.config.TimeConfigModel)
	 */
	@Override
    public Page listTimeConfigPage(Integer pageSize, Integer pageNo, TimeConfigModel configModel)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from TimeConfigModel where 1=1 ");
		//名称
		if (!StringUtils.isEmpty(configModel.getName())) 
		{
			hql.append(" and name like ? ");
			if (HqlEscapeUtil.IsNeedEscape(configModel.getName())) 
			{
				values.add("%" + HqlEscapeUtil.escape(configModel.getName()) + "%");
				hql.append(HqlEscapeUtil.HQL_ESCAPE);
			} else
				values.add("%" + configModel.getName() + "%");
		}
		//编码
		if (!StringUtils.isEmpty(configModel.getCode())) 
		{
			hql.append(" and code like ? ");
			if (HqlEscapeUtil.IsNeedEscape(configModel.getCode())) 
			{
				values.add("%" + HqlEscapeUtil.escape(configModel.getCode()) + "%");
				hql.append(HqlEscapeUtil.HQL_ESCAPE);
			} else
				values.add("%" + configModel.getCode() + "%");
		}
		
		// 学院单位  ""为学生处定义， 默认查询出学生
		if ( null != configModel.getCollege() && !StringUtils.isEmpty(configModel.getCollege().getId())) 
		{
			hql.append(" and college = ? ");
			values.add(configModel.getCollege());
		}

		if (values.size() == 0)
			return this.pagedQuery(hql.toString(), pageNo, pageSize);
		else
			return this.pagedQuery(hql.toString(), pageNo, pageSize,values.toArray());
    }

	/**
	 * @Title: findByCondition
	 * @Description:提供接口， 按照 CODE 查找对应时间设置
	 * @param code
	 * @return
	 * @throws
	 */
	@Override
	@SuppressWarnings("unchecked")
    public TimeConfigModel findByCondition(String code)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from TimeConfigModel where 1=1  ");
		if(StringUtils.isEmpty(code))
		{
			hql.append(" and 1=2 ");
		}
		else
		{
			if(!StringUtils.isEmpty(code))
			{
				hql.append(" and code = ? ");
				values.add(code);
			}
		}
		hql.append(" order by updateTime");
		
		List<TimeConfigModel> configList = this.query(hql.toString(), values.toArray());
		int len = configList == null ? 0 : configList.size();
		if(len >0 )
			return configList.get(0);
		
	    return null;
    }

	/**
	 * 描述信息: 提供接口， 按照 CODE 查找对应时间设置List
	 * @param codes
	 * @return
	 * @see com.uws.common.dao.ICommonConfigDao#findByCondition(java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<TimeConfigModel> findByCondition(String[] codes)
    {
		if(!ArrayUtils.isEmpty(codes))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			StringBuffer hql = new StringBuffer("from TimeConfigModel where code in (:codes) ");
			map.put("codes", codes);
			return this.query(hql.toString(), map);
		}
		return null;
    } 
	
}
