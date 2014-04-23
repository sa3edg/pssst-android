package com.qafeerlabs.pssst.gui;

import com.qafeerlabs.pssst.common.config.Constants;
import com.qafeerlabs.pssst.dao.DBAdapter;
import com.qafeerlabs.pssst.handler.ImagesMemoryHandler;
import com.qafeerlabs.pssst.services.GPSLocationManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class UIDispatcher extends FragmentActivity {

	public static final String NEXT_SCREEN_PARAM = "nextScreenParam";

	private int screenId;
	public static final int SPLASH_ID = 0;
	public static final int MAIN_MENU_ID = 1;
	public static final int MAIN_VIEW_ID = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AbstractScreen screenToShow = null;
		screenId = getIntent().getIntExtra(NEXT_SCREEN_PARAM, SPLASH_ID);
		switch (screenId) {
		case SPLASH_ID:
			init();
			screenToShow = new SplashScreen(this);
			screenToShow.loadScreen();
			break;
//		case MAIN_MENU_ID:
//			screenToShow = new MainMenuView(this);
//			break;
		}
		
	}

	private void init()
	{
		try{
			Constants.init(this.getResources());
			GPSLocationManager.getInstance(this).startTracking();
			DBAdapter.init(this);
			ImagesMemoryHandler.initImageLoader(this);
		}
		catch(Exception ex){
			Log.i("Error", ex.getMessage());
		}
	}
	public FragmentActivity finishCurrentActivity(Activity activity) {
		activity.finish();
		return this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}