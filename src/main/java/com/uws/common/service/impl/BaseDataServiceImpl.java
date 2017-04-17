package com.uws.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.uws.common.dao.IBaseDataDao;
import com.uws.common.service.IBaseDataService;
import com.uws.common.util.SchoolYearUtil;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseBuildingModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;
import com.uws.domain.base.BaseRoomModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.base.JsonModel;
import com.uws.domain.base.StudentRoomModel;
import com.uws.sys.model.Dic;

/**
 * 
* @ClassName: BaseDataServiceImpl 
* @Description: 基础数据 Service 方法实现
* @author 联合永道
* @date 2015-7-13 下午2:28:19 
*
 */
@Service("com.uws.common.service.impl.BaseDataServiceImpl")
public class BaseDataServiceImpl extends BaseServiceImpl implements IBaseDataService
{

	@Autowired
	private IBaseDataDao baseDataDao;
	
	/**
	 * 描述信息: 基础信息 专业查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseMajor
	 * @return
	 * @see com.uws.common.service.IBaseDataService#listBaseMajorPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseMajorModel)
	 */
	@Override
    public Page listBaseMajorPage(Integer pageSize, Integer pageNum,BaseMajorModel baseMajor)
    {
	    return baseDataDao.listBaseMajorPage(pageSize, pageNum, baseMajor);
    }

	/**
	 * 描述信息: 基础信息 班级查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseClass
	 * @return
	 * @see com.uws.common.service.IBaseDataService#listBaseClassPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseClassModel)
	 */
	@Override
    public Page listBaseClassPage(Integer pageSize, Integer pageNum, BaseClassModel baseClass)
    {
		 return baseDataDao.listBaseClassPage(pageSize, pageNum, baseClass);
    }
	
	/**
	 * 描述信息: 基础信息 教职工查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseTecher
	 * @return
	 * @see com.uws.common.service.IBaseDataService#listBaseTeahcerPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseTeacherModel)
	 */
	@Override
	public Page listBaseTeacherPage(Integer pageSize , Integer pageNum , BaseTeacherModel baseTecher)
	{
		 return baseDataDao.listBaseTeacherPage(pageSize, pageNum, baseTecher);
	}
	/**
	 * 描述信息: 查询所有的学院信息
	 * @return
	 * @see com.uws.common.service.IBaseDataService#listBaseAcademy()
	 */
	@Override
	//@Cacheable(value={"com.uws.common.service.impl.BaseDataServiceImpl"}, key="#code + 'listBaseAcademy'")
    public List<BaseAcademyModel> listBaseAcademy()
    {
	    return baseDataDao.listBaseAcademy();
    }

	/**
	 * 描述信息: 根据编号查询学院
	 * @param collegeId
	 * @return
	 * @see com.uws.common.service.IBaseDataService#findAcademyById(java.lang.String)
	 */
	@Override
    public BaseAcademyModel findAcademyById(String collegeId)
    {
		if(StringUtils.hasText(collegeId))
			return (BaseAcademyModel) baseDataDao.get(BaseAcademyModel.class, collegeId);
	    return null;
    }

	/**
	 * 描述信息: 根据编号查询专业
	 * @param majorId
	 * @return
	 * @see com.uws.common.service.IBaseDataService#findMajorById(java.lang.String)
	 */
	@Override
    public BaseMajorModel findMajorById(String majorId)
    {
		if(StringUtils.hasText(majorId))
			return (BaseMajorModel) baseDataDao.get(BaseMajorModel.class, majorId);
	    return null;
    }

	/**
	 * 描述信息:根据编号查询班级
	 * @param classId
	 * @return
	 * @see com.uws.common.service.IBaseDataService#findClassById(java.lang.String)
	 */
	@Override
    public BaseClassModel findClassById(String classId)
    {
		if(StringUtils.hasText(classId))
			return (BaseClassModel) baseDataDao.get(BaseClassModel.class, classId);
	    return null;
    }

	/**
	 * 
	 * @Title: listBaseAcademy
	 * @Description: 查询所有的班级信息 
	 * @param grade
	 * @param academyId
	 * @param majorId
	 * @return
	 * @throws
	 */
	@Override
    public List<BaseClassModel> listBaseClass(String grade, String majorId,String academyId)
    {
		return  baseDataDao.listBaseClass(grade,majorId,academyId);
    }

	/**
	 * 描述信息: 根据编号查询教职工
	 * @param teacherId
	 * @return BaseTeacherModel
	 * @see com.uws.common.service.IBaseDataService#findTeacherById(java.lang.String)
	 */
	@Override
    public BaseTeacherModel findTeacherById(String teacherId)
    {
	    if(StringUtils.hasText(teacherId))
	    	return (BaseTeacherModel) baseDataDao.get(BaseTeacherModel.class, teacherId);
	    return null;
    }
	
	/**
	 * 描述信息: 根据编号查询教职工
	 * @param teacherId
	 * @return BaseTeacherModel
	 * @see com.uws.common.service.IBaseDataService#findTeacherById(java.lang.String)
	 */
	@Override
    public BaseTeacherModel findTeacherByCode(String teacherCode)
    {
	    if(StringUtils.hasText(teacherCode))
	    	return (BaseTeacherModel) baseDataDao.findTeacherByCode(teacherCode);
	    return null;
    }
	

	/**
	 * 描述信息: 所有专业信息
	 * @return
	 * @see com.uws.common.service.IBaseDataService#getAllMajor()
	 */
	@Override
	//@Cacheable(value={"com.uws.common.service.impl.BaseDataServiceImpl"}, key="#code + 'getAllMajor'")
	public List<BaseMajorModel> getAllMajor() 
	{
		return baseDataDao.getAllMajor();
	}

	/**
	 * 描述信息: 根据ID学生住宿信息查询
	 * @param studentId
	 * @return
	 * @see com.uws.common.service.IBaseDataService#findRoomByStudentId(java.lang.String)
	 */
	@Override
    public StudentRoomModel findRoomByStudentId(String studentId)
    {
		if(StringUtils.hasText(studentId))
			return baseDataDao.findRoomByStudentId(studentId);
		return null;
    }

	/**
	 * 描述信息: 根据学号学生住宿信息查询
	 * @param studentNumber
	 * @return
	 * @see com.uws.common.service.IBaseDataService#findRoomByStudentNumber(java.lang.String)
	 */
	@Override
    public StudentRoomModel findRoomByStudentNumber(String studentNumber)
    {
		if(StringUtils.hasText(studentNumber))
			return baseDataDao.findRoomByStudentNumber(studentNumber);
		return null;
    }
	
	/**
	 * 
	 * @Description: 根据身份证学生住宿信息查询
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentRoomModel findRoomByCardID(String cardID){
		if(StringUtils.hasText(cardID))
			return baseDataDao.findRoomByCardID(cardID);
		return null;
	}

	/**
	 * 描述信息: 按宿舍查询学生住宿信息
	 * @param roomId
	 * @return
	 * @see com.uws.common.service.IBaseDataService#findRoomByRoomId(java.lang.String)
	 */
	@Override
    public List<StudentRoomModel> findRoomByRoomId(String roomId)
    {
		if(StringUtils.hasText(roomId))
			return baseDataDao.findRoomByRoomId(roomId);
		return null;
    }

	/**
	 * 描述信息:  查询宿舍成员信息排除当前学生
	 * @param roomId
	 * @param notExistStudentId
	 * @return
	 * @see com.uws.common.service.IBaseDataService#findRoomByRoomId(java.lang.String, java.lang.String)
	 */
	@Override
    public List<StudentRoomModel> findRoomByRoomId(String roomId, String notExistStudentId)
    {
		return baseDataDao.findRoomByRoomId(roomId,notExistStudentId);
    }

	/**
	 * 描述信息: 学院教师查询
	 * @param pageNo
	 * @param pageSize
	 * @param teacher
	 * @return
	 * @see com.uws.common.service.IStudentCommonService#queryCollegeTeacherPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseTeacherModel)
	 */
	@Override
    public Page queryCollegeTeacherPage(Integer pageNo, Integer pageSize,
            BaseTeacherModel teacher)
    {
	   return baseDataDao.queryCollegeTeacherPage( pageNo,pageSize,teacher);
    }

	/**
	 * 描述信息: 年级信息获取
	 * @return
	 * @see com.uws.common.service.IBaseDataService#listGradeList()
	 */
	@Override
    public List<String> listGradeList()
    {
		return baseDataDao.listGradeList();
    }
	
	/**
	 * 
	 * @Title: listGradeList
	 * @Description: 毕业年份信息 封装返回调用 离校模块调用
	 * @return
	 * @throws
	 */
	public List<String> listLeaveYearList()
	{
		return baseDataDao.listLeaveYearList();
	}

	/**
	 * 描述信息: 毕业班的状态更新
	 * @param classId
	 * @param gruadtedDic
	 * 2015-12-7 上午11:26:14
	 */
	@Override
    public void updateGraduated(String[] classId, Dic gruadtedDic)
    {
		if(!ArrayUtils.isEmpty(classId))
		{
			BaseClassModel baseClass = null;
			for(String id : classId)
			{
				baseClass = (BaseClassModel) baseDataDao.get(BaseClassModel.class, id);
				if(null == baseClass.getIsGraduatedDic() || !baseClass.getIsGraduatedDic().getId().equals(gruadtedDic.getId()))
				{
					baseClass.setIsGraduatedDic(gruadtedDic);
					baseClass.setGraduatedYearDic(SchoolYearUtil.yearToSchool(Integer.parseInt(baseClass.getMajor().getSchoolYear())+Integer.parseInt(baseClass.getGrade())-1+""));
					baseDataDao.update(baseClass);
				}
			}
		}
    }
	
	/**
	 * 描述信息: 取消毕业班标记
	 * @param classId
	 * @param gruadtedDic
	 * 2015-12-15 下午3:44:03
	 */
	@Override
    public void cancelClassGraduate(String[] classId, Dic gruadtedDic)
    {
		if(!ArrayUtils.isEmpty(classId))
			baseDataDao.updateGraduated(classId,gruadtedDic);
    }
	
	/**
	 * 描述信息: 基础信息 班级管理
	 * @param pageSize
	 * @param pageNum
	 * @param baseClass
	 * @return
	 * @see com.uws.common.service.IBaseDataService#listBaseClassPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseClassModel)
	 */
	@Override
    public Page listManageClassPage(Integer pageSize, Integer pageNum, BaseClassModel baseClass)
    {
		 return baseDataDao.listManageClassPage(pageSize, pageNum, baseClass);
    }

	/**
	 * 描述信息: 根据班级查询班级学生人数
	 * @param classId
	 * @return
	 * 2015-12-15 下午4:17:38
	 */
	@Override
    public Integer countStudentByClass(String classId)
    {
		if(StringUtils.hasText(classId))
			return baseDataDao.countStudentByClass(classId);
		
		return 0;
    }
	/**
	 * 
	 * @Description: 根据楼栋id查询对象
	 * @author LiuChen  
	 * @date 2016-11-3 上午9:58:56
	 */
	@Override
	public BaseBuildingModel getDormBuildingById(String key)
	{
	    return (BaseBuildingModel) this.baseDataDao.get(BaseBuildingModel.class, key);
	}
	
	@Override
	public String getdormFloorByBuildId(String key)
	{
	    return this.baseDataDao.getdormFloorByBuildId(key);
	}
	/**
	 * 
	 * @Description:根据楼栋和楼层查询宿舍
	 * @author LiuChen  
	 * @date 2016-11-3 上午10:37:15
	 */
	@Override
	public List<BaseRoomModel> queryDormNoByFloor(String dormBuildingId,String dormFloor)
	{
	    return this.baseDataDao.queryDormNoByFloor(dormBuildingId, dormFloor);
	}
	/**
	 * 
	 * @Description:查询楼层
	 * @author LiuChen  
	 * @date 2016-11-3 上午10:52:22
	 */
	@Override
	public List<JsonModel> queryFloorList(String floofNum)
	{
		List<JsonModel> floorList = new ArrayList<JsonModel>();
		if(floofNum != null && StringUtils.hasText(floofNum)){
			int floorCount = Integer.parseInt(floofNum);
			JsonModel json = null;
			for (int i = 1; i <= floorCount; i++){
				json = new JsonModel();
				json.setId(i+"");
				json.setName(i+"");
				floorList.add(json);
			}
		}
		return floorList;
	}
	
	
}
