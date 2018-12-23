package client.сontroller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.*;

import api.entity.Client;
import api.entity.Furniture;
import api.entity.Order;
import api.service.FurnitureService;
import api.service.OrderService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class SellRegistrationFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextArea deliveryAddressTextField;
    @FXML
    private Button sellButton;
    @FXML
    private TextField totalPriceTextField;
    @FXML
    private TextField deliveryPriceTextField;
    @FXML
    private CheckBox deliveryCheckBox;
    @FXML
    private Button getClientAddressButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField madeMoneyTextField;
    @FXML
    private TextField changeTextField;
    @FXML
    private CheckBox prinOrderCheckBox;

    private static OrderFormController main;
    private  Client client;
    private Long idStaff;
    private OrderService orderService;
    private FurnitureService furnitureService;

    @FXML
    void initialize() {

        deliveryCheckBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(deliveryCheckBox.isSelected() == true){
                    deliveryAddressTextField.setDisable(false);
                    deliveryPriceTextField.setDisable(false);
                    getClientAddressButton.setDisable(false);
                }
                else {
                    deliveryAddressTextField.setDisable(true);
                    deliveryPriceTextField.setDisable(true);
                    getClientAddressButton.setDisable(true);
                }
            }
        });

        deliveryPriceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches(".\\d*")) {
                    deliveryPriceTextField.setText(newValue.replaceAll("[^.\\d]", ""));
                }

                if(!deliveryPriceTextField.getText().isEmpty()) {
                    Float temp = (main.getTotalPrice() + Float.parseFloat(deliveryPriceTextField.getText()));
                    totalPriceTextField.setText(temp.toString());
                }

            }
        });

        madeMoneyTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches(".\\d*")) {
                    madeMoneyTextField.setText(newValue.replaceAll("[^.\\d]", ""));
                }
                if(!madeMoneyTextField.getText().isEmpty())
                    if(Float.parseFloat(madeMoneyTextField.getText()) >= Float.parseFloat(totalPriceTextField.getText())){

                        Float temp = Float.parseFloat(madeMoneyTextField.getText()) - Float.parseFloat(totalPriceTextField.getText());

                        changeTextField.setText(temp.toString());

                        sellButton.setDisable(false);
                    }
                    else {
                        changeTextField.clear();
                        sellButton.setDisable(true);
                    }

            }
        });


        deliveryAddressTextField.setDisable(true);
        deliveryPriceTextField.setDisable(true);
        getClientAddressButton.setDisable(true);
        sellButton.setDisable(true);

        cancelButton.setOnAction(this::onCancel);
        getClientAddressButton.setOnAction(this::onGetClientAddress);
        sellButton.setOnAction(this::onSell);
    }



    private void onSell(ActionEvent event) {
        ArrayList<Furniture> list = main.getOrderList();

        Float deliveryPrice = new Float(0);
        if (!deliveryPriceTextField.getText().isEmpty()) {
            Long totalFurniture = new Long(0);
            for (Furniture furniture : list) {
                totalFurniture += furniture.getQuantity();
            }
            deliveryPrice = Float.parseFloat(deliveryPriceTextField.getText()) / totalFurniture;
        }

        for (Furniture furniture : list) {
            try {
                Order order = new Order();
                order.setNameFurniture(furniture.getNameProduct());
                order.setNoteFurniture(furniture.getNote());
                order.setQuantityFurniture(furniture.getQuantity());
                order.setPriceFurniture(furniture.getPrice());
                order.setPriceDelivery(deliveryPrice);
                if (client != null)
                    order.setIdClient(client.getIdClient());
                order.setIdStaff(idStaff);
                order.setDateOrder(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

                if (client != null)
                    orderService.insertOrder(order);
                else
                    orderService.insertOrderWithoutClient(order);

                Furniture furniture1 = furnitureService.getFurnitureById(furniture.getIdProducts());
                furnitureService.updateQuantityFurniture(furniture1.getQuantity() - furniture.getQuantity(),furniture1.getIdProducts());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if(prinOrderCheckBox.isSelected()){
            printOrder();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Оформление");
        alert.setHeaderText(null);
        alert.setContentText("Заказ успешно оформлен");
        alert.showAndWait();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private void printOrder(){
        try {
            File file = new File("PrintOrder.txt");
            file.createNewFile();
            Writer writer = Files.newBufferedWriter(file.toPath());
            //FileWriter writer = new FileWriter(file);

            writer.write("=================================================================================================\r\n" +
                             "Наименование                             Примечание                  Количество            Цена \r\n" +
                             "=================================================================================================\r\n");

            ArrayList<Furniture> allFurnitureInOrder = main.getOrderList();
            for(Furniture list :allFurnitureInOrder){
                writer.write(String.format("%-40s", list.getNameProduct()));
                writer.write(String.format("%-30s", list.getNote()));
                writer.write(String.format("%-20s", list.quantityProperty().getValue()));
                writer.write(String.format("%-10s", list.getPrice()));
                writer.write("\r\n");
            writer.write("=================================================================================================\r\n\r\n");


        }
            if(!deliveryAddressTextField.getText().isEmpty()){
                writer.write("Доставка: " + deliveryAddressTextField.getText()+"\r\n" +
                        "Стоимость доставки: " + deliveryPriceTextField.getText() + "\r\n");
                writer.write("=======================================\r\n");
            }

            writer.write("К оплате: " + totalPriceTextField.getText() +"\r\n\r\n" +
                             "Внесено: " + madeMoneyTextField.getText() + "\r\n" +
                             "Cдача: " + changeTextField.getText() +"\r\n"+
                             "=======================================\r\n"+
                              new Date().toString());

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onGetClientAddress(ActionEvent event) {
        Client client = main.getClient();

        deliveryAddressTextField.setText(client.getCountryClient() +" "+ client.getTownClient() +" "+ client.getAddressClient());

    }

    private void onCancel(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private void refresh() {
        totalPriceTextField.setText(main.getTotalPrice().toString());
        client = main.getClient();
        if(client == null)
            getClientAddressButton.setVisible(false);
        SignUpFormController controller = new SignUpFormController();
        idStaff = controller.getIdStaff();
    }

    public void setMain(OrderFormController main){
        this.main = main;
        this.refresh();
        this.orderService = main.getOrderService();
        this.furnitureService = main.getFurnitureService();
    }

}
