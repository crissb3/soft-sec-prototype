package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.entity.Userclass;
import master.prototype.SoftwareSecurity.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepositoryImpl userRepository;


    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }
    public Userclass save(Userclass newUserclass){
        userRepository.save(newUserclass);
        return newUserclass;
    }
    public Userclass findUserById(Long uid){return userRepository.findByUid(uid);}

    public Userclass userScore(Userclass user, Quiz quiz){
        int score = user.getScore();
        if(user.getScore()>=30){
            score = 30;
            if(user.getScore()>=50){
                score = 50;
                if(user.getScore()>=80){
                    score = 80;
                    if(user.getScore()==100){
                        score = 100;
                    }
                }
            }
        }
        else if(quiz.getQas().size()>=3 && user.getScore()<30) user.setScore(0);
        user.setScore(score);
        return user;
    }


}
