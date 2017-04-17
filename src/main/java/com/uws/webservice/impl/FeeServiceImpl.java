package com.uws.webservice.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;

import org.apache.axis.client.Call;
import org.springframework.stereotype.Service;

import com.singlee.dto.business.InParamFITaxCountQry;
import com.uws.core.base.BaseServiceImpl;
import com.uws.util.XmlPrase4Fee;
import com.uws.webservice.IFeeServcie;

/**
 * 
* @ClassName: FeeServiceImpl 
* @Description: 财务接口调用
* @author 联合永道
* @date 2015-9-1 下午2:24:40 
*
 */
@Service("com.uws.webservice.impl.FeeServiceImpl")
public class FeeServiceImpl extends BaseServiceImpl implements IFeeServcie
{

	/**
	 * 描述信息: 根据学号去学生的缴费状态 汉字
	 * @param idNumber
	 * @param attrName
	 * @return
	 * @throws Exception 
	 * @see com.uws.webservice.IFeeServcie#getStudentFeeStatusStr(java.lang.String)
	 */
	@Override
    public String getStudentFeeStatusStr(String idNumber,String attrName)
    {
	   try{
			 org.apache.axis.client.Service service = new  org.apache.axis.client.Service();
	         Call call = (Call) service.createCall();
	         QName qn = new QName("http://business.dto.singlee.com", "InParamFITaxCountQry");
	         call.registerTypeMapping(InParamFITaxCountQry.class, qn, new org.apache.axis.encoding.ser.BeanSerializerFactory( InParamFITaxCountQry.class, qn),
	         new org.apache.axis.encoding.ser.BeanDeserializerFactory( InParamFITaxCountQry.class, qn));
	         call.setOperationName("doBusiness");
	         InParamFITaxCountQry classqry = new InParamFITaxCountQry();
			 classqry.OperID = "999992";
			 //classqry.SalaryTypeID=4;//写死
			 classqry.StudentID=idNumber;//输入学号    
			 //http://yxt.hzpt.edu.cn:8828/yxtjk/services/FIExeClientAdapter
			 //http://yxt.hzpt.edu.cn:8828/yxt/services/FIExeClientAdapter
			 String endpoint = "http://yxt.hzpt.edu.cn:8828/yxtjk/services/FIExeClientAdapter";
	         call.setTargetEndpointAddress(new java.net.URL(endpoint));
	         String result = "";
	          result = (String) call.invoke(new Object[] { classqry });
	          return XmlPrase4Fee.readStringXmlOutString(result);
         }catch(Exception ex){
            System.out.println("计财处缴费数据同步失败....");
         }
         return "接口连接超时";
    }

	
	/**
	 * 描述信息: 学生一卡通费用
	 * @param idNumber
	 * @return
	 * @throws Exception
	 * 2016-5-23 下午5:12:38
	 */
	@Override
    public String getStudentYktFee(String idNumber,String thirdTypeVal, String secret1Val ,String secret2Val)
    {
		try
        {
	        // 创建服务service  
	        URL url = new URL("http://esb.hzpt.edu.cn/esbpro/proxy/ProxyServiceYkt?wsdl");  
	        String namespace="http://www.hzsun.com/";
	        QName sname= new QName(namespace,"ThirdWebservice");  
	        javax.xml.ws.Service service =javax.xml.ws.Service.create(url,sname);  
	        //创建DIspatch  
	        Dispatch<SOAPMessage> dispatch=service.createDispatch(new QName(namespace,"ThirdWebserviceSoap"),  SOAPMessage.class, javax.xml.ws.Service.Mode.MESSAGE);  
	        SOAPMessage message = MessageFactory.newInstance().createMessage();
	        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        envelope.addNamespaceDeclaration("xsi", "http://www.hzsun.com/");
	        SOAPHeader header = envelope.getHeader();
	        SOAPBody body = envelope.getBody();
	        QName securityHeaderType = new QName(namespace,"SecurityHeader","hzs");
	        javax.xml.soap.SOAPHeaderElement securityHeaderElement = header.addHeaderElement(securityHeaderType);
	        QName thirdType = new QName(namespace,"ThirdType","hzs");
	        QName secret1 = new QName(namespace,"Secret1","hzs");
	        QName secret2 = new QName(namespace,"Secret2","hzs");
	        SOAPElement thirdTypeElement = securityHeaderElement.addChildElement(thirdType);
	        thirdTypeElement.addTextNode(thirdTypeVal);
	        SOAPElement secret1Element = securityHeaderElement.addChildElement(secret1);
	        secret1Element.addTextNode(secret1Val);
	        SOAPElement Secret2Element = securityHeaderElement.addChildElement(secret2);
	        Secret2Element.addTextNode(secret2Val);
	        QName getAccDBMoney = new QName(namespace,"GetAccDBMoney","hzs");
	        javax.xml.soap.SOAPBodyElement getAccDBMoneyElement = body.addBodyElement(getAccDBMoney);
	         
	        QName sIDNo = new QName(namespace,"sIDNo","hzs");
	        QName nIDType = new QName(namespace,"nIDType","hzs");
	        QName nEWalletNum = new QName(namespace,"nEWalletNum","hzs");
	          
	        SOAPElement sIDNoElement = getAccDBMoneyElement.addChildElement(sIDNo);
	        sIDNoElement.addTextNode(idNumber);
	        SOAPElement nIDTypeElement = getAccDBMoneyElement.addChildElement(nIDType);
	        nIDTypeElement.addTextNode("1");
	        SOAPElement nEWalletNumElement = getAccDBMoneyElement.addChildElement(nEWalletNum);
	        nEWalletNumElement.addTextNode("1");
	        //通过Dispatch传递消息,会返回相应消息  
	        SOAPMessage response = dispatch.invoke(message);  
	        return praseResponseXml(response);
        } catch (Exception e){
        	 System.out.println(idNumber + " ： 一卡通数据同步失败....");
        }
		return "数据同步异常";
    } 

	/**
	 * 
	 * @Title: praseResponseXml
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param response
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
    private String praseResponseXml( SOAPMessage response)
	{
        try
        {
	        Iterator<SOAPElement> it = response.getSOAPBody().getChildElements(); 
	        SOAPElement element = null;
	        while(it.hasNext())
	        {
	        	 element = (SOAPElement) it.next();
	        	 if(element.getNodeName().equals("GetAccDBMoneyResponse"))
	        	 {
	        		 Iterator<SOAPElement> it1 =  element.getChildElements();
	        		 while(it1.hasNext())
	        		 {
	        			 element = (SOAPElement) it1.next();
	        			 if(element.getNodeName().equals("nMoney"))
	        				 return element.getValue();
	        		 }
	        	 }
	        }
        } catch (SOAPException e){
        	 System.out.println(" ： 一卡通数据数据解析失败....");
        }
		return "数据同步异常";
	}


	/**
	 * 描述信息: 一卡通签到
	 * @return
	 * @throws Exception
	 * 2016-5-23 下午5:28:31 
	 */
	@Override
    public Map<String,Object> getYktSignIn()
    {
		Map<String, Object> map = null;
        try
        {
	        map = new HashMap<String, Object>();
	        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
	        org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();
	        call.setTargetEndpointAddress("http://esb.hzpt.edu.cn/Hzaspt_OSB_project/proxy/ProxyServiceYkt?wsdl");
	        QName qName = new QName("http://www.hzsun.com/", "SignIn");
	        call.setOperationName(qName);
	        call.setReturnType(XMLType.XSD_STRING);
	        String result = (String) call.invoke(new Object[] {});
	        map.put("resultFlag", result);
	        map.put("resultValue", call.getOutputValues());
        } catch (Exception e){
        	 System.out.println(" ： 一卡通接口签到失败 ");
        }
		return map;
    }
}
