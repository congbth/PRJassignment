package model;

public class User {
    private int id;
    private String username;
    private String fullname;
    private int departmentId;
    private String roleName;

    public User() {}

    public User(int id, String username, String fullname, int departmentId, String roleName) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.departmentId = departmentId;
        this.roleName = roleName;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
