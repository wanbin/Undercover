package com.example.undercover;


import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class net_home extends BaseActivity {
	Button btnJoin;
	Button btnCreate;
	Button btnResent;
	EditText txtRoomid;
	TextView txtTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_home);
		btnJoin = (Button) this.findViewById(R.id.btnJoin);
		btnCreate = (Button) this.findViewById(R.id.btnCreate);
		btnResent = (Button) this.findViewById(R.id.btnResent);
		
		txtRoomid = (EditText) this.findViewById(R.id.txtRoomid);
		txtTitle = (TextView) this.findViewById(R.id.txtTitle);
		
		
		String username=getFromObject("username");
		if(username.equals(""))
		{
			txtTitle.setText("还没有用户名，请先设置");
		}else{
			txtTitle.setText(username+"请创建或加入房间");
		}
		
		btnJoin.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String tem=txtRoomid.getText().toString();
				int roomid=tem.length()==0?0:Integer.parseInt(tem) ;
				if(roomid<10000){
					ToastMessageLong("请输入正确的房间号");
				}
				else{
					if(!checkhasname()){
						return;
					}
					joinRoom(roomid);
				}
			}
		});
		btnCreate.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!checkhasname()){
					return;
				}
				createRoom();
			}
		});
		btnResent.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkIsInRoom();
			}
		});
		checkIsInRoom();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		checkIsInRoom();
	}
	
	
	
	
	private boolean checkhasname(){
		String username=getFromObject("username");
		if(username.equals(""))
		{
			Intent mIntent = new Intent();
			mIntent.setClass(net_home.this, setting.class);
			startActivity(mIntent);
			return false;
		}
		return true;
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
			Intent mIntent = new Intent();
			mIntent.setClass(net_home.this, net_room_join.class);
			startActivity(mIntent);
		}else{
			btnResent.setVisibility(View.GONE);
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
