package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.Capec;
import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.repository.CapecRepositoryImpl;
import master.prototype.SoftwareSecurity.repository.QARepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapecService {

    private CapecRepositoryImpl capecRepository;

    public CapecService(CapecRepositoryImpl capecRepository) {
        this.capecRepository = capecRepository;
    }

    public List<Capec> findByDescription(String description) {
        return capecRepository.findByDescriptionContainingIgnoreCase(description);
    }
    public List<Capec> findByName(String name) {
        return capecRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Capec> findByMitigation(String mitigation) {
        return capecRepository.findByMitigationContainingIgnoreCase(mitigation);
    }
    public List<Capec> findAll() {
        return capecRepository.findAll();
    }

    public List<Capec> saveAll(List<Capec> newCapecs){
        for(Capec capec : newCapecs){
            capecRepository.save(capec);
        }
        return newCapecs;
    }

    public Capec save(Capec newCapec){
        capecRepository.save(newCapec);
        return newCapec;
    }
}

