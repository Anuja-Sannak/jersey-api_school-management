package com.api.resource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutor {

	private static final ExecutorService executor = Executors.newFixedThreadPool(5);

	private AppExecutor() {
		
	}

	public static ExecutorService getExecutor() {
		return executor;
	}
	
	public static void shutdown() {
		System.out.println("Shuting down ExecutorService....");
		executor.shutdown();
	}
}
