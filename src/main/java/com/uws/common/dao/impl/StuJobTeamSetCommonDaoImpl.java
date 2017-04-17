package com.uws.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.IStuJobTeamSetCommonDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.teacher.StuJobTeamSetModel;
import com.uws.domain.teacher.TeacherInfoModel;

@Repository("com.uws.common.dao.impl")
public class StuJobTeamSetCommonDaoImpl extends BaseDaoImpl implements IStuJobTeamSetCommonDao {
	@Override
	public List<BaseClassModel> getStuJobByBTIdAndTyId(String baseTeacherId, String teacherTypeId) {
		String hql = "select t.klass from StuJobTeamSetModel t where t.teacher.id = ? and t.teacherType.id = ? ";
		return (List<BaseClassModel>)this.query(hql, new String[]{baseTeacherId, teacherTypeId});
	}

	@Override
	public BaseTeacherModel getTeacherInfoModelByClassIdAndTyId(String classId, String typeId) {
		String hql = "select t.teacher from StuJobTeamSetModel t where t.klass.id = ? and t.teacherType.id = ? ";
		return (BaseTeacherModel) this.queryUnique(hql, new String[]{classId, typeId});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BaseTeacherModel> getTeacherInfoModelByCollegeIdAndTyId(String collegeId, String teacherTypeId) {
		String hql = "select t.teacher from StuJobTeamSetModel t where t.college.id = ? and t.teacherType.id = ? ";
		return  this.query(hql, new String[]{collegeId, teacherTypeId});
	}
	@Override
	public List<BaseAcademyModel> getStuJobCollegeByBTIdAndTyId(String baseTeacherId, String teacherTypeId) {
		String hql = "select t.college from StuJobTeamSetModel t where t.teacher.id = ? and t.teacherType.id = ? ";
		return (List<BaseAcademyModel>)this.query(hql, new String[]{baseTeacherId, teacherTypeId});
	}
	
	@Override
	public List<StuJobTeamSetModel> getMapCollegeCounsellor(String teacherTypeId) {
		String hql = " from StuJobTeamSetModel t where t.teacherType.id = ? ";
		return (List<StuJobTeamSetModel>)this.query(hql, new String[]{teacherTypeId});
	}

	@Override
	public TeacherInfoModel getTeacherInfoByBTId(String teacherId) {
		String hql = " from TeacherInfoModel t where t.teacher.id = ? ";
		return (TeacherInfoModel)this.queryUnique(hql, teacherId);
	}
}
