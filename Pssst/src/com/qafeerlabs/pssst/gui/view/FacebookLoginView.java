package com.qafeerlabs.pssst.gui.view;

import java.util.Arrays;
import java.util.List;

import com.facebook.model.GraphUser;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.qafeerlabs.pssst.client.OrderCoordinator;
import com.qafeerlabs.pssst.client.RequestFactory;
import com.qafeerlabs.pssst.client.common.Request;
//import com.qafeerlabs.pssst.client.common.Request;
import com.qafeerlabs.pssst.client.common.Response;
import com.qafeerlabs.pssst.common.model.User;
import com.qafeerlabs.pssst.gui.CustomProgressDialog;
import com.qafeerlabs.pssst.gui.IActivity;
import com.qafeerlabs.pssst.gui.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class FacebookLoginView extends Activity implements IActivity {

	private static final String TAG = FacebookLoginView.class.getSimpleName();

	private UiLifecycleHelper uiHelper;

//	private static final List<String> readPermissions = Arrays.asList("email",
//			"user_location");

	private LoginButton authButton;
	private boolean callMeRequest = false;
	private CustomProgressDialog progress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progress = new CustomProgressDialog(this);
		setContentView(R.layout.facebook_login_view);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		authButton = (LoginButton) findViewById(R.id.authButton);
		//authButton.setReadPermissions(readPermissions);
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
			if (!callMeRequest) {
				try {
					callMeRequest = true;
					OrderCoordinator.handleOrder(this,
							RequestFactory.retrieveUserProfile(this, session));
				} catch (Exception ex) {

				}
			}

		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void preExecution() {
		// TODO Auto-generated method stub
		showProgressBar();
	}

	@Override
	public void postExecution(Response response) {
		// TODO Auto-generated method stub
		try {
			if (response.getRequest().getRequestName()
					.equals(Request.FB_GET_USER_PROFILE)) {
				
				if (response.getResponseObject() != null && response.getError() == null) {
					User.profile = (GraphUser) response.getResponseObject();
					// execute addOrUpdate request
					OrderCoordinator.handleOrder(this,
							RequestFactory.AddOrUpdateUser(User.profile));
				} else {
					// show error message
					closeProgressBar();
				}

			} else if (response.getRequest().getRequestName()
					.equals(Request.CREATE_OR_UPDATE_USER)) {
				// show main view
			}
		} catch (Exception ex) {
			// show error message
			closeProgressBar();
		}
	}

	public void showProgressBar() {
		if (progress != null) {
			progress.show();
		}
	}

	public void closeProgressBar() {
		if (progress != null) {
			progress.dismiss();
		}
	}
}
