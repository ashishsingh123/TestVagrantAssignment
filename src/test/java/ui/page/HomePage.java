package ui.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pageObject.HomeObject;
import ui.utils.CommonDriverTest;

public class HomePage extends CommonDriverTest {

    public HomeObject homeObj = new HomeObject();

    public HomePage(WebDriver driver) {
          PageFactory.initElements(driver, homeObj);
    }

    public void expandSubMenu()
    {
        if(isElementDisplayed(homeObj.linkNoThanksAlert))
        {
            click(homeObj.linkNoThanksAlert);
        }
        waitForElementDisplayed(homeObj.subMenueOption);
        highlightElement(homeObj.subMenueOption);
        click(homeObj.subMenueOption);
    }

    public void clickWeatherLink()
    {
        waitForElementDisplayed(homeObj.linkWeather);
        highlightElement(homeObj.linkWeather);
        click(homeObj.linkWeather);

    }

}
