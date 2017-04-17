package com.uws.common.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.uws.common.dao.IEvaluationCommonDao;
import com.uws.common.service.IStuJobTeamSetCommonService;
import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.session.SessionFactory;
import com.uws.core.session.SessionUtil;
import com.uws.core.util.DataUtil;
import com.uws.domain.base.BaseClassModel;
import com.uws.domain.evaluation.EvaluationDetail;
import com.uws.domain.evaluation.EvaluationInfo;
import com.uws.domain.evaluation.EvaluationScore;
import com.uws.domain.evaluation.EvaluationTerm;
import com.uws.domain.evaluation.EvaluationTime;
import com.uws.domain.evaluation.EvaluationUser;
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

@Repository("evaluationCommonDao")
public class EvaluationCommonDaoImpl extends BaseDaoImpl implements IEvaluationCommonDao {

	//字典工具类
	private DicUtil dicUtil=DicFactory.getDicUtil();
	
	@Autowired
	private IStuJobTeamSetCommonService jobTeamService;
	
	//sessionUtil工具类
	private SessionUtil sessionUtil = SessionFactory.getSession(com.uws.sys.util.Constants.MENUKEY_SYSCONFIG);
	
	/***
	 * 查询单个学生的测评信息
	 * @param pageNum
	 * @param pageSize
	 * @param evaluation
	 * @return
	 */
	public Page queryEvaluationPage(int pageNum, int pageSize, String studentId){
		Dic confirmDic=this.dicUtil.getDicInfo("EVALUATION_STATUS", "CONFIRMED");//已确认状态
		List<Object> values = new ArrayList<Object>();
		
		StringBuffer hql = new StringBuffer("from EvaluationInfoVo t where 1=1");
		
		//过滤只能看到属于自己记录 studentId
		hql.append(" and ( t.student.id='"+studentId+"') ");
		
		//过滤   只查看已确认状态
		hql.append(" and t.status.id='"+confirmDic.getId()+"' ");
		
		//排序条件
		hql.append(" order by t.createTime desc ");
		
	    if(values.size()>0){
	    	return this.pagedQuery(hql.toString(), pageNum, pageSize, values.toArray());
	    }else{
	    	return this.pagedQuery(hql.toString(), pageNum, pageSize);
	    }
	}
	
	/***
	 * 根据学年、学生查询该学年这学生的测评成绩综合及各成绩的排名
	 * @param yearId
	 * @param student
	 * @return
	 */
	public Map<String,String> queryStudentEvaluationScore(String yearId, StudentInfoModel student){
		Map<String,String> map=new HashMap();
		if(DataUtil.isNotNull(student)){
			//查询班级排名及其总分
			List<Object[]> classList = queryStudentEvaluationClassRank(yearId, student);
			if(classList.size()>0){
				Object[] object=classList.get(0);
				map.put("dycpScore", object[2]!=null?object[2].toString():null);//德育总分
				map.put("dycpScoreClassRank", object[3].toString());//德育总分班级排名
				map.put("wtcpScore", object[4]!=null?object[4].toString():null);//文体总分
				map.put("wtcpScoreClassRank", object[5].toString());//文体总分班级排名
				map.put("nlcpScore", object[6]!=null?object[6].toString():null);//能力总分
				map.put("nlcpScoreClassRank", object[7].toString());//能力总分班级排名
				map.put("zycpScore", object[8]!=null?object[8].toString():null);//智育总分
				map.put("zycpScoreClassRank", object[9].toString());//智育总分班级排名
				EvaluationInfo evaluation = new EvaluationInfo();
				evaluation.setYearId(yearId);
				evaluation.seteClassId(student.getClassId().getId());
				List<Object[]> list = statisticEvaluationList(evaluation);
				Map<String,String> scoreMap=new HashMap();
				for (Object[] objects : list) {
					scoreMap.put(objects[0].toString(), objects[9].toString());
				}
				List<Entry<String,String>> listOrder = new ArrayList<Map.Entry<String,String>>(scoreMap.entrySet());  
		        Collections.sort(listOrder, new Comparator<Entry<String,String>>(){  
		            @Override  
		            public int compare(Entry<String, String> o1, Entry<String, String> o2) {  
		                int flag = o2.getValue().compareTo(o1.getValue());  
		                if(flag==0){  
		                    return o2.getKey().compareTo(o1.getKey());  
		                }  
		                return flag;  
		            }  
		        });  
				String zhcpScore= "0";//综合测评总分
				String zhcpScoreClassRank = "0";//综合测评总分班级排名
				for (int i = 0; i < listOrder.size(); i++) {
					Entry<String, String> en = listOrder.get(i);
					if(en.getKey().equals(student.getId())){
						zhcpScore = en.getValue();
						zhcpScoreClassRank = i+1+"";
						break;
					}
				}
				map.put("zhcpScore", zhcpScore);//综合测评总分
				map.put("zhcpScoreClassRank", zhcpScoreClassRank);//综合测评总分班级排名
			}
			//查询专业排名及其总分
			List<Object[]> majorList=queryStudentEvaluationMajorRank(yearId, student);
			if(majorList.size()>0){
				Object[] object=majorList.get(0);
				map.put("dycpScoreMajorRank", object[2].toString());//德育总分专业排名
				map.put("wtcpScoreMajorRank", object[3].toString());//文体总分专业排名
				map.put("nlcpScoreMajorRank", object[4].toString());//能力总分专业排名
				map.put("zycpScoreMajorRank", object[5].toString());//智育总分专业排名
				EvaluationInfo evaluation = new EvaluationInfo();
				evaluation.setYearId(yearId);
				evaluation.setMajorId(student.getMajor().getId());
				List<Object[]> list = statisticEvaluationList(evaluation);
				Map<String,String> scoreMap=new HashMap();
				for (Object[] objects : list) {
					scoreMap.put(objects[0].toString(), objects[9].toString());
				}
				List<Entry<String,String>> listOrder = new ArrayList<Map.Entry<String,String>>(scoreMap.entrySet());  
		        Collections.sort(listOrder, new Comparator<Entry<String,String>>(){  
		            @Override  
		            public int compare(Entry<String, String> o1, Entry<String, String> o2) {  
		                int flag = o2.getValue().compareTo(o1.getValue());  
		                if(flag==0){  
		                    return o2.getKey().compareTo(o1.getKey());  
		                }  
		                return flag;  
		            }  
		        });  
				String zhcpScore= "0";//综合测评总分
				String zhcpScoreMajorRank = "0";//综合测评总分专业排名
				for (int i = 0; i < listOrder.size(); i++) {
					Entry<String, String> en = listOrder.get(i);
					if(en.getKey().equals(student.getId())){
						zhcpScore = en.getValue();
						zhcpScoreMajorRank = i+1+"";
						break;
					}
				}
				map.put("zhcpScore", zhcpScore);//综合测评总分
				map.put("zhcpScoreMajorRank", zhcpScoreMajorRank);//综合测评总分班级排名
			}
		}
		
		return map;
	}
	
	/**
	 * 查询学生年度综合测评分数和在班级班名
	 * @param yearId
	 * @param student
	 * @return
	 */
	public List queryStudentEvaluationClassRank(String yearId, StudentInfoModel student){
		StringBuffer classHql = new StringBuffer(" select * from (select t.student_id, t.year_id,"+
				" sum(t.moral_score_sum), rank() over(order by sum(t.moral_score_sum) desc) moral_score_rank,"+
				" sum(t.cultrue_score_sum), rank() over(order by sum(t.cultrue_score_sum) desc) cultrue_score_rank,"+
				" sum(t.capacity_score_sum), rank() over(order by sum(t.capacity_score_sum) desc) capacity_score_rank,"+
				" sum(t.intellect_score_sum), rank() over(order by sum(t.intellect_score_sum) desc) intellect_score_rank"+
				//" sum(t.score_sum), rank() over(order by sum(t.score_sum) desc) score_sum_rank"+
				" from V_EVALUATION_SCORE t left join HKY_STUDENT_INFO s on t.student_id=s.id"+
				" where s.class_id='"+student.getClassId().getId()+"' and t.year_id = '"+yearId+"' group by t.student_id, t.year_id) p"+
				" where p.student_id = '"+student.getId()+"' and p.year_id = '"+yearId+"'");
		return this.querySQL(classHql.toString(), new Object[]{});
	}
	
	/***
	 * 查询学生年度测评成绩和各分数所在专业排名
	 * @param yearId
	 * @param student
	 * @return
	 */
	public List queryStudentEvaluationMajorRank(String yearId, StudentInfoModel student){
		StringBuffer majorHql = new StringBuffer(" select * from (select t.student_id, t.year_id,"+
				" rank() over(order by sum(t.moral_score_sum) desc) moral_score_rank,"+
				" rank() over(order by sum(t.cultrue_score_sum) desc) cultrue_score_rank,"+
				" rank() over(order by sum(t.capacity_score_sum) desc) capacity_score_rank,"+
				" rank() over(order by sum(t.intellect_score_sum) desc) intellect_score_rank"+
				//" rank() over(order by t.score_sum desc) score_sum_rank"+
				" from V_EVALUATION_SCORE t left join HKY_STUDENT_INFO s on t.student_id=s.id"+
				" where s.major='"+student.getMajor().getId()+"' and t.year_id = '"+yearId+"' group by t.student_id, t.year_id) p"+
				" where p.student_id = '"+student.getId()+"' and p.year_id = '"+yearId+"'");
		return this.querySQL(majorHql.toString(), new Object[]{});
	}
	
	/***
	 * 获取班主任下的所有班级id
	 * @param id
	 * @return
	 */
	private String getClassIdByHeadMasterId(String id){
		String ids="";
		List<BaseClassModel> list=this.jobTeamService.getHeadteacherClass(id);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BaseClassModel baseClassModel = (BaseClassModel) iterator.next();
			ids+=baseClassModel.getId()+",";
		}
		return ids;
	}
	
	/***
	 * 获取测评月份设置
	 * @return
	 */
	public List<EvaluationTime> queryEvaluationTime(String date){
		Dic statusDic=this.dicUtil.getStatusNormal();
		List<Object> values = new ArrayList<Object>();
        //StringBuffer hql = new StringBuffer(" from EvaluationTime t where t.status.id='" + statusDic.getId() + "'");
		StringBuffer hql = new StringBuffer(" from EvaluationTime t where t.status.id= ? ");
        values.add(statusDic.getId());
        if(StringUtils.isNotEmpty(date)){
        	hql.append(" and t.addEndTime < to_date(?,'yyyy-mm-dd') ");
        	values.add(date);
        }
        
        List<EvaluationTime> list =(List<EvaluationTime>) query(hql.toString(), values.toArray());
        
        return (list!=null && list.size()>0)?list:null;
	}
	
	/***
	 * 通过测评月份查询测评基础学期
	 * @return
	 */
	public EvaluationTerm queryEvaluationTermByMonthId(String monthId){
		StringBuffer hql = new StringBuffer(" from EvaluationTerm t where t.month.id = ? ");
		return (EvaluationTerm)this.queryUnique(hql.toString(), monthId);
	}
	
	/***
	 * 查询学院的所有学生
	 * @param klassId
	 * @param province
	 * @param flag
	 * @return
	 */
	@Override
	public List<StudentInfoModel> queryStudentInfoByCollegeId(String collegeId) {
		List<Object> values = new ArrayList<Object>();
        StringBuffer hql = new StringBuffer(" from StudentInfoModel t where edusStatus in ? ");
		values.add(ProjectConstants.STUDENT_NORMAL_STAUTS_STRING);
        
        hql.append(" and t.college.id = ? ");
        values.add(collegeId);
        //按学号排序
        hql.append(" order by  t.stuNumber asc");
        List<StudentInfoModel> list =(List<StudentInfoModel>) query(hql.toString(), values.toArray());
        
        return (list!=null && list.size()>0)?list:null;
	}
	
	/***
	 * 获取个人测评信息
	 */
	public EvaluationInfo getEvaluationInfo(String year, String month, String user){
		
		StringBuffer hql=new StringBuffer(" from EvaluationInfo t where t.year.id = ? and t.month.id = ? and t.student.id = ? ");
		List<EvaluationInfo> list=this.query(hql.toString(), new Object[]{year, month, user});
		EvaluationInfo evaluation=new EvaluationInfo();
		if(list.size()>0){
			evaluation=list.get(0);
		}else{
			evaluation=null;
		}
		return evaluation;
	}
	
	/**
	 * 查询班级下的测评员
	 */
	public EvaluationUser queryEvaluationUser(String classId){
		EvaluationUser evaluationUser=new EvaluationUser();
		List<EvaluationUser> list=this.query("from EvaluationUser eu where eu.classId.id = ? ", new Object[]{classId});
		if(list.size()>0){
			evaluationUser=list.get(0);
		}else{
			evaluationUser=null;
		}
		return evaluationUser;
	}
	
	/**
	 * 保存测评信息
	 */
	public void saveEvaluation(EvaluationInfo evaluation){
		this.save(evaluation);
	}
	
	/***
	 * 保存测评明细
	 */
	public void saveEvaluationDetail(EvaluationDetail detail){
		this.save(detail);
	}
	
	/***
	 * 根据学年、学生查询该学年这学生的测评成绩(困难生使用)
	 * @param yearId 学年id
	 * @param student 学生对象
	 * @return
	 */
	public Map<String,String> queryEvaluationScore(String yearId, StudentInfoModel student){
		Map<String,String> map=new HashMap();
		if(DataUtil.isNotNull(student)){
			//查询班级排名及其总分
			Object[] evaluation=queryEvaluationScoreByYearId(yearId, student);
			if(DataUtil.isNotNull(evaluation) && evaluation.length > 0){
				map.put("dycpScore", evaluation[5].toString());//德育总分
				map.put("wtcpScore", evaluation[7].toString());//文体总分
				map.put("nlcpScore", evaluation[8].toString());//能力总分
				map.put("sumScore", evaluation[9].toString());//测评总分
			}
		}
		
		return map;
	}
	
	/**
	 * 查询学生年度综合测评分数和在班级班名
	 * @param yearId
	 * @param student
	 * @return
	 */
	public Object[] queryEvaluationScoreByYearId(String yearId, StudentInfoModel student){
		Dic confirmDic=this.dicUtil.getDicInfo("EVALUATION_STATUS", "CONFIRMED");//已确认状态
		String currentUserId=this.sessionUtil.getCurrentUserId();
		List<Object> values = new ArrayList<Object>();
		
		StringBuffer hql = new StringBuffer(" select t.STUDENT_ID, p.NAME as collegeName, q.MAJOR_NAME, k.CLASS_NAME, s.NAME as studentName, "+
				" sum(t.MORAL_SCORE_SUM), avg(t.INTELLECT_SCORE_SUM), sum(t.CULTRUE_SCORE_SUM), sum(t.CAPACITY_SCORE_SUM), sum(t.SCORE_SUM) as scoresum, d.NAME as yearName, d.ID "+//, r.NAME as termName
				" from V_EVALUATION_INFO t, HKY_STUDENT_INFO s, HKY_BASE_COLLAGE p, HKY_BASE_MAJOR q, HKY_BASE_CLASS k,HKY_EVALUATION_TIME e, DIC d "+//, DIC r 
				"  where t.STUDENT_ID = s.ID and s.COLLEGE = p.ID and s.MAJOR = q.ID and s.CLASS_ID = k.ID and t.TID=e.ID and t.YEAR_ID=d.ID ");//and t.TERM_ID=r.ID
		//学年
		if(DataUtil.isNotNull(yearId)){
			hql.append(" and t.YEAR_ID = ? ");
			values.add(yearId);
		}

		//学生
		if(DataUtil.isNotNull(student) && DataUtil.isNotNull(student.getId())){
			hql.append(" and s.ID = ? ");
			values.add(student.getId());
		}
		
		//过滤   只查看已确认状态
		hql.append(" and t.STATUS='"+confirmDic.getId()+"' ");
		//过滤没有完成确认的班级的学生的测评月份
		hql.append(" and t.ID not in (select a.id from HKY_EVALUATION_INFO a where 1=1");
		//学年
		if(DataUtil.isNotNull(yearId)){
			hql.append(" and a.year_id = '"+yearId+"'");
		}
		
		hql.append(" and a.student_id in (");
		hql.append( " select distinct m.id from hky_student_info m where m.class_id in (");
		hql.append(" select distinct n.class_id from hky_student_info n, HKY_EVALUATION_INFO pp where pp.status!='"+confirmDic.getId()+"' ");
		//学年
		if(DataUtil.isNotNull(yearId)){
			hql.append(" and pp.year_id ='"+yearId+"' ");
		}
		
		hql.append(" and  n.id=pp.student_id");
		hql.append("))");
		hql.append(")");
		
		hql.append(" group by t.STUDENT_ID, p.NAME, q.MAJOR_NAME, k.CLASS_NAME, s.NAME, d.NAME, d.ID ");//, t.TERM_ID,  r.NAME
		
		List<Object[]> list=this.querySQL(hql.toString(), values.toArray());
		Object[] preObjects=new Object[]{};
		Object[] maxObjects=new Object[]{};
		List maxList=new ArrayList();

		//测评分基础设置
		List<EvaluationScore> evaluationScoreList=this.queryEvaluationSetScore();
		String moralBaseScore="";
		String cultureBaseScore="";
		String capacityBaseScore="";
		String moralRewardScore="";
		String cultureRewardScore="";
		String capacityRewardScore="";
		String moralWeight="";
		String cultureWeight="";
		String capacityWeight="";
		String intellectWeight="";
		
		DecimalFormat df=new DecimalFormat(".##");
		
		for (Iterator iterator = evaluationScoreList.iterator(); iterator.hasNext();) {
			EvaluationScore evaluationScore = (EvaluationScore) iterator.next();
			if(evaluationScore.getBaseType().getCode().equals("MORAL") && evaluationScore.getScoreType().getCode().equals("BASE_SCORE")){
				moralBaseScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("MORAL") && evaluationScore.getScoreType().getCode().equals("REWARD_SCORE")){
				moralRewardScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("MORAL") && evaluationScore.getScoreType().getCode().equals("WEIGHT")){
				moralWeight=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CULTURE") && evaluationScore.getScoreType().getCode().equals("BASE_SCORE")){
				cultureBaseScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CULTURE") && evaluationScore.getScoreType().getCode().equals("REWARD_SCORE")){
				cultureRewardScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CULTURE") && evaluationScore.getScoreType().getCode().equals("WEIGHT")){
				cultureWeight=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CAPACITY") && evaluationScore.getScoreType().getCode().equals("BASE_SCORE")){
				capacityBaseScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CAPACITY") && evaluationScore.getScoreType().getCode().equals("REWARD_SCORE")){
				capacityRewardScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CAPACITY") && evaluationScore.getScoreType().getCode().equals("WEIGHT")){
				capacityWeight=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("INTELLECT") && evaluationScore.getScoreType().getCode().equals("WEIGHT")){
				intellectWeight=evaluationScore.getScore();
			}
		}
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
				String studentNum=objects[0].toString();
				//获取查询的当前学年、测评月份、班级的德育、文体、能力最高分
				StringBuffer maxValueHql = 
						new StringBuffer("select max(sum(k.moral_score_sum)) as moral_score_sum,"+
										" max(sum(k.cultrue_score_sum)) as cultrue_score_sum,"+
										" max(sum(k.capacity_score_sum)) as capacity_score_sum"+
										" from (select  t.student_id as student_id, t.year_id,"+//t.term_id,
										" sum(t.moral_score_sum) as moral_score_sum,"+
										" sum(t.cultrue_score_sum) as cultrue_score_sum,"+
										" sum(t.capacity_score_sum) as capacity_score_sum"+
										" from hky_evaluation_info t left join HKY_STUDENT_INFO s on t.student_id=s.id "+
										" left join HKY_BASE_CLASS k on s.class_id=k.id "+
										" where  k.id in(select c.class_id from HKY_STUDENT_INFO c where c.id='"+studentNum+"')"+
										" group by t.student_id, t.year_id ) k where 1=1 ");//, t.term_id
				maxValueHql.append(" group by k.student_id, k.year_id ");//, k.term_id
				
				maxList=this.querySQL(maxValueHql.toString(), new Object[]{});
				if(maxList.size()>0){
					maxObjects=(Object[]) maxList.get(0);
					//德育分数   && (objects[5].toString().equals("0"))
					if((maxObjects[0].toString().equals("0"))){
						objects[5]=df.format(Double.parseDouble(moralBaseScore)+Double.parseDouble(objects[5].toString()));
					}else if((Double.parseDouble(objects[5].toString())<0 && Double.parseDouble(maxObjects[0].toString())<0)){
						objects[5]=df.format(Double.parseDouble(moralBaseScore)+Double.parseDouble(objects[5].toString()));
					}else{
						//最大分大于0
						objects[5]=df.format(Double.parseDouble(moralBaseScore)+
								Double.parseDouble(moralRewardScore)*(Double.parseDouble(objects[5].toString())/Double.parseDouble(maxObjects[0].toString())));
					}
					//文体分数比 && (objects[7].toString().equals("0"))
					if((maxObjects[1].toString().equals("0"))){
						objects[7]=df.format(Double.parseDouble(cultureBaseScore)+Double.parseDouble(objects[7].toString()));
					}else if(Double.parseDouble(objects[7].toString())<0 && Double.parseDouble(maxObjects[1].toString())<0){
						objects[7]=df.format(Double.parseDouble(cultureBaseScore)+Double.parseDouble(objects[7].toString()));
					}else{
						objects[7]=df.format(Double.parseDouble(cultureBaseScore)+
								Double.parseDouble(cultureRewardScore)*(Double.parseDouble(objects[7].toString())/Double.parseDouble(maxObjects[1].toString())));
					}
					//能力分数比  && (objects[8].toString().equals("0"))
					if((maxObjects[2].toString().equals("0"))){
							objects[8]=df.format(Double.parseDouble(capacityBaseScore)+Double.parseDouble(objects[8].toString()));
					}else if(Double.parseDouble(objects[8].toString())<0 && Double.parseDouble(maxObjects[2].toString())<0){
						objects[8]=df.format(Double.parseDouble(capacityBaseScore)+Double.parseDouble(objects[8].toString()));
					}else{
						objects[8]=df.format(Double.parseDouble(capacityBaseScore)+
								Double.parseDouble(capacityRewardScore)*(Double.parseDouble(objects[8].toString())/Double.parseDouble(maxObjects[2].toString())));
					}
				}
			
			//算总分
			objects[9]=df.format(Double.parseDouble(objects[5].toString())*Double.parseDouble(moralWeight)
								+Double.parseDouble((objects[6]!=null?objects[6]:"0").toString())*Double.parseDouble(intellectWeight)
								+Double.parseDouble(objects[7].toString())*Double.parseDouble(cultureWeight)
								+Double.parseDouble(objects[8].toString())*Double.parseDouble(capacityWeight));
			
			preObjects=objects;//
		}
		return preObjects;
	}
	
	/**
	 * 查询基础分
	 */
	public List<EvaluationScore> queryEvaluationSetScore(){
		List<EvaluationScore> evaluationList=this.query("from EvaluationScore ec", new Object[]{});
		return evaluationList;
	}
	
	/**
	 * 根据记录号（id）进行学生基本信息查询
	 * 
	 * @param id
	 *            学生基本信息记录号
	 * @return 提前离校申请Po
	 */
	@Override
	public StudentInfoModel getStudentById(String id) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(id))
			return (StudentInfoModel) this.queryUnique("from StudentInfoModel t where t.id = ? ",new Object[]{id});
		return null;

	}
	
	/***
	 * 测评统计
	 * @param pageNum
	 * @param pageSize
	 * @param evaluation
	 * @return
	 */
	public List statisticEvaluationList(EvaluationInfo evaluation){
		Dic confirmDic=this.dicUtil.getDicInfo("EVALUATION_STATUS", "CONFIRMED");//已确认状态
		List<Object> values = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" select t.STUDENT_ID, p.NAME as collegeName, q.MAJOR_NAME, k.CLASS_NAME, s.NAME as studentName, "+
				" sum(t.MORAL_SCORE_SUM), avg(t.INTELLECT_SCORE_SUM), sum(t.CULTRUE_SCORE_SUM), sum(t.CAPACITY_SCORE_SUM), sum(t.SCORE_SUM) as scoresum, d.NAME, d.ID"+
				" from V_EVALUATION_INFO t, HKY_STUDENT_INFO s, HKY_BASE_COLLAGE p, HKY_BASE_MAJOR q, HKY_BASE_CLASS k,HKY_EVALUATION_TIME e, DIC d "+
				"  where t.STUDENT_ID = s.ID and s.COLLEGE = p.ID and s.MAJOR = q.ID and s.CLASS_ID = k.ID and t.TID=e.ID and t.YEAR_ID=d.ID");
		//学年
		if(DataUtil.isNotNull(evaluation) && DataUtil.isNotNull(evaluation.getYearId())){
			hql.append(" and t.YEAR_ID = ? ");
			values.add(evaluation.getYearId());
		}

		//专业
		if(DataUtil.isNotNull(evaluation) && DataUtil.isNotNull(evaluation.getMajorId())){
			hql.append(" and q.ID  = ? ");
			values.add(evaluation.getMajorId());
		}
		//班级
		if(DataUtil.isNotNull(evaluation) && DataUtil.isNotNull(evaluation.geteClassId())){
			hql.append(" and k.ID  = ? ");
			values.add(evaluation.geteClassId());
		}
		
		//过滤   只查看已确认状态
		hql.append(" and t.STATUS='"+confirmDic.getId()+"' ");
		
		//过滤没有完成确认的班级的学生的测评月份
		hql.append(" and t.ID not in (select a.id from HKY_EVALUATION_INFO a where 1=1");
		//学年
		if(DataUtil.isNotNull(evaluation) && DataUtil.isNotNull(evaluation.getYearId())){
			hql.append(" and a.year_id = '"+evaluation.getYearId()+"'");
		}
		
		hql.append(" and a.student_id in (");
		hql.append( " select distinct m.id from hky_student_info m where m.class_id in (");
		hql.append(" select distinct n.class_id from hky_student_info n, HKY_EVALUATION_INFO pp where pp.status!='"+confirmDic.getId()+"' ");
		//学年
		if(DataUtil.isNotNull(evaluation) && DataUtil.isNotNull(evaluation.getYearId())){
			hql.append(" and pp.year_id ='"+evaluation.getYearId()+"' ");
		}
		
		hql.append(" and  n.id=pp.student_id");
		hql.append("))");
		hql.append(")");
		
		hql.append(" group by t.STUDENT_ID, p.NAME, q.MAJOR_NAME, k.CLASS_NAME, s.NAME, d.NAME, d.ID ");
		
		//排序条件
		hql.append(" order by k.CLASS_NAME, scoresum desc ");
		
		List<Object[]> list= this.querySQL(hql.toString(), values.toArray());
		Object[] preObjects=new Object[]{};
		Object[] maxObjects=new Object[]{};
		List maxList=new ArrayList();

		//测评分基础设置
		List<EvaluationScore> evaluationScoreList=this.queryEvaluationScore();
		String moralBaseScore="";
		String cultureBaseScore="";
		String capacityBaseScore="";
		String moralRewardScore="";
		String cultureRewardScore="";
		String capacityRewardScore="";
		String moralWeight="";
		String cultureWeight="";
		String capacityWeight="";
		String intellectWeight="";
		
		DecimalFormat df=new DecimalFormat(".##");
		
		for (Iterator iterator = evaluationScoreList.iterator(); iterator.hasNext();) {
			EvaluationScore evaluationScore = (EvaluationScore) iterator.next();
			if(evaluationScore.getBaseType().getCode().equals("MORAL") && evaluationScore.getScoreType().getCode().equals("BASE_SCORE")){
				moralBaseScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("MORAL") && evaluationScore.getScoreType().getCode().equals("REWARD_SCORE")){
				moralRewardScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("MORAL") && evaluationScore.getScoreType().getCode().equals("WEIGHT")){
				moralWeight=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CULTURE") && evaluationScore.getScoreType().getCode().equals("BASE_SCORE")){
				cultureBaseScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CULTURE") && evaluationScore.getScoreType().getCode().equals("REWARD_SCORE")){
				cultureRewardScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CULTURE") && evaluationScore.getScoreType().getCode().equals("WEIGHT")){
				cultureWeight=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CAPACITY") && evaluationScore.getScoreType().getCode().equals("BASE_SCORE")){
				capacityBaseScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CAPACITY") && evaluationScore.getScoreType().getCode().equals("REWARD_SCORE")){
				capacityRewardScore=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("CAPACITY") && evaluationScore.getScoreType().getCode().equals("WEIGHT")){
				capacityWeight=evaluationScore.getScore();
			}else if(evaluationScore.getBaseType().getCode().equals("INTELLECT") && evaluationScore.getScoreType().getCode().equals("WEIGHT")){
				intellectWeight=evaluationScore.getScore();
			}
		}
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			if(preObjects.length>0 && preObjects.length>0 &&  preObjects[3].toString().equals(objects[3].toString())){
			//若与前一位同一个班的最大值用上次查询的结果
				//德育分数  && (objects[5].toString().equals("0"))
				if((maxObjects[0].toString().equals("0"))){
					//判断为0
					objects[5]=df.format(Double.parseDouble(moralBaseScore)+Double.parseDouble(objects[5].toString()));
				}else if((Double.parseDouble(objects[5].toString())<0 && Double.parseDouble(maxObjects[0].toString())<0)){
					//判断最大值和个人分数是都小于0或者最大值为0，是则最大分做分子，个人分做分母
					objects[5]=df.format(Double.parseDouble(moralBaseScore)+Double.parseDouble(objects[5].toString()));
				}else{
					//最大分大于0
					objects[5]=df.format(Double.parseDouble(moralBaseScore)+
							Double.parseDouble(moralRewardScore)*(Double.parseDouble(objects[5].toString())/Double.parseDouble(maxObjects[0].toString())));
				}
				//文体分数比 && (objects[7].toString().equals("0"))
				if((maxObjects[1].toString().equals("0"))){
						objects[7]=df.format(Double.parseDouble(cultureBaseScore)+Double.parseDouble(objects[7].toString()));
				}else if(Double.parseDouble(objects[7].toString())<0 && Double.parseDouble(maxObjects[1].toString())<0){
					objects[7]=df.format(Double.parseDouble(cultureBaseScore)+Double.parseDouble(objects[7].toString()));
				}else{
					objects[7]=df.format(Double.parseDouble(cultureBaseScore)+
							Double.parseDouble(cultureRewardScore)*(Double.parseDouble(objects[7].toString())/Double.parseDouble(maxObjects[1].toString())));
				}
				//能力分数比 && (objects[8].toString().equals("0"))
				if((maxObjects[2].toString().equals("0"))){
					objects[8]=df.format(Double.parseDouble(capacityBaseScore)+Double.parseDouble(objects[8].toString()));
				}else if(Double.parseDouble(objects[8].toString())<0 && Double.parseDouble(maxObjects[0].toString())<0){
					objects[8]=df.format(Double.parseDouble(capacityBaseScore)+Double.parseDouble(objects[8].toString()));
				}else{
					objects[8]=df.format(Double.parseDouble(capacityBaseScore)+
							Double.parseDouble(capacityRewardScore)*(Double.parseDouble(objects[8].toString())/Double.parseDouble(maxObjects[2].toString())));
				}
			}else{
				String studentNum=objects[0].toString();
				String yearId=objects[11].toString();
				//获取查询的当前学年、测评月份、班级的德育、文体、能力最高分
				StringBuffer maxValueHql = 
						new StringBuffer("select max(sum(k.moral_score_sum)) as moral_score_sum,"+
										" max(sum(k.cultrue_score_sum)) as cultrue_score_sum,"+
										" max(sum(k.capacity_score_sum)) as capacity_score_sum"+
										" from (select t.month_id, t.student_id as student_id, t.year_id,"+
										" sum(t.moral_score_sum) as moral_score_sum,"+
										" sum(t.cultrue_score_sum) as cultrue_score_sum,"+
										" sum(t.capacity_score_sum) as capacity_score_sum"+
										" from hky_evaluation_info t left join HKY_STUDENT_INFO s on t.student_id=s.id "+
										" left join HKY_BASE_CLASS k on s.class_id=k.id "+
										" where  k.id in(select c.class_id from HKY_STUDENT_INFO c where c.id='"+studentNum+"')"+
										" group by t.student_id, t.year_id, t.month_id) k where 1=1 ");
				maxValueHql.append(" group by k.student_id, k.year_id ");
				
				maxList=this.querySQL(maxValueHql.toString(), new Object[]{});
				if(maxList.size()>0){
					maxObjects=(Object[]) maxList.get(0);
					//德育分数   && (objects[5].toString().equals("0"))
					if((maxObjects[0].toString().equals("0"))){
						//判断最大值和个人分数是否都为0，是则比例为1
						objects[5]=df.format(Double.parseDouble(moralBaseScore)+Double.parseDouble(objects[5].toString()));
					}else if((Double.parseDouble(objects[5].toString())<0 && Double.parseDouble(maxObjects[0].toString())<0)){
					//判断最大值和个人分数是都小于0，是则最大分做分子，个人分做分母
						objects[5]=df.format(Double.parseDouble(moralBaseScore)+Double.parseDouble(objects[5].toString()));
					}else{
						//最大分大于0
						objects[5]=df.format(Double.parseDouble(moralBaseScore)+
								Double.parseDouble(moralRewardScore)*(Double.parseDouble(objects[5].toString())/Double.parseDouble(maxObjects[0].toString())));
					}
					//文体分数比 && (objects[7].toString().equals("0"))
					if((maxObjects[1].toString().equals("0"))){
						objects[7]=df.format(Double.parseDouble(cultureBaseScore)+Double.parseDouble(objects[7].toString()));
					}else if(Double.parseDouble(objects[7].toString())<0 && Double.parseDouble(maxObjects[1].toString())<0){
						objects[7]=df.format(Double.parseDouble(cultureBaseScore)+Double.parseDouble(objects[7].toString()));
					}else{
						objects[7]=df.format(Double.parseDouble(cultureBaseScore)+
								Double.parseDouble(cultureRewardScore)*(Double.parseDouble(objects[7].toString())/Double.parseDouble(maxObjects[1].toString())));
					}
					//能力分数比 && (objects[8].toString().equals("0"))
					if((maxObjects[2].toString().equals("0"))){
						objects[8]=df.format(Double.parseDouble(capacityBaseScore)+Double.parseDouble(objects[8].toString()));
					}else if(Double.parseDouble(objects[8].toString())<0 && Double.parseDouble(maxObjects[2].toString())<0){
						objects[8]=df.format(Double.parseDouble(capacityBaseScore)+Double.parseDouble(objects[8].toString()));
					}else{
						objects[8]=df.format(Double.parseDouble(capacityBaseScore)+
								Double.parseDouble(capacityRewardScore)*(Double.parseDouble(objects[8].toString())/Double.parseDouble(maxObjects[2].toString())));
					}
				}
			}
			
			//算总分
			objects[9]=df.format(Double.parseDouble(objects[5].toString())*Double.parseDouble(moralWeight)
								+Double.parseDouble((objects[6]!=null?objects[6]:"0").toString())*Double.parseDouble(intellectWeight)
								+Double.parseDouble(objects[7].toString())*Double.parseDouble(cultureWeight)
								+Double.parseDouble(objects[8].toString())*Double.parseDouble(capacityWeight));
			
			preObjects=objects;//
		}
		
		return list;
	}
	
	/**
	 * 查询基础分
	 */
	public List<EvaluationScore> queryEvaluationScore(){
		List<EvaluationScore> evaluationList=this.query("from EvaluationScore ec", new Object[]{});
		return evaluationList;
	}
}
