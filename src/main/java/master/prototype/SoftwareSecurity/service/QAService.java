package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.repository.QARepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<QA> findByTags(Set<QA.Tags> tags){return qaRepository.findByTagsIn(tags);}
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
    public Set<QA.Tags> setTag(String tag1, String tag2, String tag3){
        Set<QA.Tags> tags = new HashSet<>();
        if(!(tag1==null)) tags.add(QA.Tags.valueOf(tag1));
        if(!(tag2==null)) tags.add(QA.Tags.valueOf(tag2));
        if(!(tag3==null)) tags.add(QA.Tags.valueOf(tag3));
        return tags;
    }
}
