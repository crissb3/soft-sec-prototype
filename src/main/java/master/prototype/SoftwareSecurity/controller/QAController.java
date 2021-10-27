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
        QA qa = new QA(1L, "Fruit?", "Apple");
        QA qa1 = new QA(2L, "Fruit?", "Banana");
        qaService.save(qa);
        qaService.save(qa1);
        List<QA> qas = qaService.findByWord(question);
        model.addAttribute("qas", qas);
        model.addAttribute("message", "Successfully searched for questions containing: "
                +question);

        return "index";
    }

    @PostMapping("/testAdd/{test}")
    public String testAdd(@PathVariable String test, Model model){
        return "index";
    }

//    @GetMapping("/register")
//    public String registerUser(@PathVariable String )
}
