package com.example.undercover;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.MathUtil;
import com.example.util.PunishProps;

public class PunishActivity extends BaseActivity {
	
	private Button trueBtn;
	private Button advenBtn;
	private Button changeBtn;
	private Button randomBtn;
	private TextView punish_guize;
	private TextView punish_1;
	private TextView punish_2;
	private TextView punish_3;
	private TextView punish_4;
	private TextView punish_5;
	private TextView punish_6;
	private ImageView backBtn;
	private boolean flag;
	// 是否开始 随机数字的计算；
	private boolean isRandom;
	private long number;
	//是否 点击过 随机按钮；
	private boolean isTouch;
	private TextView[] punish;
	private String num;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.punish);
		num	= "7";
		isRandom	= false;
		isTouch	= false;
		flag	= false;
		trueBtn		= (Button)findViewById(R.id.trueBtn);
		advenBtn	= (Button)findViewById(R.id.advenBtn);
		changeBtn	= (Button)findViewById(R.id.changeBtn);
		randomBtn	= (Button)findViewById(R.id.punish_random);
		punish_guize = (TextView) findViewById(R.id.punish_guize);
		punish_1	= (TextView)findViewById(R.id.punish_1);
		punish_2	= (TextView)findViewById(R.id.punish_2);
		punish_3	= (TextView)findViewById(R.id.punish_3);
		punish_4	= (TextView)findViewById(R.id.punish_4);
		punish_5	= (TextView)findViewById(R.id.punish_5);
		punish_6	= (TextView)findViewById(R.id.punish_6);
		punish		= new TextView[6];
		punish[0]	= punish_1;
		punish[1]	= punish_2;
		punish[2]	= punish_3;
		punish[3]	= punish_4;
		punish[4]	= punish_5;
		punish[5]	= punish_6;
		backBtn = (ImageView) findViewById(R.id.punish_backBtn);
		changeBtn.setVisibility(View.INVISIBLE);
		randomBtn.setVisibility(View.INVISIBLE);
		// 用户选择真心话
		trueBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				trueBtn.setVisibility(View.INVISIBLE);
				advenBtn.setVisibility(View.INVISIBLE);
				//暂时，先隐藏 换题 按钮
//				changeBtn.setVisibility(View.VISIBLE);
				randomBtn.setVisibility(View.VISIBLE);
				uMengClick("game_zhenxinhua");
				// 获取惩罚
				getTruePunish();
			}
		});
		// 用户选择大冒险
		advenBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				flag	= true;
				trueBtn.setVisibility(View.INVISIBLE);
				advenBtn.setVisibility(View.INVISIBLE);
				//暂时，先隐藏 换题 按钮
//				changeBtn.setVisibility(View.VISIBLE);
				randomBtn.setVisibility(View.VISIBLE);
				uMengClick("game_damaoxian");
				// 获取惩罚
				getAdvenPunish();
			}
		});
		// 用户选择换题目
		changeBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(flag){
					getAdvenPunish();
				}else{
					getTruePunish();
				}
				changeBtn.setBackgroundResource(R.drawable.popogray72);
				changeBtn.setEnabled(false);
			}
		});
		
		// 用户点击 返回 按钮
		backBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goMain = new Intent();
				goMain.setClass(PunishActivity.this, Setting.class);
				startActivity(goMain);
			}
		});
		
		// 用户点击 随机 按钮
		randomBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isTouch){
					Timer timer	= new Timer();
					timer.schedule(timetask, 0, 68);
					randomBtn.setTextSize(25);
					isTouch	= true;
				}
				isRandom = !isRandom;
			}
		});
	}//onCreat 方法结束
	
	private void getTruePunish(){
		int[] intArr = MathUtil.getInstance().check(190, 6);
		String[] str	= new String[6];
		for(int i=0;i<6;i++){
			str[i] = PunishProps.getQestionHard(intArr[i]);
		}
		setTextView(str);
	}
	
	private void getAdvenPunish(){
		int[] intArr = MathUtil.getInstance().check(93, 6);
		String[] str	= new String[6];
		for(int i=0;i<6;i++){
			str[i] = PunishProps.getPunish(intArr[i]);
		}
		setTextView(str);
	}
	
	private void setTextView(String[] str){
		punish_guize.setText("请输的同学依次回答以下问题");
		punish_1.setText("1、" + str[0]);
		punish_2.setText("2、" + str[1]);
		punish_3.setText("3、" + str[2]);
		punish_4.setText("4、" + str[3]);
		punish_5.setText("5、" + str[4]);
		punish_6.setText("6、" + str[5]);
	}
	
	// 接受时间
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(isRandom){
				addTenMMS();
			}
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
	
	private void addTenMMS(){
//		number	= System.currentTimeMillis()%6+1;
//		randomBtn.setText(number+"");
		num	= MathUtil.getInstance().getRandomNum();
		randomBtn.setText(num);
		returnColors(num);
	}
		
	private void returnColors(String num){
		punish_1.setTextColor(getResources().getColor(R.color.BLACK));
		punish_2.setTextColor(getResources().getColor(R.color.BLACK));
		punish_3.setTextColor(getResources().getColor(R.color.BLACK));
		punish_4.setTextColor(getResources().getColor(R.color.BLACK));
		punish_5.setTextColor(getResources().getColor(R.color.BLACK));
		punish_6.setTextColor(getResources().getColor(R.color.BLACK));
//		punish_1.setTextSize(14); //默认字体
//		punish_2.setTextSize(14);
//		punish_3.setTextSize(14);
//		punish_4.setTextSize(14);
//		punish_5.setTextSize(14);
//		punish_6.setTextSize(14);
		punish[Integer.valueOf(num)-1].setTextColor(getResources().getColor(R.color.RED));
//		punish[Integer.valueOf(num)-1].setTextSize(16);
	}
}
