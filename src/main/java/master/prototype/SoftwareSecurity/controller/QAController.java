package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.Quiz;
import master.prototype.SoftwareSecurity.entity.Tag;
import master.prototype.SoftwareSecurity.entity.Userclass;
import master.prototype.SoftwareSecurity.service.QAService;
import master.prototype.SoftwareSecurity.service.QuizService;
import master.prototype.SoftwareSecurity.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import master.prototype.SoftwareSecurity.entity.QA;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class QAController {

    @Autowired
    private QAService qaService;
    @Autowired
    private TagService tagService;
    @Autowired
    private QuizService quizService;
//    @Autowired
//    private CapecService capecService;
//    @Autowired
//    private CodingStandardsService codingStandardsService;

    @GetMapping("/searchQA")
    public String searchQA(@RequestParam String question, Model model) {

        List<QA> qas = qaService.findByWord(question);
        model.addAttribute("qas", qas);
        model.addAttribute("message", "Successfully searched for questions containing: "
                + question);

        return "adminindex";
    }

    @GetMapping("/addQuestion")
    public String createQuestion(Model model) {
        model.addAttribute("tags",tagService.findAll());
        model.addAttribute("message", "Create questions page! Use the links below to help construct your questions.");
        return "createquestion";
    }

    @GetMapping("/deleteQ")
    public String deleteQtemp(Model model) {
        model.addAttribute("message", "Successfully deleted");
        //qaService.deleteAll(); // TEMPORARY
        return "adminindex";
    }

    @PostMapping("/addQA")
    public String addQA(@RequestParam String addquestion,
                        @RequestParam String fakeanswer1,
                            @RequestParam String fakeanswer2,
                            @RequestParam String fakeanswer3,
                            @RequestParam String correctanswer,
                            @RequestParam(required = false) String explanation,
                            @RequestParam(value = "file", required = false) MultipartFile file,
                            @RequestParam(value = "tags", required = false) List<String> tags,
                            Model model) throws IOException {
        if(addquestion.isEmpty()
                || fakeanswer1.isEmpty()
                || fakeanswer2.isEmpty()
                || fakeanswer3.isEmpty()
                || correctanswer.isEmpty()
                || explanation.isEmpty()) {
            model.addAttribute("response","error");
            model.addAttribute("message", "Use the links below to help construct your question/answers.");
            model.addAttribute("message1", "You can not leave any of the fields empty.");
            model.addAttribute("tags",tagService.findAll());
            return "createquestion";
        }
        if(addquestion.length()>255
        || fakeanswer1.length()>255
        || fakeanswer2.length()>255
        || fakeanswer3.length()>255){
            model.addAttribute("response","error");
            model.addAttribute("message", "Use the links below to help construct your question/answers.");
            model.addAttribute("message1", "The maximum length that a question or an answer can be is 255 characters.");
            model.addAttribute("tags",tagService.findAll());
            return "createquestion";
        }
        else {
            QA qa = qaService.shuffleAnswers(addquestion, fakeanswer1, fakeanswer2, fakeanswer3, correctanswer);
            if(tags != null){
                List<Tag> tagList = new ArrayList<>();
                for(String tag : tags){
                    tagList.add(tagService.findBytId(Long.valueOf(tag)));
                }
                qa.setCustomtags(tagList);
            }
            if(!(file == null)){
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                qa.setImg(Base64.getEncoder().encodeToString(file.getBytes()));
            }

            qaService.save(qa);
            model.addAttribute("id",qa.getQaId());
            model.addAttribute("response","success");
            model.addAttribute("tags",tagService.findAll());
            model.addAttribute("message", "Created question: " + addquestion +".");
        }

        return "createquestion";
    }
    @PostMapping("/addTag")
    public String addTag(@RequestParam String custom_tag,
                         Model model,
                         @RequestParam(name="correctanswer", required = false) String correct,
                         @RequestParam(name="question", required = false) String question,
                         @RequestParam(name="explanation", required = false) String expl,
                         @RequestParam(name="f1", required = false) String fake1,
                         @RequestParam(name="f2", required = false) String fake2,
                         @RequestParam(name="f3", required = false) String fake3){
        Tag tag = new Tag();
        boolean isFound = tagService.findAll().stream().anyMatch(o -> o.getTag().equalsIgnoreCase(custom_tag));
        if(isFound){
            model.addAttribute("message", "Tag already exists.");
        }
        else{
            tag.setTag(custom_tag);
            tagService.save(tag);
            model.addAttribute("message", "Tag created: "+tag.getTag());
        }
        if(correct!=null)model.addAttribute("correctanswer", correct);
        if(question!=null)model.addAttribute("addquestion", question);
        if(fake1!=null)model.addAttribute("fakeanswer1", fake1);
        if(fake2!=null)model.addAttribute("fakeanswer2", fake2);
        if(fake3!=null)model.addAttribute("fakeanswer3", fake3);
        if(expl!=null)model.addAttribute("explanation", expl);

        model.addAttribute("tags",tagService.findAll());
        return "createquestion";
    }

    @PostMapping("/Question/Edit/addTag")
    public String EditQuestionaddTag(@RequestParam String custom_tag,
                         Model model,
                         @RequestParam(name="correctanswer", required = false) String correct,
                         @RequestParam(name="question", required = false) String question,
                         @RequestParam(name="explanation", required = false) String expl,
                         @RequestParam(name="f1", required = false) String fake1,
                         @RequestParam(name="f2", required = false) String fake2,
                         @RequestParam(name="f3", required = false) String fake3,
                         @RequestParam(name = "qaId") Long qaId){
        Tag tag = new Tag();
        boolean isFound = tagService.findAll().stream().anyMatch(o -> o.getTag().equalsIgnoreCase(custom_tag));
        if(isFound){
            model.addAttribute("message", "Tag already exists.");
        }
        else{
            tag.setTag(custom_tag);
            tagService.save(tag);
            model.addAttribute("message", "Tag created: "+tag.getTag());
        }
        if(correct!=null)model.addAttribute("correctanswer", correct);
        if(question!=null)model.addAttribute("addquestion", question);
        if(fake1!=null)model.addAttribute("fakeanswer1", fake1);
        if(fake2!=null)model.addAttribute("fakeanswer2", fake2);
        if(fake3!=null)model.addAttribute("fakeanswer3", fake3);
        if(expl!=null)model.addAttribute("explanation", expl);
        boolean found = quizService.findAll().stream().anyMatch(o -> o.getQas().contains(qaService.findByQaId(qaId)));
        if(!found)model.addAttribute("notfound",1);

        QA qa = qaService.findByQaId(qaId);
        model.addAttribute("qa", qa);
        model.addAttribute("qaId", qaId);
        model.addAttribute("tags",tagService.findAll());
        return "editquestion";
    }


    @GetMapping("Question/Select/Search-id")
    public String selectQuestionSearchID(Model model,
                                   @RequestParam(required = false) Long qid) {
        List<QA> qas = new ArrayList<>();
        if (!(qid == null)) {
            if (!(qaService.findByQaId(qid) == null)) {
                QA qa = qaService.findByQaId(qid);
                qas.add(qa);
            }
        } else {
            qas = qaService.findAll();
        }
        qas.sort(Comparator.comparingLong(QA::getQaId).reversed());
        model.addAttribute("qas", qas);
        return "questionselect";
    }

    @GetMapping("Question/Select/Search-name")
    public String selectQuestionSearch(Model model,
                                       @RequestParam(required = false) String name) {
        List<QA> qas;
        if (!(name.equals(""))) {
            qas = qaService.findByWord(name);
        } else {
            qas = qaService.findAll();
        }
        qas.sort(Comparator.comparingLong(QA::getQaId).reversed());
        model.addAttribute("qas", qas);
        return "questionselect";
    }

    @GetMapping("Question/Edit/Select")
    public String selectQuiz(Model model) {
        List<QA> qas = qaService.findAll();
        qas.sort(Comparator.comparingLong(QA::getQaId).reversed());
        model.addAttribute("qas", qas);
        return "questionselect";
    }

    @GetMapping("Question/Delete/{id}")
    public String deleteQuestion(Model model,
                                 @PathVariable long id) {
        qaService.deleteByqaId(id);
        model.addAttribute("response","success");
        model.addAttribute("message1","Deleted question with ID: "+id);
//        model.addAttribute("message", "Deleted question with ID: "+id);
        return "adminindex";
    }

    @GetMapping("/Question/Edit/{id}")
    public String editQuestion(Model model,
                            @PathVariable long id) {
        QA qa = qaService.findByQaId(id);
        int count = 1;
        for(String ans : qa.getAnswers()){
            if(!ans.equals(qa.getCorrectAnswer())){
                model.addAttribute("fakeanswer"+count,ans);
                count++;
            }
        }
        if(count != 4){
            for(int i = count; i<4; i++){
                model.addAttribute("fakeanswer"+i, "...");
            }
        }
        boolean found = quizService.findAll().stream().anyMatch(o -> o.getQas().contains(qa));
        if(!found)model.addAttribute("notfound",1);

        model.addAttribute("qa", qa);
        model.addAttribute("addquestion", qa.getQuestion());
        model.addAttribute("explanation", qa.getExplanation());
        model.addAttribute("correctanswer", qa.getCorrectAnswer());
        model.addAttribute("message","Question ID: "+qa.getQaId());
        model.addAttribute("tags", tagService.findAll());

        return "editquestion";
    }

    @PostMapping("/updateQA")
    public String updateQA(@RequestParam String addquestion,
                        @RequestParam String fakeanswer1,
                        @RequestParam String fakeanswer2,
                        @RequestParam String fakeanswer3,
                        @RequestParam String correctanswer,
                        @RequestParam(required = false) String explanation,
                        @RequestParam(value = "file", required = false) MultipartFile file,
                        @RequestParam(value = "tags", required = false) List<String> tags,
                        Model model,
                        @RequestParam("qaId") Long qaId) throws IOException {

        if(addquestion.isEmpty()
                || fakeanswer1.isEmpty()
                || fakeanswer2.isEmpty()
                || fakeanswer3.isEmpty()
                || correctanswer.isEmpty()) {
            model.addAttribute("response", "error");
            model.addAttribute("message", "Use the links below to help construct your question/answers.");
            model.addAttribute("message1", "You can not leave the question or answers empty.");
            model.addAttribute("qa", qaService.findByQaId(qaId));
            model.addAttribute("tags",tagService.findAll());
            return "editquestion";
        }
        if(addquestion.length()>255
                || fakeanswer1.length()>255
                || fakeanswer2.length()>255
                || fakeanswer3.length()>255){
            model.addAttribute("response", "error");
            model.addAttribute("message", "Use the links below to help construct your question/answers.");
            model.addAttribute("message1", "The maximum length that a question or an answer can be is 255 characters.");
            model.addAttribute("qa", qaService.findByQaId(qaId));
            model.addAttribute("tags",tagService.findAll());
            return "editquestion";
        }
        else {
            QA qa = qaService.shuffleAnswersEdit(qaId, addquestion, fakeanswer1, fakeanswer2, fakeanswer3, correctanswer);
            if(tags != null){
                List<Tag> tagList;
                if(qa.getCustomtags()!=null){
                    tagList = qa.getCustomtags();
                }
                else{
                    tagList = new ArrayList<>();
                }
                for(String tag : tags){
                    if(!tagList.contains(tagService.findBytId(Long.valueOf(tag)))){
                        tagList.add(tagService.findBytId(Long.valueOf(tag)));
                    }
                }
                qa.setCustomtags(tagList);
            }

            if(file != null){
                if(file.getBytes().length != 0){
                    qa.setImg(Base64.getEncoder().encodeToString(file.getBytes()));
                }
            }
            if(!explanation.equals(""))
            {
                qa.setExplanation(explanation);
            }
            if(explanation.equals("")){
                qa.setExplanation("No explanation added.");
            }
            qaService.save(qa);
            model.addAttribute("response", "success");
            model.addAttribute("message1", "Saved question with ID: " + qaId +".");
        }

        return "adminindex";
    }
//    @PostMapping("/addQAcapec")
//    public String addQAcapec(@RequestParam String addquestion,
//                            @RequestParam String fakeanswer1,
//                            @RequestParam String fakeanswer2,
//                            @RequestParam String fakeanswer3,
//                            @RequestParam String correctanswer,
//                            @RequestParam String id,
//                            @RequestParam(required = false) String tag1,
//                            @RequestParam(required = false) String tag2,
//                            @RequestParam(required = false) String tag3,
//                            Model model) {
//
//        if(addquestion.isEmpty()
//                || fakeanswer1.isEmpty()
//                || fakeanswer2.isEmpty()
//                || fakeanswer3.isEmpty()
//                || correctanswer.isEmpty()) {
//            Capec capec = capecService.findById(Integer.valueOf(id));
//            model.addAttribute("capec", capec);
//            model.addAttribute("message", "You can't leave an empty field.");
//            return "createquestioncapec";
//        }
//        else {
//            QA qa = qaService.shuffleAnswers(addquestion, fakeanswer1, fakeanswer2, fakeanswer3, correctanswer);
//            qa.setTags(qaService.setTag(tag1, tag2, tag3));
//            qaService.save(qa);
//            model.addAttribute("message", "Created question: " + addquestion);
//        }
//        List<Capec> capecs = capecService.findAll();
//        model.addAttribute("capecs", capecs);
//
//
//        return "capeccreate";
//    }

//    @PostMapping("/addQAcodingstandard")
//    public String addQAcodingstandard(
//                            @RequestParam String addquestion,
//                            @RequestParam String fakeanswer1,
//                            @RequestParam String fakeanswer2,
//                            @RequestParam String fakeanswer3,
//                            @RequestParam String correctanswer,
//                            @RequestParam String id,
//                            @RequestParam(required = false) String tag1,
//                            @RequestParam(required = false) String tag2,
//                            @RequestParam(required = false) String tag3,
//                            Model model) {
//
//        if(addquestion.isEmpty()
//                || fakeanswer1.isEmpty()
//                || fakeanswer2.isEmpty()
//                || fakeanswer3.isEmpty()
//                || correctanswer.isEmpty()) {
//            CodingStandards codingStandard = codingStandardsService.findById(Integer.valueOf(id));
//            model.addAttribute("codingStandard", codingStandard);
//            model.addAttribute("message", "You can't leave an empty field.");
//            return "createquestioncodingstandard";
//        }
//        else {
//            QA qa = qaService.shuffleAnswers(addquestion, fakeanswer1, fakeanswer2, fakeanswer3, correctanswer);
//            qa.setTags(qaService.setTag(tag1, tag2, tag3));
//            qaService.save(qa);
//
//            model.addAttribute("message", "Created question: " + addquestion);
//        }
//        List<CodingStandards> codingStandards = codingStandardsService.findAll();
//        model.addAttribute("codingStandards", codingStandards);
//
//
//        return "javacodingcreate";
//    }

}
