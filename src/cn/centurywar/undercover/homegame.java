package cn.centurywar.undercover;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.centurywar.undercover.view.GameAdapter;
import cn.centurywar.undercover.view.GameAdapter.GameContent;
import cn.centurywar.undercover.view.MailAdapter;
import cn.centurywar.undercover.view.MailAdapter.MailUser;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class homegame extends BaseActivity {
	// viewPager 滑动
	private ViewPager viewPager;
		
		
	// 包裹小圆点的LinearLayout
    private ViewGroup pointContainer;
    
	// 包含滑动ViewPager的Layout
    private ViewGroup mainContainer;
    
    private ImageView urlBtn;
    private ImageView imgNew;
    private TextView urlText;
    
    private FrameLayout frameLayout;
    private View ad;
    
    private TextView txtTitle;
    private Button btnMail;
    
    List<GameContent> temPubs;
    GameAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		
		ListView listView=(ListView)this.findViewById(R.id.gamelist);
		frameLayout=(FrameLayout)this.findViewById(R.id.frameLayout);
		btnMail=(Button)this.findViewById(R.id.btnMail);
		btnMail.setVisibility(View.INVISIBLE);
		
		btnMail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentGo = new Intent();
        		intentGo.setClass(homegame.this, mail_list.class);
        		startActivity(intentGo);
        		btnMail.setVisibility(View.INVISIBLE);
			}
		});

		
		LayoutInflater mInflater = getLayoutInflater();
		 ad = mInflater.inflate(R.layout.game_ad, null);
		ImageView btn = (ImageView) ad
				.findViewById(R.id.image);
		
		 imgNew = (ImageView) ad
				.findViewById(R.id.imgNew);
		TextView txtName = (TextView) ad
				.findViewById(R.id.txtName);
		txtTitle = (TextView) this.findViewById(R.id.txtTitle);
		
		
		txtTitle.setText("刷新用户信息");
		txtTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				willUserInfo();
			}
		});
		
		frameLayout.addView(ad);
		ad.setVisibility(View.INVISIBLE);
		urlBtn=btn;
		urlText=txtName;
		
		
		
		GameContent game1=new GameContent(ConstantControl.GAME_UNDERCOVER,"","谁是卧底","（4~12人）","");
		GameContent game2=new GameContent(ConstantControl.GAME_KILLER,"","杀人游戏","（4~12人）","");
		GameContent game3=new GameContent(ConstantControl.GAME_PUNISH,"","真心话大冒险","（4~12人）","");
		GameContent game4=new GameContent(ConstantControl.GAME_ACTION,"","我们都爱演","（4~12人）","");
		GameContent game5=new GameContent(ConstantControl.GAME_CLICK,"","有胆量就点","（4~12人）","");
		GameContent game6=new GameContent(ConstantControl.GAME_CIRCLE,"","有胆量就转","（4~12人）","");
		GameContent game7=new GameContent(ConstantControl.GAME_PUSH,"","疯狂挤数字","（4~12人）","");
		GameContent game8=new GameContent(ConstantControl.GAME_DRAW,"","大家来抽签","（4~12人）","");
		GameContent game9=new GameContent(ConstantControl.GAME_ZHUANG,"","幸运转盘","（4~12人）","");
		GameContent game10=new GameContent(ConstantControl.GAME_ZHUANG,"","疯狂猜词","（4~12人）","");
		
		temPubs = new ArrayList<GameContent>();
		temPubs.add(game1);
		temPubs.add(game2);
		temPubs.add(game3);
		temPubs.add(game4);
		temPubs.add(game5);
		temPubs.add(game6);
		temPubs.add(game7);
		temPubs.add(game8);
		temPubs.add(game9);
		temPubs.add(game10);
		
		
		adapter = new GameAdapter(homegame.this, temPubs, this.getUid());
		adapter.setCallBack(homegame.this);
		listView.setAdapter(adapter);
        
        //判断是不是本版本第一次打开游戏，如果是的话，开用户引导
        
        String checkisfirst=getFromObject("guide_"+getVersion());
        if(checkisfirst.length()==0){
            Intent intentGo = new Intent();
    		intentGo.setClass(homegame.this, homeguide.class);
    		startActivity(intentGo);
    		setToObject("guide_"+getVersion(),"guide");
        }
        
		Timer timer = new Timer();
		timer.schedule(timetask, 1000, 600000);
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			willUserInfo();
		}
	};
	
	// 传递时间
	private TimerTask timetask = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	
	private void willUserInfo(){
		txtTitle.setText("正在获取用户信息[点击刷新]");
		getUserInfo();
	}
	
	
	public void clickGame(int gameid) {
		Intent mIntent = new Intent();
		if (gameid == ConstantControl.GAME_UNDERCOVER) {
			mIntent.setClass(homegame.this, local_setting.class);
			setGameType("game_undercover");
		} else if (gameid == ConstantControl.GAME_KILLER) {
			mIntent.setClass(homegame.this, local_setting.class);
			setGameType("game_killer");
		} else if (gameid == ConstantControl.GAME_PUNISH) {
			mIntent.setClass(homegame.this, local_punish.class);
		} else if (gameid == ConstantControl.GAME_ACTION) {
			mIntent.setClass(homegame.this, local_act.class);
		} else if (gameid == ConstantControl.GAME_CLICK) {
			mIntent.setClass(homegame.this, local_click.class);
		} else if (gameid == ConstantControl.GAME_CIRCLE) {
			mIntent.setClass(homegame.this, local_bottle.class);
		} else if (gameid == ConstantControl.GAME_PUSH) {
			mIntent.setClass(homegame.this, local_push.class);
		} else if (gameid == ConstantControl.GAME_DRAW) {
			mIntent.setClass(homegame.this, local_draw.class);
		} else if (gameid == ConstantControl.GAME_ZHUANG) {
			mIntent.setClass(homegame.this, local_zhuan.class);
		}else if (gameid == ConstantControl.GAME_CAI) {
			mIntent.setClass(homegame.this, local_cai.class);
		}
		else {
			mIntent.setClass(homegame.this, game_list.class);
			mIntent.putExtra("type", "newGame");
		}
		startActivity(mIntent);
	}
	
	
	
	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.GET_USER_INFO)) {
			try {
				setUserInfo(new JSONObject(jsonobj.getString("data")));
				JSONObject obj = new JSONObject(jsonobj.getString("data"));
				//obj={"uid":"A0000043A574DC","username":"","gameuid":310,"time":1414514074,"newgameimage":"http:\/\/192.168.1.120\/CenturyServer\/www\/image\/recom_1.png","newgamename":"我爱我OR不要脸","_id":310,"newgame":1,"pushcount":"0","photo":"","channel":"ANDROID"}
				String username=obj.getString("username");
				int gameuid=obj.getInt("gameuid");
				String photo=obj.getString("photo");
				
				
				JSONObject newgamecontent=obj.getJSONObject("newgamecontent");
				String newgameimage=newgamecontent.getString("homeimg");
				String newgamename=newgamecontent.getString("title");
				int newgameid=newgamecontent.getInt("_id");
				
				//本周热门功能暂时省略
				updateNewGame(newgamename,newgameimage,newgameid,username);
				
				
				if (obj.has("local")) {
					// 本地游戏列表
					JSONArray local = obj.getJSONArray("local");
					updateLocal(local);
				}
				
				setToObject("username", username);
				setToObjectInt("gameuid", gameuid);
				setToObject("photo", photo);
				
				
//				setToObject("newgameurl", newgameurl);
				
				if (obj.has("mailcount")&&obj.getInt("mailcount")>0) {
					btnMail.setVisibility(View.VISIBLE);
					btnMail.setText("信件"+obj.getInt("mailcount"));
//					JSONArray mailArr = obj.getJSONArray("mail");
//					new AlertDialog.Builder(this)   
//					.setTitle("有新消息")  
//					.setMessage(mailArr.getJSONObject(0).getString("content"))  
//					.setPositiveButton("查看更多",  
//							new DialogInterface.OnClickListener(){
//                        public void onClick(DialogInterface dialoginterface, int i){
//                            //按钮事件
//                        	;
//                        }
//						})  
//					.setNegativeButton("关闭", null)  
//					.show(); 
//					textMail.setText("通知："+ mailArr.getJSONObject(0).getString("content"));
				}
				else{
					btnMail.setVisibility(View.INVISIBLE);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	@Override
	public void CallBackPublicCommandWrong( String cmd) {
		super.CallBackPublicCommandWrong( cmd);
		if (cmd.equals(ConstantControl.GET_USER_INFO)) {
			Log.v("error", "error");
		}
	}
	
	private void updateLocal(JSONArray localarr){
		temPubs.clear();
		for(int i=0;i<localarr.length();i++){
			try {
				JSONObject tem=localarr.getJSONObject(i);
				GameContent temgame=new GameContent(tem.getInt("gameid"),tem.getString("img"),tem.getString("name"),tem.getString("typename"),tem.getString("des"));
				temPubs.add(temgame);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		adapter.notifyDataSetChanged();
		
	}
	
	public void updateNewGame(String newgamename,String newgameimage,final int gameid,String username){
		if(username.length()>0){
			txtTitle.setText("欢迎您："+username+"[点击刷新]");
		}
		else{
			txtTitle.setText("欢迎您[点击刷新]");
		}
		urlText.setText(newgamename);
		ImageFromUrl(urlBtn,getImgBanner(newgameimage),R.color.WRITE);
		ad.setVisibility(View.VISIBLE);
		
		//显示是否显示NEW
		if (checkNetGameIsNew(gameid+"")) {
			imgNew.setVisibility(View.VISIBLE);
		} else {
			imgNew.setVisibility(View.INVISIBLE);
		}
		
		urlBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.setClass(homegame.this, game_content.class);
				mIntent.putExtra("gameid", gameid+"");
				startActivity(mIntent);
				imgNew.setVisibility(View.INVISIBLE);
			}
		});
		
	}
	
}
