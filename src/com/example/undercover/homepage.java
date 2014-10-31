package com.example.undercover;

import android.os.Bundle;
import android.webkit.WebView;

public class homepage extends BaseActivity {
	WebView urlPage;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		urlPage=(WebView)this.findViewById(R.id.urlpage);
		urlPage.loadUrl("http://www.centurywar.cn/www/index.php?showpage=help");
	}
}
