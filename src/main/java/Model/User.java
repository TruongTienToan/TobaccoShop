package Model;

import java.time.Instant;

public class User {
    private Long idUser;
    private String userName;
    private String userPassword;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String role;
    private Instant timeCreate;

    public User() {

    }

    public User(Long idUser, String userName, String userPassword, String fullName, String phoneNumber, String email, String address, String role, Instant timeCreate) {
        this.idUser = idUser;
        this.userName = userName;
        this.userPassword = userPassword;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
        this.timeCreate = timeCreate;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Instant timeCreate) {
        this.timeCreate = timeCreate;
    }

    public static User parseUser(String rawUser) {
        String[] array = rawUser.split(",");
        User user = new User();
        user.setIdUser(Long.parseLong(array[0]));
        user.setUserName(array[1]);
        user.setUserPassword(array[2]);
        user.setFullName(array[3]);
        user.setPhoneNumber(array[4]);
        user.setEmail(array[5]);
        user.setAddress(array[6]);
        user.setRole(array[7]);
        user.setTimeCreate(Instant.parse(array[8]));

        return user;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                idUser,
                userName,
                userPassword,
                fullName,
                phoneNumber,
                email,
                address,
                role,
                timeCreate);
    }
}
