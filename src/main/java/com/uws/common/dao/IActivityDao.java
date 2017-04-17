package com.uws.common.dao;

import java.util.List;

import com.uws.domain.association.AssociationMemberModel;
import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.domain.association.AssociationBaseinfoModel;
import com.uws.domain.league.LeagueMemberInfoModel;
import com.uws.domain.volunteer.VolunteerBaseinfoModel;
import com.uws.domain.volunteer.VolunteerOfficeModel;

public interface IActivityDao extends IBaseDao{

	/**
	 * 根据学生id获取学生参加的社团列表
	 * @param stuId	学生id
	 * @return	学生参加社团列表
	 */
	public List<AssociationBaseinfoModel> queryAssociationByMemberId(String stuId);
	/**
	 * 根据学生id获取社团负责人列表
	 * @param stuId	学生id
	 * @return	社团负责人列表
	 */
	public List<AssociationMemberModel> queryAssociationMemberByMemberId(String stuId);

	/**
	 * 根据学生id获取志愿者信息
	 * @param stuId	学生id
	 * @return	志愿者信息
	 */
    public VolunteerBaseinfoModel queryVolunteer(String stuId);
     /**
	 * 根据学生id获取团员信息
	 * @param stuId	学生id
	 * @return	团员信息
	 */
    public LeagueMemberInfoModel queryLeagueMember(String stuId);
    /**
     *获取志愿者基地信息(根据学院)
     * @param collegeId
     * @return
     */
    public List<VolunteerOfficeModel> queryVolunteerOfficeList(String collegeId);
    /**
     * 获取志愿者基地信息已在活动中被使用的基地
     * @return
     */
	public List<String> queryActivityVolunteerOfficeList();
}
