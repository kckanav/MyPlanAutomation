package Drivers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class AutomationDriver {

    public final PhoneNumber SENDER_NUMBER = new PhoneNumber("whatsapp:+14155238886");
    public final String AUTH_TOKEN = "9b120685f5a71395d394589ab5a48d2e";
    public final String ACCOUNT_SID = "ACe02391aed0dd7dcdbdaf8a994045b7c2";
    public final int NUMBER_OF_EMAILS_TO_CHECK = 4;

    public final String NOTIFY_UW_EMAIL = "<kckanav@uw.edu>";

    private Folder inbox;
    private MyPlanDriver myplan;

    public AutomationDriver(MyPlanDriver myplan) {
        this.myplan = myplan;
    }

    public void setNotifyUwIncoming(String username, String password) {
        logIntoGmail(username, password);
    }

    private void logIntoGmail(String gmailId,String gmailPassword ) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File("C:\\Users\\cance\\IdeaProjects\\MyPlan\\src\\smtp.properties")));
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", gmailId, gmailPassword);

            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("There was an error trying to connect to your GMAIL.");
        }
    }

    private void sendMessage(String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void run() {
        try {
            int newMessages = inbox.getNewMessageCount();
            int totalMessages = inbox.getMessageCount();
            System.out.println(newMessages);
            javax.mail.Message[] messages = inbox.getMessages(totalMessages - NUMBER_OF_EMAILS_TO_CHECK, totalMessages - 1);
            System.out.println(messages.length);
            for (javax.mail.Message message: messages) {
                String from = (message.getFrom())[0].toString();
                if (from.contains(NOTIFY_UW_EMAIL)) {
                    System.out.println("Came here");
                    if (myplan.addCourse("")) {
                        inbox.close(true);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("There was an error trying to open/close the inbox. Please check!");
        }
    }
}