package client.сontroller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import api.entity.Client;
import api.service.ClientService;
import javafx.beans.property.LongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

public class ClientFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button productsButton;
    @FXML
    private TableView<Client> clientsTableView;
    @FXML
    private TableColumn<Client, Long> idClient;
    @FXML
    private TableColumn<Client, String> surnameClient;
    @FXML
    private TableColumn<Client, String> nameClient;
    @FXML
    private TableColumn<Client, String> numberClient;
    @FXML
    private TableColumn<Client, String> countryClient;
    @FXML
    private TableColumn<Client, String> townClient;
    @FXML
    private TableColumn<Client, String> addressClient;
    @FXML
    private TableColumn<Client, String> noteClient;
    @FXML
    private TableColumn<Client, String> usernameClient;
    @FXML
    private TableColumn<Client, String> passwordClient;
    @FXML
    private TextField addressClientTextField;
    @FXML
    private TextField countryClientTextField;
    @FXML
    private TextField surnameClientTextField;
    @FXML
    private TextArea noteClientTextField;
    @FXML
    private TextField usernameClientTextField;
    @FXML
    private TextField passwordClientTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField townClientTextField;
    @FXML
    private TextField nameClientTextField;
    @FXML
    private TextField numberClientTextField;
    @FXML
    private Button ordersButton;
    @FXML
    private Button providerButton;
    @FXML
    private Button staffButton;
    @FXML
    private Button backupFormButton;


    private static ClientService clientService;
    private static FurnitureFormController main;

    private long selectedId;

    @FXML
    void initialize() {
        this.idClient.setCellValueFactory (new PropertyValueFactory("idClient"));
        this.surnameClient.setCellValueFactory (new PropertyValueFactory("surnameClient"));
        this.nameClient.setCellValueFactory (new PropertyValueFactory("nameClient"));
        this.numberClient.setCellValueFactory (new PropertyValueFactory("numberClient"));
        this.countryClient.setCellValueFactory (new PropertyValueFactory("countryClient"));
        this.townClient.setCellValueFactory (new PropertyValueFactory("townClient"));
        this.addressClient.setCellValueFactory (new PropertyValueFactory("addressClient"));
        this.noteClient.setCellValueFactory (new PropertyValueFactory("noteClient"));
        this.usernameClient.setCellValueFactory (new PropertyValueFactory("username"));
        this.passwordClient.setCellValueFactory (new PropertyValueFactory("password"));

        this.clientsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observable, Client oldValue, Client newValue) {
                if(newValue != null){
                    ClientFormController.this.surnameClientTextField.setText(newValue.getSurnameClient());
                    ClientFormController.this.nameClientTextField.setText(newValue.getNameClient());
                    ClientFormController.this.numberClientTextField.setText(newValue.getNumberClient());
                    ClientFormController.this.countryClientTextField.setText(newValue.getCountryClient());
                    ClientFormController.this.townClientTextField.setText(newValue.getTownClient());
                    ClientFormController.this.addressClientTextField.setText(newValue.getAddressClient());
                    ClientFormController.this.noteClientTextField.setText(newValue.getNoteClient());
                    ClientFormController.this.usernameClientTextField.setText(newValue.getUsername());
                    ClientFormController.this.passwordClientTextField.setText(newValue.getPassword());
                    selectedId = newValue.getIdClient();

                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            }
        });

        checkStringTextField(surnameClientTextField);
        checkStringTextField(nameClientTextField);
        checkStringTextField(countryClientTextField);
        checkStringTextField(townClientTextField);

        addButton.setOnAction(this::onInsert);
        deleteButton.setOnAction(this::onDelete);
        updateButton.setOnAction(this::onUpdate);
        refreshButton.setOnAction(this::onRefresh);

        productsButton.setOnAction(this::onProducts);
        providerButton.setOnAction(this::onProvider);
        staffButton.setOnAction(this::onStaff);
        ordersButton.setOnAction(this::onOrder);
        backupFormButton.setOnAction(this::onBackUp);

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void checkStringTextField(TextField surnameClientTextField) {
        surnameClientTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sа-яА-я*")) {
                surnameClientTextField.setText(newValue.replaceAll("[^\\sа-яА-я]", ""));
            }
        });
    }

    private void onInsert(ActionEvent event) {
        if (isFieldValid()) {
            try {

                Client client = new Client.Builder()
                        .buildSurnameClient(surnameClientTextField.getText())
                        .buildNameClient(nameClientTextField.getText())
                        .buildNumberClient(numberClientTextField.getText())
                        .buildCountryClient(countryClientTextField.getText())
                        .buildTownClient(townClientTextField.getText())
                        .buildAddressClient(addressClientTextField.getText())
                        .buildNoteClient(noteClientTextField.getText())
                        .buildUsername(usernameClientTextField.getText())
                        .buildPassword(passwordClientTextField.getText())
                        .build();

                clientService.insertClient(client);

                refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void onDelete(ActionEvent event) {
        try {
            Client client = this.clientsTableView.getSelectionModel().getSelectedItem();
            if(client == null){
                return;
            }
            clientService.deleteClient(client.getIdClient());
            refresh();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onUpdate(ActionEvent event) {
        if (isFieldValid()) {
            try {
                Client client = new Client.Builder()
                        .buildIdClient(selectedId)
                        .buildSurnameClient(surnameClientTextField.getText())
                        .buildNameClient(nameClientTextField.getText())
                        .buildNumberClient(numberClientTextField.getText())
                        .buildCountryClient(countryClientTextField.getText())
                        .buildTownClient(townClientTextField.getText())
                        .buildAddressClient(addressClientTextField.getText())
                        .buildNoteClient(noteClientTextField.getText())
                        .buildUsername(usernameClientTextField.getText())
                        .buildPassword(passwordClientTextField.getText())
                        .build();


                clientService.updateClient(client);
                refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void onRefresh(ActionEvent event) {
        refresh();
    }

    public void refresh() {
        try {
            this.clientsTableView.getItems().setAll(this.clientService.getAllClient());
            this.clearField();
            deleteButton.setDisable(true);
            updateButton.setDisable(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean isFieldValid() {
        String errorMessage = "";
        if(this.surnameClientTextField.getText() == null || this.surnameClientTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введена фамилия!\n";
        }
        if(this.nameClientTextField.getText() == null || this.nameClientTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введено имя!\n";
        }
        if(this.numberClientTextField.getText() == null || this.numberClientTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен номер!\n";
        }
        if(this.countryClientTextField.getText() == null || this.countryClientTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введена страна!\n";
        }
        if(this.townClientTextField.getText() == null || this.townClientTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен город!\n";
        }
        if(this.addressClientTextField.getText() == null || this.addressClientTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен адрес!\n";
        }
        if(this.usernameClientTextField.getText() == null || this.usernameClientTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен логин!\n";
        }
        if(this.passwordClientTextField.getText() == null || this.passwordClientTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен пароль!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            main.showErrorForm(errorMessage);
            return false;
        }
    }

    public void clearField() {
        surnameClientTextField.setText("");
        nameClientTextField.setText("");
        numberClientTextField.setText("");
        countryClientTextField.setText("");
        townClientTextField.setText("");
        addressClientTextField.setText("");
        noteClientTextField.setText("");
        usernameClientTextField.setText("");
        passwordClientTextField.setText("");
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

    private void onBackUp(ActionEvent event) {
        main.onBackUp(event);
    }


    public void setMain(FurnitureFormController main){
        this.main = main;
        this.clientService = main.getClientService();

       refresh();
    }
}
