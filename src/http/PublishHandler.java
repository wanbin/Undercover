package http;

import org.json.JSONObject;

import com.example.undercover.ConstantControl;
import com.example.undercover.httpCallBack;

public class PublishHandler extends BaseHttpCommand {


	public PublishHandler(httpCallBack messagecall) {
		super(messagecall);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 发布新闻
	 */
	public void sendPublish(String content, int type) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("content", content);
			obj.put("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.SEND_PUBLISH_ALL);
	}

	/**
	 * 发布新闻
	 */
	public void addCollect(int id, int type) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.SEND_PUBLISH_COLLECT);
	}

	/**
	 * 取得所有新闻
	 */
	public void getAllPublish(int page) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.SHOW_PUBLISH_ALL);
	}

}
