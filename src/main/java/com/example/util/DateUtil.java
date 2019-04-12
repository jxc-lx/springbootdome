package com.example.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 *  to correct time pattern. Minutes should be mm not MM (MM is month). 
 */
@SuppressWarnings("all")
public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static final String TIME_PATTERN = "HH:mm";

    /** 形如：2008-02-27 */
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private DateUtil() {
    }

    



   

    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @see SimpleDateFormat
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String aMask, String strDate)
      throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * MM/dd/yyyy HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * 
     * @see SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

   
   
    
    /**
     * 返回当前日期
     * @return
     */
    public static Date getCurrentDate(){
    	Date currentDate=null;
    	Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 currentDate = sdf.parse(sdf.format(c.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentDate;
    }
    
    public static String getCurrDate(){
    	
    	Calendar c = Calendar.getInstance();
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	return sdf.format(c.getTime());
    }

    public static String[] getCurrDateArray(){
    	Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDate=sdf.format(c.getTime());
    	return currDate.split("-");
    }
    
    /**
	 * 获得年月的最后日期
	 * @param strDate year and month  不能为空
	 * @return
	 */
	public static String FormatDate(String year,String month){
		
		String rtnDate = "";
		try{
			Calendar calendar = Calendar.getInstance();			
			if(null != year && !"".equals(year)
					&& null != month && !"".equals(month)){
				int intYear = Integer.parseInt(year);
				int intMonth = Integer.parseInt(month);
				calendar.set(Calendar.YEAR, intYear);
				calendar.set(Calendar.MONTH, intMonth-1);
				calendar.set(Calendar.DATE, 1);
				//获得当前年月的天数
				int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				if(month.length()==1){
					rtnDate =  String.valueOf(year) +"-0"+ String.valueOf(month) + "-" + String.valueOf(dayOfMonth);
				}else{
					rtnDate =  String.valueOf(year) +"-"+ String.valueOf(month) + "-" + String.valueOf(dayOfMonth);
				}
			}
			
		}catch(Exception e){
			
		}		
		return rtnDate;
	}
    
	/**
	 * 字符串转换成日期
	 * 
	 * @param dateString 日期字符串
	 * @param fString yyyy-MM-dd
	 * @return
	 */
	public static Date formatDate(String dateString, String fString) {
		SimpleDateFormat sFormat = new SimpleDateFormat(fString);
		Date date = null;
		try {
			if (dateString != null && !"".equals(dateString)) {
				date = sFormat.parse(dateString);
			} else {
				Date d = new Date();
				date = sFormat.parse(d.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 
	 * @param date
	 *            指定的时间
	 * @param format
	 *            指定的格式
	 * @return
	 */
	public static String format(Date date, String format) {
		if (date == null)
			return "";
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 获得 nowDate +/- dayNum 天的日期
	 * 
	 * @param nowDate 日期格式为 yyyy-MM-dd
	 * @param dayNum 天数
	 * @return yyyy-MM-dd
	 */
	public static String getSomeDay(String nowDate,int dayNum){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(formatDate(nowDate, "yyyy-MM-dd").getTime());
		c.add(Calendar.DATE, dayNum);
		return format(c.getTime(), "yyyy-MM-dd");
	}
	
	/**
	 * 获得 nowDate +/- monthNum 月的日期
	 * 
	 * @param nowDate 日期格式为 yyyy-MM-dd
	 * @param monthNum 月数
	 * @return yyyy-MM-dd
	 */
	public static String getSomeMonth(String nowDate,int monthNum){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(formatDate(nowDate, "yyyy-MM-dd").getTime());
		c.add(Calendar.MONTH, monthNum);
		return format(c.getTime(), "yyyy-MM-dd");
	}
	
    public static void main(String args[]){
    	
    	System.out.println(DateUtil.getCurrDate());
    }
    /**
	 * 获取N天后日期.
	 * 
	 * @param inDate
	 * @param days
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateAfter(String inDate, long days)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(inDate);
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, (int) (now.get(Calendar.DATE) + days));
		return now.getTime();
	}
	
	/**
	 * 获得年
	 * 
	 * <br>
	 * cmh > Nov 12, 2012 11:13:36 AM
	 * @param date 日期
	 * @param num +/- 数
	 * @param format 格式化
	 * @return
	 */
	public static String getYear(Date date, int num, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, num);
		return format(c.getTime(), format);
	}
	
	/**
	 * 获得年
	 * 
	 * <br>
	 * cmh > Nov 12, 2012 11:13:36 AM
	 * @param num +/- 数
	 * @param format 格式化
	 * @return
	 */
	public static String getYear(int num, String format) {
		return getYear(new Date(), num, format);
	}
	
	/**
	 * 获得年
	 * 
	 * <br>
	 * cmh > Nov 12, 2012 11:16:01 AM
	 * @param num
	 * @return
	 */
	public static String getYear(int num){
		return getYear(num,"yyyy");
	}
	
	/**
	 * 获得下一年
	 * <br>默认 yyyy
	 * 
	 * <br>
	 * cmh > Nov 12, 2012 11:16:47 AM
	 * @return
	 */
	public static String getNextYear(){
		return getYear(+1);
	}
	
	/**
	 * 给指定的日期加上天数 如：2008-2-27;加上10天则返回：2008-3-8
	 * 
	 * @param dDate
	 * @param iNbDay
	 * @return
	 */
	public static Date addDay(Date dDate, long iNbDay) {
		return addHour(dDate, iNbDay * 24);
	}
    public static String convertDateToString(Date date,String format){
    	String result="";
    	if(null==date||null==format){
    		return result;
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
        result = sdf.format(date);
    	return result;
    }
 
	/**
	 * 给指定的日期减去天数 如：2008-2-27;减去10天则返回：2008-2-17
	 * 
	 * @param dDate
	 * @param iNbDay
	 * @return
	 */
	public static Date reduceDay(Date dDate, long iNbDay) {
		return addDay(dDate, 0 - iNbDay);
	}

	/**
	 * 给指定的日期加上hour个小时.
	 * 
	 * @param dDate
	 *            指定的日期
	 * @param hour
	 *            几个小时
	 * @return 返回加完小时之后 的日期
	 */
	public static Date addHour(Date dDate, long hour) {
		return addSecond(dDate, hour * 60L * 60L);
	}
	
	/**
	 * 给指定的日期加上second秒
	 * 
	 * @param dDate
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date dDate, long second) {

		long datems = dDate.getTime();
		long secondms = second * 1000L;
		long newDateMs = datems + secondms;
		Date result = new Date(newDateMs);
		return result;

	}
	
	/**
	 * 两个日期进行相减
	 * 
	 * @param date1
	 * @param date2
	 * @return 返回两个日期差的毫秒数
	 */
	public static long subDate(Date date1, Date date2) {
		long date1ms = date1.getTime();
		long date2ms = date2.getTime();
		return date2ms - date1ms;
	}
	
	/**
	 * 返回指定日期在当前星期的周几。
	 * 
	 * @param date
	 *            指定的日期
	 * @return 返回（１－７）之间的一个整数
	 */
	public static int getWeekOfDay(Date date) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.CHINA);
		dateFormat.format(date);
		Calendar cal = dateFormat.getCalendar();
		int DAY_OF_WEEK = cal.get(Calendar.DAY_OF_WEEK);

		return DAY_OF_WEEK;
	}
	
	/**
	 * 获得当前年
	 * <br>
	 * 默认 yyyy
	 * 
	 * <br>
	 * cmh > Nov 12, 2012 11:12:38 AM
	 * @return
	 */
	public static String getNowYear() {
		return getNowYear("yyyy");
	}
	
	/**
	 * 获得当前年
	 * 
	 * <br>
	 * cmh > Nov 12, 2012 11:12:59 AM
	 * @param format 格式化
	 * @return
	 */
	public static String getNowYear(String format) {
		return format(new Date(), format);
	}
	
	/**
	 * 获得年龄
	 * 
	 * <br>
	 * @author cmh
	 * @version 2014-1-15 下午4:40:01
	 * @param birthday 出生日期，例如：1988-09-06
	 * @return 例如：25岁4个月又9天
	 */
	public static String getAge(String birthday) {
		String age = "";
		int day = 0;
		int y = 0;
		int m = 0;
		int d = 0;

		if (birthday != null && birthday.length() == 10) {
			String[] time = birthday.split("-");
			y = Integer.parseInt(time[0]);
			m = Integer.parseInt(time[1]);
			d = Integer.parseInt(time[2]);

			Calendar selectDate = Calendar.getInstance();
			Calendar currentDate = Calendar.getInstance();
			selectDate.set(Calendar.YEAR, y);
			selectDate.set(Calendar.MONTH, m - 1);
			selectDate.set(Calendar.DAY_OF_MONTH, d);
			// 上一个月
			int lastMonth = (currentDate.get(Calendar.MONTH) + 1) - 1;
			int years = currentDate.get(Calendar.YEAR) - selectDate.get(Calendar.YEAR);
			int months = currentDate.get(Calendar.MONTH) - selectDate.get(Calendar.MONTH);
			int days = currentDate.get(Calendar.DAY_OF_MONTH) - selectDate.get(Calendar.DAY_OF_MONTH);
			if (days < 0) {
				months = months - 1;
				switch (lastMonth) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					day = 31;
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					day = 30;
					break;
				default:
					if (Calendar.YEAR % 4 == 0 && Calendar.YEAR % 100 != 0 || Calendar.YEAR % 400 == 0) {
						day = 28;
					} else {
						day = 29;
					}
					break;
				}
				days = days + day;
			}
			if (months < 0) {
				years = years - 1;
				months = months + 12;
			}
			if (years < 0) {
				years = 0;
			}
			if (years == 0) {
				if (months == 0) {
					if (days == 0) {
						age = "今天是宝宝的出生日期";
					} else {
						age = days + "天";
					}
				} else {
					if (days == 0) {
						age = months + "个月";
					} else {
						age = months + "个月又" + days + "天";
					}
				}
			} else {
				if (months == 0) {
					if (days == 0) {
						age = years + "岁";
					} else {
						age = years + "岁又" + days + "天";
					}
				} else {
					if (days == 0) {
						age = years + "岁" + months + "个月";
					} else {
						age = years + "岁" + months + "个月又" + days + "天";
					}

				}
			}

		}
		return age;
	}
	/**
	 * 在当前日期基础上 加减月份
	 * @param num  如加1个月 num=1 减一个月 num=-1
	 * @param format 输出格式 如"yyyy-MM-dd"
	 * @return
	 */
	public static String addOrSubtractMonth(int num,String format){
		Date date = new Date();//当前日期
		SimpleDateFormat sdf = new SimpleDateFormat(format);//格式化对象  
		Calendar calendar = Calendar.getInstance();//日历对象  
		calendar.setTime(date);//设置当前日期  
		calendar.add(Calendar.MONTH, num);//月份加减
		return sdf.format(calendar.getTime());
	}
}
