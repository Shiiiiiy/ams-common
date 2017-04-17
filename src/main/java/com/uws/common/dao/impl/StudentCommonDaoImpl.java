package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.uws.common.dao.IStudentCommonDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.DataUtil;
import com.uws.core.util.HqlEscapeUtil;
import com.uws.domain.integrate.StudentApproveSetModel;
import com.uws.domain.orientation.StudentGuardianModel;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;
import com.uws.util.ProjectConstants;

/**
 * 
* @ClassName: StudentCommonDaoImpl 
* @Description: 学生信息综合内部调用接口DAO 实现类
* @author 联合永道
* @date 2015-7-27 上午11:06:45 
*
 */
@Repository("com.uws.common.dao.impl.StudentCommonDaoImpl")
public class StudentCommonDaoImpl extends BaseDaoImpl implements IStudentCommonDao
{
	
	//数据字典工具类
	private DicUtil dicUtil = DicFactory.getDicUtil();

	/**
	 * 描述信息:按条件分页查询 
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 * @see com.uws.common.dao.IStudentCommonDao#queryStudentPage(java.lang.Integer, java.lang.Integer, com.uws.domain.orientation.StudentInfoModel)
	 */
	@Override
    public Page queryStudentPage(Integer pageNo, Integer pageSize,StudentInfoModel student)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from StudentInfoModel where edusStatus in ? ");
		values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
		if(null != student)
		{
			if(!StringUtils.isEmpty(student.getName())) 
			{
				hql.append(" and name like ? ");
				if (HqlEscapeUtil.IsNeedEscape(student.getName())) 
				{
					values.add("%" + HqlEscapeUtil.escape(student.getName()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" +student.getName()+ "%");
			}
			if(!StringUtils.isEmpty(student.getStuNumber())) 
			{
				hql.append(" and stuNumber like ? ");
				if (HqlEscapeUtil.IsNeedEscape(student.getStuNumber())) 
				{
					values.add("%" + HqlEscapeUtil.escape(student.getStuNumber()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" +student.getStuNumber()+ "%");
			}
			//学院单位
			if (null != student.getCollege() && !StringUtils.isEmpty(student.getCollege().getId())) 
			{
				hql.append(" and college.id = ? ");
				values.add(student.getCollege().getId());
			}
			//专业
			if (null != student.getMajor() && !StringUtils.isEmpty(student.getMajor().getId())) 
			{
				hql.append(" and major.id = ? ");
				values.add(student.getMajor().getId());
			}
			//班级
			if (null != student.getClassId() && !StringUtils.isEmpty(student.getClassId().getId())) 
			{
				hql.append(" and classId.id = ? ");
				values.add(student.getClassId().getId());
			}
		}
		
		if (values.size() == 0)
			return this.pagedQuery(hql.toString(), pageNo, pageSize);
		else
			return this.pagedQuery(hql.toString(), pageNo, pageSize,values.toArray());
    }
	/**
	 * 
	 * @Title: getStudentByCode
	 * @Description: 描述信息: 按照身份证号查询学生信息
	 * @param studentId
	 * @return
	 * @throws
	 */
	@Override
	public StudentInfoModel getStudentByCode(String certificateCode) 
	{
		return (StudentInfoModel) this.queryUnique("from StudentInfoModel s where s.certificateCode=?", certificateCode);
	}
	
	/**
	 * 描述信息: 按照学号查询学生信息
	 * @param studentNo
	 * @return
	 * @see com.uws.common.dao.IStudentCommonDao#queryStudentByStudentNo(java.lang.String)
	 */
	@Override
    public StudentInfoModel queryStudentByStudentNo(String studentNo)
    {
		return (StudentInfoModel) this.queryUnique("from StudentInfoModel s where s.stuNumber=?", studentNo);
    }
	
	@Override
	public StudentApproveSetModel queryClassMonitor(String stuId) {
		StringBuffer hql = new StringBuffer(" from StudentApproveSetModel sasm where sasm.classId.id=(select classId.id from StudentInfoModel where id=?) and sasm.status.id=?");
		StudentApproveSetModel sasm = 
				(StudentApproveSetModel)this.queryUnique(hql.toString(), new Object[]{stuId,this.dicUtil.getStatusNormal().getId()});
		if(DataUtil.isNotNull(sasm) && DataUtil.isNotNull(sasm.getStudentId()) && DataUtil.isNotNull(sasm.getStudentId().getId())){
//			return (User)this.queryUnique(" from User u where u.id=?", new Object[]{sasm.getStudentId().getId()});
			return sasm;
		}
		
		return new StudentApproveSetModel();
	}
	
	
	/**
	 * 描述信息:按条件分页查询 毕业生
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 * @see 
	 */
	@Override
    public Page queryCheckGraduateStudent(Integer pageNo, Integer pageSize,StudentInfoModel student){
		List<Object> values = new ArrayList<Object>();
		Dic graduatedDic=this.dicUtil.getDicInfo("Y&N", "Y");// 是否毕业
		StringBuffer hql = new StringBuffer("from StudentInfoModel where edusStatus in ? ");
		values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
		hql.append(" and classId.isGraduatedDic.id in ?");
		values.add(graduatedDic.getId());
		if(null != student){
			if(!StringUtils.isEmpty(student.getName())){
				hql.append(" and name like ? ");
				if(HqlEscapeUtil.IsNeedEscape(student.getName())){
					values.add("%" + HqlEscapeUtil.escape(student.getName()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				}else{
					values.add("%" +student.getName()+ "%");
				}
			}
			if(!StringUtils.isEmpty(student.getStuNumber())){
				hql.append(" and stuNumber like ? ");
				if(HqlEscapeUtil.IsNeedEscape(student.getStuNumber())){
					values.add("%" + HqlEscapeUtil.escape(student.getStuNumber()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				}else{
					values.add("%" +student.getStuNumber()+ "%");
				}
			}
			//学院单位
			if(null != student.getCollege() && !StringUtils.isEmpty(student.getCollege().getId())){
				hql.append(" and college.id = ? ");
				values.add(student.getCollege().getId());
			}
			//专业
			if(null != student.getMajor() && !StringUtils.isEmpty(student.getMajor().getId())){
				hql.append(" and major.id = ? ");
				values.add(student.getMajor().getId());
			}
			//班级
			if(null != student.getClassId() && !StringUtils.isEmpty(student.getClassId().getId())){
				hql.append(" and classId.id = ? ");
				values.add(student.getClassId().getId());
			}
		}
		
		if (values.size() == 0){
			return this.pagedQuery(hql.toString(), pageNo, pageSize);
		}else{
			return this.pagedQuery(hql.toString(), pageNo, pageSize,values.toArray());
		}
    }
	
	/**
	 * @param studentId
	 * @Description:判断学生是否已毕业（所在的班级是毕业班）
	 * @author LiuChen  
	 * @date 2015-12-15 下午5:11:36
	 */
	@Override
	public StudentInfoModel queryGraduateStudentByStudentId(String studentId)
	{   
		return (StudentInfoModel) this.queryUnique("from StudentInfoModel where edusStatus in ? and id = ? and classId.isGraduatedDic.id = ? ",ProjectConstants.STUDENT_NORMAL_STAUTS_STRING,studentId,this.dicUtil.getDicInfo("Y&N", "Y").getId());
	}
	
	/**
	 * @param studentNo
	 * @Description:根据学号查询学生学生监护人信息
	 * @author wjr
	 * @date 2015-12-29 
	 */
	@Override
	public StudentGuardianModel queryStudentGuardianByStudentNo(String studentNo, String seqNum) {   
		return (StudentGuardianModel) this.queryUnique("from StudentGuardianModel s where s.studentInfo.stuNumber= ? and seqNum = ? ", studentNo, seqNum);
	}
}
