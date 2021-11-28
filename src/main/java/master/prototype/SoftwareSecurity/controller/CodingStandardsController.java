package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.Capec;
import master.prototype.SoftwareSecurity.entity.CodingStandards;
import master.prototype.SoftwareSecurity.service.CodingStandardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CodingStandardsController {
    @Autowired
    private CodingStandardsService codingStandardsService;

    @GetMapping("/javaCodingStandards")
    public String javaCodingStandards(Model model){
        List<CodingStandards> codingStandards = codingStandardsService.findAll();
        model.addAttribute("codingStandards", codingStandards);

        return "javacodingcreate";
    }

    @GetMapping("/createQuestion/codingstandard/{id}")
    public String createCapecQuestion(Model model, @PathVariable int id) {
        CodingStandards codingStandard = codingStandardsService.findById(id);
        model.addAttribute("codingStandard", codingStandard);
        return "createquestioncodingstandard";
    }
    @GetMapping("/createQuestion/codingstandard/code/{id}")
    public String codeExample(Model model, @PathVariable int id) {
        CodingStandards codingStandard = codingStandardsService.findById(id);
        model.addAttribute("codingStandard", codingStandard);
        return "codingstandardcode";
    }
    @GetMapping("/searchCodingStandards")
    public String searchCodingStandardsByDescription(@RequestParam String description, Model model){
        List<CodingStandards> codingStandards = codingStandardsService.findByDescription(description);
        int size = codingStandards.size();
        model.addAttribute("codingStandards", codingStandards);
        model.addAttribute("message_hits", "Number of hits: "+size);
        model.addAttribute("message_searched", "Containing search word: " + description);

        return "javacodingcreate";
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createCodingStandardsObjects(){
        if(codingStandardsService.findAll().isEmpty()){
            CodingStandards codingStandard = new CodingStandards();
            List<CodingStandards> codingStandards = codingStandard.createCodingStandards();
            codingStandardsService.saveAll(codingStandards);
        }
    }
}
