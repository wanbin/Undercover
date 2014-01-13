package http;

import org.json.JSONObject;

import com.example.undercover.BaseActivity;
import com.example.undercover.ConstantControl;
import com.example.undercover.httpCallBack;
import com.example.util.KeyWordFilter;

public class PublishHandler extends BaseHttpCommand {

	public PublishHandler(httpCallBack messagecall) {
		super(messagecall);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 发布新闻
	 */
	public boolean sendPublish(String content,String username, int type) {
		JSONObject obj = new JSONObject();
		if (KeyWordFilter.chackContinue(content)) {
			mc.ToastMessage("包含敏感词");
			return false;
		}
		try {
			obj.put("content", content);
			obj.put("type", type);
			obj.put("username", username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.SEND_PUBLISH_ALL);
		return true;
	}

	public boolean checkContent(String content) {
		// if(content.contains(cs))
		return true;
	}

	/**
	 * 收藏、点赞
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
	 * 收藏、点赞
	 */
	public void shenHe(int id, int type) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.PUBLISH_SHENHE);
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

	/**
	 * 取得所有需要审核的新闻
	 */
	public void getAllPublishNeedShenhe(int page) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("page", page);
			obj.put("shenhe", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.SHOW_PUBLISH_ALL);
	}

}
