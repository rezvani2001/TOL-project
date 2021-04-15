package logic.readXML.States;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class StateCase {
    @XmlAttribute
    public int numberOfStates;

    @XmlElement
    public List<MainState> state = new ArrayList<>();

    @XmlElement
    public InitialState initialState;

    @XmlElement
    public FinalStateCase FinalStates;
}
