package Model;

public enum Role {
    ADMIN("ADMIN"), USER("USER");
    private String value;

    Role(String value) {
        this.value = value;
    }

    public static Role parseRole(String rawRole) {
        Role[] values = values();
        for (Role role : values) {
            if (role.value.equals(values)) {
                return role;
            }
        }
        throw new IllegalArgumentException("invalid");
    }
}
