package com.uws.common.service;

import java.util.List;

import com.uws.core.base.IBaseService;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;

/**
 * 
* @ClassName: IScoreService 
* @Description: 成绩信息处理
* @author 联合永道
* @date 2015-12-4 下午1:55:50 
*
 */
public interface IScoreService extends IBaseService
{
	/**
	 * @Title: queryTermAvgScore
	 * @Description: 学生学期的平均成绩
	 * @param studentId
	 * @param yearDic
	 * @param termDic
	 * @return
	 * @throws
	 */
	public String queryTermAvgScore(String studentId,Dic yearDic,Dic termDic);
	
	/**
	 * @Title: queryTermTotalScore
	 * @Description: 学生学期的总成绩
	 * @param studentId
	 * @param yearDic
	 * @param termDic
	 * @return
	 * @throws
	 */
	public String queryTermTotalScore(String studentId,Dic yearDic,Dic termDic);
	
	/**
	 * @Title: queryYearAvgScore
	 * @Description: 学生学年的平均成绩
	 * @param studentId
	 * @param yearDic
	 * @return
	 * @throws
	 */
	public String queryYearAvgScore(String studentId,Dic yearDic);
	
	/**
	 * @Title: queryYearAvgScore
	 * @Description: 学生学年的总成绩
	 * @param studentId
	 * @param yearDic
	 * @return
	 * @throws
	 */
	public String queryYearTotalScore(String studentId,Dic yearDic);
	
	/**
	 * @Title: queryStudentSportScore
	 * @Description: 学生的体育成绩查询，现在是学生只有大一才有体育课，所以先不用学年学期的条件
	 * @param studentId
	 * @return
	 * @throws
	 */
	public String queryStudentSportScore(String studentId, String yearCode);
	
	/**
	 * 
	 * @Title: queryStudentLowScore
	 * @Description: 查询对低分， termDic为null查询 学年最低分，不为空查询学期最低分
	 * @param studentId
	 * @param yearDic
	 * @param termDic
	 * @return
	 * @throws
	 */
	public String queryStudentLowScore(String studentId,Dic yearDic,Dic termDic);
	
	/**
	 * 
	 * @Title: queryStudentRank
	 * @Description: 查询学生学年的成绩排名：  10：班级排名；20：专业排名
	 * @param studentId
	 * @param termDic
	 * @param type
	 * @return
	 * @throws
	 */
	public String queryStudentRank(String studentId,Dic yearDic,String type);
	
	/***
	 * 学期分数同步
	 * @return
	 */
	public void syncStuTermScore(StudentInfoModel student);
	
	/***
	 * 查询所有的学生（分数同步使用）
	 * @return
	 */
	public List<StudentInfoModel> queryStudentList();
	
	/***
	 * 判断是否有不通过的科目
	 * @param studentId
	 * @param yearCode
	 * @return
	 */
	public boolean checkCoursesPass(String studentId, String yearCode);
}
