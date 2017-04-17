package com.uws.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uws.common.dao.ILeaveCommonDao;
import com.uws.common.util.Constants;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.domain.leave.LeaveSchool;
import com.uws.domain.orientation.StudentInfoModel;

@Repository("leaveCommonDao")
public class LeaveCommonDaoImpl extends BaseDaoImpl implements ILeaveCommonDao {

	/***
	 * 查询计财处 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<StudentInfoModel> queryFinanceStudentList(){
		StringBuffer hql = new StringBuffer("select t.student from LeaveSchool t where 1=1  ");
		hql.append(" and (t.graduateStatus is null or t.graduateStatus='') ");//未毕业状态
//		hql.append(" and t.status = '1' ");//离校流程 已发起 
		hql.append(" and (t.finance is null or  t.finance  <> ? )");//财务处未办理
		List<StudentInfoModel> list=this.query(hql.toString(), new Object[]{Constants.STUDENT_FEE_STATUS_STR});
		return list;
	}
	
	/***
	 * 查询学生离校情况 2016-5-17
	 * @param studentId
	 * @return
	 */
	public LeaveSchool queryLeaveSchoolByStudentId(String studentId){
		List<LeaveSchool> list=this.query("from LeaveSchool t where t.student.id = ? ", new Object[]{studentId});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/****
	 * 计财处 离校手续 2016-5-17
	 * @param studentId
	 * 状态 ：0 未办 ;1 已办   
	 */
	public void updateLeaveFinance(String studentId, String status){
		String hql = "update LeaveSchool set finance = ?  where student.id = ?  ";
	    this.executeHql(hql, new Object[]{status,studentId});
	}
	
	/***
	 * 查询图书馆 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
    public List<LeaveSchool> queryLibraryStudentList(){
		StringBuffer hql = new StringBuffer(" from LeaveSchool t where 1=1  ");
		hql.append(" and (t.graduateStatus is null or t.graduateStatus='') ");//未毕业状态
//		hql.append(" and (upper(t.library) <> ? or t.library is null ) ");//
		List<LeaveSchool> list=this.query(hql.toString());
		return list;
	}

	/**
	 * 描述信息: 更新图书信息
	 * @param studentId
	 * @param statusStr
	 * 2016-5-20 下午2:47:40
	 */
	@Override
    public void updateLeaveLibrary(String studentId, String statusStr){
	    String hql = "update LeaveSchool set library = ?  where student.id = ?  ";
	    this.executeHql(hql, new Object[]{statusStr,studentId});
	    
    }
	
	/***
	 * 查询一卡通 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<StudentInfoModel> queryOneCardStudentList(){
		StringBuffer hql = new StringBuffer("select t.student from LeaveSchool t where 1=1  ");
		hql.append(" and (t.graduateStatus is null or t.graduateStatus='') ");//未毕业状态
		List<StudentInfoModel> list=this.query(hql.toString(), new Object[]{});
		return list;
	}
	
	/**
	 * 描述信息: 更新一卡通信息
	 * @param studentId
	 * @param statusStr
	 * 2016-5-20 下午2:47:40
	 */
    public void updateLeaveOneCard(String studentId, String money){
	    String hql = "update LeaveSchool set oneCard = ?  where student.id = ?  ";
	    this.executeHql(hql, new Object[]{money,studentId});
	    
    }
    
    /***
	 * 查询宿舍为退宿学生  2016-5-17
	 * @param leave
	 * @return
	 */
    public List<StudentInfoModel> queryDormStudentList(){
    	StringBuffer hql = new StringBuffer("select t.student from LeaveSchool t where 1=1  ");
		hql.append(" and (t.graduateStatus is null or t.graduateStatus='') ");//未毕业状态
		hql.append(" and (t.dorm <> ? or t.dorm is null) ");//为退宿
		List<StudentInfoModel> list=this.query(hql.toString(), new Object[]{"已退宿"});
		return list;
    }
    
    /**
	 * 描述信息: 更新住宿信息
	 * @param studentId
	 * @param statusStr
	 * 2016-5-20 下午2:47:40
	 */
    public void updateLeaveDorm(String studentId, String dorm){
	    String hql = "update LeaveSchool set dorm = ?  where student.id = ?  ";
	    this.executeHql(hql, new Object[]{dorm, studentId});
    }
}
