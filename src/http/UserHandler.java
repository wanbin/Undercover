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
	
	
	/**
	 * 返回创建房间的信息
	 */
	public void getRoomInfo() {
		JSONObject obj = new JSONObject();
		try {
//			obj.put("roomid", roomid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.ROOM_GET_INFO);
	}
	
	
	/**
	 * 返回加入房间的信息
	 */
	public void getRoomContent() {
		JSONObject obj = new JSONObject();
		try {
//			obj.put("roomid", roomid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.ROOM_GET_INFOCONTENT);
	}
	
	/**
	 * 返回加入房间的信息
	 */
	public void RoomStartGame(int type,int addPeople) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("type", type);
			obj.put("addPeople", addPeople);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.ROOM_START_GAME);
	}
	
	//RoomGetInfo
	public void RoomPunish(String gameuidstr) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("gameuidstr", gameuidstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.ROOM_PUNISH);
	}
	public void MailSend(String content,int gameuid) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("content", content);
			obj.put("sendto", gameuid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.MAIL_SEND);
	}
	
	public void RoomGetContent() {
		JSONObject obj = new JSONObject();
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.ROOM_GET_CONTENT);
	}
	
}
