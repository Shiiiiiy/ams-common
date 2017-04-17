package com.uws.common.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.IEvaluationCommonDao;
import com.uws.common.service.IEvaluationCommonService;
import com.uws.common.util.SchoolYearUtil;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.DataUtil;
import com.uws.core.util.DateUtil;
import com.uws.domain.evaluation.EvaluationDetail;
import com.uws.domain.evaluation.EvaluationInfo;
import com.uws.domain.evaluation.EvaluationTerm;
import com.uws.domain.evaluation.EvaluationTime;
import com.uws.domain.evaluation.EvaluationUser;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

@Service("evaluationCommonService")
public class EvaluationCommonServiceImpl extends BaseServiceImpl implements IEvaluationCommonService {
	@Autowired
	public IEvaluationCommonDao evaluationCommonDao;
	
	//字典工具类
	private DicUtil dicUtil=DicFactory.getDicUtil();
	/***
	 * 查询单个学生的测评信息
	 * @param pageNum
	 * @param pageSize
	 * @param evaluation
	 * @return
	 */
	public Page queryEvaluationPage(int pageNum, int pageSize, String studentId){
		return this.evaluationCommonDao.queryEvaluationPage(pageNum, pageSize, studentId);
	}
	
	/***
	 * 根据学年、学生查询该学年这学生的测评成绩综合及各成绩的排名
	 * @param yearId
	 * @param student
	 * @return
	 */
	public Map<String,String> queryStudentEvaluationScore(String yearId, StudentInfoModel student){
		return this.evaluationCommonDao.queryStudentEvaluationScore(yearId, student);
	}

	/***
	 * 自动生成测评记录
	 */
	public void execEvaluation(){
		//StudentInfoModel student = this.evaluationCommonDao.getStudentById("2012030229");
		//Map<String,String> map=this.evaluationCommonDao.queryEvaluationScore("402891a44ed297d9014ed2a720df0004", student);
		List<EvaluationTime> evaluationTimeList=this.evaluationCommonDao.queryEvaluationTime(DateUtil.getCurDate());
		if(evaluationTimeList != null){
			for (Iterator iterator = evaluationTimeList.iterator(); iterator.hasNext();) {
				EvaluationTime evaluationTime = (EvaluationTime) iterator.next();
				Dic yearDic=SchoolYearUtil.getYearDic();//当前学年
				String collegeId=evaluationTime.getCollege().getId();
				String monthId=evaluationTime.getMonth().getId();
				String termId="";
				if(StringUtils.isNotEmpty(monthId)){
					EvaluationTerm term=this.evaluationCommonDao.queryEvaluationTermByMonthId(monthId);
					if(null != term){
						termId=term.getTerm().getId();
					}
				}
				if(null != yearDic){
					this.addCollegeEvaluation(yearDic.getId(), termId, monthId, collegeId);
				}
			}
		}
	}
	
	/***
	 * 生成该yearId、termId、monthId、学院所有成员的测评记录 
	 * @param yearId
	 * @param termId
	 * @param monthId
	 * @param classId
	 */
	private void addCollegeEvaluation(String yearId, String termId, String monthId, String collegeId){
		List<StudentInfoModel> studentList=this.evaluationCommonDao.queryStudentInfoByCollegeId(collegeId);
		Dic statusDic=this.dicUtil.getDicInfo("EVALUATION_STATUS", "SAVE");
		//生成测评记录 
		for (Iterator iterator = studentList.iterator(); iterator.hasNext();) {
			StudentInfoModel student = (StudentInfoModel) iterator.next();
			EvaluationInfo evaluation=this.evaluationCommonDao.getEvaluationInfo(yearId, monthId, student.getId());
			 if(!DataUtil.isNotNull(evaluation)){
				//生成记录
				evaluation=new EvaluationInfo(); 
				List<Dic> baseTypeList=this.dicUtil.getDicInfoList("EVALUATION_BASE_TYPE");//测评分基础类型 
				Dic yearDic=new Dic();
				yearDic.setId(yearId);
				evaluation.setYear(yearDic);
				
				Dic termDic=new Dic();
				termDic.setId(termId);
				evaluation.setTerm(termDic);
				
				Dic monthDic=new Dic();
				monthDic.setId(monthId);
				evaluation.setMonth(monthDic); 
				
				evaluation.setStudent(student);
				EvaluationUser evaluationUser = this.evaluationCommonDao.queryEvaluationUser(student.getClassId().getId());
				if(null != evaluationUser){
					evaluation.setAssist(evaluationUser.getAssist());
				}
				
				evaluation.setStatus(statusDic);//保存状态
				evaluation.setMoralScoreSum("0");
				evaluation.setCapacityScoreSum("0");
				evaluation.setCultrueScoreSum("0");
				evaluation.setIntellectScoreSum("0");
				this.evaluationCommonDao.saveEvaluation(evaluation);//保存测评基础信息
				
				int seqNum=0;
				
				for(int j=0;j<baseTypeList.size();j++,seqNum++){
					Dic dic = (Dic) baseTypeList.get(j);
					EvaluationDetail detail=new EvaluationDetail();
					detail.setReason("");
					detail.setEvaluation(evaluation);
					detail.setType(dic);
					detail.setSeqNum(seqNum);
					this.evaluationCommonDao.saveEvaluationDetail(detail);
				}
			 }
		}
	}
	
	/***
	 * 根据学年、学生查询该学年这学生的测评成绩(困难生使用)
	 * @param yearId 学年id
	 * @param student 学生对象
	 * @return
	 */
	public Map<String,String> queryEvaluationScore(String yearId, StudentInfoModel student){
		return this.evaluationCommonDao.queryEvaluationScore(yearId, student);
	}
}
