package cn.centurywar.undercover;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class weixin extends BaseActivity {
	private ImageView weixin;
	private TextView txtDes1;
	private TextView txtDes2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weixin);
		weixin = (ImageView) findViewById(R.id.imageView1);
		txtDes1 = (TextView) findViewById(R.id.txtDes1);
		txtDes2 = (TextView) findViewById(R.id.txtDes2);
		txtDes1.setText("扫一下，加入微信也可玩");
		txtDes2.setText("微信号：centurywar");
		
		// 屏幕变黑
		// WindowManager.LayoutParams lp = getWindow().getAttributes();
		// lp.alpha = 0.3f;
		// getWindow().setAttributes(lp);
		//
		// lp.dimAmount = 0.5f;
		// getWindow().setAttributes(lp);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		weixin.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("http://weixin.qq.com/r/bnWkvArEolfdrU7f9yB8");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

	}


}
