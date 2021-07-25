package Drivers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WhatsAppDriver {

    public final PhoneNumber SENDER_NUMBER = new PhoneNumber("whatsapp:+14155238886");
    public final String AUTH_TOKEN = "9b120685f5a71395d394589ab5a48d2e";
    public final String ACCOUNT_SID = "ACe02391aed0dd7dcdbdaf8a994045b7c2";

    public WhatsAppDriver() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public boolean sendMessage(String message) {
        PhoneNumber number = new PhoneNumber("whatsapp:+12063692068");
        Message.creator(number, SENDER_NUMBER, message)
                .create();
        return true;
    }
}
