package com.qafeerlabs.pssst.gui;

import com.qafeerlabs.pssst.client.common.Response;


public interface IActivity {
	
	/**
	 * <pre>
	 * -execution event
	 * @param response
	 */
	void preExecution();

	/**
	 * post execution event
	 * 
	 * @param response
	 */
	void postExecution(Response response);

}
