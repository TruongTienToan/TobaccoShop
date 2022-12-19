package Service;

import Model.Product;
import Utilities.AppUtils;
import Utilities.CSVUtils;
import Utilities.InstantUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductServices implements IProductService {

    private static ProductServices instance;

    public ProductServices() {

    }

    public static ProductServices getInstance() {
        if (instance == null) {
            instance = new ProductServices();
        }
        return instance;
    }

    @Override
    public List<Product> findAll() {
        List<Product> listProduct = new ArrayList<>();
        List<String> records = CSVUtils.readFile(AppUtils.PATH_PRODUCT);
        for (String record : records) {
            listProduct.add(Product.parseProduct(record));
        }
        return listProduct;
    }

    @Override
    public Product findById(Long id) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getIdProduct().equals(id))
                return product;
        }
        return null;
    }

    @Override
    public void searchByName(String title) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getTitle().toLowerCase().replace("", "").equals(title.replace(" ", " "))) {
                System.out.printf("\n Sản phẩm bạn muốn tìm là : \n█  ID:  %s █ Tên sản phẩm :  %s █ Giá sản phẩm :  %s █ Số lượng :  %s █ Ngày tạo :  %s █ Ngày cập nhập:  %s  \\n",
                        product.getIdProduct(),
                        product.getTitle(),
                        InstantUtils.doubleToVND(product.getPrice()),
                        InstantUtils.quantityProducts(product.getQuantity()),
                        InstantUtils.instantToString(product.getCreatedAT()),
                        product.getUpdatedAT() == null ? "Chưa cập nhật" : InstantUtils.instantToString(product.getUpdatedAT()));
                return;
            }
        }
    }

    @Override
    public List<Product> findProductByTitle(String title) {
        List<Product> products = findAll();
        List<Product> listFind = new ArrayList<>();
        if (title != null) {
            for (Product product :products) {
                if (product.getTitle().toLowerCase().contains(title)) {
                    listFind.add(product);
                }
            }
        }
        return listFind;
    }

    @Override
    public boolean existsByID(long id) {
        return findById(id) != null;
    }

    @Override
    public void addProduct(Product newProduct) {
        List<Product> products = findAll();
        products.add(newProduct);
        CSVUtils.writeFile(AppUtils.PATH_PRODUCT, products);
    }

    @Override
    public void update(Product newProduct) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (newProduct.getIdProduct().equals(product.getIdProduct())) {
                String title = newProduct.getTitle();
                if (title != null && !title.isEmpty()) {
                    product.setTitle(newProduct.getTitle());
                }
                Double quantity = newProduct.getQuantity();
                if (quantity != null) {
                    product.setQuantity(quantity);
                }
                Double price = newProduct.getPrice();
                if (price != null) {
                    product.setPrice(price);
                }
                CSVUtils.writeFile(AppUtils.PATH_PRODUCT, productList);
                break;
            }
        }
    }

    @Override
    public void remove(Long idProduct) {
        List<Product> productList = findAll();
        productList.removeIf(id->id.getIdProduct().equals(idProduct));
        CSVUtils.writeFile(AppUtils.PATH_PRODUCT, productList);
    }

    @Override
    public void updateQuantity(Long id, double quantity) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if (product.getIdProduct().equals(id)) {
                Double oldQuantity = product.getQuantity();
                if (oldQuantity >= quantity) {
                    product.setQuantity(oldQuantity - quantity);
                    CSVUtils.writeFile(AppUtils.PATH_PRODUCT, productList);
                    break;
                }
            }
        }
    }
}
