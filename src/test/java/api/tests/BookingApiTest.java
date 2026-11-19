package api.tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class BookingApiTest {
    private static final String BASE_URI = "https://restful-booker.herokuapp.com";
    private static final String FIRST_NAME = "Jamie";
    private static final String LAST_NAME = "Morgan";
    private static final int TOTAL_PRICE = 250;
    private static final boolean DEPOSIT_PAID = true;
    private static final String CHECK_IN = "2030-05-12";
    private static final String CHECK_OUT = "2030-05-16";

    @Test
    public void canCreateAndRetrieveBooking() {
        String bookingBody = """
                {
                  "firstname": "%s",
                  "lastname": "%s",
                  "totalprice": %s,
                  "depositpaid": %b,
                  "bookingdates": {
                    "checkin": "%s",
                    "checkout": "%s"
                  }
                }
                """.formatted(FIRST_NAME, LAST_NAME, TOTAL_PRICE, DEPOSIT_PAID, CHECK_IN, CHECK_OUT);

        Integer bookingId = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .accept("application/json")
                .body(bookingBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("bookingid");

        assertNotNull(bookingId, "POST /booking should return a booking ID");
        assertTrue(bookingId > 0, "Booking ID should be positive, but was " + bookingId);

        given()
                .baseUri(BASE_URI)
                .accept("application/json")
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(200)
                // Group these checks so all field mismatches are reported in one run, similar to SoftAssert.
                .body(
                        "firstname", equalTo(FIRST_NAME),
                        "lastname", equalTo(LAST_NAME),
                        "totalprice", equalTo(TOTAL_PRICE),
                        "depositpaid", equalTo(DEPOSIT_PAID),
                        "bookingdates.checkin", equalTo(CHECK_IN),
                        "bookingdates.checkout", equalTo(CHECK_OUT));
    }
}
