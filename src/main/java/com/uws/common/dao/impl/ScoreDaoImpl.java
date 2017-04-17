package com.uws.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.uws.common.dao.IScoreDao;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.util.DataUtil;
import com.uws.domain.common.StudentTermScore;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;
import com.uws.sys.service.DicUtil;
import com.uws.sys.service.impl.DicFactory;
import com.uws.util.ProjectConstants;


/*****
 * 综合测评统计
 * @author Jiangbl
 * @date 2015-9-15
 */

@Repository("com.uws.common.dao.impl.ScoreDaoImpl")
public class ScoreDaoImpl extends BaseDaoImpl implements IScoreDao {

	//字典工具类
	private DicUtil dicUtil=DicFactory.getDicUtil();
	
	/***
	 * 查询所有的学生（分数同步使用）
	 * @return
	 */
	@Override
	public List<StudentInfoModel> queryStudentList(){
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from StudentInfoModel where edusStatus in ? ");
		values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
		List<StudentInfoModel> list =(List<StudentInfoModel>) query(hql.toString(), values.toArray());
        return (list!=null && list.size()>0)?list:null;
	}
	
	/**
	 * 查询学生某一学期的分数记录
	 * @param studentId
	 * @param yearId
	 * @param termId
	 * @return
	 */
	@Override
	public StudentTermScore queryStudentTermScore(String studentId, String yearId, String termId){
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("from StudentTermScore where 1=1 ");
		if(DataUtil.isNotNull(studentId)){
			hql.append(" and student.id = ? ");
			values.add(studentId);
		}
		if(DataUtil.isNotNull(yearId)){
			hql.append(" and year.id = ? ");
			values.add(yearId);
		}
		if(DataUtil.isNotNull(termId)){
			hql.append(" and term.id = ? ");
			values.add(termId);
		}
		List<StudentTermScore> list =(List<StudentTermScore>) query(hql.toString(), values.toArray());
        return (list!=null && list.size()>0)?list.get(0):null;
	}
	
	/***
	 * 查询学生的体育成绩
	 * @param studentId
	 * @param yearCode
	 * @return
	 */
	@Override
	public String queryStudentSportScore(String studentId, String yearCode){
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select avg(t.score) from StudentCourseScore t where t.courseName like '%体育%' ");
		if(DataUtil.isNotNull(studentId)){
			//学号
			hql.append(" and t.studentId = ? ");
			values.add(studentId);
		}
		if(DataUtil.isNotNull(yearCode)){
			//学年
			hql.append(" and t.yearCode = ? ");
			values.add(yearCode);
		}
		List list = this.query(hql.toString(), values.toArray());
		if(list != null && list.size()>0){
			return list.get(0) != null?list.get(0).toString():null;
		}
		return null;
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
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select min(t.score) from StudentCourseScore t where 1=1 ");
		if(DataUtil.isNotNull(studentId)){
			//学号
			hql.append(" and t.studentId = ? ");
			values.add(studentId);
		}
		if(DataUtil.isNotNull(yearDic) && DataUtil.isNotNull(yearDic.getCode())){
			//学年
			hql.append(" and t.yearCode = ? ");
			values.add(yearDic.getCode());
		}
		if(DataUtil.isNotNull(termDic) && DataUtil.isNotNull(termDic.getCode())){
			//学期
			hql.append(" and t.termCode = ? ");
			values.add(termDic.getCode());
		}
		List list = this.query(hql.toString(), values.toArray());
		if(list != null && list.size()>0){
			return list.get(0) != null?list.get(0).toString():null;
		}
		return null;
    }
	
	/***
     * 查询没有通过的科目
     * @param studentId
     * @param yearCode
     * @return
     */
	@Override
    public List queryCoursesNotPass(String studentId, String yearCode){
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" from StudentCourseScore t where t.score < 60 ");
		if(DataUtil.isNotNull(studentId)){
			//学号
			hql.append(" and t.studentId = ? ");
			values.add(studentId);
		}
		if(DataUtil.isNotNull(yearCode)){
			//学年
			hql.append(" and t.yearCode = ? ");
			values.add(yearCode);
		}
		return this.query(hql.toString(), values.toArray());
    }
}
