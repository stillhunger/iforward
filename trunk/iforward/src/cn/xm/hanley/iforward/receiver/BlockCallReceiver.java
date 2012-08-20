package cn.xm.hanley.iforward.receiver;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import cn.xm.hanley.iforward.constants.Constants;
import cn.xm.hanley.iforward.domain.Contact;
import cn.xm.hanley.iforward.sqlite.BlockSQLite;
import cn.xm.hanley.iforward.sqlite.HistorySQLite;
import cn.xm.hanley.iforward.utils.DataBaseFactoryUtil;

import com.android.internal.telephony.ITelephony;

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
		init(context);
		if (PHONE_STATE.equals(action)) {
			String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			BlockSQLite bs = DataBaseFactoryUtil.createFordwardDB(context);
			Contact mycontact = bs.queryForwardContactByNumber(number);
			if(null == mycontact){
				return;
			}else{
				String cnum = mycontact.getContactNumber();
				cnum = cnum.replaceAll("-", "");
				cnum = cnum.replaceAll(" ", "");
				if(number.endsWith(cnum)){
					block(state);
					saveToHistory(context,mycontact);
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
			try {
				iTelephony.endCall();
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}
	}
	
	@SuppressWarnings("static-access")
	@SuppressLint("WorldWriteableFiles")
	private void saveToHistory(Context context,Contact c){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh-mm-ss");
		String hdate = sdf.format(date);
		String htime = sdf2.format(date);
		HistorySQLite db = DataBaseFactoryUtil.createHistoryDB(context);
		
		SharedPreferences share = context.getSharedPreferences(Constants.SPS_FORWARD,context.MODE_WORLD_WRITEABLE);
		String fnumber = share.getString(Constants.SPS_FORWARD_NUMBER, "");
		
		db.insert(c.getContactName(), c.getContactNumber(), hdate, htime, fnumber);
	}

}
