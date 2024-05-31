package lesson1;

import config.ApplicationProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.USERS;
import static io.restassured.RestAssured.given;

public class BDTests {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Получение пользователя.")
    public void getUsers() {
        String url = properties.readProperty("env.dev.url") + USERS;
        Response response = given()
                .when()
                .get(url + "/1")
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("\"George\""),
                "Пользователь не найден.");
    }
}
