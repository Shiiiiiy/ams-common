package com.uws.task.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.uws.core.hibernate.dao.impl.BaseDaoImpl;
import com.uws.task.dao.IExecuProcedureDao;

/**
 * 
* @ClassName: ExecuProcedureDaoImpl 
* @Description: 存储过程调用DAO实现
* @author 联合永道
* @date 2015-7-29 下午2:42:48 
*
 */
@Repository("com.uws.task.dao.impl.ExecuProcedureDaoImpl")
public class ExecuProcedureDaoImpl extends BaseDaoImpl implements IExecuProcedureDao
{
	/**
	 * 描述信息: 调用
	 * @param procedureName
	 * @see com.uws.task.dao.IExecuProcedureDao#execProcedure(java.lang.String)
	 */
	@Override
	@SuppressWarnings("deprecation")
    public void execProcedure(String procedureName)
    {
		Session session = this.sessionFactory.openSession();  
		Connection con = session.connection(); 
	    CallableStatement   proc   =   null;     
        StringBuffer sql = new StringBuffer("{call "+procedureName+"(");
        sql.append(")}");
	    try
        {
	    	proc = con.prepareCall(sql.toString());     
	        proc.execute();
        } catch (SQLException e)
        {
	        e.printStackTrace();
        }     
	    session.close();  
    }
	
	/**
	 * 
	 * @Title: execProcedure
	 * @Description:带参数调用执行存储过程
	 * @param procedureName
	 * @param params
	 * @throws
	 */
	@SuppressWarnings("deprecation")
    public void execProcedure(String procedureName,Object[] params)
	{
		Session session = this.sessionFactory.openSession();  
		Connection con = session.connection(); 
	    CallableStatement   proc   =   null;     
        StringBuffer sql = new StringBuffer("{call "+procedureName+"(");
        int len = null == params ? 0 : params.length;
    	//参数设置
        for(int i=0;i<len;i++)
        {
    		if(0==i)
         		sql.append("?");
         	else
         		sql.append(",?");
        }
        sql.append(")}");
	    try
        {
	    	proc = con.prepareCall(sql.toString());   
	    	for(int k=0;k<len;k++)
	    		proc.setObject(k+1,params[k]);
	        proc.execute();
        } catch (SQLException e)
        {
	        e.printStackTrace();
        }     
	    session.close();  
	}
	

	/**
	 * 
	 * @Title: getStudentReportProcedureResult
	 * @Description: 学生报到进度的统计
	 * @param yearDicId
	 * @param queryType
	 * @param queryObjectId
	 * @return
	 * @throws
	 */
	@Override
	@SuppressWarnings("deprecation")
	public String getStudentReportProcedureResult(String yearDicId,String queryType,String queryObjectId)
	{
		Session session = this.sessionFactory.openSession();  
		Connection con = session.connection(); 
	    CallableStatement   proc   =   null;     
        StringBuffer sql = new StringBuffer("{call STUDENT_REPORT_RATE_COMPUTE(?,?,?,?) }");
	    try
        {
			proc = con.prepareCall(sql.toString());
			proc.setString(1, yearDicId);
			proc.setString(2, queryType);
			proc.setString(3, queryObjectId);
			proc.registerOutParameter(4, Types.VARCHAR);
			proc.execute();
			String resStr = proc.getString(4);
			if(StringUtils.hasText(resStr))
				return resStr.substring(0, resStr.length()-1);
			return "";
        } catch (SQLException e)
        {
	        e.printStackTrace();
        }     
	    session.close();  
	    return null;
	}
	
}
