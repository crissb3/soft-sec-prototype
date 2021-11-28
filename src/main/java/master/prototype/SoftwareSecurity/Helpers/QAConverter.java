package master.prototype.SoftwareSecurity.Helpers;

import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QAConverter implements Converter<Long, QA> {

    @Autowired
    QAService qaService;
    @Override
    public QA convert(Long qaId) {
        System.out.println("Trying to convert QA id: "+qaId+" into Q/A object");
        return qaService.findByQaId(qaId);
    }
}
