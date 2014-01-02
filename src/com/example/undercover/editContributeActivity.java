package com.example.undercover;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class editContributeActivity extends BaseActivity {
	private Button send;
	private Button cancle;
	private EditText textCotent;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcontribute);
		
		send = (Button) findViewById(R.id.send);
		cancle = (Button) findViewById(R.id.cancle);
		textCotent = (EditText) findViewById(R.id.editText1);
		send.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendPublish(textCotent.getText().toString(),1);
				
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
	 * 发布新闻
	 */
	protected void sendPublish(String content, int type) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("content", content);
			obj.put("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.getHttpRequest(obj, ConstantControl.SEND_PUBLISH_ALL);
	}
}
