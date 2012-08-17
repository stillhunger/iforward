package cn.xm.hanley.iforward.fragment;

import cn.xm.hanley.iforward.activity.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 手动添加拦截对象
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-16下午3:53:05
 * @Google+ http://gplus.to/hanleytowne
 */
public class CustomFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fm_customblock, container, false);
	}

	
}
