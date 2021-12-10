package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.Capec;
import master.prototype.SoftwareSecurity.entity.CodingStandards;
import master.prototype.SoftwareSecurity.service.CapecService;
import master.prototype.SoftwareSecurity.service.CodingStandardsService;
import master.prototype.SoftwareSecurity.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import master.prototype.SoftwareSecurity.entity.QA;

import java.util.List;

@Controller
public class QAController {

    @Autowired
    private QAService qaService;
    @Autowired
    private CapecService capecService;
    @Autowired
    private CodingStandardsService codingStandardsService;

    @GetMapping("/searchQA")
    public String searchQA(@RequestParam String question, Model model) {

        List<QA> qas = qaService.findByWord(question);
        model.addAttribute("qas", qas);
        model.addAttribute("message", "Successfully searched for questions containing: "
                + question);

        return "index";
    }

    @GetMapping("/addQuestion")
    public String createQuestion(Model model) {

        model.addAttribute("message", "Create questions page....");
        return "addquestions";
    }

    @GetMapping("/deleteQ")
    public String deleteQtemp(Model model) {
        model.addAttribute("message", "Successfully deleted");
        qaService.deleteAll(); // TEMPORARY
        return "index";
    }

//    @GetMapping("/addQA")
//    public String allCapec(){
//        return "testcapec";
//    }
    @PostMapping("/addQAcapec")
    public String addQAcapec(@RequestParam String addquestion,
                            @RequestParam String fakeanswer1,
                            @RequestParam String fakeanswer2,
                            @RequestParam String fakeanswer3,
                            @RequestParam String correctanswer,
                            @RequestParam String id,
                            Model model) {

        if(addquestion.isEmpty()
                || fakeanswer1.isEmpty()
                || fakeanswer2.isEmpty()
                || fakeanswer3.isEmpty()
                || correctanswer.isEmpty()) {
            Capec capec = capecService.findById(Integer.valueOf(id));
            model.addAttribute("capec", capec);
            model.addAttribute("message", "You can't leave an empty field.");
            return "createquestioncapec";
        }
        else {
            QA qa = qaService.shuffleAnswers(addquestion, fakeanswer1, fakeanswer2, fakeanswer3, correctanswer);
            qaService.save(qa);

            model.addAttribute("message", "Created question: " + addquestion);
        }
        List<Capec> capecs = capecService.findAll();
        model.addAttribute("capecs", capecs);


        return "capeccreate";
    }

    @PostMapping("/addQAcodingstandard")
    public String addQAcodingstandard(
                            @RequestParam String addquestion,
                            @RequestParam String fakeanswer1,
                            @RequestParam String fakeanswer2,
                            @RequestParam String fakeanswer3,
                            @RequestParam String correctanswer,
                            @RequestParam String id,
                            Model model) {

        if(addquestion.isEmpty()
                || fakeanswer1.isEmpty()
                || fakeanswer2.isEmpty()
                || fakeanswer3.isEmpty()
                || correctanswer.isEmpty()) {
            CodingStandards codingStandard = codingStandardsService.findById(Integer.valueOf(id));
            model.addAttribute("codingStandard", codingStandard);
            model.addAttribute("message", "You can't leave an empty field.");
            return "createquestioncodingstandard";
        }
        else {
            QA qa = qaService.shuffleAnswers(addquestion, fakeanswer1, fakeanswer2, fakeanswer3, correctanswer);
            qaService.save(qa);

            model.addAttribute("message", "Created question: " + addquestion);
        }
        List<CodingStandards> codingStandards = codingStandardsService.findAll();
        model.addAttribute("codingStandards", codingStandards);


        return "javacodingcreate";
    }
}
