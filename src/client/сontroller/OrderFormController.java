package client.сontroller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import api.entity.Client;
import api.entity.Furniture;
import api.service.ClientService;
import api.service.FurnitureService;
import api.service.OrderService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OrderFormController{

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button productsButton;
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
    private TextField totalPriceTextField;
    @FXML
    private Button providerButton;
    @FXML
    private Button staffButton;
    @FXML
    private Button clientButton;
    @FXML
    private TextField idFindProductTextField;
    @FXML
    private Button inputClientNameButton;
    @FXML
    private TextField clientNameTextFiled;
    @FXML
    private Button clearSelectedProductsButton;
    @FXML
    private Button sellProductsButton;
    @FXML
    private Button orderHistoryButton;
    @FXML
    private Button orderActiveButton;
    @FXML
    private Button backupFormButton;

    private static OrderService orderService;
    private static FurnitureService furnitureService;
    private static ClientService clientService;
    private static FurnitureFormController main;

    public ArrayList<Furniture> orderList = new ArrayList();
    private static Client client;
    private Float totalPrice;



    @FXML
    void initialize()  {
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
                        totalPrice = totalOrderPrice;
                        sellProductsButton.setDisable(false);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ObservableList data = productsTableView.getItems();
        filterTableView(data, this.idFindProductTextField);

        providerButton.setOnAction(this::onProvider);
        staffButton.setOnAction(this::onStaff);
        productsButton.setOnAction(this::onProduct);
        clientButton.setOnAction(this::onClient);
        backupFormButton.setOnAction(this::onBackUp);

        inputClientNameButton.setOnAction(this::onInputClientName);
        clearSelectedProductsButton.setOnAction(this::onClearSelectedProducts);
        sellProductsButton.setOnAction(this::onSellProducts);

        sellProductsButton.setDisable(true);

        orderHistoryButton.setOnAction(this::onOrderHistory);
        orderActiveButton.setOnAction(this::onOrderActive);
    }

    private void filterTableView(ObservableList data, TextField p) {
        p.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
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
        });
    }

    private void onOrderActive(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/OrderActiveForm.fxml"));
            Parent root = loader.load();

            OrderActiveFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        main.onOrder(event);
    }

    private void onOrderHistory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/OrderHistoryForm.fxml"));
            Parent root = loader.load();

            OrderHistoryFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onSellProducts(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/SellRegistrationForm.fxml"));
            Parent root = loader.load();

            SellRegistrationFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        main.onOrder(event);
    }

    private void onClearSelectedProducts(ActionEvent event) {
        main.onOrder(event);
    }


    private void onInputClientName(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/client/form/ChooseClientForOrder.fxml"));
            Parent root = loader.load();

            ChooseClientForOrderFormController controller = loader.getController();
            controller.setMain(this);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("EsoFun");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(client != null)
        clientNameTextFiled.setText(client.getSurnameClient() + " " + client.getNameClient());
    }

    public void setClient1( Client client){
        this.client = client;
    }


    private void onProvider(ActionEvent event) {
        main.onProvider(event);
    }

    private void onProduct(ActionEvent event) {
        main.onProduct(event);
    }

    private void onStaff(ActionEvent event) {
        main.onStaff(event);
    }

    private void onClient(ActionEvent event) {
        main.onClient(event);
    }

    private void onBackUp(ActionEvent event) {
        main.onBackUp(event);
    }


    private void showOrder(){
        if(orderList.isEmpty() == false){
            this.selectFurnitureTableView.getItems().setAll(orderList);
        }
    }

    private void refresh() {
        try {
            this.productsTableView.getItems().setAll(this.furnitureService.getAllFurniture());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMain(FurnitureFormController main){
        this.main = main;
        this.furnitureService = main.getFurnitureService();
        this.orderService = main.getOrderService();
        this.clientService = main.getClientService();

        refresh();
    }

    public static OrderService getOrderService() {
        return orderService;
    }

    public static FurnitureService getFurnitureService() {
        return furnitureService;
    }

    public static ClientService getClientService() {
        return clientService;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public  Client getClient() {
        return client;
    }

    public ArrayList<Furniture> getOrderList() {
        return orderList;
    }


}
