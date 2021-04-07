package logic.readXML.States;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class StateCase {
    @XmlAttribute
    public int numberOfStates;

    @XmlElement
    public List<MainState> state;

    @XmlElement
    public InitialState initialState;

    @XmlElement
    public FinalStateCase FinalStates;
}
