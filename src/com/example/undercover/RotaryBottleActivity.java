package com.example.undercover;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class RotaryBottleActivity extends Activity{
	int bottleWidth;
	int bottleHeight;
	float fromDe;
	float toDe;
	ImageView bottle;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.rotarybottle);
		toDe = getDegrees();
		
		bottle = (ImageView)findViewById(R.id.imageView1);
		final Button restartBtn = (Button)findViewById(R.id.startRotaryBottleBtn);
		restartBtn.setClickable(false);
		restartBtn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toDe = getDegrees();
				Animation a = getAnimation(fromDe, toDe);
				a.setFillAfter(true);
				bottle.startAnimation(a);		
			}
		});	
		
		ViewTreeObserver vto2 = bottle.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				bottle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				bottleWidth = bottle.getWidth();
				bottleHeight = bottle.getHeight();
				
				restartBtn.setClickable(true);
			}
		});
		
		
	}
	
	private float getDegrees()
	{
		Random random = new Random(System.currentTimeMillis());
		float ranFloat =  random.nextFloat()*1000;
		while(ranFloat<720)
		{
			ranFloat = random.nextFloat()*1000;
		}
		
		return ranFloat;
	}
	
	private Animation getAnimation(float fromDegrees,float toDegrees)
	{
		AnimationSet as=new AnimationSet(true);  
		RotateAnimation rt = new RotateAnimation(fromDegrees,toDegrees,bottleWidth/2,bottleHeight/2);
		rt.setDuration(6000);
		rt.setFillAfter(true);
		as.addAnimation(rt);
		
		fromDe = toDegrees%360;
		return as;
	}
}
