package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.uws.common.dao.IActivityDao;
import com.uws.common.util.Constants;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.util.DataUtil;
import com.uws.domain.association.AssociationBaseinfoModel;
import com.uws.domain.association.AssociationMemberModel;
import com.uws.domain.league.LeagueMemberInfoModel;
import com.uws.domain.volunteer.VolunteerBaseinfoModel;
import com.uws.domain.volunteer.VolunteerOfficeModel;

@Repository("com.uws.common.dao.impl.ActivityDaoImpl")
public class ActivityDaoImpl extends BaseDaoImpl implements IActivityDao{
	/**
	 * 通过学生id查询该负责人所申请的社团
	 */
	@Override
	public List<AssociationBaseinfoModel> queryAssociationByMemberId(String stuId) {
		if(DataUtil.isNotNull(stuId)){
			List<Object> values = new ArrayList<Object>();
			/*StringBuffer hql = new StringBuffer("select amm.associationPo from AssociationMemberModel amm where 1=1 ");
			if(stuId!=null && StringUtils.isNotBlank(stuId))
			{
				//当前用户是社团负责人
				hql.append(" and amm.memberPo.id=? and amm.isManager.id= ?");
				values.add(stuId);
				values.add(Constants.STATUS_YES.getId());
			}
			//社团没有注销
			hql.append(" and amm.associationPo.isCancel.id=?");
			values.add(Constants.STATUS_NO.getId());
			//社团状态为有效状态
			hql.append(" and amm.associationPo.isValid.id=?");
			values.add(Constants.STATUS_YES.getId());
			//社团删除状态
			hql.append(" and amm.associationPo.deleteStatus.id=?");
			values.add(Constants.STATUS_NORMAL.getId());
			
			hql.append(" order by amm.associationPo.updateTime desc");*/
			StringBuffer hql = new StringBuffer(" from AssociationBaseinfoModel amm where 1=1 ");
			//社团没有注销
			hql.append(" and amm.isCancel.id=?");
			values.add(Constants.STATUS_NO.getId());
			//社团状态为有效状态
			hql.append(" and amm.isValid.id=?");
			values.add(Constants.STATUS_YES.getId());
			//社团删除状态
			hql.append(" and amm.deleteStatus.id=?");
			values.add(Constants.STATUS_NORMAL.getId());
			
			hql.append(" order by amm.updateTime desc");
			
			return this.query(hql.toString(), values.toArray());
		}else{
			return new ArrayList<AssociationBaseinfoModel>();
		}
	}
	/**
	 * 根据学生id获取社团负责人列表
	 * @param stuId	学生id
	 * @return	社团负责人列表
	 */
	@Override
	public List<AssociationMemberModel> queryAssociationMemberByMemberId(String stuId) {
		if(DataUtil.isNotNull(stuId)){
			List<Object> values = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer("select amm from AssociationMemberModel amm where 1=1 ");
			
			//当前用户是社团负责人
			hql.append(" and amm.memberPo.id=? and amm.isManager.id= ?");
			values.add(stuId);
			values.add(Constants.STATUS_YES.getId());
			//社团没有注销
			hql.append(" and amm.associationPo.isCancel.id=?");
			values.add(Constants.STATUS_NO.getId());
			//社团状态为有效状态
			hql.append(" and amm.associationPo.isValid.id=?");
			values.add(Constants.STATUS_YES.getId());
			//社团删除状态
			hql.append(" and amm.associationPo.deleteStatus.id=?");
			values.add(Constants.STATUS_NORMAL.getId());
			
			hql.append(" order by amm.associationPo.updateTime desc");
			
			return this.query(hql.toString(), values.toArray());
		}else{
			return new ArrayList<AssociationMemberModel>();
		}
	}
	/**
	 * 根据学生id获取志愿者信息
	 * @param stuId	学生id
	 * @return	志愿者信息
	 */
	@Override
    public VolunteerBaseinfoModel queryVolunteer(String stuId){
		return (VolunteerBaseinfoModel)this.queryUnique(" from VolunteerBaseinfoModel v where 1=1 and v.stuInfo.id = ? and v.approveResult.code = ?",stuId,"PASS");
	}
    /**
	 * 根据学生id获取团员信息
	 * @param stuId	学生id
	 * @return	团员信息
	 */
	@Override
    public LeagueMemberInfoModel queryLeagueMember(String stuId){
		return (LeagueMemberInfoModel)this.queryUnique(" from LeagueMemberInfoModel l where 1=1 and l.stuInfo.id = ? and l.deleteStatus.id = ? "
				+ "and (l.memberType.id = ? or l.memberType.id = ? or l.memberType.id = ?)",stuId,Constants.STATUS_NORMAL.getId(),Constants.STATUS_PARTY_DICS.getId(),
				Constants.STATUS_PROBATIONARY_DICS.getId(),Constants.STATUS_LEAGUEMEMBER_DICS.getId());
	}
	 /**
     * 获取志愿者基地信息(根据学院)
     * @param collegeId
     * @return
     */
	@Override
	public List<VolunteerOfficeModel> queryVolunteerOfficeList(String collegeId) {
		
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from VolunteerOfficeModel v where 1=1 ");
		hql.append(" and v.deleteStatus.id=?");
		values.add(Constants.STATUS_NORMAL.getId());
		if(DataUtil.isNotNull(collegeId)){	
			//学院id不为空
			hql.append(" and v.college.id=?");
			values.add(collegeId);
		}
			
		if (values.size() == 0){
			return this.query(hql.toString(), new Object[0]);
		}else{
			return this.query(hql.toString(), values.toArray());
		}
		
	}
	 /**
     * 获取志愿者基地信息已在活动中被使用的基地
     * @return
     */
	@Override
	public List<String> queryActivityVolunteerOfficeList() {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select a.volunteerAddress.id from ActivityBaseinfoModel a where 1=1 ");
		hql.append(" and a.deleteStatus.id=? and a.volunteerAddress.id is not null group by a.volunteerAddress.id");
		values.add(Constants.STATUS_NORMAL.getId());
		if (values.size() == 0){
			return this.query(hql.toString(), new Object[0]);
		}else{
			return this.query(hql.toString(), values.toArray());
		}
	}
}
