package com.example.undercover;

import android.R.interpolator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class RotaryBottleActivity extends BaseActivity {
	int bottleWidth;
	int bottleHeight;
	float fromDe;
	float toDe;

	Button restartBtn;
	private Button punishment;

	ImageView bottle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.rotarybottle);
		toDe = getDegrees();

		bottle = (ImageView) findViewById(R.id.imageView1);

		//restartBtn = (Button) findViewById(R.id.startRotaryBottleBtn);
		punishment = (Button) findViewById(R.id.button1);
		punishment.setVisibility(View.INVISIBLE);

		bottle.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnimation();
				SoundPlayer.playbottle();
				uMengClick("count_bottle");
				
			}
		});

//		restartBtn.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				startAnimation();
//				uMengClick("count_bottle");
//			}
//		});
		// 惩罚
		punishment.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(RotaryBottleActivity.this,
						PunishActivity.class);
				startActivity(intentGo);
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
			}
		});

	}

	private float getDegrees() {
		return (float) Math.round(Math.random() * 360);
	}

	private Animation getAnimation(float fromDegrees, float toDegrees) {
		AnimationSet as = new AnimationSet(true);
		RotateAnimation rt = new RotateAnimation(fromDegrees, 2880 + toDegrees,
				bottleWidth / 2, bottleHeight * 4 / 7);
		rt.setDuration(6000);
		rt.setFillAfter(true);
		rt.setInterpolator(new Interpolator() {
			@Override
			public float getInterpolation(float arg0) {
				// TODO Auto-generated method stub
				bottle.setClickable(true);
				return interpolator.accelerate_decelerate;
			}
		});
		as.addAnimation(rt);

		fromDe = toDegrees % 360;
		return as;
	}

	private void startAnimation() {
		toDe = getDegrees();
		Animation a = getAnimation(fromDe, toDe);
		a.setFillAfter(true);

		bottle.setClickable(false);
		// restartBtn.setClickable(false);
		punishment.setVisibility(View.INVISIBLE);
		bottle.startAnimation(a);
		a.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				// restartBtn.setClickable(true);
				bottle.setClickable(true);
				punishment.setVisibility(View.VISIBLE);
				SoundPlayer.playclaps();
			}
		});

	}

	// //退出确认
//		public void onBackPressed() {  
	// new AlertDialog.Builder(this).setTitle("确认退出吗？")
//			    .setIcon(android.R.drawable.ic_dialog_info)  
	// .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			  
//			        @Override  
//			        public void onClick(DialogInterface dialog, int which) {  
	// // 点击“确认”后的操作
//			        RotaryBottleActivity.this.finish();  
//			  
//			        }  
//			    })  
	// .setNegativeButton("返回", new DialogInterface.OnClickListener() {
//			  
//			        @Override  
//			        public void onClick(DialogInterface dialog, int which) {  
	// // 点击“返回”后的操作,这里不设置没有任何操作
//			        }  
//			    }).show();  
//			// super.onBackPressed();  
//			   }  
		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
//			onBackPressed();
		}
}
