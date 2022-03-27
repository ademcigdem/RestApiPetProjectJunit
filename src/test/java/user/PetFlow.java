package user;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pages.Category;
import pages.Pet;
import pages.Tag;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static java.util.Collections.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class PetFlow extends Hooks{

    private Response response;


    /*************************************************
     * The Test creating a pet using the JSON file
     * The assertion using the map structure
     * POST METHOD
     *************************************************/

    @Test
    public void test1() {

        response = given().
                contentType(ContentType.JSON).
                accept("application/json").
                body(new File("src/test/resources/requestFile/createPet.json")).
                when().
                post("/pet");

        Map<Object, Object> responseMap = response.body().as(Map.class);

        assertEquals(responseMap.get("id"), 9898);
        assertEquals(responseMap.get("name"), "fluffy");
        assertEquals(responseMap.get("status"), "available");
    }


    /*************************************************
     * The Test creating a pet using the POJO Pet classes structure
     * The JSON file updated by the Pet pages
     * POST METHOD
     *************************************************/

    @Test
    public void test2() {


        /**
         * The constructor updating the request Json payload
         */
        Pet requestPet = new Pet(9999,new Category(99,"fluff"),
                "ponpon", singletonList("null"), singletonList(new Tag(1010, "crash")),
                "available");

         given().
                contentType(ContentType.JSON).
                accept("application/json").
                body(requestPet).
                when().
                post("/pet").
                then().assertThat().log().all().
                statusCode(200).
                body("id", equalTo(9999),
                        "name",is("ponpon"),
                        "status",is("available"),
                        "tags[0].id",is(1010));


    }

}
