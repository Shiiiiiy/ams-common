package com.uws.webservice;

import java.util.List;

import com.uws.core.base.IBaseService;

/**
 * 
* @ClassName: IStudentScoreServcie 
* @Description: 学生成绩接口调用
* @author 联合永道
* @date 2015-9-1 下午2:23:46 
*
 */
public interface IStudentScoreServcie extends IBaseService
{
	
	/**
	 * 
	 * @Title: getStudentFeeStatusStr
	 * @Description:根据学年，学期，学号，传入时间，签名加密(MD5)
	 * @param idNumber
	 * @param attrName
	 * @return
	 * @throws
	 */
	public List<String> getStudentAverageScore(String schoolYear,String term,String stuNumber,String time);

}
