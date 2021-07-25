import Drivers.MyPlanDriver;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MyPlanDriverTest {

    static String USERNAME = "kckanav";
    static String PASSWORD = "Softpastels23";

    static MyPlanDriver driver;

    @BeforeClass
    public static void createDriver() {
        try {
            System.setOut(new PrintStream(new File("kanav")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        driver = new MyPlanDriver(USERNAME, PASSWORD);
    }

    @Test
    public void testRegister() {
        driver.addCourse("10558");
    }
}
