package cn.xm.hanley.iforward.activity;


import cn.xm.hanley.iforward.fragment.ContactFragment;
import cn.xm.hanley.iforward.fragment.CustomFragment;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

@SuppressLint("NewApi")
public class ForwardMain extends Activity implements ActionBar.OnNavigationListener {
	
	 private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragment_main);
		
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayHomeAsUpEnabled(true);  
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.custom_block),
                                getString(R.string.contact_block),
                        }),
                this);
	}

	  @Override
	    public void onRestoreInstanceState(Bundle savedInstanceState) {
	        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
	            getActionBar().setSelectedNavigationItem(
	                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
	        }
	    }

		@Override
	    public void onSaveInstanceState(Bundle outState) {
	        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
	                getActionBar().getSelectedNavigationIndex());
	    }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.menu_title, menu);
	        return true;
	    }

	    

	    @Override
	    public boolean onNavigationItemSelected(int position, long id) {
	    	android.support.v4.app.Fragment newFragment = null;
	    	
	    	switch(position){
	    	case 0:
	    		newFragment = new CustomFragment();
	    		break;
			case 1:
				newFragment = new ContactFragment();
	    		break;
	    	default:
	    		break;
	    	}
	    	
//	    	FragmentManager fm = getFragmentManager();
//	    	FragmentTransaction ft = fm.beginTransaction();
//	    	ft.replace(R.id.container, newFragment);
//	    	ft.commit();
	        return true;
	    }
	
}
