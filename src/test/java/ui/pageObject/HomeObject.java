package ui.pageObject;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomeObject {

    @FindBy(id = "h_sub_menu")
    public WebElement subMenueOption;

    @FindBy(linkText = "WEATHER")
    public WebElement linkWeather;

    @FindBy(className = "notnow")
    public WebElement linkNoThanksAlert;


}
