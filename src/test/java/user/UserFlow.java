package user;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class UserFlow extends Hooks {



    /**************************************************************
     * The test expecting to create successfully a user on the page
     *************************************************************/
    @Test
    public void test1() {
        File file = new File("src/test/resources/requestFile/createUser.json");
        response =
                given().
                    contentType(ContentType.JSON).
                    accept("application/json").
                    body(file).
                when().
                    post("/user");

        response.
                then().
                assertThat().
                    statusCode(HTTP_OK).
                    contentType(ContentType.JSON).
                    log().all().
                    body(
                        "message", is("5555"),
                        "code", is(HTTP_OK)).
                    body(
                            matchesJsonSchemaInClasspath("responseSchema/createUserSchema.json"));

    }

    /******************************
     * Get created user credentials
     * GET METHOD
     *****************************/

    @Test
    @Order(1)
    public void test2() {
            given().
                accept("application/json").
            when().
                get("/user/dan_greaker").
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("id", is(5555),
                        "username", equalTo("dan_greaker"),
                        "email", is("dan.greaker@gmail.com"));

    }

    /**************************************
     * Update the created user(dan_greaker)
     * PUT METHOD
     ********************************/
    @Test
    @Order(2)
    public void test3() {
            given().
                contentType(ContentType.JSON).
                accept("application/json").
                body(new File("src/test/resources/requestFile/updateUser.json")).
            when().
                put("/user/dan_greaker").
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("message", is("4444"),
                        "code", is(200)).log().all();

    }

    /******************************
     * Get updated user (john_doey)
     * GET METHOD
     *****************************/
    @Test
    @Order(3)
    public void test4() {
            given().
                accept("application/json").
            when().
                get("/user/john_doey").
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("id", is(4444),
                        "username", is("john_doey"),
                        "email", is("john_doey@gmail.com"));

    }

    /***********************************
     * Delete the updated user(john_doey)
     * DELETE MRTHOD
     **********************************/
    @Test
    @Order(4)
    public void test5() {
            given().
                contentType(ContentType.JSON).
                accept("application/json").
            when().
                delete("/user/john_doey").
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all().
                body("message", is("john_doey"),
                        "code", is(200)).log().all();

    }

    /*****************************************
     * Delete the first created user(dan_greaker)
     * DELETE METHOD
     ****************************************/
    @Test
    @Order(5)
    public void test6() {
            given().
                contentType(ContentType.JSON).
                accept("application/json").
            when().
                delete("/user/dan_greaker").
            then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("message", is("dan_greaker"),
                        "code", is(200)).log().all();

    }

    /*******************************
     * Get deleted user (john_doey)
     * Validated user already deleted.
     * Thr test suppose to get error message
     * GET METHOD
     *****************************/
    @Test
    @Order(6)
    public void test7() {
            given().
                accept("application/json").
            when().
                get("/user/john_doey").
            then().
                assertThat().
                statusCode(404).
                contentType(ContentType.JSON).
                log().all().
                body("code", is(1),
                        "message", is("User not found"));

    }

    /*******************************
     * Get deleted user (dan_graker)
     * Validated user already deleted.
     * The test suppose to get error message
     * GET METHOD
     *****************************/
    @Test
    @Order(6)
    public void test8() {
            given().
                accept("application/json").
            when().
                get("/user/dan_greaker").
            then().assertThat().
                statusCode(HTTP_NOT_FOUND).
                contentType(ContentType.JSON).
                log().all().
                body("code", is(1),
                        "message", is("User not found"));

    }

}
