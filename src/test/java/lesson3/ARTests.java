package lesson3;

import config.ApplicationProperties;
import models.json.response.Booking;
import models.json.response.BookingDates;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import org.testng.Assert;
import io.restassured.http.ContentType;
import static config.Urls.BOOK;
import static io.restassured.RestAssured.given;

public class ARTests {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Новое бронирование")
    public void postBooking() {
        BookingDates bookingDates = new BookingDates();
        Booking booking = Booking.builder()
                .firstname("Anna")
                .lastname("Rubina")
                .totalprice(555)
                .depositpaid(true)
                .bookingdates(new BookingDates("2018-01-01", "2019-01-01"))
                .additionalneeds("Breakfast")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post(url)
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("\"bookingid\""),
                "Новое бронирование не осуществлено");
    }
}


