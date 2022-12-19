package Views;

import Menu.Menu;
import Model.Product;
import Service.ProductServices;
import Utilities.AppUtils;
import Utilities.InstantUtils;
import jdk.jshell.Snippet;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    public List<Product> products;
    private static ProductServices productServices = new ProductServices();
    private static final Scanner input = new Scanner(System.in);

    public void addProduct() {
        boolean flag = true;
        do {
            System.out.println("===========> Thêm sản phẩm <===========");
            Long productID = System.currentTimeMillis() / 1000;
            String title = inputTitle(Status.ADD);
            double price = inputPrice(Status.ADD);
            double quantity = inputQuantity(Status.ADD);
            Product product = new Product(productID, title, price, quantity);
            productServices.addProduct(product);
            System.out.println("Đã thêm thành công");
            showProductListShow();
            Menu.menuManagerProduct();
        } while (!flag);
    }

    public Long inputIDProduct(Status status) {
        Long idProduct;
        switch (status) {
            case EDIT:
            case REMOVE:
                System.out.println("Nhập ID sản phẩm cần xóa: ");
                break;
        }
        System.out.print("==>");
        boolean flag = true;
        do {
            idProduct = AppUtils.retryParseLong();
            boolean exist = productServices.existsByID(idProduct);
            switch (status) {
                case EDIT:
                case REMOVE:
                    if (!exist) {
                        System.out.println("Không tìm thấy ID, vui lòng nhập lại");
                        System.out.print("==> ");
                    }
                    flag = !exist;
                    break;
            }
        } while (flag);
        return idProduct;
    }

    private double inputQuantity(Status status) {
        double quantity;
        switch (status) {
            case ADD:
                System.out.println("Nhập số lượng sản phẩm: ");
                break;
            case EDIT:
                System.out.println("Nhập số lượng sản phẩm cần sửa: ");
                break;
        }
        System.out.print("==> ");
        boolean flag = true;
        do {
            quantity = AppUtils.retryParseDouble();
            boolean exist = (quantity > 0);
            switch (status) {
                case ADD:
                case EDIT:
                case REMOVE:
                    if (!exist) {
                        System.out.println(" Bạn nhập sai định dạng ( Số lượng phải lớn hơn 1)");
                        System.out.print("==> ");
                    }
                    flag = !exist;
                    break;
            }
        } while (flag);
        return quantity;
    }

    public static String inputTitle(Status status) {
        String title;
        switch (status) {
            case ADD:
                System.out.println("Nhập tên sản phẩm");
                break;
            case EDIT:
                System.out.println("Nhập tên sản phẩm muốn sửa: ");
                break;
        }
        System.out.print("==> ");
        boolean flag = true;
        do {
            title = input.nextLine().trim();
            boolean exist = (!title.isEmpty()); //true
            switch (status) {
                case ADD:
                case EDIT:
                    if (!exist) {
                        System.out.println("Bạn nhập sai định dạng, vui lòng nhập lại: ");
                        System.out.print("==> ");
                    }
                    flag = !exist;
                    break;
            }
        } while (flag);
        return title;
    }

    public static double inputPrice(Status status) {
        double price;
        switch (status) {
            case ADD:
                System.out.println("Nhập giá sản phẩm : ");
                break;
            case EDIT:
                System.out.println("Nhập giá sản phẩm muốn sửa: ");
                break;
        }
        System.out.print("==> ");
        boolean flag = true;
        do {
            price = AppUtils.retryParseDouble();
            boolean exist = (price >= 1000); // sản phẩm phải có giá lớn hơn 1000đ
            switch (status) {
                case ADD:
                case EDIT:
                case REMOVE:
                    if (!exist) {
                        System.out.println("Nhập sai định dạng ( số tiền lớn hơn 1000đ");
                        System.out.print("==> ");
                    }
                    flag = !exist;
                    break;
            }
        } while (flag);
        return price;
    }

    public void editProduct() {
        try {
            showProductList();
            System.out.println("Nhập ID sản phẩm : ");
            System.out.print("==> ");
            Long id = AppUtils.retryParseLong();
            if (productServices.existsByID(id)) {
                boolean flag = true;
                System.out.println();
                System.out.println("╔═════════════════════════════════════════╗");
                System.out.println("║             ► Sửa Sản Phẩm ◄            ║");
                System.out.println("╠═════════════════════════════════════════╣");
                System.out.println("║       1.     Sửa tên sản phẩm           ║");
                System.out.println("║       2.     Sửa giá sản phẩm           ║");
                System.out.println("║       3.     Sửa số lượng sản phẩm      ║");
                System.out.println("║       0.     Quay lại quản lý sản phẩm  ║");
                System.out.println("╚═════════════════════════════════════════╝");
                System.out.println("Chọn chức năng: ");
                System.out.print("=> ");
                Product newProduct = new Product();
                newProduct.setIdProduct(id);
                do {
                    String choice = input.nextLine();
                    switch (choice) {
                        case "1":
                            editTitle(newProduct);
                            break;
                        case "2":
                            editPrice(newProduct);
                            break;
                        case "3":
                            editQuantity(newProduct);
                            break;
                        case "0":
                            Menu.menuManagerProduct();
                            break;
                        default:
                            System.out.println("Nhập sai vui lòng nhập lại : ");
                            System.out.print("==> ");
                            flag = false;
                    }
                } while (!flag);
            } else {
                System.out.println("Không tìm thấy ID sản phẩm");
                ContinueOrExit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editTitle(Product newProduct) {
        String title = inputTitle(Status.EDIT);
        newProduct.setTitle(title);
        productServices.update(newProduct);
        System.out.println("Tên sản phẩm đã được cập nhập");
        showProductList();
        ContinueOrExit();
    }

    public void editPrice(Product newProduct) {
        double price = inputPrice(Status.EDIT);
        newProduct.setPrice(price);
        productServices.update(newProduct);
        System.out.println("Giá sản phẩm đã được cập nhập");
        showProductList();
        ContinueOrExit();
    }

    public void editQuantity(Product newProduct) {
        double quantity = inputQuantity(Status.EDIT);
        newProduct.setQuantity(quantity);
        productServices.update(newProduct);
        System.out.println("Số lượng sẩn phẩm đã được cập nhập");
        showProductList();
        ContinueOrExit();
    }

    public void removeProduct() {
        try {
            boolean flag = true;
            showProductList();
            Long id = inputIDProduct(Status.REMOVE);
            System.out.println();
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║        ► Xóa Sản Phẩm ◄        ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║       1.     Đồng ý            ║");
            System.out.println("║       2.     Quay lại          ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                String choice = input.nextLine();
                switch (choice) {
                    case "1":
                        productServices.remove(id);
                        System.out.println("Sản phẩm đã được xóa");
                        showProductListShow();
                        Menu.menuManagerProduct();
                        break;
                    case "2":
                        Menu.menuManagerProduct();
                        break;
                    default:
                        System.out.println("Nhập sai, vui lòng nhập lại");
                        System.out.print("==> ");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void showProductList() {
        System.out.println();
        System.out.println("══════════════════════════════════════ Danh Sách Sản Phẩm ═════════════════════════════════════════");
        System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
        System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
        for (Product product : productServices.findAll()) {
            System.out.printf("%-25s %-20s %-20s %-20s\n",
                    product.getIdProduct(),
                    product.getTitle(),
                    InstantUtils.quantityProducts((product.getQuantity())),
                    InstantUtils.doubleToVND(product.getPrice()));
        }
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    public void showProductListShow() {
        showProductList();
        int choice;
        do {
            System.out.println("Nhận 0 để quay lại quản lí sản phẩm");
            System.out.print("==>");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
    }

    public void showProductListShowOutMenu() {
        showProductListShow();
        Menu.menuManagerProduct();
    }

    public void showProductListShowUser() {
        showProductListShow();
        Menu.menuManagerUsers();
    }

    public void findProduct() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║          ► Tìm kiếm sản phẩm ◄          ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║                                         ║");
            System.out.println("║       1.     Tìm theo tên sản phẩm      ║");
            System.out.println("║       2.     Tìm theo ID sản phẩm       ║");
            System.out.println("║       0.     Quay lại quản lý sản phẩm  ║");
            System.out.println("║                                         ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        findByTitle();
                        break;
                    case "2":
                        findByID();
                        break;
                    case "0":
                        Menu.menuManagerProduct();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, nhập lại: ");
                        System.out.print("==> ");
                        flag = false;
                }
            } while (flag);
            findProduct();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void findByTitle() {
        System.out.println("Nhập tên sản phẩm : ");
        System.out.print("==>");
        String title = input.nextLine().trim().toLowerCase();

        List<Product> products = productServices.findProductByTitle(title);
        if (products.size() != 0) {
            System.out.println();
            System.out.println("══════════════════════════════════ Sản phẩm bạn cần tìm là: ═══════════════════════════════════════");
            System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
            for (Product product : products) {
                System.out.printf("%-25s %-20s %-20s %-20s\n",
                        product.getIdProduct(),
                        product.getTitle(),
                        InstantUtils.quantityProducts(product.getQuantity()),
                        InstantUtils.doubleToVND(product.getPrice()));
            }
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
        } else {
            System.out.println();
            System.out.println("══════════════════════════════════ Sản phẩm bạn cần tìm là: ═══════════════════════════════════════");
            System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%60s", "Sản phẩm không được tìm thấy!!!\n");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
        }
        findProduct();
    }

    public void findByID() {
        System.out.println("Nhập ID sản phẩm muốn tìm");
        System.out.print("==> ");
        Long id = AppUtils.retryParseLong();
        Product product = productServices.findById(id);
        if (product != null) {
            System.out.println("══════════════════════════════════ Sản phẩm bạn cần tìm là: ═══════════════════════════════════════");
            System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%-25s %-20s %-20s %-20s\n",
                    product.getIdProduct(),
                    product.getTitle(),
                    InstantUtils.quantityProducts(product.getQuantity()),
                    InstantUtils.doubleToVND(product.getPrice()));
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm");
                System.out.println("==>");
                choice = AppUtils.retryParseInt();
            }
            while (choice != 0);
        } else {
            System.out.println();
            System.out.println("══════════════════════════════════ Sản phẩm bạn cần tìm là: ═══════════════════════════════════════");
            System.out.printf("%-25s %-20s %-20s %-20s\n", "ID", "Tên Sản Phẩm", "Số lượng", "Giá");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.printf("%60s", "Sản phẩm không được tìm thấy!!!\n");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════════════════════════");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
        }
        findProduct();
    }

    public void ContinueOrExit() {
        boolean is = true;
        do {
            System.out.println("Nhấn '1' để tiếp tục \t|\t '2' để trở về quản lý sản phẩm");
            System.out.print("==>");
            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    editProduct();
                    break;
                case "2":
                    Menu.menuManagerProduct();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, chọn lại");
                    is = false;
                    break;
            }
        } while (!is);
    }
}
