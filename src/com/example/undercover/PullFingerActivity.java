package com.example.undercover;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

public class PullFingerActivity extends Activity {
	private Button blueBtn;
	private Button redBtn;
	private ImageView rope;
	private Integer height;
	private LayoutParams para;
	private float ropeX;
	private float ropeY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pull_finger);
		
		blueBtn		= (Button)findViewById(R.id.pull_bluebtn);
		redBtn		= (Button)findViewById(R.id.pull_redbtn);
		rope		= (ImageView)findViewById(R.id.pull_rope);
		height		= rope.getHeight();
		ropeX	= rope.getX();
		ropeY	= rope.getY();
		blueBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				ropeY	= ropeY+10;
				rope.setY(ropeY);
				para	= rope.getLayoutParams();
//				para.height	=Math.ceil(ropeY); //测试将ropeY设置给para.height
				rope.setLayoutParams(para);
				System.out.println("************blue***************"+rope.getHeight());
			}
		});
		
		redBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				para	= rope.getLayoutParams();
				para.height	-=1;
				rope.setLayoutParams(para);
				ropeY = ropeY-10;
				rope.setY(ropeY);
				System.out.println("*****************red****************"+rope.getHeight());
			}
		});
		
	}
}
