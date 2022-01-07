//package master.prototype.SoftwareSecurity.entity;
//
//import lombok.Data;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import javax.persistence.*;
//import javax.xml.XMLConstants;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Entity
//public class Capec {
//    @Id
//    private int id;
//    @Lob
//    private String desc;
//    @Lob
//    private String mitigation;
//
//    private String name;
//    // private String choiceOfAttack;
//    private static final String FILENAME_DOMAINS_OF_ATTACK = "src/main/resources/datafiles/CAPECDomainsOfAttack.xml";
////    private static final String FILENAME_MECHANISMS_OF_ATTACK = "src/main/resources/datafiles/CAPECMechanismsOfAttack.xml";
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<Category> categories = new ArrayList<>();
//
//    public enum Category {
//        SOFTWARE("software"), HARDWARE("hardware"), COMMUNICATIONS("communications"), SUPPLY_CHAIN("supply_chain"),
//        SOCIAL_ENGINEERING("social_engineering"), PHYSICAL_SECURITY("physical_security"), UNSPECIFIED("unspecified");
//
//        private String category;
//
//        Category(String category){
//            this.category = category;
//        }
//        public String getCategory() {
//            return this.category;
//        }
//    }
//
//    public Capec(int id, String description, String mitigation, String name){
//        this.id = id;
//        this.desc = description;
//        this.mitigation = mitigation;
//        this.name = name;
//    }
//
//    public List<Capec> createCapecDomainsOfAttack(){
//        List<Capec> capecs = new ArrayList<>();
//        try{
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//            factory.setNamespaceAware(true);
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(new File(FILENAME_DOMAINS_OF_ATTACK));
////            Document doc1 = builder.parse(new File(FILENAME_MECHANISMS_OF_ATTACK));
//
//            doc.getDocumentElement().normalize();
////            doc1.getDocumentElement().normalize(); Mech. of attack.
//
//            NodeList list = doc.getElementsByTagName("Attack_Pattern");
////            NodeList list1 = doc1.getElementsByTagName("Attack_Pattern"); Mechanisms of attack - same id, desc, mit tho.
//            for (int i = 0; i < list.getLength(); i++){
//                Node node = list.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//
//                    Element element = (Element) node;
//
//                    int id = Integer.parseInt((element.getAttribute("ID")));
//                    String description;
//                    String mitigation;
//                    String name;
//                    if(element.getElementsByTagName("Mitigations").item(0) == null){
//                        mitigation = "No mitigation found.";
//                    }else{
//                        mitigation = element.getElementsByTagName("Mitigations").item(0).getTextContent();
//                    }
//                    if(element.getElementsByTagName("Description").item(0) == null){
//                        description = "No description found.";
//                    }else{
//                        description = element.getElementsByTagName("Description").item(0).getTextContent();
//                    }
//                    if(element.getAttribute("Name") == null){
//                        name = "No name found.";
//                    }else{
//                        name = element.getAttribute("Name");
//                    }
//                    if(!(mitigation == "No mitigation found.")) // Comment out if want to include Capecs without mitigation.
//                    {
//                        Capec capec = new Capec(id, description, mitigation, name);
//                        capecs.add(capec);
//                    }
//                }
//            }
//
////            for(int i = 0; i< list1.getLength(); i++) {
////                Node node = list1.item(i);
////                if (node.getNodeType() == Node.ELEMENT_NODE) {
////
////                    Element element = (Element) node;
////
////                    int id = Integer.parseInt((element.getAttribute("ID")));
////
////                    String description;
////                    String mitigation;
////                    String name;
////                    if(element.getElementsByTagName("Mitigations").item(0) == null){
////                        mitigation = "No mitigation found.";
////                    }else{
////                        mitigation = element.getElementsByTagName("Mitigations").item(0).getTextContent();
////                    }
////                    if(element.getElementsByTagName("Description").item(0) == null){
////                        description = "No description found.";
////                    }else{
////                        description = element.getElementsByTagName("Description").item(0).getTextContent();
////                    }
////                    if(element.getAttribute("Name") == null){
////                        name = "No name found.";
////                    }else{
////                        name = element.getAttribute("Name");
////                    }
////
////                    Capec capec = new Capec(id, description, mitigation, name);
////                    if(!capecs.contains(capec)) {
////                        System.out.println(id);
////                        capecs.add(capec);
////                    }
////                }
////            }
//
//        } catch(IOException | ParserConfigurationException | SAXException e){
//            e.printStackTrace();
//        }
//        return capecs;
//    }
//
//    public Capec() {}
//}
