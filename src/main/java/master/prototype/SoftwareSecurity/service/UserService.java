package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.User;
import master.prototype.SoftwareSecurity.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepositoryImpl userRepository;


    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }
    public User save(User user){
        userRepository.save(user);
        return user;
    }


}
