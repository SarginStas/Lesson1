package lesson1;
import config.ApplicationProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.USERS;
import static io.restassured.RestAssured.given;
public class ABurnTests {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Получение списка пользователей по id.")
    public void getUsersById() {
        int id = 6;
        String url = properties.readProperty("env.dev.url") + USERS + "/" + id;
        Response response = given()
                .when()
                .get(url)
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("\"Tracey\""),
                "Пользователь не найден.");
    }
}
