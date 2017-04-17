package com.uws.comp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.comp.dao.ICompDao;
import com.uws.comp.service.ICompService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;
import com.uws.domain.base.StudentRoomModel;
import com.uws.domain.dorm.DormRatingView;

/**
 * 
* @ClassName: CompServiceImpl 
* @author 联合永道
* @date 2015-7-30 下午3:06:15 
*
 */
@Service("com.uws.comp.service.impl.CompServiceImpl")
public class CompServiceImpl extends BaseServiceImpl implements ICompService
{
	@Autowired
	private ICompDao compDao;

	/**
	 * 描述信息: 按照学院查找专业
	 * @param collageId
	 * @return
	 * @see com.uws.comp.service.ICompService#queryMajorByCollage(java.lang.String)
	 */
	@Override
    public List<BaseMajorModel> queryMajorByCollage(String collageId)
    {
	    return compDao.queryMajorByCollage(collageId);
    }

	/**
	 * 描述信息: 专业下所有的班级
	 * @param majorId
	 * @return
	 * @see com.uws.comp.service.ICompService#queryClassByMajor(java.lang.String)
	 */
	@Override
    public List<BaseClassModel> queryClassByMajor(String majorId)
    {
		return compDao.queryClassByMajor(majorId);
    }
	/**
	 * 
	 * @Description: 宿舍评比信息查询
	 * @author LiuChen  
	 * @date 2016-11-25 下午2:19:24
	 */
	public boolean checkDormIsGood(String studentId)
    {
		StudentRoomModel studentRoom= compDao.queryStudentRoomByStuId(studentId);
		boolean flag = true;
		if(studentRoom !=null && studentRoom.getRoom()!=null){
			List<DormRatingView> dormRatingViewList = compDao.querydormRatingByRoomId(studentRoom.getRoom());
		    if(dormRatingViewList != null && dormRatingViewList.size()>0){
		    	DormRatingView dormRatingView = dormRatingViewList.get(0);
		    	if(dormRatingView != null && dormRatingView.getIsBedRoom().equals("否")){//是否较差宿舍，是就是差宿舍，否是合格宿舍
		    		flag = true;
		    	}else{
		    		flag = false;
		    	}
		    }
		}
	    return flag;
    }
}
