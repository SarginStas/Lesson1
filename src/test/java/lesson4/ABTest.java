package lesson4;

import api.RestHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.json.response.Booking;
import models.json.response.BookingDates;
import org.testng.Assert;
import org.testng.annotations.Test;
import config.ApplicationProperties;

import static config.Urls.*;
import static io.restassured.RestAssured.given;

public class ABTest {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Manage booking")
    public void manageBooking() {
        String baseUrl = properties.readProperty("env.dev.url2");
        String url2 = properties.readProperty("env.dev.url2") + BOOK_ID;
        Booking booking = Booking.builder()
                .firstname("Jim")
                .lastname("Brown")
                .totalprice(111)
                .depositpaid(true)
                .bookingdates(new BookingDates("2018-01-01", "2019-01-01"))
                .additionalneeds("Breakfast")
                .build();
        Response postResponse = RestHelper.postRequestWithBody(baseUrl, BOOK, booking);

        int bookingId = postResponse.jsonPath().getInt("bookingid");
        String bookingEndpointWithId = "/booking/" + bookingId;
        Response getResponse = RestHelper.getRequest(baseUrl, bookingEndpointWithId);

        Booking bookingAfterChange = Booking.builder()
                .firstname("Jim")
                .lastname("Brown")
                .totalprice(130)
                .depositpaid(true)
                .bookingdates(new BookingDates("2024-07-01", "2024-07-07"))
                .additionalneeds("Dinner")
                .build();
        String token = RestHelper.getToken("admin", "password123", baseUrl + AUTH);
        Response putResponse = RestHelper.putRequestWithBodyAndToken(baseUrl, bookingEndpointWithId, bookingAfterChange, token);
        Assert.assertEquals(postResponse.getStatusCode(), 200);
        Assert.assertEquals("Breakfast", booking.getAdditionalneeds());
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(putResponse.getStatusCode(), 200);
        Assert.assertEquals("Dinner", bookingAfterChange.getAdditionalneeds());
    }

//    @Test(description = "Manage booking")
//    public void manageBooking2() {
//        String url = properties.readProperty("env.dev.url2") + BOOK;
//        String url2 = properties.readProperty("env.dev.url2") + BOOK_ID;
//        Booking booking = Booking.builder()
//                .firstname("Jim")
//                .lastname("Brown")
//                .totalprice(111)
//                .depositpaid(true)
//                .bookingdates(new BookingDates("2018-01-01", "2019-01-01"))
//                .additionalneeds("Breakfast")
//                .build();
//        Response postResponse = given()
//                .contentType(ContentType.JSON)
//                .body(booking)
//                .when()
//                .post(url)
//                .then()
//                .extract().response();
//
//
//        int bookingId = postResponse.jsonPath().getInt("bookingid");
//        Response getResponse = given()
//                .when()
//                .pathParam("id", bookingId)
//                .get(url2)
//                .then()
//                .extract().response();
//
//        Booking bookingAfterChange = Booking.builder()
//                .firstname("Jim")
//                .lastname("Brown")
//                .totalprice(130)
//                .depositpaid(true)
//                .bookingdates(new BookingDates("2024-07-01", "2024-07-07"))
//                .additionalneeds("Dinner")
//                .build();
//        Response putResponse = given()
//                .auth()
//                .preemptive()
//                .basic("admin", "password123")
//                .contentType(ContentType.JSON)
//                .body(bookingAfterChange)
//                .when()
//                .pathParam("id", bookingId)
//                .put(url2)
//                .then()
//                .extract().response();
//
//        Response updateGetResponse = given()
//                .when()
//                .pathParam("id", bookingId)
//                .get(url2)
//                .then()
//                .extract().response();
//        Assert.assertEquals(postResponse.getStatusCode(), 200);
//        Assert.assertEquals("Breakfast", booking.getAdditionalneeds());
//        Assert.assertEquals(getResponse.getStatusCode(), 200);
//        Assert.assertEquals(putResponse.getStatusCode(), 200);
//        Assert.assertEquals("Dinner", bookingAfterChange.getAdditionalneeds());
//        Assert.assertEquals(updateGetResponse.getStatusCode(), 200);
//
//    }

}
