package com.example.undercover;



import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;


public class net_room_create extends BaseActivity {
	Button btnStart;
	Button btnWX;
	ScrollView scrollContent;
	SeekBar selectPeople;
	Timer timer;
	TableLayout viewUser;
	
	JSONArray roomUser;
	int addPeople=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roomcreate);
		btnStart = (Button) this.findViewById(R.id.btnstart);
		btnWX = (Button) this.findViewById(R.id.btnwx);
		scrollContent=(ScrollView)this.findViewById(R.id.scrollContent);
		selectPeople=(SeekBar)this.findViewById(R.id.seekSelectPeople);
		viewUser=(TableLayout)this.findViewById(R.id.viewUserTable);
		
		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				joinRoom(10001);
				Intent mIntent = new Intent();
				mIntent.setClass(net_room_create.this, net_room_willstart.class);
				mIntent.putExtra("addPeople", addPeople);
				mIntent.putExtra("PeopleCount", roomUser.length());
				startActivity(mIntent);
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
//		selectPeople.seton; 
		selectPeople.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
				addPeople=progress;
				reflashUser();
//	                description.setText("当前进度："+progress+"%");
            }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});
		timer = new Timer();
		timer.schedule(timetask, 0, 30000);
	}
	
//	protected void onRestart(){
//		super.onRestart();
//		timer.schedule(timetask, 0, 3000);
//	}
	
	/* 
	 * 退出这个界面
	 * @see com.example.undercover.BaseActivity#onStop()
	 */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		timer.cancel();
	}
	
	
	// 接受时间
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				getRoomContent();
				super.handleMessage(msg);
			}
		};
		// 传递时间
		private TimerTask timetask = new TimerTask() {
			@Override
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
		int index=0;
		viewUser.removeAllViews();
		for (int i = 0; i < Math.ceil((float) (roomUser.length()+addPeople) / 4); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 4; m++) {
				FrameLayout fl = new FrameLayout(this);
				Button temBtn =new Button(this);
				if(index<addPeople){
					temBtn.setText("NO."+(index+1));
					temBtn.setBackgroundResource(R.drawable.default_photo);
				}else{
					temBtn.setText(String.valueOf(index));
				}
				temBtn.setGravity(Gravity.CENTER|Gravity.BOTTOM);
				fl.addView(temBtn);
				fl.setPadding(4, 4, 4, 4);
				newrow.addView(fl, disWidth / 4, disWidth / 4);
				index++;
				if(index>=roomUser.length()+addPeople){
					break;
				}
			}
			viewUser.addView(newrow);
		}
		
	}
	
	
	@Override
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
