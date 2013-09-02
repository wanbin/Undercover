package com.example.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {
	/**将字符串中 遇到的 标点符号 空格  都进行换行
	 * @param array
	 * @return
	 */
	public static String fromatString(String str){
		Pattern pattern = Pattern.compile("[,，；;。.\\s]+");
		Matcher  m=pattern.matcher(str);
		return m.replaceAll("\n");
		
	}
}
