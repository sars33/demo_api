import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class UserAccountActionsTest {
    private static final String BASE_URI = RestAssured.baseURI = "https://demoqa.com";
    private RequestSpecification request = RestAssured.given();
    private static final String CREDENTIALS = "testeru2:Test-123@2";

    @Test
    public void authenticateUserTest() {
        createLogin();
    }

    @Test
    public void addBookToLibraryTest() {
        createLogin();
        addBooktoLibrary();
    }

    private void createLogin() {
        baseURI = BASE_URI;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
        byte[] encodedCredentials = Base64.encodeBase64(CREDENTIALS.getBytes());
        String encodedCredentialsAsString = new String(encodedCredentials);
        request.header("Authorization", "Basic " + encodedCredentialsAsString);
    }

    private void addBooktoLibrary() {

        String isbn = "{\r\n" +
                "  \"userId\": \"2f1eb8c0-4c94-4e1e-81b0-6f4dd1d2cdc4\",\r\n" +
                "  \"collectionOfIsbns\": [\r\n" +
                "    {\r\n" +
                "      \"isbn\": \"9781449331818\"\r\n" +
                "    }\r\n" +
                "  ]\r\n" +
                "}";
        Response response = request.body(isbn).post("/BookStore/v1/Books");
        int statusCode = response.getStatusCode();
        response.prettyPrint();
        Assertions.assertEquals(201, statusCode);
    }
}






