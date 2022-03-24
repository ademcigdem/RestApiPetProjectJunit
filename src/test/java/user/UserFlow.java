package user;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateUser {


    private Response response;

    @BeforeEach
    public void createSetup() {
        baseURI = "https://petstore.swagger.io/v2/";
        basePath = "user";
    }


    /**
     * The test expecting to create successfully a user on the page
     */
    @Test
    public void createUser() {
        response =
                given().
                        contentType(ContentType.JSON).
                        accept("application/json").
                        body(new File("src/test/resources/requestFile/createUser.json")).
                        when().
                        post();

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).log().all().
                body(
                        "message", is("5555"),
                        "code", is(200));

    }

    /**
     * Get created user credentials
     */

    @Test
    public void getCreatedUser() {
        given().
                accept("application/json").
                when().
                get("/dan_greaker").
                then().
                statusCode(200).
                contentType(ContentType.JSON).log().all().
                body("id", is(5555),
                        "username", is("dan_greaker"),
                        "email", is("dan.greaker@gmail.com"));

    }

}
