package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qId;

    private String name;
    @ManyToMany
    private List<QA> qas;
    @OneToOne
    private Userclass owner;
    @OneToMany
    private List<Userclass> scores;

    private int lives;


    public Quiz(String name) {
        this.name = name;
    }
    public Quiz(String name, List<QA> qas) {
        this.name = name;
        this.qas = qas;
    }


    public Quiz() {
    }
}
