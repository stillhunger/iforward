package cn.xm.hanley.iforward.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xm.hanley.iforward.activity.ContactsActivity;
import cn.xm.hanley.iforward.activity.R;
import cn.xm.hanley.iforward.adapter.ForwardAdapter;
import cn.xm.hanley.iforward.constants.Constants;
import cn.xm.hanley.iforward.domain.Contact;
import cn.xm.hanley.iforward.domain.History;
import cn.xm.hanley.iforward.utils.ContactScanner;
import cn.xm.hanley.iforward.utils.ForwardScanner;
import cn.xm.hanley.iforward.utils.HistoryScanner;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
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
 * 转移历史记录
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-17下午3:43:19
 * @Google+ http://gplus.to/hanleytowne
 */
public class ForwardFragment extends ListFragment {
	
	private static final String TAG = "ForwardFragment";
	ArrayList<History> selectedContacts;
	private SimpleAdapter simapleAdapter;
	ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fm_forward, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ForwardScanner cs = new ForwardScanner(handler,getActivity());
		cs.start();
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
//		SimpleAdapter sa = (SimpleAdapter)this.getListAdapter();
//		HashMap<String,String> hm = (HashMap<String, String>) this.getListView().getItemAtPosition(position);
		
	}
	
private void showForward(){
		
		if(null == simapleAdapter){
			String[] itemName = new String[]{ "forwardName","forwardNumber"};
			int []   itemValue =  new int[]{ R.id.forward_name,R.id.forward_number};
			simapleAdapter = new SimpleAdapter(getActivity(), data,R.layout.item_forwar, itemName, itemValue);
			this.setListAdapter(simapleAdapter);
		}else{
			simapleAdapter.notifyDataSetChanged();
		}

	}
	
	
	@SuppressWarnings("unchecked")
	public void showData(Object data){
		ArrayList<Contact> contacts = (ArrayList<Contact>) data;
		ForwardAdapter forwardAdapter = new ForwardAdapter(getActivity(),contacts,handler);
		setListAdapter(forwardAdapter);
		forwardAdapter.notifyDataSetChanged();
	}
	
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what){
			case Constants.RESPONSE_CODE_SHOW_FORWARD:
				showData(msg.obj);
				break;
			case Constants.RESPONSE_CODE_SHOW_REFRESH:
				showData(msg.obj);
				break;
			}
		}
	};
}
