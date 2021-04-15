package logic.readXML.Automatas;

import logic.readXML.Alphabets.AlphabetCase;
import logic.readXML.States.StateCase;
import logic.readXML.Transitions.TransitionCase;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "Automata")
public class Automata {

    @XmlAttribute
    public String type;

    public AlphabetCase Alphabets;

    public StateCase States;

    public TransitionCase Transitions;
}
