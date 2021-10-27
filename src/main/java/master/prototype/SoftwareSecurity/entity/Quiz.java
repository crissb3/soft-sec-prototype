package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Quiz {
    @Id
    private Long qId;

    private String quizName;

    @OneToOne
    private User owner;

}
