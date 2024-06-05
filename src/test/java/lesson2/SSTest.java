package lesson2;

import com.google.gson.Gson;
import config.ApplicationProperties;
import io.restassured.response.Response;
import models.json.request.Auth;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.AUTH;
import static io.restassured.RestAssured.given;

public class SSTest {
    private final Gson gson;
    ApplicationProperties properties = new ApplicationProperties();

    public SSTest(Gson gson) {
        this.gson = gson;
    }

    @Test(description = "Получение токена.")
    public void getToken() {
        Auth auth = Auth.builder().username("admin").password("password123").build();
        String body = gson.toJson(auth);
        String url = properties.readProperty("url.booker") + AUTH;
        Response response = given()
                .body(body)
                .when().post(url).then()
                .extract().response();
        System.out.println(response.body().print());
        Assert.assertEquals(response.getStatusCode(), 200);
//        Assert.assertTrue(response.body().jsonPath().get("token") instanceof String, "Значение поля не является строкой");
    }
}

