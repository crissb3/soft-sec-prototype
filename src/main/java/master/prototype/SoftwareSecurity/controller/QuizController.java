package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.service.QAService;
import master.prototype.SoftwareSecurity.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private QAService qaService;

    @GetMapping("/Quiz")
    public String newQuiz(Model model) {

        model.addAttribute("message", "Create new quiz");
        List<QA> qas = qaService.findAll();
        model.addAttribute("qas", qas);
        return "newquiz";
    }


    @GetMapping("/Quiz/Search")
    public String searchQuiz(Model model, @RequestParam String quizname) {
        List<Quiz> quizzes = quizService.findByName(quizname);
        List<QA> qas  = qaService.findAll();

        model.addAttribute("qas", qas);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("message", "Searched for quiz with name:" +quizname);

        return "newquiz";
    }


    @GetMapping("/Quiz/Create")
    public String createQuiz(Model model, @RequestParam String addname) {
        if(!addname.isEmpty()){
            Quiz quiz = new Quiz(addname);
            quizService.save(quiz);
            model.addAttribute("quizzes", quiz);
            model.addAttribute("message", "Created quiz with name: " + addname);
        }
        else{
            model.addAttribute("message", "Can't create quiz without a name.");
        }
        List<QA> qas = qaService.findAll();
        model.addAttribute("qas", qas);

        return "newquiz";
    }
//    @PostMapping("/Quiz/Create")
//    public String addQuestionsToQuiz(@ModelAttribute("quiz") Quiz quiz){
//        System.out.println(quiz);
//        return "newquiz";
//    }
    @GetMapping("/testquiz")
    public String gettestquiz(Model model){
        Quiz quiz = new Quiz("test");
        List<QA> qas = new ArrayList<>();
        qas.add(new QA(1L,"What is 2+2?","4"));
        qas.add(new QA(2L, "What is 3+3?","6"));
        qas.add(new QA(3L, "What is 4+4?", "8"));
        for(QA qa : qas){
            qaService.save(qa);
        }
        model.addAttribute("quiz", quiz);
        model.addAttribute("qas", qas);

        return "quiztest";
    }
    @PostMapping("/testquiz")
    public String testquiz(@ModelAttribute("quiz") Quiz quiz){

        System.out.println(quiz);
        return "quiztest";
    }
}
