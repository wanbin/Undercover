package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.undercover.view.ItemAdapter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class local_punish_list extends BaseActivity {
	ListView listView;
	 List<String> data;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_punish_list);
		listView=(ListView)this.findViewById(R.id.list);

		
//		List<String> list = new ArrayList<String>();  
//        for(int i=0;i<10;i++){  
//            list.add("选项选项选项选项选项选项选项选项选项选项选项选项选项选项选项选项选项选项"+i);  
//        }  
		data=getData();
        //实例化自定义内容适配类  
        ItemAdapter adapter = new ItemAdapter(this,local_punish_list.this,data);  
        //为listView设置适配  
        listView.setAdapter(adapter);  
//		list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getData()));
		
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
	        data.add("for test");
	        data.add("for test1");
	        data.add("for test2");
	        return data;
	    }
	public void removeString(int str){
		List<String> data2=data;
		
	}
}
