package com.uws.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uws.common.dao.IRewardCommonDao;
import com.uws.common.service.IRewardCommonService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.domain.reward.AwardInfo;
import com.uws.domain.reward.AwardType;
import com.uws.domain.reward.CollegeAwardInfo;
import com.uws.domain.reward.CountryBurseInfo;
import com.uws.domain.reward.PunishInfo;
import com.uws.domain.reward.StudentApplyInfo;

/** 
 * @ClassName: RewardCommonServiceImpl 
 * @Description:  奖惩管理公共service
 * @author zhangyb 
 * @date 2015年9月21日 上午10:21:24  
 */
@Repository("rewardCommonService")
public class RewardCommonServiceImpl extends BaseServiceImpl implements
		IRewardCommonService {

	@Autowired
	private IRewardCommonDao rewardCommonDao;
	/* (非 Javadoc) 
	* <p>Title: getStuAwardList</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.service.IRewardCommonService#getStuAwardList(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public List<StudentApplyInfo> getStuAwardList(StudentInfoModel student) {
		StudentApplyInfo stuApply = new StudentApplyInfo();
		stuApply.setStudentId(student);
		return this.rewardCommonDao.getStuInfoList(stuApply);
	}

	/* (非 Javadoc) 
	* <p>Title: getStuPunishList</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.service.IRewardCommonService#getStuPunishList(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public List<PunishInfo> getStuPunishList(StudentInfoModel student) {
		return this.rewardCommonDao.getStuPunishList(student);
	}

	/* (非 Javadoc) 
	* <p>Title: getStuBurseList</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.service.IRewardCommonService#getStuBurseList(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public List<CountryBurseInfo> getStuBurseList(StudentInfoModel student) {
		return this.rewardCommonDao.getStuBurseList(student);
	}

	/* (非 Javadoc) 
	* <p>Title: getAwardTypeById</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see com.uws.common.service.IRewardCommonService#getAwardTypeById(java.lang.String) 
	*/
	@Override
	public AwardType getAwardTypeById(String id) {
		return this.rewardCommonDao.getAwardTypeById(id);
	}

	/* (非 Javadoc) 
	* <p>Title: getAwardInfoById</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see com.uws.common.service.IRewardCommonService#getAwardInfoById(java.lang.String) 
	*/
	@Override
	public AwardInfo getAwardInfoById(String id) {
		return this.rewardCommonDao.getAwardInfoById(id);
	}

	/* (非 Javadoc) 
	* <p>Title: checkStuGetAwardOrNot</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.service.IRewardCommonService#checkStuGetAwardOrNot(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public boolean checkStuGetAwardOrNot(StudentInfoModel student) {
		return this.rewardCommonDao.checkStuGetAwardOrNot(student);
	}

	/* (非 Javadoc) 
	* <p>Title: getGraduateStuAwardList</p> 
	* <p>Description: </p> 
	* @param schoolYear
	* @return 
	* @see com.uws.common.service.IRewardCommonService#getGraduateStuAwardList(com.uws.sys.model.Dic) 
	*/
	@Override
	public Page getGraduateStuAwardPage(StudentInfoModel stuInfo,int pageNo, int pageSize) {
		return this.rewardCommonDao.getGraduateStuAwardPage(stuInfo,pageNo, pageSize);
	}

	/* (非 Javadoc) 
	* <p>Title: getStuAllAwardPage</p> 
	* <p>Description: </p> 
	* @param stuInfo
	* @param pageNo
	* @param pageSize
	* @return 
	* @see com.uws.common.service.IRewardCommonService#getStuAllAwardPage(com.uws.domain.orientation.StudentInfoModel, int, int) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public Page getStuAllAwardPage(StudentInfoModel stuInfo, int pageNo,
			int pageSize) {
		int applyNum = (int) this.rewardCommonDao.countStuApply();
		Page awardPage = this.getGraduateStuAwardPage(stuInfo, 1, applyNum);
		List<StudentInfoModel> applyList = (List<StudentInfoModel>) awardPage.getResult();
		List<StudentInfoModel> stuList = this.rewardCommonDao.getGraduateStuBurseList(stuInfo);
		List<StudentInfoModel> collegeList = this.rewardCommonDao.getGraduateStuCollegeList(stuInfo);
		stuList.addAll(collegeList);
		for(StudentInfoModel stu : stuList) {
			if(!applyList.contains(stu)) {
				applyList.add(stu);
			}
		}
		Page dataPage = new Page();
		List<StudentInfoModel> curPageList = new ArrayList<StudentInfoModel>();
		if(applyList.size() < pageNo*pageSize) {
			curPageList = applyList.subList((pageNo-1)*pageSize, applyList.size());
		}else{
			curPageList = applyList.subList((pageNo-1)*pageSize, pageNo*pageSize);
		}
		dataPage.setResult(curPageList);
		dataPage.setTotalCount(applyList.size());
		dataPage.setPageSize(pageSize);
		dataPage.setStart((pageNo-1)*pageSize);
		return dataPage;
	}

	/* (非 Javadoc) 
	* <p>Title: getStuCollegeAwardList</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.service.IRewardCommonService#getStuCollegeAwardList(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public List<CollegeAwardInfo> getStuCollegeAwardList(
			StudentInfoModel student) {
		return this.rewardCommonDao.getStuCollegeAwardList(student);
	}

	/* (非 Javadoc) 
	* <p>Title: checkStuGetBurseOrNot</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.service.IRewardCommonService#checkStuGetBurseOrNot(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public boolean checkStuGetBurseOrNot(StudentInfoModel student) {
		return this.rewardCommonDao.checkStuGetBurseOrNot(student);
	}

	/* (非 Javadoc) 
	* <p>Title: checkStuGetCollegeOrNot</p> 
	* <p>Description: </p> 
	* @param student
	* @return 
	* @see com.uws.common.service.IRewardCommonService#checkStuGetCollegeOrNot(com.uws.domain.orientation.StudentInfoModel) 
	*/
	@Override
	public boolean checkStuGetCollegeOrNot(StudentInfoModel student) {
		return this.rewardCommonDao.checkStuGetCollegeOrNot(student);
	}


	
}
