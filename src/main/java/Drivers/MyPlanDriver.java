package Drivers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;

public class MyPlanDriver {

    public final int MAX_NUMBER_OF_CLASSES = 7;

    private WebDriver driver;

    /**
     * Constructs a new connection to the MyPlan of the given username
     * @param netid The NetId of the user
     * @param password Password for the account
     */
    public MyPlanDriver(String netid, String password) {
        setDriver();
        login(netid, password);
    }

    /**
     * Adds the given SLN to the plan of the student using the current driver in use.
     * @param sln The SLN number of the class to be added
     * @return True if successfully added, false otherwise
     */
    public boolean addCourse(String sln) {
        try {
            WebElement toEnter = driver.findElement(By.cssSelector(
                    "#regform > p:nth-child(6) > table > tbody > tr:nth-child(2) > td:nth-child(2) > input[type=TEXT]"
            ));
            toEnter.sendKeys(sln);
            driver.findElement(By.id("regform")).submit();
            return checkStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes the specified course from the MyPlan of the user
     * @param sln the course's SLN to remove
     * @return true if the course was succesfully removed, false if course is not in plan
     */
    public boolean removeCourse(String sln) {
        for (int i = 3; i <= MAX_NUMBER_OF_CLASSES + 3; i++) {
            String selector = "#regform > p:nth-child(5) > table > tbody > tr:nth-child(" + i + ") > td:nth-child(6) > tt";
            try {
                WebElement input = driver.findElement(By.cssSelector(selector));
                if (input.getText().contains(sln)) {
                    input = driver.findElement(By.cssSelector(
                            "#regform > p:nth-child(5) > table > tbody > tr:nth-child(3) > td:nth-child(1) > input[type=CHECKBOX]"
                    ));
                    input.click();
                    driver.findElement(By.id("regform")).submit();
                    return checkStatus();
                } else {
                    continue;
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                break;
            }
        }
        return false;
    }

    /**
     * Gets a list of all the courses the user is currently registered for
     * @return List of strings of all the courses's SLN
     */
    public List<String> registeredCourses() {
        List<String> list = new ArrayList<>();
        for (int i = 3; i <= MAX_NUMBER_OF_CLASSES + 3; i++) {
            String selector = "#regform > p:nth-child(5) > table > tbody > tr:nth-child(" + i + ") > td:nth-child(6) > tt";
            try {
                WebElement input = driver.findElement(By.cssSelector(selector));
                list.add(input.getText());
            } catch (org.openqa.selenium.NoSuchElementException e) {
                break;
            }
        }
        return list;
    }

    /**
     * Closes the connection and logs out of the user's MyPlan
     */
    public void close() {
        driver.close();
    }

    /**
     * Checks the status of the last change made by the driver in the user's MyPlan
     * @return
     */
    private boolean checkStatus() {
        try {
            WebElement status = driver.findElement(By.cssSelector("#doneDiv > b"));
            String result = status.getText();
            if (result.equals("Schedule updated.")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
            return false;
        }
    }

    /**
     * Sets up the new driver in the system. <b>Setup will be different for every system</b>
     */
    private void setDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\cance\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://sdb.admin.uw.edu/students/uwnetid/register.asp");
    }

    /**
     * Logs in to the given username of the driver
     */
    private void login(String netid, String password) {
        WebElement id = driver.findElement(By.id("weblogin_netid"));
        WebElement pw = driver.findElement(By.id("weblogin_password"));
        id.sendKeys(netid);
        pw.sendKeys(password);
        driver.findElement(By.id("submit_button")).click();
    }
}
