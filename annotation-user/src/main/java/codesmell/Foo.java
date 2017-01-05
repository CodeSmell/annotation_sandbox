package codesmell;

import codesmell.annotation.FooBar;
import codesmell.annotation.Rouge;

@Rouge
public class Foo {

	@FooBar
	public String foo() {
		return "bar";
	}
	
	@FooBar
	public String anotherMethod() {
		return "yay";
	}
}
