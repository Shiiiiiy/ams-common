package com.uws.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.common.dao.IScoreDao;
import com.uws.common.service.IScoreService;
import com.uws.webservice.IStudentScoreServcie;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.util.DataUtil;
import com.uws.domain.common.StudentTermScore;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;

/**
 * 
* @ClassName: ScoreServiceImpl 
* @Description: 成绩信息处理
* @author 联合永道
* @date 2015-12-4 下午1:56:43 
*
 */
@Service("com.uws.common.service.impl.ScoreServiceImpl")
public class ScoreServiceImpl extends BaseServiceImpl implements IScoreService{
	@Autowired
	public IScoreDao scoreDao;
	
	@Autowired
	private IStudentScoreServcie studentScoreService;
	//字典工具类
	private DicUtil dicUtil=DicFactory.getDicUtil();
	/**
	 * 描述信息: 学生学期的平均成绩
	 * @param studentId
	 * @param yearDic
	 * @param termDic
	 * @return
	 * 2015-12-4 下午2:11:49
	 */
	@Override
    public String queryTermAvgScore(String studentId, Dic yearDic, Dic termDic){
		if(DataUtil.isNotNull(studentId) && DataUtil.isNotNull(yearDic) && DataUtil.isNotNull(termDic)){
			StudentTermScore score = this.scoreDao.queryStudentTermScore(studentId, yearDic.getId(), termDic.getId());
			if(score != null){
				return score.getAveScore();
			}else{
				return null;
			}
		}else{
			return null;
		}
    }

	/**
	 * 描述信息: 学生学期的总成绩
	 * @param studentId
	 * @param yearDic
	 * @param termDic
	 * @return
	 * 2015-12-4 下午2:12:03
	 */
	@Override
    public String queryTermTotalScore(String studentId, Dic yearDic, Dic termDic){
		if(DataUtil.isNotNull(studentId) && DataUtil.isNotNull(yearDic) && DataUtil.isNotNull(termDic)){
			StudentTermScore score = this.scoreDao.queryStudentTermScore(studentId, yearDic.getId(), termDic.getId());
			if(score != null){
				return score.getTotalScore();
			}else{
				return null;
			}
		}else{
			return null;
		}
    }

	/**
	 * 描述信息: 学生学年的平均成绩
	 * @param studentId
	 * @param yearDic
	 * @return
	 * 2015-12-4 下午2:12:15
	 */
	@Override
    public String queryYearAvgScore(String studentId, Dic yearDic){
	    // TODO Auto-generated method stub
		List<Dic> termList = this.dicUtil.getDicInfoList("TERM");	//学期
		float aveScore = 0;
		Boolean flag = true;
		String message="不符合条件,";
		for (Dic termDic : termList) {
			String score = this.queryTermAvgScore(studentId, yearDic, termDic);
			if(null != score){
				aveScore += Float.parseFloat(score);
			}else{
				flag = false;
				message += termDic.getName()+",";
			}
		}
		message += "没有成绩！";
		if(flag){
			return (aveScore/2)+"";
		}else{
			return message;
		}
    }

	/**
	 * 描述信息: 学生学年的总成绩
	 * @param studentId
	 * @param yearDic
	 * @return
	 * 2015-12-4 下午2:12:25
	 */
	@Override
    public String queryYearTotalScore(String studentId, Dic yearDic)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	/**
	 * 描述信息: 学生的体育成绩查询，现在是学生只有大一才有体育课，所以先不用学年学期的条件
	 * @param studentId
	 * @return
	 * 2015-12-4 下午2:12:39
	 */
	@Override
    public String queryStudentSportScore(String studentId, String yearCode){
		return this.scoreDao.queryStudentSportScore(studentId, yearCode);
    }

	/**
	 * 描述信息:  查询对低分， termDic为null查询 学年最低分，不为空查询学期最低分
	 * @param studentId
	 * @param yearDic
	 * @param termDic
	 * @return
	 * 2015-12-4 下午2:12:51
	 */
	@Override
    public String queryStudentLowScore(String studentId, Dic yearDic,Dic termDic){
		return this.scoreDao.queryStudentLowScore(studentId, yearDic, termDic);
    }

	/**
	 * 描述信息: 查询学生学年的成绩排名：  10：班级排名；20：专业排名
	 * @param studentId
	 * @param yearDic
	 * @param type
	 * @return
	 * 2015-12-4 下午2:13:02
	 */
	@Override
    public String queryStudentRank(String studentId, Dic yearDic, String type)
    {
	    // TODO Auto-generated method stub
	    return null;
    }
	
	/***
	 * 查询所有的学生（分数同步使用）
	 * @return
	 */
	public List<StudentInfoModel> queryStudentList(){
		return this.scoreDao.queryStudentList();
	}

	/***
	 * 学期分数同步
	 */
	public void syncStuTermScore(StudentInfoModel student){
		List<Dic> schoolYearList = this.dicUtil.getDicInfoList("YEAR");	//学年
		List<Dic> termList = this.dicUtil.getDicInfoList("TERM");	//学期
		for (Dic yearDic : schoolYearList) {
			for (Dic termDic : termList) {
				//通过 stuid、yearid、termid查分数表
				StudentTermScore score = this.scoreDao.queryStudentTermScore(student.getId(), yearDic.getId(), termDic.getId());
				if(score == null){
					//学期 转换：编号0：第一学期，编号1：第二学期
					String termCode = "1".equals(termDic.getCode())?"0":"1";
					//无  通过webservice接口结果   
					List<String> result = studentScoreService.getStudentAverageScore(yearDic.getCode(), termCode, student.getStuNumber(), "201611010920");
					//判断结果   取出分数插入
					score = new StudentTermScore();
					if(result != null && !("0".equals(result.get(0))) && DataUtil.isNotNull(result.get(2))){
						score.setYear(yearDic);
						score.setTerm(termDic);
						score.setStudent(student);
						score.setTotalScore(result.get(2));
						score.setTotalSubjects(result.get(3));
						score.setAveScore(result.get(4));
						this.scoreDao.save(score);
					}else{
						System.out.println(student.getName()+yearDic.getName()+termDic.getName()+"没有分数!\n");
					}
				}
			}
		}
	}
	
	/***
	 * 判断是否有不通过的科目
	 * @param studentId
	 * @param yearCode
	 * @return
	 */
	@Override
	public boolean checkCoursesPass(String studentId, String yearCode){
		List list = this.scoreDao.queryCoursesNotPass(studentId, yearCode);
		if(list != null && list.size()>0){
			//存在不及格科目
			return false;
		}else{
			//全部及格
			return true;
		}
	}
}
