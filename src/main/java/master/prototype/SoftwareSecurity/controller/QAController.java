package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import master.prototype.SoftwareSecurity.entity.QA;

import java.util.List;

@Controller
public class QAController {

    @Autowired
    private QAService qaService;

    @GetMapping("/searchQA")
    public String searchQA(@RequestParam String question, Model model){
        QA qa = new QA("Fruit?", "Apple");
        QA qa1 = new QA("cc?", "Banana");
        QA qa2 = new QA("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc" +
                "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" +
                "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii", "answer");
        QA qa3 = new QA(4L, "Frua", "22");
        QA qa4 = new QA(5L, "qwe?", "12323");
        QA qa5 = new QA(6L, "Fruit?", "qwe");
        QA qa6 = new QA(7L, "Fruit?", "ewq");
        QA qa7 = new QA(8L, "badqwe?", "asd");
        QA qa8 = new QA("das?", "xesea");
        QA qa9 = new QA(10L, "weq?", "se");

        qaService.save(qa);
        qaService.save(qa1);
        qaService.save(qa2);
        qaService.save(qa3);
        qaService.save(qa4);
        qaService.save(qa5);
        qaService.save(qa6);
        qaService.save(qa7);
        qaService.save(qa8);
        qaService.save(qa9);

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

//    @GetMapping("/register")
//    public String registerUser(@PathVariable String )
}
