package lesson1;

import config.ApplicationProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.*;
import static io.restassured.RestAssured.given;

public class SSTests {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Получение списка пользователей.")
    public void getUsers() {
        String id = "1";
        String url = properties.readProperty("env.dev.url") + USERS;
        Response response = given()
                .when()
                .get(url)
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("\"George\""),
                "Пользователь не найден.");
    }

    @Test(description = "Получение пользователя по id.")
    public void getUserById() {
        String id = "1";
        String url = properties.readProperty("env.dev.url") + USER_ID;
        Response response = given()
                .when()
                .pathParam("id", id)
                .get(url)
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("\"George\""),
                "Пользователь не найден.");
    }
}
