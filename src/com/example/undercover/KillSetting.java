package com.example.undercover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class KillSetting extends BaseActivity {
	private ImageView btnAdd;
	private ImageView btnCost;
	private Button btnStart;
	private TextView people;
	private TextView wordText;
	private int maxPeople = 12;
	// 说明
	private int peopleCount = 6;
	private int policeCount = 1;
	private int killerCount = 1;
	private CheckBox afterShow;
	// 是否添加 冤死 提示，在投票后
	private boolean isShow = true;
	// 是否添加空白词
	private boolean isBlank = false;
	// 分类词组
	// private StringBuffer word=new StringBuffer();
	// 共享的参与和卧底数
	private SharedPreferences gameInfo;
//	private int itemChecked;
	private String wordStr;

	// 词汇分类
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_killsetting);
		btnAdd = (ImageView) findViewById(R.id.btnAdd);
		btnCost = (ImageView) findViewById(R.id.btnCost);
		btnStart = (Button) findViewById(R.id.btnStart);
		//Button startChatRoom =(Button) findViewById(R.id.startChatRoom);
		people = (TextView) findViewById(R.id.txtPeople);
		wordText = (TextView) findViewById(R.id.wordText);

		wordText.setText(strFromId("setting_word_new")
				+ strFromId("setting_word_all"));
		// 添加 冤死 提示按钮h
		afterShow	= (CheckBox)findViewById(R.id.afterShow);

		initBtnInfo(R.id.btninfo, strFromId("txtKillerHelp"));

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
		btnAdd.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (peopleCount < maxPeople) {
					SoundPlayer.playball();
					peopleCount++;
					policeCount = Math.max((int) Math.floor(peopleCount / 4), 1);
					killerCount = Math.max((int) Math.floor(peopleCount / 4), 1);
				}
				setPeople();
			}
		});

		btnCost.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (peopleCount > 5) {
					SoundPlayer.playball();
					peopleCount--;
					policeCount = Math.max((int) Math.floor(peopleCount / 4), 1);
					killerCount = Math.max((int) Math.floor(peopleCount / 4), 1);
				}
				setPeople();
			}
		});

		btnStart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameInfo.edit().putInt("peopleCount", peopleCount).commit(); 
				gameInfo.edit().putInt("policeCount", policeCount).commit();
				gameInfo.edit().putInt("killerCount", killerCount).commit();
				gameInfo.edit().putBoolean("isShow", isShow).commit(); 
				Intent goMain = new Intent();
				goMain.setClass(KillSetting.this, kill.class);
				startActivity(goMain);
				uMengClick("game_kill_start");
				SoundPlayer.playball();
				finish();
			}
		});
		btnStart.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					// v.setBackgroundResource(R.drawable.btn_start2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					// v.setBackgroundResource(R.drawable.btn_start);
				}
				return false;
			}
		});
		

	}

	private void setPeople() {
		if (peopleCount == maxPeople) {
//			btnAdd.setBackgroundResource(R.drawable.popogray72);
			btnAdd.setClickable(false);
		} else {
//			btnAdd.setBackgroundResource(R.drawable.popo72);
			btnAdd.setClickable(true);
		}
		if (peopleCount == 4) {
//			btnCost.setBackgroundResource(R.drawable.popogray72);
			btnCost.setClickable(false);
		} else {
//			btnCost.setBackgroundResource(R.drawable.popo72);
			btnCost.setClickable(true);
		}
		people.setText(Integer.toString(peopleCount));
		
	}




}
