package cn.centurywar.undercover;

import java.util.Random;

import android.R.interpolator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class local_zhuan extends BaseActivity {
	ImageView quan;
	ImageView quan2;
	TextView txtName;
	float toDe;
	float fromDe;
	int nowGame=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_zhuan);
		quan = (ImageView) findViewById(R.id.quan);
		quan2 = (ImageView) findViewById(R.id.quan);
		txtName = (TextView) findViewById(R.id.txtName);
		

	
		quan.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnimation();
				SoundPlayer.start();
				uMengClick("count_bottle");
				
			}
		});
		quan2.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnimation();
				SoundPlayer.start();
				uMengClick("count_bottle");
				
			}
		});
		
		quan2.setOnLongClickListener(new Button.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				changeGame();
				return true;
			}
		});
		uMengClick("game_zhuan");
	}
	
	private void changeGame(){
		nowGame++;
		int[] imageId={R.drawable.zhuan_jiu,R.drawable.zhuan_jiu_2};
		String[] gameName={"酒场","聚会"};
		if(nowGame>=gameName.length){
			nowGame=0;
		}
		txtName.setText(gameName[nowGame]);
		String imageUri = "drawable://" + imageId[nowGame];
		ImageFromLocal(quan,imageUri);
		
	}
	
	
	private float getDegrees() {
		float tem= Math.round(Math.random() * 360);
//		int a=(int)tem;
		if(tem%15<=2){
			tem+=2;
		}
		if(tem%15>=13){
			tem-=2;
		}
		return tem;
	}
	private Animation getAnimation(float fromDegrees, float toDegrees) {
		AnimationSet as = new AnimationSet(true);
		RotateAnimation rt = new RotateAnimation(fromDegrees, 1440 + toDegrees,
				dip2px(this,150),dip2px(this,150));
		rt.setDuration(6000);
		rt.setFillAfter(true);
		rt.setInterpolator(new Interpolator() {
			@Override
			public float getInterpolation(float arg0) {
				// TODO Auto-generated method stub
//				bottle.setClickable(true);
				setGameIsNew(ConstantControl.GAME_CIRCLE,false);
				return interpolator.accelerate_decelerate;
			}
		});
		as.addAnimation(rt);

		fromDe = toDegrees % 360;
		return as;
	}
	private void startAnimation() {
		uMengClick("game_zhuan_click");
//		txtDes.setVisibility(View.GONE);
		toDe = getDegrees();
		Animation a = getAnimation(fromDe, toDe);
		a.setFillAfter(true);

		quan.setClickable(false);
		quan2.setClickable(false);
		// restartBtn.setClickable(false);
//		punishment.setVisibility(View.INVISIBLE);
		quan.startAnimation(a);
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
				quan.setClickable(true);
				quan2.setClickable(true);
//				punishment.setVisibility(View.VISIBLE);
				SoundPlayer.highSouce();
//				txtDes.setVisibility(View.VISIBLE);
			}
		});

	}
}
