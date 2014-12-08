package cn.centurywar.undercover;

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
import android.widget.TextView;

public class local_bottle extends BaseActivity {
	int bottleWidth;
	int bottleHeight;
	float fromDe;
	float toDe;
	Button restartBtn;
	TextView txtDes;
	private Button punishment;
	ImageView bottle;
	ImageView quan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_bottle);
		toDe = getDegrees();
		bottle = (ImageView) findViewById(R.id.bottle_imageView);
		quan = (ImageView) findViewById(R.id.quan);
		punishment = (Button) findViewById(R.id.button1);
		punishment.setVisibility(View.INVISIBLE);
		txtDes = (TextView) findViewById(R.id.txtDes);
		txtDes.setVisibility(View.GONE);
//		bottle.setPaddingRelative(110, 0, 0, 0);
//		bottle.setPaddingRelative(start, top, end, bottom);
		quan.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnimation();
				SoundPlayer.start();
				uMengClick("count_bottle");
				
			}
		});
		// 惩罚
		punishment.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(local_bottle.this,
						local_punish.class);
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
		return Math.round(Math.random() * 360);
	}

	private Animation getAnimation(float fromDegrees, float toDegrees) {
		AnimationSet as = new AnimationSet(true);
		RotateAnimation rt = new RotateAnimation(fromDegrees, 2880 + toDegrees,
				dip2px(this,110),dip2px(this,6));
		rt.setDuration(6000);
		rt.setFillAfter(true);
		rt.setInterpolator(new Interpolator() {
			@Override
			public float getInterpolation(float arg0) {
				// TODO Auto-generated method stub
				bottle.setClickable(true);
				setGameIsNew(ConstantControl.GAME_CIRCLE,false);
				return interpolator.accelerate_decelerate;
			}
		});
		as.addAnimation(rt);

		fromDe = toDegrees % 360;
		return as;
	}

	private void startAnimation() {
		txtDes.setVisibility(View.GONE);
		toDe = getDegrees();
		Animation a = getAnimation(fromDe, toDe);
		a.setFillAfter(true);

		quan.setClickable(false);
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
				quan.setClickable(true);
				punishment.setVisibility(View.VISIBLE);
				SoundPlayer.faile();
				txtDes.setVisibility(View.VISIBLE);
			}
		});

	}
		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
//			onBackPressed();
		}
}
