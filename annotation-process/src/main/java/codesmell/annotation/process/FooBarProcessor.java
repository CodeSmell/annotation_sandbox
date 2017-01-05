package codesmell.annotation.process;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import codesmell.annotation.FooBar;

@SupportedAnnotationTypes(value = { "codesmell.annotation.FooBar" })
public class FooBarProcessor extends AbstractProcessor {

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	/**
	 * process the annotation FooBar @ compile time
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		roundEnv.getElementsAnnotatedWith(FooBar.class).stream()
			.forEach((element) -> {
				FooBar foobarAnnotation = element.getAnnotation(FooBar.class);
				// we take advantage of FooBar being targeted to methods
				// and assume that the enclosing element is a class
				StringBuilder sb = new StringBuilder("FooBar annotates the method " + element.getSimpleName());
				sb.append(" on class " + element.getEnclosingElement().getSimpleName());
				sb.append(" with the value " + foobarAnnotation.value());	
				System.out.println(sb.toString());	
			});
		return true;
	}
}
