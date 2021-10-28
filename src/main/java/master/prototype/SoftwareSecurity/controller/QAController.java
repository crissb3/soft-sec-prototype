package master.prototype.SoftwareSecurity.controller;

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

    @GetMapping("/populateTemporary")
    public String populateDBtemp(){
        QA qa = new QA("What is 1+1?", "2");
        QA qa1 = new QA("Fruit?", "Banana");
//        QA qa2 = new QA("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
//                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
//                "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc" +
//                "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
//                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
//                "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
//                "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
//                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" +
//                "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii", "answer");
        QA qa3 = new QA( "Language?", "Spanish");
        QA qa4 = new QA( "asd?", "qwe");
        QA qa5 = new QA( "test?", "test");
//        QA qa6 = new QA( "Fruit?", "ewq");
//        QA qa7 = new QA( "badqwe?", "asd");
//        QA qa8 = new QA("das?", "xesea");
//        QA qa9 = new QA( "weq?", "se");

        qaService.save(qa);
        qaService.save(qa1);
//        qaService.save(qa2);
        qaService.save(qa3);
        qaService.save(qa4);
        qaService.save(qa5);
//        qaService.save(qa6);
//        qaService.save(qa7);
//        qaService.save(qa8);
//        qaService.save(qa9);
        return "index";
    }
    @GetMapping("/searchQA")
    public String searchQA(@RequestParam String question, Model model){

        List<QA> qas = qaService.findByWord(question);
        model.addAttribute("qas", qas);
        model.addAttribute("message", "Successfully searched for questions containing: "
                +question);

        return "index";
    }

    @GetMapping("/test")
    public String test(Model model){
        return "test";
    }

    @GetMapping("/addQuestion")
    public String createQuestion(Model model){
        List<QA> qas = qaService.findAll();  // This replaced with Description & Answer text from CAPEC/CWE/...
        model.addAttribute("qas", qas);
        model.addAttribute("message", "Create questions page....");
        return "addquestions";
    }

    @GetMapping("/deleteQ")
    public String deleteQtemp(Model model){
        model.addAttribute("message", "Successfully deleted");
        qaService.deleteAll(); // TEMPORARY
        return "index";
    }

//    @GetMapping("/register")
//    public String registerUser(@PathVariable String )
}
