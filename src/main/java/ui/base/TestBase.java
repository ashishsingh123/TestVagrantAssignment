package ui.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;
import ui.utils.ReadPropertiesFile;
import java.util.concurrent.TimeUnit;

public class TestBase {


    public static WebDriver driver;
    String app_url,os;
    String driver_folder=System.getProperty("user.dir")+"//driver";
    public ReadPropertiesFile rd;


    public  void init() {
        rd=new ReadPropertiesFile();
        app_url = rd.getPropertyData("appURL");
        os = rd.getPropertyData("OS");
    }

    public void setUP(String browser) {
        init();
        try {
            if(os.equalsIgnoreCase("Windows")) {
                if (browser.equalsIgnoreCase("chrome")) {
                    System.setProperty("webdriver.chrome.driver", driver_folder + "//chromedriver.exe");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("disable-infobars");
                    driver = new ChromeDriver(options);

                } else if (browser.equalsIgnoreCase("firefox")) {
                    System.setProperty("webdriver.gecko.driver", driver_folder + "//geckodriver.exe");
                    driver = new FirefoxDriver();

                } else if (browser.equalsIgnoreCase("safari")) {
                    driver = new SafariDriver();

                } else if (browser.equalsIgnoreCase("opera")) {
                    OperaOptions options = new OperaOptions();
                    options.setBinary("");
                    driver = new OperaDriver(options);

                } else if (browser.equalsIgnoreCase("ie")) {
                    System.setProperty("webdriver.ie.driver", driver_folder + "//IEDriverServer.exe");
                    driver = new InternetExplorerDriver();

                } else if (browser.equalsIgnoreCase("edge")) {
                    System.setProperty("webdriver.edge.driver", driver_folder + "//MicrosoftWebDriver.exe");
                    driver = new EdgeDriver();

                } else if (browser.equalsIgnoreCase("headless")) {
                    System.setProperty("webdriver.chrome.driver", driver_folder + "//chromedriver.exe");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("headless");
                    driver = new ChromeDriver(options);

                }
            }else {
                    System.setProperty("webdriver.chrome.driver", driver_folder + "//chromedriver");
                    System.setProperty("webdriver.chrome.silentOutput", "true");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--start-maximized");
                    options.addArguments("--headless");
                    driver = new ChromeDriver(options);
                }

        }catch (Exception e){e.printStackTrace();}

        driver.get(app_url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public static WebDriver getWebDriver()
    {
        return driver;
    }


}
