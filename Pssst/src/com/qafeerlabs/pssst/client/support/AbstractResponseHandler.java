package com.qafeerlabs.pssst.client.support;
import static com.qafeerlabs.pssst.client.common.IRequest.RESPONSE_TYPE_BOOLEAN;
import static com.qafeerlabs.pssst.client.common.IRequest.RESPONSE_TYPE_JSON_ARRAY;
import static com.qafeerlabs.pssst.client.common.IRequest.RESPONSE_TYPE_JSON_NODES;
import static com.qafeerlabs.pssst.client.common.IRequest.RESPONSE_TYPE_JSON_OBJECT;
import static com.qafeerlabs.pssst.client.common.IRequest.RESPONSE_TYPE_STRING;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.qafeerlabs.pssst.client.common.Response;
import com.qafeerlabs.pssst.common.model.IModel;
import com.qafeerlabs.pssst.exception.IlligalResponseType;

public abstract class AbstractResponseHandler {
	
	public Object handleResponse(Response response,IModel modelType, Type collectionType)
			throws Exception {

		if(response == null){
			return null;
		}
		String jsonObject = response.getData();
		Log.i("response array", jsonObject);
		
		// handle json array response
	    if (RESPONSE_TYPE_JSON_ARRAY.equals(response.getRequest().getResponseType())) {

			return handleJsonArrayResponse(response, collectionType);
		}
	    // handle json array response
	    else if (RESPONSE_TYPE_JSON_OBJECT.equals(response.getRequest().getResponseType())) {

				return handleJsonObjectResponse(response, modelType);
			}
	    // handle json nodes response
	    else if (RESPONSE_TYPE_JSON_NODES.equals(response.getRequest().getResponseType())) {

			return handleJsonNodesResponse(jsonObject);
		}
		// handle boolean value in response node
		else if (RESPONSE_TYPE_BOOLEAN.equals(response.getRequest().getResponseType())) {

			return Boolean.valueOf(jsonObject);
		}
		// handle String value in response node
		else if (RESPONSE_TYPE_STRING.equals(response.getRequest().getResponseType())) {

			return new String(jsonObject);
		}
		
		return null;
	}

	private List<? extends IModel> handleJsonArrayResponse(Response jsonArray, Type collectionType)
			throws Exception {
		if(collectionType == null)
		{
			throw new IlligalResponseType();
		}
		
		List<? extends IModel> poolsList = new Gson().fromJson(jsonArray.getData(), collectionType);

		return poolsList;
	}
	
	private IModel handleJsonObjectResponse(Response jsonArray, IModel modelType)
			throws Exception {
		if(modelType == null)
		{
			throw new IlligalResponseType();
		}
		
		IModel model = new Gson().fromJson(jsonArray.getData(), modelType.getClass());
		return model;
	}
	
	
	private Map<String, JSONObject> handleJsonNodesResponse(String jsonString)
			throws Exception {
		if(jsonString == null || "".equals(jsonString))
		{
			return null;
		}
		Map<String, JSONObject> jsonObjects = new Hashtable<String, JSONObject>();
		JSONObject jObject = new JSONObject(jsonString.trim());
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            if( jObject.get(key) instanceof JSONObject ){
            	jsonObjects.put(key, (JSONObject)jObject.get(key));
            }
        }
		return jsonObjects;
	}

}
