package lesson3;

import config.ApplicationProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.AUTH;
import static config.Urls.BOOK;
import static io.restassured.RestAssured.given;

public class ARTests {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Новое бронирование")
    public void postBooking() {
        String url = properties.readProperty("env.dev.url2") + BOOK;
        String body = "{\"firstname\" : \"Jim\", \"lastname\" : \"Brown\", \"totalprice\" : \"111\", \"depositpaid\" : \"true\", \"bookingdates\" : { \"checkin\" : \"2018-01-01\", \"checkout\" : \"2019-01-01\" } \"additionalneeds\" : \"Breakfast\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("\"bookingid\""),
                "Новое бронирование не осуществлено");
    }
}


