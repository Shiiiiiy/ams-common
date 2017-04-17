package com.uws.comp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.uws.comp.dao.ICompDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;
import com.uws.domain.base.BaseRoomModel;
import com.uws.domain.base.StudentRoomModel;
import com.uws.domain.dorm.DormRatingView;

/**
 * 
* @ClassName: CompDaoImpl 
* @author 联合永道
* @date 2015-7-30 下午3:07:56 
*
 */
@Repository("com.uws.comp.dao.impl.CompDaoImpl")
public class CompDaoImpl extends BaseDaoImpl implements ICompDao
{

	/**
	 * 描述信息: 按照学院查找专业
	 * @param collageId
	 * @return
	 * @see com.uws.comp.dao.ICompDao#queryMajorByCollage(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<BaseMajorModel> queryMajorByCollage(String collageId)
    {
		if( StringUtils.hasText(collageId))
			return this.query("from BaseMajorModel where 1=1 and collage.id = ?  ",new Object[]{collageId});
		return null;
    }

	
	/**
	 * 描述信息: 专业下所有的班级
	 * @param majorId
	 * @return
	 * @see com.uws.comp.service.ICompService#queryClassByMajor(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<BaseClassModel> queryClassByMajor(String majorId)
    {
		if( StringUtils.hasText(majorId))
			return this.query("from BaseClassModel where 1=1 and major.id = ?  ",new Object[]{majorId});
		return null;
    }
	/**
	 * 
	 * @Description: 查询学生宿舍入住信息
	 * @author LiuChen  
	 * @date 2016-11-25 下午2:35:17
	 */
	@Override
	public StudentRoomModel queryStudentRoomByStuId(String studentId)
	{
		 return (StudentRoomModel) this.queryUnique(" from StudentRoomModel s where s.student.id=?", new Object[]{studentId});
	}
	/**
	 * 
	 * @Description:校验宿舍是否是合格宿舍
	 * @author LiuChen  
	 * @date 2016-11-25 下午2:36:05
	 */
	@Override
	public List<DormRatingView> querydormRatingByRoomId(BaseRoomModel room)
	{
		if(StringUtils.hasText(room.getId()))
			return this.query(" from DormRatingView where room.id=? order by checkTime desc", new Object[]{room.getId()});
		return null;
	}
	
}
