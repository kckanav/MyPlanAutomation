package Servers;

import Servers.Utils.CORSFilter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class WhatsAppServer {

    public static void main(String[] args) {

        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();

        Spark.get("/path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String start = request.queryParams("start");
                String end = request.queryParams("end");
                return "hi";
            }
        });
    }
}
