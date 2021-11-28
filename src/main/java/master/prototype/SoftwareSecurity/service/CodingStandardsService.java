package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.CodingStandards;
import master.prototype.SoftwareSecurity.repository.CodingStandardsRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodingStandardsService {
    private CodingStandardsRepositoryImpl codingStandardsRepository;

    public CodingStandardsService(CodingStandardsRepositoryImpl codingStandardsRepository) {
        this.codingStandardsRepository = codingStandardsRepository;
    }

    public List<CodingStandards> findAll(){return codingStandardsRepository.findAll();}
    public CodingStandards findById(int id){return codingStandardsRepository.findById(id);}
    public List<CodingStandards> findByName(String name){return codingStandardsRepository.findByNameContainingIgnoreCase(name);}
    public List<CodingStandards> findByDescription(String description)
    {return codingStandardsRepository.findByDescriptionContainingIgnoreCase(description);}

    public List<CodingStandards> saveAll(List<CodingStandards> newCodingStandards){
        for(CodingStandards codingStandard : newCodingStandards){
            codingStandardsRepository.save(codingStandard);
        }
        return newCodingStandards;
    }

    public CodingStandards save(CodingStandards newCodingStandard){
        codingStandardsRepository.save(newCodingStandard);
        return newCodingStandard;
    }

}
