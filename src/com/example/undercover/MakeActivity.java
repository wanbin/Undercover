package com.example.undercover;

import android.os.Bundle;
import android.widget.TextView;

public class MakeActivity extends BaseActivity {
	private TextView txtContent;
	private TextView txtTitle;
	private String Version;
	private String suggest;
	private String suggest1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make);
		initBtnBack(R.id.btnback);
		initShareBtn();
		Version = getResources().getString(R.string.Version);
		txtContent = (TextView) findViewById(R.id.txtContent);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText(Version);
		String emailaddr = this.getString(R.string.emailaddr);
		suggest = getResources().getString(R.string.suggest);
		suggest1 = getResources().getString(R.string.suggest1);
		txtContent
.setText(suggest + emailaddr + strFromId("connect") + suggest1);
	}


}
