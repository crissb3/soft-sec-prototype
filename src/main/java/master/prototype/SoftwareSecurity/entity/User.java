package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private String uId;

    private String username;
    private int score;
    private boolean isAdmin;

    public User(String uId, String username, int score, boolean isAdmin){
        this.uId = uId;
        this.username = username;
        this.score = score;
        this.isAdmin = isAdmin;
    }

    public User() {}
}
