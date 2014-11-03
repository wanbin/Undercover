package com.example.undercover;


import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class net_home extends BaseActivity {
	Button btnJoin;
	Button btnCreate;
	EditText txtRoomid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_home);
		btnJoin = (Button) this.findViewById(R.id.btnJoin);
		
		txtRoomid = (EditText) this.findViewById(R.id.txtRoomid);
		
		btnJoin.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				int roomid=Integer.parseInt(txtRoomid.getText().toString()) ;
				if(roomid<10000){
					ToastMessageLong("请输入正确的房间号");
				}
				else{
					joinRoom(roomid);
				}
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
	
	@Override
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
				Intent mIntent = new Intent();
				mIntent.setClass(net_home.this, net_room_join.class);
				startActivity(mIntent);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
}
