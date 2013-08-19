package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;

public class MakeActivity extends BaseActivity {
	private ImageView btnreturn;
	private TextView txtContent;
	private TextView txtTitle;
	private Button btnfb;
	private FeedbackAgent agent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make);
		txtContent = (TextView) findViewById(R.id.txtContent);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		btnreturn = (ImageView) findViewById(R.id.btnreturn);
		btnfb = (Button) findViewById(R.id.btnfb);
		txtTitle.setText("【谁是卧底】1.06版本");
		agent = new FeedbackAgent(this);
		String emailaddr = this.getString(R.string.emailaddr);
		txtContent
				.setText("意见及建议："
						+ emailaddr
						+ "\n\n\nv1.06版本新功能：\n\n1.添加好玩有趣的音效\n2.优化背景页面\n3.把【有胆量就问】节奏加快\n4.修改了上版本中的BUG");
		btnreturn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoundPlayer.playball();
				finish();
			}
		});

		btnfb.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				agent.startFeedbackActivity();
			}
		});

	}


}
