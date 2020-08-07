package ui.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.base.TestBase;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class CommonDriverTest {
    WebDriver driver= TestBase.driver;
    WebDriverWait wait = new WebDriverWait(driver, 15);

    Wait<WebDriver> flWait = new FluentWait<WebDriver>(driver)
            .withTimeout(15, TimeUnit.SECONDS)
            .pollingEvery(5, TimeUnit.SECONDS)
            .ignoring(StaleElementReferenceException.class, java.util.NoSuchElementException.class);

    public void waitForPageToLoad(WebElement id) {
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    public void waitForElementToDisAppear(String id) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
    }

    public void waitForElementPresentAndClickable(WebElement el) {
          flWait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public void waitForElementsToAppear(List<WebElement> id) {
        wait.until(ExpectedConditions.visibilityOfAllElements(id));
    }

    public void waitForElementDisplayed(WebElement id) {
        wait.until(ExpectedConditions.visibilityOf(id));
    }
    public boolean isElementDisplayed(WebElement el) {
        try {
            if(el.isDisplayed())
                return true;
            else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
    public void waitForElementClickable(WebElement id) {
        wait.until(ExpectedConditions.visibilityOf(id));
    }

    public WebElement waitForElement(WebElement arg) {
        waitForPageToLoad(arg);
        WebElement el = arg;
        return el;
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public static void highlightElement(WebElement element) {
        ((JavascriptExecutor)TestBase.driver).executeScript("arguments[0].style.border='3px solid green'", element);
    }
    public static String getScreenshot(String screenshotName) throws Exception {
        System.out.println("Taking Screenshot...........>");
        TakesScreenshot ts = (TakesScreenshot)TestBase.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"\\FailedTestsScreenshots\\"+screenshotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);
        System.out.println(dest);
        return dest;
    }
    public void click(WebElement element) {
        element.click();
    }

    public void sendKeys(WebElement element,String data) {

        element.clear();
        element.sendKeys(data);
    }
}
