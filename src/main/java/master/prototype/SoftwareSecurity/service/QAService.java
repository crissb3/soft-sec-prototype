package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.repository.QARepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QAService {

    private QARepositoryImpl qaRepository;

    public QAService(QARepositoryImpl qaRepository) {
        this.qaRepository = qaRepository;
    }

    public List<QA> findByWord(String question){
        return qaRepository.findByQuestionContainingIgnoreCase(question);
    }
    public List<QA> findAll(){return qaRepository.findAll();}

    public QA save(QA newQA){
        qaRepository.save(newQA);
        return newQA;
    }
    public void deleteAll(){
        qaRepository.deleteAll();
    }
    public QA findByQaId(Long qaId){return qaRepository.findByQaId(qaId);}

    public QA shuffleAnswers(String addquestion,
                             String fakeanswer1,
                             String fakeanswer2,
                             String fakeanswer3,
                             String correctanswer){
        QA qa = new QA();
        qa.setQuestion(addquestion);
        qa.setCorrectAnswer(correctanswer);
        List<String> answers = new ArrayList<>();
        answers.add(fakeanswer1);
        answers.add(fakeanswer2);
        answers.add(fakeanswer3);
        answers.add(correctanswer);
        Collections.shuffle(answers);
        qa.setAnswers(answers);
        return qa;
    }
}
