package logic.readXML.States;

import javax.xml.bind.annotation.XmlAttribute;

public class MainState {
    @XmlAttribute
    public String name;

    @XmlAttribute
    public double positionX;

    @XmlAttribute
    public double positionY;
}
