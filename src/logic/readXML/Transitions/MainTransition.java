package logic.readXML.Transitions;

import javax.xml.bind.annotation.XmlAttribute;

public class MainTransition {
    @XmlAttribute
    public String name;

    @XmlAttribute
    public String source;

    @XmlAttribute
    public String destination;

    @XmlAttribute
    public String label;
}
