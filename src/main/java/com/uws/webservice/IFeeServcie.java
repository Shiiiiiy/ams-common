package com.uws.webservice;

import java.util.Map;

import com.uws.core.base.IBaseService;

/**
 * 
* @ClassName: IFeeServcie 
* @Description: 财务接口调用
* @author 联合永道
* @date 2015-9-1 下午2:23:46 
*
 */
public interface IFeeServcie extends IBaseService
{
	/**
	 * 
	 * @Title: getStudentFeeStatusStr
	 * @Description: 根据学号去学生的缴费状态 汉字
	 * @param idNumber
	 * @param attrName
	 * @return
	 * @throws
	 */
	public String getStudentFeeStatusStr(String idNumber,String attrName);
	
	/**
	 * 
	 * @Title: getStudentYktFee
	 * @Description: 查询学生一卡通费用
	 * @param idNumber
	 * @param thirdTypeVal
	 * @param secret1Val
	 * @param secret2Val
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public String getStudentYktFee(String idNumber,String thirdTypeVal, String secret1Val ,String secret2Val);
	
	/**
	 * 
	 * @Title: getYktSignIn
	 * @Description: 一卡通签到
	 * @return
	 * @throws
	 */
	public Map<String,Object> getYktSignIn();
	
}
