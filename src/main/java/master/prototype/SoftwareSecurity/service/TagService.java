package master.prototype.SoftwareSecurity.service;

import master.prototype.SoftwareSecurity.entity.Tag;
import master.prototype.SoftwareSecurity.repository.TagRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private TagRepositoryImpl tagRepository;

    public TagService(TagRepositoryImpl tagRepository) {
        this.tagRepository = tagRepository;
    }
    public List<Tag> findAll(){return tagRepository.findAll();}
    public Tag save(Tag newTag){
        tagRepository.save(newTag);
        return newTag;
    }
    public Tag findBytId(Long tId){return tagRepository.findBytId(tId);}
}
