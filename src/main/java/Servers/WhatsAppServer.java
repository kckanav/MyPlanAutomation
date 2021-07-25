package Servers;

import Drivers.MyPlanDriver;
import Servers.Utils.CORSFilter;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import spark.Spark;

import java.util.Arrays;

public class WhatsAppServer {

    public static void main(String[] args) {

        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();

        MyPlanDriver driver = new MyPlanDriver("kckanav", "Softpastels23");

        Spark.post("/sms", (req, res) -> {
            res.type("application/xml");
            String result;
            String command = req.queryParams("Body");
            String[] info = command.split(" ");
            switch (info[0].toLowerCase()) {
                case "register":
                    result =  Boolean.toString(driver.addCourse(info[1]));
                    System.out.println("Register " + info[1]);
                    break;
                case "remove":
                    System.out.println("Remove " + info[1]);
                    result = Boolean.toString(driver.removeCourse(info[1]));
                    break;
                default:
                    result = Arrays.toString(driver.registeredCourses().toArray());
            }

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
}
