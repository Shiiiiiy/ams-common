package com.uws.comp.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;
import com.uws.domain.base.BaseRoomModel;
import com.uws.domain.base.StudentRoomModel;
import com.uws.domain.dorm.DormRatingView;

/**
 * 
* @ClassName: ICompDao 
* @Description: 公用组件封装
* @author 联合永道
* @date 2015-7-30 下午3:07:00 
*
 */
public interface ICompDao extends IBaseDao
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
     * @Title: ICompDao.java 
     * @Package com.uws.comp.dao 
     * @Description:根据学号查询入住信息
     * @author LiuChen 
     * @date 2016-11-25 下午2:29:52
     */
	public StudentRoomModel queryStudentRoomByStuId(String studentId);
    /**
     * 
     * @Title: ICompDao.java 
     * @Package com.uws.comp.dao 
     * @Description:判断是否是合格寝室
     * @author LiuChen 
     * @date 2016-11-25 下午2:34:36
     */
	public List<DormRatingView> querydormRatingByRoomId(BaseRoomModel room);
}
