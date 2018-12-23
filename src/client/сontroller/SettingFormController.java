package client.сontroller;

import client.Main;
import client.utilities.FileWork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class SettingFormController {

    @FXML
    private TextField ipTextField;
    @FXML
    private Button saveButton;

    @FXML
    private TextField portTextField;


    @FXML
    void initialize() {
        ipTextField.setFocusTraversable(false);
        String[] ipAndPort = FileWork.getIpAndPortFromFile("ConfigurationClient.txt");
        if (!ipAndPort[0].equals("empty")) {
            ipTextField.setText(ipAndPort[0]);
            portTextField.setText(ipAndPort[1]);
        }

        saveButton.setOnAction(event -> {
            String serverIp = ipTextField.getText();
            String serverPort = portTextField.getText();
            try {
                File file = new File("ConfigurationClient.txt");
                // Создание файла
                file.createNewFile();
                FileWriter writer = new FileWriter(file,false);
                writer.write(serverIp + "\r\n" + serverPort);

                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveButton.getScene().getWindow().hide();

            Main main = new Main();
            Stage stage = new Stage();
            try {
                main.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
