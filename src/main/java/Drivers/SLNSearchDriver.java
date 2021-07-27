package Drivers;

import Drivers.helper.MyPlan;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SLNSearchDriver {

    public final String URL = "https://myplan.uw.edu/course/#/courses/";
    public final String TERM_YEAR = "AU 21";

    private WebDriver driver;

    public SLNSearchDriver() {
        driver = MyPlan.getChromeDriver();
    }

    public Map<String, List<String>> searchClass(String courseName) {
        driver.get(URL + courseName);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getClassAvailability();
    }

    public void close() {
        driver.close();
    }

    private Map<String, List<String>> getClassAvailability() {
        Map<String, List<String>> classes = new HashMap<>();
        List<WebElement> table = driver.findElements(By.cssSelector(
                "#course-institutions-tabpane-0 > div:nth-child(1) > div.px-0.card-body > div.cdpSectionsTable > div > table > tbody"
        ));
        System.out.println(table);
        // TODO: Check for which term's table we are traversing through.
        // Traversing through all the sections available in the big myplan table
        for (WebElement element: table) {
            String section = element.getAttribute("id");
            String[] data = section.split("-");
            String lecSec = data[data.length - 1];
            element = driver.findElement(By.cssSelector(
                    "#" + section + " > tr:nth-child(1) > td:nth-child(7) > div > span"
            ));
            if (lecSec.length() == 1) {
                if (element.getText().equals("Open")) {
                    classes.put(lecSec, new LinkedList<>());
                }
            } else {
                if (classes.containsKey("" + lecSec.charAt(0)) && element.getText().equals("Open")) {
                    classes.get("" + lecSec.charAt(0)).add(lecSec);
                }
            }
        }
        return classes;
    }



    //    private void searchCorrectTerm() {
//
//        int i = 1;
//        while (true) {
//            WebElement curr;
//            try {
//                curr = driver.findElement(By.cssSelector(
//                        "#main-content > form > div.Loader > div > div > div.col-lg-9.col-12 > div.card-panel.card > div.card-body > div > ul > li:nth-child(" + i +
//                                ") > div.course-data-mobile.clearfix > span.course-term > span > abbr"
//                ));
//                System.out.println(curr.getText());
//                if (curr.getText().equals(TERM_YEAR)) {
//                    curr = driver.findElement(By.cssSelector(
//                            "#main-content > form > div.Loader > div > div > div.col-lg-9.col-12 > div.card-panel.card > div.card-body > div > ul > li:nth-child(" + i +
//                                    ") > div.course-data-mobile.clearfix > span.mobile-title > a > .course-title"
//                    ));
//                    curr.click();
//                    return;
//                }
//                i++;
//            } catch (Exception e) {
//                e.printStackTrace();
//                break;
//            }
//        }
//    }
}