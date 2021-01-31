# Showcase: Selenium Remote and docker-selenium

### Purpose
The purpose of this project is to showcase a selenium RemoteWebDriver
that uses a WebDriverService, provided in a docker-selenium instance, 
to execute tests on a webpage. 


### Scope
The showcase contains tests that are designed to target some of the
webelements on a referenced [sample page][1]


#### Project File Structure 
The project is structured as a Maven projected, with the page object model files and related aggregate functions located in the main branch, and the tests are kept in the corresponding test branch, as illustrated in [Figure1][Figure1] 
``` bash 
src
    +---main
    |   \---java
    |       +---pages
    |       |       SamplePagePO.java
    |       |       
    |       \---utils
    |               WrapperFunctions.java
    |               
    \---test
        \---java
            \---showcase
                    SamplePageTest.java
```
[Figure1]:Figure1
Figure1


## Start Showcase

### Dependencies

* Docker (Config: Linux containers)     - [Install Docker][2]
* Selenium ChromeDriver                 - [Chrome Driver][3]
* Maven
* Java libraries (Maven dependencies)
    1. org.slf4j (slf4j-api 1.7.5)      - [Simple Logging Facade for Java][4]
    2. org.slf4j (slf4j-simple 1.7.5)
    3. junit (junit 4.12)
    4. org.seleniumhq.selenium (selenium-java 3.141.59) - [Java Selenium 3][5]
    5. org.apache.maven.plugins (maven-surefire-report-plugin 3.0.0-M5)
    6. org.junit.jupiter (junit-jupiter 5.7.0) 
    7. commons-io (commons-io 2.5)
    8. org.apache.maven.plugins (maven-compiler-plugin)

### Preparation

1. Placement of External Files 
    * Place chromedriver.exe at location "C:\Testing_Tools\WebDriver\chromedriver.exe"

2. Start docker-selenium Container
    ``` bash
    # Start Selenium stand-alone container (Config: 2GB shared memory @ localhost:4449)
    $ docker container run --name selenium-solo -d -p 4449:4444 --shm-size 2g selenium/standalone-chrome:4.0.0-beta-1-prerelease-20210128
    ```


### Execute Tests

1. Use Maven to Execute Tests
    ``` bash
    $ mvn clean test
    # OR
    $ mvn install
    ```


### Post-check

Output of Execution should look like this:
``` bash
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 23.042 sec

Results :

Tests run: 7, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  26.256 s
[INFO] Finished at: 2021-01-31T20:50:40+01:00
[INFO] ------------------------------------------------------------------------
```


### Clean-up

1. Clean project files by maven
``` bash
mvn clean
```
2. Stop Selenium Container
``` bash
# Stop Selenium stand-alone container
$ docker container kill selenium-solo

# Check that Selenium stand-alone container is stopped
$ docker container ps
```

#### To-Do
Trivial
- [ ] Substitute junit with TestNG to use xml to organize in suites etc.? 

[1]: https://www.testandquiz.com/selenium/testing.html
[2]: https://www.docker.com/get-started
[3]: https://chromedriver.chromium.org/getting-started
[4]: http://www.slf4j.org/
[5]: https://www.selenium.dev/documentation/en/selenium_installation/installing_selenium_libraries/