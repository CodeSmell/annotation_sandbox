package codesmell.annotation.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.reflect.ClassPath;

import codesmell.annotation.FooBar;

public class FooBarAnnotationRuntimeHandler {

	public void annotationProcess() throws Exception {
		// find the class w/ an annotation
		ClassLoader cl = getClass().getClassLoader();
		Set<ClassPath.ClassInfo> classesInPackage = ClassPath.from(cl).getTopLevelClassesRecursive("codesmell");
		classesInPackage.stream()
			// turn stream of ClassInfo into a stream of Class
			.map(classInfo -> loadClass(classInfo))
			// turn stream of Class into stream of FooBarHolders
			// using flatMap to avoid stream of stream of FooBarHolders
			.flatMap(klass -> fooBarMethods(klass))
			// invoke the annotated method
			.forEach(fooBarInfo -> {
				fooBarInfo.invoke();
			});
	}

	public Class loadClass(ClassPath.ClassInfo classInfo) {
		Class klass = null;
		try {
			ClassLoader cl = getClass().getClassLoader();
			klass = cl.loadClass(classInfo.getName());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return klass;
	}

	public Stream<FooBarHolder> fooBarMethods(Class klass) {
		Method[] methods = klass.getMethods();
		return Arrays.stream(methods)
				.filter(method -> method.isAnnotationPresent(FooBar.class))
				.map(method -> new FooBarHolder(klass, method));
	}

	public final class FooBarHolder {
		private final Class klass;
		private final Method method;

		public FooBarHolder(Class klass, Method method) {
			this.klass = klass;
			this.method = method;
		}

		public void invoke() {
			try {
				System.out.println(method.invoke(klass.getConstructor().newInstance()));
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

}
