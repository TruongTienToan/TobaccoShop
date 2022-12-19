package Views;

import Menu.Menu;
import Model.Product;
import Service.ProductServices;
import Service.sortService.*;
import Utilities.AppUtils;
import Utilities.InstantUtils;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class SortView {
    private static ProductServices productServices = new ProductServices();
    static Scanner input = new Scanner(System.in);

    public static void menuSort() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Sắp Xếp Sản Phẩm ◄                  ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     Sắp xếp theo ID sản phẩm                  ║");
            System.out.println("║       2.     Sắp xếp theo tên sản phẩm                 ║");
            System.out.println("║       3.     Sắp xếp theo giá sản phẩm                 ║");
            System.out.println("║       0.     Quay lại quản lý sản phẩm                 ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        sortProductByID();
                        break;
                    case "2":
                        sortProductByName();
                        break;
                    case "3":
                        sortProductByPrice();
                        break;
                    case "0":
                        Menu.menuManagerProduct();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng chọn lại");
                        System.out.print("=> ");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private static void sortProductByPrice() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Sắp Xếp Theo Giá ◄                  ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     Giá sản phẩm tăng dần                     ║");
            System.out.println("║       2.     Giảm sản phẩm giảm dần                    ║");
            System.out.println("║       0.     Quay lại xắp xếp sản phẩm                 ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        sortPriceASC();
                        break;
                    case "2":
                        sortPriceDESC();
                        break;
                    case "0":
                        menuSort();
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        System.out.print("==>");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private static void sortPriceDESC() {
        List<Product> products = productServices.findAll();
        SortByPriceASC sortByPriceASC = new SortByPriceASC();
        products.sort(sortByPriceASC);
        showSortList(products);
        sortProductByPrice();
    }

    private static void sortPriceASC() {
        List<Product> products = productServices.findAll();
        SortByPriceASC sortByPriceASC = new SortByPriceASC();
        products.sort(sortByPriceASC);
        showSortList(products);
        sortProductByPrice();
    }

    private static void showSortList(List<Product> products) {
        System.out.println();
        System.out.println("══════════════════════════════════════ Danh Sách Sản Phẩm ═════════════════════════════════════════");
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
            System.out.println("Nhấn 0 để quay lại SortMenu");
            System.out.print("==> ");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
    }

    private static void sortProductByName() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Sắp Xếp Theo Tên ◄                  ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     Tên sản phẩm A -> Z                       ║");
            System.out.println("║       2.     Tên sản phẩm Z -> A                       ║");
            System.out.println("║       0.     Quay lại xắp xếp sản phẩm                 ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        sortTitleASC();
                        break;
                    case "2":
                        sortTitleDESC();
                        break;
                    case "0":
                        menuSort();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        System.out.print("==>");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private static void sortTitleDESC() {
        List<Product> products = productServices.findAll();
        SortByTitleDESC sortByTitleDESC = new SortByTitleDESC();
        products.sort(sortByTitleDESC);
        showSortList(products);
        sortProductByName();
    }

    private static void sortTitleASC() {
        List<Product> products = productServices.findAll();
        SortByTitleASC sortByTitleASC = new SortByTitleASC();
        products.sort(sortByTitleASC);
        showSortList(products);
        sortProductByName();
    }

    private static void sortProductByID() {
        try {
            boolean flag = true;
            String choice;
            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║                  ► Sắp Xếp Theo ID ◄                   ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║       1.     ID sản phẩm tăng dần                      ║");
            System.out.println("║       2.     ID sản phẩm giảm dần                      ║");
            System.out.println("║       0.     Quay lại xắp xếp sản phẩm                 ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        sortByIdASC();
                        break;
                    case "2":
                        sortByIdDSC();
                        break;
                    case "0":
                        menuSort();
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        System.out.print("==>");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private static void sortByIdDSC() {
        List<Product> products = productServices.findAll();
        SortByIdDESC sortByIdDESC = new SortByIdDESC();
        products.sort(sortByIdDESC);
        showSortList(products);
        sortProductByID();
    }

    private static void sortByIdASC() {
        List<Product> products = productServices.findAll();
        SortByIdASC sortByIdASC = new SortByIdASC();
        products.sort(sortByIdASC);
        showSortList(products);
        sortProductByID();
    }
}
