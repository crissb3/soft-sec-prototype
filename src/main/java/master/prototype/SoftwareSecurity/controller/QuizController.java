package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/addQuiz")
    public String addQuiz(Model model, @ModelAttribute Quiz quiz) {

        model.addAttribute("message", "test.html is working!");
        return "test";
    }
}
