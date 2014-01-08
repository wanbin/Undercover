package com.example.undercover;

import http.PublishHandler;
import http.UserHandler;

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

public class HttpMain extends BaseActivity {
	private Button btnChange;
	private Button btnSay;
	private ListView myList;
	//private Button userConSend;
	//private EditText userConTextField; 
	
	/* (non-Javadoc)
	 * @see com.example.undercover.BaseActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercontribute);
		initBtnBack(R.id.btnback);
		btnChange = (Button)findViewById(R.id.btnChange);
		btnSay = (Button) findViewById(R.id.btnSay);
		myList = (ListView) findViewById(R.id.myList);
		//userConSend = (Button) findViewById(R.id.userConSend);
		//userConTextField = (EditText) findViewById(R.id.userConTextField);
		setBtnGreen(btnChange);
		setBtnBlue(btnSay);
		
		//添加真心话按钮
		btnSay.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(HttpMain.this, editContributeActivity.class);
				startActivity(intentGo);
			}
		});
		
		//换一批
		btnChange.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// finish();
				if (isGm == 1) {
					getAllPublishShenHe(0);
				} else {
					getAllPublish(0);
				}

			}
		});
		
		if (!detect(HttpMain.this)) {
			ToastMessageLong("当前网络不可用");
		}
		getAllPublish(0);
		getUserInfo();
//		updateMessage(null);
	}
	
	
	/**
	 * 取得所有新闻
	 */
	protected void getAllPublish(int page) {
		PublishHandler publishHandler = new PublishHandler(this);
		publishHandler.getAllPublish(page);
	}
	
	/**
	 * 取得所有需要审核新闻
	 */
	protected void getAllPublishShenHe(int page) {
		PublishHandler publishHandler = new PublishHandler(this);
		publishHandler.getAllPublishNeedShenhe(page);
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
	 * 取得用户信息，可以判断用户当前的身份，是否显示待审核的词汇
	 */
	protected void getUserInfo() {
		UserHandler userHandler = new UserHandler(this);
		userHandler.getUserInfo(getUid());
	}

	/**
	 * update message
	 * 
	 * @param obj
	 */
	protected void updateMessage(final JSONArray obj) {
		List<Publish> temPubs = new ArrayList<Publish>();
		for (int i = 0; i < obj.length(); i++) {
			try {
				JSONObject temobj = obj.getJSONObject(i);
				if(temobj.getInt("id")==2761)
				{
					int da=1;
					da++;
				}
				//在这里把从网络传回来的参数给初始化为publish实例，并加到list里面
				temPubs.add(new Publish(temobj.getInt("id"), temobj
						.getString("gameuid"), temobj.getString("content"),
						temobj.getInt("like"), temobj.getInt("dislike")
						, temobj.getBoolean("liked"),
						temobj.getBoolean("disliked"), temobj.getBoolean("collected"), temobj.getString("sendtime"), temobj.getInt("type")));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		MyAdapter adapter = new MyAdapter(HttpMain.this, temPubs,
				this.getUid());
		adapter.setCallBack(this);
		adapter.setGM(isGm);
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


