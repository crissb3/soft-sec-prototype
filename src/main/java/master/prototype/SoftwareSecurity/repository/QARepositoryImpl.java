package master.prototype.SoftwareSecurity.repository;

import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qaRepository")
public interface QARepositoryImpl extends JpaRepository<QA, Long> {
    List<QA> findByQuestionContainingIgnoreCase(@Param("question") String question);
    QA findByQaId(@Param("qaId") Long qaId);
    List<QA> findByCustomtagsIn(List<Tag> tagsList);
}
