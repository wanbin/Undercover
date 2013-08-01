package com.example.util;

import java.util.ResourceBundle;

public class PunishProps {
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("punish");
	
	public static String get(String str){
		return resourceBundle.getString(str);
	}
	
//	public static void main(String[] args) {
//		System.out.println("*******************************");
//		String str	= PunishProps.get("punish_1");
//		System.out.println(str);
//	}
}
