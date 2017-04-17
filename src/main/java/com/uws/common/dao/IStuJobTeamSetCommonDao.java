package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.teacher.StuJobTeamSetModel;
import com.uws.domain.teacher.TeacherInfoModel;

public interface IStuJobTeamSetCommonDao extends IBaseDao {
	/**
	 * 通过教师基础表Id以及教师类型Id查询StuJobTeamSetModel
	 * @param baseTeacherId
	 * @param teacherTypeId
	 * @return List<BaseClassModel>
	 */
	public List<BaseClassModel> getStuJobByBTIdAndTyId(String baseTeacherId, String teacherTypeId);
	/**
	 * 根据班级和教师类型返回拓展信息
	 * @param classId
	 * @param typeId
	 * @return TeacherInfoModel
	 */
	public BaseTeacherModel getTeacherInfoModelByClassIdAndTyId(String classId, String typeId);
	/**
	 * 通过学院id和辅导员类型id返回TeacherInfoModel
	 * @param collegeId
	 * @param teacherTypeId
	 * @return TeacherInfoModel
	 */
	public List<BaseTeacherModel> getTeacherInfoModelByCollegeIdAndTyId(String collegeId, String teacherTypeId);
	/**
	 * 通过教师基础表Id以及教师类型Id查询StuJobTeamSetModel
	 * @param baseTeacherId
	 * @param teacherTypeId
	 * @return List<BaseAcademyModel>
	 */
	public List<BaseAcademyModel> getStuJobCollegeByBTIdAndTyId(String baseTeacherId, String teacherTypeId);
	/**
	 * 根据教师类型返回一个List<StuJobTeamSetModel>
	 * @param teacherTypeId
	 * @return List<StuJobTeamSetModel>
	 */
	public List<StuJobTeamSetModel> getMapCollegeCounsellor(String teacherTypeId);
	/**
	 * 根据教师基本表id查询教师拓展表信息
	 * @param teacherId
	 * @return TeacherInfoModel
	 */
	public TeacherInfoModel getTeacherInfoByBTId(String teacherId);
}
