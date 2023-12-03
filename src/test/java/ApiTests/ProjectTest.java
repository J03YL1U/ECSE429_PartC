package ApiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class ProjectTest {
    int currentID;

    // Define the default base URI for all calls
    public ProjectTest() {
        RestAssured.baseURI = "http://localhost:4567";
    }

    @Test
    public void testCreateProject() throws JSONException {
        // Create a JSON object for the request body
        String title = "title";
        boolean completed = false;
        boolean active = false;
        String description = "description";

        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", active);
        obj.put("description", description);

        // Send the request
        Response response = RestAssured.given().body(obj.toJSONString())
                .post("/projects");
        response.then();

        String responseBody = response.getBody().asString();
        org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
        currentID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Use the matchers associated with the response for status code and attributes
        response.then()
                .statusCode(equalTo(201))
                .body("title", equalTo(title),
                        "completed", equalTo(String.valueOf(completed)),
                        "active", equalTo(String.valueOf(active)),
                        "description", equalTo(description));
    }

    @Test
    public void testUpdateProject() {
        // Retrieve the project ID from top of class
        int projectId = currentID;

        // JSON object with updated attributes
        String updatedTitle = "newTitle";
        boolean updatedCompleted = true;
        boolean updatedActive = true;
        String updatedDescription = "newDescription";

        JSONObject obj = new JSONObject();

        obj.put("title", updatedTitle);
        obj.put("completed", updatedCompleted);
        obj.put("active", updatedActive);
        obj.put("description", updatedDescription);

        Response response = RestAssured.given().body(obj.toJSONString())
                .post("/projects/" + projectId);

        // Verify the response
        response.then()
                .statusCode(equalTo(200))
                .body("title", equalTo(updatedTitle),
                        "completed", equalTo(String.valueOf(updatedCompleted)),
                        "active", equalTo(String.valueOf(updatedActive)),
                        "description", equalTo(updatedDescription));
    }

    @Test
    public void testDeleteProject() {
        // Retrieve the project ID from top of class
        int projectId = currentID;

        // Call to delete project object
        RestAssured.given().delete("/projects/" + projectId)
                .then()
                .statusCode(200);
    }
}
