package cn.xm.hanley.iforward.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xm.hanley.iforward.activity.ContactsActivity;
import cn.xm.hanley.iforward.activity.R;
import cn.xm.hanley.iforward.constants.Constants;
import cn.xm.hanley.iforward.domain.Contact;
import cn.xm.hanley.iforward.utils.ContactScanner;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 从联系人选择拦截对象
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-16下午3:57:55
 * @Google+ http://gplus.to/hanleytowne
 */
public class ContactBlockFragment extends ListFragment {
	
	private static final String TAG = "ContactBlockFragment";
	private Button btnOk;
	private Button btnCancle;
	ArrayList<Contact> selectedContacts;
	private SimpleAdapter simapleAdapter;
	ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fm_contactblock, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findViewByids();
		setListeners();
		ContactScanner cs = new ContactScanner(handler,getActivity());
		cs.start();
	}
	
	private void findViewByids(){
		
		btnOk = (Button)getActivity().findViewById(R.id.btn_ok);
		btnCancle = (Button)getActivity().findViewById(R.id.btn_cancle);
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
						Toast.makeText(getActivity(),getResources().getString(R.string.tip_no_item_selected),Toast.LENGTH_SHORT).show();
					}else{
						
						SharedPreferences share = getActivity().getSharedPreferences(Constants.SPS_COMPLETE_PROGRESS,getActivity().MODE_WORLD_READABLE);
						share.edit().putBoolean(Constants.SPS_CONTACT_FLAG, true).commit();
						getActivity().setResult(getActivity().RESULT_OK);
						getActivity().finish();
					}
					
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					Toast.makeText(getActivity(),getResources().getString(R.string.tip_no_item_selected),Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btn_cancle:
				
				getActivity().finish();
				break;
			default:
				Log.i(TAG, "onClickListener");
				break;
			}
		}
		
	};

	@SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		CheckBox ck = (CheckBox)v.findViewById(R.id.lv_checkbox);
		SimpleAdapter sa = (SimpleAdapter)this.getListAdapter();
		HashMap<String,Object> m = (HashMap<String,Object>)sa.getItem(position);
		m.put("lvcheck", !ck.isChecked());
		sa.notifyDataSetChanged();
	}
	
private void showContacts(){
		
		if(null == simapleAdapter){
			String[] itemName = new String[]{ "contactName","contactNumber","lvcheck"};
			int []   itemValue =  new int[]{ R.id.contactName,R.id.contactNumber,R.id.lv_checkbox};
			simapleAdapter = new SimpleAdapter(getActivity(), data,R.layout.item_contacts_loader, itemName, itemValue);
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
