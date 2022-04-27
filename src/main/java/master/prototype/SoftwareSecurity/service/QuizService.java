package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.entity.Userclass;
import master.prototype.SoftwareSecurity.repository.QuizRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {
    private QuizRepositoryImpl quizRepository;

    public QuizService(QuizRepositoryImpl quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizByOwner(Userclass userclass) {
        return quizRepository.findByOwner(userclass);
    }

    public Quiz save(Quiz newQuiz){
        quizRepository.save(newQuiz);
        return newQuiz;
    }
    public List<Quiz> findAll(){return quizRepository.findAll();}

    public List<Quiz> findByName(String name){return quizRepository.findByNameContainingIgnoreCase(name);}
    public Quiz findByqId(Long qId){return quizRepository.findByqId(qId);}

    public void deleteByqId(Long qId){quizRepository.deleteById(qId);}
    public Quiz shuffleAnswersPlay(Long quizId){
        Quiz quiz = quizRepository.findByqId(quizId);
        for(QA qa : quiz.getQas()){
            List<String> answers = new ArrayList<>(qa.getAnswers());
            Collections.shuffle(answers);
            qa.setAnswers(answers);
        }
        return quiz;
    }
}
