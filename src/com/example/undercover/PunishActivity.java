package com.example.undercover;

import android.content.Intent;
import android.os.Bundle;
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
	private TextView punish_guize;
	private TextView punish_1;
	private TextView punish_2;
	private TextView punish_3;
	private TextView punish_4;
	private TextView punish_5;
	private TextView punish_6;
	private ImageView backBtn;
	private boolean flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.punish);
		flag	= false;
		trueBtn		= (Button)findViewById(R.id.trueBtn);
		advenBtn	= (Button)findViewById(R.id.advenBtn);
		changeBtn	= (Button)findViewById(R.id.changeBtn);
		punish_guize = (TextView) findViewById(R.id.punish_guize);
		punish_1	= (TextView)findViewById(R.id.punish_1);
		punish_2	= (TextView)findViewById(R.id.punish_2);
		punish_3	= (TextView)findViewById(R.id.punish_3);
		punish_4	= (TextView)findViewById(R.id.punish_4);
		punish_5	= (TextView)findViewById(R.id.punish_5);
		punish_6	= (TextView)findViewById(R.id.punish_6);
		backBtn = (ImageView) findViewById(R.id.punish_backBtn);
		changeBtn.setVisibility(View.INVISIBLE);
		// 用户选择真心话
		trueBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				trueBtn.setVisibility(View.INVISIBLE);
				advenBtn.setVisibility(View.INVISIBLE);
				changeBtn.setVisibility(View.VISIBLE);
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
				changeBtn.setVisibility(View.VISIBLE);
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
	}
	
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
}
