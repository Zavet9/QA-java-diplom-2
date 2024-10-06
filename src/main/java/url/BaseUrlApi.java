package url;

import io.restassured.RestAssured;

public class BaseUrlApi {
    public static void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }
}
