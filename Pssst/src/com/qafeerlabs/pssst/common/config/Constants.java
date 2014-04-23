package com.qafeerlabs.pssst.common.config;

import com.qafeerlabs.pssst.gui.R;

import android.content.res.Resources;

public class Constants {

    public static Resources config = null;
    public static String SERVER_URL = "";
    public static String USER_REG_SERVICE_URL = "";
    public static String SEND_PRODUCT_REVIEW_SERVICE_URL = "";

	public static synchronized void init(Resources res) throws Exception{
		config = res;
		SERVER_URL = config.getString(R.string.server_url);
		USER_REG_SERVICE_URL = config.getString(R.string.user_reg_service_url);
		SEND_PRODUCT_REVIEW_SERVICE_URL = config.getString(R.string.send_product_review_service_url);
	}
}
