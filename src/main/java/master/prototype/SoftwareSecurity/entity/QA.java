package master.prototype.SoftwareSecurity.entity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class QA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qaId;

    private String question;
    private String correctAnswer;
    @ElementCollection
    private List<String> answers;
    @ElementCollection
    private Set<Tags> tags;
    @ManyToMany
    private List<Tag> customtags;

    @Column(length = 10485760)
    @Lob
    @Type(type = "")
    private String img;

    public enum Tags {
        SOFTWARE_SECURITY("software_security"), NETWORK("network"), CODING_STANDARD("coding_standard");
        private String tag;

        Tags(String tag){
            this.tag = tag;
        }
        public String getTag(){
            return this.tag;
        }
    }

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

    public QA() {}
}
