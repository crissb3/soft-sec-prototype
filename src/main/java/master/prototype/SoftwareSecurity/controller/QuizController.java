package master.prototype.SoftwareSecurity.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import master.prototype.SoftwareSecurity.entity.QA;
import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.entity.Tag;
import master.prototype.SoftwareSecurity.entity.Userclass;
import master.prototype.SoftwareSecurity.helpers.CosineSimilarity;
import master.prototype.SoftwareSecurity.service.QAService;
import master.prototype.SoftwareSecurity.service.QuizService;
import master.prototype.SoftwareSecurity.service.TagService;
import master.prototype.SoftwareSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private QAService qaService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;


    @GetMapping("/adminindex")
    public String admin(){
        return "adminindex";
    }
    @GetMapping("/Quiz/Create/Search")
    public String searchQuiz(Model model,
                             @RequestParam String question,
                             @RequestParam(value = "tags", required = false) List<String> tags) {
        List<QA> qas;
        if (tags == null) {
            qas = qaService.findByWord(question);
        } else {
            List<Tag> tagList = new ArrayList<>();
            for (String tag : tags) {
                tagList.add(tagService.findBytId(Long.valueOf(tag)));
            }
            qas = qaService.findByTags(tagList);
            List<QA> searchwordlist = qaService.findByWord(question);
            qas.retainAll(searchwordlist);
            Set<QA> qaSet = new HashSet<>(qas);
            qas.clear();
            qas.addAll(qaSet);
            qas.sort(Comparator.comparingLong(QA::getQaId).reversed());
        }
        qas.sort(Comparator.comparingLong(QA::getQaId).reversed());
        Quiz quiz = new Quiz();
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("qas", qas);
        model.addAttribute("quiz", quiz);
        model.addAttribute("message_hits", qas.size());

        return "newquiz";
    }

    @GetMapping("/Quiz/Create")
    public String createQuiz(Model model) {
        Quiz quiz = new Quiz();

        model.addAttribute("quiz", quiz);

        List<QA> qas = qaService.findAll();
        qas.sort(Comparator.comparingLong(QA::getQaId).reversed());
        model.addAttribute("qas", qas);
        model.addAttribute("tags", tagService.findAll());

        return "newquiz";
    }

    @PostMapping("/Quiz/Create")
    public String addQuestionsToQuiz(
            @ModelAttribute("quiz") Quiz quiz,
            Model model,
            @RequestParam("lives") int lives) {

        if (quiz.getName().equals("")) {
            model.addAttribute("response", "error");
            model.addAttribute("message1", "Can not create quiz without a name.");
            return "adminindex";
        }
        if (quiz.getQas().size()>10){
            model.addAttribute("response", "error");
            model.addAttribute("message1", "The maximum number of questions allowed is 10.");
            return "adminindex";
        }
        else {
            boolean isFound = quizService.findAll().stream().anyMatch(o -> o.getName().equalsIgnoreCase(quiz.getName()));
            if(isFound){

                quiz.setLives(lives);
                quizService.save(quiz);
                quiz.setName(quiz.getName()+" "+quiz.getQId());
                quizService.save(quiz);
                model.addAttribute("response", "warn");
                model.addAttribute("message1", "Warning: Quiz name already exists! Quiz created with name: "
                        +quiz.getName()+" and ID: "+quiz.getQId()+ ". Copy and share this ID if you want others to play your quiz.");
                return "adminindex";
            }
            quiz.setLives(lives);
            quizService.save(quiz);
            model.addAttribute("response", "success");
            model.addAttribute("message1", "Created quiz with ID: " + quiz.getQId() + ". Copy and share this ID if you want others to play your quiz.");
        }
        return "adminindex";
    }

    @PostMapping("/Quiz/Create-random")
    public String addQuestionsToQuizrandom(
            Model model,
            @RequestParam("lives_random") int lives,
            @RequestParam(value = "quiz_size", required = false) String quiz_size,
            @RequestParam("name_random") String name,
            @RequestParam(value = "qas", required = false) List<String> qas) {
        List<QA> qasList = new ArrayList<>();
        List<QA> random_QA_list = new ArrayList<>();
        if (name.isEmpty()) {
            model.addAttribute("response", "error");
            model.addAttribute("message1", "Can not create quiz without a name.");
            return "adminindex";
        } else if (Objects.equals(quiz_size, "0")) {
            model.addAttribute("response", "error");
            model.addAttribute("message1", "Can not create quiz without questions");
            return "adminindex";
        } else {
            for (String qa : qas) {
                qasList.add(qaService.findByQaId(Long.valueOf(qa)));
            }
            Random rand = new Random();
            Quiz quiz = new Quiz();

            for (int i = 0; i < Integer.parseInt(quiz_size); i++) {
                int randomIndex = rand.nextInt(qasList.size());
                random_QA_list.add(qasList.get(randomIndex));
                qasList.remove(randomIndex);
            }
            boolean isFound = quizService.findAll().stream().anyMatch(o -> o.getName().equalsIgnoreCase(name));
            if(isFound){
                quiz.setLives(lives);
                quizService.save(quiz);
                quiz.setName(name+" "+quiz.getQId());
                quiz.setQas(random_QA_list);
                quizService.save(quiz);
                model.addAttribute("response", "warn");
                model.addAttribute("message1", "Quiz name already exists! Quiz created with name: "
                        +quiz.getName()+" and ID: "+quiz.getQId()+ ". Copy and share this ID if you want others to play your quiz.");
                return "adminindex";
            }
            quiz.setName(name);
            quiz.setLives(lives);
            quiz.setQas(random_QA_list);
            quizService.save(quiz);
            model.addAttribute("response", "success");
            model.addAttribute("message1", "Created quiz with ID: " + quiz.getQId() + ". Copy and share this ID if you want others to play your quiz.");
        }
        return "adminindex";
    }

    @GetMapping("")
    public String selectQuiz(Model model) {
        List<Quiz> quizzes = quizService.findAll();
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizselect";
    }

    @GetMapping("Quiz/Select/Search-id")
    public String selectQuizSearch(Model model,
                                   @RequestParam(required = false) Long qid) {
        List<Quiz> quizzes = new ArrayList<>();
        if (!(qid == null)) {
            if (!(quizService.findByqId(qid) == null)) {
                Quiz quiz = quizService.findByqId(qid);
                quizzes.add(quiz);
            }
        } else {
            quizzes = quizService.findAll();
        }
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizselect";
    }

    @GetMapping("Quiz/Select/Search-name")
    public String selectQuizSearchName(Model model,
                                       @RequestParam(required = false) String name) {
        List<Quiz> quizzes;
        if (!(name.equals(""))) {
            quizzes = quizService.findByName(name);
        } else {
            quizzes = quizService.findAll();
        }
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizselect";
    }

    @GetMapping("/Quiz/Start/{id}")
    public String startQuiz(Model model,
                            @PathVariable long id,
                            @RequestParam(name = "page", defaultValue = "0") int page) {
        Quiz quiz = quizService.findByqId(id);
        if (quiz.getQas().isEmpty()) {
            model.addAttribute("response", "error");
            model.addAttribute("message1", "Can not start a quiz without questions");
            return "index";
        } else {
            int score = 0;
            Userclass user = new Userclass();
            user.setScore(score);
            user.setLives(quiz.getLives());
            Set<String> lifelines = Set.of("5050", "call", "protection", "audience");
            user.setLifelines(lifelines);
            user = userService.answeredList(user, page); //answeredlist
            userService.save(user);
//            model.addAttribute("token",0);
            model.addAttribute("page", page);
            model.addAttribute("quiz", quiz);
            model.addAttribute("user", user);

            return "quizplay";
        }
    }

    @GetMapping("Quiz/Start/times-up")
    public String timesUp(Model model,
                          @RequestParam long id,
                          @RequestParam(name = "page", defaultValue = "-1") int page,
                          @RequestParam("user") long uid) {

        Quiz quiz = quizService.findByqId(id);
        Userclass userclass = userService.findUserById(uid);
        int lives = userclass.getLives();
        lives -= 1;
        if (quiz.getQas().size() == page) {

            model.addAttribute("id", id);
            model.addAttribute("page", page);
            model.addAttribute("quiz", quiz);

            userclass = userService.userScore(userclass, quiz);
            userclass = userService.answeredList(userclass, page);
            userService.save(userclass);
            model.addAttribute("user", userclass);
            return "quizdone";
        }
        if (lives == 0) {
            model.addAttribute("id", id);
            model.addAttribute("page", page);
            model.addAttribute("quiz", quiz);

            userclass = userService.userScore(userclass, quiz);
            userclass = userService.answeredList(userclass, page);
            userService.save(userclass);
            model.addAttribute("user", userclass);
            return "quizdone";
        }
        userclass.setLives(lives);
        userclass = userService.answeredList(userclass, page);
        userService.save(userclass);

        model.addAttribute("id", id);
        model.addAttribute("page", page);
        model.addAttribute("quiz", quiz);
        model.addAttribute("user", userclass);
//        model.addAttribute("token", token);

        return "quizplay";
    }

    @GetMapping("/Quiz/Start/page")
    public String playQuiz(Model model,
                               @RequestParam long id,
                               @RequestParam(name = "page", defaultValue = "-1") int page,
                               @RequestParam("user") long uid,
                               @RequestParam(name = "answer", required = false) String answer,
                               @RequestParam(name = "5050", required = false) String fiftyfifty,
                               @RequestParam(name = "protection", required = false) String prot,
                               @RequestParam(name = "prot", required = false) String prot_used,
                               @RequestParam(name = "call", required = false) String call,
                               @RequestParam(name = "audience", required = false) String audience) {
        Quiz quiz = quizService.shuffleAnswersPlay(id);

        Userclass userclass = userService.findUserById(uid);
        int score = userclass.getScore();

        if (answer == null) {
            if (fiftyfifty != null) {
                userclass.getLifelines().remove("5050");
                userService.save(userclass);
                String correct = quiz.getQas().get(page).getCorrectAnswer();
                for (String ans : quiz.getQas().get(page).getAnswers()) {
                    if (!ans.equals(correct)) {
                        model.addAttribute("id", id);
                        model.addAttribute("page", page);
                        model.addAttribute("quiz", quiz);
                        model.addAttribute("user", userclass);
                        model.addAttribute("correct", correct);
                        model.addAttribute("fake", ans);
                        return "5050";
                    }
                }
                model.addAttribute("id", id);
                model.addAttribute("page", page);
                model.addAttribute("quiz", quiz);
                model.addAttribute("user", userclass);
                model.addAttribute("correct", correct);
                model.addAttribute("fake", correct);
                return "5050";
            }
            if (prot != null) {
                userclass.getLifelines().remove("protection");
                userService.save(userclass);
                model.addAttribute("id", id);
                model.addAttribute("page", page);
                model.addAttribute("quiz", quiz);
                model.addAttribute("user", userclass);
                model.addAttribute("prot", prot);
                return "quizplayprot";
            }
            if (call != null) {
                userclass.getLifelines().remove("call");
                userService.save(userclass);
                model.addAttribute("id", id);
                model.addAttribute("page", page);
                model.addAttribute("quiz", quiz);
                model.addAttribute("user", userclass);
                model.addAttribute("call", "call");
//                model.addAttribute("token", token);
                return "quizplay";
            }
            if (audience != null) {
                userclass.getLifelines().remove("audience");
                userService.save(userclass);
                model.addAttribute("id", id);
                model.addAttribute("page", page);
                model.addAttribute("quiz", quiz);
                model.addAttribute("user", userclass);
//                model.addAttribute("token", token);
                model.addAttribute("audience", "audience");
                return "quizplay";
            }
        }

        assert answer != null;
        if(quiz.getQas().size() == page) {
            if (answer.equals(quiz.getQas().get(page - 1).getCorrectAnswer())) {
                if(!userclass.getAnswered().contains(page)){
                    score += 10;
                }
                userclass.setScore(score);
                userService.save(userclass);
            }
            model.addAttribute("id", id);
            model.addAttribute("page", page);
            model.addAttribute("quiz", quiz);

            userclass = userService.userScore(userclass, quiz);
            userclass = userService.answeredList(userclass, page);
            userService.save(userclass);
            model.addAttribute("user", userclass);
            return "quizdone";
        } else {
            if (answer.equals(quiz.getQas().get(page - 1).getCorrectAnswer())) {
                if(!userclass.getAnswered().contains(page)) {
                    score += 10;
                }
                userclass.setScore(score);
                userService.save(userclass);

            } else if (!answer.equals(quiz.getQas().get(page - 1).getCorrectAnswer())) {
                int lives = userclass.getLives();
                if (!userclass.getAnswered().contains(page)) {
                    if(prot_used == null){
                        lives -= 1;
                    }
                }

                if (lives == 0) {
                    model.addAttribute("id", id);
                    model.addAttribute("page", page);
                    model.addAttribute("quiz", quiz);

                    userclass = userService.userScore(userclass, quiz);
                    userclass = userService.answeredList(userclass, page);
                    userService.save(userclass);
                    model.addAttribute("user", userclass);
                    return "quizdone";
                }
                userclass.setLives(lives);
                userService.save(userclass);
            }
        }
        userclass = userService.answeredList(userclass, page);
        userService.save(userclass);
        model.addAttribute("id", id);
        model.addAttribute("page", page);
        model.addAttribute("quiz", quiz);
        model.addAttribute("user", userclass);
        return "quizplay";
    }

    @PostMapping("Quiz/final-score")
    public String finalScore(Model model,
                             @RequestParam String name,
                             @RequestParam long id,
                             @RequestParam("user") long uid) {
        List<Userclass> scores;
        Userclass user = userService.findUserById(uid);
        if (name.equals("")) {
            user.setUsername("User " + uid);
        }
        if (!name.equals("")) {
            user.setUsername(name);
        }
        userService.save(user);
        Quiz quiz = quizService.findByqId(id);
        if (quiz.getScores().isEmpty()) {
            scores = new ArrayList<>();
        } else {
            scores = quiz.getScores();
        }
        if (user.getScore() > (quiz.getQas().size() * 10)) {
            if(name.equals(""))model.addAttribute("message", "You cheated! Score not posted for user: User " + uid);
            if(!name.equals(""))model.addAttribute("message", "You cheated! Score not posted for user: " + name);
            return "index";
        }

        scores.add(user);
        quiz.setScores(scores);
        quizService.save(quiz);

//        model.addAttribute("name", name);
//        model.addAttribute("score", user.getScore());
        model.addAttribute("response", "success");
        if(name.equals(""))model.addAttribute("message1", "Score posted to leaderoard for user: User "+uid);
        if(!name.equals(""))model.addAttribute("message1", "Score posted to leaderboard for user: " + name);
        return "index";
    }

    @GetMapping("/Quiz/Leaderboard/Select")
    public String selectLeaderboard(Model model) {
        List<Quiz> quizzes = quizService.findAll();
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizselectleaderboard";
    }
    @GetMapping("Quiz/Leaderboard/Search-name")
    public String selectLeaderboardSearchName(Model model,
                                       @RequestParam(required = false) String name) {
        List<Quiz> quizzes;
        if (!(name.equals(""))) {
            quizzes = quizService.findByName(name);
        } else {
            quizzes = quizService.findAll();
        }
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizselectleaderboard";
    }
    @GetMapping("Quiz/Leaderboard/Search-id")
    public String selectLeaderboardSearchID(Model model,
                                   @RequestParam(required = false) Long qid) {
        List<Quiz> quizzes = new ArrayList<>();
        if (!(qid == null)) {
            if (!(quizService.findByqId(qid) == null)) {
                Quiz quiz = quizService.findByqId(qid);
                quizzes.add(quiz);
            }
        }
        else {
            quizzes = quizService.findAll();
        }
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizselectleaderboard";
    }

    @GetMapping("/Quiz/Leaderboard/{id}")
    public String leaderboard(Model model,
                              @PathVariable long id) {
        Quiz quiz = quizService.findByqId(id);
        List<Userclass> scores = quiz.getScores();
        scores.sort(Comparator.comparingInt(Userclass::getScore).reversed());
        List<Userclass> top10 = scores.stream().limit(10).collect(Collectors.toList());
        model.addAttribute("scores", top10);
        return "leaderboard";
    }

    @GetMapping("/Quiz/Delete")
    public String deleteSelect(Model model) {
        List<Quiz> quizzes = quizService.findAll();
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);

        return "quizselectdelete";
    }

    @GetMapping("/Quiz/Delete/{id}")
    public String deleteQuiz(Model model, @PathVariable long id) {
        Quiz quiz = quizService.findByqId(id);
        model.addAttribute("quiz", quiz);
        return "quizdelete";
    }

    @PostMapping("/Quiz/Delete/{id}")
    public String deleteQuizconfirm(Model model, @PathVariable long id) {
        quizService.deleteByqId(id);
        model.addAttribute("response", "success");
        model.addAttribute("message1", "Deleted quiz with ID: "+id);
        return "adminindex";
    }


    @GetMapping("/callFriend")
    public String callFriend() {
        return "callfriend";
    }

    @GetMapping("/callJava")
    public String callJava() {
        return "calljava";
    }

    @GetMapping("/callCapec")
    public String callCapec() {
        return "callcapec";
    }

    @GetMapping("/callCWE")
    public String callCWE() {
        return "callcwe";
    }

    @GetMapping("/callSO")
    public String callSO() {
        return "callso";
    }
    @GetMapping("/callWiki")
    public String callWiki() {
        return "callwiki";
    }
    @GetMapping("/askAudience")
    public String askAudience(@RequestParam Long id,
                              @RequestParam int page,
                              Model model) {
        Quiz quiz = quizService.findByqId(id);

        model.addAttribute("question", quiz.getQas().get(page).getQuestion());

        List<String> answers = quiz.getQas().get(page).getAnswers();
        String question = quiz.getQas().get(page).getQuestion();

        String searchword = question.replaceAll("\\s+", "%20");
        searchword = searchword.replaceAll("[^a-zA-Z0-9%]", "");

        String url = "https://customsearch.googleapis.com/customsearch/v1?cx=f5e58680532973aeb&num=10&q=" +
                searchword + "&prettyPrint=true&key=AIzaSyD01AyjxliyuVTXE1lyTCPdLR76TEMAbqQ&num=2";

        try (java.io.InputStream is =
                     new java.net.URL(url).openStream()) {
            String contents = new String(is.readAllBytes());
            JsonObject jsonObject = JsonParser.parseString(contents).getAsJsonObject();
            JsonArray items = jsonObject.getAsJsonArray("items");
            StringBuilder snippets = new StringBuilder();
            if (items != null) {
                for (int i = 0; i < items.size(); i++) {
                    JsonObject object = items.get(i).getAsJsonObject();
                    if(!(object.get("snippet") == null))snippets.append(object.get("snippet").toString(), 1, object.get("snippet").toString().length() - 1);
                }
                snippets.append(quiz.getQas().get(page).getExplanation());
//                System.out.println(quiz.getQas().get(page).getExplanation());
//                System.out.println(snippets);
                double sum_cosine = 0;
                HashMap<String, Double> cos_list = new HashMap<>();
                for (String answer : answers) {
                    CosineSimilarity cosineSimilarity = new CosineSimilarity(answer, snippets.toString());
                    sum_cosine += cosineSimilarity.getCosineSimilarity();
                    cos_list.put(answer, cosineSimilarity.getCosineSimilarity());
                }
                int counter = 0;
                List<String> abcd = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
                for (String answer : answers) {
                    double percent = cos_list.get(answer) / sum_cosine * 100;
                    model.addAttribute(abcd.get(counter),String.format("%.0f",percent));
                    counter++;
                }

            } else {
                model.addAttribute("message", "The audience could not decide on this question.");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "askaudience";
    }

    @GetMapping("Quiz/Edit/Select/Search-id")
    public String selectQuizSearchID(Model model,
                                         @RequestParam(required = false) Long id) {
        List<Quiz> quizzes = new ArrayList<>();
        if (!(id == null)) {
            if (!(quizService.findByqId(id) == null)) {
                Quiz quiz = quizService.findByqId(id);
                quizzes.add(quiz);
            }
        } else {
            quizzes = quizService.findAll();
        }
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizeditselect";
    }

    @GetMapping("Quiz/Edit/Select/Search-name")
    public String selectQuizSearch(Model model,
                                       @RequestParam(required = false) String name) {
        List<Quiz> quizzes;
        if (!(name.equals(""))) {
            quizzes = quizService.findByName(name);
        } else {
            quizzes = quizService.findAll();
        }
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizeditselect";
    }

    @GetMapping("Quiz/Edit/Select")
    public String selectQuizEdit(Model model) {
        List<Quiz> quizzes = quizService.findAll();
        quizzes.sort(Comparator.comparingLong(Quiz::getQId).reversed());
        model.addAttribute("quizzes", quizzes);
        return "quizeditselect";
    }

    @GetMapping("/Quiz/Edit/{id}")
    public String editQuestion(Model model,
                               @PathVariable long id) {
        Quiz quiz = quizService.findByqId(id);
        List<QA> qas = qaService.findAll();
        qas.sort(Comparator.comparingLong(QA::getQaId).reversed());
        model.addAttribute("qas", qas);
        model.addAttribute("quiz", quiz);
        return "quizedit";
    }

    @PostMapping("/Quiz/Edit")
    public String editQuiz(
            @ModelAttribute("quiz") Quiz quiz,
            Model model,
            @RequestParam("lives") int lives,
            @RequestParam("id") Long id) {
        Quiz oldQuiz = quizService.findByqId(id);

        if (quiz.getName().equals("")) {
            model.addAttribute("response", "error");
            model.addAttribute("message1", "Can not save quiz without a name.");
            return "adminindex";
        }
        if (quiz.getQas().size()>10){
            model.addAttribute("response", "error");
            model.addAttribute("message1", "The maximum number of questions allowed is 10.");
            return "adminindex";
        }
        else {
            boolean isFound = quizService.findAll().stream().anyMatch(o -> o.getName().equalsIgnoreCase(quiz.getName()));
            if(isFound && !Objects.equals(quiz.getName(), oldQuiz.getName())){
                if(lives != -2)oldQuiz.setLives(lives);
                quizService.save(oldQuiz);
                oldQuiz.setName(quiz.getName()+" "+oldQuiz.getQId());
                quizService.save(oldQuiz);
                model.addAttribute("response", "warn");
                model.addAttribute("message1", "Warning: Quiz name already exists! Quiz saved with name: "
                        +oldQuiz.getName()+" and ID: "+oldQuiz.getQId()+ ". Copy and share this ID if you want others to play your quiz.");
                return "adminindex";
            }
            if(lives != -2)oldQuiz.setLives(lives);
            oldQuiz.setName(quiz.getName());
            oldQuiz.setQas(quiz.getQas());
            quizService.save(oldQuiz);
            model.addAttribute("response", "success");
            model.addAttribute("message1", "Saved quiz with ID: " + oldQuiz.getQId() + ". Copy and share this ID if you want others to play your quiz.");
        }
        return "adminindex";
    }

//    @GetMapping("/testAPI")
//    public String testapi() throws MalformedURLException {
//        String searchword = "Expressions and methods spoofing";
//        searchword = searchword.replaceAll("\\s+", "%20");
//        System.out.println(searchword);
//        String url = "https://customsearch.googleapis.com/customsearch/v1?cx=602325f04a96e5851&num=10&q=" +
//                searchword + "&prettyPrint=true&key=AIzaSyD01AyjxliyuVTXE1lyTCPdLR76TEMAbqQ";
//
//
//        try (java.io.InputStream is =
//                     new java.net.URL(url).openStream()) {
//            String contents = new String(is.readAllBytes());
//            JsonObject jsonObject = JsonParser.parseString(contents).getAsJsonObject();
//            JsonArray items = jsonObject.getAsJsonArray("items");
//            System.out.println(items);
//
//            for (int i = 0; i < items.size(); i++) {
//                JsonObject object = items.get(i).getAsJsonObject();
//                System.out.println(object.get("snippet").toString().substring(1, object.get("snippet").toString().length() - 1));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return "testapi";
//    }

}
