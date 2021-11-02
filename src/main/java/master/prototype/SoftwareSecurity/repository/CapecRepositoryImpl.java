package master.prototype.SoftwareSecurity.repository;

import master.prototype.SoftwareSecurity.entity.Capec;
import master.prototype.SoftwareSecurity.entity.QA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CapecRepository")
public interface CapecRepositoryImpl extends JpaRepository<Capec, Integer> {
    List<Capec> findByDescriptionContainingIgnoreCase(@Param("description") String description);
    List<Capec> findByNameContainingIgnoreCase(@Param("name")String name);
    List<Capec> findByMitigationContainingIgnoreCase(@Param("mitigation")String mitigation);

}
