package user;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserFlow {


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
    public void createUserTest() {
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
    @Order(1)
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

    /**
     * Update the created user
     */
    @Test
    @Order(2)
    public void updateUser() {
        given().
                contentType(ContentType.JSON).
                accept("application/json").
                body(new File("src/test/resources/requestFile/updateUser.json")).
                when().
                put("/dan_greaker").
                then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("message", is("4444"),
                        "code", is(200)).log().all();

    }

    /**
     * Get updated user
     */
    @Test
    @Order(3)
    public void getUpdatedUser() {
        given().
                accept("application/json").
                when().
                get("/john_doey").
                then().
                statusCode(200).
                contentType(ContentType.JSON).log().all().
                body("id", is(4444),
                        "username", is("john_doey"),
                        "email", is("john_doey@gmail.com"));

    }

    /**
     * Delete the created user
     */
    @Test
    @Order(4)
    public void deleteUser() {
        given().
                contentType(ContentType.JSON).
                accept("application/json").
                when().
                delete("/john_doey").
                then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("message", is("john_doey"),
                        "code", is(200)).log().all();

    }

    /**
     * Get deleted user
     */
    @Test
    @Order(5)
    public void getDeletedUser() {
        given().
                accept("application/json").
                when().
                get("/john_doey").
                then().
                assertThat().
                statusCode(404).
                contentType(ContentType.JSON).log().all().
                body("code", is(1),
                        "message", is("User not found"));

    }

}
