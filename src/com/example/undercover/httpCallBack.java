package com.example.undercover;

import org.json.JSONObject;

public interface httpCallBack {
	public String getUid();
	public void MessageCallBack(JSONObject obj,String cmd);
}
