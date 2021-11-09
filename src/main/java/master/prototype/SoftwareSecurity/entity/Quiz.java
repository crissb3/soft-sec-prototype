package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long qId;

    private String name;
    @OneToMany
    private List<QA> qas;

    @OneToOne
    private User owner;

    public Quiz(String name) {
        this.name = name;
    }
    public Quiz(Long qId, String quizName, List<QA> qas) {
        this.qId = qId;
        this.name = name;
        this.qas = qas;
    }

    public Quiz() {
    }
}
