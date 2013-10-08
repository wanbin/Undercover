package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;

public class SelectGameActivity extends BaseActivity {
	// viewPager 滑动
	private ViewPager viewPager;
	
	// 把需要滑动的页卡添加到这个list中
	private List<View> viewList;
	
	// 包裹小圆点的LinearLayout
    private ViewGroup pointContainer;
    
	// 包含滑动ViewPager的Layout
    private ViewGroup mainContainer;
    
	// 将小圆点ImageView放入该List
	private ImageView[] imageViews;
	private FeedbackAgent agent;
	private Button clickmeButton, circlemeButton, questionButton, weixinButton,
			gongxianbtn, guanyubtn, appmakerbButton, btnReStart,
			usercontribution, zhenxin, startButton, btnSound, btnfb;
	private CheckBox sound;
	private boolean soundon = true;
	private FrameLayout framPing, framClick, framTrue, frameAsk, frameKill,
			framePush;
	private LinearLayout scrollHelpContent;
	private ImageView pointleft, pointright;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater mInflater = getLayoutInflater();

		// Welcome 的Layout
		View welcomeView = mInflater.inflate(R.layout.activity_welcome, null);
		// 帮助页面的layout
		View helpView = mInflater.inflate(R.layout.undercover_content, null);
		// 更多游戏的Layout
		View moreView = mInflater.inflate(R.layout.activity_more, null);

		viewList = new ArrayList<View>();
		viewList.add(helpView);
		viewList.add(welcomeView);
		viewList.add(moreView);


		mainContainer = (ViewGroup) mInflater.inflate(
				R.layout.activity_select_game, null);
		pointContainer = (ViewGroup) mainContainer
				.findViewById(R.id.pointGroup);
		viewPager = (ViewPager) mainContainer.findViewById(R.id.viewpager);

		sound = (CheckBox) welcomeView.findViewById(R.id.sound);
		btnSound = (Button) welcomeView.findViewById(R.id.switchbtn);
		soundon = SoundPlayer.getSoundSt();

		setSwithSound(soundon);

		sound.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setSwithSound(isChecked);
			}
		});

		MobclickAgent.updateOnlineConfig(this);
        
        imageViews = new ImageView[viewList.size()];
        Log.d("imageView length", String.valueOf(imageViews.length));
		// 将小圆点放到Layout中
		for (int i = 0; i < imageViews.length; i++) {
			ImageView image = new ImageView(SelectGameActivity.this);
			image.setLayoutParams(new LayoutParams(55, 6));
			// image.setPadding(0, 20, 20, 0);
			// 默认为第一小圆点
			if (i==0) {
				image.setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				image.setBackgroundResource(R.drawable.page_indicator);
			}
			
			imageViews[i] = image;
			pointContainer.addView(image);
		}
        setContentView(mainContainer);
        
		// helpButton = (Button) helpView.findViewById(R.id.helpButton);
		// helpButton.setOnClickListener(new MyClickListener());
		startButton = (Button) welcomeView.findViewById(R.id.startButton);
        startButton.setOnClickListener(new MyClickListener());


		pointleft = (ImageView) welcomeView.findViewById(R.id.imagePointRight);
		pointright = (ImageView) welcomeView.findViewById(R.id.imagePointLeft);
		startMoveAnmi(pointleft, 1);
		startMoveAnmi(pointright, 2);

		btnReStart = (Button) welcomeView.findViewById(R.id.btnReStartLast);
		btnReStart.setOnClickListener(new MyClickListener());
		weixinButton = (Button) helpView.findViewById(R.id.btnweixin);
		weixinButton.setOnClickListener(new MyClickListener());
		framPing = (FrameLayout) moreView.findViewById(R.id.btnPing);
		frameAsk = (FrameLayout) moreView.findViewById(R.id.btnAsk);
		framTrue = (FrameLayout) moreView.findViewById(R.id.btnTrue);
		framClick = (FrameLayout) moreView.findViewById(R.id.btnClick);
		frameKill = (FrameLayout) moreView.findViewById(R.id.btnKill);
		framePush = (FrameLayout) moreView.findViewById(R.id.btnPush);
		
		

		scrollHelpContent = (LinearLayout) helpView
				.findViewById(R.id.linearContent);
		initFrame(framPing, strFromId("circleme"), 3, 6,
				R.drawable.icon_bottle, RotaryBottleActivity.class,
				"game_bottle", "bottle", 0);
		initFrame(frameAsk, strFromId("askme"), 4, 8, R.drawable.icon_ask,
				QuestionAnswer.class, "game_ask", "ask", 0);
		initFrame(framTrue, strFromId("truethings"), 2, 8,
				R.drawable.icon_true, PunishActivity.class,
				"game_zhenxinhua_damaoxian", "true", 0);
		initFrame(framClick, strFromId("clickme"), 2, 8, R.drawable.icon_click,
				random_50.class, "game_click", "click", 0);
		initFrame(frameKill, strFromId("txtKillerGameName"), 6, 12,
				R.drawable.icon_kill, Setting.class, "game_kill_select",
				"kill", 0);
		initFrame(framePush, strFromId("txtPush"), 2, 8, R.drawable.icon_push,
				Push.class, "game_push_select", "push", 0);


		String[] HelpConfig = { strFromId("app_name"),
				strFromId("txtKillerGameName"), strFromId("clickme"),
				strFromId("txtPush") };
		for (int i = 0; i < scrollHelpContent.getChildCount(); i++) {
			FrameLayout temFra = (FrameLayout) scrollHelpContent.getChildAt(i);
			if (temFra == null) {
				continue;
			}
			if (i > HelpConfig.length - 1) {
				temFra.setVisibility(View.GONE);
				continue;
			}
			if (HelpConfig[i].equals(strFromId("app_name"))) {
				initHelp(temFra, R.drawable.cerblue01, strFromId("app_name"),
						strFromId("GameRule"), "07/09/2013", Setting.class,
						"game_undercover", "undercover");
			} else if (HelpConfig[i].equals(strFromId("txtKillerGameName"))) {
				initHelp(temFra, R.drawable.ceryellow01,
						strFromId("txtKillerGameName"),
						strFromId("txtKillerRule"), "07/09/2013",
						Setting.class, "game_kill_select", "kill");
			} else if (HelpConfig[i].equals(strFromId("clickme"))) {
				initHelp(temFra, R.drawable.cerpink01, strFromId("clickme"),
						strFromId("clicksay"), "30/09/2013", random_50.class,
						"game_click", "click");
			} else if (HelpConfig[i].equals(strFromId("txtPush"))) {
				initHelp(temFra, R.drawable.cergray01, strFromId("txtPush"),
						strFromId("txtPushRule"), "06/10/2013", Push.class,
						"game_push_select", "push");
			}
		}

		gongxianbtn = (Button) helpView.findViewById(R.id.btnyonghu);
		gongxianbtn.setOnClickListener(new MyClickListener());
		guanyubtn = (Button) helpView.findViewById(R.id.btnguanyu);
		guanyubtn.setOnClickListener(new MyClickListener());


		btnfb = (Button) helpView.findViewById(R.id.btnfb);
		btnfb.setOnClickListener(new MyClickListener());
		// 用户贡献

		TextView temmore = (TextView) welcomeView.findViewById(R.id.txtTitle);
		temmore.setVisibility(View.VISIBLE);
		temmore.setText(strFromId("strwhoisundercover"));

		TextView temwelcome = (TextView) moreView.findViewById(R.id.txtTitle);
		temwelcome.setVisibility(View.VISIBLE);
		temwelcome.setText(strFromId("moregame"));

		TextView temhelp = (TextView) helpView.findViewById(R.id.txtTitle);
		temhelp.setVisibility(View.VISIBLE);
		temhelp.setText(strFromId("txtgamerolu"));

        viewPager.setAdapter(new MyPageAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setCurrentItem(1);

        
        setBtnPink(weixinButton);
		setBtnPink(btnfb);
		setBtnPink(guanyubtn);
		setBtnPink(gongxianbtn);
        setBtnGreen(startButton);
		setBtnBlue(btnReStart);
		btnSound.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				soundon = !soundon;
				setSwithSound(soundon);
			}
		});
		agent = new FeedbackAgent(this);


	}

	// kindtype 1.为新游戏，2，热门游戏
	private void initFrame(FrameLayout fram, String title, int min, int max,
			int imageid, final Class classname, final String umengclick,
			final String gamename, int kindtype) {
		// 点击游戏就把之前的状态清空
		TextView title2 = (TextView) fram.findViewById(R.id.txtTitle);
		ImageView imageIcon = (ImageView) fram.findViewById(R.id.imageIcon);
		imageIcon.setImageResource(imageid);
		Button btn = (Button) fram.findViewById(R.id.btnBg);
		setBtnMoreGame(btn);
		title2.setText(title);
		TextView titlePeople = (TextView) fram
				.findViewById(R.id.txtPeopleCount);
		titlePeople.setText("适合人数:" + min + "~" + max);
		ImageView imageType = (ImageView) fram.findViewById(R.id.imageTag);
		if (kindtype == 1) {
			imageType.setBackgroundResource(R.drawable.tag_new);
		} else if (kindtype == 2) {
			imageType.setBackgroundResource(R.drawable.tag_hot);
		} else {
			imageType.setVisibility(View.INVISIBLE);
		}

		if (classname != null) {
			btn.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					cleanStatus();
					Intent mIntent = new Intent();
					mIntent.setClass(SelectGameActivity.this, classname);
					startActivity(mIntent);
					uMengClick(umengclick);
					setGameType(gamename);
				}
			});
		}
	}
	private void initHelp(FrameLayout fram, int imageid, String helpTitle,
			String helpContent, String helpother, final Class classname,
			final String umengclick, final String gamename) {
		ImageView imageIcon = (ImageView) fram.findViewById(R.id.imageHelpIcon);
		imageIcon.setImageResource(imageid);
		TextView title = (TextView) fram.findViewById(R.id.txtHelpTitle);
		title.setText(helpTitle);
		TextView content = (TextView) fram.findViewById(R.id.txtHelpContent);
		content.setText(helpContent);
		TextView other = (TextView) fram.findViewById(R.id.txtHerlpOther);
		other.setText(helpother);

		if (classname != null) {
			imageIcon.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					cleanStatus();
					Intent mIntent = new Intent();
					mIntent.setClass(SelectGameActivity.this, classname);
					startActivity(mIntent);
					uMengClick(umengclick);
					setGameType(gamename);
				}
			});
		}
	}

	private void setSwithSound(boolean isChecked) {
		if (isChecked) {
			SoundPlayer.setSoundSt(true);
			sound.setText(strFromId("soundon"));
			// btnSound.setBackgroundResource(R.drawable.btn_select_true);
		} else {
			sound.setText(strFromId("soundoff"));
			SoundPlayer.setSoundSt(false);
			// btnSound.setBackgroundResource(R.drawable.btn_select_false);
		}
	}

	// 播放动画
	private void startMoveAnmi(View points, int type) {
		AnimationSet as = new AnimationSet(true);

		TranslateAnimation al = null;
		if (type == 1) {
			al = new TranslateAnimation(0, 0, 0, 20, 0, 0, 0, 0);
		} else {
			al = new TranslateAnimation(0, 0, 0, -20, 0, 0, 0, 0);
		}
		al.setDuration(1000);
		as.addAnimation(al);
		al.setRepeatMode(Animation.REVERSE);
		al.setRepeatCount(-1);
		points.startAnimation(as);
	}


	private class MyClickListener implements android.view.View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent mIntent = new Intent();
			switch (v.getId()) {
			// case R.id.helpButton:
			// mIntent.setClass(SelectGameActivity.this,
			// UnderCoverContent.class);
			// break;
			case R.id.startButton:
			case R.id.btnStart:
				cleanStatus();
				SoundPlayer.playball();
				mIntent.setClass(SelectGameActivity.this, Setting.class);
				uMengClick("game_undercover");
				setGameType("undercover");
				break;
			case R.id.btnweixin:
				SoundPlayer.playball();
				uMengClick("click_weixin");
				mIntent.setClass(SelectGameActivity.this, weixin.class);
				break;
			case R.id.btnReStartLast:
				if (getStatus()) {
						mIntent.setClass(SelectGameActivity.this, guess.class);
				} else {
					cleanStatus();
					mIntent.setClass(SelectGameActivity.this, Setting.class);
				}
				break;
			case R.id.btnyonghu:
				SoundPlayer.playball();
				uMengClick("click_usercontribution");
				mIntent.setClass(SelectGameActivity.this,
						ContributionActivity.class);
				break;

			case R.id.btnguanyu:
				SoundPlayer.playball();
				mIntent.setClass(SelectGameActivity.this, MakeActivity.class);
				uMengClick("click_about");
				break;
			case R.id.btnfb:
				SoundPlayer.playball();
				agent.startFeedbackActivity();
				return;
			default:
				break;
			}
			
			startActivity(mIntent);
		}
		
	}
	
	// 滑动页面的的适配器啊，主要把ViewList中的View传给ViewPager
    private class MyPageAdapter extends PagerAdapter {  
  	  
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
    class MyPageChangeListener implements OnPageChangeListener{

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
					imageViews[i].setBackgroundResource(R.drawable.page_indicator);
				}
				imageViews[position].setBackgroundResource(R.drawable.page_indicator_focused);
			}
		}
    	
    }
    
	// 退出确认
    public void onBackPressed() {  
		new AlertDialog.Builder(this)
				.setTitle(strFromId("exit"))
            .setIcon(android.R.drawable.ic_dialog_info)  
				.setPositiveButton(strFromId("doit"),
						new DialogInterface.OnClickListener() {
          
                @Override  
                public void onClick(DialogInterface dialog, int which) {  
								// 点击“确认”后的操作
                SelectGameActivity.this.finish();  
          
                }  
            })  
				.setNegativeButton(strFromId("returnit"),
						new DialogInterface.OnClickListener() {
          
                @Override  
                public void onClick(DialogInterface dialog, int which) {  
								// 点击“返回”后的操作,这里不设置没有任何操作
							}
            }).show();  
        // super.onBackPressed();  
    }  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_game, menu);
		return true;
	}

	public void onResume() {
		super.onResume();
		if (!getStatus()) {
			btnReStart.setVisibility(View.GONE);
		} else {
			btnReStart.setVisibility(View.VISIBLE);
		}
	}
}
