package client.сontroller;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import api.entity.Furniture;
import api.entity.Order;
import api.service.FurnitureService;
import api.service.OrderService;
import api.service.StaffService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

public class OrderActiveFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableView<Order> ordrersTableView;
    @FXML
    private TableColumn<Order, Long> idOrder;
    @FXML
    private TableColumn<Order, String> nameFurniture;
    @FXML
    private TableColumn<Order, String> noteFurniture;
    @FXML
    private TableColumn<Order, Long> quantityFurniture;
    @FXML
    private TableColumn<Order, Float> priceFurniture;
    @FXML
    private TableColumn<Order, Float> priceDelivery;
    @FXML
    private TableColumn<Order, Long> idClient;
    @FXML
    private TableColumn<Order, Long> idStaff;
    @FXML
    private TableColumn<Order, LocalDate> dateOrder;

    @FXML
    private TextField deliveryPriceTextField;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;

    private OrderFormController main;
    private OrderService orderService;
    private FurnitureService furnitureService;

    private Order selectedOrder = new Order();

    @FXML
    void initialize() {
        this.idOrder.setCellValueFactory (new PropertyValueFactory("idOrder"));
        this.nameFurniture.setCellValueFactory (new PropertyValueFactory("nameFurniture"));
        this.noteFurniture.setCellValueFactory (new PropertyValueFactory("noteFurniture"));
        this.quantityFurniture.setCellValueFactory (new PropertyValueFactory("quantityFurniture"));
        this.priceFurniture.setCellValueFactory (new PropertyValueFactory("priceFurniture"));
        this.priceDelivery.setCellValueFactory (new PropertyValueFactory("priceDelivery"));
        this.idClient.setCellValueFactory (new PropertyValueFactory("idClient"));
        this.idStaff.setCellValueFactory (new PropertyValueFactory("idStaff"));
        this.dateOrder.setCellValueFactory (new PropertyValueFactory("dateOrder"));

        this.ordrersTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                if(newValue != null){
                    selectedOrder.setIdOrder(newValue.getIdOrder());
                    selectedOrder.setNameFurniture(newValue.getNameFurniture());
                    selectedOrder.setNoteFurniture(newValue.getNoteFurniture());
                    selectedOrder.setQuantityFurniture(newValue.getQuantityFurniture());
                    selectedOrder.setPriceFurniture(newValue.getPriceFurniture());

                    selectedOrder.setIdClient(newValue.getIdClient());


                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            }
        });

        checkLongTextField(deliveryPriceTextField);

        deleteButton.setOnAction(this::onDelete);
        updateButton.setOnAction(this::onUpdate);
        refreshButton.setOnAction(this::onRefresh);

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
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

    private void onDelete(ActionEvent event) {
        try {
            Order order = this.ordrersTableView.getSelectionModel().getSelectedItem();
            if(order == null){
                return;
            }
            orderService.deleteOrder(order.getIdOrder());
            refresh();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onUpdate(ActionEvent event) {
        if (isFieldValid()) {
            try {
                selectedOrder.setPriceDelivery(Float.parseFloat(deliveryPriceTextField.getText()));

                SignUpFormController controller = new SignUpFormController();
                selectedOrder.setIdStaff(controller.getIdStaff());

                selectedOrder.setDateOrder(Date.valueOf(dateDatePicker.getValue()));

                orderService.updateOrder(selectedOrder);


                Furniture furniture1 = furnitureService.getFurnitureByName(selectedOrder.getNameFurniture());
                furnitureService.updateQuantityFurniture(furniture1.getQuantity() - selectedOrder.getQuantityFurniture(),furniture1.getIdProducts());

                refresh();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isFieldValid() {
        String errorMessage = "";
        if(this.deliveryPriceTextField.getText() == null || this.deliveryPriceTextField.getText().isEmpty()) {
            errorMessage += "Неправильно введена цена доставки!\n";
        }
        if(this.dateDatePicker.getValue() == null ) {
            errorMessage += "Неправильно введена дата!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            showErrorForm(errorMessage);
            return false;
        }
    }

    private void showErrorForm(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Пожалуйста исправьте неверные поля");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
    private void onRefresh(ActionEvent event) {
        refresh();
    }

    private void refresh() {
        try {
            this.ordrersTableView.getItems().setAll(this.orderService.getAllActiveOrder());
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMain(OrderFormController main){
        this.main = main;
        this.orderService = main.getOrderService();
        this.furnitureService = main.getFurnitureService();
        refresh();
    }
}
