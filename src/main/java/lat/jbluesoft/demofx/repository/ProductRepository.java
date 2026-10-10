package lat.jbluesoft.demofx.repository;

import lat.jbluesoft.demofx.models.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Laptop HP", "Marca HP", new BigDecimal("2000.00")),
            new Product(2, "Macbook Pro", "Marca apple", new BigDecimal("10000.00")),
            new Product(3, "Iphone 14 Pro Max", "Marca apple", new BigDecimal("5000.00"))
    ));

    public List<Product> findAll() {
        return List.copyOf(products);
    }

    public void deleteById(int idProduct) {
        products.removeIf(product -> product.getIdProduct() == idProduct);
    }
}