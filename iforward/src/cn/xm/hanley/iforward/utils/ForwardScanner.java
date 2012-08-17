package cn.xm.hanley.iforward.utils;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xm.hanley.iforward.constants.Constants;
import cn.xm.hanley.iforward.domain.Contact;
import cn.xm.hanley.iforward.domain.History;
import cn.xm.hanley.iforward.sqlite.BlockSQLite;
import cn.xm.hanley.iforward.sqlite.HistorySQLite;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

/**
 * 转接对象扫描
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-17下午5:51:38
 * @Google+ http://gplus.to/hanleytowne
 */
public class ForwardScanner extends Thread {

	private Handler handler;

	private Context context;

	public ForwardScanner(Handler handler, Context context) {
		this.handler = handler;
		this.context = context;
	}

	@Override
	public void run() {
		loadForward();
	}

	@SuppressLint("NewApi")
	public void loadForward() {
		BlockSQLite db = DataBaseFactoryUtil.createFordwardDB(context);
		db.insert("Lacey", "18678474837");
		ArrayList<Contact> contacts = db.queryForwardContact();
		if(null != contacts){
			Message msg  = handler.obtainMessage(Constants.RESPONSE_CODE_SHOW_FORWARD, contacts);
			msg.sendToTarget();
		}
	}

}
