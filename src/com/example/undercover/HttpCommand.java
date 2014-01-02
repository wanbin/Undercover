package com.example.undercover;

import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpCommand {

	public static int gameuid=0;
	// protected String serverUrl =
	// "http://42.121.123.185/CenturyServer/Entry.php";
	protected static String serverUrl = "http://192.168.1.31/Entry.php";



	/**
	 * 从服务器取数据
	 */
	public static void getHttpRequest(JSONObject obj,String cmd,final httpCallBack messagecall) {
		RequestParams param = new RequestParams();
		param.put("cmd", cmd);
		JSONObject sign = new JSONObject();
		try {
			sign.put("gameuid", gameuid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("data", obj.toString());
		param.put("sign", sign.toString());
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(serverUrl, param, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
//				Toast.makeText(BaseActivity.this,response, Toast.LENGTH_LONG).show();
//				System.out.println(response);
				try {
					JSONObject obj = new JSONObject(response);
					String cmd=obj.getString("cmd");
					messagecall.MessageCallBack(obj, cmd);
//					MessageCallBack(obj,cmd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
