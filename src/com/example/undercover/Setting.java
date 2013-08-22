package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Setting extends BaseActivity {
	private ImageView btnAdd;
	private ImageView btnCost;
	private ImageView btnAddUnder;
	private ImageView btnCostUnder;
	private ImageView btnStart;
	private ImageView moregame;
	private TextView people;
	private TextView under;
	private TextView title;
	private int maxPeople = 12;
	// 说明
	private int peopleCount = 4;
	private int underCount = 1;
	private String[] content;
	private Random random;
	private String son;
	private String father;
	private CheckBox afterShow;
	// 是否添加 冤死 提示，在投票后
	private boolean isShow;
	// 是否添加空白词
	private boolean isBlank;
	
	// 共享的参与和卧底数
	private SharedPreferences gameInfo;

	// private int soncount = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		btnAdd = (ImageView) findViewById(R.id.btnAdd);
		btnAddUnder = (ImageView) findViewById(R.id.btnAddUnder);
		btnCost = (ImageView) findViewById(R.id.btnCost);
		btnCostUnder = (ImageView) findViewById(R.id.btnCostUnder);
		btnStart = (ImageView) findViewById(R.id.btnStart);
		moregame = (ImageView) findViewById(R.id.moregame);
		//Button startChatRoom =(Button) findViewById(R.id.startChatRoom);
		people = (TextView) findViewById(R.id.txtPeople);
		under = (TextView) findViewById(R.id.txtUnder);
		title = (TextView) findViewById(R.id.txtPeopleTitle);
		// 添加 冤死 提示按钮
		afterShow	= (CheckBox)findViewById(R.id.afterShow);
		
		// 共享数据
		gameInfo = getSharedPreferences("gameInfo", 0);
		
		afterShow.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					isShow	= true;
				}else{
					isShow	= false;
				}
			}
		});
		// 添加 空白词 按钮
		CheckBox blank	= (CheckBox)findViewById(R.id.isBlank);
		blank.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					isBlank	= true;
				}else{
					isBlank	= false;
				}
			}
		});
		random = new Random();
		
		ScaleAnimation scaleAni = new ScaleAnimation(1.0f, 1.02f, 1.0f, 1.02f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAni.setRepeatMode(Animation.REVERSE);
		scaleAni.setRepeatCount(-1);
		scaleAni.setDuration(1000);
		btnStart.startAnimation(scaleAni);

		// 注释掉chat room
		//startChatRoom.setVisibility(View.INVISIBLE);
		setPeople();
		setUnder();
		btnAdd.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (peopleCount < maxPeople) {
					SoundPlayer.playball();
					peopleCount++;
					underCount = Math.max((int) Math.floor(peopleCount / 4), 1);
				}
				setPeople();
				setUnder();
			}
		});

		btnCost.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (peopleCount > 4) {
					SoundPlayer.playball();
					peopleCount--;
					underCount = Math.min(
							Math.max((int) Math.floor(peopleCount / 4), 1),
							underCount);
				}
				setPeople();
				setUnder();
			}
		});

		btnAddUnder.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (underCount < 3) {
					SoundPlayer.playball();
					underCount++;
					peopleCount = Math.min(peopleCount + 4, maxPeople);
				}
				setPeople();
				setUnder();
			}
		});

		btnCostUnder.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (underCount > 1) {
					SoundPlayer.playball();
					underCount--;
				}
				setUnder();
			}
		});
		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				int selectindex = Math.abs(random.nextInt()) % content.length;
//				String[] tem = getRandomString(content[selectindex]);
				gameInfo.edit().putInt("peopleCount", peopleCount).commit(); 
				gameInfo.edit().putInt("underCount", underCount).commit(); 
				gameInfo.edit().putBoolean("isShow", isShow).commit(); 
				gameInfo.edit().putBoolean("isBlank", isBlank).commit(); 
//				Bundle bundle = new Bundle();
//				bundle.putStringArray("content", tem);
//				bundle.putString("son", son);
//				bundle.putInt("sonCount", underCount);
//				bundle.putInt("peopleCount", peopleCount);
//				bundle.putInt("underCount", underCount);
//				bundle.putBoolean("isShow", isShow);
//				bundle.putBoolean("isBlank", isBlank);
				Intent goMain = new Intent();
//				goMain.putExtras(bundle);
				goMain.setClass(Setting.this, fanpai.class);
				startActivity(goMain);
				uMengClick("game_undercover_start");
				SoundPlayer.playball();
				finish();
			}
		});
		
		

		moregame.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				if (true){
					SoundPlayer.playball();
					finish();
				}
			}
		});

	}

	private void setPeople() {
		if (peopleCount == maxPeople) {
			btnAdd.setBackgroundResource(R.drawable.popogray72);
			btnAdd.setClickable(false);
		} else {
			btnAdd.setBackgroundResource(R.drawable.popo72);
			btnAdd.setClickable(true);
		}
		if (peopleCount == 4) {
			btnCost.setBackgroundResource(R.drawable.popogray72);
			btnCost.setClickable(false);
		} else {
			btnCost.setBackgroundResource(R.drawable.popo72);
			btnCost.setClickable(true);
		}
		people.setText(Integer.toString(peopleCount));
		
	}

	private void setUnder() {
		if (underCount == 3) {
			btnAddUnder.setBackgroundResource(R.drawable.popogray72);
			btnAddUnder.setClickable(false);
		} else {
			btnAddUnder.setBackgroundResource(R.drawable.popo72);
			btnAddUnder.setClickable(true);
		}
		if (underCount == 1) {
			btnCostUnder.setBackgroundResource(R.drawable.popogray72);
			btnCostUnder.setClickable(false);
		} else {
			btnCostUnder.setBackgroundResource(R.drawable.popo72);
			btnCostUnder.setClickable(true);
		}
		under.setText(Integer.toString(underCount));
	}

//	private String[] getRandomString(String contnettxt)
//	{
//		// if (peopleCount > 15) {
//		// soncount = 4;
//		// } else if (peopleCount > 10) {
//		// soncount = 3;
//		// } else if (peopleCount > 5) {
//		// soncount = 2;
//		// }
//		String[] children  =new String[2];
//		children = contnettxt.split("_");
//		int sonindex = Math.abs(random.nextInt()) % 2;
//		son = children[sonindex];
//		father = children[Math.abs(sonindex - 1)];
//		String[] ret = new String[peopleCount];
//		for (int n = 0; n < ret.length; n++) {
//			ret[n] = father;
//		}
//		for (int i = 0; i < underCount; i++) {
//			int tem;
//			do {
//				tem = Math.abs(random.nextInt()) % peopleCount;
//			} while (ret[tem].equals(son));
//			ret[tem] = son;
//		}
//		return ret;
//	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

	// protected void initTip() {
	// tip.setText("游戏规则：\n" + "\t 1.选择参与人数与卧底人数开始游戏 \n"
	// + "\t 2.每人记得自己的编号和身份 \n" + "\t 3.依次描述自己的身份 \n"
	// + "\t 4.每轮结束大家投票选出卧底 \n" + "\t 5.卧底出局后剩余玩家继续进行游戏 \n"
	// + "胜利条件； \n" + "\t 1.当卧底全部被指出则平民胜利 \n"
	// + "\t 2.当卧底数大于等于平民数则卧底胜利");
	// }
	

}
