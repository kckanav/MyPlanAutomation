package Drivers.helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MyDriver {

    /**
     * Generates a new WebDriver for the given URL, and assumes that the Given URL leads to a UW login page.
     * It then logs in using the provided netId and password, and returns a WebDriver with the specifc URL open after logging in.
     * @param url The url to be open. This URL must first point to a UW login page.
     * @param netid the netid of the user
     * @param password the password for the UW netid
     * @return a WebDriver with the URL open, after being logged in.
     */
    public static WebDriver getLogInDriver(String url, String netid, String password) {
       WebDriver driver = setDriver();
        driver.get(url);
       login(driver, netid, password);
       return driver;
    }

    /**
     * Generates a new WebDriver with the provided link opened.
     * @param url the url to open
     * @return A Chrome WebDriver with the specified link open.
     */
    public static WebDriver openLink(String url) {
        WebDriver driver = setDriver();
        driver.get(url);
        return driver;
    }

    /**
     * Returns a new empty Chrome WebDriver.
     * @return a new ChromeWebDriver.
     */
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
