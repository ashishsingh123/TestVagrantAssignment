package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestAPIUtils {

    /**
     * @author Ashish
     * @param baseUrl
     * @param city
     * @param key
     * @return
     */
    public static Response getRequest(String baseUrl, String city, String key) {
        RestAssured.baseURI = baseUrl;
        Response responsePost = given().get("?q="+city+"&appid="+key);
        return responsePost;
    }
}
