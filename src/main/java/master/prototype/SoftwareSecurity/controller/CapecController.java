//package master.prototype.SoftwareSecurity.controller;
//
//import master.prototype.SoftwareSecurity.entity.Capec;
//import master.prototype.SoftwareSecurity.service.CapecService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//@Controller
//public class CapecController {
//    @Autowired
//    private CapecService capecService;
//
//    @GetMapping("/capecDomainsOfAttack")
//    public String capecDomainsOfAttack(Model model){
//        List<Capec> capecs = capecService.findAll();
//        model.addAttribute("capecs", capecs);
//
//        return "capeccreate";
//    }
//    @GetMapping("/searchCapecDomainsOfAttack")
//    public String searchCapecByDescription(@RequestParam String description, Model model){
//        List<Capec> capecs = capecService.findByDescription(description);
//        int size = capecs.size();
//        model.addAttribute("capecs", capecs);
//        model.addAttribute("message_hits", "Number of hits: "+size);
//        model.addAttribute("message_searched", "Containing search word: \n" + description);
//
//        return "capeccreate";
//    }
//    @GetMapping("/allCapecs")
//    public String allCapecDomOfAttack(Model model){
//        List<Capec> capecs = capecService.findAll();
//        Collections.sort(capecs, Comparator.comparing(Capec::getName));
//        model.addAttribute("capecs", capecs);
//
//        return "allcapecnames";
//    }
//    @GetMapping("/createQuestion/{id}")
//    public String createCapecQuestion(Model model, @PathVariable int id) {
//        Capec capec = capecService.findById(id);
//        model.addAttribute("capec", capec);
//        return "createquestioncapec";
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void createCapecObjects(){
//        if(capecService.findAll().isEmpty()){
//            Capec capec = new Capec();
//            List<Capec> capecs = capec.createCapecDomainsOfAttack();
//            capecService.saveAll(capecs);
//        }
//    }
//}
