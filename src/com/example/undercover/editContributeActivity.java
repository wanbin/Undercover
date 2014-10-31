package com.example.undercover;

import http.PublishHandler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class editContributeActivity extends BaseActivity {
	private Button send;
	private Button honest;
	private Button adventure;
	private Button actor;
	private EditText textCotent;
	private EditText textUsername;
	private CheckBox checkImport;
	private int sign=1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcontribute);
		initBtnBack(R.id.btnback);
		honest = (Button) findViewById(R.id.honest);
		adventure = (Button) findViewById(R.id.adventure);
		actor = (Button) findViewById(R.id.actor);
		send = (Button) findViewById(R.id.btnsend);
		
		textCotent = (EditText) findViewById(R.id.editText1);
		textUsername = (EditText) findViewById(R.id.editUserName);
		checkImport = (CheckBox) findViewById(R.id.checkImport);
		
		
		setBtnBlue(honest);
		setBtnGray(actor);
		setBtnGray(adventure);
		setBtnGreen(send);
		honest.setClickable(false);
		
		honest.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setBtnBlue(honest);
				setBtnGray(actor);
				setBtnGray(adventure);
				honest.setClickable(false);
				adventure.setClickable(true);
				actor.setClickable(true);
				sign=1;
			}
			
		});
		adventure.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setBtnBlue(adventure);
				setBtnGray(honest);
				setBtnGray(actor);
				honest.setClickable(true);
				adventure.setClickable(false);
				actor.setClickable(true);
				sign=2;
			}
			
		});
		actor.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setBtnBlue(actor);
				setBtnGray(honest);
				setBtnGray(adventure);
				honest.setClickable(true);
				adventure.setClickable(true);
				actor.setClickable(false);
				sign=3;
			}
			
		});
		
		
		
		send.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				String content = textCotent.getText().toString();
				String username = textUsername.getText().toString();
				if(textCotent.getText().length()!=0)
						{
						
				//判断content的内容是否为控制
				if (sendPublish(content,username)) {
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
	public boolean sendPublish(String content,String username) {
		PublishHandler publishHandler = new PublishHandler(this);
		return publishHandler.sendPublish(content,username, sign);
	}

//	public void sendRandom() {
//		List<String> array = new ArrayList<String>();
//		PublishHandler publishHandler = new PublishHandler(this);
//		for (int i = 0; i < 2000; i++) {
//			String tem = getRandomMaoxian("start");
//			if (array.contains(tem)) {
//				continue;
//			} else {
//				publishHandler.sendPublish(tem,username, 2);
//			}
//		}
//	}
}
