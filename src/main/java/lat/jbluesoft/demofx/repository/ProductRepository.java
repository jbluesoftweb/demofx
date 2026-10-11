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

    private int nextId = products.stream()
            .mapToInt(Product::getIdProduct)
            .max()
            .orElse(0) +1;

    public List<Product> findAll() {
        return List.copyOf(products);
    }

    public Product save(Product product){
        product.setIdProduct(nextId++);
        products.add(product);
        return product;
    }

    public void deleteById(int idProduct) {
        products.removeIf(product -> product.getIdProduct() == idProduct);
    }

    public void update(Product updated){
        for (int i = 0; i < products.size();i++) {
            if (products.get(i).getIdProduct() == updated.getIdProduct()){
                products.set(i, updated);
                return;
            }
        }
    }
}