package cn.centurywar.undercover;

import java.util.Random;

import cn.centurywar.undercover.R.color;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;

public class local_draw extends BaseActivity {
	private Button btnRestart;
	private Button btnPunish;
	private RelativeLayout layoutContent;
	private LinearLayout chengfa;
	int peoplecount=12;
	int randomNum=0;
	private Random random = new Random();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_draw);
		btnRestart=(Button)this.findViewById(R.id.btnReflash);
		btnPunish=(Button)this.findViewById(R.id.btnPunish);
		layoutContent=(RelativeLayout)this.findViewById(R.id.layoutContent);
		chengfa=(LinearLayout)this.findViewById(R.id.chengfa);
		
		btnPunish.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentGo = new Intent();
				intentGo.setClass(local_draw.this, local_punish.class);
				startActivity(intentGo);
			}
		});
		
		btnRestart.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				init();
			}
		});
		init();
		uMengClick("game_draw");
	}
	private void init(){
		uMengClick("game_draw_count");
		int temindex=0;
		randomNum= Math.abs(random.nextInt()%peoplecount);
		layoutContent.removeAllViews();
		chengfa.setVisibility(View.GONE);
		for (int i = 0; i < Math.ceil(peoplecount / 4); i++) {
			for (int m = 0; m < 4; m++) {
				if (temindex >= peoplecount) {
					break;
				}
				final int nowindex=temindex;
				final Button select = new Button(this);
				select.setTag(temindex);
				select.setText(String.valueOf(temindex + 1));
				select.setTextSize(30);
				select.setTextColor(Color.WHITE);
				select.setGravity(Gravity.CENTER|Gravity.TOP);
				select.setBackgroundResource(R.drawable.fang_red_pressed);
				
				select.setOnLongClickListener(new Button.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						if(nowindex==randomNum){
							SoundPlayer.faile();
							select.setBackgroundResource(R.drawable.fang_blue_pressed);
							end();
						}else{
							tap(v);
							SoundPlayer.click();
						}
//						if (hasClicked[(Integer) v.getTag()] == true) {
//							return true;
//						}
//						// 如果是杀人游戏，界面显示去掉一些东西
//						if (lastGameType().equals("game_killer")) {
//							tapIndexKiller((Integer) v.getTag());
//						} else {
//							tapIndex((Integer) v.getTag());
//						}
//						hasClicked[(Integer) v.getTag()] = true;
//						v.setBackgroundResource(R.drawable.default_photo3);
//						v.setEnabled(false);
//						v.setClickable(false);
						return true;
					}
				});
				int rowindex=temindex/4;
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(   LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);   
//				lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  
//				lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);     
				lp.leftMargin=disWidth*(temindex%4)/4+5;
				int temheight=disHeight/3-40;
//				if(rowindex==0){
					lp.topMargin=rowindex*(temheight+10)+5;
//				}
				select.setWidth(disWidth/4-10);
				select.setHeight(temheight);
				layoutContent.addView(select, lp);
				temindex++;
			}
		}
	}

	private void tap(View btn){
		btn.setVisibility(View.GONE);
	}
	
	private void end(){
		chengfa.setVisibility(View.VISIBLE);
	}
}
