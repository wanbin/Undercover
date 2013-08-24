package com.example.undercover;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DiceActivity extends Activity{
	
	private Bitmap diceImage1;
	private Bitmap diceImage2;
	private Bitmap diceImage3;
	private Bitmap diceImage4;
	private Bitmap diceImage5;
	private Bitmap diceImage6;
	
	private Bitmap t_diceImage1;
	private Bitmap t_diceImage2;
	private Bitmap t_diceImage3;
	private Bitmap t_diceImage4;
	
	Timer timer ;
	TimerTask timerTask ;
	boolean flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		diceImage1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.dice1);
		diceImage2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.dice2);
		diceImage3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.dice3);
		diceImage4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.dice4);
		diceImage5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.dice5);
		diceImage6 = BitmapFactory.decodeResource(this.getResources(), R.drawable.dice6);
		
		t_diceImage1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t_dice1);
		t_diceImage2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t_dice2);
		t_diceImage3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t_dice3);
		t_diceImage4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.t_dice4);
		
		final Bitmap[] bitmaps = new Bitmap[]{diceImage1,diceImage2,diceImage3,diceImage4,diceImage5,diceImage6};
		final Bitmap[] t_bitmaps = new Bitmap[]{t_diceImage1,t_diceImage2,t_diceImage3,t_diceImage4};
		
		final DiceView diceView = new DiceView(this);
		RelativeLayout.LayoutParams diceViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		diceViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        diceView.setLayoutParams(diceViewLayoutParams);
		
		
		Button backBtn = new Button(this);
		backBtn.setBackgroundResource(R.drawable.backblue);
		RelativeLayout.LayoutParams btnLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		btnLayoutParams.height = 100;
		btnLayoutParams.width = 100;
		

		
		
		RelativeLayout backgroud = new RelativeLayout(this) ;
		backgroud.addView(backBtn,btnLayoutParams);
		backgroud.addView(diceView);
		
		setContentView(backgroud);
		
		backBtn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final Handler handler = new Handler()
		   {
			   @Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					if(msg.what == 0x123)
					{
						int index = (int) Math.round(Math.random()*3);
						diceView.drawImage = t_bitmaps[index];
						diceView.invalidate();
					}
					
				}
		   };
			
		 diceView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flag)
				{
					if(timer != null)
					{
				        timer.cancel();
				        timer = null;
					}
				   
					if(timerTask != null)
					{
				        timerTask.cancel();
				        timerTask = null;
					}
				   
					if(timer == null && timerTask == null)
					{
				        int index = (int) Math.round(Math.random()*5);
				        diceView.drawImage = bitmaps[index];
				        diceView.invalidate();
				        flag = false;
					}
				}
				else
				{
					if(timer == null)
					{
					    timer = new Timer();
					}
					
					if(timerTask == null)
					{
					    timerTask = new TimerTask() {
						    @Override
						    public void run() {
							    // TODO Auto-generated method stub
							    handler.sendEmptyMessage(0x123);
						    }};
					}
					
					if(timer != null && timerTask != null)
					{
					    timer.schedule(timerTask, 0,100);
					    flag = true;
					}
				}
				
			}
		});
		
	}
	
	public class DiceView extends View
	{
		public Bitmap drawImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.dice1); 
		
		public DiceView(Context context) {
			// TODO Auto-generated constructor stub
			super(context);
			
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			canvas = new Canvas();
			canvas.drawBitmap(drawImage, 0 ,0,null);
		}
		
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
