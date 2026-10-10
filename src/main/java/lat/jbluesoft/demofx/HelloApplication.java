package lat.jbluesoft.demofx;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lat.jbluesoft.demofx.models.Product;

import java.io.IOException;


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
        TableColumn<Product, String> nameColumn = new TableColumn<>("NAME");
        TableColumn<Product, String> desColumn = new TableColumn<>("DESCRIPTION");
        TableColumn<Product, Double> priceColumn = new TableColumn<>("PRICE");

        idColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getIdProduct()));
        nameColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getName()));
        desColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getDescription()));
        priceColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getPrice()));

        TableColumn<Product, Void> deleteColumn = new TableColumn<>("delete");
        deleteColumn.setCellFactory(param -> new TableCell<>(){
            private final Button deleteButton = new Button("DELETE");
            {
                deleteButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    tableView.getItems().remove(product);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
               super.updateItem(item, empty);
               if (empty){
                   setGraphic(null);
               }else {
                   setGraphic(deleteButton);
               }
            }
        });



        tableView.getColumns().addAll(idColumn,nameColumn,desColumn,priceColumn, deleteColumn);
        tableView.setItems(this.products);

        VBox vBox = new VBox(tableView);
        Scene scene = new Scene(vBox, 640, 400);
        stage.setTitle("Gestión de Productos");
        stage.setScene(scene);
        stage.show();
    }
}
