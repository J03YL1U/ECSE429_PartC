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

        FileWriter createTimeTodoCSV = new FileWriter("csv/1-create-todo-object-time.csv");
        createTimeTodoCSV.append("Total Objects,Time (ms)\n");

        FileWriter modifyTimeTodoCSV = new FileWriter("csv/1-modify-todo-object-time.csv");
        modifyTimeTodoCSV.append("Total Objects,Time (ms)\n");

        FileWriter deleteTimeTodoCSV = new FileWriter("csv/1-delete-todo-object-time.csv");
        deleteTimeTodoCSV.append("Total Objects,Time (ms)\n");

        FileWriter createCPUTodoCSV = new FileWriter("csv/1-create-todo-object-cpu.csv");
        createCPUTodoCSV.append("Total Objects,CPU Usage\n");

        FileWriter modifyCPUTodoCSV = new FileWriter("csv/1-modify-todo-object-cpu.csv");
        modifyCPUTodoCSV.append("Total Objects,CPU Usage\n");

        FileWriter deleteCPUTodoCSV = new FileWriter("csv/1-delete-todo-object-cpu.csv");
        deleteCPUTodoCSV.append("Total Objects,CPU Usage\n");

        FileWriter createMemoryTodoCSV = new FileWriter("csv/1-create-todo-object-memory.csv");
        createMemoryTodoCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter modifyMemoryTodoCSV = new FileWriter("csv/1-modify-todo-object-memory.csv");
        modifyMemoryTodoCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter deleteMemoryTodoCSV = new FileWriter("csv/1-delete-todo-object-memory.csv");
        deleteMemoryTodoCSV.append("Total Objects,Memory Usage (MB)\n");

        TodoTest todoTest = new TodoTest();

        //--------------------------------------------------------Category Setup--------------------------------------------------------//

        FileWriter createTimeCategoryCSV = new FileWriter("csv/2-create-category-object-time.csv");
        createTimeCategoryCSV.append("Total Objects,Time (ms)\n");

        FileWriter modifyTimeCategoryCSV = new FileWriter("csv/2-modify-category-object-time.csv");
        modifyTimeCategoryCSV.append("Total Objects,Time (ms)\n");

        FileWriter deleteTimeCategoryCSV = new FileWriter("csv/2-delete-category-object-time.csv");
        deleteTimeCategoryCSV.append("Total Objects,Time (ms)\n");

        FileWriter createCPUCategoryCSV = new FileWriter("csv/2-create-category-object-cpu.csv");
        createCPUCategoryCSV.append("Total Objects,CPU Usage\n");

        FileWriter modifyCPUCategoryCSV = new FileWriter("csv/2-modify-category-object-cpu.csv");
        modifyCPUCategoryCSV.append("Total Objects,CPU Usage\n");

        FileWriter deleteCPUCategoryCSV = new FileWriter("csv/2-delete-category-object-cpu.csv");
        deleteCPUCategoryCSV.append("Total Objects,CPU Usage\n");

        FileWriter createMemoryCategoryCSV = new FileWriter("csv/2-create-category-object-memory.csv");
        createMemoryCategoryCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter modifyMemoryCategoryCSV = new FileWriter("csv/2-modify-category-object-memory.csv");
        modifyMemoryCategoryCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter deleteMemoryCategoryCSV = new FileWriter("csv/2-delete-category-object-memory.csv");
        deleteMemoryCategoryCSV.append("Total Objects,Memory Usage (MB)\n");

        CategoryTest categoryTest = new CategoryTest();

        for (int i = 0; i < 1000; i++) {

            long startTime;
            long endTime;
            
            Random random = new Random();
            PerformanceTest.createTodo("Test Todo object number#" + random.nextInt(), false, "This is description number#" + random.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create todo
            startTime = Calendar.getInstance().getTimeInMillis();
            todoTest.testCreateTodo();
            endTime = Calendar.getInstance().getTimeInMillis();

            createTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + (endTime-startTime) + "\n");
            createTimeTodoCSV.flush();

            double cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu + "\n");
            createCPUTodoCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryInMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + usedMemoryInMB + "\n");
            createMemoryTodoCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Todo operation:");
            System.out.println("Total instances \tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (endTime - startTime));


            // Modify todo
            startTime = Calendar.getInstance().getTimeInMillis();
            todoTest.testModifyTodo();
            endTime = Calendar.getInstance().getTimeInMillis();

            modifyTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + (endTime-startTime) + "\n");
            modifyTimeTodoCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu + "\n");
            modifyCPUTodoCSV.flush();

            long modifyMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double modifyMemoryInMB = modifyMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + modifyMemoryInMB + "\n");
            modifyMemoryTodoCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Todo operation:");
            System.out.println("Total instances \tModify Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (endTime - startTime));

            // Delete todo
            startTime = Calendar.getInstance().getTimeInMillis();
            todoTest.testDeleteTodo();
            endTime = Calendar.getInstance().getTimeInMillis();

            deleteTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + (endTime-startTime) + "\n");
            deleteTimeTodoCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu + "\n");
            deleteCPUTodoCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryInMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + deleteMemoryInMB + "\n");
            deleteMemoryTodoCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Todo operation:");
            System.out.println("Total instances \tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (endTime - startTime));
        }

        for (int i = 0; i < 1000; i++) {

            long startTime;
            long endTime;

            Random random = new Random();
            PerformanceTest.createCategory("Test category object number#" + random.nextInt(), "This is description number#" + random.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create category
            startTime = Calendar.getInstance().getTimeInMillis();
            categoryTest.testCreateCategory();
            endTime = Calendar.getInstance().getTimeInMillis();

            createTimeCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + (endTime-startTime) + "\n");
            createTimeCategoryCSV.flush();

            double cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu + "\n");
            createCPUCategoryCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryInMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + usedMemoryInMB + "\n");
            createMemoryCategoryCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Category operation:");
            System.out.println("Total instances \tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (endTime - startTime));

            // Modify category
            startTime = Calendar.getInstance().getTimeInMillis();
            categoryTest.testModifyCategory();
            endTime = Calendar.getInstance().getTimeInMillis();

            modifyTimeCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + (endTime-startTime) + "\n");
            modifyTimeCategoryCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu + "\n");
            modifyCPUCategoryCSV.flush();

            long modifyMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double modifyMemoryInMB = modifyMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + modifyMemoryInMB + "\n");
            modifyMemoryCategoryCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Category operation:");
            System.out.println("Total instances \tModify Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (endTime - startTime));

            // Delete todo
            startTime = Calendar.getInstance().getTimeInMillis();
            categoryTest.testDeleteCategory();
            endTime = Calendar.getInstance().getTimeInMillis();

            deleteTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + (endTime-startTime) + "\n");
            deleteTimeTodoCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + cpu + "\n");
            deleteCPUCategoryCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryInMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + deleteMemoryInMB + "\n");
            deleteMemoryCategoryCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Category operation:");
            System.out.println("Total instances \tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + (endTime - startTime));
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
