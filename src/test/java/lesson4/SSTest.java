package lesson4;

import api.RestHelper;
import config.ApplicationProperties;
import io.restassured.response.Response;
import models.json.response.Booking;
import models.json.response.BookingDates;
import org.testng.Assert;
import org.testng.annotations.Test;

import static api.RestHelper.getRequest;
import static api.RestHelper.postRequestWithBody;
import static config.Urls.BOOK;

public class SSTest {
    static ApplicationProperties properties = new ApplicationProperties();
    String url = properties.readProperty("env.dev.url2");

    @Test(description = "Get bookings.")
    public void getBookings() {
        Response response = getRequest(url, BOOK);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "Create booking.")
    public void createBooking() {
        Booking booking = Booking.builder()
                .firstname("Jim")
                .lastname("Brown")
                .totalprice(111)
                .depositpaid(true)
                .bookingdates(new BookingDates("2018-01-01", "2019-01-01"))
                .additionalneeds("Breakfast")
                .build();
        Response response = postRequestWithBody(url, BOOK, booking);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
