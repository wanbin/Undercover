package http;

import org.json.JSONObject;

import com.example.undercover.ConstantControl;
import com.example.undercover.httpCallBack;

public class UserHandler extends BaseHttpCommand {


	public UserHandler(httpCallBack messagecall) {
		super(messagecall);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 取得所有新闻
	 */
	public void getUserInfo(String uid) {
		JSONObject obj = new JSONObject();
		try {
//			obj.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.GET_USER_INFO);
	}

	
	public void joinRoom(int roomid,String uid) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("roomid", roomid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.ROOM_JOIN);
	}
	public void createRoom(String uid) {
		JSONObject obj = new JSONObject();
		try {
//			obj.put("roomid", roomid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.ROOM_CREATE);
	}
	
}
