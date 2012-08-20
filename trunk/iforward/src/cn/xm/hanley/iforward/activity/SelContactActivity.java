package cn.xm.hanley.iforward.activity;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xm.hanley.iforward.constants.Constants;
import cn.xm.hanley.iforward.utils.ContactScanner;


import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


/**
 * 转接号码选择联系人
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-20上午11:19:08
 * @Google+ http://gplus.to/hanleytowne
 */
public class SelContactActivity extends ListActivity {

	ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_sel_contact);
		ContactScanner cs = new ContactScanner(handler,this);
		cs.start();
		
	}
	
	private SimpleAdapter simapleAdapter;

	@SuppressWarnings("unchecked")
	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		super.onListItemClick(lv, v, position, id);
		
		SimpleAdapter sa = (SimpleAdapter)this.getListAdapter();
		HashMap<String,Object> m = (HashMap<String,Object>)sa.getItem(position);
		Intent data = new Intent();
		data.putExtra("number", m.get("contactNumber").toString());
		setResult(RESULT_OK, data);
		finish();
	}

	
	private void showContacts(){
		
		if(null == simapleAdapter){
			String[] itemName = new String[]{ "contactName","contactNumber"};
			int []   itemValue =  new int[]{ R.id.contactName,R.id.contactNumber};
			simapleAdapter = new SimpleAdapter(this, data,R.layout.item_sel_contact, itemName, itemValue);
			this.setListAdapter(simapleAdapter);
		}else{
			simapleAdapter.notifyDataSetChanged();
		}

	}
	
	
	
	private Handler handler = new Handler(){

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what){
			case Constants.RESPONSE_CODE_SHOW_CONTACT:
				HashMap<String,Object> m = (HashMap<String,Object>) msg.obj;
				data.add(m);
				showContacts();
				break;
			}
		}
		
	};
	
}
