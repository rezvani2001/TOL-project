package logic.readXML.States;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class FinalStateCase {

    @XmlAttribute
    public int numberOfFinalStates;

    @XmlElement
    public List<MainFinalState> finalState;
}
