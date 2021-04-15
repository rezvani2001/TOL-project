package logic.readXML.Transitions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class TransitionCase {
    @XmlAttribute
    public int numberOfTrans;

    @XmlElement
    public List<MainTransition> transition = new ArrayList<>();
}
