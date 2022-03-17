package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Userclass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String username;
//    private String email;
    private int score;
//    private boolean isAdmin;
    private int lives;
    @ElementCollection
    private Set<String> lifelines;
    @ElementCollection
    private List<Integer> answered;

    public Userclass() {}
}
