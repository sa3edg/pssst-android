package com.qafeerlabs.pssst.client;

import static com.qafeerlabs.pssst.common.config.Constants.USER_REG_SERVICE_URL;
import static com.qafeerlabs.pssst.common.config.Constants.SEND_PRODUCT_REVIEW_SERVICE_URL;

import org.json.JSONObject;

import android.app.Activity;

import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.qafeerlabs.pssst.client.common.Request;
import com.qafeerlabs.pssst.client.processing.FacebookProcessor;

public final class RequestFactory {

	private static Request createGeneralRequest(
			String requestBody, String requestName, String serviceURL, int serviceMethod, boolean isAsync) {
		Request request = new Request();
		request.setOrderType(Request.SIMPLE_ORDER_TYPE);
		request.setRequestName(requestName);
		request.setRequestBody(requestBody);
		request.setRequestURL(serviceURL);
		request.setRequestMethod(serviceMethod);
		//Set is Async order to prevent interaction with the GUI
		//should be true if need to  receive a response from the server.
		request.setAsync(isAsync);
		return request;
	}
	private static Request createGeneralFacebookRequest(Activity mActivity,Session session,
			String requestName, boolean isAsync) {
		Request request = new Request();
		request.setOrderType(Request.FACEBOOK_ORDER_TYPE);
		FacebookProcessor fProcessor = new FacebookProcessor();
		fProcessor.setmActivity(mActivity);
		fProcessor.setSession(session);
		request.setProcessor(fProcessor);
		request.setRequestName(requestName);
		//Set is Async order to prevent interaction with the GUI
		//should be true if need to  receive a response from the server.
		request.setAsync(isAsync);
		return request;
	}
	
//	private static JSONObject createAuthenticationJSONObject()throws Exception{
//		JSONObject authenticationObj = new JSONObject();
//		authenticationObj.put(Request.MOBILE_NUMBER, User.mySetting.getDefaultMobileNumber().getPhoneNumber());
//		authenticationObj.put(Request.ACCESS_TOKEN, User.mySetting.getDefaultMobileNumber().getAccessToken());
//		authenticationObj.put(Request.MOBILE_NUMBER, "0563307435");
//		authenticationObj.put(Request.ACCESS_TOKEN, "123");
//		return authenticationObj;
//	}
	
	public static Request AddOrUpdateUser(GraphUser user) throws Exception{
//		JSONObject requestBody = new JSONObject();
//		requestBody.put("", user.getId());
//		requestBody.put("", user.getUsername());
//		requestBody.put("", user.getFirstName());
//		requestBody.put("", user.getMiddleName());
//		requestBody.put("", user.getLastName());
//		requestBody.put("", user.getName());
//		requestBody.put("", user.getLink());
//		String requestBodyString = requestBody.toString();
		String temp = user.getInnerJSONObject().toString();
		return createGeneralRequest(user.getInnerJSONObject().toString(), Request.CREATE_OR_UPDATE_USER, USER_REG_SERVICE_URL, Request.POST_METHOD,  false);
	}
	public static Request sendScannedBarCode(String uid, String productId, String barcode) throws Exception{
		JSONObject requestBody = new JSONObject();
		String requestBodyString = requestBody.toString();
		return createGeneralRequest(requestBodyString, Request.SEND_PRODUCT_REVIEW, SEND_PRODUCT_REVIEW_SERVICE_URL, Request.POST_METHOD,  false);
	}
	
	public static Request retrieveUserProfile(Activity mActivity, Session session) throws Exception{
		return createGeneralFacebookRequest(mActivity, session, Request.FB_GET_USER_PROFILE,  false);
	}

}
