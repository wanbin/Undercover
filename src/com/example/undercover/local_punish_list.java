package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class local_punish_list extends BaseActivity {
	ListView list;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_punish_list);
		list=(ListView)this.findViewById(R.id.list);

		list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getData()));
	}
	 private List<String> getData(){
	        List<String> data = new ArrayList<String>();
	        String strLocal=getFromObject("local_punish");
	        try {
				JSONArray temArray=new JSONArray(strLocal);
				 for(int i=0;i<temArray.length();i++){
					 data.add(temArray.getString(i));
				 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return data;
	    }
}
