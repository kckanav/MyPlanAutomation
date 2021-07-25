package Drivers;

import Drivers.helper.MyPlan;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SLNSearchDriver {

    public final String URL = "https://myplan.uw.edu/course/login/netid";
    public final String TERM_YEAR = "AU 21";

    private WebDriver driver;

    public SLNSearchDriver() {
        driver = MyPlan.getDriver(URL, "kckanav", "Softpastels23");
    }

    public String searchClass(String courseName) {
        try {
            Thread.sleep(2000);
            WebElement input = driver.findElement(By.name("searchQuery"));
            input.sendKeys(courseName);
            input = driver.findElement(By.cssSelector(
                    "#main-content > form > div.search-form > div:nth-child(2) > div.col-lg-5.col-md-6.col-12 > div > div > button"
            ));
            input.click();
            Thread.sleep(2000);
            searchCorrectTerm();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void searchCorrectTerm() {
        int i = 1;
        while (true) {
            WebElement curr;
            try {
                curr = driver.findElement(By.cssSelector(
                        "#main-content > form > div.Loader > div > div > div.col-lg-9.col-12 > div.card-panel.card > div.card-body > div > ul > li:nth-child(" + i +
                                ") > div.course-data-mobile.clearfix > span.course-term > span > abbr"
                ));
                System.out.println(curr.getText());
                if (curr.getText().equals(TERM_YEAR)) {
                    curr = driver.findElement(By.cssSelector(
                            "#main-content > form > div.Loader > div > div > div.col-lg-9.col-12 > div.card-panel.card > div.card-body > div > ul > li:nth-child(" + i +
                                    ") > div.course-data-mobile.clearfix > span.mobile-title > a > .course-title"
                    ));
                    curr.click();
                    return;
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}