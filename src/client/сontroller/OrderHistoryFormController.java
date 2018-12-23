package client.сontroller;

import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import api.entity.Order;
import api.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderHistoryFormController {

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
    private BarChart<?, ?> salaryBarChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

    @FXML
    private BarChart<?, ?> deliveryBarChart;
    @FXML
    private CategoryAxis x1;
    @FXML
    private NumberAxis y1;


    private OrderFormController main;
    private OrderService orderService;

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

    }

    private void refresh() {
        try {
            this.ordrersTableView.getItems().setAll(this.orderService.getAllUnActiveOrder());

            Map<Month, Float> map = new HashMap();
            List<Order> list = orderService.getAllUnActiveOrder();
            for (Order order : list) {
                boolean flag = false;
                for (Month month : map.keySet()) {
                    if (order.getDateOrder().toLocalDate().getMonth().equals(month)) {
                        map.put(month, map.get(month) + order.getPriceFurniture());
                        flag = true;
                    }
                }
                if (flag == false)
                    map.put(order.getDateOrder().toLocalDate().getMonth(), order.getPriceFurniture());
            }

            XYChart.Series series = new XYChart.Series();
            for (Map.Entry<Month, Float> item : map.entrySet()) {
                String mount = convertMountToRu(item.getKey().toString());
                series.getData().add(new XYChart.Data(mount, item.getValue()));
            }
            salaryBarChart.getData().add(series);

            Map<Month, Float> map1 = new HashMap();
            for (Order order : list) {
                boolean flag = false;
                for (Month month : map1.keySet()) {
                    if (order.getDateOrder().toLocalDate().getMonth().equals(month)) {
                        map1.put(month, map1.get(month) + order.getPriceDelivery());
                        flag = true;
                    }
                }
                if (flag == false)
                    map1.put(order.getDateOrder().toLocalDate().getMonth(), order.getPriceDelivery());
            }

            XYChart.Series series1 = new XYChart.Series();
            for (Map.Entry<Month, Float> item : map1.entrySet()) {
                String mount = convertMountToRu(item.getKey().toString());
                series1.getData().add(new XYChart.Data(mount, item.getValue()));
            }
            deliveryBarChart.getData().add(series1);


        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private String convertMountToRu(String mount) {
        if(mount == "JANUARY")
            return "Январь";
        else if(mount == "FEBRUARY")
            return "Февраль";
        else if(mount == "MARCH")
            return "Март";
        else if(mount == "APRIL")
            return "Апрель";
        else if(mount == "MAY")
            return "Май";
        else if(mount == "JUNE")
            return "Июнь";
        else if(mount == "JULY")
            return "Июль";
        else if(mount == "AUGUST")
            return "Август";
        else if(mount == "SEPTEMBER")
            return "Сентябрь";
        else if(mount == "OCTOBER")
            return "Октябрь";
        else if(mount == "NOVEMBER")
            return "Ноябрь";
        else
            return "Декабрь";
    }

    public void setMain(OrderFormController main){
        this.main = main;
        this.orderService = main.getOrderService();

        refresh();
    }
}
