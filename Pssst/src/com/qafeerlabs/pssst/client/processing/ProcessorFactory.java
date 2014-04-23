package com.qafeerlabs.pssst.client.processing;

import com.qafeerlabs.pssst.client.common.Request;
import com.qafeerlabs.pssst.exception.HttpRequestException;
import com.qafeerlabs.pssst.gui.IActivity;


public class ProcessorFactory {


	/** Singleton instance. */
	private static ProcessorFactory self = null;
	
	private ProcessorFactory()
	{
		
	}

	/**
	 * Singleton getter.
	 * 
	 * @return the singleton instance.
	 */
	public static synchronized ProcessorFactory getInstance() {
		if (self == null) {
			ProcessorFactory temp = new ProcessorFactory();
			self = temp;
		}
		return self;
	}

	/**
	 * Shortcut for {@link #createProcessor(Request, Element)}.
	 * 
	 * @param request
	 *            the request
	 * @return the created processor
	 */
	public static IProcessor create(IActivity activity, Request request) {
		return getInstance().createProcessor(activity, request);
	}

	/**
	 * The factory method to create a processor depending on the schemaType of
	 * the request.
	 * 
	 * @param request
	 *            the request
	 * @return the created processor
	 * @throws BadArgumentException
	 *             on unknown schema type
	 */
	public IProcessor createProcessor(IActivity activity, Request request) {
		// get the schema type of the request
		String orderType = request.getOrderType();
		assert orderType != null;
        
		IProcessor processor = null;
		// create processor
		if(Request.SIMPLE_ORDER_TYPE.equals(orderType))
			processor = createProcessor(orderType);
		else
			processor = request.getProcessor();

		// if failed, signal error
		if (processor == null) {
			throw new HttpRequestException();
		}

		// provide data
		processor.setRequest(request);
		processor.setActivity(activity);

		return processor;
	}

	protected IProcessor createProcessor(String orderType) {
			return new DefaultHttpCLientProcessor();
	}

}
