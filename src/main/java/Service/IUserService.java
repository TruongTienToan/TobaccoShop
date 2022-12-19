package Service;

import Model.User;

import java.util.List;

public interface IUserService {
    List<User> findAllUser();

    User loginUser(String userName, String userPassword);

    void addUser(User newUser);

    void removeUser(Long idUser);

    void editUser(User newUser);

    User findIdUser(Long idUser);

    boolean existUserID(Long idUser);

    boolean existUserEmail(String email);

    boolean existUserPhone(String phone);

    boolean existUserName(String nameUser);
}
