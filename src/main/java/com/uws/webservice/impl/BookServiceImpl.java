package com.uws.webservice.impl;

import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.uws.common.util.JsonUtils;
import com.uws.core.base.BaseServiceImpl;
import com.uws.webservice.IBookService;

@Service("com.uws.webservice.impl.BookServiceImpl")
public class BookServiceImpl extends BaseServiceImpl implements IBookService
{

	/**
	 * 描述信息: 图书信息同步
	 * @param studentId
	 * @return
	 * 2016-5-20 下午1:32:49
	 */
	@Override
    public String getBookReturnStatus(String studentId,boolean flag)
    {
		try
		{
		    JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		    String wsdlUrl = "http://esb.hzpt.edu.cn/esbpro/proxy/ProxyServiceBook?wsdl";
		    //String wsdlUrl = "http://172.18.180.38:83/SErvice.asmx?wsdl";
	        org.apache.cxf.endpoint.Client client = dcf.createClient(wsdlUrl);  
	        //url为调用webService的wsdl地址  method
	        QName name=new QName("http://tempuri.org/", "CancelBarcode_XGH"); 
	        Object[] objects=client.invoke(name,studentId,flag);
	        return praseJson(objects[0].toString());

		} catch (Exception e){
			e.printStackTrace();
		}
		return "接口异常";
    }
	
	/**
	 * 
	 * @Title: praseJson
	 * @Description: 解析JSON字符串
	 * @param jsonStr
	 * @return
	 * @throws
	 */
	private String praseJson(String jsonStr)
	{
		if(StringUtils.hasText(jsonStr))
		{
			JSONObject o = JsonUtils.string2JsonObject(jsonStr);
			return o.get("RetValue").toString();
		}
		return "接口异常";
	}

	/**
	 * 描述信息: 检查连接
	 * 2016-5-27 上午11:27:37
	 */
	@Override
    public void checkConnect()
    {
		try
		{
		    JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		    String wsdlUrl = "http://esb.hzpt.edu.cn/esbpro/proxy/ProxyServiceBook?wsdl";
		    //String wsdlUrl = "http://172.18.180.38:83/SErvice.asmx?wsdl";
	        org.apache.cxf.endpoint.Client client = dcf.createClient(wsdlUrl);  
	        QName name=new QName("http://tempuri.org/", "CheckConnet"); 
	        client.invoke(name);
		} catch (Exception e){
			e.printStackTrace();
		}
    }
	
}
