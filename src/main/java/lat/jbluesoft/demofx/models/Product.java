package lat.jbluesoft.demofx.models;

import java.math.BigDecimal;

public class Product {

    private int idProduct;
    private String name;
    private String description;
    private BigDecimal price;

    public Product(){}

    public Product(int idProduct, String name, String description, BigDecimal price) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
