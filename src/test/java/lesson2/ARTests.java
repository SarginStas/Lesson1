package lesson2;

import config.ApplicationProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.AUTH;
import static io.restassured.RestAssured.given;

public class ARTests {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Получение токена")
    public void getToken() {
        String url = properties.readProperty("env.dev.url2") + AUTH;
        String body = "{\"username\" : \"admin\", \"password\" : \"password123\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().asString().contains("\"token\""),
                "Токен не получен");
    }
    }

