package com.uws.common.dao;

import java.util.List;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.domain.common.StudentTermScore;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;

public interface IScoreDao extends IBaseDao{
	/***
	 * 查询所有的学生（分数同步使用）
	 * @return
	 */
	public List<StudentInfoModel> queryStudentList();
	
	/**
	 * 查询学生某一学期的分数记录
	 * @param studentId
	 * @param yearId
	 * @param termId
	 * @return
	 */
	public StudentTermScore queryStudentTermScore(String studentId, String yearId, String termId);
	
	/***
	 * 查询学生的体育成绩
	 * @param studentId
	 * @param yearCode
	 * @return
	 */
	public String queryStudentSportScore(String studentId, String yearCode);
	
	/**
	 * 描述信息:  查询对低分， termDic为null查询 学年最低分，不为空查询学期最低分
	 * @param studentId
	 * @param yearDic
	 * @param termDic
	 * @return
	 * 2015-12-4 下午2:12:51
	 */
    public String queryStudentLowScore(String studentId, Dic yearDic,Dic termDic);
    
    /***
     * 查询没有通过的科目
     * @param studentId
     * @param yearCode
     * @return
     */
    public List queryCoursesNotPass(String studentId, String yearCode);
}
