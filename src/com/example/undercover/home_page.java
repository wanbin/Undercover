package com.example.undercover;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class home_page extends Activity {

	//private Button game5;

	
	private ImageView undercover_im;
	private ImageView random_im;
	private ImageView timeclick_im;
	private ImageView bottlerotate_im;
	
	//private TextView undercover_text;
	//private TextView random_text;
	//private TextView timeclick_text;
	//private TextView bottlerotate_text;

	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//game5 = (Button) findViewById(R.id.rotaryBottleBtn);

		setContentView(R.layout.hp);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
		undercover_im = (ImageView) findViewById(R.id.imageView1);
		random_im = (ImageView) findViewById(R.id.imageView2);
		timeclick_im = (ImageView) findViewById(R.id.imageView3);
		bottlerotate_im = (ImageView) findViewById(R.id.imageView4);
		
		//undercover_text = (TextView) findViewById(R.id.textView1);
		//random_text = (TextView) findViewById(R.id.textView2);
		//timeclick_text = (TextView) findViewById(R.id.textView3);
		//bottlerotate_text = (TextView) findViewById(R.id.textView4);
		
		
	
		
		undercover_im.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				
				if (true){
					
					Intent goChat = new Intent();
					goChat.setClass(home_page.this,Setting.class);
					startActivity(goChat);
				} 
			}
		});
		
		random_im.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				
				if (true){
					
					Intent goChat = new Intent();
					goChat.setClass(home_page.this,random_50.class);
					startActivity(goChat);
				}
			}
		});
		
		timeclick_im.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				
				if (true){
					
					Intent goChat = new Intent();
					goChat.setClass(home_page.this,random_50.class);
					startActivity(goChat);
				}
			}
		});
		
		bottlerotate_im.setOnClickListener(new Button.OnClickListener() {
			@Override
			
			public void onClick(View v) {
				
				if (true){
					
					Intent goChat = new Intent();
					goChat.setClass(home_page.this,random_50.class);
					startActivity(goChat);
				}
			}
		});
		
//		game5.setOnClickListener(new Button.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent goChat = new Intent();
//				goChat.setClass(home_page.this,RotaryBottleActivity.class);
//				startActivity(goChat);
//			}
//		});
	}
}