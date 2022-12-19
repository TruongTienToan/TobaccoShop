package Views;

import Menu.Menu;
import Model.User;
import Service.UserService;
import Utilities.AppUtils;
import Utilities.InstantUtils;
import Utilities.ValidateUtils;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class UserView {
    public List<User> products;
    private static UserService userService = new UserService();
    static Scanner input = new Scanner(System.in);

    public static void signIn() {
        boolean flag = true;
        do {
            System.out.println();
            System.out.println("====== Tạo tài khoản ======");
            Long idUser = System.currentTimeMillis() / 1000;
            String userName = "BKR" + idUser;
            System.out.println("Mã tài khoản của bạn là : " + userName );
            String passWord = inputPassWord(Status.ADD);
            String fullName = inputFullName(Status.ADD);
            String phoneNumber = inputPhoneNumber(Status.ADD);
            String email = inputEmail(Status.ADD);
            String address = inputAddress(Status.ADD);
            String roleUser = inputRoleUser();
            User user = new User(idUser, userName, passWord, fullName, phoneNumber, email, address, roleUser, Instant.now());
            System.out.println("Đăng ký thành công.");
            userService.addUser(user);
            int choice;
            do {
                System.out.println("Nhấn 0 để quay lại màn hình chính");
                System.out.print("==> ");
                choice = AppUtils.retryParseInt();
            } while (choice != 0);
            Menu.tobaccoShop();
        } while (!flag);
    }

    public static void removeUser() {
        try {
            boolean flag = true;
            showUserList();
            Long id = inputIdUser(Status.REMOVE);
            System.out.println();
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║          ► Xóa User ◄          ║");
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
                        userService.removeUser(id);
                        System.out.println("Đã xóa User");
                        showUserListChoice();
                        Menu.menuManagerUsers();
                        break;
                    case "2":
                        Menu.menuManagerUsers();
                        break;
                    default:
                        System.out.println("Lựa chọn sai, vui lòng chọn lại");
                        System.out.print("==> ");
                        flag = false;
                }
            } while (!flag);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void setRoleUser() {
        try {
            showUserList();
            System.out.println("Nhập ID USER : ");
            System.out.println("==> ");
            Long id = AppUtils.retryParseLong();
            if (userService.existUserID(id)) {
                boolean flag = true;
                System.out.println();
                System.out.println("╔═════════════════════════════════════════╗");
                System.out.println("║             ► Set Role User ◄           ║");
                System.out.println("╠═════════════════════════════════════════╣");
                System.out.println("║       1.     Set quyền Admin            ║");
                System.out.println("║       2.     Set làm User               ║");
                System.out.println("║       0.     Quay lại quản lý Users     ║");
                System.out.println("╚═════════════════════════════════════════╝");
                System.out.println("Chọn chức năng: ");
                System.out.print("=> ");
                User newUser = new User();
                newUser.setIdUser(id);
                do {
                    String choice = input.nextLine();
                    switch (choice) {
                        case "1":
                            setAdmin(newUser);
                            break;
                        case "2":
                            setUser(newUser);
                            break;
                        case "0":
                            Menu.menuManagerUsers();
                            break;
                        default:
                            System.out.println("Lựa chọn sai, vui lòng chọn lại");
                            System.out.print("==> ");
                            flag = false;
                    }
                } while (!flag);
            } else {
                System.out.println("Không tìm thấy ID USER.");
                setRoleUser();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private static void setAdmin(User user) {
        user.setRole("Admin");
        userService.editUser(user);
        System.out.println("Cập nhập role user thành công");
        showUserListChoice();
        Menu.menuManagerUsers();
    }

    private static void setUser(User user) {
        user.setRole("User");
        userService.editUser(user);
        System.out.println("Cập nhập role user thành công");
        showUserListChoice();
        Menu.menuManagerUsers();
    }

    public static void showUserListChoice() {
        showUserList();
        int choice;
        do {
            System.out.println("Nhấn 0 để quay lại");
            System.out.print("=> ");
            choice = AppUtils.retryParseInt();
        } while (choice != 0);
        Menu.menuManagerUsers();
    }

    public static Long inputIdUser(Status status) {
        Long idUser;
        switch (status) {
            case EDIT:
            case REMOVE:
                System.out.println("Nhập ID cần xóa : ");
                break;
        }
        System.out.print("=> ");
        boolean flag = true;
        do {
            idUser = AppUtils.retryParseLong();
            boolean exist = userService.existUserID(idUser);
            switch (status) {
                case EDIT:
                case REMOVE:
                    if (!exist) {
                        System.out.print("Không tìm thấy ID, nhập lại: ");
                        System.out.print("=> ");
                    }
                    flag = !exist;
                    break;
            }
        } while (flag);
        return idUser;

    }

    public static void showUserList() {
        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════════════════════════════ Danh Sách Users ════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.printf("%-15s %-15s %-25s %-25s %-15s %-25s %-25s %-10s %-25s\n", "ID", "Username", "Password", "FullName", "Phone Number", "Email", "Address", "Role", "Time Creat");
        System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        for (User user : userService.findAllUser()) {
            System.out.printf("%-15s %-15s %-25s %-25s %-15s %-25s %-25s %-10s %-25s\n",
                    user.getIdUser(),
                    user.getUserName(),
                    user.getUserPassword(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getRole(),
                    InstantUtils.instantToString(user.getTimeCreate()));
        }
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    public static void ContinueOrExit() {
        boolean flag = true;
        do {
            System.out.println("Nhấn 1 để tiếp tục or nhấn 2 để thoát");
            System.out.print("=> ");
            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    break;
                case "2":
                    Menu.tobaccoShop();
                    break;
                default:
                    System.out.println("Lựa chọn không đúng vui lòng nhập lại");
                    flag = false;
                    break;
            }
        } while (!flag);
    }

    public static String inputRoleUser() {
        System.out.println("Nhập mã Admin");
        System.out.println("Không nhập thì bạn là USER");
        System.out.print("==> ");
        String setRole = input.nextLine();
        String role;
        if (setRole.equals("112233")) {
            role = "Admin";
        } else {
            role = "User";
        }
        return role;
    }

    public static String inputEmail(Status status) {
        String email;
        switch (status) {
            case ADD:
                System.out.println("Nhập Email của bạn: ");
                break;
            case EDIT:
                System.out.println("Nhập Email mới của bạn: ");
                break;
        }
        System.out.print("==> ");
        boolean flag = true;
        do {
            if (!ValidateUtils.isEmailValid(email = input.nextLine())) {
                System.out.println("Email " + email + "của bạn không đúng định dạng! Vui lòng kiểm tra và nhập lại ");
                System.out.println("Nhập email (VD: abcdef1234@gmail.com)");
                System.out.print("=> ");
                continue;
            }
            break;
        } while (flag);
        return email;

    }

    public static String inputAddress(Status status) {
        String address;
        switch (status) {
            case ADD:
                System.out.println("Nhập địa chỉ của bạn: ");
                break;
            case EDIT:
                System.out.println("Nhập địa chỉ mới của bạn: ");
                break;
        }
        System.out.print("==> ");
        boolean flag = true;
        do {
            address = input.nextLine().trim();
            boolean exist = (!address.isEmpty());
            switch (status) {
                case ADD:
                case EDIT:
                    if (!exist) {
                        System.out.println("Địa chỉ không được để trống, vui lòng nhập lại:");
                        System.out.print("=> ");
                    }
                    flag = !exist;
                    break;
            }
        } while (flag);
        return address;
    }

    public static String inputPhoneNumber(Status status) {
        String phone;
        switch (status) {
            case ADD:
                System.out.println("Nhập số điện thoại : ");
                break;
            case EDIT:
                System.out.println("Nhập số điện thoại muốn đổi: ");
                break;
        }
        System.out.print("==> ");
        boolean flag = true;
        do {
            phone = input.nextLine().trim();
            if (!ValidateUtils.isPhoneValid(phone)) {
                System.out.println("Số " + phone + " của bạn không đúng. Vui lòng nhập lại. (Số điện thoại bao gồm 10 -> 11 số và bắt đầu là số 0)");
                System.out.println("Nhập số điện thoại (VD: 0912345679)");
                System.out.print("=> ");
                continue;
            }
            break;
        } while (flag);
        return phone;
    }

    public static String inputFullName(Status status) {
        String fullName;
        switch (status) {
            case ADD:
                System.out.println("Nhập tên của bạn (VD : Quoc Cuong) :");
                break;
            case EDIT:
                System.out.println("Nhập tên bạn muốn đổi : ");
                break;
        }
        System.out.print("==> ");
        while (!ValidateUtils.isNameValid(fullName = input.nextLine())) {
            System.out.println("Tên " + fullName + " không đúng định dạng. (Tên không có dấu và viết hoa chữ đầu) ");
            System.out.print("==> ");
        }
        return fullName;
    }

    public static String inputPassWord(Status status) {
        String password;
        switch (status) {
            case ADD:
                System.out.println("Nhập mật khẩu của bạn từ 8 đến 16 kí tự : ");
                break;
        }
        System.out.print("==> ");
        boolean flag = true;
        do {
            password = input.nextLine().trim();
            boolean exist = (password.length() >= 8 && password.length() <= 16);
            switch (status) {
                case ADD:
                    if (!exist) {
                        System.out.println("Nhập sai định dạng, vui lòng nhập lại");
                        System.out.print("==> ");
                    }
                    flag = !exist;
                    break;
            }
        } while (flag);
        return password;
    }
    public static void login(){
        User users;
        do {
            try {
                System.out.println();
                System.out.println("══════════► Đăng Nhập ◄══════════");
                System.out.println("Vui lòng nhập mã số tài khoản:");
                System.out.print("==> ");
                String userName = input.nextLine();
                System.out.println("Vui lòng nhập mật khẩu : ");
                System.out.print("==>");
                String passWord = input.nextLine();
                users = userService.loginUser(userName,passWord);
                if (userName.equals("admin")&& passWord.equals("admin")){
                    System.out.println("Đăng nhập thành công");
                    Menu.menuAdmin();
                }else if (users.getUserName().equals(userName)&& users.getUserPassword().equals(passWord)){
                    if (users.getRole().equals("User")){
                        System.out.println("Đăng nhập thành công");
                        Menu.menuAdmin();
                    }else if (users.getRole().equals("Admin")){
                        System.out.println("Đăng nhập thành công");
                        Menu.menuManagerUsers();
                    }
                }
                break;
            }catch (Exception e){
                System.out.println("Tài khoản hoặc mật khẩu không đúng, vui lòng nhập lại");
                System.out.println("==> ");
                ContinueOrExit();
            }
        }while (true);
    }
}
