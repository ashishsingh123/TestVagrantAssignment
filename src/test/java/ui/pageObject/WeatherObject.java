package ui.pageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WeatherObject {

    @FindBy(id = "searchBox")
    public WebElement txtSearchBox;

    @FindBy(className = "messages")
    public WebElement cityDetails;

    @FindBy(xpath = "//div[@class='leaflet-popup-content']//span/b")
    public List<WebElement> weatherDetails;

}
