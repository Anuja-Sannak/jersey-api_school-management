package com.api.listener;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import com.api.resource.AppExecutor;

public class AppEventListener implements ApplicationEventListener{

	@Override
	public void onEvent(ApplicationEvent event) {
		
		if(event.getType() == ApplicationEvent.Type.DESTROY_FINISHED) {
			System.out.println("Jersey api stoping .... cleaning up the resourses...");
			AppExecutor.shutdown();
		}
		
	}

	@Override
	public RequestEventListener onRequest(RequestEvent requestEvent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}