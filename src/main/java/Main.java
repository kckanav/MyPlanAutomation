import Drivers.MyPlanDriver;
import Drivers.SLNSearchDriver;

import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[]args){
        SLNSearchDriver d = new SLNSearchDriver();
        System.out.println(d.searchClass("cse351"));
//        d.close();
    }

//    public static void main(String[] args) {
//        MyPlanDriver driver = new MyPlanDriver("kckanav", "Softpastels23", "10558");
//        driver.setGmailAuth("cancer.kanav70@gmail.com", "Softpastels@23");
//        Timer t = new Timer();
//        t.schedule(driver, 0, 6000);
//        if(driver.taskDone) {
//            System.exit(0);
//        }
//    }

}

