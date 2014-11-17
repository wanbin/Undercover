package cn.centurywar.undercover;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import cn.centurywar.undercover.view.ItemAdapter;

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
		data=new ArrayList<String>();
		data=getData();
        ItemAdapter adapter = new ItemAdapter(this,local_punish_list.this,data);  
        listView.setAdapter(adapter);  
		
	}
	 private List<String> getData(){
		JSONArray jsonarray = getLocateDamaoxian();
		try {
			for (int i = 0; i < jsonarray.length(); i++) {
				String tem = jsonarray.getJSONObject(i).getString("data");
				if (tem != null) {
					data.add(tem);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	public void removeString(String str){
		removeDamaoxian(str);
	}
}
