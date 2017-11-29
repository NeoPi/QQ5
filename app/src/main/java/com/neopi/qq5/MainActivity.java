package com.neopi.qq5;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.neopi.qq5.adapter.MainPageAdapter;
import com.neopi.qq5.widget.RouterLayout;
import com.neopi.qq5.widget.TabItem;

public class MainActivity extends AppCompatActivity {


	private ViewPager mViewPager ;
	private RouterLayout mainRouter ;
	private MainPageAdapter adapter ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		initView();
	}

	private void initView() {

		adapter = new MainPageAdapter(getSupportFragmentManager(),this);

		mViewPager = findViewById(R.id.main_view_pager) ;
		mViewPager.setOffscreenPageLimit(4);
		mViewPager.setAdapter(adapter);

		mainRouter = findViewById(R.id.main_router);
		mainRouter.setupViewPager(mViewPager);
		mainRouter.setOnItemClickListener(new RouterLayout.OnItemClickListener() {
			@Override
			public void onTabSelected(TabItem tabItem, int index) {

			}

			@Override
			public void onTabReleased(TabItem tabItem, int index) {

			}

			@Override
			public void onTabRepeat(TabItem tabItem, int index) {

			}

			@Override
			public void onCenterIconClick() {
				Toast.makeText(MainActivity.this,"center" ,Toast.LENGTH_SHORT).show();
			}
		});
	}

}
