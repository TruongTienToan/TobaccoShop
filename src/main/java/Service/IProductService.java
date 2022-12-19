package Service;

import Model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    Product findById(Long id);
    void searchByName(String tittle);

    List<Product> findProductByTitle(String title);

    boolean existsByID(long id);

    void addProduct(Product newProduct);

    void update(Product newProduct);

    void remove(Long idProduct);

    void updateQuantity(Long id, double quantity);
}
