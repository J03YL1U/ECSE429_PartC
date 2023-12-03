package ApiTests;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Calendar;

import com.sun.management.OperatingSystemMXBean;
import java.lang.Runtime;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.util.Random;
public class PerformanceTest {
    //Authors: Joey Liu, Ke yan, Abiola Olaniyan, Mihail Cali

    public static int TOTAL_OBJECTS_CREATED = 0;

    public static void main(String[] args) throws IOException {

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        //--------------------------------------------------------Todo Setup--------------------------------------------------------//

        FileWriter createTimeTodoCSV = new FileWriter("csv/create-todo-object-time.csv");
        createTimeTodoCSV.append("Total Objects,Time (ms)\n");

        FileWriter modifyTimeTodoCSV = new FileWriter("csv/modify-todo-object-time.csv");
        modifyTimeTodoCSV.append("Total Objects,Time (ms)\n");

        FileWriter deleteTimeTodoCSV = new FileWriter("csv/delete-todo-object-time.csv");
        deleteTimeTodoCSV.append("Total Objects,Time (ms)\n");

        FileWriter createCPUTodoCSV = new FileWriter("csv/create-todo-object-cpu.csv");
        createCPUTodoCSV.append("Total Objects,CPU Usage\n");

        FileWriter modifyCPUTodoCSV = new FileWriter("csv/modify-todo-object-cpu.csv");
        modifyCPUTodoCSV.append("Total Objects,CPU Usage\n");

        FileWriter deleteCPUTodoCSV = new FileWriter("csv/delete-todo-object-cpu.csv");
        deleteCPUTodoCSV.append("Total Objects,CPU Usage\n");

        FileWriter createMemoryTodoCSV = new FileWriter("csv/create-todo-object-memory.csv");
        createMemoryTodoCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter modifyMemoryTodoCSV = new FileWriter("csv/modify-todo-object-memory.csv");
        modifyMemoryTodoCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter deleteMemoryTodoCSV = new FileWriter("csv/delete-todo-object-memory.csv");
        deleteMemoryTodoCSV.append("Total Objects,Memory Usage (MB)\n");

        TodoTest todoTest = new TodoTest();

        //--------------------------------------------------------Category Setup--------------------------------------------------------//

        FileWriter createTimeCategoryCSV = new FileWriter("csv/create-category-object-time.csv");
        createTimeCategoryCSV.append("Total Objects,Time (ms)\n");

        FileWriter modifyTimeCategoryCSV = new FileWriter("csv/modify-category-object-time.csv");
        modifyTimeCategoryCSV.append("Total Objects,Time (ms)\n");

        FileWriter deleteTimeCategoryCSV = new FileWriter("csv/delete-category-object-time.csv");
        deleteTimeCategoryCSV.append("Total Objects,Time (ms)\n");

        FileWriter createCPUCategoryCSV = new FileWriter("csv/create-category-object-cpu.csv");
        createCPUCategoryCSV.append("Total Objects,CPU Usage\n");

        FileWriter modifyCPUCategoryCSV = new FileWriter("csv/modify-category-object-cpu.csv");
        modifyCPUCategoryCSV.append("Total Objects,CPU Usage\n");

        FileWriter deleteCPUCategoryCSV = new FileWriter("csv/delete-category-object-cpu.csv");
        deleteCPUCategoryCSV.append("Total Objects,CPU Usage\n");

        FileWriter createMemoryCategoryCSV = new FileWriter("csv/create-category-object-memory.csv");
        createMemoryCategoryCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter modifyMemoryCategoryCSV = new FileWriter("csv/modify-category-object-memory.csv");
        modifyMemoryCategoryCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter deleteMemoryCategoryCSV = new FileWriter("csv/delete-category-object-memory.csv");
        deleteMemoryCategoryCSV.append("Total Objects,Memory Usage (MB)\n");

        CategoryTest categoryTest = new CategoryTest();

        for (int i = 0; i < 1000; i++) {

            long start_time;
            long end_time;
            
            Random rn = new Random();
            PerformanceTest.createTodo("Test Todo object number#" + rn.nextInt(), false, "This is description number#" + rn.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create todo
            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testCreateTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            createTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + (end_time-start_time) + "\n");
            createTimeTodoCSV.flush();

            double cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu_load + "\n");
            createCPUTodoCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + usedMemoryMB + "\n");
            createMemoryTodoCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Todo operation:");
            System.out.println("Total instances \tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (end_time - start_time));


            // Modify todo
            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testModifyTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            modifyTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + (end_time-start_time) + "\n");
            modifyTimeTodoCSV.flush();

            cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu_load + "\n");
            modifyCPUTodoCSV.flush();

            long modifyMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double modifyMemoryMB = modifyMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + modifyMemoryMB + "\n");
            modifyMemoryTodoCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Todo operation:");
            System.out.println("Total instances \tModify Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (end_time - start_time));

            // Delete todo
            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testDeleteTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            deleteTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + (end_time-start_time) + "\n");
            deleteTimeTodoCSV.flush();

            cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu_load + "\n");
            deleteCPUTodoCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + deleteMemoryMB + "\n");
            deleteMemoryTodoCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Todo operation:");
            System.out.println("Total instances \tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (end_time - start_time));
        }

        for (int i = 0; i < 1000; i++) {

            long start_time;
            long end_time;

            Random rn = new Random();
            PerformanceTest.createCategory("Test category object number#" + rn.nextInt(), "This is description number#" + rn.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create category
            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testCreateTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            createTimeCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + (end_time-start_time) + "\n");
            createTimeCategoryCSV.flush();

            double cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu_load + "\n");
            createCPUCategoryCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + usedMemoryMB + "\n");
            createMemoryCategoryCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Category operation:");
            System.out.println("Total instances \tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (end_time - start_time));

            // Modify category
            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testModifyTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            modifyTimeCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + (end_time-start_time) + "\n");
            modifyTimeCategoryCSV.flush();

            cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu_load + "\n");
            modifyCPUCategoryCSV.flush();

            long modifyMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double modifyMemoryMB = modifyMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + modifyMemoryMB + "\n");
            modifyMemoryCategoryCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Category operation:");
            System.out.println("Total instances \tModify Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (end_time - start_time));

            // Delete todo
            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testDeleteTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            deleteTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + (end_time-start_time) + "\n");
            deleteTimeTodoCSV.flush();

            cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu_load + "\n");
            deleteCPUCategoryCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + deleteMemoryMB + "\n");
            deleteMemoryCategoryCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Category operation:");
            System.out.println("Total instances \tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (end_time - start_time));
        }
    }

    /*
        create todo
    */
    private static void createTodo(String title, Boolean doneStatus, String description) {
        RequestSpecification request = RestAssured.given().baseUri("http://localhost:4567");

        JSONObject requestParams = new JSONObject();
        requestParams.put("title", title);
        requestParams.put("doneStatus", doneStatus);
        requestParams.put("description", description);

        request.body(requestParams.toJSONString());
        request.post("/todos");
    }

    private static void createCategory(String title, String description) {
        RequestSpecification request = RestAssured.given().baseUri("http://localhost:4567");

        JSONObject requestParams = new JSONObject();
        requestParams.put("title", title);
        requestParams.put("description", description);

        request.body(requestParams.toJSONString());
        request.post("/categories");
    }

}
