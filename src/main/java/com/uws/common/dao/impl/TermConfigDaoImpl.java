package com.uws.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.ITermConfigDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.util.DateUtil;
import com.uws.domain.config.TermConfigModel;
import com.uws.sys.model.Dic;

/**
 * 
* @ClassName: TermConfigDaoImpl 
* @Description: 学期设置DAO实现
* @author 联合永道
* @date 2015-8-28 上午10:33:44 
*
 */
@Repository("com.uws.common.dao.impl.TermConfigDaoImpl")
public class TermConfigDaoImpl extends BaseDaoImpl implements ITermConfigDao
{

	/**
	 * 描述信息: 查询 学期设置的列表
	 * @return
	 * @see com.uws.common.dao.ITermConfigDao#listTermConfigList()
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<TermConfigModel> listTermConfigList()
    {
		StringBuffer hql = new StringBuffer(" from TermConfigModel order by yearDic desc , termDic desc");
		return this.query(hql.toString());
    }

	/**
	 * 描述信息: 按照学年学期查询设置信息
	 * @param yearDic
	 * @param termDic
	 * @return
	 * @see com.uws.common.dao.ITermConfigDao#queryTermConfigList(com.uws.sys.model.Dic, com.uws.sys.model.Dic)
	 */
	@SuppressWarnings("unchecked")
    @Override
    public List<TermConfigModel> queryTermConfigList(Dic yearDic, Dic termDic)
    {
		StringBuffer hql = new StringBuffer(" from TermConfigModel where yearDic = ? and termDic = ? ");
		return this.query(hql.toString(),new Object[]{yearDic,termDic});
    }

	/**
	 * 描述信息: 判断当前学期配置
	 * @return
	 * @see com.uws.common.dao.ITermConfigDao#getCurrentTermConfig()
	 */
	@Override
	@SuppressWarnings("unchecked")
    public TermConfigModel getCurrentTermConfig()
    {
		StringBuffer hql = new StringBuffer(" from TermConfigModel where beginDate <= ?  and endDate >= ?");
		List<TermConfigModel> list = this.query(hql.toString(),new Object[]{DateUtil.getDate(),DateUtil.getDate()});
		int len = list == null ? 0 :list.size();
		if(len>0)
			return list.get(0);
		return null;
    }

}
