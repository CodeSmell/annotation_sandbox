package codesmell;

import java.lang.reflect.Method;
import java.util.Arrays;

import codesmell.annotation.FooBar;

public class HelloWorld {

	public HelloWorld() {
		inspectAtRuntime();
	}

	@FooBar("hello world")
	public String hello() {
		return "hello";
	}

	public static void main(String[] args) {
		HelloWorld hw = new HelloWorld();
		System.out.println(hw.hello());
	}

	/**
	 * because our FooBar annotation has retention set to RUNTIME we can use
	 * reflection and find it
	 */
	public void inspectAtRuntime() {
		Method[] methods = this.getClass().getMethods();
		Arrays.stream(methods)
			.filter(method -> method.isAnnotationPresent(FooBar.class))
			.forEach((method) -> {
				FooBar ann = method.getAnnotation(FooBar.class);
				System.out.println("Annotated method: " + method.getName() + " with FooBar value:" + ann.value());
			});
	}
}
