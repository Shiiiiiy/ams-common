package com.uws.webservice.impl;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Service;

import com.uws.common.util.JsonUtils;
import com.uws.core.base.BaseServiceImpl;
import com.uws.webservice.IDromService;

/**
 * 
* @ClassName: DromServiceImpl 
* @Description: 宿舍同步接口
* @author 联合永道
* @date 2016-5-27 上午10:49:00 
*
 */
@Service("com.uws.webservice.impl.DromServiceImpl")
public class DromServiceImpl extends BaseServiceImpl implements IDromService
{

	@Override
    public String isTuiSu(String studentId)
    {
		 String status = "同步异常";
		 HttpClient client = new HttpClient();
	     String bathUrl = "http://172.18.180.87:8080/rest/api/lixiao/stuLx/";
	     HttpMethod method = null;
		try
        {
	        method=new GetMethod(bathUrl + studentId);  
	        method.setRequestHeader("Connection", "close"); 
	        client.executeMethod(method);  
	        int statusCode = method.getStatusCode(); 
	        if( 200 == statusCode)
	        {
	        	 String jsonString = method.getResponseBodyAsString();
	 	         JSONObject json = JsonUtils.string2JsonObject(jsonString);
	 	         JSONObject data =  (JSONObject) json.get("data"); 
	 	         if(null == data || "null".equals(data.toString()))
	 	        	status = "未退宿";
	 	         else 
	 	        	status = (String) data.get("sfts");
	        }
        } catch (Exception e){
	        e.printStackTrace();
        }finally
        {
	        method.releaseConnection();
        }
        return status;
    }

}
