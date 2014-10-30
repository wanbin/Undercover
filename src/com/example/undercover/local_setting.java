package com.example.undercover;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;

public class local_setting extends BaseActivity {
	SeekBar people;
	SeekBar undercover;
	TextView peopleCount;
	TextView undercoverCount;
	Button btnStart;
	String gameType;
	TableRow tableUndercover;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_setting);
		tableUndercover=(TableRow)this.findViewById(R.id.rowUndercover);
		gameType=lastGameType();
		if (gameType.equals("undercover")) {

		} else if (gameType.equals("killer")) {
			tableUndercover.setVisibility(View.GONE);
		}
	}
}
