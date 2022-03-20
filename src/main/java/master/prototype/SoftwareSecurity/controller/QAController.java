package master.prototype.SoftwareSecurity.controller;

import master.prototype.SoftwareSecurity.entity.Tag;
import master.prototype.SoftwareSecurity.service.QAService;
import master.prototype.SoftwareSecurity.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import master.prototype.SoftwareSecurity.entity.QA;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class QAController {

    @Autowired
    private QAService qaService;
    @Autowired
    private TagService tagService;
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
                || correctanswer.isEmpty()) {
            model.addAttribute("message", "You can't leave an empty field. Use the links below to help construct your questions.");
            model.addAttribute("tags",tagService.findAll());
            return "createquestion";
        }
        if(addquestion.length()>255
        || fakeanswer1.length()>255
        || fakeanswer2.length()>255
        || fakeanswer3.length()>255){
            model.addAttribute("message", "The maximum length that a question or an answer can be is 255 characters. ");
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
//            qa.setTags(qaService.setTag(tag1, tag2, tag3));
            if(!(file == null)){
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                qa.setImg(Base64.getEncoder().encodeToString(file.getBytes()));
            }
            if(!explanation.equals(""))
            {
                qa.setExplanation(explanation);
            }
            if(explanation.equals("")){
                qa.setExplanation("No explanation added.");
            }
            qaService.save(qa);
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

    @GetMapping("/testimg")
    public String testImage(Model model){
        List<QA> qas = qaService.findAll();
        model.addAttribute("qas", qas);

        return "testiterate";
    }
    @PostMapping("/testimg")
    public String testImg(@RequestParam("img") MultipartFile multipartFile) throws IOException {
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        QA qa = new QA();
//        qa.setImg(fileName);
//        QA savedQA = qaService.save(qa);
//
//        String uploadDir = "./qa-images/" + savedQA.getQaId();
//        Path uploadPath = Paths.get(uploadDir);
//        if(!Files.exists(uploadPath)){
//            Files.createDirectories(uploadPath);
//        }
//
//        try(InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e){
//            throw new IOException();
//        }
//        System.out.println(qaService.findByQaId(savedQA.getQaId()).getImg());
        qaService.testimage(multipartFile);
        return "testiterate";
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
