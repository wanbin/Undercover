package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class userContribute extends BaseActivity {
	private Button AddTureBt;
	private Button AddAdvenBt;
	private Button userConSend;
	private EditText userConTextField; 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercontribute);
		
		AddTureBt = (Button)findViewById(R.id.addTruth);
		AddAdvenBt = (Button) findViewById(R.id.addAdventure);
		userConSend = (Button) findViewById(R.id.userConSend);
		userConTextField = (EditText) findViewById(R.id.userConTextField);
		
		userConSend.setVisibility(View.INVISIBLE);
		userConTextField.setVisibility(View.INVISIBLE);
		
		//添加真心话按钮
		AddTureBt.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userConSend.setVisibility(View.VISIBLE);
				userConTextField.setVisibility(View.VISIBLE);
				AddAdvenBt.setVisibility(View.INVISIBLE);
				AddTureBt.setVisibility(View.INVISIBLE);
			}
		});
		//添加大冒险按钮
		AddAdvenBt.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userConSend.setVisibility(View.VISIBLE);
				userConTextField.setVisibility(View.VISIBLE);
				AddAdvenBt.setVisibility(View.INVISIBLE);
				AddTureBt.setVisibility(View.INVISIBLE);
				
			}
		});
		//发送用户编写的真心话大冒险
		userConSend.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * 上传大冒险
	 * @param content
	 */
	public void sendPublish(String content){
		
	}
	
	
	
	/**
	 * 上传真心话
	 * @param content
	 */
	public void sendTurns(String content){
		
	}

	/**
	 * 取得大冒险
	 * @param page
	 */
	public void getPublic(int page){
		
	}
	/**
	 * 取得真心话
	 * @param page
	 */
	public void getTures(int page){
		
	}
	
	/**
	 * 取得个人收藏
	 * @param page
	 */
	public void getUserCollect(int page){
		
	}
	
	
	/**
	 * 设置 1，收藏 ，2 喜欢，3，不喜欢
	 * @param page
	 */
	public void controlContent(int id ,int type){
		
	}

}

