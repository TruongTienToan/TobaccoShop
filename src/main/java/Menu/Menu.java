package Menu;

import Utilities.AppUtils;
import Views.OrderView;
import Views.ProductView;
import Views.SortView;
import Views.UserView;

import java.util.Scanner;

public class Menu {
    static Scanner input = new Scanner(System.in);
    static ProductView productView = new ProductView();

    public static void tobaccoShop() {
        try {
            String choose;
            System.out.println();
            System.out.println("========== \uD83D\uDEAC Tobacco Shop \uD83D\uDEAC =========");
            System.out.println("\uD83D\uDEAC\uD83D\uDEAC️ 1.        Đăng nhập          \uD83D\uDEAC\uD83D\uDEAC️");
            System.out.println("\uD83D\uDEAC\uD83D\uDEAC️ 0.    Thoát chương trình     \uD83D\uDEAC\uD83D\uDEAC️");
            System.out.println("=======  \uD83D\uDEAC SIÊU THỊ THUỐC LÁ \uD83D\uDEAC  =======");
            System.out.println("Vui lòng chọn chức năng:");
            System.out.print("=>");
            do {
                choose = input.nextLine();

                switch (choose) {
                    case "1":
                        UserView.login();
                        break;
                    case "2":
                        UserView.signIn();
                        break;
                    case "0":
                        AppUtils.exit();
                        break;
                    default:
                        System.out.println("Không có chức năng, vui lòng chọn lại !");
                        System.out.print("=>");
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menuAdmin() {
        boolean check = true;
        String choose;

        System.out.println("=============== \uD83D\uDEAC Menu \uD83D\uDEAC ===============");
        System.out.println("\uD83C\uDF2B️     1. Quản lý sản phẩm              \uD83C\uDF2B️");
        System.out.println("\uD83C\uDF2B️     2. Quản lý đơn hàng              \uD83C\uDF2B️");
        System.out.println("\uD83C\uDF2B️     4. Quay lại màn hình đăng nhập   \uD83C\uDF2B️");
        System.out.println("========  \uD83D\uDEAC SIÊU THỊ THUỐC LÁ \uD83D\uDEAC  ========");
        System.out.println("Vui lòng chọn chức năng:");
        System.out.print("=>");

        do {
            choose = input.nextLine();
            switch (choose) {
                case "1":
                    menuManagerProduct();
                    break;
                case "2":
                    menuManagerOrder();
                    break;
                case "3":
                    menuManagerUsers();
                    break;
                case "4":
                    tobaccoShop();
                    break;
                default:
                    System.out.println("Không có chức năng, vui lòng chọn lại !");
                    System.out.print("=>");
                    check = false;
            }
        } while (!check);
    }

    public static void menuManagerProduct() {
        boolean check = true;
        String choose;

        System.out.println("=================\uD83D\uDEACQuản lý Đơn Hàng\uD83D\uDEAC================");
        System.out.println("\uD83C\uDF2B️              1. Thêm sản phẩm                 \uD83C\uDF2B");
        System.out.println("\uD83C\uDF2B️              2. Sửa thông tin sản phẩm        \uD83C\uDF2B");
        System.out.println("\uD83C\uDF2B️              3. Xóa sản phẩm                  \uD83C\uDF2B");
        System.out.println("\uD83C\uDF2B️              4. Hiển thị danh sách sản phẩm   \uD83C\uDF2B");
        System.out.println("\uD83C\uDF2B️              5. Tìm kiếm sản phẩm             \uD83C\uDF2B");
        System.out.println("\uD83C\uDF2B              6. Sắp xếp sản phẩm               \uD83C\uDF2B");
        System.out.println("\uD83C\uDF2B              0. Quay lại menu Admin            \uD83C\uDF2B");
        System.out.println("=============== \uD83D\uDEAC SIÊU THỊ THUỐC LÁ \uD83D\uDEAC  ================");
        System.out.println("Chọn chức năng : ");
        System.out.print("=>");

        do {
            choose = input.nextLine();
            switch (choose) {
                case "1":
                    productView.addProduct();
                    break;
                case "2":
                    productView.editProduct();
                    break;
                case "3":
                    productView.removeProduct();
                    break;
                case "4":
                    productView.showProductListShowOutMenu();
                    break;
                case "5":
                    productView.findProduct();
                    break;
                case "6":
                    SortView.menuSort();
                    break;
                case "0":
                    Menu.menuAdmin();
                    break;
                default:
                    System.out.println("Lựa chọn không đúng vui lòng nhập lại");
                    System.out.print("⟹");
                    check = false;
            }
        } while (!check);
    }

    public static void menuManagerUsers() {
        try {
            boolean flag = true;
            String choice;
            System.out.println("=================***Quản lý Người Dùng***================");
            System.out.println("****              1. Xem danh sách User              ****");
            System.out.println("****              2. Xóa User                        ****");
            System.out.println("****              3. Set quyền truy cập User         ****");
            System.out.println("****              0. Quay lại menu Admin             ****");
            System.out.println("===============  !!! SMOKING DOG !!!  ================");
            System.out.println("Chọn chức năng : ");
            System.out.println("==> ");
            do {
                choice = input.nextLine();
                switch (choice){
                    case "1":
                        UserView.showUserListChoice();
                        break;
                    case "2":
                        UserView.removeUser();
                        break;
                    case "3":
                        UserView.setRoleUser();
                        break;
                    case "0":
                        Menu.menuAdmin();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                        System.out.print("=> ");
                        flag = false;
                }
            }while (!flag);
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public static void menuManagerOrder(){
        try {
            boolean flag = true;
            String choice;
            System.out.println("=================***Quản lý Đơn Hàng***================");
            System.out.println("****              1. Tạo đơn hàng                  ****");
            System.out.println("****              2. Xem danh sách Order           ****");
            System.out.println("****              0. Quay lại menu Quản lí         ****");
            System.out.println("===============  *** SMOKING DOG *** ===============");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice){
                    case"1":
                        OrderView.addOrder();
                        break;
                    case "2":
                        OrderView.showListOrder();
                        break;
                    case "0":
                        menuAdmin();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập lại.");
                        System.out.print("=> ");
                        flag = false;
                }
            }while (!flag);
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
