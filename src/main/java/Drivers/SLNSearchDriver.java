package Drivers;

import Drivers.helper.MyPlan;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SLNSearchDriver {

    public final String URL = "https://myplan.uw.edu/course/#/courses/";
    public final String TERM_YEAR = "AU 21";

    private WebDriver driver;

    /**
     * Creats a new driver which can be used to search for classes.
     */
    public SLNSearchDriver() {
        driver = MyPlan.getChromeDriver();
    }

    /**
     * Searches for all available lectures and classes for the given courseName.
     * @param courseName the name of the course as DepartmentCode CourseNumber (eg. CSE 351).
     *                  Any formating works. (cse351, cse 351, CsE351)
     * @return A map with keys as the letter of the lecture, and the values being all available
     *          sections/labs for that particular lecture. Only shows lectures which are currently available.
     *
     */
    public Map<String, List<String>> searchClass(String courseName) {
        driver.get(URL + courseName);
        sleep(2000);
        return getClassAvailability();
    }

    /**
     * Closes the driver. Call everytime you are done with using the driver.
     */
    public void close() {
        driver.close();
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for the availibilty of the classes. Assumes that the driver is currently at the MyPlan page of the specifc course
     * the user wants to search for.
     * @return Map, the same returned in searchClass(String courseName)
     */
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
}