package client.сontroller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import api.entity.Client;
import api.entity.Furniture;
import api.entity.Order;
import api.service.ClientService;
import api.service.FurnitureService;
import api.service.OrderService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClientWindowFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private TableView<Furniture> productsTableView;
    @FXML
    private TableColumn<Furniture, Long> idProducts;
    @FXML
    private TableColumn<Furniture, String> nameProduct;
    @FXML
    private TableColumn<Furniture, String> brand;
    @FXML
    private TableColumn<Furniture, String> quantity;
    @FXML
    private TableColumn<Furniture, String> purchasePrice;
    @FXML
    private TableColumn<Furniture, Float> price;
    @FXML
    private TableColumn<Furniture, String> note;
    @FXML
    private TableColumn<Furniture, String> nameProvider;

    @FXML
    private TableView<Furniture> selectFurnitureTableView;
    @FXML
    private TableColumn<Furniture, String> nameSelectFurniture;
    @FXML
    private TableColumn<Furniture, String> noteSelectFurniture;
    @FXML
    private TableColumn<Furniture, Long> quantitySelectFurniture;
    @FXML
    private TableColumn<Furniture, Float> totalPriceSelectFurniture;

    @FXML
    private TextField idFindProductTextField;

    @FXML
    private Button findButton;

    @FXML
    private TextField totalPriceTextField;

    @FXML
    private Button clearSelectedProductsButton;

    @FXML
    private Button sellProductsButton;

    @FXML
    private TextArea deliveryAddressTextField;
    @FXML
    private Button getClientAddressButton;


    @FXML
    private Button ordersButton;
    @FXML
    private Button clientButton;

    private SignUpFormController main;
    private static FurnitureService furnitureService;
    private static OrderService orderService;
    private static ClientService clientService;

    public ArrayList<Furniture> orderList = new ArrayList();

    private static Long IdClient;

    @FXML
    void initialize() {
        this.idProducts.setCellValueFactory (new PropertyValueFactory("idProducts"));
        this.nameProduct.setCellValueFactory (new PropertyValueFactory("nameProduct"));
        this.brand.setCellValueFactory (new PropertyValueFactory("brand"));
        this.quantity.setCellValueFactory (new PropertyValueFactory("quantity"));
        this.purchasePrice.setCellValueFactory (new PropertyValueFactory("purchasePrice"));
        this.price.setCellValueFactory (new PropertyValueFactory("price"));
        this.note.setCellValueFactory (new PropertyValueFactory("note"));
        this.nameProvider.setCellValueFactory (new PropertyValueFactory("nameProvider"));

        this.nameSelectFurniture.setCellValueFactory (new PropertyValueFactory("nameProduct"));
        this.noteSelectFurniture.setCellValueFactory (new PropertyValueFactory("note"));
        this.quantitySelectFurniture.setCellValueFactory (new PropertyValueFactory("quantity"));
        this.totalPriceSelectFurniture.setCellValueFactory (new PropertyValueFactory("price"));

        idFindProductTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    idFindProductTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        productsTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

                    try {
                        boolean flag = false;
                        Furniture furniture = furnitureService.getFurnitureById(productsTableView.getSelectionModel().getSelectedItem().getIdProducts());

                        for (Furniture findFurniture : orderList) {
                            if (findFurniture.getNameProduct().equals(furniture.getNameProduct())) {
                                if(findFurniture.getQuantity() < furniture.getQuantity()) {
                                    findFurniture.setQuantity(findFurniture.getQuantity() + 1);
                                    findFurniture.setPrice(findFurniture.getPrice() + furniture.getPrice());
                                }
                                flag = true;
                                break;
                            }
                        }
                        if (flag == false && furniture.getQuantity() != 0) {
                            furniture.setQuantity(1);
                            orderList.add(furniture);
                        }
                        showOrder();

                        Float totalOrderPrice = new Float(0);
                        for(Furniture furniture1 : orderList) {
                            totalOrderPrice =  totalOrderPrice + furniture1.getPrice();
                        }
                        totalPriceTextField.setText(totalOrderPrice.toString());

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        findButton.setOnAction(this::onFind);
        clearSelectedProductsButton.setOnAction(this::onClearSelectedProducts);

        sellProductsButton.setDisable(true);

        sellProductsButton.setOnAction(this::onSellProducts);

        getClientAddressButton.setOnAction(this::onGetClientAddress);

        deliveryAddressTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(deliveryAddressTextField.getText().isEmpty())
                    sellProductsButton.setDisable(true);
                else
                    if(!orderList.isEmpty())
                    sellProductsButton.setDisable(false);
            }
        });

        clientButton.setOnAction(this::onClient);
        ordersButton.setOnAction(this::onOrder);

    }

    public void onOrder(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/ClientWindowForm.fxml"));
            Parent root = loader.load();

            ClientWindowFormController controller = loader.getController();
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

    private void onClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/AccountClientForm.fxml"));
            Parent root = loader.load();

            AccountClientFormController controller = loader.getController();
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

    private void onSellProducts(ActionEvent event) {
        for (Furniture furniture : orderList) {
            try {
                Order order = new Order();
                order.setNameFurniture(furniture.getNameProduct());
                order.setNoteFurniture(furniture.getNote());
                order.setQuantityFurniture(furniture.getQuantity());
                order.setPriceFurniture(furniture.getPrice());
                order.setIdClient(main.getIdClient());


                orderService.insertOrderFromClient(order);


            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Оформление");
        alert.setHeaderText(null);
        alert.setContentText("Заказ успешно оформлен");
        alert.showAndWait();
        onOrder(event);
    }

    private void onGetClientAddress(ActionEvent event) {
        try {
            Long idClient = main.getIdClient();
            Client client = clientService.getClientById(idClient);
            deliveryAddressTextField.setText(client.getCountryClient() +" "+ client.getTownClient() +" "+ client.getAddressClient());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void onFind(ActionEvent event) {
        if (idFindProductTextField.getText().isEmpty() == false && idFindProductTextField.getText() != null)
            try {
                this.productsTableView.getItems().setAll(this.furnitureService.getFurnitureById(Long.parseLong(idFindProductTextField.getText())));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        else {
            try {
                this.productsTableView.getItems().setAll(this.furnitureService.getAllFurniture());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void onClearSelectedProducts(ActionEvent event) {
        refresh();
    }

    private void showOrder(){
        if(orderList.isEmpty() == false){
            this.selectFurnitureTableView.getItems().setAll(orderList);
            if(!deliveryAddressTextField.getText().isEmpty())
            sellProductsButton.setDisable(false);
        }
    }

    private void refresh() {
        try {
            this.productsTableView.getItems().setAll(this.furnitureService.getAllFurniture());
            this.selectFurnitureTableView.getItems().clear();
            this.totalPriceTextField.setText("");
            this.deliveryAddressTextField.setText("");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMain(SignUpFormController main) {
        this.main = main;
        this.furnitureService = main.getFurnitureService();
        this.orderService = main.getOrderService();
        this.clientService = main.getClientService();
        this.IdClient = main.getIdClient();

        refresh();
    }



    public static ClientService getClientService() {
        return clientService;
    }

    public static Long getIdClient() {
        return IdClient;
    }
}
