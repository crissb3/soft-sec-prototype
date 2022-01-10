package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Userclass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String username;
    private String email;
    private int score;
    private boolean isAdmin;

    public Userclass(Long uid, String username, String email, int score, boolean isAdmin){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.score = score;
        this.isAdmin = isAdmin;
    }

    public Userclass() {}
}
