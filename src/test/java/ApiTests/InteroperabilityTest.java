package ApiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class InteroperabilityTest {

    public InteroperabilityTest() {
        RestAssured.baseURI = "http://localhost:4567";
    }

    int projectID;
    int categoryID;
    int todoID;

    // Will focus on the relationship between projects and the other models
    @Test
    public void testCreateInterProjectAndCategory() {
        // Create a JSON object for the request body
        String projectTitle = "projectTitle";
        boolean completed = false;
        boolean active = false;
        String projectDescription = "projectDescription";

        JSONObject projectObj = new JSONObject();
        projectObj.put("title", projectTitle);
        projectObj.put("completed", completed);
        projectObj.put("active", active);
        projectObj.put("description", projectDescription);

        // Send the request
        Response projResponse = RestAssured.given().body(projectObj.toJSONString())
                .post("/projects");
        projResponse.then();

        String responseBody = projResponse.getBody().asString();
        org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
        projectID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Use the matchers associated with the response for status code and attributes
        // First check that project was properly created
        projResponse.then()
                .statusCode(equalTo(201))
                .body("title", equalTo(projectTitle),
                        "completed", equalTo(String.valueOf(completed)),
                        "active", equalTo(String.valueOf(active)),
                        "description", equalTo(projectDescription));

        // Create the relationship between the created project and a new category
        // Create a JSON object for the request body
        String categoryTitle = "categoryTitle";
        String categoryDescription = "categoryDescription";

        JSONObject categoryObj = new JSONObject();
        categoryObj.put("title", categoryTitle);
        categoryObj.put("description", categoryDescription);

        // Send the request
        Response categoryResponse = RestAssured.given().body(categoryObj.toJSONString())
                .post("/projects/" + projectID + "/categories");
        categoryResponse.then();

        responseBody = categoryResponse.getBody().asString();
        jsonResponse = new org.json.JSONObject(responseBody);
        categoryID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Use the matchers associated with the response for status code and attributes
        // First check that project was properly created
        categoryResponse.then()
                .statusCode(equalTo(201))
                .body("title", equalTo(categoryTitle),
                        "description", equalTo(categoryDescription));
    }

    @Test
    public void testDeleteInterProjectAndCategory() {
        int currProjID = projectID;
        int currCategoryID = categoryID;

        RestAssured.given().delete("/projects/" + currProjID + "/categories/" + currCategoryID)
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateInterCategoryAndTodo() {
        // Create a JSON object for the request body
        String categoryTitle = "categoryTitle";
        String categoryDescription = "categoryDescription";

        JSONObject categoryObj = new JSONObject();
        categoryObj.put("title", categoryTitle);
        categoryObj.put("description", categoryDescription);

        // Send the request
        Response categoryResponse = RestAssured.given().body(categoryObj.toJSONString())
                .post("/categories");
        categoryResponse.then();

        String responseBody = categoryResponse.getBody().asString();
        org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
        categoryID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Use the matchers associated with the response for status code and attributes
        // First check that project was properly created
        categoryResponse.then()
                .statusCode(equalTo(201))
                .body("title", equalTo(categoryTitle),
                        "description", equalTo(categoryDescription));

        // Create the relationship between the created category and a new todo
        // Create a JSON object for the request body
        String todoTitle = "todoTitle";
        Boolean doneStatus = false;
        String todoDescription = "todoDescription";

        JSONObject todoObj = new JSONObject();
        todoObj.put("title", todoTitle);
        todoObj.put("doneStatus", doneStatus);
        todoObj.put("description", todoDescription);

        // Send the request
        Response todoResponse = RestAssured.given().body(todoObj.toJSONString())
                .post("/categories/" + projectID + "/todos");
        todoResponse.then();

        responseBody = todoResponse.getBody().asString();
        jsonResponse = new org.json.JSONObject(responseBody);
        todoID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Use the matchers associated with the response for status code and attributes
        // First check that project was properly created
        todoResponse.then()
                .statusCode(equalTo(201))
                .body("title", equalTo(todoTitle),
                        "doneStatus", equalTo(String.valueOf(doneStatus)),
                        "description", equalTo(todoDescription));
    }

    @Test
    public void testDeleteInterCategoryAndTodo() {
        int currCategoryID = categoryID;
        int currTodoID = todoID;

        RestAssured.given().delete("/categories/" + currCategoryID + "/todos/" + currTodoID);
    }

    @Test
    public void testCreateInterTodoAndProject() {
        // Create a JSON object for the request body
        String todoTitle = "todoTitle";
        Boolean doneStatus = false;
        String todoDescription = "todoDescription";

        JSONObject todoObj = new JSONObject();
        todoObj.put("title", todoTitle);
        todoObj.put("doneStatus", doneStatus);
        todoObj.put("description", todoDescription);

        // Send the request
        Response todoResponse = RestAssured.given().body(todoObj.toJSONString())
                .post("/todos");
        todoResponse.then();

        String responseBody = todoResponse.getBody().asString();
        org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
        todoID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Use the matchers associated with the response for status code and attributes
        // First check that project was properly created
        todoResponse.then()
                .statusCode(equalTo(201))
                .body("title", equalTo(todoTitle),
                        "doneStatus", equalTo(String.valueOf(doneStatus)),
                        "description", equalTo(todoDescription));

        // Create the relationship between the created todo and a new project
        // Create a JSON object for the request body
        String projectTitle = "projectTitle";
        boolean completed = false;
        boolean active = false;
        String projectDescription = "projectDescription";

        JSONObject projectObj = new JSONObject();
        projectObj.put("title", projectTitle);
        projectObj.put("completed", completed);
        projectObj.put("active", active);
        projectObj.put("description", projectDescription);

        // Send the request
        Response projResponse = RestAssured.given().body(projectObj.toJSONString())
                .post("/todos/" + todoID + "/tasksof");
        projResponse.then();

        responseBody = projResponse.getBody().asString();
        jsonResponse = new org.json.JSONObject(responseBody);
        projectID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Use the matchers associated with the response for status code and attributes
        // First check that project was properly created
        projResponse.then()
                .statusCode(equalTo(201))
                .body("title", equalTo(projectTitle),
                        "completed", equalTo(String.valueOf(completed)),
                        "active", equalTo(String.valueOf(active)),
                        "description", equalTo(projectDescription));
    }

    @Test
    public void testDeleteInterTodoAndProject() {
        int currTodoID = todoID;
        int currProjID = projectID;

        RestAssured.given().delete("/todos/" + currTodoID + "/tasksof/" + currProjID)
                .then()
                .statusCode(200);
    }

}
