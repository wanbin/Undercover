package cn.centurywar.util;

import java.util.ResourceBundle;

public class PunishProps {
	/** 绕口令的数目 */
	private static int raokouling = 55;
	// 加载punish.properties文件
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("punish");
	// 加载question.properties文件
	private static final ResourceBundle questionBundle = ResourceBundle.getBundle("question");
	
	/**
	 * 获取惩罚条目。
	 * 
	 * @param str
	 * @return
	 */
	public static String getPunish(int str){
		return resourceBundle.getString("punish_"+str);
	}
	
	/**
	 * 获得较为复杂的问题
	 * 
	 * @param str
	 * @return
	 */
	public static String getQestionHard(int str){
		return questionBundle.getString("question_hard_"+str+"");
	}
	
	/**
	 * 获得较为简单的问题
	 * 
	 * @param str
	 * @return
	 */
	public static String getQestionEasy(int str){
		return questionBundle.getString("question_easy_"+str+"");
	}
	
	/**
	 * 获得绕口令
	 * 
	 * @param str
	 * @return
	 */
	public static String getRaoKouLing(int str){
		return questionBundle.getString("raokouling_"+str+"");
	}
	
	/**
	 * 获得一条绕口令
	 * 
	 * @return
	 */
	public static String getRaoKouLing(){
		return getRaoKouLing((int) Math.floor(Math.random() * raokouling));
	}
}
