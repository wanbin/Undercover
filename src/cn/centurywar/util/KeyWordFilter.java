package cn.centurywar.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyWordFilter {
	private static Pattern pattern = null;

	// 从words.properties初始化正则表达式字符串
	private static void initPattern() {
		StringBuffer patternBuf = new StringBuffer("");
		try {
			InputStream in = KeyWordFilter.class.getClassLoader()
					.getResourceAsStream("words.properties");
			Properties pro = new Properties();
			pro.load(in);
			Enumeration enu = pro.propertyNames();
			patternBuf.append("(");
			while (enu.hasMoreElements()) {
				patternBuf.append((String) enu.nextElement() + "|");
			}
			patternBuf.deleteCharAt(patternBuf.length() - 1);
			patternBuf.append(")");

			// unix换成UTF-8
			// pattern = Pattern.compile(new
			// String(patternBuf.toString().getBytes("ISO-8859-1"), "UTF-8"));
			// win下换成gb2312
			pattern = Pattern.compile(new String(patternBuf.toString()
					.getBytes("ISO-8859-1"), "gb2312"));
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
	}

	private static String doFilter(String str) {
		Matcher m = pattern.matcher(str);
		str = m.replaceAll("");
		return str;
	}

	public static void main(String[] args) {
		String str = "国敏感词一院学位办就敏感词三的报道表示敏感词二";
		System.out.println("str:" + str);
		initPattern();
		System.out.println("共" + str.length() + "个字符，查到"
				+ KeyWordFilter.doFilter(str));
	}

	/**
	 * 基本的检查敏感词
	 * @param content
	 * @return
	 */
	public static boolean chackContinue(String content) {
		String[] array = { ",", "_", "\"" };
		for (int i = 0; i < array.length; i++) {
			if (content.indexOf(array[i])>=0) {
				return true;
			}
		}
		return false;
	}
}