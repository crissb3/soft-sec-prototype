package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.Capec;
import master.prototype.SoftwareSecurity.service.CapecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CapecController {
    @Autowired
    private CapecService capecService;

    @GetMapping("/testCapec")
    public String testCapec(Model model){
        List<Capec> capecslist = capecService.findAll();  // This replaced with Description & Answer text from CAPEC/CWE/...
        model.addAttribute("capecs", capecslist);
        model.addAttribute("message", "Capec test page....");
        return "testcapec";
    }
    @GetMapping("/searchCapecDomainsOfAttack")
    public String searchCapecByDescription(@RequestParam String description, Model model){
        List<Capec> capecs = capecService.findByDescription(description);
        int size = capecs.size();
        model.addAttribute("capecs", capecs);
        model.addAttribute("message_hits", "Number of hits: "+size);
        model.addAttribute("message_searched", "Containing search word: " + description);

        return "testcapec";
    }
    @GetMapping("/CapecDomainsOfAttack")
    public String createQuestionCapecDomainsOfAttack(Model model){
        return "testcapec";
    }


    @EventListener(ApplicationReadyEvent.class)
    public void createCapecObjects(){
        if(capecService.findAll().isEmpty()){
            Capec capec = new Capec();
            List<Capec> capecs = capec.createCapecDomainsOfAttack();
            capecService.saveAll(capecs);
        }
    }
}
