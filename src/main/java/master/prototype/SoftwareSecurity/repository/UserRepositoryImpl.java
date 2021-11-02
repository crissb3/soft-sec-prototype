package master.prototype.SoftwareSecurity.repository;

import master.prototype.SoftwareSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepositoryImpl extends JpaRepository<User, Long> {
    User findByUsername(@Param("username") String username);
    User findByEmail(@Param("email") String email);
}
