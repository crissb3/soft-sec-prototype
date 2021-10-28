package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class QA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long qaId;

    @Lob
    private String question;
    private String answer;

    @ManyToOne
    private Quiz quiz;


    public QA(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

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
