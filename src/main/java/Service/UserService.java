package Service;

import Model.User;
import Utilities.AppUtils;
import Utilities.CSVUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    private static UserService instanceUser;

    public UserService() {

    }

    public static UserService getInstance() {
        if (instanceUser == null) {
            instanceUser = new UserService();
        }
        return instanceUser;
    }

    @Override
    public List<User> findAllUser() {
        List<User> userList = new ArrayList<>();
        List<String> records = CSVUtils.readFile(AppUtils.PATH_USER);
        for (String record : records) {
            userList.add(User.parseUser(record));
        }
        return userList;
    }

    @Override
    public User loginUser(String userName, String userPassword) {
        List<User> userList = findAllUser();
        for (User users : userList) {
            if (users.getUserName().equals(userName) && users.getUserPassword().equals(userPassword)) {
                return users;
            }
        }
        return null;
    }

    @Override
    public void addUser(User newUser) {
        List<User> userList = findAllUser();
        userList.add(newUser);
        CSVUtils.writeFile(AppUtils.PATH_USER, userList);
    }

    @Override
    public void removeUser(Long idUser) {
        List<User> userList = findAllUser();
        userList.removeIf(id->id.getIdUser().equals(idUser));
        CSVUtils.writeFile(AppUtils.PATH_USER,userList);
    }

    @Override
    public void editUser(User newUser) {
        List<User> userList = findAllUser();
        for (User user : userList) {
            if (user.getIdUser().equals(newUser.getIdUser())) {
                String fullName = newUser.getFullName();
                if (fullName != null && !fullName.isEmpty()) {
                    user.setFullName(fullName);
                }
                String phone = newUser.getPhoneNumber();
                if (phone != null && !phone.isEmpty()) {
                    user.setPhoneNumber(phone);
                }
                String email = newUser.getEmail();
                if (email != null && !email.isEmpty()) {
                    user.setEmail(email);
                }
                String address = newUser.getAddress();
                if (address != null && !email.isEmpty()) {
                    user.setEmail(email);
                }
                String role = newUser.getRole();
                if (role != null && !role.isEmpty()) {
                    user.setRole(role);
                }
                user.setTimeCreate(Instant.now());
                CSVUtils.writeFile(AppUtils.PATH_USER, userList);
                break;
            }
        }
    }

    @Override
    public User findIdUser(Long idUser) {
        List<User> userList = findAllUser();
        for (User user: userList) {
            user.getIdUser().equals(idUser);
            return user;
        }
        return null;
    }

    @Override
    public boolean existUserID(Long idUser) {
        return findIdUser(idUser) !=null;
    }

    @Override
    public boolean existUserEmail(String email) {
        List<User> userList = findAllUser();
        for (User users: userList) {
            if (users.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existUserPhone(String phone) {
        List<User> userList = findAllUser();
        for (User user: userList) {
            if (user.getFullName().equals(phone)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existUserName(String nameUser) {
        List<User> userList = findAllUser();
        for (User user: userList) {
            if (user.getFullName().equals(nameUser)){
                return true;
            }
        }
        return false;
    }
}
