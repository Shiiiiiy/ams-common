package com.uws.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.IActivityDao;
import com.uws.common.service.IActivityService;
import com.uws.common.service.IBaseDataService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.domain.association.AssociationBaseinfoModel;
import com.uws.domain.association.AssociationMemberModel;
import com.uws.domain.league.LeagueMemberInfoModel;
import com.uws.domain.volunteer.VolunteerBaseinfoModel;
import com.uws.domain.volunteer.VolunteerOfficeModel;

@Service("com.uws.common.service.impl.ActivityServiceImpl")
public class ActivityServiceImpl extends BaseServiceImpl implements IActivityService{
	
	@Autowired
	private IActivityDao activityDao;
	
	/**
	 * 根据学生id获取学生参加的社团列表
	 * @param stuId	学生id
	 * @return	学生参加社团列表
	 */
	@Override
	public List<AssociationBaseinfoModel> queryAssociationByMemberId(String stuId){
		
		return this.activityDao.queryAssociationByMemberId(stuId);
	}
	/**
	 * 根据学生id获取社团负责人列表
	 * @param stuId	学生id
	 * @return	社团负责人列表
	 */
	@Override
	public List<AssociationMemberModel> queryAssociationMemberByMemberId(String stuId){
		return this.activityDao.queryAssociationMemberByMemberId(stuId);
	}
	/**
	 * 根据学生id获取志愿者信息
	 * @param stuId	学生id
	 * @return	志愿者信息
	 */
	@Override
    public VolunteerBaseinfoModel queryVolunteer(String stuId){
		return activityDao.queryVolunteer(stuId);
	}
    /**
	 * 根据学生id获取团员信息
	 * @param stuId	学生id
	 * @return	团员信息
	 */
	@Override
    public LeagueMemberInfoModel queryLeagueMember(String stuId){
		return activityDao.queryLeagueMember(stuId);
	}
	 /**
     * 获取志愿者基地信息(根据学院)
     * @param collegeId
     * @return
     */
	@Override
	public List<VolunteerOfficeModel> queryVolunteerOfficeList(String collegeId) {
		
		return this.activityDao.queryVolunteerOfficeList(collegeId);
	}
	 /**
     * 获取志愿者基地信息已在活动中被使用的基地
     * @return
     */
	@Override
	public List<String> queryActivityVolunteerOfficeList() {
		
		return this.activityDao.queryActivityVolunteerOfficeList();
	}
	
}
