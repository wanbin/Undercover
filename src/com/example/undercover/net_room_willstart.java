package com.example.undercover;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author wanbin
 * 这个界面是准备选择是否开始游戏
 */
public class net_room_willstart extends BaseActivity {
	Button btnBack;
	Button btnUndercover;
	Button btnKiller;
	TextView txtUndercoverTip;
	TextView txtKillerTip;
	int PeopleCount=6;
	
	int UndercoverMin=4;
	int UndercoverMax=12;
	int KillerMin=6;
	int KillerMax=16;
	
	/**
	 * 用来标记是谁是卧底，还是杀人游戏的
	 */
	int gameType=1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_netgame_select);
		
		final int addPeople=getIntent().getIntExtra("addPeople",0);
		final int PeopleCount2=getIntent().getIntExtra("PeopleCount",1);
		
		PeopleCount=addPeople+PeopleCount2;
		
		btnBack=(Button)this.findViewById(R.id.btnBack);
		btnUndercover=(Button)this.findViewById(R.id.btnUndercover);
		btnKiller=(Button)this.findViewById(R.id.btnKiller);
		
		txtUndercoverTip=(TextView)this.findViewById(R.id.txtUndercoverTip);
		txtKillerTip=(TextView)this.findViewById(R.id.txtKillerTip);
		
		btnBack.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				joinRoom(10001);
				finish();
			}
		});
		
		
		btnUndercover.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				joinRoom(10001);
//				finish();
				gameType=1;
				RoomStartGame(1,addPeople);
			}
		});
		btnKiller.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				joinRoom(10001);
				gameType=2;
				RoomStartGame(2,addPeople);
//				finish();
			}
		});
		checkIsEnable();
	}
	
	protected void checkIsEnable(){
		if(PeopleCount<UndercoverMin){
			txtUndercoverTip.setText("还差"+(UndercoverMin-PeopleCount)+"人");
			btnUndercover.setEnabled(false);
		}else if(PeopleCount>UndercoverMax){
			txtUndercoverTip.setText("多了"+(PeopleCount-UndercoverMax)+"人");
			btnUndercover.setEnabled(false);
		}else{
			txtUndercoverTip.setVisibility(View.VISIBLE);
			btnUndercover.setEnabled(true);
		}
		
		if(PeopleCount<KillerMin){
			txtKillerTip.setText("还差"+(KillerMin-PeopleCount)+"人");
			btnKiller.setEnabled(false);
		}else if(PeopleCount>KillerMax){
			txtKillerTip.setText("多了"+(PeopleCount-KillerMax)+"人");
			btnKiller.setEnabled(false);
		}else{
			btnKiller.setEnabled(true);
		}
		
	}
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.ROOM_START_GAME)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				JSONArray roomUser=obj.getJSONArray("room_user");
				String gamename=obj.getString("content");
//				int roomid=obj.getInt("roomid");
//				setToObject("roomid",String.valueOf(roomid));
//				setToObject("gametype","create");
//				reflashUser();
				Intent mIntent = new Intent();
				mIntent.setClass(net_room_willstart.this, net_room_game.class);
				mIntent.putExtra("userJson",roomUser.toString());
				mIntent.putExtra("gameName", gamename);
				mIntent.putExtra("gameType", gameType);
				mIntent.putExtra("room_contente", obj.getJSONObject("room_contente").toString());
				
//				mIntent.putExtra("PeopleCount", roomUser.length());
				startActivity(mIntent);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
}
