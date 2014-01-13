package http;

import org.json.JSONObject;

import com.example.undercover.ConstantControl;
import com.example.undercover.httpCallBack;

public class BehaveHandler extends BaseHttpCommand {


	public BehaveHandler(httpCallBack messagecall) {
		super(messagecall);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 添加用户行为
	 */
	public void addUserBehave(String behave, String data) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("behave", behave);
			obj.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getHttpRequest(obj, ConstantControl.BEHAVE_ADD);
	}

}
