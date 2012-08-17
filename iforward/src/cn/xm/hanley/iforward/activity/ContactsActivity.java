package cn.xm.hanley.iforward.activity;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xm.hanley.iforward.constants.Constants;
import cn.xm.hanley.iforward.domain.Contact;
import cn.xm.hanley.iforward.utils.ContactScanner;
import cn.xm.hanley.iforward.utils.ContactsUtil;


import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * @Description: 显示联系人
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-5下午3:58:37
 * @Google+ http://gplus.to/hanleytowne 
 */
public class ContactsActivity extends ListActivity {

	private static final String TAG = "ContactsActivity";
	private Button btnOk;
	private Button btnCancle;
	ArrayList<Contact> selectedContacts;
	ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_contact);
		findViewByids();
		setListeners();
		ContactScanner cs = new ContactScanner(handler,this);
		cs.start();
		
	}
	
	private void findViewByids(){
		btnOk = (Button)findViewById(R.id.btn_ok);
		btnCancle = (Button)findViewById(R.id.btn_cancle);
	}
	
	private void setListeners(){
		btnOk.setOnClickListener(onClickListener);
		btnCancle.setOnClickListener(onClickListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener(){

		@SuppressLint("WorldReadableFiles")
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btn_ok:
				try {
					currentSelectedContants();
					int size = selectedContacts.size();
					if (0 == size) {
						Toast.makeText(getApplication(),getResources().getString(R.string.tip_no_item_selected),Toast.LENGTH_SHORT).show();
					}else{
						
						SharedPreferences share = getSharedPreferences(Constants.SPS_COMPLETE_PROGRESS,MODE_WORLD_READABLE);
						share.edit().putBoolean(Constants.SPS_CONTACT_FLAG, true).commit();
						setResult(RESULT_OK);
						finish();
					}
					
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					Toast.makeText(getApplication(),getResources().getString(R.string.tip_no_item_selected),Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btn_cancle:
				ContactsActivity.this.finish();
				break;
			default:
				Log.i(TAG, "onClickListener");
				break;
			}
		}
		
	};
	private SimpleAdapter simapleAdapter;

	@SuppressWarnings("unchecked")
	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		super.onListItemClick(lv, v, position, id);
		CheckBox ck = (CheckBox)v.findViewById(R.id.lv_checkbox);
		SimpleAdapter sa = (SimpleAdapter)this.getListAdapter();
		HashMap<String,Object> m = (HashMap<String,Object>)sa.getItem(position);
		m.put("lvcheck", !ck.isChecked());
		sa.notifyDataSetChanged();
	}


	@SuppressWarnings("unused")
	private void showALLContacts(){
		ArrayList<HashMap<String,Object>> data = ContactsUtil.loadContacts(this);
		String[] itemName = new String[]{ "contactName","contactNumber","lvcheck"};
		int []   itemValue =  new int[]{ R.id.contactName,R.id.contactNumber,R.id.lv_checkbox};
		SimpleAdapter simapleAdapter = new SimpleAdapter(this, data,R.layout.item_contact, itemName, itemValue);
		this.setListAdapter(simapleAdapter);
		simapleAdapter.notifyDataSetChanged();
	}
	
	private void showContacts(){
		
		if(null == simapleAdapter){
			String[] itemName = new String[]{ "contactName","contactNumber","lvcheck"};
			int []   itemValue =  new int[]{ R.id.contactName,R.id.contactNumber,R.id.lv_checkbox};
			simapleAdapter = new SimpleAdapter(this, data,R.layout.item_contact, itemName, itemValue);
			this.setListAdapter(simapleAdapter);
		}else{
			simapleAdapter.notifyDataSetChanged();
		}

	}
	
	
	@SuppressWarnings("unchecked")
	private void currentSelectedContants(){
		SimpleAdapter sa = (SimpleAdapter)this.getListAdapter();
		selectedContacts = new ArrayList<Contact>();
		int size = sa.getCount();
		for(int i=0; i<size; i++){
			HashMap<String,Object> contact = (HashMap<String,Object>)sa.getItem(i);
			boolean flag = (Boolean) contact.get("lvcheck");
			if(flag){
				String contactName = (String) contact.get("contactName");
				String contactNumber = (String)contact.get("contactNumber");
				contactNumber = contactNumber.replaceAll(" ", "");
				Contact ct = new Contact();
				ct.setContactName(contactName);
				ct.setContactNumber(contactNumber.replaceAll("-", ""));
				selectedContacts.add(ct);
			}
		}
		Constants.CURRENT_SELECTED_CONTACTS = selectedContacts;
	}
	
	
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what){
			case Constants.RESPONSE_CODE_SHOW_CONTACT:
				@SuppressWarnings("unchecked")
				HashMap<String,Object> m = (HashMap<String,Object>) msg.obj;
				data.add(m);
				showContacts();
				break;
			}
			
		}
		
	};
	
}
