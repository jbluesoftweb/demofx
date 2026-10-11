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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import java.math.RoundingMode;


import java.io.IOException;
import java.math.BigDecimal;


public class HelloApplication extends Application {

    private final ProductRepository repository = new ProductRepository();
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    private final TextField nameField = new TextField();
    private final TextField descriptionField = new TextField();
    private final TextField priceField = new TextField();
    private final Button saveButton = new Button("Agregar");
    private final Button cancelButton = new Button("Cancelar");
    private final Label messageLabel = new Label();

    private Product editingProduct;

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
                    if (editingProduct != null && editingProduct.getIdProduct() == product.getIdProduct()) {
                        resetForm();
                    }
                    refreshProducts();
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

        TableColumn<Product, Void> editColumn = new TableColumn<>("Editar");
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Editar");
            {
                editButton.setOnAction(event ->
                        startEditing(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });



        tableView.getColumns().addAll(idColumn, nameColumn, desColumn, priceColumn, editColumn, deleteColumn);
        tableView.setItems(products);

        nameField.setPromptText("Nombre");
        descriptionField.setPromptText("Descripción");
        priceField.setPromptText("Precio (ej. 1500.50)");
        cancelButton.setVisible(false);
        cancelButton.setManaged(false);

        HBox.setHgrow(nameField, Priority.ALWAYS);
        HBox.setHgrow(descriptionField, Priority.ALWAYS);

        saveButton.setOnAction(event -> saveProduct());
        cancelButton.setOnAction(event -> resetForm());

        HBox form = new HBox(8, nameField, descriptionField, priceField, saveButton, cancelButton);

        VBox root = new VBox(8, form, messageLabel, tableView);

        root.setPadding(new Insets(10));
        VBox.setVgrow(tableView, Priority.ALWAYS);

        Scene scene = new Scene(root, 760, 420);
        stage.setTitle("Gestión de Productos");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshProducts() {
        products.setAll(repository.findAll());
    }


    private void saveProduct() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();

        if (name.isEmpty()) {
            messageLabel.setText("El nombre es obligatorio.");
            return;
        }

        BigDecimal price;
        try {
            price = new BigDecimal(priceField.getText().trim());
        } catch (NumberFormatException e) {
            messageLabel.setText("El precio no es un número válido.");
            return;
        }
        if (price.signum() <= 0) {
            messageLabel.setText("El precio debe ser mayor que 0.");
            return;
        }
        price = price.setScale(2, RoundingMode.HALF_UP);

        if (editingProduct == null) {
            repository.save(new Product(0, name, description, price));
        } else {
            repository.update(new Product(editingProduct.getIdProduct(), name, description, price));
        }

        refreshProducts();
        resetForm();
    }

    private void startEditing(Product product) {
        editingProduct = product;
        nameField.setText(product.getName());
        descriptionField.setText(product.getDescription());
        priceField.setText(product.getPrice().toPlainString());
        saveButton.setText("Actualizar");
        cancelButton.setVisible(true);
        cancelButton.setManaged(true);
        messageLabel.setText("");
        nameField.requestFocus();
    }

    private void resetForm() {
        editingProduct = null;
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        saveButton.setText("Agregar");
        cancelButton.setVisible(false);
        cancelButton.setManaged(false);
        messageLabel.setText("");
        nameField.requestFocus();
    }


}
