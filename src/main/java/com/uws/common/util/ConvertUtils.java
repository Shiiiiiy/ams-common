package com.uws.common.util;

/**
 *	 转换工具类
 */
public class ConvertUtils {

	   /**
	    * 将题目选项变为【大写英文字母】  
	    * @param inputStr
	    * @return
	    */
	  public static String num2UpperLetter(String inputStr) {  
		  return num2LowerLetter(inputStr).toUpperCase();
	  }
	  
	  /**
	   * 将题目选项变为【小写英文字母】  
	   * @param inputStr
	   * @return
	   */
	  public static String num2LowerLetter(String inputStr) {  
		  StringBuffer sbff = new StringBuffer();
		  for (byte b : inputStr.getBytes()) {
			  sbff.append((char) (b + 48));  
		  }
		  return sbff.toString();
	  }
	  
	  
}
