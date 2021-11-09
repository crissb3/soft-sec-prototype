package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/newQuiz")
    public String newQuiz(Model model) {

        model.addAttribute("message", "Create new quiz");
        return "newquiz";
    }

    @GetMapping("/createQuiz")
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

        return "newquiz";
    }

    @GetMapping("/searchQuiz")
    public String searchQuiz(Model model, @RequestParam String quizname) {
        List<Quiz> quizzes = quizService.findByName(quizname);

        model.addAttribute("quizzes", quizzes);
        model.addAttribute("message", "Searched for quiz with name:" +quizname);

        return "searchquiz";
    }
}
