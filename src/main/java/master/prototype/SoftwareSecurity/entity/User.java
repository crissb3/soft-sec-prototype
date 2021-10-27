package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private Long uId;

    private String username;
    private String email;
    private int score;
    private boolean isAdmin;

    public User(Long uId, String username, String email, int score, boolean isAdmin){
        this.uId = uId;
        this.username = username;
        this.email = email;
        this.score = score;
        this.isAdmin = isAdmin;
    }

    public User() {}
}
