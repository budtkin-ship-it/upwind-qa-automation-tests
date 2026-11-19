package api.tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class BookingApiTest extends ApiBaseTest {
    @Test
    public void canCreateAndRetrieveBooking() {
        final String firstName = "Jamie";
        final String lastName = "Morgan";
        final int totalPrice = 250;
        final boolean depositPaid = true;
        final String checkIn = "2030-05-12";
        final String checkOut = "2030-05-16";

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
                """.formatted(firstName, lastName, totalPrice, depositPaid, checkIn, checkOut);

        Integer bookingId = apiRequest()
                .contentType(ContentType.JSON)
                .body(bookingBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("bookingid");

        assertNotNull(bookingId, "POST /booking should return a booking ID");
        assertTrue(bookingId > 0, "Booking ID should be positive, but was " + bookingId);

        apiRequest()
                .pathParam("id", bookingId)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(200)
                // Group these checks so all field mismatches are reported in one run, similar to SoftAssert.
                .body(
                        "firstname", equalTo(firstName),
                        "lastname", equalTo(lastName),
                        "totalprice", equalTo(totalPrice),
                        "depositpaid", equalTo(depositPaid),
                        "bookingdates.checkin", equalTo(checkIn),
                        "bookingdates.checkout", equalTo(checkOut));
    }

    // Verifies that requesting a non-existing booking returns 404 Not Found.
    @Test
    public void returnsNotFoundForNonExistingBooking() {
        apiRequest()
                .get("/booking/0")
                .then()
                .statusCode(404);
    }
}
