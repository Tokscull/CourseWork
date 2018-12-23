package client.сontroller;

import api.service.*;
import client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SignUpFormController {

    @FXML
    private TextField loginTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button singUpButton;

    private static Main main;
    private static FurnitureService furnitureService;
    private static ProviderService providerService;
    private static StaffService staffService;
    private static ClientService clientService;
    private static OrderService orderService;
    private static BackupService backupService;

    private static Long idStaff;
    private static Long idClient;


    @FXML
    void initialize() {
        loginTextField.setFocusTraversable(false);

        cancelButton.setOnAction(this::onCancel);

        loginButton.setOnAction(this::onLogin);

        singUpButton.setOnAction(this::onSighUp);
    }

    private void onCancel(ActionEvent event) {
        System.exit(0);
    }

    private void onLogin(ActionEvent event) {
        try {
            idStaff = staffService.checkLoginAndPassword(loginTextField.getText(), passwordTextField.getText());
            idClient = clientService.checkLoginAndPassword(loginTextField.getText(), passwordTextField.getText());
            if (idStaff != null) {

                openFurnitureWindow(event);




            } else if (idClient != null) {

                openClientWindow(event);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неверный логин или пароль");
                alert.showAndWait();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onSighUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/RegistrationForm.fxml"));
            Parent root = loader.load();

            RegistrationFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Регистрация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFurnitureWindow(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/ProductsForm.fxml"));
            Parent root = loader.load();

            FurnitureFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openClientWindow(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/ClientWindowForm.fxml"));
            Parent root = loader.load();

            ClientWindowFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main){
        this.main = main;
        this.furnitureService = main.getFurnitureService();
        this.providerService = main.getProviderService();
        this.staffService = main.getStaffService();
        this.clientService = main.getClientService();
        this.orderService = main.getOrderService();
        this.backupService = main.getBackupService();
    }


    public static Long getIdStaff() {
        return idStaff;
    }

    public static Long getIdClient() {
        return idClient;
    }

    public static FurnitureService getFurnitureService() {
        return furnitureService;
    }
    public static ProviderService getProviderService() { return providerService; }
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
}

