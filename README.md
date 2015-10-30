Base skeleton structure to start every new project, based on the design patters suggested by Robert C. Martin *(aka Uncle Bob)* on his clean architecture.

It is divided in three modules:
* **domain**: java library project. Every test is a unit test and all its dependencies are pure java libraries: dagger 2, rxjava, junit and mockito.
* *data**: android library project. It’s the repository that supplies the data which will be consumed by the domain module. Dependencies: domain module, retrofit 2, dagger 2, rxjava, junit, mockito, roboelectric.
* *presentation**: android app project. It links the two previous modules and implement all the views interfaces defined in domain module. Dependencies: domain and data module, dagger 2, rxAndroid, androidannotations and espresso.
