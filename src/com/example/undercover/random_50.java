package com.example.undercover;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class random_50 extends Activity {
	//int random_times;
	int click_times=50;
	private Button clike_button;
	Random random=new Random();
	int random_times=Math.abs(random.nextInt()) % 50;
	
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);	
	setContentView(R.layout.random_50);
	clike_button = (Button) findViewById(R.id.button1);
	clike_button.setOnClickListener(new Button.OnClickListener() {
		@Override
		
		public void onClick(View v) {
			click_times--;
			if (click_times==random_times){
				
				Intent goChat = new Intent();
				goChat.setClass(random_50.this,Setting.class);
				startActivity(goChat);
			}
		}
	});
	}
	}

