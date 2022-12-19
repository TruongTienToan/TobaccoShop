package Model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Product {
    private Long idProduct;
    private String title;
    private Double price;
    private Double quantity;
    private Instant createdAT;
    private Instant updatedAT;
    public List<Product> products;

    public Product() {

    }

    public Product(Long idProduct, String title, Double price, Double quantity) {
        this.idProduct = idProduct;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(Long idProduct, String title, Double price, Double quantity, Instant createdAT, Instant updatedAT) {
        this.idProduct = idProduct;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.createdAT = createdAT;
        this.updatedAT = updatedAT;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Instant getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(Instant createdAT) {
        this.createdAT = createdAT;
    }

    public Instant getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(Instant updatedAT) {
        this.updatedAT = updatedAT;
    }

    public static Product parseProduct(String rawProduct) {
        String[] array = rawProduct.split(",");
        Product product = new Product();
        product.setIdProduct(Long.parseLong(array[0]));
        product.setTitle(array[1]);
        product.setPrice(Double.parseDouble(array[2]));
        product.setQuantity(Double.parseDouble(array[3]));
        return product;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s",
                idProduct,
                title,
                price,
                quantity);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(idProduct, product.idProduct) && Objects.equals(title, product.title) && Objects.equals(price, product.price) && Objects.equals(quantity, product.quantity) && Objects.equals(createdAT, product.createdAT) && Objects.equals(updatedAT, product.updatedAT) && Objects.equals(products, product.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, title, price, quantity, createdAT, updatedAT, products);
    }
}
