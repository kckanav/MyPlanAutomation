package Servers;

import Drivers.MyPlanDriver;
import Drivers.SLNSearchDriver;
import Servers.Utils.CORSFilter;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import spark.Spark;

import java.util.Arrays;

public class WhatsAppServer {

    private static MyPlanDriver driver;
    private static SLNSearchDriver searchDriver;

    public static void main(String[] args) {

        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();

        driver = new MyPlanDriver("kckanav", "Softpastels23");
        searchDriver = new SLNSearchDriver();

        Spark.post("/sms", (req, res) -> {
            res.type("application/xml");
            String command = req.queryParams("Body");
            String result = processInput(command);

            // Default template to follow to send a message back to the user on WhatsApp
            Body body = new Body
                    .Builder(result)
                    .build();
            Message sms = new Message
                    .Builder()
                    .body(body)
                    .build();
            MessagingResponse twiml = new MessagingResponse
                    .Builder()
                    .message(sms)
                    .build();
            return twiml.toXml();
        });
    }

    private static String processInput(String command) {
        String result;
        String[] info = command.split(" ");
        if (info.length <= 2) {
            switch (info[0].toLowerCase()) {
                case "find":
                    result = searchDriver.searchClass(info[1]).toString();
                    break;
                case "register":
                    driver.addCourse(info[1]);
                    result = driver.getCurrentStatus();
                    System.out.println("Register " + info[1]);
                    break;
                case "remove":
                    System.out.println("Remove " + info[1]);
                    driver.removeCourse(info[1]);
                    result = driver.getCurrentStatus();
                    break;
                default:
                    //TODO: Show the SLN and the name of the courses
                    result = Arrays.toString(driver.registeredCourses().toArray());
            }
            // TODO: Change remove to list of remove
        } else {
            switch (info[0].toLowerCase()) {
                case "register":
                    driver.addCourses(Arrays.copyOfRange(info, 1, info.length));
                    result = driver.getCurrentStatus();
                    break;
                default:
                    result = Arrays.toString(driver.registeredCourses().toArray());
            }
        }
        return result;
    }
}
