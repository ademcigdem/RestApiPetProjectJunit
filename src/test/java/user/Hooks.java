package user;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pages.Pet;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class Hooks {

    public Response response;
    public Map<Object, Object> responseMap;
    public static int pet_id;
    public Pet requestPet;

    @BeforeEach
    public void createSetup() {
        baseURI = "https://petstore.swagger.io/v2/";

    }
}
