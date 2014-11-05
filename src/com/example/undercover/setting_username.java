package com.example.undercover;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class setting_username extends BaseActivity {
	Button btnChange;
	EditText editName;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_changename);
		
		btnChange=(Button)this.findViewById(R.id.btnChange);
		editName=(EditText)this.findViewById(R.id.editName);
		String username=getFromObject("username");
		editName.setText(username);
		
		btnChange.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String newname=editName.getText().toString();
				if(newname.length()==0){
					ToastMessage("请输入昵称");
				}else if(newname.length()>10){
					ToastMessage("昵称太长");
				}else{
					NameChange(newname,"");
					name=newname;
				}
			}
		});
	}

	@Override
	public void CallBackPublicCommand(JSONObject jsonobj, String cmd) {
		super.CallBackPublicCommand(jsonobj, cmd);
		if (cmd.equals(ConstantControl.NAME_CHANGE)) {
			try {
				ToastMessage("昵称修改成功");
				setToObject("username",name);
				updateUsername(name);
				finish();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
}
