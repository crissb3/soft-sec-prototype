//package master.prototype.SoftwareSecurity.repository;
//
//import master.prototype.SoftwareSecurity.entity.CodingStandards;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository("codingStandardsRepository")
//public interface CodingStandardsRepositoryImpl extends JpaRepository<CodingStandards, Integer> {
//    List<CodingStandards> findByDescriptionContainingIgnoreCase(@Param("description") String description);
//    List<CodingStandards> findByMitigationContainingIgnoreCase(@Param("mitigation") String mitigation);
//    List<CodingStandards> findByNameContainingIgnoreCase(@Param("name") String name);
//    CodingStandards findById(@Param("id")int id);
//
//}
