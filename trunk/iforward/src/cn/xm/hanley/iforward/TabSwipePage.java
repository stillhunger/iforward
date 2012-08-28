package cn.xm.hanley.iforward;

import cn.xm.hanley.iforward.activity.AboutActivity;
import cn.xm.hanley.iforward.activity.GuidePageActivity;
import cn.xm.hanley.iforward.activity.R;
import cn.xm.hanley.iforward.fragment.CustomFragment;
import cn.xm.hanley.iforward.fragment.ForwardFragment;
import cn.xm.hanley.iforward.fragment.HistoryFragment;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 主界面
 * @author HanleyTowne
 * @email  tanghly@gmail.com
 * @date   2012-8-22下午3:19:18
 * @Google+ http://gplus.to/hanleytowne
 */
public class TabSwipePage extends FragmentActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabswipe_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
        	
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_title, menu);
        return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		
		switch(item.getItemId()){
		case R.id.menu_about:
			intent = new Intent(this,AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_guide:
			intent = new Intent(this,GuidePageActivity.class);
			intent.putExtra("flag", true);
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            
            Fragment newFragment = null;
            switch(i){
	    	case 0:
	    		newFragment = new CustomFragment();
	    		break;
			case 1:
				newFragment = new ForwardFragment();
	    		break;
			case 2:
				newFragment = new HistoryFragment();
	    		break;
	    	default:
	    		break;
	    	}
	    	
	    	return newFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
            	case 0: return getString(R.string.forward_number).toUpperCase();
                case 1: return getString(R.string.forward_object).toUpperCase();
                case 2: return getString(R.string.forward_history).toUpperCase();
            }
            return null;
        }
    }

}
