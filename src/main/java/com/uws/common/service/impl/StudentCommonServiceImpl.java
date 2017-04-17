package com.uws.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.uws.common.dao.IStudentCommonDao;
import com.uws.common.service.IStudentCommonService;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.hibernate.dao.support.Page;
import com.uws.core.util.DataUtil;
import com.uws.domain.integrate.StudentApproveSetModel;
import com.uws.domain.orientation.StudentGuardianModel;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.sys.model.Dic;
import com.uws.user.model.User;

/**
 * 
 * @ClassName: StudentCommonServiceImpl
 * @Description: 学生信息通用内部接口 实现类型
 * @author 联合永道
 * @date 2015-7-27 上午11:04:15
 * 
 */
@Service("com.uws.common.service.impl.StudentCommonServiceImpl")
public class StudentCommonServiceImpl extends BaseServiceImpl implements IStudentCommonService
{
	@Autowired
	private IStudentCommonDao studentCommonDao;
	/**
	 * 描述信息: 按照ID查询学生信息
	 * @param id
	 * @return
	 * @see com.uws.common.service.IStudentCommonService#queryStudentById(java.lang.String)
	 */
	public StudentInfoModel queryStudentById(String id)
	{
		if (StringUtils.hasText(id))
			return (StudentInfoModel) studentCommonDao.get(StudentInfoModel.class, id);
		return null;
	}

	/**
	 * 描述信息: 按照条件分页查询 学生信息
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 * @see com.uws.common.service.IStudentCommonService#queryStudentPage(java.lang.Integer, java.lang.Integer, com.uws.domain.orientation.StudentInfoModel)
	 */
	@Override
    public Page queryStudentPage(Integer pageNo, Integer pageSize,StudentInfoModel student)
    {
	    return studentCommonDao.queryStudentPage(pageNo,pageSize,student);
    }

	/**
	 * 描述信息: 按照身份证号查询学生信息
	 * @param code
	 * @return
	 * @see com.uws.common.service.IStudentCommonService#queryStudentByCode(java.lang.String)
	 */
	@Override
	public StudentInfoModel queryStudentByCode(String certificateCode) {
		if (StringUtils.hasText(certificateCode))
			return (StudentInfoModel) studentCommonDao.getStudentByCode(certificateCode);
		return null;
	}

	/**
	 * 描述信息: 学号查询
	 * @param studentNo
	 * @return
	 * @see com.uws.common.service.IStudentCommonService#queryStudentByStudentNo(java.lang.String)
	 */
	@Override
    public StudentInfoModel queryStudentByStudentNo(String studentNo)
    {
		if (StringUtils.hasText(studentNo))
			return (StudentInfoModel) studentCommonDao.queryStudentByStudentNo(studentNo);
		return null;
    }

	/**
	 * 根据发起人(学生)找到班长
	 * @param initiator		发起人信息
	 * @return	学生班长
	 */
	@Override
	public StudentApproveSetModel getClassMonitor(User initiator) 
	{
		if(DataUtil.isNotNull(initiator) && DataUtil.isNotNull(initiator.getId())){
			return this.studentCommonDao.queryClassMonitor(initiator.getId());
		}
		return new StudentApproveSetModel();
	}

	/**
	 * 团员团籍异动时，同步学生基础信息的政治面貌
	 * politicalDic 政治面貌的数据字典
	 * @param studentId
	 * @param politicalDic
	 */
	@Override
	public void synPoliticalStatus2Student(String studentId,Dic politicalDic) 
	{
		if (StringUtils.hasText(studentId) && null!=politicalDic)
		{
			StudentInfoModel student = (StudentInfoModel) studentCommonDao.get(StudentInfoModel.class, studentId);
			if(null != student)
			{
				student.setPoliticalDic(politicalDic);
				studentCommonDao.update(student);
			}
		}
	}
	
	/**
	 * 描述信息: 按照条件分页查询 毕业学生信息
	 * @param pageNo
	 * @param pageSize
	 * @param student
	 * @return
	 * @see 
	 * */
	@Override
    public Page queryCheckGraduateStudent(Integer pageNo, Integer pageSize,StudentInfoModel student){
	    return studentCommonDao.queryCheckGraduateStudent(pageNo,pageSize,student);
    }
	
	
	/**
	 * 
	 * @Title: IStudentCommonService.java 
	 * @Package com.uws.common.service 
	 * @Description:判断学生是否已毕业（所在的班级是毕业班）
	 * @author LiuChen 
	 * @date 2015-12-15 下午4:10:29
	 */
	public boolean checkIsGraduateStudent(String studentId) {
		boolean bol = false;
		if(StringUtils.hasText(studentId))
		{
			StudentInfoModel studentInfo = studentCommonDao.queryGraduateStudentByStudentId(studentId);
			if(null!=studentInfo && !"".equals(studentInfo.getId()))
				bol = true;
		}
		return bol;
	}

	
	/**
	 * 描述信息: 学号查询学生监护人信息
	 * @param studentNo
	 * @return
	 * @see com.uws.common.service.IStudentCommonService#queryStudentGuardianByStudentNo(java.lang.String)
	 */
	@Override
    public StudentGuardianModel queryStudentGuardianByStudentNo(String studentNo, String seqNum){
		if (StringUtils.hasText(studentNo))
			return (StudentGuardianModel) studentCommonDao.queryStudentGuardianByStudentNo(studentNo,seqNum);
		return null;
    }
}
