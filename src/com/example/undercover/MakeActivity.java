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
	private String Version;
	private String suggest;
	private String suggest1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make);
		Version = getResources().getString(R.string.Version);
		suggest = getResources().getString(R.string.suggest);
		suggest1 = getResources().getString(R.string.suggest1);
		txtContent = (TextView) findViewById(R.id.txtContent);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		btnreturn = (ImageView) findViewById(R.id.btnreturn);
		btnfb = (Button) findViewById(R.id.btnfb);
		txtTitle.setText(Version);
		agent = new FeedbackAgent(this);
		String emailaddr = this.getString(R.string.emailaddr);
		txtContent
				.setText(suggest+ emailaddr
						+ suggest1);
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
				SoundPlayer.playball();
				agent.startFeedbackActivity();
			}
		});

	}


}
