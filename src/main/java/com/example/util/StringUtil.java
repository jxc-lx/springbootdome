package com.example.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipInputStream;


/**
 * 字符串工具类
 * 
 * @author cmh > Nov 7, 2012 5:58:15 PM
 * 
 */
@SuppressWarnings("all")
public class StringUtil {
	private final static Log log = LogFactory.getLog(StringUtil.class);

	// ~ Static fields/initializers
	// =============================================
	private static final int HEIGHT_SPACE = 20;// 图片之间的间隔
	public static String spliteStr0 = ":";
	public static String spliteStr1 = "_";
	public static String spliteStr2 = ",";
	public static String spliteStr3 = " ";
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;
	private static MessageDigest digest = null;
	private final static int ENCODE_XORMASK = 0x5A;
	private final static char ENCODE_DELIMETER = '\002';
	private final static char ENCODE_CHAR_OFFSET1 = 'A';
	private final static char ENCODE_CHAR_OFFSET2 = 'h';

	// ~ Methods
	// ================================================================

	/**
	 * 获得两个数的百分比
	 * 
	 * <br>
	 * @author cmh
	 * @version 2013-8-27 下午5:36:54
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String getPercentStr(float num1, float num2) {
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		String result = numberFormat.format(num1 / num2 * 100) + "%";
		return result;
	}
	
	/**
	 * 把Stream转换成字符串
	 * 
	 * <br>
	 * 
	 * @author cmh
	 * @version 2013-8-14 下午1:12:12
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 把空的字符串转换为字符串"0"
	 * 
	 * <br>
	 * @author cmh
	 * @version 2013-7-31 下午6:22:51
	 * @param s
	 * @return
	 */
	public static String null2String0(String s) {
		if (isEmpty(s))
			s = "0";
		return s;
	}
	
	/**
	 * 判断字符串是否为Null或空
	 * 
	 * @author cmh > Apr 25, 2012 11:42:32 AM
	 * @param str 需要判断的字符串
	 * @return 为空返回 ture ； 不为空返回 false；
	 */
	public static boolean isEmpty(String str) {
		boolean flag = true;
		if (null != str && !"".equals(str)) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 首字母转小写
	 * 
	 * <br>
	 * 
	 * @author cmh
	 * @version 2013-4-19 下午1:31:53
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 * 
	 * <br>
	 * 
	 * @author cmh
	 * @version 2013-4-19 下午1:31:59
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			log.error("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * 
	 * 
	 * @param var
	 * 
	 * @return
	 */
	public static String convertToGBK(String var) {
		try {
			return new String(var.getBytes(), "GBK");
		} catch (Exception e) {
			return var;
		}
	}

	/**
	 * 
	 * 
	 * @param var
	 * 
	 * @return
	 */
	public static String[] convertToGBK(String[] var) {
		String[] ss = new String[var.length];
		try {
			for (int i = 0; i < var.length; i++) {
				ss[i] = new String(var[i].getBytes(), "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			return var;
		}
		return ss;
	}

	/**
	 * 
	 * 
	 * @param username
	 *            The username.
	 * @param password
	 *            The password.
	 * @return String
	 */
	public static String encodePasswordCookie(String memberid, String username, String password) {

		StringBuffer buf = new StringBuffer();

		if (memberid != null && username != null && password != null) {
			byte[] bytes = (memberid + ENCODE_DELIMETER + username + ENCODE_DELIMETER + password).getBytes();
			int b;

			for (int n = 0; n < bytes.length; n++) {
				b = bytes[n] ^ (ENCODE_XORMASK + n);
				buf.append((char) (ENCODE_CHAR_OFFSET1 + (b & 0x0F)));
				buf.append((char) (ENCODE_CHAR_OFFSET2 + ((b >> 4) & 0x0F)));
			}
		}

		return buf.toString();
	}

	/**
	 * 
	 * 
	 * @param value
	 * 
	 * @return String[]
	 */
	public static String[] decodePasswordCookie(String cookieVal) {

		if (cookieVal == null || cookieVal.length() <= 0) {
			return null;
		}

		char[] chars = cookieVal.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int b;

		for (int n = 0, m = 0; n < bytes.length; n++) {
			b = chars[m++] - ENCODE_CHAR_OFFSET1;
			b |= (chars[m++] - ENCODE_CHAR_OFFSET2) << 4;
			bytes[n] = (byte) (b ^ (ENCODE_XORMASK + n));
		}

		cookieVal = new String(bytes);

		int pos = cookieVal.indexOf(ENCODE_DELIMETER);
		int pos1 = cookieVal.lastIndexOf(ENCODE_DELIMETER);
		String memberid = (pos < 0) ? "" : cookieVal.substring(0, pos);
		String username = (pos < 0) ? "" : cookieVal.substring(pos + 1, pos1);
		String password = (pos1 < 0) ? "" : cookieVal.substring(pos1 + 1);

		return new String[] { memberid, username, password };
	}

	/**
	 * 
	 * 
	 * @param number
	 * @param upperCaseFlag
	 * @return java.lang.String
	 * @throws Exception
	 */
	public static String numberToLetter(int number, boolean upperCaseFlag) throws Exception {

		// add nine to bring the numbers into the right range (in java, a= 10, z
		// = 35)
		if (number < 1 || number > 26) {
			throw new Exception("The number is out of the proper range (1 to " + "26) to be converted to a letter.");
		}
		int modnumber = number + 9;
		char thechar = Character.forDigit(modnumber, 36);
		if (upperCaseFlag) {
			thechar = Character.toUpperCase(thechar);
		}
		return "" + thechar;
	} /* numberToLetter(int, boolean) */

	/**
	 * 
	 * 
	 * @param i
	 * 
	 * @param len
	 * 
	 * @return
	 */
	public static String formatIntToStr(int i, int len) {
		String s = "";
		String t = "";
		s = String.valueOf(i);

		if (s.length() < len) {
			for (int j = 0; j < (len - s.length()); j++)
				t = t + "0";
		}
		if (i > 0)
			return (t + s);
		else
			return ("-" + t + String.valueOf(i * -1));
	}

	/**
	 * 格式化字符串
	 * 
	 * @param str
	 * 
	 * @param len
	 * 
	 * @param s
	 * 
	 * @return
	 */
	public static String formatStr(String str, int len, String s) {
		String t = "";
		if (s.length() < len) {
			for (int j = 0; j < (len - str.length()); j++)
				t = t + s;
		}
		return (str + t);
	}

	public static String expandStr(String src, int iLen, char supply) {
		if (src == null)
			src = "";
		if (src.length() > iLen)
			return src;

		char content[] = new char[iLen];

		src.getChars(0, src.length(), content, iLen - src.length());
		Arrays.fill(content, 0, iLen - src.length(), supply);

		return new String(content);

	}

	public static String replace(String src, String srcSubStr, String descStr) {

		if ((null == src || "".equals(src)) || (null == srcSubStr || "".equals(srcSubStr)) || descStr == null || descStr.equals(srcSubStr))
			return src;
		StringBuffer buffer = new StringBuffer(src);
		int iParamePos = buffer.toString().indexOf(srcSubStr);
		while (iParamePos >= 0) {
			int iPlaceSignLen = srcSubStr.length();
			buffer.replace(iParamePos, iParamePos + iPlaceSignLen, descStr);
			iParamePos = buffer.toString().indexOf(srcSubStr);
		}
		return buffer.toString();
	}

	public static boolean isNull(Object value) {
		if (value == null) {
			return true;
		} else {
			if (value instanceof String && value.equals("")) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 
	 * 
	 * @param codes
	 * 
	 * @return
	 */
	public static String decode(byte[] codes) {
		if (codes == null || codes.length == 0)
			return "";
		char ch, ch2, ch3;

		byte result[] = new byte[codes.length];
		int i = 0;
		int count = 0;

		// Loop through and decode each char
		while (i < codes.length) {
			ch = (char) (codes[i] & 0xFF);

			switch (ch >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7: // Single byte char, 0xxxxxxx
				i++;
				result[count++] = (byte) (ch & 0xFF);
				break;
			case 12:
			case 13: // Double bytes char, 110xxxxx 10xxxxxx
				i += 2;

				if (i > codes.length)
					return "";

				ch2 = (char) (codes[i - 1] & 0xFF);
				if ((ch2 & 0xC0) != 0x80)
					return "";

				result[count++] = (byte) (((ch & 0x1F) << 6) | (ch2 & 0x3F));
				break;
			case 14: // Triple bytes char, 1110xxxx 10xxxxxx 10xxxxxx
				i += 3;
				if (i > codes.length)
					return "";

				ch2 = (char) (codes[i - 2] & 0xFF);
				ch3 = (char) (codes[i - 1] & 0xFF);
				if (((ch2 & 0xC0) != 0x80) || ((ch3 & 0xC0) != 0x80))
					return "";
				count++;
				result[count++] = (byte) (((ch & 0x0F) << 12) | ((ch2 & 0x3F) << 6) | ((ch3 & 0x3F) << 0));
				break;
			default: // Unsupported, 10xxxxxx 1111xxxx
				return "";
			}
		}
		String temp = "";
		try {
			temp = new String(trim(result, count), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 
	 * 
	 * @param string
	 * 
	 * @return
	 */
	public static String encode(String string) {
		// Assert parameter
		if (string == null) {
			// Nothing to encode
			return "";
		}
		char ch;
		int count = 0;
		char[] codes = string.toCharArray();
		byte result[] = new byte[codes.length * 3];

		// Loop through string and encode each char
		for (int i = 0; i < codes.length; i++) {
			ch = codes[i];

			// Single byte in range 0x0001 - 0x007F
			if ((ch >= 0x0001) && (ch <= 0x007F)) {
				result[count++] = (byte) ch;
			}
			// Triple bytes above 0x07FF (should never occur, ASCII 0x00 - 0xFF)
			else if (ch > 0x07FF) {
				result[count++] = (byte) (((ch >> 12) & 0x0F) | 0xE0);
				result[count++] = (byte) (((ch >> 6) & 0x3F) | 0x80);
				result[count++] = (byte) (((ch >> 0) & 0x3F) | 0x80);
			}
			// Double bytes for 0x0000 and in range 0x0080 - 0x07FF
			else {
				result[count++] = (byte) (((ch >> 6) & 0x1F) | 0xC0);
				result[count++] = (byte) (((ch >> 0) & 0x3F) | 0x80);
			}
		}
		return new String(trim(result, count));
	}

	private static byte[] trim(byte[] codes, int length) {
		byte temp[] = new byte[length];
		for (int i = 0; i < length; i++) {
			temp[i] = codes[i];
		}
		return temp;
	}

	public static boolean isUTF8(byte[] data) {
		int count_good_utf = 0;
		int count_bad_utf = 0;
		byte current_byte = 0x00;
		byte previous_byte = 0x00;
		for (int i = 1; i < data.length; i++) {
			current_byte = data[i];
			previous_byte = data[i - 1];
			if ((current_byte & 0xC0) == 0x80) {// 10xxxxxx
				if ((previous_byte & 0xC0) == 0xC0) {// 11xxxxxx
					count_good_utf++;
				} else if ((previous_byte & 0x80) == 0x00) {// 0xxxxxxx
					count_bad_utf++;
				}
			} else if ((previous_byte & 0xC0) == 0xC0) {// 11xxxxxx
				count_bad_utf++;
			}
		}
		if (count_good_utf > count_bad_utf) {
			return true;
		} else {
			return false;
		}
	}

	public static String ObjToString(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	public static String getSubString(String str, int length) {
		if (str == null)
			return "";
		if (str.length() < length) {
			return str;
		}
		return str.substring(0, length) + "...";
	}

	public static String IntToStr(Integer value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

	/**
	 * 
	 * 
	 * @param String
	 *            value
	 * @return java.lang.String[]
	 */
	public static String[] getCheckBoxValues(String value) {
		String[] str = null;
		try {
			str = value.split(":");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 
	 * 
	 * @param String
	 *            value
	 * @param String
	 *            separator
	 * @return java.lang.String[]
	 */
	public static String[] splitString(String value, String separator) {
		String[] str = null;
		try {
			str = value.split(separator);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 
	 * cmh > Nov 7, 2012 6:02:05 PM
	 * 
	 * @param str
	 * @param values
	 * @return
	 */
	public static String strArrayToStr(String str, String[] values) {
		String string = "";
		if (str == null)
			str = "";
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				if (values[i] == null)
					values[i] = "";
				string = string.concat(values[i]).concat(str);
			}
			if (string.length() > 0)
				string = string.substring(0, string.length() - 1);
		}
		return string;
	}

	/**
	 * 
	 * 
	 * @param value
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static String subString(String value, int beginIndex, int endIndex) {
		if (!isNull(value) && endIndex >= beginIndex) {
			if (value.length() >= endIndex + 1) {
				return value.substring(beginIndex, endIndex);
			} else if (value.length() >= beginIndex + 1) {
				return value.substring(beginIndex);
			} else {
				return value;
			}
		} else {
			log.error("value is null or beginIndex > endIndex");
			return value;
		}

	}

	// 判断字符串是否为空
	public static boolean strIsEmpty(String str) {
		if (str == null)
			return true;
		str = str.trim();
		if ("".equals(str))
			return true;
		else
			return false;
	}

	public static boolean strNotEmpty(String str) {
		return !strIsEmpty(str);
	}

	public static String strFormat(Object str) {
		if (str != null) {
			return str.toString();
		} else {
			return "";
		}
	}

	/**
	 * 将形如"a,b,c,d"的字符串转换成形如"'a','b','c','d'"的字符串
	 * 
	 * @return
	 */
	public static String convertStringsToSqlStrings(String str) {
		if (str != null && !"".equals(str)) {
			if (str.indexOf(",") != -1) {
				str = str.replaceAll(",", "','");
			}
			str = "'" + str + "'";
			return str;
		} else {
			return "";
		}
	}

	/**
	 * 根据指定的字符把源字符串分割成一个数组
	 * 
	 * @author cmh > Oct 13, 2012 4:26:23 PM
	 * @param pattern
	 *            指定的字符
	 * @param src
	 *            源字符串
	 * @return
	 */
	public static List<String> parseString2ListByCustomerPattern(String pattern, String src) {
		if (src == null)
			return null;
		List<String> list = new ArrayList<String>();
		String[] result = src.split(pattern);
		for (int i = 0; i < result.length; i++) {
			list.add(result[i]);
		}
		return list;
	}

	public static String getUTF8Str(String s1) {
		String s = "";
		if (s1 != null && !"".equals(s1)) {
			try {
				s = java.net.URLEncoder.encode(s1, "UTF-8");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * URL解码
	 * 
	 * @param p
	 * @return
	 */
	public static String decondeRequestPara(String p) {

		if (p != null && !"".equals(p)) {
			try {
				p = java.net.URLDecoder.decode(p, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			p = "";
		}
		return p;
	}

	public static String delBlank(String s) {
		if (s != null && !"".equals(s)) {
			s = s.replaceAll(" ", "");
		}
		return s;
	}

	public static String initStr(String s) {
		String tempStr = "";
		if (s == null) {
			s = tempStr;
		}
		return s;
	}

	

	/*
	 * public static String fullGroupByP(String group,String business){ //05
	 * 2011 05 0010PA String queryGroup = ""; Calendar c =
	 * Calendar.getInstance(); String year = c.get(Calendar.YEAR)+"";//获取年份
	 * String month = c.get(Calendar.MONTH)+"";//获取月份 if(group.length()==14){
	 * return group; }else if(group.length()==4){ queryGroup =
	 * business+year+month+"00"+group.trim().toString(); return queryGroup; } }
	 */
	public static String fillGroupQueryNew(String group, String corpId) {
		group = group.toUpperCase();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDateValue = sdf.format(c.getTime());
		String currDate[] = currDateValue.split("-");
		if (group != null && group.length() < 14) {
			if (group.length() <= 6) {
				String tmp = "000000";
				group = tmp.substring(0, 6 - group.length()) + group;
				group = corpId + currDate[0] + currDate[1] + group;
			} else {
				if (group.length() == 8) {
					group = corpId + currDate[0] + group;
				}
			}
		}
		return group;
	}

	/**
	 * PC号补全(外网)
	 * 
	 * @param group
	 * @param business
	 * @return
	 */
	public static String fillGroupQueryPCWw(String group, String business) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD);
		String currDate = null;//
		String tmpGroup = null;//
		currDate = sdf.format(c.getTime()).toString();
		if (group.length() == 11) {//
			return group;
		} else if (group.length() == 14) {
			return group;
		} else if (group.length() != 12) {
			if (group.length() == 0) {
				return group;
			}

			if (-1 == group.indexOf("PC") && -1 == group.indexOf("pc")) {
				if (-1 != group.indexOf("pC") || -1 != group.indexOf("Pc")) {
					group = group.substring(0, group.length() - 2);
				}
			} else {
				if (-1 != group.indexOf("PC") || -1 != group.indexOf("pc")) {
					group = group.substring(0, group.length() - 2);
				}
			}
			if (group.length() <= 4) {
				String tmp = String.valueOf(group);
				String tmp1 = "0000";
				tmp = tmp1.substring(0, 4 - tmp.length()) + tmp;//
				currDate = currDate.replace("-", "").substring(0, 6);
				tmpGroup = currDate + tmp;
			}
			if (group.length() == 5) {
				String tmp = String.valueOf(group);
				String tmp1 = "0";
				tmp = tmp1 + tmp;//
				currDate = currDate.replace("-", "").substring(0, 4);
				tmpGroup = currDate + tmp;
			}

			if (group.length() == 8) {
				group = group.substring(2, group.length());
			}

			if (group.length() == 7) {
				group = group.substring(1, group.length());
			}

			if (group.length() == 6) {
				String tmp = String.valueOf(group);
				currDate = currDate.replace("-", "").substring(0, 4);
				tmpGroup = currDate + tmp;
			}
			if (group.length() == 10) {
				String tmp = String.valueOf(group);
				tmpGroup = tmp;
			}
			group = business + tmpGroup + "PC";

		}
		return group;
	}

	public static String fillGroupQuery(String group, String business) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD);
		String currDate = null;//
		String tmpGroup = null;//
		currDate = sdf.format(c.getTime()).toString();
		if (group.length() == 11) {//
			return group;
		} else if (group.length() == 14) {
			return group;
		} else if (group.length() != 12) {
			if (group.length() == 0) {
				return group;
			}
			if (group.length() <= 4) {
				String tmp = String.valueOf(group);
				String tmp1 = "0000";
				tmp = tmp1.substring(0, 4 - tmp.length()) + tmp;//
				currDate = currDate.replace("-", "").substring(0, 6);
				tmpGroup = currDate + tmp;
			}
			if (group.length() == 5) {
				String tmp = String.valueOf(group);
				String tmp1 = "0";
				tmp = tmp1 + tmp;//
				currDate = currDate.replace("-", "").substring(0, 4);
				tmpGroup = currDate + tmp;
			}
			if (group.length() == 6) {
				String tmp = String.valueOf(group);
				currDate = currDate.replace("-", "").substring(0, 4);
				tmpGroup = currDate + tmp;
			}
			if (group.length() == 10) {
				String tmp = String.valueOf(group);
				tmpGroup = tmp;
			}
			group = business + tmpGroup + "PB";
		}
		return group;
	}

	//
	public static String fillDocNum(String doc, String corpNum) {
		Calendar c = Calendar.getInstance();
		// String corpNum = "350000000";
		// 350000000 1 2011 03 00019
		// 生成审批档案编号 档案编号生成规：
		// 创建单位编号+4位年份（例如：2010）+四位流水号。
		// 例如：440000000020100100'
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD);
		String currDate = null;
		String tmpGroup = null;
		currDate = sdf.format(c.getTime()).toString();
		if (doc.length() > 8) {
			return doc;
		} else if (doc.length() == 0) {
			return doc;
		} else if (doc.length() <= 8) {
			if (doc.length() == 0) {
				return doc;
			}
			// 3500000001201106000111
			if (doc.length() <= 4) {
				String tmp = String.valueOf(doc);
				String tmp1 = "0000";
				tmp = tmp1.substring(0, 4 - tmp.length()) + tmp;
				currDate = currDate.replace("-", "").substring(0, 4);
				tmpGroup = currDate + tmp;
			}
			if (doc.length() == 5) {
				String tmp = String.valueOf(doc);
				currDate = currDate.replace("-", "").substring(0, 3);
				tmpGroup = currDate + tmp;
			}
			if (doc.length() == 6) {
				String tmp = String.valueOf(doc);
				currDate = currDate.replace("-", "").substring(0, 2);
				tmpGroup = currDate + tmp;
			}
			if (doc.length() == 7) {
				String tmp = String.valueOf(doc);
				// 201103
				currDate = currDate.replace("-", "").substring(0, 1);
				tmpGroup = tmp;
			}
			doc = corpNum + tmpGroup;
		}
		return doc;
	}

	/**
	 * 档案号补全
	 * 
	 * @param corpid
	 * @param inputStr
	 * @return
	 */
	public static String fillDocNumBj(String inputStr, String epSysInfoCorpCode) {
		String rtnStr = "";

		if (inputStr.length() <= 5) {
			StringBuffer sb = new StringBuffer();
			String tmp1 = "00000";
			String lastStr = tmp1.substring(0, 5 - inputStr.length()) + inputStr;
			String currDate[] = DateUtil.getCurrDateArray();

			sb.append(epSysInfoCorpCode);
			sb.append("11");
			sb.append(currDate[0] + currDate[1]);
			sb.append(lastStr);
			rtnStr = sb.toString();
		} else if (inputStr.length() == 8) {
			rtnStr = epSysInfoCorpCode + "11" + inputStr;
		} else {
			rtnStr = inputStr;
		}
		return rtnStr;
	}

	// 团组号补位查询
	public static String fillGroupQuery(String group) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD);
		String currDate = null;// 系统当前日期
		String tmpGroup = null;// 临时团组号
		String business = "PB";
		currDate = sdf.format(c.getTime()).toString();
		if (group.length() == 11) {// 历史数据
			return group;
		} else if (group.length() != 12) {
			if (group.length() == 0) {
				return group;
			}
			if (group.length() <= 4) {
				String tmp = String.valueOf(group);
				String tmp1 = "0000";
				tmp = tmp1.substring(0, 4 - tmp.length()) + tmp;// 后四位流水号
				currDate = currDate.replace("-", "").substring(0, 6);
				tmpGroup = currDate + tmp;
			}
			if (group.length() == 5) {
				String tmp = String.valueOf(group);
				String tmp1 = "0";
				tmp = tmp1 + tmp;// 后四位流水号
				currDate = currDate.replace("-", "").substring(0, 4);
				tmpGroup = currDate + tmp;
			}
			if (group.length() == 6) {
				String tmp = String.valueOf(group);
				currDate = currDate.replace("-", "").substring(0, 4);
				tmpGroup = currDate + tmp;
			}
			if (group.length() == 10) {
				String tmp = String.valueOf(group);
				tmpGroup = tmp;
			}
			group = business + tmpGroup;
		}
		return group;
	}

	public static String fillVisaId(String visaId) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String nowDate = format.format(date);
		if (null != visaId) {
			if (visaId.length() == 1) {
				visaId = nowDate + "000" + visaId;
			}
		}
		if (null != visaId) {
			if (visaId.length() == 2) {
				visaId = nowDate + "00" + visaId;
			}
		}
		if (null != visaId) {
			if (visaId.length() == 3) {
				visaId = nowDate + "0" + visaId;
			}
		}
		if (null != visaId) {
			if (visaId.length() == 4) {
				visaId = nowDate + visaId;
			}
		}
		return visaId;
	}

	public static String seqFormat(String seqNo) {
		String tmp = String.valueOf(seqNo);
		String tmp1 = "0000";
		tmp = tmp1.substring(0, 4 - tmp.length()) + tmp;// 鍚庡洓浣嶆祦姘村彿
		return tmp;
	}

	public static String strToDXDS(String str) {
		if (str != null) {
			str = str.trim().replace('1', '一').replace('2', '二').replace('3', '三').replace('4', '四').replace('5', '五').replace('6', '六').replace('7', '七').replace('8', '八').replace('9', '九')
					.replace('0', 'O');
			if (str.length() > 1) {
				if (str.substring(0, 1).equals("一")) {
					str = "十" + str.substring(1, 2);
				} else {
					str = str.substring(0, 1) + "十" + str.substring(1, 2);
				}
				if (str.substring(str.length() - 1, str.length()).equals("O")) {
					str = str.substring(0, str.length() - 1);
				}
			}
		}
		return str;
	}

	public static String strToDX(String str) {
		if (str != null) {
			str = str.trim().replace('1', '一').replace('2', '二').replace('3', '三').replace('4', '四').replace('5', '五').replace('6', '六').replace('7', '七').replace('8', '八').replace('9', '九')
					.replace('0', '〇');
		}

		return str;
	}

	public static String strToDateDX(String str) {
		String date = str;
		if (str != null) {
			if (str.length() > 9) {
				str = strToDX(date.substring(0, 4));
				str = str + date.substring(4, 5).replace("-", "年");
				if (date.substring(5, 6).equals("0")) {
					str = str + strToDX(date.substring(6, 7));
				} else {
					str = str + strToDX(date.substring(5, 7));
				}
				str = str + date.substring(7, 8).replace("-", "月");
				if (date.substring(8, 9).equals("0")) {
					str = str + strToDX(date.substring(9, 10));
				} else {
					str = str + strToDX(date.substring(8, 10));
					if (str.length() > 9) {
						String str1 = date.substring(8, 9);
						if (date.substring(8, 9).equals("1")) {
							str = str.substring(0, 8) + "十" + str.substring(9, 10);
						} else {
							str = str.substring(0, 9) + "十" + str.substring(9, 10);
						}
					} else {
						if (date.substring(8, 9).equals("1")) {
							str = str.substring(0, 7) + "十";
						} else {
							str = str.substring(0, 8) + "十" + str.substring(8, 9);
						}
					}
				}
				str = str + "日";
			}

		}
		return str;
	}

	public static List splitPassPort(String s) {
		s = decondeRequestPara(s);
		String[] passPortInfo = s.split("&");
		List<String[]> list = new ArrayList<String[]>();
		if (passPortInfo != null) {
			if (passPortInfo.length > 0) {
				for (int i = 0; i < passPortInfo.length; i++) {
					String[] passPort = passPortInfo[i].split(",");
					if (passPort.length > 0) {
						String[] port = new String[passPort.length];
						for (int j = 0; j < passPort.length; j++) {
							if (passPort[j] != null) {
								passPort[j] = passPort[j].trim();
							}
							port[j] = passPort[j];
						}
						list.add(passPort);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 空字符串转换
	 * 
	 * @param input
	 * @return
	 */
	public static String ObjectToString(Object inputObj) {
		String rtnStr = "";
		if (null != inputObj) {
			String nowStr = inputObj.toString();
			if (nowStr != null && !"null".equals(nowStr) && !"NULL".equals(nowStr)) {
				rtnStr = nowStr;
			}
		}
		return rtnStr;
	}

	public static String strToDXSZ(String str) {
		if (str.equals("0")) {
			str = "零";
		}
		if (str.equals("1")) {
			str = "壹";
		}
		if (str.equals("2")) {
			str = "贰";
		}
		if (str.equals("3")) {
			str = "叁";
		}
		if (str.equals("4")) {
			str = "肆";
		}
		if (str.equals("5")) {
			str = "伍";
		}
		if (str.equals("6")) {
			str = "陆";
		}
		if (str.equals("7")) {
			str = "柒";
		}
		if (str.equals("8")) {
			str = "捌";
		}
		if (str.equals("9")) {
			str = "玖";
		}

		return str;
	}

	public static int getTimeCount(String hj) {
		int timeCount = 0;
		if ("021001".equals(hj)) {
			timeCount = 4;
		} else if ("021002".equals(hj)) {
			timeCount = 1;
		} else if ("021003".equals(hj)) {
			timeCount = 0;
		} else {
			timeCount = 2;
		}
		return timeCount;
	}

	/**
	 * 将压缩过的byte数组解压转换成字符串 <br>
	 * 默认GBK
	 * 
	 * @author cmh
	 * @version 2013-1-10 下午4:32:54
	 * @param compressed
	 * @return
	 */
	public static String decompress(byte[] compressed) {
		return decompress(compressed, "GBK");
	}

	/**
	 * 将压缩过的byte数组解压转换成字符串
	 * 
	 * <br>
	 * 
	 * @author cmh
	 * @version 2013-1-10 下午4:32:54
	 * @param compressed
	 * @param encode
	 *            编码
	 * @return
	 */
	public static final String decompress(byte[] compressed, String encode) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			java.util.zip.ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
				out.flush();
			}
			decompressed = new String(out.toByteArray(), encode);
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}

	/**
	 * 拷贝文件
	 * 
	 * @author 王宪良
	 * @param file
	 *            原有文件
	 * @param uploadPath
	 *            拷贝到的文件路径
	 */
	public static void uploadFile(File file, String uploadPath) {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(uploadPath);
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {

					fos.flush();
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

	/**
	 * 字节流，输出为文件
	 * 
	 * @author 王宪良
	 * @param result
	 *            字节数组
	 * @param fileName
	 *            输出文件的位置以及文件名称
	 */
	public static void outFileByByte(byte[] result, String fileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			fos.write(result, 0, result.length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {

					fos.flush();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 根据文件夹名称，得到文件名称字符串数组
	 * 
	 * @author 王宪良
	 * @param fileName
	 * @return
	 */
	public static String[] getStringsByFile(String fileName) {
		String[] fileNameStrings = null;
		if (fileName != null && !"".equals(fileName)) {
			File file = new File(fileName);
			fileNameStrings = file.list();
		}
		return fileNameStrings;
	}

	/**
	 * 利用递归算法，循环删除文件夹内的内容
	 * 
	 * @author 王宪良
	 * @param path
	 *            要删除的文件夹路径
	 */
	public static void deleteAllFile(String path) {
		File file = new File(path);
		if (file.isFile()) {
			file.delete();
		}
		if (file.isDirectory()) {
			String[] tempList = file.list();
			if (tempList.length != 0) {
				File temp = null;
				for (int i = 0; i < tempList.length; i++) {
					if (path.endsWith(File.separator)) {
						temp = new File(path + tempList[i]);
					} else {
						temp = new File(path + File.separator + tempList[i]);
					}
					if (temp.isFile()) {
						temp.delete();
					}
					if (temp.isDirectory()) {
						deleteAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文�件
					}
				}
			}
		}
	}

	public static void createFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static String getVisitPlaceString(String[] groupVisitPlaceString) {
		String visitPlaceStr = "";
		if (groupVisitPlaceString != null && !"".equals(groupVisitPlaceString)) {
			for (int i = 1; i < groupVisitPlaceString.length; i++) {
				String[] visitPlaceStrings = groupVisitPlaceString[i].split(",");
				if (visitPlaceStrings != null && visitPlaceStrings.length >= 3) {
					visitPlaceStr += visitPlaceStrings[2] + ",";
				}
			}
			if (visitPlaceStr.lastIndexOf(",") != -1) {
				visitPlaceStr = visitPlaceStr.substring(0, visitPlaceStr.lastIndexOf(","));
			}
		}
		return visitPlaceStr;
	}

	

	/**
	 * 此方法根据规则，将字符串拆分成字符串数组
	 * 
	 * @author 王宪良
	 * @param country
	 *            国家拼接的字符串 规则：国家,国家,国家
	 * @param regex
	 *            拆分的规则
	 * @return String[]
	 */
	public static String[] getCountry(String country, String regex) {
		String[] countryStrings = null;
		if (country != null) {
			countryStrings = country.split(regex);
		}
		return countryStrings;
	}

	/**
	 * 此函数通过角色返回的字符串，返回角色的数组。
	 * 
	 * @author 王宪良
	 * @日期：2010-11-05
	 * @时间：下午：13:26
	 * @param roleString
	 *            角色字符串 格式：
	 *            "result=0|appcode=110|appUserid=wcgf|username=wcgf|rolenum=10|rolecode=102,104,106,108,110,112,114,116,118,120|rolename=基本权限,团组信息录入,护照配号,护照签署,证件接收,发票重打,护照核查,证件恢复/注销,出库单使用,护照作废查询管理"
	 * @return String[]
	 */
	public static String[] getRoleStringArray(String roleString, int count) {
		String[] userAndRoles = roleString.split("\\|");
		String[] roleNameAndroles = null;
		String[] roles = null;
		if (userAndRoles != null && userAndRoles.length >= 7) {
			roleNameAndroles = userAndRoles[count].split("=");
		}
		if (roleNameAndroles != null && roleNameAndroles.length >= 2) {
			roles = roleNameAndroles[1].split(",");
		}
		return roles;
	}

	public static String getPassPortByByte(String passport[]) {
		String passportId = "";
		if (passport != null && passport.length > 0) {
			for (int i = 0; i < passport.length; i++) {
				passportId += "'" + passport[i] + "',";
			}
			if (passportId.lastIndexOf(",") != -1)
				passportId = passportId.substring(0, passportId.lastIndexOf(","));
		}
		return passportId;
	}

	/**
	 * 补全流水号 <br>
	 * 默认按0补全
	 * 
	 * <br>
	 * cmh > Nov 9, 2012 4:03:15 PM
	 * 
	 * @param seqNo
	 *            流水号
	 * @param count
	 *            总位数
	 * @return
	 */
	public static String seqStringFormat(String seqNo, int count) {
		return seqStringFormat(seqNo, "0", count);
	}

	/**
	 * 补全流水号
	 * 
	 * <br>
	 * cmh > Nov 9, 2012 4:04:24 PM
	 * 
	 * @param seqNo
	 *            流水号
	 * @param flag
	 *            补全标记
	 * @param count
	 *            总位数
	 * @return
	 */
	public static String seqStringFormat(String seqNo, String flag, int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(flag);
		}
		seqNo = sb.substring(0, count - seqNo.length()) + seqNo;
		return seqNo;
	}

	public static String convertToString(Object o) {
		String s = "";
		if (o != null) {
			s = o.toString().trim();
		}
		return s;
	}

	/**
	 * 去掉时间后的多余字符eg:1980-03-04 00:00:00.0 万杰
	 * 
	 * @param sourceStr
	 * @return 1980-03-04
	 */
	public static String convertToDataString(Object o) {
		String s = "";
		if (o != null) {
			s = o.toString().trim();
		}
		String[] datas = s.split(" ");
		if (datas.length > 0) {
			s = datas[0];
		}
		return s;
	}

	public static int convertToInt(Object o) {
		int i = 0;
		try {
			if (o != null && o.toString().trim().length() > 0) {
				i = Integer.parseInt(o.toString().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	

	/**
	 * 目的是为了去掉字符串中的字母
	 * 
	 * @author update bu zyj dongjiaxu
	 * @param str
	 * @return
	 */
	public static int convertToNumber(String str) {
		try {
			char[] strArray = str.toCharArray();
			for (char c : strArray) {
				if (Character.isLetter(c)) {
					str = str.replace(c + "", "");
				}
			}
			return Integer.valueOf(str);
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * 去掉字符串中的数字
	 * 
	 * @author dongjiaxu
	 * @param str
	 * @return
	 */
	public static String convertToStr(String str) {
		try {
			char[] strArray = str.toCharArray();
			for (char c : strArray) {
				if (Character.isDigit(c)) {
					str = str.replace(c + "", "");
				}
			}
			return str;
		} catch (Exception ex) {
			return "";
		}
	}

	public static long convertToLong(Object o) {
		long l = 0L;

		try {
			if (o != null && o.toString().trim().length() > 0) {
				l = Long.parseLong(o.toString().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return l;
	}

	public static double convertToDouble(Object o) {
		double d = 0.00;

		try {
			if (o != null && o.toString().trim().length() > 0) {
				d = Double.parseDouble(String.valueOf(o));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public static Float getPercent(int str1, int str2) {

		Float percent = 0f; // 百分比率
		Float temp = 0f; // 百分率的中间变量

		try {

			if (str2 == 0) {
				throw new Exception("被除数不得为零");
			}

			temp = Float.parseFloat(String.valueOf(str1)) / Float.parseFloat(String.valueOf(str2)) - 1;

			percent = temp * 10000;
			percent = Float.valueOf(Math.round(percent));
			percent = percent / 100;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return percent;

	}

	// 以##.00%形式
	public static String baiFenPercent(int y, int z) {
		String baifenbi = "";// 接受百分比的值
		double baiy = y * 1.00;
		double baiz = z * 1.00;
		double fen = baiy / baiz;
		// NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法
		// nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
		DecimalFormat df1 = new DecimalFormat("##.00%"); // ##.00%
															// 百分比格式，后面不足2位的用0补齐
		baifenbi = df1.format(fen);
		return baifenbi;
	}

	// 将十六进制转换为字符串
	public static String getHexToString(String strValue) {
		int intCounts = strValue.length() / 2;
		String strReturn = "";
		String strHex = "";
		int intHex = 0;
		byte byteData[] = new byte[intCounts];
		try {
			for (int intI = 0; intI < intCounts; intI++) {
				strHex = strValue.substring(0, 2);
				strValue = strValue.substring(2);
				intHex = Integer.parseInt(strHex, 16);
				if (intHex > 128)
					intHex = intHex - 256;
				byteData[intI] = (byte) intHex;
			}
			strReturn = new String(byteData, "GB2312");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strReturn;
	}

	public static byte[] getByteFromFile(File file) {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	

	/*
	 * @author:dongjiaxu @time:2011-6-18 @to:把string convert base64
	 */
	public static String getStringBase64ByString(String string) {
		BASE64Encoder encoder = new BASE64Encoder();
		String stringbase64 = encoder.encode(string.getBytes());
		return stringbase64;
	}

	/**
	 * 用来把字节数组装换成八base64
	 * 
	 * <br>
	 * @author cmh
	 * @version 2013-8-15 下午5:14:29
	 * @param image
	 * @return
	 */
	public static String getStringBase64ByByte(byte[] image) {
		String stringbase64 = "";
		if(null!=image){
			BASE64Encoder encoder = new BASE64Encoder();
			stringbase64 = encoder.encode(image);
		}
		return stringbase64;
	}

	/**
	 * 用来把Blob转换成byte数组
	 * 
	 * <br>
	 * @author cmh
	 * @version 2013-8-15 下午5:14:36
	 * @param blob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static byte[] getByteByBlob(Blob blob) throws SQLException, IOException {
		InputStream input = null;
		ByteArrayOutputStream bos = null;
		byte[] bytes = null;
		if (blob != null) {
			input = blob.getBinaryStream();
			bos = new ByteArrayOutputStream();
			int byteRead = 0;
			byte[] buffer = new byte[8192];

			while ((byteRead = input.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, byteRead);
			}
			bytes = bos.toByteArray();
			input.close();
			bos.close();
		}
		return bytes;
	}

	/**
	 * author dongjiaxu creat time;2011-07-08
	 * 
	 */
	public static byte[] getByteFromImageString(String imageString) {
		byte[] by = null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			by = decoder.decodeBuffer(imageString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return by;
	}

	public static String checkString(String str) {
		if (null == str) {
			str = "";
		}
		return str;
	}

	/**
	 * 字符串是否为NULL，是否为空
	 * 
	 * <br>
	 * 
	 * @author cmh
	 * @version 2013-5-24 上午10:10:11
	 * @param str
	 *            需要校验的字符串
	 * @return 如果不为NULL并且不为空，返回 true ，其它情况返回 false
	 */
	public static boolean isNotNullAndNotBlank(String str) {
		if (null != str && !"".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 校验str是否为空<br>
	 * 如果字符串里面是 null ，也认为是空字符串
	 * 
	 * <br>
	 * @author cmh
	 * @version 2013-7-18 下午1:39:24
	 * @param str
	 * @return  字符串为空返回 false ，否则返回 true
	 */
	public static boolean checkNotNull(String str) {
		if (null != str) {
			if (!"null".equals(str) && !"".equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查字符串是否为Long
	 * 
	 * <br>
	 * @author cmh
	 * @version 2013-7-18 下午1:57:08
	 * @param lon
	 * @return
	 */
	public static Long checkLong(Long lon) {
		if (null == lon) {
			lon = 0L;
		}
		return lon;
	}

	public static String delNull(String str) {
		if (null != str) {
			return str;
		} else {
			return "";
		}
	}

	public static Integer checkInteger(Integer in) {
		if (null != in) {
			return in;
		} else {
			return 0;
		}
	}

	

	public static void testBarcode(String barcode) {
		File file = new File("D:/1234.png");
		FileOutputStream fos = null;
		try {
			// InputStream inputStream=resultSet.getBinaryStream(1);
			fos = new FileOutputStream(file);
			int len = 0;
			byte[] bytes = new byte[1024];
			bytes = new BASE64Decoder().decodeBuffer(barcode);

			// while((len=inputStream.read(bytes))!=-1){
			fos.write(bytes, 0, len);
			// }
			fos.flush();
			System.out.println("Success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static byte[] getByteByByte(byte[] barcodeByte) throws SQLException, IOException {
		InputStream input = null;
		ByteArrayOutputStream bos = null;
		byte[] bytes = null;
		if (barcodeByte.length > 0) {
			input = new ByteArrayInputStream(barcodeByte);
			bos = new ByteArrayOutputStream();
			int byteRead = 0;
			byte[] buffer = new byte[8192];

			while ((byteRead = input.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, byteRead);
			}
			bytes = bos.toByteArray();
			input.close();
			bos.close();
		}
		return bytes;
	}

	public static String addBlankToName(String personName) {
		if (personName != null && personName.length() > 0) {
			personName = personName.trim();
			if (personName.length() == 3) {
				personName = personName.substring(0, 1) + " " + personName.substring(1, 2) + personName.substring(2, 3);
			}
			if (personName.length() == 2) {
				personName = personName.substring(0, 1) + " " + personName.substring(1, 2);
			}
		}
		return personName;
	}

	public static void testBarcodeFromByte(byte[] bytes) {
		File file = new File("D:/1235.png");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			int len = 0;
			fos.write(bytes, 0, len);
			fos.flush();
			System.out.println("Success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	



	public static String checkObjectAndToString(Object str) {
		if (null != str) {
			return str.toString();
		} else {
			return "";
		}
	}


	/**
	 * 格式化字符串列表，使之符合： 'a','b'
	 * 
	 * @param strList
	 * @return
	 */
	public static String getSqlInFormartStr(List<String> strList) {
		StringBuffer tempStr = new StringBuffer();
		for (int i = 0; i < strList.size(); i++) {
			if (i != strList.size() - 1) {
				tempStr.append("'" + strList.get(i) + "',");
			} else {
				tempStr.append("'" + strList.get(i) + "'");
			}
		}
		return tempStr.toString();
	}

	/**
	 * 根据字符串获得符合sql语句 in 需要的查询格式 <br>
	 * 例如：'a','b'
	 * 
	 * <br>
	 * 
	 * @author cmh
	 * @version 2013-4-15 上午10:43:45
	 * @param str
	 *            字符串（例如 ： a,b ）
	 * @return 例如：'a','b'
	 */
	public static String getSqlInFormartStr(String str) {
		return getSqlInFormartStr(str, ",");
	}

	/**
	 * 根据字符串获得符合sql语句 in 需要的查询格式 <br>
	 * 例如：'a','b'
	 * 
	 * <br>
	 * 
	 * @author cmh
	 * @version 2013-4-15 上午10:43:45
	 * @param str
	 *            字符串（例如 ： a,b ）
	 * @param flag
	 *            字符串的分割标记 例如： ,
	 * @return 例如：'a','b'
	 */
	public static String getSqlInFormartStr(String str, String flag) {
		if(checkNotNull(str)){
			String[] tempArr = splitString(str, flag);
			List<String> strList = new ArrayList(Arrays.asList(tempArr));
			return getSqlInFormartStr(strList);
		}
		return "";
	}

	/**
	 * 阿拉伯数字转成中文小写数字
	 * 
	 * @param str
	 *            需要转换的阿拉伯数字(字符串)
	 * @author cmh
	 * @return
	 */
	public static String strNumToChineseNum(String str) {
		if (str != null) {
			str = str.trim().replace('1', '一').replace('2', '二').replace('3', '三').replace('4', '四').replace('5', '五').replace('6', '六').replace('7', '七').replace('8', '八').replace('9', '九')
					.replace('0', '〇');

			if (str.length() == 3) {// 三位数
				if (str.substring(0, 3).equals("〇〇〇")) {
					return "〇";
				}
				if (str.substring(0, 2).equals("〇〇")) {
					return strNumToChineseNum(str.substring(2, 3));
				}
				if (str.substring(0, 1).equals("〇")) {
					return strNumToChineseNum(str.substring(1, 3));
				}
				if (str.substring(1, 3).equals("〇〇")) {
					return str.substring(0, 1) + "百";
				}
				if (str.substring(1, 2).equals("一")) {
					if (str.substring(1, 3).equals("一〇")) {
						str = str.substring(0, 1) + "百一十";
					} else {
						str = str.substring(0, 1) + "百一十" + str.substring(2, 3);
					}
					return str;
				}
				if (str.substring(1, 2).equals("〇")) {
					str = str.substring(0, 1) + "百〇" + str.substring(2, 3);
					return str;
				}
				str = str.substring(0, 1) + "百" + strNumToChineseNum(str.substring(1, 3));
				return str;
			}

			if (str.length() == 2) {// 两位数
				if (str.substring(0, 1).equals("〇")) {
					str = str.substring(1, str.length());
					return strNumToChineseNum(str);
				}
				if (str.substring(0, 1).equals("一")) {
					str = "十" + str.substring(1, 2);
				} else {
					str = str.substring(0, 1) + "十" + str.substring(1, 2);
				}
				if (str.substring(str.length() - 1, str.length()).equals("〇")) {
					str = str.substring(0, str.length() - 1);
				}
				return str;
			}
		}
		return str;
	}

	/**
	 * 通过前后分割符获得补年后的格式数据
	 * 
	 * @param inputFileNo
	 * @param splitStrStart
	 * @param splitStrEnd
	 * @return
	 */
	public static String setYearToFileNo(String inputFileNo, String splitStrStart, String splitStrEnd) {
		String rtnFileNo = "";
		String year = DateUtil.format(new Date(), "yyyy");
		if (null != inputFileNo && !"".equals(inputFileNo) && !"null".equals(inputFileNo)) {

			if (!inputFileNo.contains(splitStrStart) || !inputFileNo.contains(splitStrEnd)) {
				return rtnFileNo;
			}

			if (inputFileNo.contains(splitStrStart)) {
				String tempSpilit = "\\" + splitStrStart;
				String[] tempArray = inputFileNo.split(tempSpilit);
				if (null != tempArray && tempArray.length >= 0) {
					String fistStr = tempArray[0];
					rtnFileNo = fistStr + splitStrStart;
				}

			}
			if (inputFileNo.contains(splitStrEnd)) {
				String tempSpilit = "\\" + splitStrEnd;
				String[] tempArray = inputFileNo.split(tempSpilit);
				if (null != tempArray && tempArray.length >= 2) {
					String lastStr = tempArray[1];
					rtnFileNo = rtnFileNo + year + splitStrEnd + lastStr;

				}
			}

		}
		return rtnFileNo;
	}

	/**
	 * 根据List获得按flag拼接的字符串
	 * 
	 * <br>
	 * cmh > 2012-12-3 下午4:43:09
	 * 
	 * @param dataList
	 *            数据列表
	 * @param flag
	 *            拼接符号
	 * @return
	 */
	public static String getStrByList(List<String> dataList, String flag) {
		StringBuffer sb = new StringBuffer("");
		if (null != dataList) {
			for (int i = 0; i < dataList.size(); i++) {
				String data = checkObjectAndToString(dataList.get(i));
				sb.append(data);
				if (i != dataList.size() - 1) {
					sb.append(flag);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 根据List获得按 , 拼接的字符串
	 * 
	 * <br>
	 * cmh > 2012-12-3 下午4:44:47
	 * 
	 * @param dataList
	 *            数据列表
	 * @return
	 */
	public static String getStrByList(List<String> dataList) {
		return getStrByList(dataList, ",");
	}
}
