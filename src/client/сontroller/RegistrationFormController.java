package client.сontroller;

import api.entity.Client;
import api.entity.Staff;
import api.service.ClientService;
import api.service.StaffService;
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
import java.util.List;


public class RegistrationFormController {

    @FXML
    private TextField loginTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private PasswordField repeatPasswordTextField;


    private SignUpFormController main;
    private StaffService staffService;
    private ClientService clientService;

    @FXML
    void initialize() {
        nameTextField.setFocusTraversable(false);

        loginButton.setOnAction(this::registrationNewUser);

        cancelButton.setOnAction(this::onCancel);

        checkStringTextField(surnameTextField);

        checkStringTextField(nameTextField);
    }

    private void checkStringTextField(TextField surnameTextField) {
        surnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sа-яА-я*")) {
                surnameTextField.setText(newValue.replaceAll("[^\\sа-яА-я]", ""));
            }
        });
    }

    private void onCancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/client/form/SignUpForm.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Авторизация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registrationNewUser(ActionEvent event) {
        if (this.isFieldValid()) {
            try {
                List<Staff> list = staffService.getAllStaff();
                if(list.isEmpty()){
                    Staff staff = new Staff();
                    staff.setUsername(loginTextField.getText());
                    staff.setPassword(passwordTextField.getText());
                    staff.setNameStaff(nameTextField.getText());
                    staff.setSurnameStaff(surnameTextField.getText());

                    staffService.registrationStaff(staff);
                }
                else {
                    Client client = new Client();
                    client.setUsername(loginTextField.getText());
                    client.setPassword(passwordTextField.getText());
                    client.setNameClient(nameTextField.getText());
                    client.setSurnameClient(surnameTextField.getText());

                    clientService.registrationClient(client);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Регистрация");
                alert.setHeaderText(null);
                alert.setContentText("Пользователь успешно зарегистрирован");
                alert.showAndWait();
                onCancel(event);
            } catch (RemoteException var3) {
                var3.printStackTrace();
            }
        }
    }


    public void setMain(SignUpFormController main){
        this.main = main;
        this.staffService = main.getStaffService();
        this.clientService = main.getClientService();
    }

    private boolean isFieldValid() {
        String errorMessage = "";
        if(this.nameTextField.getText() == null || this.nameTextField.getText().isEmpty()) {
            errorMessage += "Не введено имя!\n";
        }
        if(this.surnameTextField.getText() == null || this.surnameTextField.getText().isEmpty()) {
            errorMessage += "Не введена фамилия!\n";
        }
        if(this.loginTextField.getText() == null || this.loginTextField.getText().isEmpty()) {
            errorMessage += "Не введен логин!\n";
        }
        try {
            List<Client> allUserName = clientService.getAllClient();
            if (!allUserName.isEmpty())
                for (Client userName : allUserName)
                    if (this.loginTextField.getText().equals(userName.getUsername())) {
                        errorMessage += "Пользователь с таким логином уже зарегистрирован\n";
                    }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(this.passwordTextField.getText() == null || this.passwordTextField.getText().isEmpty()) {
            errorMessage += "Не введен пароль!\n";
        }
        if(!passwordTextField.getText().equals(repeatPasswordTextField.getText())) {
            errorMessage += "Пароли не соовпадают!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пожалуйста исправьте неверные поля");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
