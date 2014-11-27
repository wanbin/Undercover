package http;

import org.json.JSONObject;

import cn.centurywar.undercover.httpCallBack;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BaseHttpCommand {

//	protected String serverUrl = "http://api.centurywar.cn/Entry.php";
	protected static String serverUrl = "http://192.168.1.120/Entry.php";
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
		System.out.println(param.toString());
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
//		AsyncImageLoader il = new AsyncImageLoader();
//		ImageView indexcover = (ImageView) findViewById(R.id.indexcover);
//		il.loadDrawable("http://ticketfinder.sinaapp.com/custom/2wm.png",indexcover);

	}
}
