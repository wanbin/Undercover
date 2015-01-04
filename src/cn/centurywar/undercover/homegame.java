package cn.centurywar.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.centurywar.undercover.view.GameAdapter;
import cn.centurywar.undercover.view.GameAdapter.GameContent;
import cn.centurywar.undercover.view.MailAdapter;
import cn.centurywar.undercover.view.MailAdapter.MailUser;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
	// 把需要滑动的页卡添加到这个list中
	private List<View> viewList;
	// viewPager 滑动
	private ViewPager viewPager;
		
	// 将小圆点ImageView放入该List
	private ImageView[] imageViews;
		
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
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Welcome 的Layout

		viewList = new ArrayList<View>();
		
		
		
		String[] gamename={"谁是卧底","杀人游戏","真心话大冒险","我们都爱演","有胆量就点","有胆量就转","疯狂挤数字","大家来抽签","幸运转盘","疯狂猜词"};
		String[] gamepeople={
				"（4~12人）",
				"（6~16人）",
				"（惩罚游戏）",
				"（惩罚游戏）",
				"（2人及以上）",
				"（2人及以上）",
				"（2人及以上）",
				"（2人及以上）",
				"（聚会游戏、酒桌游戏）",
				""
				};
		String[] des={
				"找出卧底",
				"（6~16人）",
				"（惩罚游戏）",
				"（惩罚游戏）",
				"（2人及以上）",
				"（2人及以上）",
				"（2人及以上）",
				"（2人及以上）",
				"（聚会游戏、酒桌游戏）",
				""
				};
		int gameCount=gamename.length;
		imageViews = new ImageView[gameCount+1];
		
		int[] gameId={
				ConstantControl.GAME_UNDERCOVER,
				ConstantControl.GAME_KILLER,
				ConstantControl.GAME_PUNISH,
				ConstantControl.GAME_ACTION,
				ConstantControl.GAME_CLICK,
				ConstantControl.GAME_CIRCLE,
				ConstantControl.GAME_PUSH,
				ConstantControl.GAME_DRAW,
				ConstantControl.GAME_ZHUANG,
				ConstantControl.GAME_CAI
		};
 		
		setContentView(R.layout.game_list);
		
		ListView listView=(ListView)this.findViewById(R.id.gamelist);
		frameLayout=(FrameLayout)this.findViewById(R.id.frameLayout);
		
		
		
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
				getUserInfo();
			}
		});
		
		frameLayout.addView(ad);
		ad.setVisibility(View.INVISIBLE);
		
		
		urlBtn=btn;
		urlText=txtName;
		
		
		List<GameContent> temPubs = new ArrayList<GameContent>();
		for (int m = 0; m < gamename.length; m++) {
			try {
				//在这里把从网络传回来的参数给初始化为publish实例，并加到list里面
				temPubs.add(new GameContent(gameId[m],"",gamename[m],gamepeople[m],""));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		GameAdapter adapter= new GameAdapter(homegame.this, temPubs,
				this.getUid());
		adapter.setCallBack(homegame.this);
		listView.setAdapter(adapter);
        
        getUserInfo();
        
        
        
        //判断是不是本版本第一次打开游戏，如果是的话，开用户引导
        
        String checkisfirst=getFromObject("guide_"+getVersion());
        if(checkisfirst.length()==0){
            Intent intentGo = new Intent();
    		intentGo.setClass(homegame.this, homeguide.class);
    		startActivity(intentGo);
    		setToObject("guide_"+getVersion(),"guide");
        }
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
				updateNewGame(newgamename,newgameimage,newgameid);
				
				setToObject("username", username);
				setToObjectInt("gameuid", gameuid);
				setToObject("photo", photo);
				
//				setToObject("newgameurl", newgameurl);
				
				if (obj.has("mail")) {
					JSONArray mailArr = obj.getJSONArray("mail");
					new AlertDialog.Builder(this)   
					.setTitle("有新消息")  
					.setMessage(mailArr.getJSONObject(0).getString("content"))  
					.setPositiveButton("查看更多",  
							new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialoginterface, int i){
                            //按钮事件
                        	Intent intentGo = new Intent();
                    		intentGo.setClass(homegame.this, mail_list.class);
                    		startActivity(intentGo);
                        }
						})  
					.setNegativeButton("关闭", null)  
					.show(); 
//					textMail.setText("通知："+ mailArr.getJSONObject(0).getString("content"));
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
	
	
	public void updateNewGame(String newgamename,String newgameimage,final int gameid){
		urlText.setText(newgamename);
		ImageFromUrl(urlBtn,getImgBanner(newgameimage),R.color.WRITE);
		ad.setVisibility(View.VISIBLE);
		
		//显示是否显示NEW
		if (checkNetGameIsNew(gameid)) {
			imgNew.setVisibility(View.VISIBLE);
		} else {
			imgNew.setVisibility(View.INVISIBLE);
		}
		
		urlBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.setClass(homegame.this, game_content.class);
				mIntent.putExtra("gameid", gameid);
				startActivity(mIntent);
				imgNew.setVisibility(View.INVISIBLE);
			}
		});
		
	}
}
