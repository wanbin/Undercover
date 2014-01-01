package com.example.undercover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.undercover.view.PublishContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
	protected void updateMessage(JSONArray obj) {
		String[] names = new String[] { "wanbin", "wanbin", "wanbin" };
		String[] desc = new String[] { "wanbinde", "wanbinde", "wanbinde" };
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		
		
		try {
			for (int i = 0; i < obj.length(); i++) {
				JSONObject temobj = obj.getJSONObject(i);
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("txtName", temobj.getString("gameuid"));
				listItem.put("txtContent",temobj.getString("content"));
				listItem.put("id",temobj.getInt("id"));
				listItems.add(listItem);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		SimpleAdapter temAdapter = new SimpleAdapter(this, listItems,
				R.layout.activity_sub_usercontribute, new String[] { "txtName",
						"txtContent" }, new int[] { R.id.txtName,
						R.id.txtContent });
		myList.setAdapter(temAdapter);
		
//		
//		try {
//			for (int i = 0; i < obj.length(); i++) {
//				JSONObject temobj = obj.getJSONObject(i);
//				String content = temobj.getString("content");
//				int like = temobj.getInt("like");
//				int dislike = temobj.getInt("dislike");
//				int id = temobj.getInt("id");
////				PublishContent con = new PublishContent(this);
////				con.init(2, "wanbin", content, "2013", id);
////				textLayout.addView(con);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

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


