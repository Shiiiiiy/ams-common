
package com.uws.common.service;

import java.util.List;

import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.domain.reward.AwardInfo;
import com.uws.domain.reward.AwardType;
import com.uws.domain.reward.CollegeAwardInfo;
import com.uws.domain.reward.CountryBurseInfo;
import com.uws.domain.reward.PunishInfo;
import com.uws.domain.reward.StudentApplyInfo;

/** 
 * @ClassName: IRewardCommonService 
 * @Description:  
 * @author zhangyb 
 * @date 2015年9月21日 上午10:19:42  
 */
public interface IRewardCommonService {

	/** 
	* @Title: getStuAwardList 
	* @Description:  获取该学生所有评奖评优信息
	* @param  @param student
	* @param  @return    
	* @return List<StudentApplyInfo>    
	* @throws 
	*/
	public List<StudentApplyInfo> getStuAwardList(StudentInfoModel student);
	
	/** 
	* @Title: getStuPunishList 
	* @Description:  获取该学生所有惩罚信息
	* @param  @param student
	* @param  @return    
	* @return List<PunishInfo>    
	* @throws 
	*/
	public List<PunishInfo> getStuPunishList(StudentInfoModel student);
	
	/** 
	* @Title: getStuBurseList 
	* @Description:  获取该学生所有国家奖助信息
	* @param  @param student
	* @param  @return    
	* @return List<CountryBurseInfo>    
	* @throws 
	*/
	public List<CountryBurseInfo> getStuBurseList(StudentInfoModel student);
	
	/** 
	* @Title: getAwardTypeById 
	* @Description:  通过ID获取awardType对象
	* @param  @param id
	* @param  @return    
	* @return AwardType    
	* @throws 
	*/
	public AwardType getAwardTypeById(String id);
	
	/** 
	* @Title: getAwardInfoById 
	* @Description:  通过ID获取AwardInfo对象
	* @param  @param id
	* @param  @return    
	* @return AwardInfo    
	* @throws 
	*/
	public AwardInfo getAwardInfoById(String id);
	
	/** 
	* @Title: checkStuGetAwardOrNot 
	* @Description: 判断毕业生是否获得过评奖评优 true为是 false 为否
	* @param  @param student
	* @return boolean    
	* @throws 
	*/
	public boolean checkStuGetAwardOrNot(StudentInfoModel student);
	
	/** 
	* @Title: checkStuGetAwardOrNot 
	* @Description: 判断毕业生是否获得过 国家奖助 true为是 false 为否
	* @param  @param student
	* @return boolean    
	* @throws 
	*/
	public boolean checkStuGetBurseOrNot(StudentInfoModel student);
	
	/** 
	* @Title: getGraduateStuAwardList 
	* @Description: 获取获得评奖评优的毕业生 
	* @param  @param schoolYear
	* @param  @return    
	* @return List<StudentInfoModel>    
	* @throws 
	*/
	public Page getGraduateStuAwardPage(StudentInfoModel stuInfo,int pageNo,int pageSize);
	
	/** 
	* @Title: getStuAllAwardPage 
	* @Description: 获取获得评奖评优或者国家奖助的毕业生 
	* @param  @param stuInfo
	* @param  @param pageNo
	* @param  @param pageSize
	* @param  @return    
	* @return Page    
	* @throws 
	*/
	public Page getStuAllAwardPage(StudentInfoModel stuInfo,int pageNo,int pageSize);
	
	/** 
	* @Title: getStuCollegeAwardList 
	* @Description: 获取学生校内奖励list
	* @param  @param student
	* @param  @return    
	* @return List<CollegeAwardInfo>    
	* @throws 
	*/
	public List<CollegeAwardInfo> getStuCollegeAwardList(StudentInfoModel student);
	
	/** 
	* @Title: checkStuGetCollegeOrNot 
	* @Description: 判断毕业生是否获得过 校内奖励 true为是 false 为否
	* @param  @param student
	* @param  @return    
	* @return boolean    
	* @throws 
	*/
	public boolean checkStuGetCollegeOrNot(StudentInfoModel student);
	
	
}