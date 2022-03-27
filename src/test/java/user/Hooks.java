package user;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;

public class Hooks {

    @BeforeEach
    public void createSetup() {
        baseURI = "https://petstore.swagger.io/v2/";

    }
}
