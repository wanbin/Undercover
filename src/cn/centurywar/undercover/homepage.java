package cn.centurywar.undercover;

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
		if(gameType!=null&&gameType.equals("newGame")){
			String url=getFromObject("newgameurl");
			urlPage.loadUrl(url+"?showpage=gamenow&uid="+getUid());
		}else{
			urlPage.loadUrl("http://mobile.centurywar.cn?showpage=help&uid="+getUid());
		}
	}
}
