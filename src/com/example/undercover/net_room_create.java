package com.example.undercover;



import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;


public class net_room_create extends BaseActivity {
	Button btnStart;
	Button btnWX;
	ScrollView scrollContent;
	SeekBar selectPeople;
	Timer timer;
	AbsoluteLayout viewUser;
	
	JSONArray roomUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roomcreate);
		btnStart = (Button) this.findViewById(R.id.btnstart);
		btnWX = (Button) this.findViewById(R.id.btnwx);
		scrollContent=(ScrollView)this.findViewById(R.id.scrollContent);
		selectPeople=(SeekBar)this.findViewById(R.id.seekSelectPeople);
		viewUser=(AbsoluteLayout)this.findViewById(R.id.viewUserRelative);
		
		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				joinRoom(10001);
			}
		});
		btnWX.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				createRoom();
				Intent mIntent = new Intent();
				mIntent.setClass(net_room_create.this, weixin.class);
				startActivity(mIntent);
			}
		});
		timer = new Timer();
		timer.schedule(timetask, 0, 3000);
	}
	
//	protected void onRestart(){
//		super.onRestart();
//		timer.schedule(timetask, 0, 3000);
//	}
	
	/* 
	 * 退出这个界面
	 * @see com.example.undercover.BaseActivity#onStop()
	 */
	protected void onDestroy(){
		super.onDestroy();
		timer.cancel();
	}
	
	
	// 接受时间
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				getRoomContent();
				super.handleMessage(msg);
			}
		};
		// 传递时间
		private TimerTask timetask = new TimerTask() {
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};
	
	/**
	 * 取得房间信息，每30秒刷新一次，或者是有推送进来的时候刷新
	 */
	public void getRoomContent(){
		getRoomInfo();
	}
	
	
	/**
	 * 生成用户列表
	 */
	private void reflashUser(){
		for(int i=0;i<40;i++){
//			try {
//				JSONObject tem=roomUser.getJSONObject(i);
//				String name=tem.getString("username");
//				int gameuid=tem.getInt("gameuid");
//				String photo=tem.getString("gameuid");
				
				
				Button temBtn =new Button(this);
				int width=disWidth/4;
				
				RelativeLayout temview=new RelativeLayout(this);
//				FrameLayout fl = new FrameLayout(this);
				
				int left=i%4*width;
				int top=(i/4)*width;
				temview.setLayoutParams(new LayoutParams(left, top));
				
				viewUser.addView(temview, width, width);
				
				temBtn.setPadding(5, 5, 5, 5);
				temBtn.setText(String.valueOf(i));
				temBtn.setWidth(width-10);
				temBtn.setHeight(width-10);
				temview.addView(temBtn);
				
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.ROOM_GET_INFO)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				roomUser=obj.getJSONArray("room_user");
//				int roomid=obj.getInt("roomid");
//				setToObject("roomid",String.valueOf(roomid));
//				setToObject("gametype","create");
				reflashUser();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	
}
