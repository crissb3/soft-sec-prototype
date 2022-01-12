package master.prototype.SoftwareSecurity.controller;

//import master.prototype.SoftwareSecurity.entity.Capec;
//import master.prototype.SoftwareSecurity.entity.CodingStandards;
//import master.prototype.SoftwareSecurity.service.CapecService;
//import master.prototype.SoftwareSecurity.service.CodingStandardsService;
import master.prototype.SoftwareSecurity.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import master.prototype.SoftwareSecurity.entity.QA;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class QAController {

    @Autowired
    private QAService qaService;
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

        return "index";
    }

    @GetMapping("/addQuestion")
    public String createQuestion(Model model) {

        model.addAttribute("message", "Create questions page....");
        return "createquestion";
    }

    @GetMapping("/deleteQ")
    public String deleteQtemp(Model model) {
        model.addAttribute("message", "Successfully deleted");
        qaService.deleteAll(); // TEMPORARY
        return "index";
    }

    @PostMapping("/addQA")
    public String addQA(@RequestParam String addquestion,
                        @RequestParam String fakeanswer1,
                            @RequestParam String fakeanswer2,
                            @RequestParam String fakeanswer3,
                            @RequestParam String correctanswer,
                            @RequestParam(required = false) String tag1,
                            @RequestParam(required = false) String tag2,
                            @RequestParam(required = false) String tag3,
                            Model model) {
        if(addquestion.isEmpty()
                || fakeanswer1.isEmpty()
                || fakeanswer2.isEmpty()
                || fakeanswer3.isEmpty()
                || correctanswer.isEmpty()) {
            model.addAttribute("message", "You can't leave an empty field.");
            return "createquestion";
        }
        else {
            QA qa = qaService.shuffleAnswers(addquestion, fakeanswer1, fakeanswer2, fakeanswer3, correctanswer);
            qa.setTags(qaService.setTag(tag1, tag2, tag3));
            qaService.save(qa);
            model.addAttribute("message", "Created question: " + addquestion);
        }

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
