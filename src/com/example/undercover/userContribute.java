package com.example.undercover;

import http.PublishHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.undercover.view.MyAdapter;
import com.example.undercover.view.MyAdapter.Publish;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class userContribute extends BaseActivity {
	private Button AddTureBt;
	private Button AddAdvenBt;
	private ListView myList;
	//private Button userConSend;
	//private EditText userConTextField; 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercontribute);
		
		AddTureBt = (Button)findViewById(R.id.addcontribute);
		AddAdvenBt = (Button) findViewById(R.id.finish);
		myList = (ListView) findViewById(R.id.myList);
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
//		updateMessage(null);
	}
	
	
	/**
	 * 取得所有新闻
	 */
	protected void getAllPublish(int page) {
		PublishHandler publishHandler = new PublishHandler(this);
		publishHandler.getAllPublish(page);
	}
	
	/* 处理回调方法
	 * @see com.example.undercover.BaseActivity#MessageCallBack(org.json.JSONObject)
	 */
	public void MessageCallBack(JSONObject jsonobj,String cmd) {
		super.MessageCallBack(jsonobj,cmd);
		if(cmd.equals(ConstantControl.SHOW_PUBLISH_ALL))
		{
			try{
				String temstr=jsonobj.getString("data");
//				temstr=String.
				JSONArray content=new JSONArray(temstr);
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
	protected void updateMessage(final JSONArray obj) {
		
		List<Publish> temPubs = new ArrayList<Publish>();
		for (int i = 0; i < obj.length(); i++) {
			try {
				JSONObject temobj = obj.getJSONObject(i);
				temPubs.add(new Publish(temobj.getInt("id"), temobj.getString("gameuid"),
						temobj.getString("content")));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		MyAdapter adapter = new MyAdapter(userContribute.this,temPubs,this.getUid()) ;
		adapter.setCallBack(this);
		myList.setAdapter(adapter);
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


