package com.uws.common.service;

import com.uws.core.base.IBaseService;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.integrate.StudentApproveSetModel;
import com.uws.domain.orientation.StudentGuardianModel;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;
import com.uws.user.model.User;

/**
 * 
* @ClassName: IStudentCommonService 
* @Description: 学生信息通用内部接口
* @author 联合永道
* @date 2015-7-27 上午11:03:30 
*
 */
public interface IStudentCommonService extends IBaseService
{
	/**
	 * 
	 * @Title: queryStudentById
	 * @Description: 描述信息: 按照ID查询学生信息
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentInfoModel queryStudentById( String studentId );
	
	/**
	 * 
	 * @Title: queryStudentPage
	 * @Description: 按条件分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 * @throws
	 */
	public Page queryStudentPage(Integer pageNo,Integer pageSize,StudentInfoModel student);
	/**
	 * 
	 * @Title: queryStudentByCode
	 * @Description: 描述信息: 按照身份证号查询学生信息
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentInfoModel queryStudentByCode( String  certificateCode);
	
	/**
	 * 
	 * @Title: queryStudentByCode
	 * @Description: 描述信息:学生学号查询
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentInfoModel queryStudentByStudentNo( String  studentNo);

	/**
	 * 根据发起人(学生)找到班长
	 * @param initiator		发起人信息
	 * @return	学生班长
	 */
	public StudentApproveSetModel getClassMonitor(User initiator);
	
	/**
	 * 团员团籍异动时，同步学生基础信息的政治面貌
	 * politicalDic 政治面貌的数据字典
	 * @param studentId
	 * @param politicalDic
	 */
	public void synPoliticalStatus2Student(String studentId,Dic politicalDic);
	
	/**
	 * 
	 * @Title: queryCheckSteudent
	 * @Description: 毕业生按条件分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 * @throws
	 */
	public Page queryCheckGraduateStudent(Integer pageNo,Integer pageSize,StudentInfoModel student);
	
	/**
	 * 
	 * @Title: IStudentCommonService.java 
	 * @Package com.uws.common.service 
	 * @Description:判断学生是否已毕业（所在的班级是毕业班）
	 * @author LiuChen 
	 * @date 2015-12-15 下午4:10:29
	 */
	public boolean checkIsGraduateStudent(String studentId);


	/**
	 * 描述信息: 学号查询学生监护人信息
	 * @param studentNo
	 * @return
	 */
    public StudentGuardianModel queryStudentGuardianByStudentNo(String studentNo, String seqNum);

}
