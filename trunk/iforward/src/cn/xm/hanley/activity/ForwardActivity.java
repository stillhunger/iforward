package cn.xm.hanley.activity;

import cn.xm.hanley.constants.Constants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @Description: 设置转接号码
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-7-10下午10:16:06
 * @Google+ http://gplus.to/hanleytowne 
 */
public class ForwardActivity extends Activity {

	protected static final String TAG = "ForwardActivity";
	private Button btnOk;
	private EditText editNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_forward);

		findViewByIds();
		setListeners();
		init();
	}

	private void findViewByIds() {
		btnOk = (Button)findViewById(R.id.btn_ok);
		editNumber = (EditText)findViewById(R.id.edit_fnumber);
	}

	private void setListeners() {
		btnOk.setOnClickListener(onClickListener);
	}
	
	
	@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
	private void init(){
		SharedPreferences share = this.getSharedPreferences(Constants.SPS_FORWARD,MODE_WORLD_WRITEABLE);
		String fnumber = share.getString(Constants.SPS_FORWARD_NUMBER, "");
		if(null != fnumber && !"".equals(fnumber)){
			editNumber.setText(fnumber);
		}
	}

	@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btn_ok:
				String number = editNumber.getText().toString();
				Log.i(TAG, number);
				if(null == number || number.length() == 0){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.tip_no_content), Toast.LENGTH_LONG).show();
				}else{
					Intent intent = new Intent(Intent.ACTION_CALL);
					String newNumber = bulidNumber(number);
					intent.setData(Uri.parse(newNumber));
					startActivity(intent);
					SharedPreferences share = getSharedPreferences(Constants.SPS_FORWARD,MODE_WORLD_WRITEABLE);
					share.edit().putString(Constants.SPS_FORWARD_NUMBER, number).commit();
					
					SharedPreferences share2 = getSharedPreferences(Constants.SPS_COMPLETE_PROGRESS,MODE_WORLD_READABLE);
					share2.edit().putBoolean(Constants.SPS_NUMBER_FLAG, true).commit();
					
					setResult(RESULT_OK);
					finish();
				}
				break;
			}
		}
		
	};
	
	public String bulidNumber(String number){
		StringBuffer sb = new StringBuffer();
		sb.append("tel:**67*");
		sb.append(number);
		sb.append("%23");
		return sb.toString();
	}
}
