package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;

/**
 * @author liuchunlong 谁是卧底 游戏简介
 */

public class ContributionActivity extends BaseActivity {
	private LinearLayout content;
	private String[] contribution;
	private TextView email;
	private Button btnfb;
	private FeedbackAgent agent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_punish);
		initBtnBack(R.id.btnback);
		initShareBtn();
		content = (LinearLayout) findViewById(R.id.content);
		btnfb = (Button) findViewById(R.id.btnfb);
		email = (TextView) findViewById(R.id.txtEmail);
		contribution = getResources().getStringArray(R.array.contribution);
		agent = new FeedbackAgent(this);

		for (int i = 0; i < contribution.length; i++) {
			TextView temText = new TextView(this);
			temText.setText(contribution[i]);
			if (i % 2 != 0) {
				temText.setPadding(35, 5, 5, 5);
			} else {
				temText.setPadding(5, 5, 5, 5);
			}
			content.addView(temText);
		}

		String emailaddr = this.getString(R.string.emailaddr);
		String suggest = getResources().getString(R.string.suggest);
		email.setText(suggest + emailaddr);
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
		btnfb.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoundPlayer.playball();
				agent.startFeedbackActivity();
			}
		});
	}
}
