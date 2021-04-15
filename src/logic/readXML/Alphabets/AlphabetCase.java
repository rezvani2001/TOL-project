package logic.readXML.Alphabets;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class AlphabetCase {
    @XmlAttribute
    public int numberOfAlphabets;

    @XmlElement
    public List<MainAlphabet> alphabet = new ArrayList<>();

}
