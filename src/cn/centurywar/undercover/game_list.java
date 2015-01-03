package cn.centurywar.undercover;

import http.PublishHandler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.centurywar.undercover.view.GameAdapter;
import cn.centurywar.undercover.view.GameAdapter.GameContent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

public class game_list extends BaseActivity {
//	WebView urlPage;
	TextView txtTitle;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list2);
		txtTitle=(TextView)this.findViewById(R.id.txtTitle);
		listView=(ListView)this.findViewById(R.id.gamelist);
//		urlPage=(WebView)this.findViewById(R.id.urlpage);
//		String gameType=getIntent().getStringExtra("type");
//		if(gameType!=null&&gameType.equals("newGame")){
//			String url=getFromObject("newgameurl");
//			urlPage.loadUrl(url+"?showpage=gamenow&uid="+getUid());
//		}else{
//			urlPage.loadUrl("http://www.centurywar.cn/?cat=2&uid="+getUid());
//		}
		
		
		txtTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getAllGame();
			}
		});
		
		getAllGame();
	}
	protected void getAllGame(){
		txtTitle.setText("正在获取游戏列表");
		PublishHandler publishHandler = new PublishHandler(this);
		publishHandler.getGameList(1);
	}
	
	@Override
	public void MessageCallBack(JSONObject jsonobj,String cmd) {
		super.MessageCallBack(jsonobj,cmd);
		if(cmd.equals(ConstantControl.GAME_LIST))
		{
			try{
				String temstr=jsonobj.getString("data");
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
		if(cmd.equals(ConstantControl.GAME_LIST))
		{
			try{
				txtTitle.setText("获取失败，请重试[点击刷新]");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void clickGame(int gameid) {
		Intent mIntent = new Intent();
		mIntent.setClass(game_list.this, game_content.class);
		mIntent.putExtra("gameid", gameid);
		startActivity(mIntent);
	}
	
	protected void updateMessage(JSONArray content){
		txtTitle.setText("成功获取"+content.length()+"条信息[点击刷新]");
		List<GameContent> temPubs = new ArrayList<GameContent>();
		for (int m = 0; m < content.length(); m++) {
			try {
				JSONObject tem=content.getJSONObject(m);
				//在这里把从网络传回来的参数给初始化为publish实例，并加到list里面
				temPubs.add(new GameContent(tem.getInt("_id"),tem.getString("homeimg"),tem.getString("title"),"",""));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		GameAdapter adapter= new GameAdapter(game_list.this, temPubs,
				this.getUid());
		adapter.setCallBack(game_list.this);
		listView.setAdapter(adapter);
	}
}
