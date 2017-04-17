package com.uws.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uws.core.base.BaseServiceImpl;
import com.uws.domain.orientation.StudentInfoModel;
import com.uws.task.dao.IExecuWebDao;
import com.uws.task.service.IExecuWebService;
import com.uws.webservice.IFeeServcie;
@Service("com.uws.task.service.impl.ExecuWebServiceimpl")
public class ExecuWebServiceimpl extends BaseServiceImpl implements IExecuWebService
{
	
	@Autowired
	private IExecuWebDao webDao;
	
	// 根据财务的接口
	@Autowired
	private IFeeServcie feeServcie;
	
	

	/**
	 * 共通的方法（通过学号调计财处的webservice接口，取得缴费状态）
	 * 
	 * @param 学年
	 *            
	 * @return boolean
	 */
	public void execCont(String  year) {
		if (year != null) {
			
			//取得当前学年的学生信息
			List<Object> empList =webDao.queryStudentBySchoolYear(year);
					
			if(empList !=null && empList.size()>0){
				
				
				for(Object studentemp:empList){
					
				StudentInfoModel student = (StudentInfoModel) studentemp;
					String feeStatus = "";
					try {
						String costState=student.getCostState();
						if(costState!=null && ("".equals(costState) || "0".equals(costState))){
							feeStatus = feeServcie.getStudentFeeStatusStr(student.getCertificateCode(), "JFZT");
						
							// 缴清 状态 3 
							if (feeStatus != null && !"".equals(feeStatus)) {
								if ("缴清".equals(feeStatus)) {
									student.setCostState("1");
									webDao.updateStudentCostState(student.getId(),"1");
								} else {
									student.setCostState("0");
								}
							} else {
							student.setCostState("0");
							}
						
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
					
				}
			
	}

}

}
