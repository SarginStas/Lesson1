package api;

import config.ApplicationProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.json.request.Auth;
import models.json.response.Booking;

import static io.restassured.RestAssured.given;


public class RestHelper {
    public static String getToken(String username, String password, String url) {
        Auth auth = Auth.builder().username(username).password(password).build();
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .body(auth)
                .when().post(url).then().log().all()
                .extract().response();
        return response.body().jsonPath().get("token");
    }

    public static Response getRequest(String baseUrl, String endPoint) {
        String url = baseUrl + endPoint;
        return given().relaxedHTTPSValidation().log().all()
                .contentType(ContentType.JSON)
                .when().get(url).then().log().all()
                .extract().response();
    }

    public static Response postRequestWithBody(String baseUrl, String endPoint, String body) {
        String url = baseUrl + endPoint;
        return given().relaxedHTTPSValidation().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(url).then()
                .extract().response();
    }

    public static Response postRequestWithBody(String baseUrl, String endPoint, Booking body) {
        String url = baseUrl + endPoint;
        return given().relaxedHTTPSValidation().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(url).then()
                .extract().response();
    }

    public static Response putRequestWithBodyAndToken(String baseUrl, String endPoint, Booking body, String token) {
        String url = baseUrl + endPoint;
        return given().relaxedHTTPSValidation().log().all()
                .header("Cookie", "token=" + token)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }

}
