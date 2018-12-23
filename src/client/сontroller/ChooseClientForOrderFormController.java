package client.сontroller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import api.entity.Client;
import api.service.ClientService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ChooseClientForOrderFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
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


    private static ClientService clientService;
    private static OrderFormController main;
    public static Client client;

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


        clientsTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    try {
                        client = clientService.getClientById(clientsTableView.getSelectionModel().getSelectedItem().getIdClient());

                            OrderFormController controller = new OrderFormController();
                            controller.setClient1(client);

                        ((Node) (event.getSource())).getScene().getWindow().hide();

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    private void refresh() {
        try {
            this.clientsTableView.getItems().setAll(this.clientService.getAllClient());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void setMain(OrderFormController main){
        this.main = main;
        this.clientService = main.getClientService();
        this.refresh();
    }
}
