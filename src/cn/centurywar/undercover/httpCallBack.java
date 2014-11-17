package cn.centurywar.undercover;

import org.json.JSONObject;

public interface httpCallBack {
	public String getUid();
	public void ToastMessage(String message);
	public void MessageCallBack(JSONObject obj,String cmd);
}
