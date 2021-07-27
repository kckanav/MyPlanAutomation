package Drivers.helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MyPlan {

    public static WebDriver getDriver(String url, String netid, String password) {
       WebDriver driver = setDriver();
        driver.get(url);
       login(driver, netid, password);
       return driver;
    }

    public static WebDriver openLink(String url) {
        WebDriver driver = setDriver();
        driver.get(url);
        return driver;
    }

    public static WebDriver getChromeDriver() {
        return setDriver();
    }

    /**
     * Sets up the new driver in the system. <b>Setup will be different for every system</b>
     */
    private static WebDriver setDriver() {
        WebDriverManager.chromedriver().setup(); // Line 2
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    /**
     * Logs in to the given username of the driver
     */
    private static void login(WebDriver driver, String netid, String password) {
        WebElement id = driver.findElement(By.id("weblogin_netid"));
        WebElement pw = driver.findElement(By.id("weblogin_password"));
        id.sendKeys(netid);
        pw.sendKeys(password);
        driver.findElement(By.id("submit_button")).click();
    }
}
