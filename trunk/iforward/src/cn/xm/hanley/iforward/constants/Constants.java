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
	
	public final static String SPS_FORWARD = "forward";
	
	public final static String SPS_FORWARD_NUMBER = "forward_number";
	
	/********************SharedPreferences********************/
	
	/**软件设置完成进度*/
	public final static String SPS_COMPLETE_PROGRESS = "complete_progress";
	
	/**号码是否设置完成*/
	public final static String SPS_NUMBER_FLAG = "number_flag";
	
	/**联系人是否设置完成*/
	public final static String SPS_CONTACT_FLAG = "contact_flag";
}
