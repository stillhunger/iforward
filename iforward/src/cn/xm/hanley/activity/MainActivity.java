package cn.xm.hanley.activity;

import cn.xm.hanley.constants.Constants;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import cn.xm.hanley.activity.ContactsActivity;
/**
 * @Description:
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-7-10下午10:05:14
 * @Google+ http://gplus.to/hanleytowne 
 */
public class MainActivity extends Activity {
	
	protected static final int CODE_REQUEST_FORWARD = 1;
	protected static final int CODE_REQUEST_CONTACT = 2;
	private Button btnfNumber,BtnfList;
	private TextView tip_number;
	private TextView tip_flist;
	private String TAG = "MainActivity";

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewByIds();
        setListeners();
        init();
    }
    
    
    @SuppressLint("WorldReadableFiles")
	private void init(){
    	SharedPreferences share = this.getSharedPreferences(Constants.SPS_COMPLETE_PROGRESS,MODE_WORLD_READABLE);
    	boolean number_flag = share.getBoolean(Constants.SPS_NUMBER_FLAG, false);
    	boolean contact_flag = share.getBoolean(Constants.SPS_CONTACT_FLAG, false);
    	if(number_flag){
    		tip_number.setText(getResources().getString(R.string.tips_complete));
    		tip_number.setTextColor(Color.BLUE);
    	}else{
    		tip_number.setText(getResources().getString(R.string.tips_no_complete));
    		tip_number.setTextColor(Color.RED);
    	}
    	
    	if(contact_flag){
    		tip_flist.setText(getResources().getString(R.string.tips_complete));
    		tip_flist.setTextColor(Color.BLUE);
    	}else{
    		tip_flist.setText(getResources().getString(R.string.tips_no_complete));
    		tip_flist.setTextColor(Color.RED);
    	}
    }
    
    private void findViewByIds(){
    	btnfNumber = (Button)findViewById(R.id.btn_fnumber);
    	BtnfList = (Button)findViewById(R.id.btn_flist);
    	tip_number = (TextView)findViewById(R.id.tip_number);
    	tip_flist = (TextView)findViewById(R.id.tip_flist);
    }
    
    private void setListeners(){
    	btnfNumber.setOnClickListener(btnListener);
    	BtnfList.setOnClickListener(btnListener);
    }
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater(); 
        inflater.inflate(R.menu.menu_title, menu); 
        return true; 
	}
    

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	
    	Intent intent = null;
    	switch(item.getItemId()){
    	case R.id.menu_about:
    		intent = new Intent(MainActivity.this,AboutActivity.class);
    		MainActivity.this.startActivity(intent);
    		break;
    	case R.id.menu_settings:
    		intent = new Intent(MainActivity.this,SettingsActivity.class);
    		MainActivity.this.startActivity(intent);
    		break;
    	}
		return super.onOptionsItemSelected(item);
	}


	private OnClickListener btnListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch(v.getId()){
			case R.id.btn_fnumber:
				intent = new Intent(MainActivity.this,ForwardActivity.class);
				startActivityForResult(intent,CODE_REQUEST_FORWARD);
				break;
			case R.id.btn_flist:
				intent = new Intent(MainActivity.this,ContactsActivity.class);
				startActivityForResult(intent,CODE_REQUEST_CONTACT);
				break;
			default:
				Log.i(TAG, "btnListener default");
				break;
			}
		}
    	
    };



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CODE_REQUEST_FORWARD){
			if(resultCode == RESULT_OK){
				tip_number.setText(getResources().getString(R.string.tips_complete));
	    		tip_number.setTextColor(Color.BLUE);
			}
		}
		
		if(requestCode == CODE_REQUEST_CONTACT){
			if(resultCode == RESULT_OK){
				tip_flist.setText(getResources().getString(R.string.tips_complete));
	    		tip_flist.setTextColor(Color.BLUE);
			}
		}
	}
    
    

}