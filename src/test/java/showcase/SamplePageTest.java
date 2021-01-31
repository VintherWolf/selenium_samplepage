package showcase;

import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.SamplePagePO;
import utils.WrapperFunctions;

import static org.hamcrest.CoreMatchers.containsString;
import static utils.WrapperFunctions.saveScreenCapture;


/**
 *      Author:             Daniel K.V. Wolf
 *      Created:            27-01-2021
 *      Description:        Showcase with Selenium WebDriver
 *      Dependencies:       1. Chrome Webdriver,
 *                          2. Selenium 3 or 4 WebDriver Jar's
 *                          3. utils.WrapperFunctions.java
        External:           Uses Test Webpage:   https://www.testandquiz.com/selenium/testing.html
*/

public class SamplePageTest {


    private static final String chromeDriverPath = "C:\\Testing_Tools\\WebDriver\\chromedriver.exe";
    private static RemoteWebDriver driver = null;
    private static ChromeDriverService service;
    private static final Logger log = LoggerFactory.getLogger(SamplePageTest.class);

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromeDriverPath))
                .usingAnyFreePort()
                .build();
        service.start();
        log.info("Service, Chrome Driver: Start Service was Called");
    }

    @AfterClass
    public static void stopService() {
        log.info("Service, Chrome Driver: Stop Service was Called");
        service.stop();
    }

    @Before
    public void createDriver() {
        /* Assemble Driver Specific Options */
        ChromeOptions options = new ChromeOptions();
        // Used as Environment Variable by the SUT
        options.setCapability("marionette",true);
        // Enable Logging
        LoggingPreferences logs = new LoggingPreferences();
        // Set Log levels to Include
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.CLIENT, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        logs.enable(LogType.PERFORMANCE, Level.ALL);
        logs.enable(LogType.PROFILER, Level.ALL);
        logs.enable(LogType.SERVER, Level.ALL);
        // Inject the Log preferences into the options object
        options.setCapability(CapabilityType.LOGGING_PREFS, logs);


        /* Create The Remote Web Driver that Connects to the Service Driver */
        driver = new RemoteWebDriver(service.getUrl(), options);

        // Use implicit timeout to poll element for x amount of time
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        log.info("Remote, Chrome Driver: " +
                "Create and Connect new Chrome Driver via Service");
    }

    @After
    public void cleanUp() {

        try {
            Logs logs = driver.manage().logs();
            LogEntries logEntries = logs.get(LogType.DRIVER);

            for (LogEntry logEntry : logEntries) {
                System.out.println(logEntry.getMessage());
            }
        }
        catch (Exception e) {
            System.out.println("ERROR: Unable to show output of logs"  + e);
        }

        driver.quit();
        log.info("Remote, Chrome Driver: Quit Chrome Driver was Called");
    }

    //region GenerateAlertBox

    @Test(timeout = 6000)
    @DisplayName("Sample Page: Generates an Alert Box")
    public void SamplePage_GenerateAlertBox_ButtonClicked_AlertBoxIsShown() {

        log.info("TEST CASE: Generate Alert Box");

        /* Arrange */
        SamplePagePO samplePage = new SamplePagePO(driver);
        boolean modalDialogSeen;

        /* Act */
        samplePage.generateAlertBox();
        modalDialogSeen = samplePage.alertBoxIsPresent();
        // Alert Box shall be closed before interacting with driver again
        samplePage.dismissAlertBox();

        /* Assert */
        // Alert Box is visible
        Assert.assertTrue(modalDialogSeen);
    }

    @Test(timeout = 6000)
    @DisplayName("Sample Page: Alert Box is Accepted and Closed")
    public void SamplePage_GenerateAlertBox_AcceptAlert_AlertBoxIsClosed() {

        log.info("TEST CASE: Sample Page Alert Box is Accepted and Closed");

        /* Arrange */
        SamplePagePO samplePage = new SamplePagePO(driver);

        /* Act */
        samplePage.generateAlertBox();
        samplePage.acceptAlertBox();
        // Log Screenshot Capture to confirm that Alert box is closed
        String filename = "alertboxWasClosed.png";
        if (saveScreenCapture(driver, filename)) {
            log.info("SUCCESS: Saved Screenshot " + filename);
        }
        else {
            log.info("FAILURE: Unable to save screenshot " + filename);
        }

        /* Assert */
        // Alert Box is closed
        Assert.assertFalse(samplePage.alertBoxIsPresent());
    }

    @Test(timeout = 6000)
    @DisplayName("Sample Page: Alert Box Text is Correct")
    public void SamplePage_GenerateAlertBox_GetText_TextIsCorrect() {

        log.info("TEST CASE: Sample Page Alert Box Text is Correct");

        /* Arrange */
        SamplePagePO samplePage = new SamplePagePO(driver);
        String expected = "hi, JavaTpoint Testing";
        String actualText;

        /* Act */
        samplePage.generateAlertBox();
        actualText = samplePage.getAlertBoxText();
        // Alert Box shall be closed before interacting with SUT
        samplePage.dismissAlertBox();

        /* Assert */
        // Alert Box text is as expected
        Assert.assertThat(actualText, containsString(expected));
        Assert.assertEquals(actualText, expected);
    }
    //endregion

    //region GenerateConfirmBox
    @Test(timeout = 6000)
    @DisplayName("Sample Page: Generates a Confirm Box")
    public void SamplePage_GenerateConfirmBox_ButtonClicked_ConfirmBoxIsShown() {

        log.info("TEST CASE: Sample Page Generates a Confirm Box");

        /* Arrange */
        SamplePagePO samplePage = new SamplePagePO(driver);
        boolean modalDialogSeen;

        /* Act */
        // Click button for confirm box
        samplePage.generateConfirmBox();
        modalDialogSeen = samplePage.confirmBoxIsPresent();
        // Alert Box shall be closed before interacting with driver again
        samplePage.dismissConfirmBox();

        /* Assert */
        // Confirm Box is visible
        Assert.assertTrue(modalDialogSeen);
    }


    @Test(timeout = 6000)
    @DisplayName("Sample Page: Confirm box text is correct")
    public void SamplePage_GenerateConfirmBox_GetText_TextIsCorrect() {

        log.info("TEST CASE: Sample Page Confirm box text is correct");

        /* Arrange */
        SamplePagePO samplePage = new SamplePagePO(driver);
        String expected = "Press a button!";
        String actualText;

        /* Act */
        samplePage.generateConfirmBox();
        actualText = samplePage.getConfirmBoxText();
        samplePage.dismissConfirmBox();

        /* Assert */
        // Text is as Expected
        Assert.assertThat(actualText, containsString(expected));
        Assert.assertEquals(actualText, expected);
    }


    @Test(timeout = 6000)
    @DisplayName("Sample Page: Confirm box is accepted and closed")
    public void SamplePage_GenerateConfirmBox_Accept_BoxClosed() {

        log.info("TEST CASE: Sample Page Confirm box is accepted and closed");

        /* Arrange */
        SamplePagePO samplePage = new SamplePagePO(driver);

        /* Act */
        samplePage.generateConfirmBox();
        samplePage.acceptConfirmBox();
        // Log Screenshot Capture to confirm that Confirm box is closed
        String filename = "confirmboxWasAccepted.png";
        if (saveScreenCapture(driver, filename)) {
            log.info("SUCCESS: Saved Screenshot " + filename);
        }
        else {
            log.info("FAILURE: Unable to save screenshot " + filename);
        }

        /* Assert */
        Assert.assertFalse(samplePage.confirmBoxIsPresent());
    }

    @Test(timeout = 6000)
    @DisplayName("Sample Page: Confirm box is dismissed and closed")
    public void SamplePage_GenerateConfirmBox_Dismiss_BoxClosed() {

        log.info("TEST CASE: Sample Page Confirm box is dismissed and closed");

        /* Arrange */
        SamplePagePO samplePage = new SamplePagePO(driver);

        /* Act */
        samplePage.generateConfirmBox();
        samplePage.dismissConfirmBox();
        // Log Screenshot Capture to confirm that Confirm box is closed
        String filename = "confirmboxWasDismissed.png";
        if (saveScreenCapture(driver, filename)) {
            log.info("SUCCESS: Saved Screenshot " + filename);
        }
        else {
            log.info("FAILURE: Unable to save screenshot " + filename);
        }

        /* Assert */
        Assert.assertFalse(samplePage.confirmBoxIsPresent());
    }

    //endregion

    public static void main(String[] args){
        Result result = JUnitCore.runClasses(SamplePageTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

}
