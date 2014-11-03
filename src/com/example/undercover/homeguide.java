package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class homeguide extends BaseActivity {
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
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater mInflater = getLayoutInflater();
		// Welcome 的Layout

		viewList = new ArrayList<View>();
		
		int gameCount=5;
		imageViews = new ImageView[gameCount];
		
		String[] gamename={"快速！","简洁！","丰富！","妥协！","爱上聚会"};
		String[] des1={"感受到了吗？","删除复杂的功能与设计","总有适合你们的游戏","不幸的是，我们妥协了","你们才是主角"};
		String[] des2={"我们和您一样，是急性了，不想在聚会的时候耽搁时间。","一目了然的设计，让所有人都能快速使用。","每个人都有理由参与其中","因为这只是工具，不必太华丽","马上开始"};
		
  		
		for(int i=0;i<gameCount;i++){
			if(i<4){
				View welcomeView = mInflater.inflate(R.layout.game_guide, null);
				TextView txtTitle=(TextView)welcomeView.findViewById(R.id.txtTitle);
				TextView txtDes1=(TextView)welcomeView.findViewById(R.id.txtDes1);
				TextView txtDes2=(TextView)welcomeView.findViewById(R.id.txtDes2);
				txtTitle.setText(gamename[i]);
				txtDes1.setText(des1[i]);
				txtDes2.setText(des2[i]);
				
				viewList.add(welcomeView);
			}else{
				View welcomeView = mInflater.inflate(R.layout.game_guide2, null);
				TextView txtTitle=(TextView)welcomeView.findViewById(R.id.txtTitle);
				TextView txtDes1=(TextView)welcomeView.findViewById(R.id.txtDes1);
				Button btnStart=(Button)welcomeView.findViewById(R.id.btnStart);
				btnStart.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
				txtTitle.setText(gamename[i]);
				txtDes1.setText(des1[i]);
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
			ImageView image = new ImageView(homeguide.this);
			image.setLayoutParams(new LayoutParams(10, 10));
	
			RelativeLayout temview=new RelativeLayout(homeguide.this);
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
    
}
