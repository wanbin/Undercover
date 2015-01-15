package cn.centurywar.undercover;

import http.PublishHandler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class net_punish_add extends BaseActivity {
	private Button send;
	private EditText textCotent;
	private int sign = 1;
	private String oldStr="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_punish_add);
		send = (Button) findViewById(R.id.btnsend);
		textCotent = (EditText) findViewById(R.id.editText1);
		String content=getIntent().getStringExtra("content");
		textCotent.setText(content);
		oldStr=content;
		send.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = textCotent.getText().toString();
				if(content.length()==0){
					ToastMessageLong("输入内容为空");
					return;
				}
				if(content.equals(oldStr)){
					ToastMessageLong("请不要提交相同内容");
					return;
				}
					// 判断content的内容是否为控制
				if (sendPublish(content)) {
					addDamaoxian(content);
					finish();
					ToastMessageLong("提交成功");
				}
			}
		});
	}

	// 弹出提示
	void showEditContent() {
		Toast.makeText(this, "<Please edit the content!!>", Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 发布新闻方法
	 */
	public boolean sendPublish(String content) {
		PublishHandler publishHandler = new PublishHandler(this);
		return publishHandler.sendPublish(content, "", sign);
	}
}
