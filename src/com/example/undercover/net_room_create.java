package com.example.undercover;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class net_room_create extends BaseActivity {
	Button btnStart;
	Button btnreflash;
	Button btnWX;
	TextView txtRoominfo;
	ScrollView scrollContent;
	SeekBar selectPeople;
	Timer timer;
	TableLayout viewUser;
	
	JSONArray roomUser;
	int addPeople=0;
	
	List<TableRow> listRow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_room_create);
		btnStart = (Button) this.findViewById(R.id.btnstart);
		btnreflash = (Button) this.findViewById(R.id.btnreflash);
		btnWX = (Button) this.findViewById(R.id.btnwx);
		txtRoominfo = (TextView) this.findViewById(R.id.txtRoominfo);
		scrollContent=(ScrollView)this.findViewById(R.id.scrollContent);
		selectPeople=(SeekBar)this.findViewById(R.id.seekSelectPeople);
		viewUser=(TableLayout)this.findViewById(R.id.viewUserTable);
		
		listRow=new ArrayList<TableRow>();
		
		int roomid=Integer.parseInt(getFromObject("roomid")) ;
		if(roomid<10000){
			finish();
		}else{
			txtRoominfo.setText("房间号:"+roomid+"[上限10人]");
		}
		
		
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
		btnreflash.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				joinRoom(10001);
				getRoomContent();
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
//		timer = new Timer();
//		timer.schedule(timetask, 0, 30000);
		getRoomContent();
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
//		timer.cancel();
	}
	
	
	// 接受时间
//		Handler handler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				getRoomContent();
//				super.handleMessage(msg);
//			}
//		};
//		// 传递时间
//		private TimerTask timetask = new TimerTask() {
//			@Override
//			public void run() {
//				Message message = new Message();
//				message.what = 1;
//				handler.sendMessage(message);
//			}
//		};
	
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
//		viewUser.removeAllViews();
		for(int count=0;count<listRow.size();count++){
			TableRow tem=listRow.get(count) ;
			if ( null != tem) {
			      ViewGroup parent = ( ViewGroup )tem.getParent() ;
			      parent.removeView( tem ) ;
			}
		}
		listRow.clear();
		for (int i = 0; i < Math.ceil((float) (roomUser.length()+addPeople) / 4); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 4; m++) {
				try {
					JSONObject temobj =new JSONObject();
					if(index<roomUser.length())
					{
						temobj= roomUser.getJSONObject(index);
					}else{
						temobj.put("photo", "");
					}
					FrameLayout fl = new FrameLayout(this);
					ImageView temBtn = new ImageView(this);
					TextView txt = new TextView(this);
					if (index >= roomUser.length()) {
						txt.setText("NO." + (index + 1-roomUser.length()));
						temBtn.setBackgroundResource(R.drawable.default_photo);
					} else {
						txt.setText(temobj.getString("username"));
					}
					txt.setGravity(Gravity.CENTER | Gravity.BOTTOM);
					fl.addView(temBtn);
					fl.addView(txt);
					fl.setPadding(5, 5, 5, 5);
					newrow.addView(fl, disWidth / 4, disWidth / 4);
					index++;
					ImageFromUrl(
							temBtn,
							temobj.getString("photo"),
							R.drawable.default_photo);
					if (index >= roomUser.length() + addPeople) {
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			listRow.add(newrow);
			viewUser.addView(newrow);
		}
		btnStart.setText("开始游戏 共"+(roomUser.length()+addPeople)+"人");
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
				ToastMessage("获取成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	
}
