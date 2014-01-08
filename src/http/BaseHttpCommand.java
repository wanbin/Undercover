package http;

import org.json.JSONObject;

import com.example.undercover.httpCallBack;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BaseHttpCommand {

//	protected String serverUrl = "http://42.121.123.185/CenturyServer/Entry.php";
	protected static String serverUrl = "http://192.168.1.31/Entry.php";
	 protected httpCallBack mc=null;
	 protected String uid="";

	public BaseHttpCommand(final httpCallBack messagecall) {
		this.mc = messagecall;
	}
	
	public void setUid(String uid){
		this.uid=uid;
	}

	/**
	 * 从服务器取数据
	 */
	public void getHttpRequest(JSONObject obj, String cmd) {
		RequestParams param = new RequestParams();
		param.put("cmd", cmd);
		JSONObject sign = new JSONObject();
		try {
			sign.put("uid", mc != null ? mc.getUid() : this.uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		param.put("data", obj.toString());
		param.put("sign", sign.toString());
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(serverUrl, param, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {
					System.out.println(response);
					JSONObject obj = new JSONObject(response);
					String cmd = obj.getString("cmd");
					if (mc != null) {
						mc.MessageCallBack(obj, cmd);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
