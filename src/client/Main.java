package client;

import api.service.*;
import client.utilities.FileWork;
import client.сontroller.SignUpFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public  class Main extends Application {
    private static FurnitureService furnitureService;
    private static ProviderService providerService;
    private static ClientService clientService;
    private static StaffService staffService;
    private static OrderService orderService;
    private static BackupService backupService;

    private String ip;
    private int port;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String[] ipAndPort = FileWork.getIpAndPortFromFile("ConfigurationClient.txt");
        if (!ipAndPort[0].equals("empty")) {
            ip = ipAndPort[0];
            try {
                port = Integer.parseInt(ipAndPort[1].trim());
            }catch (Exception e){}
        }

        boolean flag = true;
        try {
            Registry registry = LocateRegistry.getRegistry(ip, port);
            this.furnitureService = (FurnitureService)registry.lookup("furniture");
            this.providerService = (ProviderService)registry.lookup("provider");
            this.staffService = (StaffService)registry.lookup("staff");
            this.clientService = (ClientService)registry.lookup("client");
            this.orderService = (OrderService)registry.lookup("order");
            this.backupService = (BackupService)registry.lookup("backup");



        } catch (Exception e) {
            flag = false;
            System.out.println("Incorrect ip or port");

            Parent root = FXMLLoader.load(getClass().getResource("/client/form/SettingForm.fxml"));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Авторизация");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Некоректно введен\nip или порт сервера");
            alert.showAndWait();
        }

        if(flag == true) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("form/SignUpForm.fxml"));
            Parent root = loader.load();

            SignUpFormController controller = loader.getController();
            controller.setMain(this);


            primaryStage.setResizable(false);
            primaryStage.setTitle("Авторизация");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        }
    }

    public static FurnitureService getFurnitureService() { return furnitureService; }
    public static ProviderService getProviderService(){return  providerService;}
    public static StaffService getStaffService() { return staffService; }
    public static ClientService getClientService() {
        return clientService;
    }
    public static OrderService getOrderService() {
        return orderService;
    }
    public static BackupService getBackupService() {
        return backupService;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
