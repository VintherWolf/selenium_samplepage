package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SamplePagePO {

    protected RemoteWebDriver _driver;
    private String _samplePageUrl = "https://www.testandquiz.com/selenium/testing.html";
    // Generate Alert Box
    private By _alertBoxBy = By.xpath("/html/body/div/div[11]/div/p/button");
    private Alert _alertBox = null;
    private boolean _alertBoxIsPresent = false;

    // Generate Confirm Box
    private By _confirmBoxBy = By.xpath("/html/body/div/div[12]/div/p/button");
    private Alert _confirmBox = null;
    private boolean _confirmBoxIsPresent = false;

    // Constuctor
    public SamplePagePO(RemoteWebDriver driverObj) {
        this._driver = driverObj;
        _driver.get(_samplePageUrl);
    }


    //region AlertBox
    public void generateAlertBox() {
        _driver.findElement(_alertBoxBy).click();
        _checkIsAlertBoxPresent();
    }

    private void _checkIsAlertBoxPresent() {
        try {
            this._alertBox = _driver.switchTo().alert();
            this._alertBoxIsPresent = true;
        }
        catch (NoAlertPresentException e) {
            this._alertBoxIsPresent = false;
        }
    }

    public boolean alertBoxIsPresent() {
        return this._alertBoxIsPresent;
    }


    public void dismissAlertBox() {
        // Dismiss alert box if it is present
        if (this._alertBoxIsPresent) {
            this._alertBox.dismiss();
        }
        // Update '_alertBoxIsPresent'
        _checkIsAlertBoxPresent();
    }

    public void acceptAlertBox() {
        // Accept alert box if it is present
        if (this._alertBoxIsPresent) {
            this._alertBox.accept();
        }
        // Update '_alertBoxIsPresent'
        _checkIsAlertBoxPresent();
    }

    public String getAlertBoxText() {
        if (this._alertBoxIsPresent) {
            return this._alertBox.getText();
        }
        else {
            return "FAILURE";
        }

    }


    //endregion

    //region ConfirmBox

    public void generateConfirmBox() {
        _driver.findElement(_confirmBoxBy).click();
        _checkIsConfirmBoxPresent();
    }


    private void _checkIsConfirmBoxPresent() {
        try {
            this._confirmBox = _driver.switchTo().alert();
            this._confirmBoxIsPresent = true;
        }
        catch (NoAlertPresentException e) {
            this._confirmBoxIsPresent = false;
        }
    }


    public boolean confirmBoxIsPresent() {
        return this._confirmBoxIsPresent;
    }


    public void dismissConfirmBox() {
        // Dismiss confirm box if it is present
        if (this._confirmBoxIsPresent) {
            this._confirmBox.dismiss();
        }
        // Update '_confirmBoxIsPresent'
        _checkIsConfirmBoxPresent();
    }


    public void acceptConfirmBox() {
        // Accept confirm box if it is present
        if (this._confirmBoxIsPresent) {
            this._confirmBox.accept();
        }
        // Update '_confirmBoxIsPresent'
        _checkIsConfirmBoxPresent();
    }


    public String getConfirmBoxText() {
        if (this._confirmBoxIsPresent) {
            return this._confirmBox.getText();
        }
        else {
            return "FAILURE";
        }

    }

    //endregion
}
