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

    @GetMapping("/Quiz/Create/Search")
    public String searchQuiz(Model model, @RequestParam String question) {
        List<QA> qas  = qaService.findByWord(question);
        Quiz quiz = new Quiz();
        model.addAttribute("qas", qas);
        model.addAttribute("quiz", quiz);

        return "newquiz";
    }


    @GetMapping("/Quiz/Create")
    public String createQuiz(Model model, @ModelAttribute String name) {
        Quiz quiz = new Quiz();
        if(!name.isEmpty()){
            quiz.setName(name);
            quizService.save(quiz);
        }
        model.addAttribute("quiz", quiz);

        List<QA> qas = qaService.findAll();
        model.addAttribute("qas", qas);

        return "newquiz";
    }
    @PostMapping("/Quiz/Create")
    public String addQuestionsToQuiz(@ModelAttribute("quiz") Quiz quiz, Model model){

        if(quiz.getName().isEmpty()){
            model.addAttribute("message", "Can't create quiz without a name.");
        }
        else{
            model.addAttribute("message", "Created quiz with name: " + quiz.getName());
            quizService.save(quiz);
        }
        return "index";
    }
    @GetMapping("Quiz/Select")
    public String selectQuiz(Model model){
        List<Quiz> quizzes = quizService.findAll();
        model.addAttribute("quizzes",quizzes);
        return "quizselect";
    }
    @GetMapping("/Quiz/Start/{id}")
    public String startQuiz(Model model, @PathVariable long id){

        Quiz quiz = quizService.findByqId(id);

        System.out.println(quiz.getName());
        System.out.println(quiz.getQas());
        System.out.println(quiz.getQas().get(0).getQuestion());
        System.out.println(quiz.getQas().get(0).getAnswers());
        model.addAttribute("quiz", quiz);

        return "quizplay";
    }
    @GetMapping("/Quiz/Play/{id}/{count}")
    public String playQuiz(Model model, @PathVariable long id, @PathVariable int count){
        
        Quiz quiz = quizService.findByqId(id);
        System.out.println(count);
        System.out.println(quiz.getName());
        System.out.println(quiz.getQas());
        System.out.println(quiz.getQas().get(count).getQuestion());
        System.out.println(quiz.getQas().get(count).getAnswers());
        model.addAttribute("quiz", quiz);

        return "quizplay";
    }




    @GetMapping("/testquiz")
    public String gettestquiz(Model model, @ModelAttribute String name){
        Quiz quiz = new Quiz(name);
        List<QA> qas = qaService.findAll();
        model.addAttribute("quiz", quiz);
        model.addAttribute("qas", qas);
        quizService.save(quiz);
        return "quiztest";
    }
    @PostMapping("/testquiz")
    public String testquiz(@ModelAttribute("quiz") Quiz quiz){
        quizService.save(quiz);
        System.out.println(quiz);

        return "index";
    }
    @GetMapping("/testfind")
    public String testfind(Model model){
        List<Quiz> quizzes = quizService.findAll();
        model.addAttribute("quizzes", quizzes);

        return "testfind";
    }
}
