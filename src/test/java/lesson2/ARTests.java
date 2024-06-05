package lesson2;

import config.ApplicationProperties;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static config.Urls.AUTH;
import static io.restassured.RestAssured.given;

public class ARTests {
    ApplicationProperties properties = new ApplicationProperties();

    @Test(description = "Получение токена")
    public void getToken() {

        String user="admin";
        String userPass="password123";
        String url = properties.readProperty("env.dev.url2") + AUTH;
        Response response = given().auth()
                .form("user","userPass")
                .when()
                .post(url)
                .body(user, userPass)
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.body().to.be.an("String"),
                "Токен не получен");
    }
    }

