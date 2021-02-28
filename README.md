# MvcJsonMockAPI
[![Build Status](https://travis-ci.org/dexecutor/dexecutor-core.svg?branch=master)](https://travis-ci.org/dexecutor/dexecutor-core)
[![Coverage Status](https://coveralls.io/repos/github/dexecutor/dexecutor-core/badge.svg?branch=master)](https://coveralls.io/github/dexecutor/dexecutor-core?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.dexecutor/dexecutor-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.dexecutor/dexecutor-core)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


MvcJsonMockAPI simplifies the way we can create a rest-api test.

Refer [wiki](https://github.com/carloshn90/mvcJsonMockAPI/wiki/Documentation) for more Details.


| Stable Release Version | JDK Version compatibility | Release Date |
| ------------- | ------------- | ------------|
|   |  |  |


## Documentation

Get started with MvcJsonMockAPI, below you can see an example:

```java
@MvcJsonMockApi(jsonPath = "controller-test.json")
class ControllerTest {

    private MvcJsonMock mvcJsonMock;

    @BeforeEach
    void setUp() {
        Controller controller = new Controller();
        this.mvcJsonMock = MvcJsonMockBuilder.standaloneSetup(controller).build();
    }

    @TestEndPoint(name = "elements-not-found-empty-body")
    void getElements_ElementsNotFound_EmptyBody() throws ApiException {
        this.mvcJsonMock.callEndPoint();
    }
}
```

For more information go to the [documentation](https://github.com/carloshn90/mvcJsonMockAPI/wiki/Documentation).

Here you can see an example project [WebMvcJsonExample](https://github.com/carloshn90/WebMvcJsonExample/blob/main/src/test/java/com/example/mvc/json/api/examplemvcjsonapi/controller/UserControllerTest.java)

## License

MvcJsonMockAPI is licensed under **MIT License**.

## News
* Version **0.0.1** released on 26/02/2021.

## Maven Repository

Coming soon

## BUILDING from the sources

As it is maven project, building is just a matter of executing the following in your console:

	mvn package

This will produce the mvcJsonMockAPI-x.y.z.jar file under the target directory.

## Support
If you need help using MvcJsonMockAPI feel free to drop an email or create an issue in github.com (preferred)

## Contributions
To help MvcJsonMockAPI development you are encouraged to
* Provide suggestion/feedback/Issue
* pull requests for new features
* Star :star2: the project

<a href="https://www.linkedin.com/in/carlos-hernandez-navarro/"><img width="120" heigh="16" alt="View My profile on LinkedIn" src="https://cdn.business2community.com/wp-content/uploads/2016/02/View-my-LinkedIn-profile-image-3-300x140.png.png"></a>
	
