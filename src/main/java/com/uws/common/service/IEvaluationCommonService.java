package com.uws.common.service;

import java.util.List;
import java.util.Map;

import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.evaluation.EvaluationTime;
import com.uws.domain.orientation.StudentInfoModel;

public interface IEvaluationCommonService {
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
	 * @param yearId 学年id
	 * @param student 学生对象
	 * @return
	 */
	public Map<String,String> queryStudentEvaluationScore(String yearId, StudentInfoModel student); 

	/***
	 * 自动生成测评记录
	 */
	public void execEvaluation();
	
	/***
	 * 根据学年、学生查询该学年这学生的测评成绩(困难生使用)
	 * @param yearId 学年id
	 * @param student 学生对象
	 * @return
	 */
	public Map<String,String> queryEvaluationScore(String yearId, StudentInfoModel student); 
}
