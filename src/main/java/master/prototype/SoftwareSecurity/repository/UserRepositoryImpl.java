package master.prototype.SoftwareSecurity.repository;

import master.prototype.SoftwareSecurity.entity.Userclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepositoryImpl extends JpaRepository<Userclass, Long> {
    Userclass findByUsername(@Param("username") String username);
    Userclass findByEmail(@Param("email") String email);
}
