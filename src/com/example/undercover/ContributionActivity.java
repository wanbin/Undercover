package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author liuchunlong 谁是卧底 游戏简介
 */

public class ContributionActivity extends BaseActivity {
	private LinearLayout content;
	private String[] contribution;
	private ImageView btnreturn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contribution);
		content = (LinearLayout) findViewById(R.id.content);
		btnreturn = (ImageView) findViewById(R.id.btnreturn);
		contribution = getResources().getStringArray(R.array.contribution);
		for (int i = 0; i < contribution.length; i++) {
			TextView temText = new TextView(this);
			temText.setText(contribution[i]);
			temText.setPadding(5, 5, 5, 5);
			content.addView(temText);
		}
		// // btnreturn = (ImageView) findViewById(R.id.btnreturn);
		// // TextView ruleText = (TextView) findViewById(R.id.ruleText);
		// // TextView winText = (TextView) findViewById(R.id.winText);
		// ruleText.setText(GameRule);
		// winText.setText(Vrequir);
		// btnreturn.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// finish();
		// }
		// });
		btnreturn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoundPlayer.playball();
				finish();
			}
		});
	}
}
