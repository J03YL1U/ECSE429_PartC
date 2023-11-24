package ApiTests;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Calendar;

import com.sun.management.OperatingSystemMXBean;
import java.lang.Runtime;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.util.Random;
public class PerformanceTest {
    //Authors: Joey Liu

    public static int TOTAL_OBJECTS = 3;

    public static void main(String[] args) throws IOException {

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        // Setup CSV files

        //todo
        FileWriter createTimeCSV = new FileWriter("csv/create-todo-time.csv");
        createTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter editTimeCSV = new FileWriter("csv/edit-todo-time.csv");
        editTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter deleteTimeCSV = new FileWriter("csv/delete-todo-time.csv");
        deleteTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter createCSV = new FileWriter("csv/create-todo-cpu.csv");
        createCSV.append("Total Objects,CPU Usage\n");

        FileWriter editCSV = new FileWriter("csv/edit-todo-cpu.csv");
        editCSV.append("Total Objects,CPU Usage\n");

        FileWriter deleteCSV = new FileWriter("csv/delete-todo-cpu.csv");
        deleteCSV.append("Total Objects,CPU Usage\n");

        FileWriter createMemCSV = new FileWriter("csv/create-todo-mem.csv");
        createMemCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter editMemCSV = new FileWriter("csv/edit-todo-mem.csv");
        editMemCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter deleteMemCSV = new FileWriter("csv/delete-todo-mem.csv");
        deleteMemCSV.append("Total Objects,Memory Usage (MB)\n");

        TodoTest todoTest = new TodoTest();

        // Setup todos
        setupTodos(TOTAL_OBJECTS);

        // TODOS

        for (int i = 0; i < 1000; i++) {

            long start_time;
            long end_time;

            // Add another todo
            Random rn = new Random();
            PerformanceTest.createTodo("Test Todo #" + rn.nextInt(), false, "This is a test description #" + rn.nextInt());
            TOTAL_OBJECTS++;

            // Create todo

            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testCreateTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            createTimeCSV.append(TOTAL_OBJECTS + "," + (end_time-start_time) + "\n");
            createTimeCSV.flush();

            double cpu_load = osBean.getProcessCpuLoad() * 100;
            createCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            createCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemCSV.append(TOTAL_OBJECTS + "," + usedMemoryMB + "\n");
            createMemCSV.flush();

            // Print results for create operation
            System.out.println("Results for Create Todo operation:");
            System.out.println("Total instances \tCreate Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));
            System.out.println("---");


            // Modify todo

            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testModifyTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            editTimeCSV.append(TOTAL_OBJECTS + "," + (end_time-start_time) + "\n");
            editTimeCSV.flush();

            cpu_load = osBean.getProcessCpuLoad() * 100;
            editCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            editCSV.flush();

            long editMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double editMemoryMB = editMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            editMemCSV.append(TOTAL_OBJECTS + "," + editMemoryMB + "\n");
            editMemCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modify Todo operation:");
            System.out.println("Total instances \tModify Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));
            System.out.println("---");


            // Delete todo

            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testDeleteTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            deleteTimeCSV.append(TOTAL_OBJECTS + "," + (end_time-start_time) + "\n");
            deleteTimeCSV.flush();

            cpu_load = osBean.getProcessCpuLoad() * 100;
            deleteCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            deleteCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemCSV.append(TOTAL_OBJECTS + "," + deleteMemoryMB + "\n");
            deleteMemCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Delete Todo operation:");
            System.out.println("Total instances \tDelete Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));
            System.out.println("---");
        }
    }

    public static void setupTodos(int num_todos) {
        RestAssured.baseURI = "http://localhost:4567";

        System.out.print("Initializing todos...");
        String title = "Test Task";
        String description = "Simple description";
        for (int i = 0; i < num_todos; i++) {
            PerformanceTest.createTodo(title + (i + 1), false, description + (i + 1));
        }
        System.out.print("DONE\n---\n");

    }
    /*
        create todo
    */
    private static int createTodo(String titleOfTodo, Boolean doneStatus, String descriptionOfTodo) {
        RequestSpecification request = RestAssured.given().baseUri("http://localhost:4567");

        JSONObject requestParams = new JSONObject();
        requestParams.put("title", titleOfTodo);
        requestParams.put("doneStatus", doneStatus);
        requestParams.put("description", descriptionOfTodo);

        request.body(requestParams.toJSONString());

        Response response = request.post("/todos");

        if (response.statusCode() != 201) {
            return -1;
        }
        return 0;
    }

}
