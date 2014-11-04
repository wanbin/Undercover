package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class home extends BaseActivity {
    private Context mContex = this;  
    
	private TabHost mTabHost;
			
    private String TAB1 = "游戏";  
    private String TAB2 = "参与";  
    private String TAB3 = "玩法";  
    private String TAB4 = "设置";  
    
    private List<LinearLayout> menuItemList;  

    
//    protected void onCreate(Bundle savedInstanceState) {
//    	 super.onCreate(savedInstanceState);
//    	 setContentView(R.layout.hostnew);
//    	 mTabHost=getTabHost();//得到TabHost对象实例
//    	 
//    	 TabHost.TabSpec tab1=mTabHost.newTabSpec("tab1");//创建标签
//    	 tab1.setIndicator("tab1");//设置tab标题
//    	 tab1.setContent(R.id.tab_item);//设置Tab布局内容
//    	 
//    	 TabHost.TabSpec tab2=mTabHost.newTabSpec("tab2");
//    	 tab2.setIndicator("tab2");
//    	 tab2.setContent(R.id.tab_item2);
//    	 mTabHost.addTab(tab1);//将tab加入TabHost中
//    	 mTabHost.addTab(tab2);
////    	 mTabHost.setOnTabChangedListener(this);
//    }
    
    public void onTabChanged(String tabId) {
    	 // TODO Auto-generated method stub
    	 if(tabId.equals("tab2"))
    	 Toast.makeText(this, "this is tab2", Toast.LENGTH_SHORT).show();
     }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hostnew);
		mTabHost=(TabHost) findViewById(android.R.id.tabhost);
		LocalActivityManager mLocalActivityManager =new LocalActivityManager(this,false);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		mTabHost.setup(mLocalActivityManager);
//		mTabHost.setup();
		menuItemList = new ArrayList<LinearLayout>(); 
//		mTabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
//		initIntent();
		
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(getMenuItem(R.drawable.game_2x,TAB1)).setContent(new Intent(this, homegame.class)));  
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(getMenuItem(R.drawable.join_2x,TAB2)).setContent(new Intent(this, net_home.class)));  
	    mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(getMenuItem(R.drawable.help_2x,TAB3)).setContent(new Intent(this, net_room_punish.class)));  
	    mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator(getMenuItem(R.drawable.setting_2x,TAB4)).setContent(new Intent(this, setting.class)));
//	        mTabHost.addTab(mTabHost.newTabSpec("tab5").setIndicator(getMenuItem(R.drawable.son, TAB5)).setContent(R.id.tab5));
	    
	    
	    
	    
	    
	    
		String soundOn = getFromObject("sound");
		if (soundOn.equals("")) {
			setToObject("sound", "on");
			soundOn="on";
		}
		if(soundOn.equals("on")){
//			SoundPlayer.setMusicSt(true);
			SoundPlayer.setSoundSt(true);
		}else{
//			SoundPlayer.setMusicSt(false);
			SoundPlayer.setSoundSt(false);
		}
	    
		

		
	}
    public View getMenuItem(int imgID, String textID){  
    	LinearLayout ll = (LinearLayout) LayoutInflater.from(mContex).inflate(R.layout.tab_item, null);  
        TextView textView = (TextView)ll.findViewById(R.id.name);  
        textView.setText(textID);  
        ImageView img=(ImageView)ll.findViewById(R.id.image);
        img.setBackgroundResource(imgID);
        menuItemList.add(ll);  
        return ll;  
    } 
    
    

}