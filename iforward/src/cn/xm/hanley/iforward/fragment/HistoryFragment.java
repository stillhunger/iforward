package cn.xm.hanley.iforward.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xm.hanley.iforward.activity.ContactsActivity;
import cn.xm.hanley.iforward.activity.R;
import cn.xm.hanley.iforward.constants.Constants;
import cn.xm.hanley.iforward.domain.Contact;
import cn.xm.hanley.iforward.domain.History;
import cn.xm.hanley.iforward.utils.ContactScanner;
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
public class HistoryFragment extends ListFragment {
	
	private static final String TAG = "HistoryFragment";
	ArrayList<History> selectedContacts;
	private SimpleAdapter simapleAdapter;
	ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fm_history, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		HistoryScanner cs = new HistoryScanner(handler,getActivity());
		cs.start();
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		SimpleAdapter sa = (SimpleAdapter)this.getListAdapter();
		HashMap<String,String> hm = (HashMap<String, String>) this.getListView().getItemAtPosition(position);
		
	}
	
private void showHistory(){
		
		if(null == simapleAdapter){
			String[] itemName = new String[]{ "historyName","historyNumber","historyDate","historyTime","historyTransferred"};
			int []   itemValue =  new int[]{ R.id.history_name,R.id.history_number,R.id.history_date,R.id.history_time,R.id.history_transferred};
			simapleAdapter = new SimpleAdapter(getActivity(), data,R.layout.item_history, itemName, itemValue);
			this.setListAdapter(simapleAdapter);
		}else{
			simapleAdapter.notifyDataSetChanged();
		}

	}
	
	
	
	
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what){
			case Constants.RESPONSE_CODE_SHOW_HISTORY:
				@SuppressWarnings("unchecked")
				ArrayList<History> hsy = (ArrayList<History>) msg.obj;
				for(History h:hsy){
					HashMap<String,String> m = new HashMap<String,String>();
					m.put("historyName", h.getHname());
					m.put("historyNumber", h.getHnumber());
					m.put("historyDate", h.getHdate());
					m.put("historyTime", h.getHtime());
					m.put("historyTransferred",getResources().getString(R.string.transferred_to)+":"+h.getHtransfer());
					data.add(m);
				}
				showHistory();
				break;
			}
			
		}
		
	};
}
