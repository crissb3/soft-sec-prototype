package master.prototype.SoftwareSecurity.repository;

import master.prototype.SoftwareSecurity.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("tagRepository")
public interface TagRepositoryImpl extends JpaRepository<Tag, Long> {
    Tag findBytId(@Param("tId")Long tId);
}
