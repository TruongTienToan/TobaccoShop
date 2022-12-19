package Views;

import Menu.Menu;
import Model.ItemOrder;
import Model.Order;
import Model.Product;
import Service.ItemOrderService;
import Service.OderService;
import Service.ProductServices;
import Utilities.AppUtils;
import Utilities.InstantUtils;
import Utilities.ValidateUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    public List<Order> orders;
    private static OderService orderService = new OderService();
    private static ProductServices productServices = new ProductServices();

    private static ItemOrderService itemOrderService = new ItemOrderService();
    private static ProductView productView = new ProductView();
    private static final Scanner input = new Scanner(System.in);

    public OrderView() {
        orderService = OderService.getInstance();
        productServices = ProductServices.getInstance();
        itemOrderService = ItemOrderService.instanceItemOrder();
    }

    public static void addOrder() {
        try {
            itemOrderService.findAllItemOrder();
            Long idOrder = System.currentTimeMillis() / 1000;
            System.out.println("Nhập tên người đặt hàng (Tên không có dấu) : ");
            System.out.print("==>");
            String fullName;
            while (!ValidateUtils.isNameValid(fullName = input.nextLine())) {
                System.out.println("Tên" + fullName + "không đúng định dạng." + "Nhập lại :");
                System.out.print("==> ");
            }
            System.out.println("Nhập số điện thoại : ");
            System.out.print("==> ");
            String NumPhone = input.nextLine();
            while (!ValidateUtils.isPhoneValid(NumPhone) || NumPhone.trim().isEmpty()) {
                System.out.println("Số điện thoại " + NumPhone + "không đúng định dạng, nhập lại.");
                System.out.println("==> ");
                NumPhone = input.nextLine();
            }
            System.out.println("Nhập địa chỉ của bạn : ");
            System.out.print("==> ");
            String address = input.nextLine();
            while (address.trim().isEmpty()) {
                System.out.println("Địa chỉ của bạn không được để trống.");
                System.out.println("==> ");
                address = input.nextLine();
            }
            Order order = new Order(idOrder, fullName, NumPhone, address, Instant.now());
            List<ItemOrder> itemOrders = addItemsOrder(idOrder);
            double total = 0;
            for (ItemOrder itemOrder : itemOrders) {
                itemOrderService.addItemOrder(itemOrder);
                total += itemOrder.getTotal();
            }
            order.setGrandTotal(total);
            orderService.add(order);
            confirmOrder(order);
        } catch (Exception e) {
            System.out.println("Không đúng ! Nhập lại.");
        }
    }

    public static void confirmOrder(Order order) {
        try {
            boolean flag = true;
            String choice;
            System.out.println("Đã tạo order thành công:");
            System.out.println();
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║               ► Đơn Hàng ◄              ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║       1.     In bill                    ║");
            System.out.println("║       2.     Quay lại                   ║");
            System.out.println("║       0.     Exit                       ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        showBillAdmin(order);
                        break;
                    case "2":
                        Menu.menuManagerOrder();
                        break;
                    case "0":
                        System.exit(5);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, chọn lại :");
                        System.out.print("==>");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static List<ItemOrder> addItemsOrder(Long id) {
        List<ItemOrder> itemOrders = new ArrayList<>();
        productView.showProductList();
        System.out.println("Nhập số lương sản phẩm mà bạn muốn mua: ");
        System.out.print("==> ");
        int choice = Integer.parseInt(input.nextLine());
        while (choice < 0) {
            System.out.println("Số lượng sản phẩm không hợp lệ ( số lượng phải >0)");
            System.out.print("==> ");
            choice = Integer.parseInt(input.nextLine());
        }
        int count = 0;
        do {
            try {
                itemOrders.add(addItemsOrders(id));
                count++;
            } catch (Exception e) {
                System.out.println("Không đúng, nhập lại");
            }
        } while (count < choice);
        return itemOrders;
    }

    public static ItemOrder addItemsOrders(Long idOrder) {
        do {
            try {
//                itemOrderService.findAllItemOrder();
                productView.showProductList();
                Long id = System.currentTimeMillis() / 1000;
                System.out.println("Nhập ID sản phẩm bạn muốn mua");
                System.out.print("==> ");
                Long idProduct = Long.parseLong(input.nextLine());
                while (!productServices.existsByID(idProduct)) {
                    System.out.println("ID sản phẩm không tồn tại, nhập lại :");
                    System.out.print("==> ");
                    idProduct = Long.parseLong(input.nextLine());
                }
                Product product = productServices.findById(idProduct);
                Double price = product.getPrice();
                System.out.println("Nhập số lượng bạn muốn mua: ");
                System.out.print("==> ");
                Double quantity = Double.parseDouble(input.nextLine());
                while (!checkQuantityProduct(product, quantity)) {
                    System.out.println("Số lượng sản phẩm không đúng, nhập lại: ");
                    System.out.println("==> ");
                    quantity = Double.parseDouble(input.nextLine());
                    if (product.getQuantity() == 0) {
                        System.out.println("Sản phẩm hết hàng");
                        int choice;
                        do {
                            System.out.println("Nhấn 0 để quay lại trang quản lý sản phẩm ");
                            choice = AppUtils.retryParseInt();
                        } while (choice != 0);
                        Menu.menuManagerOrder();
                    }
                }
                String nameProduct = product.getTitle();
                Double total = quantity * price;
                Double grandTotal = 0.0;
                ItemOrder itemOrder = new ItemOrder(id, price, quantity, idOrder, idProduct, nameProduct, total, grandTotal);
                productServices.updateQuantity(idProduct, quantity);
                return itemOrder;
            } catch (Exception e) {
                System.out.println("Nhập lại ");
            }
        } while (true);
    }

    public static boolean checkQuantityProduct(Product product, Double quantity) {
        if (quantity <= product.getQuantity()) {
            return true;
        }
        return false;
    }

    public static void showBill(Order order) {
        try {
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                 HÓA ĐƠN                                   ║");
            System.out.println("╠═════════════════════╤═════════════════════════════════════════════════════╣");
            System.out.printf("║%-20s \t%-30s %16s ║\n", " Tên người đặt       │", order.getFullName(), "");
            System.out.printf("║%-20s \t%-30s %16s ║\n", " Số điện thoại       │", order.getMobile(), "");
            System.out.printf("║%-20s \t%-30s %16s ║\n", " Địa chỉ             │", order.getAddress(), "");
            System.out.printf("║%-20s \t%-30s %16s ║\n", " Ngày tạo đơn        │", InstantUtils.instantToString(order.getTimeCreatOrder()), "");
            System.out.println("╠════╤════════════════╧═════════════╤══════════════════╤════════════════════╣");
            System.out.printf("║%-3s │\t%-27s │\t%-14s │\t%-15s ║\n", "STT", "Tên sản phẩm", "Số lượng", "Giá");
            System.out.println("╟────┼──────────────────────────────┼──────────────────┼────────────────────╢");
            List<ItemOrder> itemOrders = itemOrderService.findAllItemOrder();
            double sum = 0;
            int count = 0;
            for (ItemOrder itemOrder : itemOrders) {
                if (itemOrder.getIdOrder().equals(order.getIdOrder())) {
                    sum += itemOrder.getTotal();
                    count++;
                    itemOrder.setGrandTotal(sum);
                    itemOrderService.update(itemOrder.getIdOrder(), itemOrder.getPrice(), sum);
                    System.out.printf("║ %-2s │\t%-27s │\t%-14s │\t%-15s ║\n",
                            count,
                            itemOrder.getNameProduct(),
                            InstantUtils.quantityProducts(itemOrder.getQuantity()),
                            InstantUtils.doubleToVND(itemOrder.getPrice()));
                    System.out.println("╟────┼──────────────────────────────┼──────────────────┼────────────────────╢");
                }
            }
            System.out.println("╟────┴──────────────────────────────┴──────────────────┴────────────────────╢");
            System.out.printf("║                                             Tổng tiền: %17s  ║\n", InstantUtils.doubleToVND(sum));
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
        } catch (Exception e) {
            System.out.println("Nhập không đúng, nhập  lại");
        }
    }

    public static void showBillAdmin(Order order) {
        showBill(order);
        int choice;
        do {
            System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
        Menu.menuManagerOrder();
    }

    public static void showBillUser(Order order) {
        showBill(order);
        int choice;
        do {
            System.out.println("Nhấn 0 để quay lại quản lý sản phẩm");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
        Menu.menuManagerOrder();
    }

    public static void showListOrder() {
        try {
            List<Order> orders = orderService.findAllOrder();
            List<ItemOrder> itemOrders = itemOrderService.findAllItemOrder();
            int count = 0;
            double printTotal = 0;
            double total = 0;
            double sum = 0;
            double grandTotal = 0;
            System.out.println();
            System.out.println("═══════════════════════════════════════════════════════════════════════ ***TOBACCO SHOP*** ══════════════════════════════════════════════════════════════════════════════════════════════════════");
            System.out.println("═══════════════════════════════════════════════════════════════════════   Danh Sách Hóa Đơn   ══════════════════════════════════════════════════════════════════════════════════════════════════════");
            for (Order order : orders) {
                System.out.println("\t╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.printf("\t║\t%-20s %-50s %-20s %-47s║\n", "Id            : ", order.getIdOrder(), "Tên khách hàng :", order.getFullName());
                System.out.printf("\t║\t%-20s %-50s %-20s %-47s║\n", "Số điện thoại : ", order.getMobile(), "Địa chỉ        : ", order.getAddress());
                System.out.println("\t╠═══════╤═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.printf("\t║\t%-2s │%-10s %-25s %-10s %-20s %-10s %-20s %-10s %-23s║\n", "STT", "", "Tên Sản Phẩm", "", "Số Lượng", "", "Giá", "", "Tổng Tiền Sản Phẩm");
                System.out.println("\t╟───────┼───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╢");
                for (ItemOrder itemOrder : itemOrders) {
                    if (itemOrder.getIdOrder().equals(order.getIdOrder())) {
                        count++;
                        total = itemOrder.getPrice() * itemOrder.getQuantity();
                        System.out.printf("\t║\t%-3s │%-10s %-25s %-10s %-20s %-10s %-20s %-10s %-23s║\n",
                                count, "",
                                itemOrder.getNameProduct(), " ",
                                InstantUtils.quantityProducts(itemOrder.getQuantity()), " ",
                                InstantUtils.doubleToVND(itemOrder.getPrice()), "",
                                InstantUtils.doubleToVND(total));
                        grandTotal += total;
                    }
                }
                printTotal += grandTotal;
                System.out.printf("\t╚═══════╧════════════════════════════════════════════════════════════════════════════════════════════════ Tổng Hóa Đơn:  %15s ═══════╝\n\n\n", InstantUtils.doubleToVND(grandTotal));
                sum = 0;
                grandTotal = 0;
                count = 0;
            }
            System.out.println("\t\t\t\t\t\t\t╔════════════════════════════════════════════════════════════════╗");
            System.out.printf("\t\t\t\t\t\t\t╟───────────── Tổng Doanh Thu: %20s ─────────────╢\n", InstantUtils.doubleToVND(printTotal));
            System.out.println("\t\t\t\t\t\t\t╚════════════════════════════════════════════════════════════════╝\n");
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại quản lý sản phẩm.");
                System.out.print("=> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
            Menu.menuManagerOrder();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void confirmOrderUser(Order order) {
        try {
            boolean flag = true;
            String choice;
            System.out.println("Đã tạo thành công.");
            System.out.println();
            System.out.println("╔═════════════════════════════════════════╗");
            System.out.println("║               ► Đơn Hàng ◄              ║");
            System.out.println("╠═════════════════════════════════════════╣");
            System.out.println("║       1.     In bill                    ║");
            System.out.println("║       2.     Quay lại                   ║");
            System.out.println("║       0.     Exit                       ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.println("Chọn chức năng: ");
            System.out.print("=> ");
            do {
                choice = input.nextLine();
                switch (choice) {
                    case "1":
                        showBillUser(order);
                        break;
                    case "2":
                        Menu.menuManagerUsers();
                        break;
                    case "0":
                        System.exit(5);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, chọn lại");
                        System.out.print("==>");
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
