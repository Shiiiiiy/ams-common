package com.uws.webservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;
import org.webservice.common.ArrayOfString;
import org.webservice.common.WsScore;
import org.webservice.common.WsScoreSoap;

import com.uws.webservice.IStudentScoreServcie;
import com.uws.core.base.BaseServiceImpl;
import com.uws.core.util.MD5;


import org.apache.cxf.endpoint.Client;
import org.webservice.common.ArrayOfString;
/**
 * 
* @ClassName: FeeServiceImpl 
* @Description: 学生成绩接口调用
* @author 联合永道
* @date 2015-9-1 下午2:24:40 
*
 */
@Service("com.uws.webservice.impl.StudentScoreServcieImpl")
public class StudentScoreServcieImpl extends BaseServiceImpl implements IStudentScoreServcie{

	@Override
	public List<String> getStudentAverageScore(String schoolYearCode, String termCode,String stuNumber, String time){
		String key ="HZKJ201613026";
		//格式schoolYear code ：2015  term code 1   stuNumber:20150326  time : 年月日十分
		String sign = schoolYearCode+"#"+termCode+"#"+stuNumber+"#"+key+"#"+time;
		System.out.println(sign);
		List<String> result = new ArrayList();;
		try{
	        String signkey = MD5.crypt(sign).toUpperCase();
	        WsScore ws = new WsScore();
			WsScoreSoap wss = ws.getWsScoreSoap();
			
			ArrayOfString ret;
			ret = wss.getStudentAverageScore(schoolYearCode, termCode, stuNumber, time, signkey);
			result = ret.getString();
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
