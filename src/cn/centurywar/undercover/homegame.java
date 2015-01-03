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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
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
    private TextView urlText;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater mInflater = getLayoutInflater();
		// Welcome 的Layout

		viewList = new ArrayList<View>();
		
		
		
		String[] gamename={"谁是卧底","杀人游戏","真心话大冒险","我们都爱演","有胆量就点","有胆量就转","疯狂挤数字","大家来抽签","幸运转盘","本周热门"};
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
				ConstantControl.GAME_RECOMMEND
		};
 		
		int[] imageId={R.drawable.logo_11_2x,R.drawable.logo_12_2x,R.drawable.logo_13_2x,R.drawable.logo_19,R.drawable.logo_14_2x,R.drawable.logo_15_2x,R.drawable.logo_16_2x,R.drawable.logo_17_2x,R.drawable.logo_18_2x,R.drawable.week_recom_2x};
  		
		for(int i=-1;i<gameCount;i++){
			//-1显示缩略图
			if (i < 0) {
				View welcomeView = mInflater.inflate(R.layout.game_list, null);
				ListView listView=(ListView)welcomeView.findViewById(R.id.gamelist);
				
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
				viewList.add(welcomeView);
				
			} else {
				
				View welcomeView = mInflater.inflate(R.layout.game_select, null);
				ImageView btn = (ImageView) welcomeView
						.findViewById(R.id.image);
				ImageView imgNew = (ImageView) welcomeView
						.findViewById(R.id.imgNew);
				TextView txtName = (TextView) welcomeView
						.findViewById(R.id.txtName);
				txtName.setText(gamename[i]);

				if(i==gameCount-1){
					urlBtn=btn;
					urlText=txtName;
				}
				
				if (!checkGameIsNew(gameId[i])) {
					imgNew.setVisibility(View.GONE);
				}

				String imageUri = "drawable://" + imageId[i];
				ImageFromLocal(btn, imageUri);

				final int temgameid=gameId[i];
				btn.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						cleanStatus();
						clickGame(temgameid);
					}
				});
				viewList.add(welcomeView);
			}
		}
		
		
		
		mainContainer = (ViewGroup) mInflater.inflate(
				R.layout.activity_select_game, null);
		pointContainer = (ViewGroup) mainContainer
				.findViewById(R.id.pointGroup);
		
		viewPager = (ViewPager) mainContainer.findViewById(R.id.viewpager);
		
	
		// 将小圆点放到Layout中
		for (int i = 0; i < imageViews.length; i++) {
			ImageView image = new ImageView(homegame.this);
			image.setLayoutParams(new LayoutParams(10, 10));
	
			RelativeLayout temview=new RelativeLayout(homegame.this);
			temview.setLayoutParams(new LayoutParams(20, 10));
			image.setPadding(5, 0, 5, 0);
			temview.addView(image);
			// 默认为第一小圆点
			if (i==0) {
				image.setBackgroundResource(R.drawable.red_ball_2x);
			}else{
				image.setBackgroundResource(R.drawable.gray_ball_2x);
			}
			imageViews[i] = image;
			pointContainer.addView(temview);
		}
		pointContainer.setPadding(5, 0, 5, 20);
		
		
		viewPager.setAdapter(new PageAdapterGame());
        viewPager.setOnPageChangeListener(new GamePageChangeListener());
        viewPager.setCurrentItem(0);
        setContentView(mainContainer);
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
	
	
//	ConstantControl.GAME_UNDERCOVER,
//	ConstantControl.GAME_KILLER,
//	ConstantControl.GAME_PUNISH,
//	ConstantControl.GAME_ACTION,
//	ConstantControl.GAME_CLICK,
//	ConstantControl.GAME_CIRCLE,
//	ConstantControl.GAME_PUSH,
//	ConstantControl.GAME_DRAW,
//	ConstantControl.GAME_ZHUANG,
//	ConstantControl.GAME_RECOMMEND
	
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
		} else {
			mIntent.setClass(homegame.this, game_list.class);
			mIntent.putExtra("type", "newGame");
		}
		startActivity(mIntent);
	}
	
	
	// 滑动页面的的适配器啊，主要把ViewList中的View传给ViewPager
    private class PageAdapterGame extends PagerAdapter {  
  	  
        @Override  
        public int getCount() {  
            return viewList.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View view, int position, Object object) {  
            // TODO Auto-generated method stub  
            ((ViewPager) view).removeView(viewList.get(position));  
        }  
  
        @Override  
        public Object instantiateItem(View view, int position) {  
            // TODO Auto-generated method stub  
            ((ViewPager) view).addView(viewList.get(position));  
            return viewList.get(position);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    } 
    
    
	// 监听页面切换，切换时把小圆点变更
    class GamePageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			for (int i = 0; i < imageViews.length; i++) {
				if (i!=position) {
					imageViews[i].setBackgroundResource(R.drawable.gray_ball_2x);
				}
				imageViews[position].setBackgroundResource(R.drawable.red_ball_2x);
			}
		}
    	
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
				
				
				String newgameimage=obj.getString("newgameimage");
				String newgamename=obj.getString("newgamename");
				String newgame=obj.getString("newgame");
				String newgameurl=obj.getString("newgameurl");
				
				updateNewGame(newgamename,newgameimage);
				
				setToObject("username", username);
				setToObjectInt("gameuid", gameuid);
				setToObject("photo", photo);
				
				setToObject("newgameurl", newgameurl);
				
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
	
	public void updateNewGame(String newgamename,String newgameimage){
//		View temview=viewList.get(viewList.size()-1);
//		ImageView btn=(ImageView)temview.findViewById(R.id.image);
//		TextView txtName=(TextView)temview.findViewById(R.id.txtName);
		urlText.setText(newgamename);
		
		ImageFromUrl(urlBtn,newgameimage,R.drawable.week_recom_2x);
	}
}
