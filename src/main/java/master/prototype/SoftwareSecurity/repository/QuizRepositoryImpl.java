package master.prototype.SoftwareSecurity.repository;

import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("quizRepository")
public interface QuizRepositoryImpl extends JpaRepository<Quiz, Long> {

    List<Quiz> findByNameContainingIgnoreCase(@Param("name") String name);
    List<Quiz> findByOwner(@Param("owner") User user);
}
