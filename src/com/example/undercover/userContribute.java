package com.example.undercover;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class userContribute extends BaseActivity {
	private Button AddTureBt;
	private Button AddAdvenBt;
	//private Button userConSend;
	//private EditText userConTextField; 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercontribute);
		
		AddTureBt = (Button)findViewById(R.id.addcontribute);
		AddAdvenBt = (Button) findViewById(R.id.finish);
		//userConSend = (Button) findViewById(R.id.userConSend);
		//userConTextField = (EditText) findViewById(R.id.userConTextField);
		
		
		//添加真心话按钮
		AddTureBt.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(userContribute.this, editContributeActivity.class);
				startActivity(intentGo);
			}
		});
		//添加大冒险按钮
		AddAdvenBt.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//finish();
				getAllPublish(0);
			}
		});
		
	}
	
	
	/**
	 * 取得所有新闻
	 */
	protected void getAllPublish( int page) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.getHttpRequest(obj, ConstantControl.SHOW_PUBLISH_ALL);
	}
	
	/* 处理回调方法
	 * @see com.example.undercover.BaseActivity#MessageCallBack(org.json.JSONObject)
	 */
	protected void MessageCallBack(JSONObject jsonobj,String cmd) {
		super.MessageCallBack(jsonobj,cmd);
		if(cmd.equals(ConstantControl.SHOW_PUBLISH_ALL))
		{
			try{
				JSONArray content=jsonobj.getJSONArray("data");
				updateMessage(content);
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.print(jsonobj.toString());
		}
	}
	
	
	/**
	 * update message 
	 * @param obj
	 */
	protected void updateMessage(JSONArray obj){
		for(int i=0;i<obj.length();i++){
			
			
			
		}
		
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

	
	/**
	 * 初始化显示方格
	 * @param fram
	 * @param imageid
	 * @param userName
	 * @param content
	 * @param date
	 * @param messageId
	 */
	private void initHelp(FrameLayout fram, int imageid, String userName,
			String content, String date,int messageId) {		
		ImageView imageIcon = (ImageView) fram.findViewById(R.id.imageHelpIcon);
		imageIcon.setImageResource(imageid);
		
		TextView title = (TextView) fram.findViewById(R.id.txtName);
		title.setText(userName);
		TextView contentText = (TextView) fram.findViewById(R.id.txtContent);
		contentText.setText(content);
		TextView other = (TextView) fram.findViewById(R.id.txtTime);
		other.setText(date);

		
	}
}

