Base skeleton structure to start every new project, based on the design patters suggested by Robert C. Martin *(aka Uncle Bob)* on his clean architecture.

*base_app_android* is the project which we use at [Refineria Web](http://www.refineriaweb.com/servicios/desarrollo-apps-moviles/) to start any new Android project. It presents a structure of 3 layers, domain, data and presentation; this approach has been chosen with the purpose of creating an application that conducts unit testing and also allow the portability between platforms, mainly iOS.

The project is divided in three modules:
* **domain**: java library project. Every test is a unit test and all its dependencies are pure java libraries: dagger 2, rxjava, lombok, junit and mockito.
* **data**: java library project. It’s the repository that supplies the data which will be consumed by the domain module. Dependencies: domain module, retrofit 2, dagger 2, rxjava, rxcache, lombok, junit, and mockito.
* **presentation**: android app project. It links the two previous modules and implement all the views interfaces defined in domain module. Dependencies: domain and data module, dagger 2, rxAndroid, lombok, androidannotations and espresso.

The application implements a minimal example using the Github Api. It's purpose is to illustrate in a simple way the natural workflow derived from this kind of architecture, using the classes created in the foundation packages in every one of the 3 layers. 

To facility the use of this project as a common base one for any new project, a gradle task called *freshStart* has been created. This task removes the earlier mentioned minimal example, leaving the appliaction in an optimal state to start any new project. 

Just run the next command from any terminal located at the root project:

```
$ ./gradlew freshStart
```
After that, build and clean project

## Some considerations:
+ [Lombok Plugin for IntelliJ IDEA](https://github.com/mplushnikov/lombok-intellij-plugin) is required in order to support the annotations generated by [Lombo library](https://github.com/rzwitserloot/lombok)
+ [Spoon](https://github.com/square/spoon) has a [Spoon Gradle Plugin](https://github.com/stanfy/spoon-gradle-plugin) that allows you running a specific test suite or a test class by passing a parameter, e.g:
```
        $ ./gradlew clean build spoon -P classNameTestTuRun=fully.qualified.TestCase
        $ ./gradlew clean build spoon -P classNameTestTuRun=presentation.common.SuiteIntegration
```

+ To use [Gradle Retrolambda Plugin](https://github.com/evant/gradle-retrolambda) you will need to install [JDK 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) and create JAVA8_HOME environment variable. The same for your previous JDK (e.g: JAVA7_HOME). So please make sure that JAVA8_HOME and JAVA7_HOME has been added to the path of the system
```
    $ nano ~/.bash_profile
	export JAVA8_HOME=/Library/Java/JavaVirtualMachines/jdk8_etc/Contents/Home
	export JAVA7_HOME=/Library/Java/JavaVirtualMachines/jdk7_etc/Contents/Home
	export PATH=$PATH:$JAVA8_HOME:$JAVA7_HOME
```

Also, specify in Android Studio the JDK Location pointing to jdk8
/Library/Java/JavaVirtualMachines/jdk8_etc/Contents/Home
