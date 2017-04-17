package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.IRewardCommonDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.DataUtil;
import com.uws.core.util.HqlEscapeUtil;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.domain.reward.AwardInfo;
import com.uws.domain.reward.AwardType;
import com.uws.domain.reward.CollegeAwardInfo;
import com.uws.domain.reward.CountryBurseInfo;
import com.uws.domain.reward.PunishInfo;
import com.uws.domain.reward.StudentApplyInfo;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;
import com.uws.util.ProjectConstants;

/** 
 * @ClassName: RewardCommonDaoImpl 
 * @Description:  
 * @author zhangyb 
 * @date 2015年9月21日 上午11:14:49  
 */
@Repository("rewardCommonDao")
public class RewardCommonDaoImpl extends BaseDaoImpl implements
		IRewardCommonDao {
	private DicUtil dicUtil = DicFactory.getDicUtil();
	@SuppressWarnings("unchecked")
	public List<StudentApplyInfo> getStuInfoList(StudentApplyInfo stuApplyInfo) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer("from StudentApplyInfo s where 1=1");
		if(DataUtil.isNotNull(stuApplyInfo)) {
			hql.append(" and s.studentId.id = ?");
			values.add(stuApplyInfo.getStudentId().getId());
		}
		hql.append(" and s.processStatus = 'PASS'");
		return this.query(hql.toString(), values.toArray());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PunishInfo> getStuPunishList(StudentInfoModel student) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer("from PunishInfo p where 1=1");
		if(DataUtil.isNotNull(student)) {
			hql.append(" and p.stuId.id = ?");
			values.add(student.getId());
		}
		return this.query(hql.toString(), values.toArray());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryBurseInfo> getStuBurseList(StudentInfoModel student) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer("from CountryBurseInfo b where 1=1");
//		学院
		if(student != null && student.getCollege() != null && DataUtil.isNotNull(student.getCollege().getId())) {
			hql.append(" and b.stuId.college.id = ?");
			values.add(student.getCollege().getId());
		}
//		专业
		if(student != null && student.getMajor() != null && DataUtil.isNotNull(student.getMajor().getId())) {
			hql.append(" and b.stuId.major.id = ?");
			values.add(student.getMajor().getId());
		}
//		班级
		if(student != null && student.getClassId() != null && DataUtil.isNotNull(student.getClassId().getId())) {
			hql.append(" and b.stuId.classId.id = ?");
			values.add(student.getClassId().getId());
		}
//		姓名
		if(student != null && DataUtil.isNotNull(student.getName())) {
			hql.append(" and b.stuId.name like ?");
			values.add("%" + HqlEscapeUtil.escape(student.getName()) + "%");
		}
//		学号
		if(student != null && DataUtil.isNotNull(student.getStuNumber())) {
			hql.append(" and b.stuId.stuNumber like ?");
			values.add("%" + HqlEscapeUtil.escape(student.getStuNumber()) + "%");
		}
//		是否是毕业生
		if(student != null && DataUtil.isNotNull(student.getEdusStatus())) {
			hql.append(" and b.stuId.edusStatus in ? and  b.stuId.classId.isGraduatedDic.id = ?");
			values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
			values.add(this.dicUtil.getDicInfo("Y&N", "Y").getId());
		}
		return this.query(hql.toString(), values.toArray());
	}

	/* (非 Javadoc) 
	* <p>Title: getAwardTypeById</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#getAwardTypeById(java.lang.String) 
	*/
	@Override
	public AwardType getAwardTypeById(String id) {
		String hql = " from AwardType a where a.id = ?";
		return (AwardType) this.queryUnique(hql, new Object[]{id});
	}

	/* (非 Javadoc) 
	* <p>Title: getAwardInfoById</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#getAwardInfoById(java.lang.String) 
	*/
	@Override
	public AwardInfo getAwardInfoById(String id) {
		String hql = " from AwardInfo a where a.id = ?";
		return (AwardInfo) this.queryUnique(hql, new Object[]{id});
	}

	/* (非 Javadoc) 
	* <p>Title: checkStuGetAwardOrNot</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#checkStuGetAwardOrNot(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public boolean checkStuGetAwardOrNot(StudentInfoModel student) {
		String sql = " from StudentApplyInfo s inner join s.studentId t where s.studentId.id = ? "
				+ " and s.processStatus = 'PASS'"
				+ " and t.edusStatus in ? and t.classId.isGraduatedDic.id = ?";
		@SuppressWarnings("unchecked")
		List<StudentApplyInfo> applyInfoList = this.query(sql, 
				new Object[]{student.getId(),ProjectConstants.STUDENT_NORMAL_STAUTS_STRING,this.dicUtil.getDicInfo("Y&N", "Y").getId()});
		return applyInfoList.size() > 0 ? true : false;
	}

	/* (非 Javadoc) 
	* <p>Title: getGraduateStuAwardList</p> 
	* <p>Description: </p> 
	* @param schoolYear
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#getGraduateStuAwardList(com.uws.sys.model.Dic) 
	*/
	@Override
	public Page getGraduateStuAwardPage(StudentInfoModel stuInfo,int pageNo,int pageSize) {
		StringBuffer hql = new StringBuffer("select distinct s.studentId from StudentApplyInfo s "
				+ " inner join s.studentId t where s.processStatus = 'PASS'"
				+ " and t.edusStatus in ? and t.classId.isGraduatedDic.id = ?");
		List<String> values = new ArrayList<String>();
		values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
		values.add(this.dicUtil.getDicInfo("Y&N", "Y").getId());
//		学院
		if(stuInfo != null && stuInfo.getCollege() != null && DataUtil.isNotNull(stuInfo.getCollege().getId())) {
			hql.append(" and t.college.id = ?");
			values.add(stuInfo.getCollege().getId());
		}
//		专业
		if(stuInfo != null && stuInfo.getMajor() != null && DataUtil.isNotNull(stuInfo.getMajor().getId())) {
			hql.append(" and t.major.id = ?");
			values.add(stuInfo.getMajor().getId());
		}
//		班级
		if(stuInfo != null && stuInfo.getClassId() != null && DataUtil.isNotNull(stuInfo.getClassId().getId())) {
			hql.append(" and t.classId.id = ?");
			values.add(stuInfo.getClassId().getId());
		}
//		姓名
		if(stuInfo != null && DataUtil.isNotNull(stuInfo.getName())) {
			hql.append(" and t.name like ?");
			values.add("%" + HqlEscapeUtil.escape(stuInfo.getName()) + "%");
		}
//		学号
		if(stuInfo != null && DataUtil.isNotNull(stuInfo.getStuNumber())) {
			hql.append(" and t.stuNumber like ?");
			values.add("%" + HqlEscapeUtil.escape(stuInfo.getStuNumber()) + "%");
		}
		return this.pagedQuery(hql.toString(), pageNo, pageSize, values.toArray());
	}

	/* (非 Javadoc) 
	* <p>Title: countStuApply</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#countStuApply() 
	*/
	@Override
	public long countStuApply() {
		String hql = "select count(s.id) from StudentApplyInfo s where 1 = 1";
		return this.queryCount(hql, new Object[]{});
	}

	/* (非 Javadoc) 
	* <p>Title: getGraduateStuBurseList</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#getGraduateStuBurseList(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfoModel> getGraduateStuBurseList(
			StudentInfoModel student) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer("select distinct b.stuId from CountryBurseInfo b inner join b.stuId t "
				+ "where 1=1 and t.edusStatus in ? and t.classId.isGraduatedDic.id = ?");
		values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
		values.add(this.dicUtil.getDicInfo("Y&N", "Y").getId());
//		学院
		if(student != null && student.getCollege() != null && DataUtil.isNotNull(student.getCollege().getId())) {
			hql.append(" and b.stuId.college.id = ?");
			values.add(student.getCollege().getId());
		}
//		专业
		if(student != null && student.getMajor() != null && DataUtil.isNotNull(student.getMajor().getId())) {
			hql.append(" and b.stuId.major.id = ?");
			values.add(student.getMajor().getId());
		}
//		班级
		if(student != null && student.getClassId() != null && DataUtil.isNotNull(student.getClassId().getId())) {
			hql.append(" and b.stuId.classId.id = ?");
			values.add(student.getClassId().getId());
		}
//		姓名
		if(student != null && DataUtil.isNotNull(student.getName())) {
			hql.append(" and b.stuId.name like ?");
			values.add("%" + HqlEscapeUtil.escape(student.getName()) + "%");
		}
//		学号
		if(student != null && DataUtil.isNotNull(student.getStuNumber())) {
			hql.append(" and b.stuId.stuNumber like ?");
			values.add("%" + HqlEscapeUtil.escape(student.getStuNumber()) + "%");
		}
		return this.query(hql.toString(), values.toArray());
	}

	/* (非 Javadoc) 
	* <p>Title: getStuCollegeAwardList</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#getStuCollegeAwardList(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CollegeAwardInfo> getStuCollegeAwardList(
			StudentInfoModel student) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer("from CollegeAwardInfo c where 1=1");
//		学院
		if(student != null && student.getCollege() != null && DataUtil.isNotNull(student.getCollege().getId())) {
			hql.append(" and c.studentId.college.id = ?");
			values.add(student.getCollege().getId());
		}
//		专业
		if(student != null && student.getMajor() != null && DataUtil.isNotNull(student.getMajor().getId())) {
			hql.append(" and c.studentId.major.id = ?");
			values.add(student.getMajor().getId());
		}
//		班级
		if(student != null && student.getClassId() != null && DataUtil.isNotNull(student.getClassId().getId())) {
			hql.append(" and c.studentId.classId.id = ?");
			values.add(student.getClassId().getId());
		}
//		姓名
		if(student != null && DataUtil.isNotNull(student.getName())) {
			hql.append(" and c.studentId.name like ?");
			values.add("%" + HqlEscapeUtil.escape(student.getName()) + "%");
		}
//		学号
		if(student != null && DataUtil.isNotNull(student.getStuNumber())) {
			hql.append(" and c.studentId.stuNumber like ?");
			values.add("%" + HqlEscapeUtil.escape(student.getStuNumber()) + "%");
		}
		return this.query(hql.toString(), values.toArray());
	}

	/* (非 Javadoc) 
	* <p>Title: checkStuGetBurseOrNot</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#checkStuGetBurseOrNot(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public boolean checkStuGetBurseOrNot(StudentInfoModel student) {
		String sql = " from CountryBurseInfo s inner join s.stuId t where s.stuId.id = ? "
				+ " and t.edusStatus in ? and t.classId.isGraduatedDic.id = ?";
		@SuppressWarnings("unchecked")
		List<CountryBurseInfo> burseInfoList = this.query(sql, 
				new Object[]{student.getId(),ProjectConstants.STUDENT_NORMAL_STAUTS_STRING,this.dicUtil.getDicInfo("Y&N", "Y").getId()});
		return burseInfoList.size() > 0 ? true : false;
	}

	/* (非 Javadoc) 
	* <p>Title: getGraduateStuCollegeList</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#getGraduateStuCollegeList(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfoModel> getGraduateStuCollegeList(
			StudentInfoModel student) {
		List<String> values = new ArrayList<String>();
		StringBuffer hql = new StringBuffer("select distinct b.studentId from CollegeAwardInfo b inner join b.studentId t "
				+ "where 1=1 and t.edusStatus in ? and t.classId.isGraduatedDic.id = ?");
		values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
		values.add(this.dicUtil.getDicInfo("Y&N", "Y").getId());
//		学院
		if(student != null && student.getCollege() != null && DataUtil.isNotNull(student.getCollege().getId())) {
			hql.append(" and b.studentId.college.id = ?");
			values.add(student.getCollege().getId());
		}
//		专业
		if(student != null && student.getMajor() != null && DataUtil.isNotNull(student.getMajor().getId())) {
			hql.append(" and b.studentId.major.id = ?");
			values.add(student.getMajor().getId());
		}
//		班级
		if(student != null && student.getClassId() != null && DataUtil.isNotNull(student.getClassId().getId())) {
			hql.append(" and b.studentId.classId.id = ?");
			values.add(student.getClassId().getId());
		}
//		姓名
		if(student != null && DataUtil.isNotNull(student.getName())) {
			hql.append(" and b.studentId.name like ?");
			values.add("%" + HqlEscapeUtil.escape(student.getName()) + "%");
		}
//		学号
		if(student != null && DataUtil.isNotNull(student.getStuNumber())) {
			hql.append(" and b.studentId.stuNumber like ?");
			values.add("%" + HqlEscapeUtil.escape(student.getStuNumber()) + "%");
		}
		return this.query(hql.toString(), values.toArray());
	}

	/* (非 Javadoc) 
	* <p>Title: checkStuGetCollegeOrNot</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.dao.IRewardCommonDao#checkStuGetCollegeOrNot(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public boolean checkStuGetCollegeOrNot(StudentInfoModel student) {
		String sql = " from CollegeAwardInfo s inner join s.studentId t where s.studentId.id = ? "
				+ " and t.edusStatus in ? and t.classId.isGraduatedDic.id = ?";
		@SuppressWarnings("unchecked")
		List<CollegeAwardInfo> collegeInfoList = this.query(sql, 
				new Object[]{student.getId(),ProjectConstants.STUDENT_NORMAL_STAUTS_STRING,this.dicUtil.getDicInfo("Y&N", "Y").getId()});
		return collegeInfoList.size() > 0 ? true : false;
	}
}
