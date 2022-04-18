package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.entity.Userclass;
import master.prototype.SoftwareSecurity.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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
        if(user.getScore()>=20){
            score = 20;
            if(user.getScore()>=40){
                score = 40;
                if(user.getScore()>=60){
                    score = 60;
                    if(user.getScore()>=80){
                        score = 80;
                        if(user.getScore()==100){
                            score = 100;
                        }
                    }
                }
            }
        }
        if(quiz.getQas().size()>2 && user.getScore()<20){
            user.setScore(0);
            return user;
        }
        if(quiz.getQas().size()==(user.getScore()/10)){
            return user;
        }
        user.setScore(score);
        return user;
    }

    public Userclass answeredList(Userclass user, int page){
        List<Integer> ansList;
        if(user.getAnswered() == null){
            ansList = new ArrayList<>();
        }
        else{
            ansList = user.getAnswered();
        }
        if(!ansList.contains(page)){
            ansList.add(page);
        }
        user.setAnswered(ansList);
        return user;
    }


}
