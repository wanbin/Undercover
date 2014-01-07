package com.example.undercover;

import http.PublishHandler;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class editContributeActivity extends BaseActivity {
	private ImageView send;
	private ImageView cancle;
	private EditText textCotent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcontribute);

		send = (ImageView) findViewById(R.id.send);
		cancle = (ImageView) findViewById(R.id.cancle);
		textCotent = (EditText) findViewById(R.id.editText1);
		send.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendPublish();
				finish();
			}
		});

		cancle.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * 发布新闻方法
	 */
	public void sendPublish() {
		PublishHandler publishHandler = new PublishHandler(this);
		publishHandler.sendPublish(textCotent.getText().toString(), 1);
	}

}
