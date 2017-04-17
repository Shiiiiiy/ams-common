package com.uws.common.dao;

import com.uws.core.base.BaseModel;
import com.uws.core.hibernate.dao.IBaseDao;

/**
 *	公用排序算法定义
 */
public interface IRankDao extends IBaseDao {
	
	/**
	 * 上移当前对象
	 * @param bm							对象实体
	 * @param rankColumn		排序字段
	 */
	public void moveUpSelf(BaseModel  bm,String rankColumn);
	
	/**
	 * 下移当前对象
	 * @param bm							对象实体
	 * @param rankColumn		排序字段
	 */
	public void moveDownSelf(BaseModel  bm,String rankColumn);
	
	/**
	 * 上移紧后对象
	 * @param bm							对象实体
	 * @param rankColumn		排序字段名称
	 * @param fkColumn				外键字段名称
	 */
	public void moveUpNextObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception ;
	
	/**
	 * 下移紧前对象
	 * @param bm							对象实体
	 * @param rankColumn		排序字段名称
	 * @param fkColumn				外键字段名称
	 */
	public void moveDownLastObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception ;
	
	/**
	 * 上移紧后的所有对象
	 * @param bm							对象实体
	 * @param rankColumn		排序字段名称
	 * @param fkColumn				外键字段名称
	 */
	public void moveUpAll(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception ;

	/**
	 * 获取排序边界信息
	 * @param bm								对象实体
	 * @param rankColumn			排序字段名称
	 * @param fkColumn					外键字段名称
	 * @param foreignKeyValue	外键字段当前值
	 * @return										排序边界信息
	 */
	public boolean isMaxSeq(BaseModel bm, String rankColumn,int rankValue,String fkColumn,String foreignKeyValue);
	
	/**
	 * 获取排序边界信息
	 * @param bm								对象实体
	 * @param rankColumn			排序字段名称
	 * @param fkColumn					外键字段名称
	 * @param foreignKeyValue	外键字段当前值
	 * @return										排序边界信息
	 */
	public boolean isMinSeq(BaseModel bm, String rankColumn,int rankValue,String fkColumn,String foreignKeyValue);
}
