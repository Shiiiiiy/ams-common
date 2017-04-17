package com.uws.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
* @ClassName: XmlPrase4Fee 
* @Description: 财务接口解析工具类
* @author 联合永道
* @date 2015-9-1 下午2:31:31 
*
 */
public class XmlPrase4Fee
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map readStringXmlOut(String xml)
	{
		Map map = new HashMap();
		Document doc = null;
		try
		{
			// 将字符串转为XML
			doc = DocumentHelper.parseText(xml);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			Iterator iter = rootElt.elementIterator("ROWDATA");
			// 遍历ROWDATA节点
			while (iter.hasNext())
			{
				Element recordEle = (Element) iter.next();
				Iterator iters = recordEle.elementIterator("ROW");
				// 遍历Header节点下的ROW节点
				while (iters.hasNext())
				{
					Element itemEle = (Element) iters.next();
					if("01".equals(itemEle.attribute("JFDM").getText()))
					{
						String jfzt = itemEle.attribute("JFZT").getText();
						String jfmc = itemEle.attribute("JFMC").getText();
						map.put("JFZT", jfzt);
						map.put("JFMC", jfmc);
					}
				}
			}
		}catch (Exception e){
			System.out.println("文件解析异常");
		}
		return map;
	}
	
	@SuppressWarnings({ "rawtypes"})
    public static String readStringXmlOutString(String xml)
	{
		Document doc = null;
		StringBuffer statusStr = new StringBuffer();
		// 将字符串转为XML
		try
        {
	        doc = DocumentHelper.parseText(xml);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			Iterator iter = rootElt.elementIterator("ROWDATA");
			// 遍历ROWDATA节点
			while (iter.hasNext())
			{
				Element recordEle = (Element) iter.next();
				Iterator iters = recordEle.elementIterator("ROW");
				// 遍历Header节点下的ROW节点
				while (iters.hasNext())
				{
					Element itemEle = (Element) iters.next();
					statusStr.append(itemEle.attribute("JFZT").getText()).append("#");
				}
			}
        } catch (DocumentException e){
        	System.out.println("缴费状态分析异常");
        }
		
		 String result = statusStr.toString();
    	 if(( result.contains("缴清#") && result.contains("欠费#")||result.contains("未缴#") ) || result.contains("部缴#"))
    		 result = "部缴";
    	 else if(result.contains("缴清#") && !result.contains("欠费#") && !result.contains("未缴#"))
    		 result = "缴清";
    	 else if(!result.contains("缴清#") && (result.contains("欠费#")))
    		 result = "欠费";
    	 else if(!result.contains("缴清#") && !(result.contains("欠费#")) && (result.contains("未缴#")))
    		 result = "未缴";
		
		return result;
	}
	
}
