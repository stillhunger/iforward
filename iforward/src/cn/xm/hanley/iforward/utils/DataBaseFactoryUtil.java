package cn.xm.hanley.iforward.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import cn.xm.hanley.iforward.sqlite.BlockSQLite;

/**
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-16上午11:59:29
 * @Google+ http://gplus.to/hanleytowne
 */
public class DataBaseFactoryUtil {

	
	/**
	 * 得倒拦截对象DB
	 * @param context
	 * @return BlockSQLite
	 * @throws
	 */
	public static BlockSQLite createBlockContactDB(Context context){
		BlockSQLite bs = new BlockSQLite(context);
		SQLiteDatabase sd = bs.getWritableDatabase();
		sd.close();
		return bs;
	}
}
