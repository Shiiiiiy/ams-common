package com.uws.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.ILeaveCommonDao;
import com.uws.common.service.ILeaveCommonService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.domain.leave.LeaveSchool;
import com.uws.domain.orientation.StudentInfoModel;

@Service("leaveCommonService")
public class LeaveCommonServiceImpl extends BaseServiceImpl implements ILeaveCommonService {
	
	@Autowired
	private ILeaveCommonDao leaveCommonDao;

	/***
	 * 查询计财处 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
	public List<StudentInfoModel> queryFinanceStudentList(){
		return this.leaveCommonDao.queryFinanceStudentList();
	}
	
	/****
	 * 计财处 离校手续 2016-5-17
	 * @param studentId
	 * 状态 ：0 未办 ;1 已办   
	 */
	public void operateFinance(String studentId, String status){
		this.leaveCommonDao.updateLeaveFinance(studentId, status);
	}
	
	/***
	 * 查询图书馆 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
	public List<LeaveSchool> queryLibraryStudentList(){
		return this.leaveCommonDao.queryLibraryStudentList();
	}
	
	/****
	 * 图书馆 离校手续 2016-5-17
	 * @param studentId
	 * 状态 ：true 已办; 其他:未办   
	 */
	public void operateLibrary(String studentId, String status){
		this.leaveCommonDao.updateLeaveLibrary(studentId, status);
	}
	
	/***
	 * 查询一卡通 未办理学生  2016-5-17
	 * @param leave
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<StudentInfoModel> queryOneCardStudentList(){
		return this.leaveCommonDao.queryOneCardStudentList();
	}

	/**
	 * 描述信息: 更新一卡通信息
	 * @param studentId
	 * @param statusStr
	 * 2016-5-20 下午2:47:40
	 */
    public void updateLeaveOneCard(String studentId, String money){
    	this.leaveCommonDao.updateLeaveOneCard(studentId, money);
    }
    
    /***
	 * 查询宿舍为退宿学生  2016-5-17
	 * @param leave
	 * @return
	 */
    public List<StudentInfoModel> queryDormStudentList(){
    	return this.leaveCommonDao.queryDormStudentList();
    }
    
    /**
	 * 描述信息: 更新住宿信息
	 * @param studentId
	 * @param statusStr
	 * 2016-5-20 下午2:47:40
	 */
    public void updateLeaveDorm(String studentId, String dorm){
    	this.leaveCommonDao.updateLeaveDorm(studentId, dorm);
    }
}
