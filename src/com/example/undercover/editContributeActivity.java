package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

import http.PublishHandler;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class editContributeActivity extends BaseActivity {
	private Button send;
	private EditText textCotent;
	private CheckBox checkImport;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcontribute);
		initBtnBack(R.id.btnback);
		send = (Button) findViewById(R.id.btnsend);
		textCotent = (EditText) findViewById(R.id.editText1);
		checkImport = (CheckBox) findViewById(R.id.checkImport);
		setBtnGreen(send);
		send.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				String content = textCotent.getText().toString();
				if(textCotent.getText().length()!=0)
						{
						
				//判断content的内容是否为控制
				if (sendPublish(content)) {
					if (checkImport.isChecked()) {
						addDamaoxian(content);
					}
					finish();
				}
				
				
//				 sendRandom();

			} else showEditContent();	
				
			}
			
		});

	}
	// 弹出提示
	void showEditContent (){
	Toast.makeText(this,"<Please edit the content!!>", Toast.LENGTH_SHORT).show();
	}
	/**
	 * 发布新闻方法
	 */
	public boolean sendPublish(String content) {
		PublishHandler publishHandler = new PublishHandler(this);
		return publishHandler.sendPublish(content, 1);
	}

	public void sendRandom() {
		List<String> array = new ArrayList<String>();
		PublishHandler publishHandler = new PublishHandler(this);
		for (int i = 0; i < 2000; i++) {
			String tem = getRandomMaoxian("start");
			if (array.contains(tem)) {
				continue;
			} else {
				publishHandler.sendPublish(tem, 2);
			}
		}
	}
}
