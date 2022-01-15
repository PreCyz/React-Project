package react.pw.carly.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Data
public class User {
    public final static String USER_TYPE_ADMIN="admin";
    public final static String USER_TYPE_SERVICE="service";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "userType")
    private String userType;
}
