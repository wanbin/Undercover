package com.example.undercover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home_page extends Activity {
	private Button game1;
	private Button game2;
	private Button game3;
	private Button game4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		game1 = (Button) findViewById(R.id.button1);
		game2 = (Button) findViewById(R.id.button2);
		game3 = (Button) findViewById(R.id.button3);
		game4 = (Button) findViewById(R.id.button4);
		
		game1.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				
				if (true){
					
					Intent goChat = new Intent();
					goChat.setClass(home_page.this,Setting.class);
					startActivity(goChat);
				} 
			}
		});
		
		game2.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				
				if (true){
					
					Intent goChat = new Intent();
					goChat.setClass(home_page.this,random_50.class);
					startActivity(goChat);
				}
			}
		});
		
		game3.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				
				if (true){
					
					Intent goChat = new Intent();
					goChat.setClass(home_page.this,random_50.class);
					startActivity(goChat);
				}
			}
		});
		
		game4.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				
				if (true){
					
					Intent goChat = new Intent();
					goChat.setClass(home_page.this,random_50.class);
					startActivity(goChat);
				}
			}
		});
	}
}