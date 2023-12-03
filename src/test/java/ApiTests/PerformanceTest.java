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
    //Authors: Joey Liu, Ke Yan, Abiola Olaniyan, Mihail Cali

    public static int TOTAL_OBJECTS = 3;

    public static void main(String[] args) throws IOException {

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        /* Setup CSV files and test objects */
        // CSVs for todos
        FileWriter createTimeCSV = new FileWriter("csv/create-todo-object-time.csv");
        createTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter modifyTimeCSV = new FileWriter("csv/modify-todo-object-time.csv");
        modifyTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter deleteTimeCSV = new FileWriter("csv/delete-todo-object-time.csv");
        deleteTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter createCPUCSV = new FileWriter("csv/create-todo-object-cpu-usage.csv");
        createCPUCSV.append("Total Objects,CPU Usage\n");

        FileWriter modifyCPUCSV = new FileWriter("csv/modify-todo-object-cpu-usage.csv");
        modifyCPUCSV.append("Total Objects,CPU Usage\n");

        FileWriter deleteCPUCSV = new FileWriter("csv/delete-todo-object-cpu-usage.csv");
        deleteCPUCSV.append("Total Objects,CPU Usage\n");

        FileWriter createMemoryCSV = new FileWriter("csv/create-todo-object-memory.csv");
        createMemoryCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter modifyMemoryCSV = new FileWriter("csv/modify-todo-object-memory.csv");
        modifyMemoryCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter deleteMemoryCSV = new FileWriter("csv/delete-todo-object-memory.csv");
        deleteMemoryCSV.append("Total Objects,Memory Usage (MB)\n");

        TodoTest todoTest = new TodoTest();

        // CSVs for projects
        FileWriter createProjTimeCSV = new FileWriter("csv/create-proj-object-time.csv");
        createProjTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter modifyProjTimeCSV = new FileWriter("csv/modify-proj-object-time.csv");
        modifyProjTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter deleteProjTimeCSV = new FileWriter("csv/delete-proj-object-time.csv");
        deleteProjTimeCSV.append("Total Objects,Time (ms)\n");

        FileWriter createProjCPUCSV = new FileWriter("csv/create-proj-object-cpu-usage.csv");
        createProjCPUCSV.append("Total Objects,CPU Usage\n");

        FileWriter modifyProjCPUCSV = new FileWriter("csv/modify-proj-object-cpu-usage.csv");
        modifyProjCPUCSV.append("Total Objects,CPU Usage\n");

        FileWriter deleteProjCPUCSV = new FileWriter("csv/delete-proj-object-cpu-usage.csv");
        deleteProjCPUCSV.append("Total Objects,CPU Usage\n");

        FileWriter createProjMemoryCSV = new FileWriter("csv/create-proj-object-memory.csv");
        createProjMemoryCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter modifyProjMemoryCSV = new FileWriter("csv/modify-proj-object-memory.csv");
        modifyProjMemoryCSV.append("Total Objects,Memory Usage (MB)\n");

        FileWriter deleteProjMemoryCSV = new FileWriter("csv/delete-proj-object-memory.csv");
        deleteProjMemoryCSV.append("Total Objects,Memory Usage (MB)\n");

        ProjectTest projectTest = new ProjectTest();

        // Setup todos
        setupTodoObjects(TOTAL_OBJECTS);

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

            double cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            createCPUCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryCSV.append(TOTAL_OBJECTS + "," + usedMemoryMB + "\n");
            createMemoryCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Todo operation:");
            System.out.println("Total instances \tCreate Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));


            // Modify todo
            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testModifyTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            modifyTimeCSV.append(TOTAL_OBJECTS + "," + (end_time-start_time) + "\n");
            modifyTimeCSV.flush();

            cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyCPUCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            modifyCPUCSV.flush();

            long editMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double editMemoryMB = editMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyMemoryCSV.append(TOTAL_OBJECTS + "," + editMemoryMB + "\n");
            modifyMemoryCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Todo operation:");
            System.out.println("Total instances \tModify Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));

            // Delete todo
            start_time = Calendar.getInstance().getTimeInMillis();
            todoTest.testDeleteTodo();
            end_time = Calendar.getInstance().getTimeInMillis();

            deleteTimeCSV.append(TOTAL_OBJECTS + "," + (end_time-start_time) + "\n");
            deleteTimeCSV.flush();

            cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            deleteCPUCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryCSV.append(TOTAL_OBJECTS + "," + deleteMemoryMB + "\n");
            deleteMemoryCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Todo operation:");
            System.out.println("Total instances \tDelete Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));
        }

        // Setup projects
        setupProjectObjects(TOTAL_OBJECTS);

        // PROJECTS
        for (int i = 0; i < 1000; i++) {

            long start_time;
            long end_time;

            // Add another project
            Random rn = new Random();
            PerformanceTest.createProject("Test Project #" + rn.nextInt(), false, false,"This is a test description #" + rn.nextInt());
            TOTAL_OBJECTS++;

            // Create project
            start_time = Calendar.getInstance().getTimeInMillis();
            projectTest.testCreateProject();
            end_time = Calendar.getInstance().getTimeInMillis();

            createProjTimeCSV.append(TOTAL_OBJECTS + "," + (end_time-start_time) + "\n");
            createProjTimeCSV.flush();

            double cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createProjCPUCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            createProjCPUCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createProjMemoryCSV.append(TOTAL_OBJECTS + "," + usedMemoryMB + "\n");
            createProjMemoryCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Project operation:");
            System.out.println("Total instances \tCreate Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));


            // Modify project
            start_time = Calendar.getInstance().getTimeInMillis();
            projectTest.testUpdateProject();
            end_time = Calendar.getInstance().getTimeInMillis();

            modifyProjTimeCSV.append(TOTAL_OBJECTS + "," + (end_time-start_time) + "\n");
            modifyProjTimeCSV.flush();

            cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyProjCPUCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            modifyProjCPUCSV.flush();

            long editMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double editMemoryMB = editMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyProjMemoryCSV.append(TOTAL_OBJECTS + "," + editMemoryMB + "\n");
            modifyProjMemoryCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Project operation:");
            System.out.println("Total instances \tModify Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));

            // Delete project
            start_time = Calendar.getInstance().getTimeInMillis();
            projectTest.testDeleteProject();
            end_time = Calendar.getInstance().getTimeInMillis();

            deleteProjTimeCSV.append(TOTAL_OBJECTS + "," + (end_time-start_time) + "\n");
            deleteProjTimeCSV.flush();

            cpu_load = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteProjCPUCSV.append(TOTAL_OBJECTS + "," + cpu_load + "\n");
            deleteProjCPUCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteProjMemoryCSV.append(TOTAL_OBJECTS + "," + deleteMemoryMB + "\n");
            deleteProjMemoryCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Project operation:");
            System.out.println("Total instances \tDelete Time");
            System.out.println(TOTAL_OBJECTS + "\t\t\t\t\t" + (end_time - start_time));
        }
    }

    public static void setupTodoObjects(int number) {
        RestAssured.baseURI = "http://localhost:4567";

        String title = "Test Task";
        String description = "Simple description";
        for (int i = 0; i < number; i++) {
            PerformanceTest.createTodo(title + (i + 1), false, description + (i + 1));
        }
        System.out.print("Finish setting up todo objects");

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

    public static void setupProjectObjects(int number) {
        RestAssured.baseURI = "http://localhost:4567";

        String title = "Test Project";
        String description = "Simple description";
        for (int i = 0; i < number; i++) {
            PerformanceTest.createProject(title + (i + 1), false, false, description + (i + 1));
        }
        System.out.print("Finish setting up project objects");

    }
    /*
        create project
    */
    private static void createProject(String title, Boolean completed, Boolean active, String description) {
        RequestSpecification request = RestAssured.given().baseUri("http://localhost:4567");

        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("completed", completed);
        obj.put("active", active);
        obj.put("description", description);

        request.body(obj.toJSONString()).post("/projects");
    }
}
