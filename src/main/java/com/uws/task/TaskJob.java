package com.uws.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uws.common.service.IEvaluationCommonService;
import com.uws.common.service.ILeaveCommonService;
import com.uws.common.service.IScoreService;
import com.uws.core.util.DateUtil;
import com.uws.domain.leave.LeaveSchool;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.log.Logger;
import com.uws.log.LoggerFactory;
import com.uws.task.service.IExecuProcedureService;
import com.uws.webservice.IBookService;
import com.uws.webservice.IDromService;
import com.uws.webservice.IFeeServcie;

/**
 * 
* @ClassName: TaskJob 
* @Description: 处理数据同步的任务调度
* @author 联合永道
* @date 2015-7-24 下午1:46:47 
*
 */
@Component("taskJob")
@PropertySource(value ={ "classpath:config/timer_config.properties" })
public class TaskJob
{
	// 调试日志
	private Logger logger = new LoggerFactory(TaskJob.class);
	/**
	 * 存储过程调用service
	 */
	@Autowired
	private IExecuProcedureService procedureService;
	
	@Autowired
	private IEvaluationCommonService evaluationCommonService;
	
	@Autowired
	private IFeeServcie feeService;
	
	@Autowired
	private ILeaveCommonService leaveService;
	
	@Autowired
	private IBookService bookService;
	
	@Autowired
	private IDromService dormService;
	
	@Autowired
	private IScoreService scoreService;
	
	/**
	 * 学生信息同步方法
	 * 1、取同步视图的学生数据
	 * 2、查询学生是否已经存在，若存在则更新，若不存在则新增
	 * 3、根据学生同步到用户数据中，赋值学生角色和操作权限
	 * 4、更新时间每天 0 点 同步处理数据
	 */
	@Scheduled(cron = "${STUDENT_SYCN_TIMER}")
	public void syncStudent()
	{
		procedureService.execProcedure("STUDENT_SYNC_PROC");
		logger.debug("学生信息同步 ...");
		procedureService.execProcedure("STUDENT_USER_SYNC_PROC");
		logger.debug("学生对应的用户同步 ...");
	}
	
	/**
	 * 教职工信息同步方法
	 * 1、取同步视图的教职工数据
	 * 2、查询教职工是否已经存在，若存在则更新，若不存在则新增
	 * 3、根据教职工信息处理同步到用户信息表中，配置权限，配置教职工权限和操作
	 * 4、更新时间每天 0 点 同步处理数据 
	 */
	@Scheduled(cron = "${TEACHER_SYCN_TIMER}")
	public void syncTeacherStaff()
	{
		procedureService.execProcedure("XG_TEACHER_SYNC_PROC");
		logger.debug("教职工信息同步 ...");
		procedureService.execProcedure("TEACHER_USER_SYNC_PROC");
		logger.debug("教职工用户信息同步 ...");
		
	}
	
	/**
	 * 班级信息同步方法
	 * 1、取同步视图的班级数据
	 * 4、更新时间每天 0 点 30分 同步处理数据
	 */
	@Scheduled(cron = "${CLASS_SYCN_TIMER}")
	public void syncClass()
	{
		procedureService.execProcedure("CLASS_SYNC_PROC");
		logger.debug("班级信息同步 ...");
	}

	/**
	 * 财务数据的方法（使用webserive 接口取得）
	 * 1、取同步视图的教职工数据
	 * 2、查询教职工是否已经存在，若存在则更新，若不存在则新增
	 * 3、根据教职工信息处理同步到用户信息表中，配置权限，配置教职工权限和操作
	 * 4、更新时间每天 0 点 同步处理数据 
	 */
	@Scheduled(cron ="* * * * 10 ?")
	public void syncConstStaff()
	{
		
		//System.out.println("财务数据（缴费状态） ...");
		
		//Dic dicYear=SchoolYearUtil.getYearDic();
		//if(dicYear !=null){
			
			//webService.execCont(dicYear.getId());
			
			//logger.debug("财务数据（缴费状态） ...");
			//System.out.println("财务数据（缴费状态） ...");
		//}else{
			
			//System.out.println("没有选择财务缴费学年");
		//}
		
		
	}

	/**
	 * 
	 * @Title: syncCollageInfo
	 * 1、取同步视图的学院数据
	 * 2、查询教职工是否已经存在，若存在则更新，若不存在则新增
	 * 4、更新时间每天 0 点 同步处理数据 
	 * @Description: 同步学院信息
	 * @throws
	 */
	@Scheduled(cron = "${COLLEGE_SYCN_TIMER}")
	public void syncCollageInfo()
	{
		procedureService.execProcedure("COLLAGE_SYNC_PROC");
		logger.debug("学院信息同步 ...");
	}
	
	/**
	 * 1、取同步视图的专业
	 * 2、查询专业是否已经存在，若存在则更新，若不存在则新增
	 * 4、更新时间每天 0 点 同步处理数据 
	 * @Title: syncMajorInfo
	 * @throws
	 */
	@Scheduled(cron = "${MAJOR_SYCN_TIMER}")
	public void syncMajorInfo()
	{
		procedureService.execProcedure("MAJOR_SYNC_PROC");
		logger.debug("专业信息同步 ...");
	}
	
	/**
	 * 1、新生宿舍信息同步
	 * 2、每年的 8月 9月 每隔5分钟同步一次
	 * @Title: syncNewStudentDroomInfo
	 * @throws
	 */
	@Scheduled(cron = "${NEW_STUDENT_DROOM_SYCN_TIMER}")
	public void syncNewStudentDroomInfo()
	{
		procedureService.execProcedure("ROOM_INFO_SYCN_PROC");
		procedureService.execProcedure("STUDENT_ROOM_SYNC_PROC");
		logger.debug("专业信息同步 ...");
	}
	
	/**
	 * 查询当前时间已超过测评添加结束时间的测评记录，修改已保存状态为已提交
	 * 查询当前时间已超过测评修改结束时间的测评记录，修改待确认状态为已确认
	 * 每天0点1分执行
	 */
	@Scheduled(cron = "${EVALUATE_SYCN_TIMER}")
	public void updateEvaluationStatus(){
		this.evaluationCommonService.execEvaluation();//自动生成测评记录
		procedureService.execProcedure("EVALUATION_STATUS_PROC", new Object[]{DateUtil.getCurDate()});
		logger.debug("综合测评更新测评记录状态 ...");
	}
	
	
	/**
	 * 
	 * @Title: sycnLeaveStudentInfo
	 * @Description:  离校学生同步，教务离校学生信息同步
	 * @throws
	 */
	@Scheduled(cron = "${LEAVE_STUDENT_SYCN_TIMER}")
	public void sycnLeaveStudentInfo(){
		procedureService.execProcedure("XG_LEAVE_SCHOOL_SYNC_PROC");
	}
	
	/**
	 * 
	 * @Title: sycnLeaveStudentDrom
	 * @Description:离校学生退宿信息的同步 每5分钟同步一次
	 * @throws
	 */
	@Scheduled(cron = "${XG_LEAVE_DROM_SYNC_TIMER}")
	public void sycnLeaveStudentDrom(){
		procedureService.execProcedure("XG_LEAVE_DROM_SYNC_PROC");
	}
	
	/**
	 * 
	 * @Title: sycnLeaveStudentDrom
	 * @Description: 学籍异动的信息同步 
	 * @throws
	 */
	@Scheduled(cron = "${XG_LEAVE_XJYD_SYNC_TIMER}")
	public void sycnLeaveStudentXJYD(){
		procedureService.execProcedure("XG_LEAVE_XXYD_SYNC_PROC");
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: sycnLeaveStudentFeeStatus
	 * @Description: 离校缴费状态的同步接口
	 * @throws
	 */
	@Scheduled(cron = "${STUDENT_FEE_SYCN_TIMER}")
	public void sycnLeaveStudentFeeStatus() throws Exception{
		List<StudentInfoModel> studentList = leaveService.queryFinanceStudentList();
		if(null!=studentList)
		{
			for(StudentInfoModel student : studentList)
			{
				String status = feeService.getStudentFeeStatusStr(student.getCertificateCode(), "JFZT");
				leaveService.operateFinance(student.getId(), status);
			}
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: sycnLeaveStudentFeeStatus
	 * @Description: 离校图书馆的同步接口
	 * 备注：学校接口问题 先停用接口调用
	 * @throws
	 */
	@Scheduled(cron = "${STUDENT_BOOK_SYCN_TIMER}")
	public void sycnBookStudentFeeStatus() throws Exception{
		List<LeaveSchool> studentList = leaveService.queryLibraryStudentList();// .queryOneCardStudentList();
		if(null!=studentList)
		{
			bookService.checkConnect();
			for(LeaveSchool student : studentList)
			{
				String status = "";
				if(null!=student.getStatus() && "1".equals(student.getStatus()))
					 status = bookService.getBookReturnStatus(student.getStudent().getId(),true);
				else
					 status = bookService.getBookReturnStatus(student.getStudent().getId(),false);
				
				leaveService.operateLibrary(student.getStudent().getId(), status);
			}
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: sycnLeaveStudentFeeStatus
	 * @Description: 离校一卡通缴费的同步接口
	 * @throws
	 */
	@Scheduled(cron = "${STUDENT_YKT_FEE_SYCN_TIMER}")
	public void sycnStudentYktFee() throws Exception
	{
		List<StudentInfoModel> studentList = leaveService.queryOneCardStudentList();
		if(null!=studentList)
		{
			Map<String,Object> map = feeService.getYktSignIn();
			if(null != map && "1".equals(map.get("resultFlag")))
			{
				List list = (List) map.get("resultValue");
				for(StudentInfoModel student : studentList)
				{
					String fee = feeService.getStudentYktFee(student.getId(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString());
					leaveService.updateLeaveOneCard(student.getId(), fee);
				}
			}
		}
	}

	/**
	 * 
	 * @Title: sycnLeaveStudentFeeStatus
	 * @Description: 退宿信息接口同步， 实时同步 每间隔10分钟
	 * @throws Exception
	 * @throws
	 */
//	@Scheduled(cron = "${STUDENT_DROOM_SYCN_TIMER}")
	public void sycnLeaveStudentDromStatus()
	{
		List<StudentInfoModel> studentList = leaveService.queryDormStudentList();
		if(null!=studentList)
		{
			for(StudentInfoModel student : studentList)
			{
				String status = dormService.isTuiSu(student.getId());
				leaveService.updateLeaveDorm(student.getId(), status);
			}
		}
	}
	
	/**
	 * 学生政治面貌同步至团员信息
	 */
	@Scheduled(cron = "${XG_LEAGUE_MEMBER_SYNC_TIMER}")
	public void sycnLeagueMember(){
		procedureService.execProcedure("HKY_PROC_LEAGUE_MEMBER_SYN");
		logger.debug("学生政治面貌同步至团员信息......");
	}
	
	/***
	 * 学生学期分数同步
	 */
	@Scheduled(cron = "${STUDENT_TERM_SCORE_SYCN_TIMER}")
	public void sycnStudentScore(){
		List<StudentInfoModel> studentList = this.scoreService.queryStudentList();
		System.out.println("分数同步开始");
		for (StudentInfoModel student : studentList) {
			scoreService.syncStuTermScore(student);
		}
		System.out.println("分数同步结束");
	}
}
