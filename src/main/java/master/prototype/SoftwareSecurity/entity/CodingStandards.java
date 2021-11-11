package master.prototype.SoftwareSecurity.entity;

import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class CodingStandards {
    @Id
    private int id;

    private String name;
    @Lob
    private String description;
    @Lob
    private String mitigation;
    @Lob
    private String detection;
    @Lob
    private String example_code;

    private static final String FILENAME_CODING_STANDARDS = "src/main/resources/datafiles/CodingStandards.xml";

    public CodingStandards(int id,String name, String description, String mitigation, String detection, String example_code) {
        this.id = id;
        this.description = description;
        this.mitigation = mitigation;
        this.detection = detection;
        this.example_code = example_code;
        this.name = name;
    }

    public static void main(String[] args) {
        CodingStandards codingStandard = new CodingStandards();
        List<CodingStandards> codingStandards = codingStandard.createCodingStandards();
    }
    public CodingStandards() {}

    public List<CodingStandards> createCodingStandards() {
        List<CodingStandards> codingStandards = new ArrayList<>();
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(FILENAME_CODING_STANDARDS));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("Weakness");

            for (int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    int id = Integer.parseInt((element.getAttribute("ID")));
                    String description;
                    String mitigation;
                    String detection;
                    String example_code;
                    String name;
                    if(element.getElementsByTagName("Potential_Mitigations").item(0) == null){
                        mitigation = "No mitigation found.";
                    }else{
                        mitigation = element.getElementsByTagName("Potential_Mitigations").item(0).getTextContent();
                    }
                    if(element.getElementsByTagName("Description").item(0) == null){
                        description = "No description found.";
                    }else{
                        if(!(element.getElementsByTagName("Extended_Description").item(0) == null)) {
                            description = element.getElementsByTagName("Description").item(0).getTextContent()
                                    + "\n" + element.getElementsByTagName("Extended_Description").item(0).getTextContent();
                        }
                        else{
                            description = element.getElementsByTagName("Description").item(0).getTextContent();
                        }
                    }
                    if(element.getAttribute("Name") == null){
                        name = "No name found.";
                    }else{
                        name = element.getAttribute("Name");
                    }
                    if(element.getElementsByTagName("Detection_Methods").item(0) == null) {
                        detection = "No detection method found";
                    }
                    else{
                        detection = element.getElementsByTagName("Detection_Methods").item(0).getTextContent();
                    }
                    if(element.getElementsByTagName("Example_Code").item(0) == null) {
                        example_code = "No detection method found";
                    }
                    else {
                        example_code = element.getElementsByTagName("Example_Code").item(0).getTextContent();
                    }

                    CodingStandards codingStandard = new CodingStandards(id,name, description, mitigation, detection, example_code);
                    codingStandards.add(codingStandard);

                }}

        } catch(IOException | ParserConfigurationException | SAXException e){
            e.printStackTrace();
        }
        return codingStandards;
    }

}
