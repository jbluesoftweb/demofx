package lat.jbluesoft.demofx;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lat.jbluesoft.demofx.models.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private ObservableList<Product> products = FXCollections.observableArrayList(
        new Product(1,"Laptop HP", "Marca HP",2000),
        new Product(2,"Macbook Pro", "Marca apple",10000),
        new Product(3,"Iphone 14 Pro Max", "Marca apple",5000)
    );

    @Override
    public void start(Stage stage) throws IOException {

        TableView<Product> tableView = new TableView<>();

        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> desColumn = new TableColumn<>("DESCRIPTION");
        desColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("PRICE");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(idColumn,nameColumn,desColumn,priceColumn);
        tableView.setItems(this.products);

        VBox vBox = new VBox(tableView);
        Scene scene = new Scene(vBox, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
