package ApiTests;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.time.LocalTime;
import java.util.Calendar;

import com.sun.management.OperatingSystemMXBean;
import java.lang.Runtime;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.util.Random;

import static org.hamcrest.core.IsEqual.equalTo;

public class PerformanceTest {
    //Authors: Joey Liu, Ke Yan, Abiola Olaniyan, Mihail Cali

    public static int TOTAL_OBJECTS_CREATED = 0;

    public static void main(String[] args) throws IOException {

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        //--------------------------------------------------------Todo Setup--------------------------------------------------------//

        FileWriter createTimeTodoCSV = new FileWriter("csv/1-create-todo-object-time.csv");
        createTimeTodoCSV.append("Total Objects,Sample Time,Time (ms)\n");

        FileWriter modifyTimeTodoCSV = new FileWriter("csv/1-modify-todo-object-time.csv");
        modifyTimeTodoCSV.append("Total Objects,Sample Time,Time (ms)\n");

        FileWriter deleteTimeTodoCSV = new FileWriter("csv/1-delete-todo-object-time.csv");
        deleteTimeTodoCSV.append("Total Objects,Sample Time,Time (ms)\n");

        FileWriter createCPUTodoCSV = new FileWriter("csv/1-create-todo-object-cpu.csv");
        createCPUTodoCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter modifyCPUTodoCSV = new FileWriter("csv/1-modify-todo-object-cpu.csv");
        modifyCPUTodoCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter deleteCPUTodoCSV = new FileWriter("csv/1-delete-todo-object-cpu.csv");
        deleteCPUTodoCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter createMemoryTodoCSV = new FileWriter("csv/1-create-todo-object-memory.csv");
        createMemoryTodoCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        FileWriter modifyMemoryTodoCSV = new FileWriter("csv/1-modify-todo-object-memory.csv");
        modifyMemoryTodoCSV.append("Total Objects, Sample Time,Memory Usage (MB),\n");

        FileWriter deleteMemoryTodoCSV = new FileWriter("csv/1-delete-todo-object-memory.csv");
        deleteMemoryTodoCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        TodoTest todoTest = new TodoTest();

        //--------------------------------------------------------Category Setup--------------------------------------------------------//

        FileWriter createTimeCategoryCSV = new FileWriter("csv/2-create-category-object-time.csv");
        createTimeCategoryCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter modifyTimeCategoryCSV = new FileWriter("csv/2-modify-category-object-time.csv");
        modifyTimeCategoryCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter deleteTimeCategoryCSV = new FileWriter("csv/2-delete-category-object-time.csv");
        deleteTimeCategoryCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter createCPUCategoryCSV = new FileWriter("csv/2-create-category-object-cpu.csv");
        createCPUCategoryCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter modifyCPUCategoryCSV = new FileWriter("csv/2-modify-category-object-cpu.csv");
        modifyCPUCategoryCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter deleteCPUCategoryCSV = new FileWriter("csv/2-delete-category-object-cpu.csv");
        deleteCPUCategoryCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter createMemoryCategoryCSV = new FileWriter("csv/2-create-category-object-memory.csv");
        createMemoryCategoryCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        FileWriter modifyMemoryCategoryCSV = new FileWriter("csv/2-modify-category-object-memory.csv");
        modifyMemoryCategoryCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        FileWriter deleteMemoryCategoryCSV = new FileWriter("csv/2-delete-category-object-memory.csv");
        deleteMemoryCategoryCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        CategoryTest categoryTest = new CategoryTest();

        //--------------------------------------------------------Project Setup--------------------------------------------------------//
        FileWriter createTimeProjectCSV = new FileWriter("csv/3-create-project-object-time.csv");
        createTimeProjectCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter modifyTimeProjectCSV = new FileWriter("csv/3-modify-project-object-time.csv");
        modifyTimeProjectCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter deleteTimeProjectCSV = new FileWriter("csv/3-delete-project-object-time.csv");
        deleteTimeProjectCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter createCPUProjectCSV = new FileWriter("csv/3-create-project-object-cpu.csv");
        createCPUProjectCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter modifyCPUProjectCSV = new FileWriter("csv/3-modify-project-object-cpu.csv");
        modifyCPUProjectCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter deleteCPUProjectCSV = new FileWriter("csv/3-delete-project-object-cpu.csv");
        deleteCPUProjectCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter createMemoryProjectCSV = new FileWriter("csv/3-create-project-object-memory.csv");
        createMemoryProjectCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        FileWriter modifyMemoryProjectCSV = new FileWriter("csv/3-modify-project-object-memory.csv");
        modifyMemoryProjectCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        FileWriter deleteMemoryProjectCSV = new FileWriter("csv/3-delete-project-object-memory.csv");
        deleteMemoryProjectCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        ProjectTest projectTest = new ProjectTest();

        //--------------------------------------------------------Interoperability of Projects and Categories Setup--------------------------------------------------------//
        FileWriter createTimeInterProjCatCSV = new FileWriter("csv/4-create-inter-project-category-object-time.csv");
        createTimeInterProjCatCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter deleteTimeInterProjCatCSV = new FileWriter("csv/4-delete-inter-project-category-object-time.csv");
        deleteTimeInterProjCatCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter createCPUInterProjCatCSV = new FileWriter("csv/4-create-inter-project-category-object-cpu.csv");
        createCPUInterProjCatCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter deleteCPUInterProjCatCSV = new FileWriter("csv/4-delete-inter-project-category-object-cpu.csv");
        deleteCPUInterProjCatCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter createMemoryInterProjCatCSV = new FileWriter("csv/4-create-inter-project-category-object-memory.csv");
        createMemoryInterProjCatCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        FileWriter deleteMemoryInterProjCatCSV = new FileWriter("csv/4-delete-inter-project-category-object-memory.csv");
        deleteMemoryInterProjCatCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        //--------------------------------------------------------Interoperability of Categories and Todos Setup--------------------------------------------------------//
        FileWriter createTimeInterCatTodoCSV = new FileWriter("csv/5-create-inter-category-todo-object-time.csv");
        createTimeInterCatTodoCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter deleteTimeInterCatTodoCSV = new FileWriter("csv/5-delete-inter-category-todo-object-time.csv");
        deleteTimeInterCatTodoCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter createCPUInterCatTodoCSV = new FileWriter("csv/5-create-inter-category-todo-object-cpu.csv");
        createCPUInterCatTodoCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter deleteCPUInterCatTodoCSV = new FileWriter("csv/5-delete-inter-category-todo-object-cpu.csv");
        deleteCPUInterCatTodoCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter createMemoryInterCatTodoCSV = new FileWriter("csv/5-create-inter-category-todo-object-memory.csv");
        createMemoryInterCatTodoCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        FileWriter deleteMemoryInterCatTodoCSV = new FileWriter("csv/5-delete-inter-category-todo-object-memory.csv");
        deleteMemoryInterCatTodoCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        //--------------------------------------------------------Interoperability of Todos and Projects Setup--------------------------------------------------------//
        FileWriter createTimeInterTodoProjCSV = new FileWriter("csv/6-create-inter-todo-project-object-time.csv");
        createTimeInterTodoProjCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter deleteTimeInterTodoProjCSV = new FileWriter("csv/6-delete-inter-todo-project-object-time.csv");
        deleteTimeInterTodoProjCSV.append("Total Objects, Sample Time,Time (ms)\n");

        FileWriter createCPUInterTodoProjCSV = new FileWriter("csv/6-create-inter-todo-project-object-cpu.csv");
        createCPUInterTodoProjCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter deleteCPUInterTodoProjCSV = new FileWriter("csv/6-delete-inter-todo-project-object-cpu.csv");
        deleteCPUInterTodoProjCSV.append("Total Objects, Sample Time,CPU Usage\n");

        FileWriter createMemoryInterTodoProjCSV = new FileWriter("csv/6-create-inter-todo-project-object-memory.csv");
        createMemoryInterTodoProjCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        FileWriter deleteMemoryInterTodoProjCSV = new FileWriter("csv/6-delete-inter-todo-project-object-memory.csv");
        deleteMemoryInterTodoProjCSV.append("Total Objects, Sample Time,Memory Usage (MB)\n");

        InteroperabilityTest interoperabilityTest = new InteroperabilityTest();

        // Todos
        for (int i = 0; i < 1000; i++) {
            long sampleTime = System.currentTimeMillis();
            long startTime;
            long endTime;
            
            Random random = new Random();
            PerformanceTest.createTodo("Test Todo object number#" + random.nextInt(), false, "This is description number#" + random.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create todo
            startTime = Calendar.getInstance().getTimeInMillis();
            todoTest.testCreateTodo();
            endTime = Calendar.getInstance().getTimeInMillis();

            createTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            createTimeTodoCSV.flush();

            double cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            createCPUTodoCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryInMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + usedMemoryInMB + "\n");
            createMemoryTodoCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Todo operation:");
            System.out.println("Total instances \tSample Time \t\t\tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));


            // Modify todo
            startTime = Calendar.getInstance().getTimeInMillis();
            todoTest.testModifyTodo();
            endTime = Calendar.getInstance().getTimeInMillis();

            modifyTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            modifyTimeTodoCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            modifyCPUTodoCSV.flush();

            long modifyMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double modifyMemoryInMB = modifyMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + modifyMemoryInMB + "\n");
            modifyMemoryTodoCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Todo operation:");
            System.out.println("Total instances \tSample Time \t\t\tModify Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));

            // Delete todo
            startTime = Calendar.getInstance().getTimeInMillis();
            todoTest.testDeleteTodo();
            endTime = Calendar.getInstance().getTimeInMillis();

            deleteTimeTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            deleteTimeTodoCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            deleteCPUTodoCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryInMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + deleteMemoryInMB + "\n");
            deleteMemoryTodoCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Todo operation:");
            System.out.println("Total instances \tSample Time \t\t\tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));
        }

        // Categories
        for (int i = 0; i < 1000; i++) {
            long sampleTime = System.currentTimeMillis();
            long startTime;
            long endTime;

            Random random = new Random();
            PerformanceTest.createCategory("Test category object number#" + random.nextInt(), "This is description number#" + random.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create category
            startTime = Calendar.getInstance().getTimeInMillis();
            categoryTest.testCreateCategory();
            endTime = Calendar.getInstance().getTimeInMillis();

            createTimeCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," +  sampleTime + "," + (endTime-startTime) + "\n");
            createTimeCategoryCSV.flush();

            double cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            createCPUCategoryCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryInMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + usedMemoryInMB + "\n");
            createMemoryCategoryCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Category operation:");
            System.out.println("Total instances \tSample Time \t\t\tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));

            // Modify category
            startTime = Calendar.getInstance().getTimeInMillis();
            categoryTest.testModifyCategory();
            endTime = Calendar.getInstance().getTimeInMillis();

            modifyTimeCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            modifyTimeCategoryCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            modifyCPUCategoryCSV.flush();

            long modifyMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double modifyMemoryInMB = modifyMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + modifyMemoryInMB + "\n");
            modifyMemoryCategoryCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Category operation:");
            System.out.println("Total instances \tSample Time \t\t\tModify Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));

            // Delete category
            startTime = Calendar.getInstance().getTimeInMillis();
            categoryTest.testDeleteCategory();
            endTime = Calendar.getInstance().getTimeInMillis();

            deleteTimeCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            deleteTimeCategoryCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            deleteCPUCategoryCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryInMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryCategoryCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + deleteMemoryInMB + "\n");
            deleteMemoryCategoryCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Category operation:");
            System.out.println("Total instances \tSample Time \t\t\tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));
        }

        // Projects
        for (int i = 0; i < 1000; i++) {
            long sampleTime = System.currentTimeMillis();
            long startTime;
            long endTime;

            Random random = new Random();
            PerformanceTest.createProject("Test project object number#" + random.nextInt(), false, false, "This is description number#" + random.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create project
            startTime = Calendar.getInstance().getTimeInMillis();
            projectTest.testCreateProject();
            endTime = Calendar.getInstance().getTimeInMillis();

            createTimeProjectCSV.append(TOTAL_OBJECTS_CREATED + "," +  sampleTime + "," + (endTime-startTime) + "\n");
            createTimeProjectCSV.flush();

            double cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUProjectCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            createCPUProjectCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryInMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryProjectCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + usedMemoryInMB + "\n");
            createMemoryProjectCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Project operation:");
            System.out.println("Total instances \tSample Time \t\t\tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));

            // Modify project
            startTime = Calendar.getInstance().getTimeInMillis();
            projectTest.testUpdateProject();
            endTime = Calendar.getInstance().getTimeInMillis();

            modifyTimeProjectCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            modifyTimeProjectCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            modifyCPUProjectCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            modifyCPUProjectCSV.flush();

            long modifyMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double modifyMemoryInMB = modifyMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            modifyMemoryProjectCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + modifyMemoryInMB + "\n");
            modifyMemoryProjectCSV.flush();

            // Print results for modify operation
            System.out.println("Results for Modifying Project operation:");
            System.out.println("Total instances \tSample Time \t\t\tModify Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));

            // Delete project
            startTime = Calendar.getInstance().getTimeInMillis();
            projectTest.testDeleteProject();
            endTime = Calendar.getInstance().getTimeInMillis();

            deleteTimeProjectCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            deleteTimeProjectCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUProjectCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            deleteCPUProjectCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryInMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryProjectCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + deleteMemoryInMB + "\n");
            deleteMemoryProjectCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Project operation:");
            System.out.println("Total instances \tSample Time \t\t\tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));
        }

        // Interop. of Projects and Categories
        for (int i = 0; i < 1000; i++) {
            long sampleTime = System.currentTimeMillis();
            long startTime;
            long endTime;

            Random random = new Random();
            PerformanceTest.createInterProjectAndCategory("Test category of project object number#" + random.nextInt(), "This is description number#" + random.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create category of project
            startTime = Calendar.getInstance().getTimeInMillis();
            interoperabilityTest.testCreateInterProjectAndCategory();
            endTime = Calendar.getInstance().getTimeInMillis();

            createTimeInterProjCatCSV.append(TOTAL_OBJECTS_CREATED + "," +  sampleTime + "," + (endTime-startTime) + "\n");
            createTimeInterProjCatCSV.flush();

            double cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUInterProjCatCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            createCPUInterProjCatCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryInMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryInterProjCatCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + usedMemoryInMB + "\n");
            createMemoryInterProjCatCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Category of Project operation:");
            System.out.println("Total instances \tSample Time \t\t\tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));

            // Delete category of project
            startTime = Calendar.getInstance().getTimeInMillis();
            interoperabilityTest.testDeleteInterProjectAndCategory();
            endTime = Calendar.getInstance().getTimeInMillis();

            deleteTimeInterProjCatCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            deleteTimeInterProjCatCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUInterProjCatCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            deleteCPUInterProjCatCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryInMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryInterProjCatCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + deleteMemoryInMB + "\n");
            deleteMemoryInterProjCatCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Category of Project operation:");
            System.out.println("Total instances \tSample Time \t\t\tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));
        }

        // Interop. of Categories and Todos
        for (int i = 0; i < 1000; i++) {
            long sampleTime = System.currentTimeMillis();
            long startTime;
            long endTime;

            Random random = new Random();
            PerformanceTest.createInterCategoryAndTodo("Test todo of category object number#" + random.nextInt(), false, "This is description number#" + random.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create todo of category
            startTime = Calendar.getInstance().getTimeInMillis();
            interoperabilityTest.testCreateInterCategoryAndTodo();
            endTime = Calendar.getInstance().getTimeInMillis();

            createTimeInterCatTodoCSV.append(TOTAL_OBJECTS_CREATED + "," +  sampleTime + "," + (endTime-startTime) + "\n");
            createTimeInterCatTodoCSV.flush();

            double cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUInterCatTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            createCPUInterCatTodoCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryInMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryInterCatTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + usedMemoryInMB + "\n");
            createMemoryInterCatTodoCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Todo of Category operation:");
            System.out.println("Total instances \tSample Time \t\t\tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));

            // Delete todo of category
            startTime = Calendar.getInstance().getTimeInMillis();
            interoperabilityTest.testDeleteInterCategoryAndTodo();
            endTime = Calendar.getInstance().getTimeInMillis();

            deleteTimeInterCatTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            deleteTimeInterCatTodoCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUInterCatTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            deleteCPUInterCatTodoCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryInMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryInterCatTodoCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + deleteMemoryInMB + "\n");
            deleteMemoryInterCatTodoCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Todo of Category operation:");
            System.out.println("Total instances \tSample Time \t\t\tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));
        }

        // Interop. of Todos and Projects
        for (int i = 0; i < 1000; i++) {
            long sampleTime = System.currentTimeMillis();
            long startTime;
            long endTime;

            Random random = new Random();
            PerformanceTest.createInterTodoAndProject("Test project of todo object number#" + random.nextInt(), false, false, "This is description number#" + random.nextInt());
            TOTAL_OBJECTS_CREATED++;

            // Create project of todo
            startTime = Calendar.getInstance().getTimeInMillis();
            interoperabilityTest.testCreateInterTodoAndProject();
            endTime = Calendar.getInstance().getTimeInMillis();

            createTimeInterTodoProjCSV.append(TOTAL_OBJECTS_CREATED + "," +  sampleTime + "," + (endTime-startTime) + "\n");
            createTimeInterTodoProjCSV.flush();

            double cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            createCPUInterTodoProjCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            createCPUInterTodoProjCSV.flush();

            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double usedMemoryInMB = usedMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            createMemoryInterTodoProjCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + usedMemoryInMB + "\n");
            createMemoryInterTodoProjCSV.flush();

            // Print results for create operation
            System.out.println("Results for Creating Project of Todo operation:");
            System.out.println("Total instances \tSample Time \t\t\tCreate Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));

            // Delete project of todo
            startTime = Calendar.getInstance().getTimeInMillis();
            interoperabilityTest.testDeleteInterTodoAndProject();
            endTime = Calendar.getInstance().getTimeInMillis();

            deleteTimeInterTodoProjCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + (endTime-startTime) + "\n");
            deleteTimeInterTodoProjCSV.flush();

            cpu = operatingSystemMXBean.getProcessCpuLoad() * 100;
            deleteCPUInterTodoProjCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + cpu + "\n");
            deleteCPUInterTodoProjCSV.flush();

            long deleteMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            // Convert to MB
            double deleteMemoryInMB = deleteMemory / (1024.0 * 1024.0);

            // Log memory usage to deleteCSV
            deleteMemoryInterTodoProjCSV.append(TOTAL_OBJECTS_CREATED + "," + sampleTime + "," + deleteMemoryInMB + "\n");
            deleteMemoryInterTodoProjCSV.flush();

            // Print results for delete operation
            System.out.println("Results for Deleting Project of Todo operation:");
            System.out.println("Total instances \tSample Time \t\t\tDelete Time");
            System.out.println(TOTAL_OBJECTS_CREATED + "\t\t\t\t\t" + sampleTime + "\t\t\t\t\t" + (endTime - startTime));
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

    /*
        create project method
    */
    private static void createProject(String title, Boolean completed, Boolean active, String description) {
        RequestSpecification request = RestAssured.given().baseUri("http://localhost:4567");

        JSONObject requestParams = new JSONObject();
        requestParams.put("title", title);
        requestParams.put("completed", completed);
        requestParams.put("active", active);
        requestParams.put("description", description);

        request.body(requestParams.toJSONString());
        request.post("/projects");
    }

    /*
        create relationship between project and category
     */
    private static void createInterProjectAndCategory(String categoryTitle, String categoryDescription) {
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

        String responseBody = projResponse.getBody().asString();
        org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
        int projectID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        JSONObject categoryObj = new JSONObject();
        categoryObj.put("title", categoryTitle);
        categoryObj.put("description", categoryDescription);

        // Send the request
        RestAssured.given().body(categoryObj.toJSONString())
                .post("/projects/" + projectID + "/categories");
    }

    /*
        create relationship between category and todo
     */
    private static void createInterCategoryAndTodo(String todoTitle, Boolean doneStatus, String todoDescription) {
        // Create a JSON object for the request body
        String categoryTitle = "categoryTitle";
        String categoryDescription = "categoryDescription";

        JSONObject categoryObj = new JSONObject();
        categoryObj.put("title", categoryTitle);
        categoryObj.put("description", categoryDescription);

        // Send the request
        Response categoryResponse = RestAssured.given().body(categoryObj.toJSONString())
                .post("/categories");

        String responseBody = categoryResponse.getBody().asString();
        org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
        int categoryID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Create the relationship between the created category and a new todo
        JSONObject todoObj = new JSONObject();
        todoObj.put("title", todoTitle);
        todoObj.put("doneStatus", doneStatus);
        todoObj.put("description", todoDescription);

        // Send the request
        RestAssured.given().body(todoObj.toJSONString())
                .post("/categories/" + categoryID + "/todos");
    }

    /*
        create relationship between todos and project
    */
    private static void createInterTodoAndProject(String projectTitle, Boolean completed, Boolean active, String projectDescription) {
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
        int todoID = jsonResponse.getInt("id"); // Keep track of id for the other tests

        // Create the relationship between the created todo and a new project
        JSONObject projectObj = new JSONObject();
        projectObj.put("title", projectTitle);
        projectObj.put("completed", completed);
        projectObj.put("active", active);
        projectObj.put("description", projectDescription);

        // Send the request
        RestAssured.given().body(projectObj.toJSONString())
                .post("/todos/" + todoID + "/tasksof");
    }
}
