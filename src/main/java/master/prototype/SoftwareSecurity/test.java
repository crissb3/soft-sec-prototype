//package master.prototype.SoftwareSecurity;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import javax.xml.XMLConstants;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.xpath.XPath;
//import javax.xml.xpath.XPathConstants;
//import javax.xml.xpath.XPathExpression;
//import javax.xml.xpath.XPathFactory;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class test {
//    private static final String FILENAME = "src/main/resources/datafiles/CAPECDomainsOfAttack.xml";
//    public static void main(String[] args) throws Exception {
//        try{
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//        factory.setNamespaceAware(true);
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(new File(FILENAME));
//        // asd
//        doc.getDocumentElement().normalize();
//        System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
//        NodeList list = doc.getElementsByTagName("Attack_Pattern");
//        System.out.println(list.getLength());
//        for (int i = 0; i < list.getLength(); i++){
//            Node node = list.item(i);
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//
//                Element element = (Element) node;
//
//
//                String id = element.getAttribute("ID");
//                String description = element.getElementsByTagName("Description").item(0).getTextContent();
//                String mitigation = "";
//                if(element.getElementsByTagName("Mitigations").item(0) == null){
//                    mitigation = "No mitigation found.";
//                }else{
//                    mitigation = element.getElementsByTagName("Mitigations").item(0).getTextContent();
//                }
//
//
//                System.out.println(id);
//                System.out.println(mitigation);
//        }}
//
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//
////        XPathFactory xPathFactory = XPathFactory.newInstance();
////        XPath xPath = xPathFactory.newXPath();
////
////        System.out.println("Test");
////        XPathExpression expr = xPath.compile("//*/Attack_Patterns/Attack_Pattern/@ID");
////        Object result = expr.evaluate(doc, XPathConstants.NODESET);
////        NodeList nodes = (NodeList) result;
////        System.out.println(nodes.getLength());
////        for (int  i = 0; i< nodes.getLength(); i++){
////            System.out.println(nodes.item(i).getNodeValue());
////        }
//    }
//}
//
