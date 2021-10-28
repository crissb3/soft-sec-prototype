package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.repository.QARepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QAService {

    private QARepositoryImpl qaRepository;

    public QAService(QARepositoryImpl qaRepository) {
        this.qaRepository = qaRepository;
    }

    public List<QA> findByWord(String question){
        return qaRepository.findByQuestionContainingIgnoreCase(question);
    }
    public List<QA> findAll(){return qaRepository.findAll();}

    public QA save(QA newQA){
        qaRepository.save(newQA);
        return newQA;
    }
    public void deleteAll(){
        qaRepository.deleteAll();
    }
}
