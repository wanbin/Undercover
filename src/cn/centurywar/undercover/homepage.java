package cn.centurywar.undercover;

import android.os.Bundle;
import android.webkit.WebView;

public class homepage extends BaseActivity {
	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		webView=(WebView)this.findViewById(R.id.webView);
		String url=getIntent().getStringExtra("url");
		if(url.equals("")){
			url="http://www.centurywar.cn";
		}
		webView.loadUrl(url);
	}
}
