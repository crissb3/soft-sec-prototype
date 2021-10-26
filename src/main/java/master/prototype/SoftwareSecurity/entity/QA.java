package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class QA {
    @Id
    private Long qaId;

    private String question;
    private String answer;

    @ManyToOne
    private Quiz quiz;

    public QA(Long qaId, String question, String answer){
        this.qaId = qaId;
        this.question = question;
        this.answer = answer;
    }

    public QA(Long qaId, String question, String answer, Quiz quiz){
        this.qaId = qaId;
        this.question = question;
        this.answer = answer;
        this.quiz = quiz;
    }

    public QA() {}
}
