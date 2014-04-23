package com.qafeerlabs.pssst.client;

import android.util.Log;

import com.qafeerlabs.pssst.client.common.Request;
import com.qafeerlabs.pssst.client.processing.IProcessor;
import com.qafeerlabs.pssst.client.processing.ProcessorFactory;
import com.qafeerlabs.pssst.gui.IActivity;

public class OrderCoordinator {
	
	private static OrderCoordinator self = null;
	private OrderCoordinator()
	{
		
	}
	
	public static void createInstance()
	{
		if(self == null)
		{
			self = new OrderCoordinator();
		}
	}
	public static void handleOrder(IActivity activity,Request request)
	{
		IProcessor processor = null;
		try
		{
			
			processor = ProcessorFactory.create(activity,request);
			processor.preprocess();
			processor.process();
		}
		catch(Exception ex)
		{
			Log.i("Error", ex.getMessage());
		}
		finally
		{
			if(processor != null)
				processor.terminate();
		}
	}

}
