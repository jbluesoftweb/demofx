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
import lat.jbluesoft.demofx.repository.ProductRepository;

import java.io.IOException;
import java.math.BigDecimal;


public class HelloApplication extends Application {

    private final ProductRepository repository = new ProductRepository();
    private final ObservableList<Product> products = FXCollections.observableArrayList();



    @Override
    public void start(Stage stage) throws IOException {
        products.setAll(repository.findAll());

        TableView<Product> tableView = new TableView<>();

        TableColumn<Product, Integer> idColumn = new TableColumn<>("Código");
        TableColumn<Product, String> nameColumn = new TableColumn<>("Nombre");
        TableColumn<Product, String> desColumn = new TableColumn<>("Descripción");
        TableColumn<Product, BigDecimal> priceColumn = new TableColumn<>("Precio");

        idColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getIdProduct()));
        nameColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getName()));
        desColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getDescription()));
        priceColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getPrice()));

        TableColumn<Product, Void> deleteColumn = new TableColumn<>("Eliminar");
        deleteColumn.setCellFactory(param -> new TableCell<>(){
            private final Button deleteButton = new Button("Eliminar");
            {
                deleteButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    repository.deleteById(product.getIdProduct());
                    products.setAll(repository.findAll());
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
