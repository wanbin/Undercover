package com.example.undercover;


import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class net_home extends BaseActivity {
	Button btnJoin;
	Button btnCreate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_home);
		btnJoin = (Button) this.findViewById(R.id.btnJoin);
		btnJoin.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				joinRoom(10001);
			}
		});
		btnCreate = (Button) this.findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				createRoom();
			}
		});
		checkIsInRoom();
	}
	
	
	/**
	 * 进来的时候，检测是不是已经在房间中，如果在房间中则进行跳转
	 */
	private void checkIsInRoom(){
		String roomType=getFromObject("gametype");
		if(roomType.equals("create")){
			Intent mIntent = new Intent();
			mIntent.setClass(net_home.this, net_room_create.class);
			startActivity(mIntent);
		}else if (roomType.equals("join")){
			
		}
	}
	
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.ROOM_CREATE)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				int roomid=obj.getInt("roomid");
				setToObject("roomid",String.valueOf(roomid));
				setToObject("gametype","create");
				if(roomid>0){
					Intent mIntent = new Intent();
					mIntent.setClass(net_home.this, net_room_create.class);
					startActivity(mIntent);
				}
				else{
					//创建房间出错
					ToastMessage("创建房间出错");
				}
				//显示

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}else if(cmd.equals(ConstantControl.ROOM_JOIN)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				setToObject("gametype","join");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
}
