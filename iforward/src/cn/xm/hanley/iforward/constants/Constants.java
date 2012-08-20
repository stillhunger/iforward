package cn.xm.hanley.iforward.constants;

import java.util.ArrayList;

import cn.xm.hanley.iforward.domain.Contact;



/**
 * @Description: 不解释
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-7-10下午10:16:47
 * @Google+ http://gplus.to/hanleytowne 
 */
public class Constants {
	
	/**当前选中的联系人 FIXME 后续更改*/
	public static ArrayList<Contact> CURRENT_SELECTED_CONTACTS;
	
	/**显示联系人*/
	public static final int RESPONSE_CODE_SHOW_CONTACT = 1;
	
	/**显示历史转接*/
	public static final int RESPONSE_CODE_SHOW_HISTORY = 2;
	
	/**显示被转接记录*/
	public static final int RESPONSE_CODE_SHOW_FORWARD = 3;
	
	/**刷新转接记录*/
	public static final int RESPONSE_CODE_SHOW_REFRESH = 4;
	
	/**导出到SDCARD*/
	public static final int RESPONSE_CODE_EXPORT_SDCARD = 5;
	
	
	/********************SharedPreferences********************/
	
	public final static String SPS_FORWARD = "forward";
	
	public final static String SPS_FORWARD_NUMBER = "forward_number";
}
