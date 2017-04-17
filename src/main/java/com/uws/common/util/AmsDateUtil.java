package com.uws.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.uws.core.util.DataUtil;
import com.uws.core.util.DateUtil;

/**
 *	自定义日期处理工具
 */
public class AmsDateUtil extends DateUtil{

	private static final long serialVersionUID = -6571207533080162892L;

	/**
	 * 字符串日期转换为Date类型
	 * @param value
	 * @return
	 */
	public static java.util.Date toTime(String value)
	{
		 GregorianCalendar calendar = new GregorianCalendar();
	     if ((value != null) && (value.length() > 10)) {
	       String date = value.substring(0, 10);
	       String[] d = DataUtil.split(date, "-");
	       int year = DataUtil.strToInt(d[0]);
	       int month = DataUtil.strToInt(d[1]) - 1;
	       int day = DataUtil.strToInt(d[2]);
	       
	       String time = value.substring(date.length());
	       String [] timeArray = DataUtil.split(time.trim(), ":");
	       if(timeArray.length == 3){
	    	   
	    	   int hour = DataUtil.strToInt(DataUtil.isNotNull(timeArray[0])?timeArray[0]:"0");
	    	   int minute = DataUtil.strToInt(DataUtil.isNotNull(timeArray[1])?timeArray[1]:"0");
	    	   int second = DataUtil.strToInt(DataUtil.isNotNull(timeArray[2])?timeArray[2]:"0");
	    	   calendar = new GregorianCalendar(year, month, day,hour,minute,second);
	       }else{
	    	   
	    	   calendar = new GregorianCalendar(year, month, day);
	       }
	 
	       return new java.util.Date(calendar.getTimeInMillis());
	  }else if((value != null) && (value.length() == 10)){
		  	String date = value.substring(0, 10);
		    String[] d = DataUtil.split(date, "-");
		    int year = DataUtil.strToInt(d[0]);
		    int month = DataUtil.strToInt(d[1]) - 1;
		    int day = DataUtil.strToInt(d[2]);
		  
		   calendar = new GregorianCalendar(year, month, day);
		   return new java.util.Date(calendar.getTimeInMillis());
	  }
	     return null;
	}

	/**
	 * 获取当前日期所在的月初日期
	 * @param date
	 * @return
	 */
	public static Date getMonthStart(Date date) { 
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(date); 
        int index = calendar.get(Calendar.DAY_OF_MONTH); 
        calendar.add(Calendar.DATE, (1 - index)); 
        return calendar.getTime(); 
    } 
	
	/**
	 * 获取当前日期的上个月的月初日期
	 * @param date
	 * @return
	 */
	public static String getLastMonthStart(Date date) { 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date); 
		int curYear = calendar.get(Calendar.YEAR); 
		int lastMonth = calendar.get(Calendar.MONTH); 
		String month = (lastMonth<10)?"0"+lastMonth:lastMonth+"";
		
		return curYear+"-"+month+"-01"; 
	} 

	/**
	 * 获取当前日期所在的月末日期
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) { 
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(date); 
        calendar.add(Calendar.MONTH, 1); 
        int index = calendar.get(Calendar.DAY_OF_MONTH); 
        calendar.add(Calendar.DATE, (-index)); 
        return calendar.getTime(); 
    } 

    /**
     * 获取当前日期的后一天
     * @param date
     * @return
     */
	public static Date getNext(Date date) { 
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(date); 
        calendar.add(Calendar.DATE, 1); 
        return calendar.getTime(); 
    }
    
    /**
     * 获取当前日期的前一天
     * @param date
     * @return
     */
	public static Date getLast(Date date) { 
    	Calendar calendar = Calendar.getInstance(); 
    	calendar.setTime(date); 
    	calendar.add(Calendar.DATE, -1); 
    	return calendar.getTime(); 
    }
    
	   /**
	    * 比较两个String类型的日期是否相等
	    * @param str1
	    * @param str2
	    * @return
	    */
	   public static boolean compareDate(String str1, String str2) {
		   String s1=DateUtil.formatDate(str1.trim());
		   String s2=DateUtil.formatDate(str2.trim());
		   return java.sql.Date.valueOf(s1).equals(java.sql.Date.valueOf(s2));
	   }
	   
		/**
		 * 获取日期范围内的所有日期
		 * @param curDate
		 * @param endDate
		 * @param rangeDateList
		 */
		public static void fomateRangeDateList(Date curDate, Date endDate,List<String> rangeDateList) {
			rangeDateList.add(getCustomDateString(curDate, "yyyy-MM-dd"));
			if(hasNext(curDate,endDate)){
				fomateRangeDateList(getNext(curDate),endDate,rangeDateList);
			}else{
				return;
			}
		}
	
		private static boolean hasNext(Date curDate, Date endDate) {
			boolean returnValue = false;
			Date nextDate = getNext(curDate);
			if(nextDate.before(endDate)){
				returnValue = true;
			}
			return returnValue;
		}
		
		/**
		 * 获取日期范围内的天数
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static long getDiffDays(String startDate,String endDate){
			Date d1 =  java.sql.Date.valueOf(startDate);
			Date d2 =  java.sql.Date.valueOf(endDate);
			long diffs = d2.getTime()-d1.getTime();
			return diffs/1000/60/60/24;
		}
		
		/**
		 * 判断日期的是否再这个日期时间段内
		 * @param curDate 
		 * @param startDate  开始日期
		 * @param endDate  结束日期
		 * @return
		 */
		public static boolean isDateScope(Date curDate, Date startDate,Date endDate) {
			boolean returnValue = false;
   
			int start = curDate.compareTo(startDate);
			
			int end = curDate.compareTo(getNext(endDate));
			
			if(start >=0  && end <=0 ){
				returnValue = true;
				
			}
			return returnValue;
		}
}
