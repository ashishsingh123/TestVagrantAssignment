package ui.tests;

import api.RestAPIUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import ui.base.TestBase;
import ui.page.HomePage;
import ui.page.WeatherPage;
import ui.utils.CompareObject;
import ui.utils.Logger;
import ui.utils.TempratureData;
import java.util.HashMap;

public class MatchWeatherTest extends TestBase {

    private HomePage homePage;
    private WeatherPage weatherPage;

    @Parameters({ "browserType" })
    @BeforeClass
    public void launchApplication(@Optional("chrome")String browser)  {
        setUP(browser); //Launch Application
        homePage  =new HomePage(driver);
        weatherPage = new WeatherPage(driver);
    }

    @Test(priority = 0,dataProvider ="City", description = "Verify weather report from two source",groups = {"Weather"})
    public void testWeatherReportFromTwoSource(String city) throws Exception {

        Logger.log("******** Started execution of testWeatherReportFromTwoSource for Data :: " +city);

        homePage.expandSubMenu();
        Logger.log("Expanded submenu option");

        homePage.clickWeatherLink();
        Logger.log("Clicked 'WEATHER' link");

        weatherPage.enterCityInSearchBox(city);
        weatherPage.pinYourCityName(city);
        Logger.log("Entered '"+city+"' and pinned it");

        weatherPage.verifyCityOnMapWithTemprature(city);
        Logger.log("Verified City is showing on Map");

        weatherPage.selectAnyCityOnMap(city);
        Logger.log("Selected city on Map : "+city);

        HashMap<String,String> hmap=weatherPage.verifyAllWeatherInformations();
        Logger.log("Validated after selecting city,map itself reveals the weather details");

        TempratureData ndtvObj = new TempratureData(hmap.get("Temp in Fahrenheit").replaceAll(" ",""),
                hmap.get("Humidity").replace("%",""));

        String API_url = rd.getPropertyData("API");
        String api_key = rd.getPropertyData("API_Key");

        Response response = RestAPIUtils.getRequest(API_url,city,api_key);
        String temp = response.jsonPath().get("main.temp").toString().replaceAll(" ","");
        String humidity = response.jsonPath().get("main.humidity").toString().replaceAll(" ","");
        TempratureData apiObj = new TempratureData(temp,humidity);

        int temperature = CompareObject.compareTemp(ndtvObj,apiObj);
        int humadity = CompareObject.compareHumadity(ndtvObj,apiObj);


        System.out.println("Temperature on NDTV Source :"+ndtvObj.getTemp() +" Fahrenheit");
        System.out.println("Humidity on NDTV Source :"+ndtvObj.getHumidity());
        System.out.println("Temperature on API Source :"+apiObj.getTemp()+" Fahrenheit");
        System.out.println("Humidity on API Source :"+apiObj.getHumidity());

        Assert.assertTrue(temperature < Integer.parseInt(rd.getPropertyData("AcceptTempDiff")) &&
                humadity < Integer.parseInt(rd.getPropertyData("AcceptHumadityDiff")),"Variance difference is not acceptable");

        driver.navigate().to(rd.getPropertyData("appURL"));
        Logger.log("******** Ending execution of testWeatherReportFromTwoSource for Data :: " +city);
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser()
    {
        driver.quit();
    }

    @DataProvider(name="City")
    public static Object[][] cityNames()
    {
        return new Object[][] {{"Ahmedabad"},{"Bengaluru"}};
    }
}
