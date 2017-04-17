package com.uws.common.dao;

import com.uws.core.hibernate.dao.IBaseDao;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.domain.integrate.StudentApproveSetModel;
import com.uws.domain.orientation.StudentGuardianModel;
import com.uws.domain.orientation.StudentInfoModel;

/**
 * 
* @ClassName: IStudentCommonDao 
* @Description: 学生信息综合内部调用接口DAO
* @author 联合永道
* @date 2015-7-27 上午11:05:31 
*
 */
public interface IStudentCommonDao extends IBaseDao
{
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
	 * @Title: getStudentByCode
	 * @Description: 描述信息: 按照身份证号查询学生信息
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentInfoModel getStudentByCode( String  certificateCode);
	
	/**
	 * 
	 * @Title: queryStudentByCode
	 * @Description: 描述信息: 按学生学号查询
	 * @param studentId
	 * @return
	 * @throws
	 */
	public StudentInfoModel queryStudentByStudentNo( String  studentNo);
	
	/**
	 * 获取学生所在班级的班长
	 * @param stuId	学生ID
	 * @return
	 */
	public StudentApproveSetModel queryClassMonitor(String stuId);
	
	/**
	 * 
	 * @Title: queryStudentPage
	 * @Description: 按条件分页查询毕业生
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 * @throws
	 */
	public Page queryCheckGraduateStudent(Integer pageNo,Integer pageSize,StudentInfoModel student);
	
	/**
	 * 
	 * @Title: IStudentCommonDao.java 
	 * @Package com.uws.common.dao 
	 * @Description: 判断学生是否已毕业（所在的班级是毕业班）
	 * @author LiuChen 
	 * @date 2015-12-15 下午4:13:17
	 */
	public StudentInfoModel queryGraduateStudentByStudentId(String studentId);
	/**
	 * @param studentNo
	 * @Description:根据学号查询学生学生监护人信息
	 * @author wjr
	 * @date 2015-12-29 
	 */
	public StudentGuardianModel queryStudentGuardianByStudentNo(String studentNo, String seqNum);
}
