package cn.xm.hanley.utils;

import java.util.HashMap;

import cn.xm.hanley.constants.Constants;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

/**
 * @Description: 扫描联系人
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-14下午5:32:35
 * @Google+ http://gplus.to/hanleytowne
 */
public class ContactScanner extends Thread {

	private Handler handler;

	private Context context;

	public ContactScanner(Handler handler, Context context) {
		this.handler = handler;
		this.context = context;
	}

	@Override
	public void run() {
		loadContact();
	}

	@SuppressLint("NewApi")
	public void loadContact() {

		Cursor cursor = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		while (cursor.moveToNext()) {
			int nameFieldColumnIndex = cursor
					.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			String contactName = cursor.getString(nameFieldColumnIndex);
			if (null == contactName) {
				continue;
			}

			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			int hasPhone = cursor
					.getInt(cursor
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

			if (hasPhone > 0) {

				Cursor phones = context.getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId, null, null);
				while (phones.moveToNext()) {
					String contacNumber = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					if (null == contacNumber) {
						continue;
					} else {

						// 只显示有姓名和电话的联系人
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("contactName", contactName);
						map.put("contactNumber", contacNumber);
						map.put("lvcheck", false);

						Message msg = handler.obtainMessage(Constants.RESPONSE_CODE_SHOW_CONTACT, map);
						msg.sendToTarget();
					}
					break;// 目前不考虑有多个号码的联系人，故此break
				}
				phones.close();
			}

		}
		cursor.close();
	}

}
