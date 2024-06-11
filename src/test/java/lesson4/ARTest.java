package lesson4;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.json.response.Booking;
import models.json.response.BookingDates;
import org.testng.Assert;
import org.testng.annotations.Test;
import config.ApplicationProperties;

import static config.Urls.BOOK;
import static config.Urls.BOOK_ID;
import static io.restassured.RestAssured.given;

public class ABTest {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Новое бронирование с изменением сроков бронирования")
    public void manageBooking() {
        String url = properties.readProperty("env.dev.url2") + BOOK;
        String url2 = properties.readProperty("env.dev.url2") + BOOK_ID;
        Booking booking = Booking.builder()
                .firstname("Anna")
                .lastname("Rubina")
                .totalprice(555)
                .depositpaid(true)
                .bookingdates(new BookingDates("2018-01-01", "2019-01-01"))
                .additionalneeds("Breakfast")
                .build();
        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post(url)
                .then()
                .extract().response();


        int bookingId = postResponse.jsonPath().getInt("bookingid");
        Response getResponse = given()
                .when()
                .pathParam("id", bookingId)
                .get(url2)
                .then()
                .extract().response();

        Booking bookingAfterChange = Booking.builder()
                .firstname("Anna")
                .lastname("Rubina")
                .totalprice(200)
                .depositpaid(true)
                .bookingdates(new BookingDates("2018-01-01", "2018-01-02"))
                .additionalneeds("Breakfast")
                .build();
        Response putResponse = given()
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .contentType(ContentType.JSON)
                .body(bookingAfterChange)
                .when()
                .pathParam("id", bookingId)
                .put(url2)
                .then()
                .extract().response();

        Response updateGetResponse = given()
                .when()
                .pathParam("id", bookingId)
                .get(url2)
                .then()
                .extract().response();
        Assert.assertEquals(postResponse.getStatusCode(), 200);

    }

}
