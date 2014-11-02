package com.example.undercover;

import android.os.Bundle;
import android.webkit.WebView;

public class homepage extends BaseActivity {
	WebView urlPage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		
		urlPage=(WebView)this.findViewById(R.id.urlpage);
		
		String gameType=getIntent().getStringExtra("type");
		
		if(gameType.equals("newGame")){
			urlPage.loadUrl("http://www.centurywar.cn/www/index.php?showpage=gamenow&uid="+getUid());
		}else{
			urlPage.loadUrl("http://www.centurywar.cn/www/index.php?showpage=help");
		}
	}
	
}
