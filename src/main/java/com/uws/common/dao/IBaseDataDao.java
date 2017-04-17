package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;
import com.uws.domain.base.BaseRoomModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.base.StudentRoomModel;
import com.uws.sys.model.Dic;

/**
 * 
* @ClassName: IBaseDataDao 
* @Description: 基础信息DAO接口
* @author 联合永道
* @date 2015-7-13 下午3:28:47 
*
 */
public interface IBaseDataDao extends IBaseDao
{
	/**
	 * 
	 * @Title: listBaseMajorPage
	 * @Description: 基础信息 专业查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseMajor
	 * @return
	 * @throws
	 */
	public Page listBaseMajorPage(Integer pageSize , Integer pageNum , BaseMajorModel baseMajor);
	
	/**
	 * 
	 * @Title: listBaseMajorPage
	 * @Description: 基础信息 班级查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseMajor
	 * @return
	 * @throws
	 */
	public Page listBaseClassPage(Integer pageSize , Integer pageNum , BaseClassModel baseClass);
	
	
	/**
	 * 
	 * @Title: listBaseMajorPage
	 * @Description: 基础信息 教职工查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseMajor
	 * @return
	 * @throws
	 */
	public Page listBaseTeacherPage(Integer pageSize , Integer pageNum , BaseTeacherModel baseTecher);
	
	/**
	 * 
	 * @Title: listBaseAcademy
	 * @Description: 查询所有的学院信息 
	 * 
	 * @return
	 * @throws
	 */
	public List<BaseAcademyModel> listBaseAcademy();
	
	/**
	 * 
	 * @Title: listBaseAcademy
	 * @Description: 查询所有的班级信息 
	 * @param grade
	 * @param academyId
	 * @param academyId
	 * @return
	 * @throws
	 */
	public List<BaseClassModel> listBaseClass(String grade,String majorId,String academyId);

	/**
	 * 
	 * @Title: getAllMajor
	 * @Description: 查询所有专业信息
	 * @return
	 * @throws
	 */
	public List<BaseMajorModel> getAllMajor();
	
	/**
	 * 
	 * @Title: findTeacherByCode
	 * @Description: 根据职工号查询教职工
	 * @param teacherCode
	 * @return BaseTeacherModel
	 * @throws
	 */
	public BaseTeacherModel findTeacherByCode(String teacherCode);
	
	
	/**
	 * 
	 * @Title: findRoomByStudentId
	 * @Description: 根据ID学生住宿信息查询
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentRoomModel findRoomByStudentId(String studentId);
	
	/**
	 * 
	 * @Title: findRoomByStudentNumber
	 * @Description: 根据学号学生住宿信息查询
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentRoomModel findRoomByStudentNumber(String studentNumber);
	
	/**
	 * 
	 * @Description: 根据身份证学生住宿信息查询
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentRoomModel findRoomByCardID(String cardID);
	
	/**
	 * 
	 * @Title: findRoomByRoomId
	 * @Description: 按宿舍查询学生住宿信息
	 * @param roomId
	 * @return
	 * @throws
	 */
	public List<StudentRoomModel> findRoomByRoomId(String roomId);
	
	/**
	 * 
	 * @Title: findRoomByRoomId
	 * @Description: 查询宿舍成员信息排除当前学生
	 * @param roomId
	 * @param notExistStudentId
	 * @return
	 * @throws
	 */
	public List<StudentRoomModel> findRoomByRoomId(String roomId,String notExistStudentId);
	
	/**
	 * 
	 * @Title: queryCollegeTeacherPage
	 * @Description: 学院教师分页查询 
	 * @param pageNo
	 * @param pageSize
	 * @param teacher
	 * @return
	 * @throws
	 */
	public Page queryCollegeTeacherPage(Integer pageNo,Integer pageSize, BaseTeacherModel teacher);
	
	
	/**
	 * 
	 * @Title: listGradeList
	 * @Description: 年级信息 封装返回调用
	 * @return
	 * @throws
	 */
	public List<String> listGradeList();
	
	/**
	 * 
	 * @Title: listGradeList
	 * @Description: 毕业年份信息 封装返回调用 离校模块调用
	 * @return
	 * @throws
	 */
	public List<String> listLeaveYearList();

	/**
	 * 
	 * @Title: updateGraduated
	 * @Description: 更新毕业班级的状态
	 * @param classId
	 * @param gruadtedDic
	 * @throws
	 */
	public void updateGraduated(String[] classId,Dic gruadtedDic);
	
	/**
	 * @Title: listBaseMajorPage
	 * @Description: 基础信息 班级管理查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseMajor
	 * @return
	 * @throws
	 */
	public Page listManageClassPage(Integer pageSize , Integer pageNum , BaseClassModel baseClass);
	
	/**
	 * 
	 * @Title: countStudentByClass
	 * @Description: 根据班级查询班级学生人数
	 * @param classId
	 * @return
	 * @throws
	 */
	public Integer countStudentByClass(String classId);
    /**
     * 
     * @Title: IBaseDataDao.java 
     * @Package com.uws.common.dao 
     * @Description:根据楼栋查询最大层数
     * @author LiuChen 
     * @date 2016-11-3 上午10:38:05
     */
	public String getdormFloorByBuildId(String key);
    /**
     * 
     * @Title: IBaseDataDao.java 
     * @Package com.uws.common.dao 
     * @Description: 根据楼栋和楼层查询宿舍
     * @author LiuChen 
     * @date 2016-11-3 上午10:37:50
     */
	public List<BaseRoomModel> queryDormNoByFloor(String dormBuildingId,String dormFloor);
}
