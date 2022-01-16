package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.entity.Userclass;
import master.prototype.SoftwareSecurity.service.QAService;
import master.prototype.SoftwareSecurity.service.QuizService;
import master.prototype.SoftwareSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private QAService qaService;
    @Autowired
    private UserService userService;

    @GetMapping("/Quiz/Create/Search")
    public String searchQuiz(Model model,
                             @RequestParam String question,
                             @RequestParam(required = false) String tag1,
                             @RequestParam(required = false) String tag2,
                             @RequestParam(required = false) String tag3) {
        List<QA> qas;
        Set<QA.Tags> tags = qaService.setTag(tag1,tag2,tag3);
        if(!(tag1==null)) tags.add(QA.Tags.valueOf(tag1));
        if(!(tag2==null)) tags.add(QA.Tags.valueOf(tag2));
        if(!(tag3==null)) tags.add(QA.Tags.valueOf(tag3));
        if(tags.isEmpty()){
            qas  = qaService.findByWord(question);
        }
        else{
            qas  = qaService.findByTags(tags);
            List<QA> searchwordlist = qaService.findByWord(question);
            qas.retainAll(searchwordlist);
            Set<QA> qaSet = new HashSet<>(qas);
            qas.clear();
            qas.addAll(qaSet);
            Collections.sort(qas, Comparator.comparingLong(QA::getQaId));
        }
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
            quizService.save(quiz);
            model.addAttribute("message", "Created quiz with ID: " + quiz.getQId() + "\n Copy and share this ID if you want others to play your quiz.");
        }
        return "index";
    }
    @GetMapping("Quiz/Select")
    public String selectQuiz(Model model){
        List<Quiz> quizzes = quizService.findAll();
        model.addAttribute("quizzes",quizzes);
        return "quizselect";
    }
    @GetMapping("Quiz/Select/Search")
    public String selectQuizSearch(Model model,
                                   @RequestParam(required = false) Long qid){
        List<Quiz> quizzes = new ArrayList<>();
        if(!(qid == null)){
            if(!(quizService.findByqId(qid)==null)){
            Quiz quiz = quizService.findByqId(qid);
            quizzes.add(quiz);}
        }
        else{
            quizzes = quizService.findAll();
        }
        model.addAttribute("quizzes", quizzes);
        return "quizselect";
    }
    @GetMapping("/Quiz/Start/{id}")
    public String startQuiz(Model model,
                            @PathVariable long id,
                            @RequestParam(name="page", defaultValue = "0") int page){
        Quiz quiz = quizService.findByqId(id);
        if(quiz.getQas().isEmpty()){
            model.addAttribute("message", "Can't start a quiz without questions");
            return "index";
        }
        else{
            int score = 0;
            model.addAttribute("score", score);
            model.addAttribute("page", page);
            model.addAttribute("quiz", quiz);

            return "quizplay";
        }
    }
    @GetMapping("/Quiz/Start/page")
    public String playQuiztest(Model model,
                               @RequestParam long id,
                               @RequestParam(name="page", defaultValue = "-1") int page,
                               @RequestParam("score") int score,
                               @RequestParam("answer") String answer){
        Quiz quiz = quizService.findByqId(id);
        Userclass userclass = new Userclass();
        if(quiz.getQas().size() == page){
            if(answer.equals(quiz.getQas().get(page-1).getCorrectAnswer())){
                score += 10*page;
                userclass.setScore(score);
            }
            model.addAttribute("idtest", id);
            model.addAttribute("page", 0);
            model.addAttribute("quiz", quiz);
            model.addAttribute("score", score);
            return "quizdone";
        }
        else{
            if(answer.equals(quiz.getQas().get(page-1).getCorrectAnswer())){
                score += 10*page;
                userclass.setScore(score);
            }
            model.addAttribute("idtest", id);
            model.addAttribute("page", page);
            model.addAttribute("quiz", quiz);
            model.addAttribute("score", score);
        }
        return "quizplay";
    }
    @PostMapping("Quiz/final-score")
    public String finalScore(Model model, @RequestParam String name, @RequestParam int score, @RequestParam long id){
        Userclass newUserclass = new Userclass();
        List<Userclass> scores;
        newUserclass.setScore(score);
        newUserclass.setUsername(name);
        userService.save(newUserclass);
        Quiz quiz = quizService.findByqId(id);
//        System.out.println(user);
        if(quiz.getScores().isEmpty()){
            scores = new ArrayList<>();
        }
        else{
            scores = quiz.getScores();
        }
        scores.add(newUserclass);
        quiz.setScores(scores);
        quizService.save(quiz);
//        System.out.println(quiz.getScores());

        model.addAttribute("name",name);
        model.addAttribute("score", score);
        model.addAttribute("message", "Score posted to leaderboard for user: "+name);
        return "index";
    }
    @GetMapping("/Quiz/Leaderboard/Select")
    public String selectLeaderboard(Model model){
        List<Quiz> quizzes = quizService.findAll();
        model.addAttribute("quizzes", quizzes);
        return "quizselectleaderboard";
    }
    @GetMapping("/Quiz/Leaderboard/{id}")
    public String leaderboard(Model model,
                              @PathVariable long id){
        Quiz quiz = quizService.findByqId(id);
        List<Userclass> scores = quiz.getScores();
        Collections.sort(scores, Comparator.comparingInt(Userclass::getScore).reversed());
        model.addAttribute("scores", scores);
        return "leaderboard";
    }
    @GetMapping("/Quiz/Delete")
    public String deleteSelect(Model model){
        List<Quiz> quizzes = quizService.findAll();
        model.addAttribute("quizzes", quizzes);

        return "quizselectdelete";
    }
    @GetMapping("/Quiz/Delete/{id}")
    public String deleteQuiz(Model model, @PathVariable long id){
        Quiz quiz = quizService.findByqId(id);
        model.addAttribute("quiz", quiz);
        return "quizdelete";
    }
    @PostMapping("/Quiz/Delete/{id}")
    public String deleteQuizconfirm(Model model, @PathVariable long id){
        String name = quizService.findByqId(id).getName();
        quizService.deleteByqId(id);
        model.addAttribute("message","Deleted quiz with name: "+name);
        return "index";
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

    @GetMapping("/testiterate")
    public String testIterate(Model model, @RequestParam(name="page", defaultValue = "0") int page){

        List<Quiz> quizzes = quizService.findAll();
        if(quizzes.size()==page){
            model.addAttribute("quizzes", quizzes);
            model.addAttribute("page",0);
        }
        else{
            model.addAttribute("quizzes", quizzes);
            model.addAttribute("page", page);
        }

        return "testiterate";
    }

}
