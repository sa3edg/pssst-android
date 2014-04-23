package com.qafeerlabs.pssst.client.common;

import com.google.gson.Gson;
import com.qafeerlabs.pssst.exception.HttpRequestException;

public class RequestError {
	private boolean hasError = false;
	private String errorCode = "";
	private String errorMessage = "";
	private HttpRequestException requestException;
	public static final String FETCH_BYTE_DATA_ERROR_MESSAGE = "Can not get data from server.";
	public static final String FETCH_BYTE_DATA_ERROR_ERROR_CODE = "11";
	public RequestError()
	{
		
	}
	public RequestError(String errorString){
		RequestError errorObj = new Gson().fromJson(errorString, RequestError.class);
		this.errorCode = errorObj.errorCode;
		this.errorMessage = errorObj.errorMessage;
		this.setHasError(errorObj.hasError);
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public HttpRequestException getRequestException() {
		return requestException;
	}
	public void setRequestException(HttpRequestException requestException) {
		this.requestException = requestException;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

}
