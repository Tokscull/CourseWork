package client.сontroller;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import api.entity.Furniture;
import api.service.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FurnitureFormController {

    @FXML
    private TableView<Furniture> productsTableView;
    @FXML
    private TableColumn<Furniture, Long> idProducts;
    @FXML
    private TableColumn<Furniture, String> nameProduct;
    @FXML
    private TableColumn<Furniture, String> brand;
    @FXML
    private TableColumn<Furniture, Long> quantity;
    @FXML
    private TableColumn<Furniture, Float> purchasePrice;
    @FXML
    private TableColumn<Furniture, Float> price;
    @FXML
    private TableColumn<Furniture, String> note;
    @FXML
    private TableColumn<Furniture, String> nameProvider;
    @FXML
    private TextField brandProductTextField;
    @FXML
    private TextField quantityProductTextField;
    @FXML
    private TextField purchasePriceProductTextField;
    @FXML
    private TextField priceProductTextField;
    @FXML
    private TextArea noteProductTextField;
    @FXML
    private TextField nameProductTextField;
    @FXML
    private ComboBox<String> nameProviderComboBox;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Button productsButton;
    @FXML
    private Button providerButton;
    @FXML
    private Button staffButton;
    @FXML
    private Button clientButton;
    @FXML
    private Button backupFormButton;

    private static FurnitureService furnitureService;
    private static ProviderService providerService;
    private static StaffService staffService;
    private static ClientService clientService;
    private static OrderService orderService;
    private static BackupService backupService;
    private static SignUpFormController main;

    private long idFurniture;

    @FXML
    void initialize() {
        this.idProducts.setCellValueFactory(new PropertyValueFactory("idProducts"));
        this.nameProduct.setCellValueFactory(new PropertyValueFactory("nameProduct"));
        this.brand.setCellValueFactory(new PropertyValueFactory("brand"));
        this.quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        this.purchasePrice.setCellValueFactory(new PropertyValueFactory("purchasePrice"));
        this.price.setCellValueFactory(new PropertyValueFactory("price"));
        this.note.setCellValueFactory(new PropertyValueFactory("note"));
        this.nameProvider.setCellValueFactory(new PropertyValueFactory("nameProvider"));


        checkLongTextField(quantityProductTextField);
        checkLongTextField(purchasePriceProductTextField);
        checkLongTextField(priceProductTextField);


            this.productsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Furniture>() {
                @Override
                public void changed(ObservableValue<? extends Furniture> observable, Furniture oldValue, Furniture newValue) {
                    if (newValue != null) {
                        FurnitureFormController.this.nameProductTextField.setText(newValue.getNameProduct());
                        FurnitureFormController.this.brandProductTextField.setText(newValue.getBrand());
                        FurnitureFormController.this.quantityProductTextField.setText(Long.toString(newValue.getQuantity()));
                        FurnitureFormController.this.purchasePriceProductTextField.setText(Float.toString(newValue.getPurchasePrice()));
                        FurnitureFormController.this.priceProductTextField.setText(Float.toString(newValue.getPrice()));
                        FurnitureFormController.this.noteProductTextField.setText(newValue.getNote());
                        FurnitureFormController.this.nameProviderComboBox.setValue(newValue.getNameProvider());
                        idFurniture = newValue.getIdProducts();

                        updateButton.setDisable(false);
                        deleteButton.setDisable(false);
                    }
                }
            });


        ObservableList data = productsTableView.getItems();

        filterTableView(data, this.nameProductTextField);
        filterTableView(data, this.brandProductTextField);



        addButton.setOnAction(this::onInsert);
        deleteButton.setOnAction(this::onDelete);
        updateButton.setOnAction(this::onUpdate);
        refreshButton.setOnAction(this::onRefresh);

        providerButton.setOnAction(this::onProvider);
        staffButton.setOnAction(this::onStaff);
        productsButton.setOnAction(this::onProduct);
        clientButton.setOnAction(this::onClient);
        ordersButton.setOnAction(this::onOrder);
        backupFormButton.setOnAction(this::onBackUp);

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void filterTableView(ObservableList data, TextField p) {
        p.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (productsTableView.getSelectionModel().isEmpty()) {
                    if (oldValue != null && (newValue.length() < oldValue.length())) {
                        productsTableView.setItems(data);
                    }
                    String value = newValue.toLowerCase();
                    ObservableList<Furniture> subentries = FXCollections.observableArrayList();

                    long count = productsTableView.getColumns().stream().count();
                    for (int i = 0; i < productsTableView.getItems().size(); i++) {
                        for (int j = 0; j < count; j++) {
                            String entry = "" + productsTableView.getColumns().get(j).getCellData(i);
                            if (entry.toLowerCase().contains(value)) {
                                subentries.add(productsTableView.getItems().get(i));
                                break;
                            }
                        }
                    }
                    productsTableView.setItems(subentries);
                }
            }
        });
    }


    private void checkLongTextField(TextField quantityProductTextField) {
        quantityProductTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches(".\\d*") ) {
                    quantityProductTextField.setText(newValue.replaceAll("[^.\\d]", ""));
                }
            }
        });
    }

    private void onInsert(ActionEvent event) {
        if(isFieldValid()) {
            try {
                Furniture furniture = new Furniture();
                furniture.setNameProduct(nameProductTextField.getText());
                furniture.setBrand(brandProductTextField.getText());
                furniture.setQuantity(Long.parseLong(quantityProductTextField.getText()));
                furniture.setPurchasePrice(Float.parseFloat(purchasePriceProductTextField.getText()));
                furniture.setPrice(Float.parseFloat(priceProductTextField.getText()));
                furniture.setNote(noteProductTextField.getText());
                furniture.setNameProvider(nameProviderComboBox.getValue());

                furnitureService.insertFurniture(furniture);

                refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void refresh() {
        try {
            this.clearField();
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            this.productsTableView.getItems().setAll(this.furnitureService.getAllFurniture());
            List<String> list = providerService.getAllNameProvider();
            for (String provider : list)
                nameProviderComboBox.getItems().addAll((provider));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onDelete(ActionEvent event) {
        try {
            Furniture furniture = this.productsTableView.getSelectionModel().getSelectedItem();
            if(furniture == null){
                return;
            }
            furnitureService.deleteFurniture(furniture.getIdProducts());
            refresh();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onUpdate(ActionEvent event) {
        if (isFieldValid()) {
            try {
                Furniture furniture = new Furniture();
                furniture.setIdProducts(idFurniture);
                furniture.setNameProduct(nameProductTextField.getText());
                furniture.setBrand(brandProductTextField.getText());
                furniture.setQuantity(Long.parseLong(quantityProductTextField.getText()));
                furniture.setPurchasePrice(Float.parseFloat(purchasePriceProductTextField.getText()));
                furniture.setPrice(Float.parseFloat(priceProductTextField.getText()));
                furniture.setNote(noteProductTextField.getText());
                furniture.setNameProvider(nameProviderComboBox.getValue());

                furnitureService.updateFurniture(furniture);
                refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void onRefresh(ActionEvent event) {
        refresh();
    }


    public void onProvider(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/ProviderForm.fxml"));
            Parent root = loader.load();

            ProviderFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onStaff(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/StaffForm.fxml"));
            Parent root = loader.load();

            StaffFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/ProductsForm.fxml"));
            Parent root = loader.load();

            FurnitureFormController controller = loader.getController();
            controller.refresh();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/ClientForm.fxml"));
            Parent root = loader.load();

            ClientFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onOrder(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/OrderForm.fxml"));
            Parent root = loader.load();

            OrderFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBackUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/BackupForm.fxml"));
            Parent root = loader.load();

            BackupFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isFieldValid() {
        String errorMessage = "";
        if(this.nameProductTextField.getText() == null || this.nameProductTextField.getText().isEmpty()) {
            errorMessage += "Не введено имя!\n";
        }
        if(this.brandProductTextField.getText() == null || this.brandProductTextField.getText().isEmpty()) {
            errorMessage += "Не введен бренд!\n";
        }
        if(this.quantityProductTextField.getText() == null || this.quantityProductTextField.getText().isEmpty()) {
            errorMessage += "Не введено количество!\n";
        }
        if(this.purchasePriceProductTextField.getText() == null || this.purchasePriceProductTextField.getText().isEmpty()) {
            errorMessage += "Не введена цена закупка!\n";
        }
        if(this.priceProductTextField.getText() == null || this.priceProductTextField.getText().isEmpty()) {
            errorMessage += "Не введена цена продажи!\n";
        }
        if(this.noteProductTextField.getText() == null || this.noteProductTextField.getText().isEmpty()) {
            errorMessage += "Не введено примечание!\n";
        }
        if(this.nameProviderComboBox.getValue() == null || this.nameProviderComboBox.getValue().isEmpty()){
            errorMessage += "Поставщик не выбран\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            showErrorForm(errorMessage);
            return false;
        }
    }

    public void showErrorForm(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Пожалуйста исправьте неверные поля");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void clearField() {
        nameProductTextField.setText("");
        brandProductTextField.setText("");
        quantityProductTextField.setText("");
        purchasePriceProductTextField.setText("");
        priceProductTextField.setText("");
        noteProductTextField.setText("");
        nameProviderComboBox.getItems().removeAll(nameProviderComboBox.getItems());
    }


    public void setMain(SignUpFormController main){
        this.main = main;
        this.furnitureService = main.getFurnitureService();
        this.providerService = main.getProviderService();
        this.staffService = main.getStaffService();
        this.clientService = main.getClientService();
        this.orderService = main.getOrderService();
        this.backupService = main.getBackupService();

        refresh();
    }


    public static ProviderService getProviderService() { return providerService; }
    public static StaffService getStaffService() { return staffService; }
    public static ClientService getClientService() {
        return clientService;
    }
    public static FurnitureService getFurnitureService() {
        return furnitureService;
    }
    public static OrderService getOrderService() {
        return orderService;
    }
    public static BackupService getBackupService() {
        return backupService;
    }
}
