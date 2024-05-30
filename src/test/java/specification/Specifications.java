package specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {
    public static RequestSpecification requestSpecification (String url) {
        return new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setRelaxedHTTPSValidation()
                .setContentType(ContentType.JSON)
                .setBaseUri(url)
                .build();
    }

    public static ResponseSpecification responseSpecification () {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .expectStatusCode(200)
                .build();
    }
}
