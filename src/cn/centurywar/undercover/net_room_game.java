package cn.centurywar.undercover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class net_room_game extends BaseActivity {
	Button btnTip1;
	Button btnTip2;
	Button btnTip3;
	Button btnTip4;
	Button btnTip5;
	Button btnPunish;
	TextView txtTitle;
	TableLayout viewUser;
	JSONArray roomUser;
	JSONArray punishUser;
	String gameName;
	int gameType;
	int addPeople;
	List<ImageView> btnList;

	JSONObject room_contente;

	// 谁是卧底用到的参数
	int soncount = 0;
	int fathercount = 0;
	String sonstr = "";
	String fatherstr = "";
	Timer timer;

	// 杀人游戏用到的参数
	int killercount=0;
	int policecount=0;
	int peoplecount=0;

	// 正在展示的tag
	int isShowTag = 0;
	int showLimitTime = -1;
	
	//判断一下，是否完全看完自己的身份
	int localTotalPeople=1;

	boolean gameStart=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_room_game);

		btnTip1 = (Button) this.findViewById(R.id.btnTip1);
		btnTip2 = (Button) this.findViewById(R.id.btnTip2);
		btnTip3 = (Button) this.findViewById(R.id.btnTip3);
		btnTip4 = (Button) this.findViewById(R.id.btnTip4);
		btnTip5 = (Button) this.findViewById(R.id.btnTip5);
		txtTitle = (TextView) this.findViewById(R.id.txtTitle);

		
		txtTitle.setText("查看并记住身份");
		
		btnPunish = (Button) this.findViewById(R.id.btnPunish);
		viewUser = (TableLayout) this.findViewById(R.id.tableUser);

		// 把本地用户显示正常

		gameName = getIntent().getStringExtra("gameName");
		gameType = getIntent().getIntExtra("gameType", 0);
		addPeople = getIntent().getIntExtra("addPeople", 0);
		localTotalPeople+=addPeople;
		try {
			roomUser = new JSONArray(getIntent().getStringExtra("userJson"));
			room_contente = new JSONObject(getIntent().getStringExtra(
					"room_contente"));
			if (gameType == 1) {
				soncount = room_contente.getInt("soncount");
				sonstr = room_contente.getString("son");
				fatherstr = room_contente.getString("father");
				fathercount = roomUser.length() - soncount;
			} else if (gameType == 2) {
				killercount=room_contente.getInt("killer");
				policecount=room_contente.getInt("police");
				peoplecount=roomUser.length()-killercount-policecount-1;
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

		btnList = new ArrayList<ImageView>();
		initShowShenfen();
		reflashUser();

		timer = new Timer();
		timer.schedule(timetask, 0, 1000);
	}

	/**
	 * 查看用户身份
	 */
	private void initShowShenfen() {
		switch (addPeople) {
		case 0:
			btnTip5.setVisibility(View.INVISIBLE);
			btnTip4.setVisibility(View.INVISIBLE);
			btnTip3.setVisibility(View.INVISIBLE);
			btnTip2.setVisibility(View.INVISIBLE);
			break;
		case 1:
			btnTip5.setVisibility(View.INVISIBLE);
			btnTip4.setVisibility(View.INVISIBLE);
			btnTip3.setVisibility(View.INVISIBLE);
			break;
		case 2:
			btnTip5.setVisibility(View.INVISIBLE);
			btnTip4.setVisibility(View.INVISIBLE);
			break;
		case 3:
			btnTip5.setVisibility(View.INVISIBLE);
			break;
		case 4:
			break;
		}
		btnTip5.setText("NO.4(长按)");
		btnTip4.setText("NO.3(长按)");
		btnTip3.setText("NO.2(长按)");
		btnTip2.setText("NO.1(长按)");
		btnTip1.setText("自己(长按)");
		int thisGameuid = Integer.parseInt(getFromObject("gameuid"));
		btnTip1.setTag(thisGameuid);
		btnTip2.setTag(1);
		btnTip3.setTag(2);
		btnTip4.setTag(3);
		btnTip5.setTag(4);
		btnTip1.setOnLongClickListener(new Button.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return showShenFen(v);
			}
		});
		btnTip2.setOnLongClickListener(new Button.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return showShenFen(v);
			}
		});
		btnTip3.setOnLongClickListener(new Button.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return showShenFen(v);
			}
		});
		btnTip4.setOnLongClickListener(new Button.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return showShenFen(v);
			}
		});
		btnTip5.setOnLongClickListener(new Button.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return showShenFen(v);
			}
		});
	}

	private boolean showShenFen(View v) {
		int tag = (Integer) v.getTag();
		if(isShowTag != tag&& isShowTag != 0){
			return false;
		}
		Button btn = (Button) v;
		String shenfen = getShenfenOfGameuid(tag);
		btn.setText(shenfen);
		if (isShowTag == tag && isShowTag != 0) {
			hideBtnOfGameuid(isShowTag);
			return false;
		}
		isShowTag = tag;
		showLimitTime = 3;
		return true;
	}

	private void hideBtnOfGameuid(int gameuid) {
		switch (gameuid) {
		case 1:
			btnTip2.setVisibility(View.INVISIBLE);
			break;
		case 2:
			btnTip3.setVisibility(View.INVISIBLE);
			break;
		case 3:
			btnTip4.setVisibility(View.INVISIBLE);
			break;
		case 4:
			btnTip5.setVisibility(View.INVISIBLE);
			break;
		}
		int thisgameuid = Integer.parseInt(getFromObject("gameuid"));
		if (gameuid == thisgameuid) {
			btnTip1.setVisibility(View.INVISIBLE);
		}
		isShowTag=0;
		if(btnTip1.getVisibility()==View.INVISIBLE&&
				btnTip2.getVisibility()==View.INVISIBLE&&
				btnTip3.getVisibility()==View.INVISIBLE&&
				btnTip4.getVisibility()==View.INVISIBLE&&
				btnTip5.getVisibility()==View.INVISIBLE){
			txtTitle.setText("游戏正式开始，长按投票");
			gameStart=true;
		}
	}

	private void reflashUser() {
		int index = 0;
		viewUser.removeAllViews();
		for (int i = 0; i < Math.ceil((float) (roomUser.length()) / 4); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 4; m++) {
				JSONObject temobj = new JSONObject();
				FrameLayout fl = new FrameLayout(this);
				final ImageView temBtn = new ImageView(this);
				final TextView txt = new TextView(this);
				JSONObject userinfo;
				String name = "玩家";
				String content="";
				try {
					temobj = roomUser.getJSONObject(index);
					userinfo = roomUser.getJSONObject(index);
					name = userinfo.getString("username");
					content = userinfo.getString("content");
					if(temobj.has("photo")&&temobj.getString("photo").length()>0){
						ImageFromUrl(temBtn, temobj.getString("photo"),
								R.drawable.btn_photo_pressed);
						}
					else{
						temBtn.setBackgroundResource(R.drawable.btn_photo_pressed);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				final int tag = index;
				temBtn.setOnLongClickListener(new Button.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						//游戏没有正式开始的时候，不能点
						if(!gameStart){
							return false;
						}
						txt.setText("出局");
						if (gameType == 1) {
							tapUnderCoverUser(tag);
						} else if (gameType == 2) {
							tapKillerUser(tag);
						}
						temBtn.setBackgroundResource(R.drawable.default_photo3);
						temBtn.setEnabled(false);
						return true;
					}
				});

				if(gameType==2&&content.equals("法官")){
					name="法官";
					temBtn.setEnabled(false);
				}
				temBtn.setTag(index);
				txt.setText(name);
				txt.setGravity(Gravity.CENTER | Gravity.BOTTOM);
				fl.addView(temBtn);
				fl.addView(txt);
				fl.setPadding(4, 4, 4, 4);
				newrow.addView(fl, disWidth / 4, disWidth / 4);
				btnList.add(temBtn);
				index++;
				if (index >= roomUser.length()) {
					break;
				}
			}
			viewUser.addView(newrow);
		}

	}

	/**
	 * 点击某个玩家，判断游戏是否结束,谁是卧底游戏点击
	 * 
	 * @param index
	 */
	private void tapUnderCoverUser(int index) {
		String shenfen = getShenfenOfIndex(index);
		// 证明是卧底
		if (shenfen.equals(sonstr)) {
			soncount--;
		} else {
			fathercount--;
		}
		boolean isEnd = false;
		// 卧底胜利
		if (soncount >= fathercount) {
			btnPunish.setText("卧底胜利，平民接受惩罚");
			String punishStr = getGameruidOfShenfen(fatherstr);
			RoomPunish(punishStr);
			isEnd = true;
			SoundPlayer.normalSouce();
		}

		// 平民胜利
		if (soncount <= 0) {
			btnPunish.setText("平民胜利，卧底接受惩罚");
			String punishStr = getGameruidOfShenfen(sonstr);
			RoomPunish(punishStr);
			SoundPlayer.highSouce();
			isEnd = true;
		}
		if (isEnd) {
			disableAllButton();
		}
	}

	private String getGameruidOfShenfen(String shenfen) {
		String ret = "";
		for (int i = 0; i < roomUser.length(); i++) {
			try {
				JSONObject userinfo = roomUser.getJSONObject(i);
				if (userinfo.getString("content").equals(shenfen)) {
					ret = ret + userinfo.getString("gameuid") + "_";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	private String getShenfenOfIndex(int index) {
		try {
			JSONObject userinfo = roomUser.getJSONObject(index);
			return userinfo.getString("content");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private String getShenfenOfGameuid(int gameuid) {
		String ret = "";
		for (int i = 0; i < roomUser.length(); i++) {
			try {
				JSONObject userinfo = roomUser.getJSONObject(i);
				if (Math.abs(userinfo.getInt("gameuid")) == gameuid) {
					return userinfo.getString("content");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 点击某个玩家，判断游戏是否结束,谁是卧底游戏点击
	 * 
	 * @param index
	 */
	private void tapKillerUser(int index) {
		String shenfen = getShenfenOfIndex(index);
		// 证明是卧底
		if (shenfen.equals("警察")) {
			policecount--;
		} else if (shenfen.equals("杀手")) {
			killercount--;
		}else if (shenfen.equals("平民")) {
			peoplecount--;
		}
		boolean isEnd = false;
		// 卧底胜利
		if (killercount <= 0) {
			txtTitle.setText("杀手失败，平民和警察胜利");
			btnPunish.setText("杀人接受惩罚");
			String punishStr = getGameruidOfShenfen("杀手");
			RoomPunish(punishStr);
			isEnd = true;
			SoundPlayer.normalSouce();
		}

		// 平民胜利
		if (peoplecount <= 0||policecount<=0) {
			txtTitle.setText("杀手胜利，平民和警察失败");
			btnPunish.setText("平民和警察接受惩罚");
			String punishStr = getGameruidOfShenfen("平民")+getGameruidOfShenfen("警察");
			RoomPunish(punishStr);
			SoundPlayer.highSouce();
			isEnd = true;
		}
		if (isEnd) {
			disableAllButton();
		}
		
	}

	private void disableAllButton() {
		for (int i = 0; i < btnList.size(); i++) {
			ImageView tembtn = btnList.get(i);
			tembtn.setEnabled(false);
			tembtn.setBackgroundResource(R.drawable.default_photo3);
//			tembtn.setText(getShenfenOfIndex(i));
		}
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.ROOM_PUNISH)) {
			try {
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				punishUser = obj.getJSONArray("punish");
				btnPunish.setBackgroundResource(R.drawable.fang_purple_pressed);
				btnPunish.setEnabled(true);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

	private void costOneSec() {
		// int isShowTag;
		// int showLimitTime;
		if (showLimitTime > 0) {
			showLimitTime--;
		} else if (showLimitTime == 0) {
			showLimitTime = -1;
			hideBtnOfGameuid(isShowTag);
		}
	}

	// 接受时间
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			costOneSec();
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
}
