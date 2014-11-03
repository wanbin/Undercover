package com.example.undercover;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class feedback extends BaseActivity {
	Button btnFeedback;
	EditText txtCosntent;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		btnFeedback=(Button)this.findViewById(R.id.btnFeedback);
		txtCosntent=(EditText)this.findViewById(R.id.txtContent);
		
		
		btnFeedback.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
//				setSwithSound(!SoundPlayer.getSoundSt());
				if(txtCosntent.getText().toString().length()>0){
					MailSend(txtCosntent.getText().toString(),-2);
				}else{
					ToastMessage("还未有反馈内容");
				}
			}
		});
		initContent();	
	}
	private void initContent(){
		txtCosntent.setText("["+getVersion()+"]");
	}
	
	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.MAIL_SEND)) {
			try {
				initContent();
				ToastMessage("感谢您的反馈");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
}
