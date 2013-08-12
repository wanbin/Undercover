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
		txtTitle.setText("【谁是卧底】1.04版本");
		agent = new FeedbackAgent(this);
		String emailaddr = this.getString(R.string.emailaddr);
		txtContent
				.setText("意见及建议："
						+ emailaddr
						+ "\n\n\nv1.04版本新功能：\n\n1.修改界面效果\n2.完善游戏惩罚措施\n3.优化点我小游戏\n4.修改选择人数及卧底数逻辑");
		btnreturn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
