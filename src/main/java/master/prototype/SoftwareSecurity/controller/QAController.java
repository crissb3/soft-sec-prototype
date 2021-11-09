package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import master.prototype.SoftwareSecurity.entity.QA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class QAController {

    @Autowired
    private QAService qaService;

    @GetMapping("/populateTemporary")
    public String populateDBtemp() {
        List<String> answers = new ArrayList<>();
        answers.add("3");
        answers.add("4");
        answers.add("5");
        QA qa = new QA("What is 1+1?", "2", answers);
        answers.add(qa.getCorrectAnswer());
        Collections.shuffle(answers);
        qa.setAnswers(answers);
        qaService.save(qa);
        return "index";
    }

    @GetMapping("/searchQA")
    public String searchQA(@RequestParam String question, Model model) {

        List<QA> qas = qaService.findByWord(question);
        model.addAttribute("qas", qas);
        model.addAttribute("message", "Successfully searched for questions containing: "
                + question);

        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

    @GetMapping("/addQuestion")
    public String createQuestion(Model model) {
        List<QA> qas = qaService.findAll();  // This replaced with Description & Answer text from CAPEC/CWE/...
        model.addAttribute("qas", qas);
        model.addAttribute("message", "Create questions page....");
        return "addquestions";
    }

    @GetMapping("/deleteQ")
    public String deleteQtemp(Model model) {
        model.addAttribute("message", "Successfully deleted");
        qaService.deleteAll(); // TEMPORARY
        return "index";
    }

    @GetMapping("/addQA")
    public String addQAtest(@RequestParam String addquestion, @RequestParam String fakeanswers,
                            @RequestParam String correctanswer, Model model) {
        QA qa = new QA();
        qa.setQuestion(addquestion);
        qa.setCorrectAnswer(correctanswer);
        List<String> answers = new ArrayList<>();
        String[] fakes = fakeanswers.split(" ");
        for(String fake : fakes){
            answers.add(fake);
        }
        answers.add(qa.getCorrectAnswer());
        Collections.shuffle(answers);
        qa.setAnswers(answers);
        qaService.save(qa);

        model.addAttribute("message", "Created question: " + addquestion);
        return "index";
    }
}
