package master.prototype.SoftwareSecurity.repository;

import master.prototype.SoftwareSecurity.entity.QA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("qaRepository")
public interface QARepositoryImpl extends JpaRepository<QA, Long> {
    List<QA> findByQuestionContainingIgnoreCase(@Param("question") String question);
    QA findByQaId(@Param("qaId") Long qaId);
    List<QA> findByTagsIn(@Param("tags") Set<QA.Tags> tags);
}
