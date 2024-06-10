package lesson2;

import com.google.gson.Gson;
import config.ApplicationProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.json.request.Auth;
import models.json.response.Booking;
import models.json.response.BookingDates;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.AUTH;
import static io.restassured.RestAssured.given;

public class SSTest {
    ApplicationProperties properties = new ApplicationProperties();

    @Test
    public void getToken() {
        Auth auth = Auth.builder().username("admin").password("password123").build();
        String url = properties.readProperty("url.booker") + AUTH;
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .body(auth)
                .when().post(url).then().log().all()
                .extract().response();
        System.out.println(response.body().print());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().jsonPath().get("token") instanceof String, "Значение поля не является строкой");
    }
}

