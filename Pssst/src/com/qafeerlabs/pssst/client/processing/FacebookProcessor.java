package com.qafeerlabs.pssst.client.processing;

import android.app.Activity;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FacebookProcessor extends AbstractHttpClientProcessor {
	private Activity mActivity;
	private Session session;
	private static final String TAG = "facebook_processor";

	public void process() throws Exception {

		if (request.getRequestName().equals(
				com.qafeerlabs.pssst.client.common.Request.FB_GET_USER_PROFILE)) {
			getUserProfile();
		} else if (request.getRequestName().equals(
				com.qafeerlabs.pssst.client.common.Request.FB_LOGOUT_REQUEST)) {
			logOut();
		}
	}
	
	public void preprocess() {
		super.preprocess();
	}

	public void terminate() {
		super.terminate();
	}

	public boolean isLoggedin() {
		final boolean[] isLoggedIn = new boolean[1];
		// start Facebook Login
		Session.openActiveSession(getmActivity(), false,
				new Session.StatusCallback() {
					// callback when session changes state

					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						if (session.isOpened()) {
							isLoggedIn[0] = true;
						}
					}
				});
		return isLoggedIn[0];
	}

	private void getUserProfile() throws Exception {
		// start Facebook Login
		Request.newMeRequest(getSession(), new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				Log.i(TAG, "onCompleted");
				Log.i(TAG, response.toString());
				com.qafeerlabs.pssst.client.common.Response res = new com.qafeerlabs.pssst.client.common.Response(
						user);
				prepareResponse(res);
				getActivity().postExecution(res);
			}
		}).executeAsync();
	}

	private void logOut() throws Exception {
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().closeAndClearTokenInformation();
		}
	}

	public Activity getmActivity() {
		return mActivity;
	}

	public void setmActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
