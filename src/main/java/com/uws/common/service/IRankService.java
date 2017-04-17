package com.uws.common.service;

import com.uws.core.base.BaseModel;
import com.uws.core.base.IBaseService;

/**
 *	公用排序算法定义
 */
public interface IRankService extends IBaseService {

	/**
	 * 上移对象
	 * @param bm							对象实体
	 * @param rankColumn		排序字段名称
	 * @param rankValue			排序字段当前值
	 * @param fkColumn				外键字段名称
	 * @param fkValue					外键字段当前值
	 */
	public void moveUpObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception ;
	
	/**
	 * 下移对象
	 * @param bm							对象实体
	 * @param rankColumn		排序字段名称
	 * @param rankValue			排序字段当前值
	 * @param fkColumn				外键字段名称
	 * @param fkValue					外键字段当前值
	 */
	public void moveDownObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception ;
	
	/**
	 * 删除当前对象后上移紧后对象
	 * @param bm							对象实体
	 * @param rankColumn		排序字段名称
	 * @param rankValue			排序字段当前值
	 * @param fkColumn				外键字段名称
	 * @param fkValue					外键字段当前值
	 */
	public void deleteCurObject(BaseModel  bm,String rankColumn,int rankValue,String fkColumn,String fkValue) throws Exception ;

	/**
	 * 获取排序边界信息
	 * @param bm								对象实体
	 * @param rankColumn			排序字段名称
	 * @param rankValue				排序字段当前值
	 * @param fkColumn					外键字段名称
	 * @param foreignKeyValue	外键字段当前值
	 * @return										排序边界信息
	 */
	public boolean isMaxSeq(BaseModel  bm, String rankColumn,int rankValue,String fkColumn, String foreignKeyValue);

	/**
	 * 获取排序边界信息
	 * @param bm								对象实体
	 * @param rankColumn			排序字段名称
	 * @param rankValue				排序字段当前值
	 * @param fkColumn					外键字段名称
	 * @param foreignKeyValue	外键字段当前值
	 * @return										排序边界信息
	 */
	public boolean isMinSeq(BaseModel  bm, String rankColumn,int rankValue,String fkColumn, String foreignKeyValue);

}
