# Annotation Sandbox

Playing with annotations 

There are two projects 
* annotation-process
* annotation-user

## Annotation-Process
This project contains the annotations `FooBar` and `Rogue`
It also contains the annotation processor `FooBarProcessor`
The file `javax.annotation.processing.Processor` is located in the `META-INF/services` folder. This is how compile time annotation processors are registered. 

The reason for the annotation processor being placed in a separate project was the need to supply the compilerArgument `-proc:none` to the maven compiler plugin so that annotation processing is disabled. See this [answer in Stack Overflow](http://stackoverflow.com/questions/6967514/maven-example-of-annotation-preprocessing-and-generation-of-classes-in-same-comp/6974117) for more information. 


## Annotation-User
Contains Java classes that are annotated with `FooBar` and/or `Rogue`

### Compile Time processor
When running `mvn clean compile` the annotation processor is triggered.
This should result in a list of the classes that have methods annotated with `FooBar` being written to stdin

    [INFO] --- maven-compiler-plugin:3.3:compile (default-compile) @ annotation-user ---
    [INFO] Changes detected - recompiling the module!
    [INFO] Compiling 2 source files to /Volumes/dev/annotation-sandbox/annotation-user/target/classes
    FooBar annotates the method foo on class Foo with the value FooBar
    FooBar annotates the method anotherMethod on class Foo with the value FooBar
    FooBar annotates the method hello on class HelloWorld with the value hello world


### Run Time processing
There is also a handler that uses Reflection to analyze classes at runtime. The current handler, `FooBarAnnotationRuntimeHandler` looks for all classes in the classpath that have a method annotated with `FooBar` and invokes the method. The current handler assumes that the classes with have a default constructor and that the method annotated with `FooBar` does not have any arguments.  