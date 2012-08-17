package cn.xm.hanley.iforward.utils;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xm.hanley.iforward.constants.Constants;
import cn.xm.hanley.iforward.domain.History;
import cn.xm.hanley.iforward.sqlite.HistorySQLite;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

/**
 * 扫描历史记录
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-17下午3:51:22
 * @Google+ http://gplus.to/hanleytowne
 */
public class HistoryScanner extends Thread {

	private Handler handler;

	private Context context;

	public HistoryScanner(Handler handler, Context context) {
		this.handler = handler;
		this.context = context;
	}

	@Override
	public void run() {
		loadHistory();
	}

	@SuppressLint("NewApi")
	public void loadHistory() {
		HistorySQLite db = DataBaseFactoryUtil.createHistoryDB(context);
		db.insert("Hanley", "18687876545", "2012-08-16", "10:08:12","110");
		ArrayList<History> hlist = db.queryHistory();
		if(null != hlist){
			Message msg  = handler.obtainMessage(Constants.RESPONSE_CODE_SHOW_HISTORY, hlist);
			msg.sendToTarget();
		}
	}

}
