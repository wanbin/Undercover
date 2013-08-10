package com.example.undercover;



import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home_page extends BaseActivity {

	//private Button game5;

	
	private Button btnreturn;
	private Button btnclickme;
	private Button btnaskme;
	private Button btncricleme;
	private Button btntruethings;
	private Button btnQuestion;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hp);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
		btnreturn = (Button) findViewById(R.id.btnreturn);
		btnclickme = (Button) findViewById(R.id.btnclickme);
		// btnaskme = (Button) findViewById(R.id.btnaskme);
		btncricleme = (Button) findViewById(R.id.btncricleme);
		btnQuestion = (Button) findViewById(R.id.jumpQuestion_Btn);
		// btntruethings = (Button) findViewById(R.id.btntruethings);
		
		btnreturn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (true){
					finish();
				} 
			}
		});
		
		btnclickme.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (true){
					Intent goChat = new Intent();
					goChat.setClass(home_page.this, random_50.class);
					uMengClick("game_click");
					startActivity(goChat);
				}
			}
		});
		
		// btnaskme.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if (true){
		// Intent goChat = new Intent();
		// goChat.setClass(home_page.this, QuestionAnswer.class);
		// startActivity(goChat);
		// }
		// }
		// });
		
		// btntruethings.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if (true){
		// Intent goChat = new Intent();
		// goChat.setClass(home_page.this, RotaryBottleActivity.class);
		// startActivity(goChat);
		// }
		// }
		// });
		
		//酒瓶旋转模块跳转
		btncricleme.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goChat = new Intent();
				goChat.setClass(home_page.this, RotaryBottleActivity.class);
				uMengClick("game_bottle");
				startActivity(goChat);
			}
		});
		//真心话模块跳转 lcl 20130810 00:08:56
		btnQuestion.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goChat = new Intent();
				goChat.setClass(home_page.this, QuestionAnswer.class);
				startActivity(goChat);
			}
		});
	}
}