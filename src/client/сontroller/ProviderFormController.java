package client.сontroller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import api.entity.Provider;
import api.service.ProviderService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProviderFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button productsButton;
    @FXML
    private TableView<Provider> productsTableView;
    @FXML
    private TableColumn<Provider, Long> idProvider;
    @FXML
    private TableColumn<Provider, String> nameProvider;
    @FXML
    private TableColumn<Provider, String> countryProvider;
    @FXML
    private TableColumn<Provider, String> townProvider;
    @FXML
    private TableColumn<Provider, String> adresProvider;
    @FXML
    private TableColumn<Provider, String> contactPersonProvider;
    @FXML
    private TableColumn<Provider, String> numberProvider;
    @FXML
    private TableColumn<Provider, String> noteProvider;
    @FXML
    private TextField numberProviderTextField;
    @FXML
    private TextField adresProviderTextField;
    @FXML
    private TextField nameProviderTextField;
    @FXML
    private TextArea noteProviderTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField contactPersonProviderTextField1;
    @FXML
    private TextField countryProviderTextField1;
    @FXML
    private TextField townProviderTextField11;
    @FXML
    private Button ordersButton;
    @FXML
    private Button staffButton;
    @FXML
    private Button clientButton;
    @FXML
    private Button backupFormButton;

    private static ProviderService providerService;
    private static FurnitureFormController main;

    private long selectedId;

    @FXML
    void initialize() {
        this.idProvider.setCellValueFactory (new PropertyValueFactory("idProviders"));
        this.nameProvider.setCellValueFactory (new PropertyValueFactory("nameProvider"));
        this.countryProvider.setCellValueFactory (new PropertyValueFactory("country"));
        this.townProvider.setCellValueFactory (new PropertyValueFactory("town"));
        this.adresProvider.setCellValueFactory (new PropertyValueFactory("address"));
        this.contactPersonProvider.setCellValueFactory (new PropertyValueFactory("contactPerson"));
        this.numberProvider.setCellValueFactory (new PropertyValueFactory("contactNumber"));
        this.noteProvider.setCellValueFactory (new PropertyValueFactory("note"));

        this.productsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Provider>() {
            @Override
            public void changed(ObservableValue<? extends Provider> observable, Provider oldValue, Provider newValue) {
                if(newValue != null){
                    ProviderFormController.this.nameProviderTextField.setText(newValue.getNameProvider());
                    ProviderFormController.this.countryProviderTextField1.setText(newValue.getCountry());
                    ProviderFormController.this.townProviderTextField11.setText(newValue.getTown());
                    ProviderFormController.this.adresProviderTextField.setText(newValue.getAddress());
                    ProviderFormController.this.contactPersonProviderTextField1.setText(newValue.getContactPerson());
                    ProviderFormController.this.numberProviderTextField.setText(newValue.getContactNumber());
                    ProviderFormController.this.noteProviderTextField.setText(newValue.getNote());
                    selectedId = newValue.getIdProviders();

                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            }
        });

        checkStringTextField(countryProviderTextField1);

        checkStringTextField(townProviderTextField11);

        checkStringTextField(contactPersonProviderTextField1);

        addButton.setOnAction(this::onInsert);
        deleteButton.setOnAction(this::onDelete);
        updateButton.setOnAction(this::onUpdate);
        refreshButton.setOnAction(this::onRefresh);

        productsButton.setOnAction(this::onProducts);
        staffButton.setOnAction(this::onStaff);
        clientButton.setOnAction(this::onClient);
        ordersButton.setOnAction(this::onOrder);
        backupFormButton.setOnAction(this::onBackUp);

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void checkStringTextField(TextField countryProviderTextField1) {
        countryProviderTextField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sа-яА-я*")) {
                countryProviderTextField1.setText(newValue.replaceAll("[^\\sа-яА-я]", ""));
            }
        });
    }

    private void onInsert(ActionEvent event) {
        if(isFieldValid()) {
            try {
                Provider provider = new Provider();
                provider.setNameProvider(nameProviderTextField.getText());
                provider.setCountry(countryProviderTextField1.getText());
                provider.setTown(townProviderTextField11.getText());
                provider.setAddress(adresProviderTextField.getText());
                provider.setContactPerson(contactPersonProviderTextField1.getText());
                provider.setContactNumber(numberProviderTextField.getText());
                provider.setNote(noteProviderTextField.getText());

                providerService.insertProvider(provider);

                refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void onDelete(ActionEvent event) {
        try {
            Provider provider = this.productsTableView.getSelectionModel().getSelectedItem();
            if(provider == null){
                return;
            }
            providerService.deleteProvider(provider.getIdProviders());
            refresh();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onUpdate(ActionEvent event) {
        if (isFieldValid()) {
            try {
                Provider provider = new Provider();
                provider.setIdProviders(selectedId);
                provider.setNameProvider(nameProviderTextField.getText());
                provider.setCountry(countryProviderTextField1.getText());
                provider.setTown(townProviderTextField11.getText());
                provider.setAddress(adresProviderTextField.getText());
                provider.setContactPerson(contactPersonProviderTextField1.getText());
                provider.setContactNumber(numberProviderTextField.getText());
                provider.setNote(noteProviderTextField.getText());

                providerService.updateProvider(provider);
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
            this.productsTableView.getItems().setAll(this.providerService.getAllProvider());
            this.clearField();
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean isFieldValid() {
        String errorMessage = "";
        if(this.nameProviderTextField.getText() == null || this.nameProviderTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введено имя!\n";
        }
        if(this.countryProviderTextField1.getText() == null || this.countryProviderTextField1.getText().isEmpty()) {
            errorMessage += "Неправильно введена страна!\n";
        }
        if(this.townProviderTextField11.getText() == null || this.townProviderTextField11.getText().isEmpty()) {
            errorMessage += "Неправильно введен город!\n";
        }
        if(this.adresProviderTextField.getText() == null || this.adresProviderTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен адрес!\n";
        }
        if(this.contactPersonProviderTextField1.getText() == null || this.contactPersonProviderTextField1.getText().isEmpty()) {
            errorMessage += "Неправильно введено контактное лицо!\n";
        }
        if(this.numberProviderTextField.getText() == null || this.numberProviderTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен номер!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            main.showErrorForm(errorMessage);
            return false;
        }
    }

    private void clearField() {
        nameProviderTextField.setText("");
        countryProviderTextField1.setText("");
        townProviderTextField11.setText("");
        adresProviderTextField.setText("");
        contactPersonProviderTextField1.setText("");
        numberProviderTextField.setText("");
        noteProviderTextField.setText("");
    }


    private void onProducts(ActionEvent event) {
        main.onProduct(event);
    }

    private void onStaff(ActionEvent event) {
        main.onStaff(event);
    }

    private void onClient(ActionEvent event) {
        main.onClient(event);
    }

    private void onOrder(ActionEvent event) {
        main.onOrder(event);
    }

    private void onBackUp(ActionEvent event) {
        main.onBackUp(event);
    }


    public void setMain(FurnitureFormController main){
        this.main = main;
        this.providerService = main.getProviderService();

        refresh();
    }
}
