package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class QA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long qaId;

    @Lob
    private String question;
    private String correctAnswer;
    @ElementCollection
    private List<String> answers = new ArrayList<>();

    @ManyToOne
    private Quiz quiz;

    public QA(Long qaId, String question, String correctAnswer){
        this.qaId = qaId;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public QA(Long qaId, String question, String correctAnswer, List<String> answers){
        this.qaId = qaId;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
    public QA(String question, String correctAnswer, List<String> answers){
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

//    public QA(Long qaId, String question, String answer, Quiz quiz){
//        this.qaId = qaId;
//        this.question = question;
//        this.answer = answer;
//        this.quiz = quiz;
//    }

    public QA() {}
}
