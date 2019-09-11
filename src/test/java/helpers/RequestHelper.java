package helpers;

import extentions.CallContext;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Objects;

public class RequestHelper {

    private static Logger logger = LogManager.getLogger(RequestHelper.class);

    public static <T, R> R post(CallContext call, String url, T requestType, Type responseType, int statusCode) throws Exception {
        return post(call, url, requestType, statusCode).getBody().as(responseType);
    }

    public static <T> Response post(CallContext call, String url, T requestType, int statusCode) throws Exception {
        Response response = withBaseSpecifications(call)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(SerializationHelper.serialize(requestType))
                .when()
                .post(url);

        logger.info("POST: {} ", url);
        response.getBody().prettyPrint();
        response.then().assertThat().statusCode(statusCode);
        return response;
    }


    public static <T, R> R get(CallContext call, String url, Type responseType, int statusCode, String paramKey, Object paramValue,
                               Object... paramPairs) {
        Response response = withBaseSpecifications(call)
                .when()
                .queryParams(paramKey, paramValue, paramPairs)
                .get(url);

        logger.info("GET: {} ", url);
        response.getBody().prettyPrint();
        response.then().assertThat().statusCode(statusCode);
        return response.getBody().as(responseType);
    }

    public static <T, R> R get(CallContext call, String url, Type responseType, int statusCode) {
        return get(call, url, statusCode).getBody().as(responseType);
    }

    public static Response get(CallContext call, String url, int statusCode) {
        Response response = withBaseSpecifications(call)
                .when()
                .get(url);

        logger.info("GET: {} ", url);
        response.getBody().prettyPrint();
        response.then().assertThat().statusCode(statusCode);
        return response;
    }

    public static Response delete(CallContext call, String url, int statusCode) {
        Response response = withBaseSpecifications(call)
                .when()
                .delete(url);

        logger.info("DELETE: {} ", url);
        response.getBody().prettyPrint();
        response.then().assertThat().statusCode(statusCode);
        return response;
    }

    private static RequestSpecification withBaseSpecifications(CallContext callContext) {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = callContext.getBasePath().getHost();
        if (Objects.nonNull(callContext.getAuthScheme())) {
            RestAssured.authentication = callContext.getAuthScheme();
        }
        return RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

}
