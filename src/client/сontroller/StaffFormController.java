package client.сontroller;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import api.entity.Staff;
import api.service.StaffService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class StaffFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button productsButton;
    @FXML
    private TableView<Staff> staffsTableView;
    @FXML
    private TableColumn<Staff, Long> idStaff;
    @FXML
    private TableColumn<Staff, String> surnameStaff;
    @FXML
    private TableColumn<Staff, String> nameStaff;
    @FXML
    private TableColumn<Staff, String> positionStaff;
    @FXML
    private TableColumn<Staff, LocalDate> birthDayStaff;
    @FXML
    private TableColumn<Staff, LocalDate> employmentDayStaff;
    @FXML
    private TableColumn<Staff, String> numberStaff;
    @FXML
    private TableColumn<Staff, String> noteStaff;
    @FXML
    private TableColumn<Staff, String> usernameStaff;
    @FXML
    private TableColumn<Staff, String> passwordStaff;
    @FXML
    private TextField numberStaffTextField;
    @FXML
    private TextField surnameStaffTextField;
    @FXML
    private TextArea noteStaffTextField;
    @FXML
    private TextField usernameStaffTextField;
    @FXML
    private TextField passwordStaffTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField nameStaffTextField;
    @FXML
    private DatePicker birthDayStaffDatePicker;
    @FXML
    private TextField positionStaffTextField;
    @FXML
    private DatePicker employmentDayStaffDatePicker;
    @FXML
    private Button ordersButton;
    @FXML
    private Button providerButton;
    @FXML
    private Button clientButton;
    @FXML
    private Button backupFormButton;

    private static StaffService staffService;
    private static FurnitureFormController main;

    private long selectedId;

    @FXML
    void initialize() {
        this.idStaff.setCellValueFactory (new PropertyValueFactory("idStaff"));
        this.surnameStaff.setCellValueFactory (new PropertyValueFactory("surnameStaff"));
        this.nameStaff.setCellValueFactory (new PropertyValueFactory("nameStaff"));
        this.positionStaff.setCellValueFactory (new PropertyValueFactory("positionStaff"));
        this.birthDayStaff.setCellValueFactory (new PropertyValueFactory("birthDayStaff"));
        this.employmentDayStaff.setCellValueFactory (new PropertyValueFactory("employmentDayStaff"));
        this.numberStaff.setCellValueFactory (new PropertyValueFactory("numberStaff"));
        this.noteStaff.setCellValueFactory (new PropertyValueFactory("noteStaff"));
        this.usernameStaff.setCellValueFactory (new PropertyValueFactory("username"));
        this.passwordStaff.setCellValueFactory (new PropertyValueFactory("password"));

        this.staffsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
            @Override
            public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                if(newValue != null){
                    StaffFormController.this.surnameStaffTextField.setText(newValue.getSurnameStaff());
                    StaffFormController.this.nameStaffTextField.setText(newValue.getNameStaff());
                    StaffFormController.this.positionStaffTextField.setText(newValue.getPositionStaff());
                    StaffFormController.this.birthDayStaffDatePicker.setValue(newValue.getBirthDayStaff());
                    StaffFormController.this.employmentDayStaffDatePicker.setValue(newValue.getEmploymentDayStaff());
                    StaffFormController.this.numberStaffTextField.setText(newValue.getNumberStaff());
                    StaffFormController.this.noteStaffTextField.setText(newValue.getNoteStaff());
                    StaffFormController.this.usernameStaffTextField.setText(newValue.getUsername());
                    StaffFormController.this.passwordStaffTextField.setText(newValue.getPassword());
                    selectedId = newValue.getIdStaff();

                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            }
        });

        checkStringTextField(surnameStaffTextField);
        checkStringTextField(nameStaffTextField);
        checkStringTextField(positionStaffTextField);

        addButton.setOnAction(this::onInsert);
        deleteButton.setOnAction(this::onDelete);
        updateButton.setOnAction(this::onUpdate);
        refreshButton.setOnAction(this::onRefresh);

        productsButton.setOnAction(this::onProducts);
        providerButton.setOnAction(this::onProvider);
        clientButton.setOnAction(this::onClient);
        ordersButton.setOnAction(this::onOrder);
        backupFormButton.setOnAction(this::onBackUp);

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void checkStringTextField(TextField surnameStaffTextField) {
        surnameStaffTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-\\sа-яА-я*")) {
                surnameStaffTextField.setText(newValue.replaceAll("[^-\\sа-яА-я]", ""));
            }
        });
    }

    private void onInsert(ActionEvent event) {
        if(isFieldValid()) {
            try {
                Staff staff = new Staff();
                staff.setSurnameStaff(surnameStaffTextField.getText());
                staff.setNameStaff(nameStaffTextField.getText());
                staff.setPositionStaff(positionStaffTextField.getText());
                staff.setBirthDayStaff(birthDayStaffDatePicker.getValue());
                staff.setEmploymentDayStaff(employmentDayStaffDatePicker.getValue());
                staff.setNumberStaff(numberStaffTextField.getText());
                staff.setNoteStaff(noteStaffTextField.getText());
                staff.setUsername(usernameStaffTextField.getText());
                staff.setPassword(passwordStaffTextField.getText());

                staffService.insertStaff(staff);

                refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void onDelete(ActionEvent event) {
        try {
            Staff provider = this.staffsTableView.getSelectionModel().getSelectedItem();
            if(provider == null){
                return;
            }
            staffService.deleteStaff(provider.getIdStaff());
            refresh();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onUpdate(ActionEvent event) {
        if (isFieldValid()) {
            try {
                Staff staff = new Staff();
                staff.setIdStaff(selectedId);
                staff.setSurnameStaff(surnameStaffTextField.getText());
                staff.setNameStaff(nameStaffTextField.getText());
                staff.setPositionStaff(positionStaffTextField.getText());
                staff.setBirthDayStaff(birthDayStaffDatePicker.getValue());
                staff.setEmploymentDayStaff(employmentDayStaffDatePicker.getValue());
                staff.setNumberStaff(numberStaffTextField.getText());
                staff.setNoteStaff(noteStaffTextField.getText());
                staff.setUsername(usernameStaffTextField.getText());
                staff.setPassword(passwordStaffTextField.getText());

                staffService.updateStaff(staff);
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
            this.staffsTableView.getItems().setAll(this.staffService.getAllStaff());
            this.clearField();
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean isFieldValid() {
        String errorMessage = "";
        if(this.surnameStaffTextField.getText() == null || this.surnameStaffTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введена фамилия!\n";
        }
        if(this.nameStaffTextField.getText() == null || this.nameStaffTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введено имя!\n";
        }
        if(this.positionStaffTextField.getText() == null || this.positionStaffTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введена должность!\n";
        }
        if(this.birthDayStaffDatePicker.getValue() == null ) {
            errorMessage += "Неправильно введена дата рождения!\n";
        }
        if(this.employmentDayStaffDatePicker.getValue() == null) {
            errorMessage += "Неправильно введена дата найма на работу!\n";
        }
        if(this.numberStaffTextField.getText() == null || this.numberStaffTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен номер!\n";
        }
        if(this.usernameStaffTextField.getText() == null || this.usernameStaffTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен логин!\n";
        }
        if(this.passwordStaffTextField.getText() == null || this.passwordStaffTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введен пароль!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            main.showErrorForm(errorMessage);
            return false;
        }
    }

    private void clearField() {
        surnameStaffTextField.setText("");
        nameStaffTextField.setText("");
        positionStaffTextField.setText("");
        birthDayStaffDatePicker.setValue(null);
        employmentDayStaffDatePicker.setValue(null);
        numberStaffTextField.setText("");
        noteStaffTextField.setText("");
        usernameStaffTextField.setText("");
        passwordStaffTextField.setText("");
    }


    private void onProducts(ActionEvent event) {
        main.onProduct(event);
    }

    private void onProvider(ActionEvent event) {
        main.onProvider(event);
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
        this.staffService = main.getStaffService();

        refresh();
    }
}
