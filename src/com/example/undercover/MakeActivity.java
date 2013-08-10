package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MakeActivity extends BaseActivity {
	private Button btnreturn;
	private TextView txtContent;
	private TextView txtTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make);
		txtContent = (TextView) findViewById(R.id.txtContent);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		btnreturn = (Button) findViewById(R.id.btnreturn);
		txtTitle.setText("【谁是卧底】1.04版本");
		String emailaddr = this.getString(R.string.emailaddr);
		txtContent
.setText("设计制作：一路向北工作室\n意见及建议：" + emailaddr
				+ "\n\n\nv1.04版本新功能：\n\n1.添加有胆量就问小游戏\n2.完善游戏惩罚措施\n3.优化界面");
		btnreturn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});


	}


}
