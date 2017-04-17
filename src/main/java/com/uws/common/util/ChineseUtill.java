package com.uws.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.uws.core.util.DataUtil;

/**
 * 中文乱码处理工具类
 */
public class ChineseUtill {

	/**
	 * 判断当前字符是否为中文
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前字符串是否为乱码
	 * @param strName
	 * @return
	 */
	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = 0;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
				chLength++;
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 转换乱码为中文
	 * @param tempMsg
	 * @return
	 */
	public static String toChinese(Object tempMsg) {
		if (DataUtil.isNotNull(tempMsg) && isMessyCode(tempMsg.toString())) {
			try {
				return new String(tempMsg.toString().getBytes("ISO8859-1"), "UTF-8");
			} catch (Exception e) {
			}
		}
		return tempMsg.toString();
	}
	
	public static void main(String[] args) {
		ChineseUtill cu = new ChineseUtill();
		System.out.println(cu.toChinese("哈哈"));
		System.out.println(cu.toChinese("å¯¹æ ¡å­å°è±¡å¦ä½"));
	}
}
