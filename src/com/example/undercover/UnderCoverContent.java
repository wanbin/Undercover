package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author liuchunlong 谁是卧底 游戏简介
 */

public class UnderCoverContent extends BaseActivity {
	private ImageView btnreturn;
	private String GameRule;
	private String Vrequir;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.undercover_content);
		GameRule = getResources().getString(R.string.GameRule);
		Vrequir = getResources().getString(R.string.Vrequir);
		btnreturn = (ImageView) findViewById(R.id.btnreturn);
		TextView ruleText	= (TextView) findViewById(R.id.ruleText);
		TextView winText	= (TextView) findViewById(R.id.winText);
		ruleText.setText(GameRule);
		winText.setText(Vrequir);
		btnreturn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		/**
		 * 下面注释的代码，请勿动，谢谢
		 * 
		 * LinearLayout layoutBoss = new LinearLayout(this); LinearLayout
		 * layoutHor = new LinearLayout(this); LinearLayout layoutVen = new
		 * LinearLayout(this); ImageView imageView = new ImageView(this);
		 * TextView cnText = new TextView(this); TextView enText = new
		 * TextView(this); layoutHor.setOrientation(0);//0水平
		 * layoutHor.setBackgroundColor
		 * (getResources().getColor(R.color.content_head));
		 * layoutVen.setOrientation(1);//1垂直 layoutVen.setPadding(17, 0, 0, 0);
		 * imageView
		 * .setImageDrawable(getResources().getDrawable(R.drawable.popo_who));
		 * imageView.setAdjustViewBounds(true); imageView.setMaxHeight(64);
		 * imageView.setMaxWidth(64);
		 * cnText.setText(getResources().getString(R.string.content_zn));
		 * cnText.setTextSize(25);
		 * enText.setText(getResources().getString(R.string.content_en));
		 * cnText.setTextSize(20);
		 * 
		 * 
		 * layoutHor.addView(enText); layoutHor.addView(cnText);
		 * layoutVen.addView(imageView); layoutVen.addView(layoutHor);
		 * layoutBoss.addView(layoutVen);
		 */
		
	}
}
