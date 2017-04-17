package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.domain.leave.LeaveSchool;
import com.uws.domain.orientation.StudentInfoModel;

public interface ILeaveCommonDao extends IBaseDao{
	/***
	 * 查询计财处 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
	public List<StudentInfoModel> queryFinanceStudentList();
	
	/***
	 * 查询学生离校情况 2016-5-17
	 * @param studentId
	 * @return
	 */
	public LeaveSchool queryLeaveSchoolByStudentId(String studentId);
	
	/****
	 * 计财处 离校手续 2016-5-17
	 * @param studentId
	 * 状态 ：0 未办 ;1 已办   
	 */
	public void updateLeaveFinance(String studentId, String status);
	
	public void updateLeaveLibrary(String studentId , String statusStr);
	
	/***
	 * 查询图书馆 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
	public List<LeaveSchool> queryLibraryStudentList();
	
	/***
	 * 查询一卡通 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<StudentInfoModel> queryOneCardStudentList();
	
	/**
	 * 描述信息: 更新一卡通信息
	 * @param studentId
	 * @param statusStr
	 * 2016-5-20 下午2:47:40
	 */
    public void updateLeaveOneCard(String studentId, String money);
    
    /***
	 * 查询宿舍为退宿学生  2016-5-17
	 * @param leave
	 * @return
	 */
    public List<StudentInfoModel> queryDormStudentList();
    
    /**
	 * 描述信息: 更新住宿信息
	 * @param studentId
	 * @param statusStr
	 * 2016-5-20 下午2:47:40
	 */
    public void updateLeaveDorm(String studentId, String dorm);
}
