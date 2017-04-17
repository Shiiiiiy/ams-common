package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.domain.config.TermConfigModel;
import com.uws.sys.model.Dic;

/**
 * 
* @ClassName: ITermConfigDao 
* @Description:学期设置DAO
* @author 联合永道
* @date 2015-8-28 上午10:30:07 
*
 */
public interface ITermConfigDao extends IBaseDao
{
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
	 * @Title: getCurrentTermConfig
	 * @Description: 判断当前学期配置
	 * @return
	 * @throws
	 */
	public TermConfigModel getCurrentTermConfig();
}
