package Drivers;

import Drivers.helper.MyDriver;
import Structures.Course;
import Structures.CourseAvailabilityMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.HashMap;
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
        driver = MyDriver.getChromeDriver();
    }

    /**
     * Searches for all available lectures and classes for the given courseName.
     * @param courseName the name of the course as DepartmentCode CourseNumber (eg. CSE 351).
     *                  Any formating works. (cse351, cse 351, CsE351)
     * @return A Course Availability Map.
     *
     */
    public CourseAvailabilityMap searchClass(String courseName) {
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
    private CourseAvailabilityMap getClassAvailability() {
        // Getting the MyPlan table and the name of the course for the table

        List<WebElement> table = driver.findElements(By.cssSelector(
                "#course-institutions-tabpane-0 > div:nth-child(1) > div.px-0.card-body > div.cdpSectionsTable > div > table > tbody"
        ));

        String courseName = driver.findElement(By.cssSelector("#main-content > div.page-heading > h1")).getText();
        CourseAvailabilityMap availibiltyMap = new CourseAvailabilityMap(courseName);

        // TODO: Check for which term's table we are traversing through.
        // Traversing through all the sections available in the big myplan table
        for (WebElement element: table) {

            String section = element.getAttribute("id");

            String[] data = section.split("-");
            String lecSec = data[data.length - 1];
            element = driver.findElement(By.cssSelector(
                    "#" + section + " > tr:nth-child(1) > td:nth-child(7) > div > span"
            ));
            if (element.getText().equals("Open")) {
                if (lecSec.length() == 1) {
                    Course curr = generateCourseInfo(section);
                    availibiltyMap.putLecture(curr);
                } else if (availibiltyMap.containsLecture(lecSec)) {
                    Course curr = generateCourseInfo(section);
                    availibiltyMap.putSection(curr);
                }
            }
        }
        return availibiltyMap;
    }

    private Course generateCourseInfo(String courseElementID) {
        String sln = driver.findElement(By.cssSelector(
                "#" + courseElementID + " > tr:nth-child(1) > td:nth-child(6)"
        )).getText().split("\n")[1];

        String dateTime = driver.findElement(By.cssSelector(
                "#" + courseElementID + " > tr:nth-child(1) > td:nth-child(4) > div > div > div.d-inline-block.mr-2.mr-sm-0.flex-sm-fill > span"
        )).getText();

        dateTime += driver.findElement(By.cssSelector(
                "#" + courseElementID + " > tr:nth-child(1) > td:nth-child(4) > div > div > div:nth-child(2)"
        )).getText();

        String availability = driver.findElement(By.cssSelector(
                "#" + courseElementID + " > tr:nth-child(1) > td:nth-child(7) > small"
        )).getText();

        String[] data = courseElementID.split("-");
        String lecSec = data[data.length - 1];

        return new Course(sln, lecSec, availability, dateTime);
    }

}