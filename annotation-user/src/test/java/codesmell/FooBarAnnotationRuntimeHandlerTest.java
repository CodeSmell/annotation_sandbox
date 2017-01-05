package codesmell;

import org.junit.Before;
import org.junit.Test;

import codesmell.annotation.handler.FooBarAnnotationRuntimeHandler;

public class FooBarAnnotationRuntimeHandlerTest {
	FooBarAnnotationRuntimeHandler handler;
	
	@Before 
	public void init() {
		handler = new FooBarAnnotationRuntimeHandler();
	}
	
	@Test
	public void test() throws Exception {
		handler.annotationProcess();
	}
}
