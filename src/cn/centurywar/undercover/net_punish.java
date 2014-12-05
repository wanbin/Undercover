package cn.centurywar.undercover;

import http.PublishHandler;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.centurywar.undercover.view.MyAdapter;
import cn.centurywar.undercover.view.MyAdapter.Publish;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

public class net_punish extends BaseActivity {
	private Button btnChange;
	private Button btnSay;
	private ListView myList;
	//private Button userConSend;
	//private EditText userConTextField; 
	
	/* (non-Javadoc)
	 * @see cn.centurywar.undercover.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_punish);
//		initBtnBack(R.id.btnback);
		btnChange = (Button)findViewById(R.id.btnChange);
		btnSay = (Button) findViewById(R.id.btnSay);
		myList = (ListView) findViewById(R.id.myList);
		//userConSend = (Button) findViewById(R.id.userConSend);
		//userConTextField = (EditText) findViewById(R.id.userConTextField);
		//添加真心话按钮
		btnSay.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				punishAdd("");
			}
		});
		
		//换一批
		btnChange.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// finish();
				uMengClick("click_reflash");
				getAllPublish(0);

			}
		});
		

		if (!detect(net_punish.this)) {
			ToastMessageLong("当前网络不可用");
		}

		getAllPublish(0);
//		updateMessage(null);
	}
	
	public void punishAdd(String content){
		Intent intentGo = new Intent();
		intentGo.setClass(net_punish.this, net_punish_add.class);
		intentGo.putExtra("content", content);
		startActivity(intentGo);
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
	 * @see cn.centurywar.undercover.BaseActivity#MessageCallBack(org.json.JSONObject)
	 */
	@Override
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
	
	
	@Override
	public void MessageCallBackWrong(String cmd) {
		super.MessageCallBackWrong(cmd);
		if(cmd.equals(ConstantControl.SHOW_PUBLISH_ALL))
		{
			try{
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
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
				//在这里把从网络传回来的参数给初始化为publish实例，并加到list里面
				temPubs.add(new Publish(temobj.getInt("id"), temobj
						.getString("username"), temobj.getString("content"),
						temobj.getInt("like"), temobj.getInt("dislike")
						, temobj.getBoolean("liked"),
						temobj.getBoolean("disliked"), temobj.getInt("type")));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		MyAdapter adapter = new MyAdapter(net_punish.this, temPubs,
				this.getUid());
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


