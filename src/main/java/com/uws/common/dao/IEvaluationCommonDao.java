package com.uws.common.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.evaluation.EvaluationDetail;
import com.uws.domain.evaluation.EvaluationInfo;
import com.uws.domain.evaluation.EvaluationTerm;
import com.uws.domain.evaluation.EvaluationTime;
import com.uws.domain.evaluation.EvaluationUser;
import com.uws.domain.orientation.StudentInfoModel;

public interface IEvaluationCommonDao {
	/***
	 * 查询单个学生的测评信息
	 * @param pageNum
	 * @param pageSize
	 * @param evaluation
	 * @return
	 */
	public Page queryEvaluationPage(int pageNum, int pageSize, String studentId);
	
	/***
	 * 根据学年、学生查询该学年这学生的测评成绩综合及各成绩的排名
	 * @param yearId
	 * @param student
	 * @return
	 */
	public Map<String,String> queryStudentEvaluationScore(String yearId, StudentInfoModel student);
	
	/***
	 * 获取测评月份设置
	 * @return
	 */
	public List<EvaluationTime> queryEvaluationTime(String date);

	/***
	 * 通过测评月份查询测评基础学期
	 * @return
	 */
	public EvaluationTerm queryEvaluationTermByMonthId(String monthId);
	
	/***
	 * 查询学院的所有学生
	 * @param klassId
	 * @param province
	 * @param flag
	 * @return
	 */
	public List<StudentInfoModel> queryStudentInfoByCollegeId(String collegeId);
	
	/***
	 * 通过学年测评、月份、用户查询测评记录
	 * @param year
	 * @param month
	 * @param user
	 * @return
	 */
	public EvaluationInfo getEvaluationInfo(String year, String month, String user);
	
	/***
	 * 获取班级测评人
	 * @param classId
	 * @return
	 */
	public EvaluationUser queryEvaluationUser(String classId);
	
	/***
	 * 保存测评记录 
	 * @param evaluation
	 */
	public void saveEvaluation(EvaluationInfo evaluation);
	
	/***
	 * 保存测评记录 
	 * @param detail
	 */
	public void saveEvaluationDetail(EvaluationDetail detail);
	
	/***
	 * 根据学年、学生查询该学年这学生的测评成绩(困难生使用)
	 * @param yearId 学年id
	 * @param student 学生对象
	 * @return
	 */
	public Map<String,String> queryEvaluationScore(String yearId, StudentInfoModel student);
	
	/**
	 * 根据记录号（id）进行学生基本信息查询
	 * 
	 * @param id
	 *            学生基本信息记录号
	 * @return 提前离校申请Po
	 */
	public StudentInfoModel getStudentById(String id);
}
