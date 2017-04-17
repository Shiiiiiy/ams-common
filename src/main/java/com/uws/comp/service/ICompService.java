package com.uws.comp.service;

import java.util.List;

import com.uws.core.base.IBaseService;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;

/**
 * 
* @ClassName: ICompService 
* @Description: 组件封装service接口
* @author 联合永道
* @date 2015-7-30 下午3:05:11 
*
 */
public interface ICompService extends IBaseService
{
	/**
	 * 
	 * @Title: queryMajorByCollage
	 * @Description: 根据学院查询专业
	 * @param collageId
	 * @return
	 * @throws
	 */
	public List<BaseMajorModel> queryMajorByCollage( String collageId );
	
	/**
	 * 
	 * @Title: queryClassByCollage
	 * @Description: 专业下的班级
	 * @param classId
	 * @return
	 * @throws
	 */
	public List<BaseClassModel> queryClassByMajor( String majorId );
	/**
	 * 
	 * @Title: ICompService.java 
	 * @Package com.uws.comp.service 
	 * @Description:检查宿舍是否合格，true合格，false不合格
	 * @author LiuChen 
	 * @date 2016-11-25 下午2:05:51
	 */
	public boolean checkDormIsGood(String studentId);
}
