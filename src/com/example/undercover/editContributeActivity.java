package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class editContributeActivity extends BaseActivity {
	private Button send;
	private Button cancle;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcontribute);
		
		send = (Button) findViewById(R.id.send);
		cancle = (Button) findViewById(R.id.cancle);
		
		send.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			}
		});
		
		cancle.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

}
