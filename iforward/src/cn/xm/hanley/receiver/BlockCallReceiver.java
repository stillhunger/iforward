package cn.xm.hanley.receiver;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.android.internal.telephony.ITelephony;

import cn.xm.hanley.constants.Constants;
import cn.xm.hanley.domain.Contact;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @Description: 转接来电,这里过滤拦截号码
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-7-21上午11:34:45
 * @Google+ http://gplus.to/hanleytowne 
 */
public class BlockCallReceiver extends BroadcastReceiver {
	
	private AudioManager audioManager;
	private ITelephony iTelephony;
	private TelephonyManager telephonyMgr;
	private final String PHONE_STATE = "android.intent.action.PHONE_STATE";
	private static final String TAG = "IncomingCallReceiver";
	
	public void init(Context context){
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Method getITelephonyMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);
			getITelephonyMethod.setAccessible(true);
			iTelephony = (ITelephony) getITelephonyMethod.invoke(telephonyMgr,(Object[]) null);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}
	

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.d(TAG, "action:" + action);
		
		init(context);
		
		if (PHONE_STATE.equals(action)) {
			String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			Log.i(TAG, "State: " + state);

			String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			
			Log.i(TAG, "In Coming Call: " + number);
			Toast.makeText(context, "number="+number, Toast.LENGTH_LONG).show();
			ArrayList<Contact> al = Constants.CURRENT_SELECTED_CONTACTS;
			if(null == al || al.size() == 0){
				return;
			}
			for(Contact c:al){
				Log.d("ContactName-->", c.getContactName());
				Log.d("ContactNumber-->", c.getContactNumber());
				if(number.endsWith(c.getContactNumber())){
					block(state);
				}
			}
		}
	}
	

	/**
	 * @Title: block
	 * @param state
	 * @return void
	 * @throws
	 */
	private void block(String state){
		
		if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			Log.i(TAG, "静音处理");
			try {
				iTelephony.endCall();
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			//恢复正常来电模式
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			Log.i(TAG, "恢复正常来电模式");
		}
	}

}
