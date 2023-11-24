package ApiTests;

import io.restassured.RestAssured;
import io.restassured.response.*;
import io.restassured.specification.RequestSpecification;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;


public class TodoTest {

    int ID;

    public TodoTest() {
        RestAssured.baseURI = "http://localhost:4567";
    }

    // create todo test
    @Test
    public void testCreateTodo() throws JSONException {

        String title = "title";
        boolean doneStatus = false;
        String description = "description";

        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("title", title);
        requestParams.put("doneStatus", doneStatus);
        requestParams.put("description", description);

        request.body(requestParams.toJSONString());

        Response response = request.post("/todos");
        response.then();
        String body = response.getBody().asString();
        org.json.JSONObject jsonResponse = new org.json.JSONObject(body);
        ID = jsonResponse.getInt("id");

        response.then()
                .assertThat()
                .statusCode(equalTo(201))
                .body("title", equalTo(title),
                        "doneStatus", equalTo(String.valueOf(doneStatus)),
                        "description", equalTo(description));
    }

    // edit todo test
    @Test
    public void testModifyTodo() {

        int todoId = ID;
        String newTitle = "new title";
        boolean newDoneStatus = true;
        String newDescription = "new description";

        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();

        requestParams.put("title", newTitle);
        requestParams.put("doneStatus", newDoneStatus);
        requestParams.put("description", newDescription);

        request.body(requestParams.toJSONString());

        Response response = request.post("/todos/" + todoId);

        response.then()
                .assertThat()
                .statusCode(equalTo(200))
                .body("title", equalTo(newTitle),
                        "doneStatus", equalTo(String.valueOf(newDoneStatus)),
                        "description", equalTo(newDescription));
    }

    // delete todo test
    @Test
    public void testDeleteTodo() {
        int todoId = ID;

        RequestSpecification request = RestAssured.given();

        request.delete("/todos/" + todoId).then().assertThat().statusCode(200);
    }

}