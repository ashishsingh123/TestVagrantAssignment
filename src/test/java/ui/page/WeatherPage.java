package ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ui.base.TestBase;
import ui.pageObject.WeatherObject;
import ui.utils.CommonDriverTest;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherPage extends CommonDriverTest {

    public WeatherObject weatherObj = new WeatherObject();

    public WeatherPage(WebDriver driver) {
        PageFactory.initElements(driver, weatherObj);
    }


    public void enterCityInSearchBox(String city)
    {
        highlightElement(weatherObj.txtSearchBox);
        sendKeys(weatherObj.txtSearchBox,city);
    }

    public void pinYourCityName(String city)
    {
        try {
            WebElement cityEl = weatherObj.cityDetails.findElement(By.xpath("//label[@for='" + city + "']/input"));
            if (!cityEl.isSelected()) {
                click(cityEl);
            }
        }catch (NoSuchElementException e){
            System.out.println("Please check you City name -> "+city);
            throw e;
        }
    }

    public void verifyCityOnMapWithTemprature(String city)
    {
        Assert.assertTrue(TestBase.getWebDriver().findElement(By.xpath("//div[@title='"+city+"']/div[@class='temperatureContainer']")).isDisplayed());
    }

    public void selectAnyCityOnMap(String city)
    {
        click(TestBase.getWebDriver().findElement(By.xpath("//div[@title='"+city+"']/span/img")));
    }

    public HashMap<String,String> verifyAllWeatherInformations()
    {
        String[] labels={"Condition","Wind","Humidity","Temp in Degrees","Temp in Fahrenheit"};
        HashMap<String,String> hmap = new HashMap<String,String>();
        int i=0;
        for(WebElement we :weatherObj.weatherDetails )
        {
            Assert.assertTrue(we.getText().contains(labels[i]));
            hmap.put(labels[i],we.getText().split(":")[1]);
            i++;
        }
        return hmap;
    }

}
