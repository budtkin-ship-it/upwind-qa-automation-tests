package api.tests;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public abstract class ApiBaseTest {
    private String baseUri;

    @BeforeClass
    public void loadApiConfiguration() {
        baseUri = ResourceBundle.getBundle("selenide").getString("api.baseUrl");
    }

    protected RequestSpecification apiRequest() {
        return given()
                .baseUri(baseUri)
                .accept("application/json");
    }
}
