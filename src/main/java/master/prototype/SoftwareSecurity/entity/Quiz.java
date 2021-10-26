package master.prototype.SoftwareSecurity.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Quiz {
    @Id
    private Long qId;

}
