package client.сontroller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import api.service.BackupService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BackupFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button productsButton;
    @FXML
    private Button backupButton;
    @FXML
    private Button restoreButton;
    @FXML
    private Label backupLabel;
    @FXML
    private Label resetLabel;
    @FXML
    private Button ordersButton;
    @FXML
    private Button providerButton;
    @FXML
    private Button staffButton;
    @FXML
    private Button clientButton;

    private static FurnitureFormController main;
    private BackupService backupService;

    @FXML
    void initialize() {

        productsButton.setOnAction(this::onProducts);
        providerButton.setOnAction(this::onProvider);
        staffButton.setOnAction(this::onStaff);
        ordersButton.setOnAction(this::onOrder);
        clientButton.setOnAction(this::onClient);

        backupButton.setOnAction(this::onBackup);
        restoreButton.setOnAction(this::onRestore);

    }

    private void onProducts(ActionEvent event) {
        main.onProduct(event);
    }

    private void onProvider(ActionEvent event) {
        main.onProvider(event);
    }

    private void onStaff(ActionEvent event) {
        main.onStaff(event);
    }

    private void onOrder(ActionEvent event) {
        main.onOrder(event);
    }

    private void onClient(ActionEvent event) {
        main.onClient(event);
    }


    private void onRestore(ActionEvent event) {
        try {
            String massage = backupService.restoreDb();
            resetLabel.setText(massage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onBackup(ActionEvent event) {
        try {
            String massage = backupService.backupDb();
            backupLabel.setText(massage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        backupLabel.setText("");
        resetLabel.setText("");
    }


    public void setMain(FurnitureFormController main){
        this.main = main;
        this.backupService = main.getBackupService();

        refresh();
    }
}
