package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.time.LocalDateTime;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class WrapperFunctions {

    private static String _createTimestamp() {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        return now.format(timeFormat) + "_";

    }


    public static boolean saveScreenCapture(WebDriver driver, String filename) {

        // Take Screenshot after Alert Box is closed
        File screenShotImage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Set +path+timestamp+filename as destination
        String defaultPath = ".\\media\\screenshots\\";
        String timestamp = _createTimestamp();

        File destination = new File(defaultPath + timestamp + filename);

        // Save screenshot at destination
        try {
            FileUtils.copyFile(screenShotImage, destination);
            return true;
        }
        catch (IOException e) {
            // Remember to log "INFO: Unable to save screenshot <path><filename>"
            return false;
        }

    }

    public static boolean saveScreenCapture(WebDriver driver, String filename, String path) {

        // Take Screenshot after Alert Box is closed
        File screenShotImage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Set path+timestamp+filename as destination
        String timestamp = _createTimestamp();

        System.out.println(path + "\\" + timestamp + filename);

        File destination = new File(path + "\\" + timestamp + filename);

        // Save screenshot at destination
        try {
            FileUtils.copyFile(screenShotImage, destination);
            return true;
        } catch (IOException e) {
            // Remember to log "INFO: Unable to save screenshot <path><filename>"
            return false;
        }
    }

}
