package com.example.util;


import com.example.undercover.R;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContentHeadView extends View {

	public ContentHeadView(Context context){
		super(context);
		LinearLayout layoutBoss  = new LinearLayout(context);
		LinearLayout layoutHor  = new LinearLayout(context);
		LinearLayout layoutVen  = new LinearLayout(context);
		ImageView	imageView	= new ImageView(context);
		TextView cnText	= new TextView(context);
		TextView enText	= new TextView(context);
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
	}
	
	public ContentHeadView(Context context,AttributeSet set){
		super(context,set);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
	
	
}
