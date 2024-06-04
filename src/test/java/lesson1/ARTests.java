package lesson1;

import config.ApplicationProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.USER5;
import static io.restassured.RestAssured.given;

public class ARTests {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Поиск пользователя по ID=5")
    public void getUsersID() {
        String url = properties.readProperty("env.dev.url") + USER5;
        Response response = given()
                .when()
                .pathParam("id", "5")
                .get(url)
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("\"Charles\""),
                "ID=5 не найден");
    }
}
