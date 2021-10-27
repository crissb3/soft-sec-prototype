package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.entity.User;
import master.prototype.SoftwareSecurity.repository.QuizRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private QuizRepositoryImpl quizRepository;

    public QuizService(QuizRepositoryImpl quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizByOwner(User user) {
        return quizRepository.findByOwner(user);
    }

    public Quiz save(Quiz newQuiz){
        quizRepository.save(newQuiz);
        return newQuiz;
    }
}
