package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.uws.common.dao.IBaseDataDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.HqlEscapeUtil;
import com.uws.domain.base.BaseAcademyModel;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.base.BaseMajorModel;
import com.uws.domain.base.BaseRoomModel;
import com.uws.domain.base.BaseTeacherModel;
import com.uws.domain.base.StudentRoomModel;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;
import com.uws.util.ProjectConstants;

/**
 * 
* @ClassName: BaseDataDaoImpl 
* @Description:基础信息管理DAO实现
* @author 联合永道
* @date 2015-7-13 下午3:30:31 
*
 */
@Repository("com.uws.common.dao.impl.BaseDataDaoImpl")
public class BaseDataDaoImpl extends BaseDaoImpl implements IBaseDataDao
{
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
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from BaseMajorModel where 1=1 ");
		
		//学院单位
		if (null != baseMajor.getCollage() && !StringUtils.isEmpty(baseMajor.getCollage().getId())) 
		{
			hql.append(" and collage.id = ? ");
			values.add(baseMajor.getCollage().getId());
		}
		
		//专业
		if (null != baseMajor && !StringUtils.isEmpty(baseMajor.getId())) 
		{
			hql.append(" and id = ? ");
			values.add(baseMajor.getId());
		}
		
		//专业名称
		if (!StringUtils.isEmpty(baseMajor.getMajorName())) 
		{
			hql.append(" and majorName like ? ");
			if (HqlEscapeUtil.IsNeedEscape(baseMajor.getMajorName())) 
			{
				values.add("%" + HqlEscapeUtil.escape(baseMajor.getMajorName()) + "%");
				hql.append(HqlEscapeUtil.HQL_ESCAPE);
			} else
				values.add("%" + baseMajor.getMajorName() + "%");
		}
		
		//专业编码
		if (!StringUtils.isEmpty(baseMajor.getMajorCode())) 
		{
			hql.append(" and majorCode like ? ");
			if (HqlEscapeUtil.IsNeedEscape(baseMajor.getMajorCode())) 
			{
				values.add("%" + HqlEscapeUtil.escape(baseMajor.getMajorCode()) + "%");
				hql.append(HqlEscapeUtil.HQL_ESCAPE);
			} else
				values.add("%" + baseMajor.getMajorCode() + "%");
		}
		// 门类编码
		if ( !StringUtils.isEmpty(baseMajor.getCategoryCode())) 
		{
			hql.append(" and categoryCode like ? ");
			if (HqlEscapeUtil.IsNeedEscape(baseMajor.getCategoryCode())) 
			{
				values.add("%" + HqlEscapeUtil.escape(baseMajor.getCategoryCode()) + "%");
				hql.append(HqlEscapeUtil.HQL_ESCAPE);
			} else
				values.add("%" + baseMajor.getCategoryCode() + "%");
		}

		if (values.size() == 0)
			return this.pagedQuery(hql.toString(), pageNum, pageSize);
		else
			return this.pagedQuery(hql.toString(), pageNum, pageSize,values.toArray());
    }

	/**
	 * 描述信息: 基础信息 班级查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseClass
	 * @return
	 * @see com.uws.common.dao.IBaseDataDao#listBaseClassPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseClassModel)
	 */
	@Override
    public Page listBaseClassPage(Integer pageSize, Integer pageNum,BaseClassModel baseClass)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from BaseClassModel where 1=1 ");
		
		if(null != baseClass)
		{
			//学院单位
			if (null != baseClass.getMajor() && null != baseClass.getMajor().getCollage() && !StringUtils.isEmpty(baseClass.getMajor().getCollage().getId())) 
			{
				hql.append(" and major.collage = ? ");
				values.add(baseClass.getMajor().getCollage());
			}
			//专业
			if (null != baseClass.getMajor() && !StringUtils.isEmpty(baseClass.getMajor().getId())) 
			{
				hql.append(" and major = ? ");
				values.add(baseClass.getMajor());
			}
			//班级名称
			if (!StringUtils.isEmpty(baseClass.getClassName())) 
			{
				hql.append(" and className like ? ");
				if (HqlEscapeUtil.IsNeedEscape(baseClass.getClassName())) 
				{
					values.add("%" + HqlEscapeUtil.escape(baseClass.getClassName()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" + baseClass.getClassName()+ "%");
			}
		}
		
		if (values.size() == 0)
			return this.pagedQuery(hql.toString(), pageNum, pageSize);
		else
			return this.pagedQuery(hql.toString(), pageNum, pageSize,values.toArray());
    }

	
	/**
	 * 描述信息: 基础信息 教职工功能查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseTeacher
	 * @return
	 * @see com.uws.common.dao.IBaseDataDao#listBaseClassPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseClassModel)
	 */
	@Override
    public Page listBaseTeacherPage(Integer pageSize, Integer pageNum,BaseTeacherModel baseTeacher)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from BaseTeacherModel where 1=1 ");
		
		if(null != baseTeacher)
		{
			//隶属单位
			if (null != baseTeacher.getOrg() &&!StringUtils.isEmpty(baseTeacher.getOrg().getId())) 
			{
				hql.append(" and org.id = ? ");
				values.add(baseTeacher.getOrg().getId());
			}
			//姓名
			if (!StringUtils.isEmpty(baseTeacher.getName())) 
			{
				hql.append(" and name like ? ");
				if (HqlEscapeUtil.IsNeedEscape(baseTeacher.getName())) 
				{
					values.add("%" + HqlEscapeUtil.escape(baseTeacher.getName()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" + baseTeacher.getName()+ "%");
			}
			//职工号
			if (!StringUtils.isEmpty(baseTeacher.getId())) 
			{
				hql.append(" and id like ? ");
				if (HqlEscapeUtil.IsNeedEscape(baseTeacher.getId())) 
				{
					values.add("%" + HqlEscapeUtil.escape(baseTeacher.getId()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" + baseTeacher.getId()+ "%");
			}
		}
		
		if (values.size() == 0)
			return this.pagedQuery(hql.toString(), pageNum, pageSize);
		else
			return this.pagedQuery(hql.toString(), pageNum, pageSize,values.toArray());
    }
	
	/**
	 * 描述信息: 查询所有的学院信息
	 * @return
	 * @see com.uws.common.dao.IBaseDataDao#listBaseAcademy()
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<BaseAcademyModel> listBaseAcademy()
    {
	   return this.query("from BaseAcademyModel order by code desc");
    }

	/**
	 * 描述信息: 按条件查询班级信息
	 * @param grade
	 * @param academyId
	 * @param majorId
	 * @return
	 * @see com.uws.common.dao.IBaseDataDao#listBaseClass(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<BaseClassModel> listBaseClass(String grade,String majorId , String academyId)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from BaseClassModel where 1=1 ");
		if(!StringUtils.isEmpty(grade))
		{
			hql.append(" and grade = ? ");
			values.add(grade);
		}
		
		if(!StringUtils.isEmpty(majorId))
		{
			hql.append(" and major.id = ? ");
			values.add(majorId);
		}
		
		if(!StringUtils.isEmpty(academyId))
		{
			hql.append(" and major.collage.id = ? ");
			values.add(academyId);
		}
		hql.append(" order by code ");
		return this.query(hql.toString(), values.toArray());
    }

	/**
	 * 描述信息: 所有专业信息
	 * @return
	 * @see com.uws.common.dao.IBaseDataDao#getAllMajor()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BaseMajorModel> getAllMajor() 
	{
		return this.query("from BaseMajorModel order by majorCode ");
	}

	/**
	 * 描述信息: 根据职工号查询教职工
	 * @param teacherCode
	 * @return
	 * @see com.uws.common.dao.IBaseDataDao#findTeacherByCode(java.lang.String)
	 */
	@Override
    public BaseTeacherModel findTeacherByCode(String teacherCode)
    {
		return (BaseTeacherModel) this.queryUnique("from BaseTeacherModel where code = ? ",new Object[]{teacherCode});
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
		if(!StringUtils.isEmpty(studentId))
			return (StudentRoomModel) this.queryUnique("from StudentRoomModel where student.id = ? ",new Object[]{studentId});
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
		if(!StringUtils.isEmpty(studentNumber))
			return (StudentRoomModel) this.queryUnique("from StudentRoomModel where student.stuNumber = ? ",new Object[]{studentNumber});
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
		if(!StringUtils.isEmpty(cardID))
			return (StudentRoomModel) this.queryUnique("from StudentRoomModel where student.certificateCode = ? ",new Object[]{cardID});
		return null;
	}

	/**
	 * 描述信息: 按宿舍查询学生住宿信息
	 * @param roomId
	 * @return
	 * @see com.uws.common.service.IBaseDataService#findRoomByRoomId(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<StudentRoomModel> findRoomByRoomId(String roomId)
    {
		if(!StringUtils.isEmpty(roomId))
		{
			StringBuffer hql = new StringBuffer(" from StudentRoomModel where room.id = ? ");
			return this.query(hql.toString(), new Object[]{roomId});
		}
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
	@SuppressWarnings("unchecked")
    public List<StudentRoomModel> findRoomByRoomId(String roomId, String notExistStudentId)
    {
		if(!StringUtils.isEmpty(roomId))
		{
			StringBuffer hql = new StringBuffer(" from StudentRoomModel where room.id = ? and student.id <> ?  ");
			return this.query(hql.toString(), new Object[]{roomId,notExistStudentId});
		}
		return null;
    }

	/**
	 * 描述信息: 学院教师信息分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param teacher
	 * @return
	 * @see com.uws.common.dao.IBaseDataDao#queryCollegeTeacherPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseTeacherModel)
	 */
	@Override
    public Page queryCollegeTeacherPage(Integer pageNo, Integer pageSize, BaseTeacherModel baseTeacher)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from BaseTeacherModel where org.id in ( select id from BaseAcademyModel) ");
		
		if(null != baseTeacher)
		{
			//隶属单位
			if (null != baseTeacher.getOrg() &&!StringUtils.isEmpty(baseTeacher.getOrg().getId())) 
			{
				hql.append(" and org.id = ? ");
				values.add(baseTeacher.getOrg().getId());
			}
			//姓名
			if (!StringUtils.isEmpty(baseTeacher.getName())) 
			{
				hql.append(" and name like ? ");
				if (HqlEscapeUtil.IsNeedEscape(baseTeacher.getName())) 
				{
					values.add("%" + HqlEscapeUtil.escape(baseTeacher.getName()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" + baseTeacher.getName()+ "%");
			}
			//主键ID
			if (!StringUtils.isEmpty(baseTeacher.getId())) 
			{
				hql.append(" and id like ? ");
				if (HqlEscapeUtil.IsNeedEscape(baseTeacher.getId())) 
				{
					values.add("%" + HqlEscapeUtil.escape(baseTeacher.getId()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" + baseTeacher.getId()+ "%");
			}
			//职工号
			if (!StringUtils.isEmpty(baseTeacher.getCode())) 
			{
				hql.append(" and code like ? ");
				if (HqlEscapeUtil.IsNeedEscape(baseTeacher.getCode())) 
				{
					values.add("%" + HqlEscapeUtil.escape(baseTeacher.getCode()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" + baseTeacher.getCode()+ "%");
			}
		}
		
		if (values.size() == 0)
			return this.pagedQuery(hql.toString(), pageNo, pageSize);
		else
			return this.pagedQuery(hql.toString(), pageNo, pageSize,values.toArray());
    }

	@Override
	@SuppressWarnings("unchecked")
    public List<String> listGradeList()
    {
		return this.query("select distinct grade from BaseClassModel order by grade desc ");
    }
	
	/**
	 * 
	 * @Title: listGradeList
	 * @Description: 毕业年份信息 封装返回调用 离校模块调用
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
    public List<String> listLeaveYearList()
	{
		return this.query("select distinct year from LeaveInfo order by year desc ");
	}

	/**
	 * 描述信息: 更新毕业状态
	 * @param classId
	 * @param gruadtedDic
	 * 2015-12-7 上午11:28:17
	 */
	@Override
    public void updateGraduated(String[] classId, Dic gruadtedDic)
    {
		Map<String,Object> values = new HashMap<String,Object>();
		String hql = "update BaseClassModel set isGraduatedDic = :isGraduatedDic , graduatedYearDic = null  where id in (:ids) ";
		values.put("isGraduatedDic", gruadtedDic);
		values.put("ids", classId);
		this.executeHql(hql, values);
    }
	
	/**
	 * 描述信息: 基础信息 班级管理查询
	 * @param pageSize
	 * @param pageNum
	 * @param baseClass
	 * @return
	 * @see com.uws.common.dao.IBaseDataDao#listManageClassPage(java.lang.Integer, java.lang.Integer, com.uws.domain.base.BaseClassModel)
	 */
	@Override
    public Page listManageClassPage(Integer pageSize, Integer pageNum,BaseClassModel baseClass)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from BaseClassModel where 1=1 ");
		
		if(null != baseClass)
		{
			//学院单位
			if (null != baseClass.getMajor() && null != baseClass.getMajor().getCollage() && !StringUtils.isEmpty(baseClass.getMajor().getCollage().getId())) 
			{
				hql.append(" and major.collage = ? ");
				values.add(baseClass.getMajor().getCollage());
			}
			//专业
			if (null != baseClass.getMajor() && !StringUtils.isEmpty(baseClass.getMajor().getId())) 
			{
				hql.append(" and major = ? ");
				values.add(baseClass.getMajor());
			}
			//班级名称
			if (!StringUtils.isEmpty(baseClass.getClassName())) 
			{
				hql.append(" and className like ? ");
				if (HqlEscapeUtil.IsNeedEscape(baseClass.getClassName())) 
				{
					values.add("%" + HqlEscapeUtil.escape(baseClass.getClassName()) + "%");
					hql.append(HqlEscapeUtil.HQL_ESCAPE);
				} else
					values.add("%" + baseClass.getClassName()+ "%");
			}
			//班级毕业状态
			if (null != baseClass.getIsGraduatedDic() && !StringUtils.isEmpty(baseClass.getIsGraduatedDic().getId())) 
			{
				hql.append(" and isGraduatedDic = ? ");
				values.add(baseClass.getIsGraduatedDic());
			}
			//年级
			if (!StringUtils.isEmpty(baseClass.getGrade())) 
			{
				hql.append(" and grade = ? ");
				values.add(baseClass.getGrade());
			}
		}
		
		//班级可用状态 数据同步后 条件放开
		//hql.append(" and status = ? ");
		//values.add(ProjectConstants.BASE_CLASS_STAUTS_USE);
		
		//排序
		hql.append(" order by grade , major.collage , major ");
		if (values.size() == 0)
			return this.pagedQuery(hql.toString(), pageNum, pageSize);
		else
			return this.pagedQuery(hql.toString(), pageNum, pageSize,values.toArray());
    }
	
	/**
	 * 描述信息: 根据班级查询班级学生人数
	 * @param classId
	 * @return
	 * 2015-12-15 下午4:17:38
	 */
	@Override
	@SuppressWarnings("unchecked")
    public Integer countStudentByClass(String classId)
    {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from StudentInfoModel where  classId.id = ? and edusStatus in (?) ");
		values.add(classId);
		values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
        List<StudentInfoModel> list = this.query(hql.toString(), values.toArray());
		return null == list ? 0 : list.size();
    }
	
	@Override
	public String getdormFloorByBuildId(String key)
	{
		String hql ="select max(r.floorNum) from BaseRoomModel r where r.building.id = ?";
		return (String) this.queryUnique(hql, new Object[]{key});
	}
	/**
	 * 
	 * @Description:根据楼层和楼层查询宿舍
	 * @author LiuChen  
	 * @date 2016-11-3 上午10:38:38
	 */
	@Override
	public List<BaseRoomModel> queryDormNoByFloor(String dormBuildingId,String dormFloor)
	{
		StringBuffer hql = new StringBuffer("from BaseRoomModel where building.id = ? and floorNum = ? order by name");
		return this.query(hql.toString(), new Object[]{dormBuildingId,dormFloor});
	}
}
