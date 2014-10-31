package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class net_room_game extends BaseActivity {
	Button btnTip1;
	Button btnTip2;
	Button btnTip3;
	Button btnTip4;
	Button btnTip5;
	Button btnPunish;
	TableLayout viewUser;
	JSONArray roomUser;
	JSONArray punishUser;
	String gameName;
	int gameType;
	List<Button> btnList;

	JSONObject room_contente;
	
	
	//谁是卧底用到的参数
	int soncount=0;
	int fathercount=0;
	String sonstr="";
	String fatherstr="";
	
	//杀人游戏用到的参数
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_netroom_game);
		btnTip1=(Button)this.findViewById(R.id.btnTip1);
		btnTip2=(Button)this.findViewById(R.id.btnTip2);
		btnTip3=(Button)this.findViewById(R.id.btnTip3);
		btnTip4=(Button)this.findViewById(R.id.btnTip4);
		btnTip5=(Button)this.findViewById(R.id.btnTip5);
		btnPunish=(Button)this.findViewById(R.id.btnPunish);
		viewUser=(TableLayout)this.findViewById(R.id.tableUser);
		btnTip1.setX(dip2px(this,100.0f));
		gameName=getIntent().getStringExtra("gameName");
		gameType=getIntent().getIntExtra("gameType",0);
		try {
			roomUser=new JSONArray(getIntent().getStringExtra("userJson"));
			room_contente=new JSONObject(getIntent().getStringExtra("room_contente"));
			if(gameType==1){
				soncount=room_contente.getInt("soncount");
				sonstr=room_contente.getString("son");
				fatherstr=room_contente.getString("father");
				fathercount=roomUser.length()-soncount;
			}else if(gameType==2){
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		btnPunish.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.setClass(net_room_game.this, net_room_punish.class);
				mIntent.putExtra("punish", punishUser.toString());
				startActivity(mIntent);
			}
		});
		
		btnList=new ArrayList<Button>();
		reflashUser();
	}
	
	private void reflashUser(){
		int index=0;
		viewUser.removeAllViews();
		for (int i = 0; i < Math.ceil((float) (roomUser.length()) / 4); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 4; m++) {
				FrameLayout fl = new FrameLayout(this);
				final Button temBtn =new Button(this);
				JSONObject userinfo;
				String name = "玩家";
				try {
					userinfo=roomUser.getJSONObject(index);
					name=userinfo.getString("username");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				final int tag=index;
				temBtn.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						temBtn.setText("出局");
						if(gameType==1){
							tapUnderCoverUser(tag);	
						}else if(gameType==2){
							tapKillerUser(tag);
						}
						temBtn.setEnabled(false);
					}
				});
				
				temBtn.setTag(index);
				temBtn.setText(name);
				temBtn.setGravity(Gravity.CENTER|Gravity.BOTTOM);
				fl.addView(temBtn);
				fl.setPadding(4, 4, 4, 4);
				newrow.addView(fl, disWidth / 4, disWidth / 4);
				btnList.add(temBtn);
				index++;
				if(index>=roomUser.length()){
					break;
				}
			}
			viewUser.addView(newrow);
		}
		
	}
	
	/**
	 * 点击某个玩家，判断游戏是否结束,谁是卧底游戏点击
	 * @param index
	 */
	private void tapUnderCoverUser(int index){
		String shenfen=getShenfenOfIndex(index);
		//证明是卧底
		if(shenfen.equals(sonstr)){
			soncount--;
		}else{
			fathercount--;
		}
		boolean isEnd=false;
		//卧底胜利
		if(soncount>=fathercount){
			btnPunish.setText("卧底胜利，平民接受惩罚");
			String punishStr=getGameruidOfShenfen(fatherstr);
			RoomPunish(punishStr);
			isEnd=true;
		}
		
		//平民胜利
		if(soncount<=0){
			btnPunish.setText("平民胜利，卧底接受惩罚");
			String punishStr=getGameruidOfShenfen(sonstr);
			RoomPunish(punishStr);
			isEnd=true;
		}
		if(isEnd){
			disableAllButton();
		}
	}
	
	private String getGameruidOfShenfen(String shenfen){
		String ret="";
		for(int i=0;i<roomUser.length();i++){
			try {
				JSONObject userinfo=roomUser.getJSONObject(i);
				if( userinfo.getString("content").equals(shenfen)){
					ret=ret+userinfo.getString("gameuid")+"_";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	private String getShenfenOfIndex(int index){
		try {
			JSONObject userinfo=roomUser.getJSONObject(index);
			return userinfo.getString("content");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 点击某个玩家，判断游戏是否结束,谁是卧底游戏点击
	 * @param index
	 */
	private void tapKillerUser(int index){
		Log.v("tap", "tap killer:"+index); 
//		disableAllButton();
	}
	
	private void disableAllButton(){
		for(int i=0;i<btnList.size();i++){
			Button tembtn=btnList.get(i);
			tembtn.setEnabled(false);
			tembtn.setText(getShenfenOfIndex(i));
		}
	}
	
	
	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.ROOM_PUNISH)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				punishUser=obj.getJSONArray("punish");
				btnPunish.setEnabled(true);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
}
