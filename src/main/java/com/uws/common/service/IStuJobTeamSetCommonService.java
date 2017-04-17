package com.uws.common.service;

import java.util.List;

import com.uws.core.base.IBaseService;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.teacher.StuJobTeamSetModel;
import com.uws.domain.teacher.TeacherInfoModel;

public interface IStuJobTeamSetCommonService extends IBaseService {
	/**
	 * 通过教师基础表id查询是不是班主任
	 * @param baseTeacherId
	 * @return boolean
	 */
	public boolean isHeadMaster(String baseTeacherId);
	
	/**
	 * 通过baseTeacherId获得班主任所教班级
	 * @param baseTeacherId 
	 * @return BaseClassModel
	 */
	public List<BaseClassModel> getHeadteacherClass(String baseTeacherId);
	/**
	 * 通过班级id查询班主任
	 * @param classId
	 * @return
	 */
	public BaseTeacherModel queryHeadmasterByClassId(String classId);
	/**
	 * 通过班级id查询教学辅导员
	 * @param classId
	 * @return
	 */
	public BaseTeacherModel queryTeacherCounsellorByClassId(String classId);
	/**
	 * 判断当前登录人是否是测评辅导员
	 * @param teacherId
	 * @return
	 */
	public boolean isEvaCounsellor(String teacherId);
	/**
	 * 根据学院id返回测评辅导员
	 * @param collegeId
	 * @return
	 */
	public List<BaseTeacherModel> getEvaCounsellorByCollegeId(String collegeId);
	/**
	 * 通过教师id获得其下的学院信息
	 * @param teacherId
	 * @return List<BaseAcademyModel>
	 */
	public List<BaseAcademyModel> getBAMByTeacherId(String teacherId);
	/**
	 * 获取学院 测评辅导员的map
	 * @return List<StuJobTeamSetModel>
	 */
	public List<StuJobTeamSetModel> getMapCollegeEvaCounsellor();
	/**
	 * 通过教师id返回资助辅导员的学院信息
	 * @param teacherId
	 * @return List<BaseAcademyModel>
	 */
	public List<BaseAcademyModel> isSubsidizeCounsellor(String teacherId);
	/**
	 * 根据教学辅导员id返回其班级列表
	 * @param teacherId
	 * @return
	 */
	public List<BaseClassModel> queryBaseClassModelByTCId(String teacherId);
	/**
	 * 根据教师基本表id查询教师拓展表信息
	 * @param teacherId
	 * @return TeacherInfoModel
	 */
	public TeacherInfoModel getTeacherInfoByBTId(String teacherId);
	
	/***
	 * 判断是否教学辅导员
	 * @param teacherId
	 * @return
	 */
	public boolean isTeacherCounsellor(String teacherId);
}
