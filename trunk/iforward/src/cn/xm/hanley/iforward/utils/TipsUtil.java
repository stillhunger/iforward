package cn.xm.hanley.iforward.utils;

import cn.xm.hanley.iforward.activity.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/** 
 * @ClassName: TipsUtil 
 * @Description: 提示工具类
 * @author TANGHL
 * @date 2011-5-17 下午04:07:41 
 * Copyright (c) By ANTROU LTD. 2011 
 */
public class TipsUtil {

	private static TipsUtil instance;
	
	private TipsUtil(){
		
	}
	
	public static TipsUtil getInstance() {
		if(instance==null){
			synchronized (TipsUtil.class) {
				if(instance == null){
					instance = new TipsUtil();
				}
			}
		}
		return instance;
	}
	
	
    /**
     * 
    * @Description: 关于
    * @param @param context  
    * @return void 
    * @throws
     */
    public  void shwoAbout(Context context) {   
        AlertDialog.Builder builder = new AlertDialog.Builder(context);   
        builder.setTitle(context.getResources().getString(R.string.about));   
        builder.setMessage(context.getResources().getString(R.string.app_introduction));   
        builder.setPositiveButton(context.getResources().getString(R.string.ok),   
                new DialogInterface.OnClickListener() {   
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }   
                });   
        builder.show();   
    } 
    
	
}
