package com.uws.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.IStuJobTeamSetCommonDao;
import com.uws.common.service.IStuJobTeamSetCommonService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.teacher.StuJobTeamSetModel;
import com.uws.domain.teacher.TeacherInfoModel;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

@Service("com.uws.common.service.impl")
public class StuJobTeamSetCommonServiceImpl extends BaseServiceImpl 
	implements IStuJobTeamSetCommonService {
	@Autowired
	private IStuJobTeamSetCommonDao stuJobTeamSetDao;
	private static DicUtil dicUtil = DicFactory.getDicUtil();
	
	@Override
	public boolean isHeadMaster(String baseTeacherId) {
		List<BaseClassModel> po = stuJobTeamSetDao.getStuJobByBTIdAndTyId(baseTeacherId, 
				dicUtil.getDicInfo("TEACHER_TYPE", "HEADMASTER").getId());
		return (po!=null && po.size()>0);
	}

	@Override
	public List<BaseClassModel> getHeadteacherClass(String baseTeacherId) {
		List<BaseClassModel> po = stuJobTeamSetDao.getStuJobByBTIdAndTyId(baseTeacherId, 
				dicUtil.getDicInfo("TEACHER_TYPE", "HEADMASTER").getId());
		return po;
	}

	@Override
	public BaseTeacherModel queryHeadmasterByClassId(String classId) {
		BaseTeacherModel po = stuJobTeamSetDao.getTeacherInfoModelByClassIdAndTyId(classId, 
				dicUtil.getDicInfo("TEACHER_TYPE", "HEADMASTER").getId());
		return po;
	}

	@Override
	public BaseTeacherModel queryTeacherCounsellorByClassId(String classId) {
		BaseTeacherModel po = stuJobTeamSetDao.getTeacherInfoModelByClassIdAndTyId(classId, 
				dicUtil.getDicInfo("TEACHER_TYPE", "TEACHER_COUNSELLOR").getId());
		return po;
	}

	@Override
	public boolean isEvaCounsellor(String teacherId) {
		List<BaseAcademyModel> po = stuJobTeamSetDao.getStuJobCollegeByBTIdAndTyId(teacherId, 
				dicUtil.getDicInfo("TEACHER_TYPE", "EVALUATION_COUNSELLOR").getId());
		return (po!=null && po.size()>0);
	}

	@Override
	public List<BaseTeacherModel> getEvaCounsellorByCollegeId(String collegeId) 
	{
		return stuJobTeamSetDao.getTeacherInfoModelByCollegeIdAndTyId(collegeId,dicUtil.getDicInfo("TEACHER_TYPE", "EVALUATION_COUNSELLOR").getId());
	}

	@Override
	public List<BaseAcademyModel> getBAMByTeacherId(String teacherId) {
		return stuJobTeamSetDao.getStuJobCollegeByBTIdAndTyId(teacherId, 
				dicUtil.getDicInfo("TEACHER_TYPE", "EVALUATION_COUNSELLOR").getId());
	}
	@Override
	public List<StuJobTeamSetModel> getMapCollegeEvaCounsellor() {
		return stuJobTeamSetDao.getMapCollegeCounsellor(dicUtil.getDicInfo("TEACHER_TYPE", "EVALUATION_COUNSELLOR").getId());
	}

	@Override
	public List<BaseAcademyModel> isSubsidizeCounsellor(String teacherId) {
		return stuJobTeamSetDao.getStuJobCollegeByBTIdAndTyId(teacherId,
				dicUtil.getDicInfo("TEACHER_TYPE", "SUBSIDIZE_COUNSELLOR").getId());
	}

	@Override
	public List<BaseClassModel> queryBaseClassModelByTCId(String teacherId) {
		return stuJobTeamSetDao.getStuJobByBTIdAndTyId(teacherId, 
				dicUtil.getDicInfo("TEACHER_TYPE", "TEACHER_COUNSELLOR").getId());
	}

	@Override
	public TeacherInfoModel getTeacherInfoByBTId(String teacherId) {
		return stuJobTeamSetDao.getTeacherInfoByBTId(teacherId);
	}
	
	@Override
	public boolean isTeacherCounsellor(String teacherId) {
		List<BaseClassModel> po = stuJobTeamSetDao.getStuJobByBTIdAndTyId(teacherId, 
				dicUtil.getDicInfo("TEACHER_TYPE", "TEACHER_COUNSELLOR").getId());
		return (po!=null && po.size()>0);
	}

}
