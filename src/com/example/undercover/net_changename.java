package com.example.undercover;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class net_changename extends BaseActivity {
	private Button finish;
	private EditText username;
	
	protected String name2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_changename);
		initBtnBack(R.id.btnback);
		finish = (Button) findViewById(R.id.button1);
		username = (EditText) findViewById(R.id.editname);
		setBtnBlue(finish);
		
		finish.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name2 = username.getText().toString();
				if (username.getText().length() != 0) {
					gameInfo.edit().putString("username", name2).commit();
					Intent intentGo = new Intent();
					intentGo.setClass(net_changename.this, net_punish.class);
					// uMengClick("click_intenet");
					startActivity(intentGo);
					finish();
				} else{
					showEditContent();
				}
			}
			
		});
		
}
	
	void showEditContent (){
		Toast.makeText(this,"<Please edit the name!!>", Toast.LENGTH_SHORT).show();
		}
}
