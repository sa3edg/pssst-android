package com.qafeerlabs.pssst.client.common;

import com.google.gson.Gson;

public class Response {

	// the json data from the response
	private String data = "";
	// response object, used for images and videos.
	private byte[] byteResponse;
	private Object responseObject;
	private Request request;
	private RequestError error;

	public Response(String response) {
		Response responseObj = new Gson().fromJson(response, Response.class);
		this.data = responseObj.data;
		this.error = responseObj.error;
		
	}
	
	public Response(Object response) {
		this.setResponseObject(response);
		if(response == null)
		{
			createHasErrorNode();
		}
	}

	public Response(byte[] byteResponse) {
		
		this.byteResponse = byteResponse;
		if(byteResponse == null || byteResponse.length == 0)
		{
			createHasErrorNode();
		}
	}

	public RequestError getError() {
		return error;
	}

	public void setError(RequestError error) {
		this.error = error;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public byte[] getByteResponse() {
		return byteResponse;
	}

	public void setByteResponse(byte[] byteResponse) {
		this.byteResponse = byteResponse;
	}
	private void createHasErrorNode(){
		this.error = new RequestError();
		this.error.setHasError(true);
		this.error.setErrorCode(RequestError.FETCH_BYTE_DATA_ERROR_ERROR_CODE);
		this.error.setErrorMessage(RequestError.FETCH_BYTE_DATA_ERROR_MESSAGE);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
}
