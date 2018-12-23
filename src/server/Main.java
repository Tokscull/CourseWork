package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.service.*;
import server.utilities.DatabaseConnection;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.Scanner;


public class Main extends Application {
    private int port;
    private String hostname;

    @Override
    public void start(Stage primaryStage) throws Exception {

        File file = new File("ConfigurationServer.txt");
        System.out.println("Change hostname and port?(y/n)");
        Scanner scanner = new Scanner(System.in);
        String checkYorN = scanner.nextLine();

        if(!file.exists() || file.length() == 0|| checkYorN.equals("y")){
                // Создание файла
                file.createNewFile();
                // Создание объекта FileWriter
                FileWriter writer = new FileWriter(file);

                System.out.println("Setting the server");
                System.out.println("==================");
                System.out.print("Input hostname Ip address : ");
                String hostname = scanner.nextLine();
                System.out.print("Input port : ");
                String port = scanner.nextLine();

                // Запись содержимого в файл
                writer.write(hostname + "\n" + port);
                writer.flush();
                writer.close();
        }

        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            String[] line = content.split("\n", 2);

            hostname = line[0];
            port = Integer.parseInt(line[1]);
        }
        catch (Exception e) {
            System.err.println("Uncorrected setting");
            e.printStackTrace();
            System.exit(0);
        }


    //    hostname = "localhost";
    //    port = 6789;

        DatabaseConnection.getConnection();

        System.setProperty("java.rmi.server.hostname",hostname);

        Registry registry = LocateRegistry.createRegistry(port);

        FurnitureServiceImpl furnitureServiceImpl = new FurnitureServiceImpl();
        ProviderServiceImpl providerServiceImpl = new ProviderServiceImpl();
        StaffServiceImpl staffServiceImpl = new StaffServiceImpl();
        ClientServiceImpl clientServiceImpl = new ClientServiceImpl();
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
        BackupServiceImpl backupServiceImpl = new BackupServiceImpl();


        registry.bind("furniture",furnitureServiceImpl);
        registry.bind("provider", providerServiceImpl);
        registry.bind("staff",staffServiceImpl);
        registry.bind("client",clientServiceImpl);
        registry.bind("order",orderServiceImpl);
        registry.bind("backup",backupServiceImpl);

        System.out.println("==================");
        System.out.println("Registry: " + registry);


        for (String boundName : registry.list()) {
            System.out.println("Name bound: " + boundName);
        }
        System.out.println("==================");


        System.out.println(new Date().toString());
        System.out.println("Server is running");
        System.out.println("==================");

    }


    public static void main(String[] args) {
        launch(args);
    }
}
