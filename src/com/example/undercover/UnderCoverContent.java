package com.example.undercover;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author liuchunlong
 * 谁是卧底  游戏简介
 */
public class UnderCoverContent extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.undercover_content);
		TextView ruleText	= (TextView) findViewById(R.id.ruleText);
		TextView winText	= (TextView) findViewById(R.id.winText);
		ruleText.setText("游戏规则:\n1、选择参与人数与卧底人数开始游戏\n2、每人需记得自己的词语和编号" +
				"\n3、依次描述自己的词语\n4、每轮描述结束后，投票选出卧底\n5、剩余玩家继续描述");
		winText.setText("胜利条件：\n1、卧底全部被指认出，平民胜利\n2、卧底人数大于等于平民数目时，卧底胜利");
		
		/**      
		 * 下面注释的代码，请勿动，谢谢
		
		LinearLayout layoutBoss  = new LinearLayout(this);
		LinearLayout layoutHor  = new LinearLayout(this);
		LinearLayout layoutVen  = new LinearLayout(this);
		ImageView	imageView	= new ImageView(this);
		TextView cnText	= new TextView(this);
		TextView enText	= new TextView(this);
		layoutHor.setOrientation(0);//0水平
		layoutHor.setBackgroundColor(getResources().getColor(R.color.content_head));
		layoutVen.setOrientation(1);//1垂直
		layoutVen.setPadding(17, 0, 0, 0);
		imageView.setImageDrawable(getResources().getDrawable(R.drawable.popo_who));
		imageView.setAdjustViewBounds(true);
		imageView.setMaxHeight(64);
		imageView.setMaxWidth(64);
		cnText.setText(getResources().getString(R.string.content_zn));
		cnText.setTextSize(25);
		enText.setText(getResources().getString(R.string.content_en));
		cnText.setTextSize(20);
		
		
		layoutHor.addView(enText);
		layoutHor.addView(cnText);
		layoutVen.addView(imageView);
		layoutVen.addView(layoutHor);
		layoutBoss.addView(layoutVen);
		*/
		
	}
}
