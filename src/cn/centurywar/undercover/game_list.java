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
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class game_list extends BaseActivity {
//	WebView urlPage;
	TextView txtTitle;
	ListView listView;
	TextView moreTextView;
	LinearLayout loadProgressBar;
	
	List<GameContent> temPubs ;
	GameAdapter adapter;
	   //分页加载的数据的数量
    private int nowpage=1;
//    private final int pageType=1;
    
	//1是游戏，2是帮助
	int showtype;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list2);
		txtTitle=(TextView)this.findViewById(R.id.txtTitle);
		listView=(ListView)this.findViewById(R.id.gamelist);

		txtTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getGame(1);
				temPubs.clear();
			}
		});
		showtype=getIntent().getIntExtra("showtype",1);
		temPubs = new ArrayList<GameContent>();
		
		addPageMore();
		
		adapter= new GameAdapter(game_list.this, temPubs,
				this.getUid());
		adapter.setCallBack(game_list.this);
		listView.setAdapter(adapter);
		getGame(1);
	}
	protected void getGame(int page){
		nowpage=page;
		txtTitle.setText("正在获取列表");
		PublishHandler publishHandler = new PublishHandler(this);
		publishHandler.getGameList(page,showtype);
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
		mIntent.putExtra("gameid", gameid+"");
		startActivity(mIntent);
	}
	
	protected void updateMessage(JSONArray content){
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
		txtTitle.setText("成功获取"+temPubs.size()+"条信息[点击刷新]");
		adapter.notifyDataSetChanged();
        //显示进度条
		moreTextView.setText("加载更多");
	}

	/**
     * 在ListView中添加"加载更多"
     */
    private void addPageMore(){
        View view=LayoutInflater.from(this).inflate(R.layout.list_page_load, null);
        moreTextView=(TextView)view.findViewById(R.id.more_id);
        moreTextView.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏"加载更多"
                moreTextView.setText("正在加载");
                //显示进度条
                nowpage++;
            	getGame(nowpage);
            }
        });
        listView.addFooterView(view);
    }
}
