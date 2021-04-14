package logic.processData;


import java.util.ArrayList;
import java.util.List;

public class Automatas {
    public String type;

    public List<String> alphabets;

    public List<State> states;
    public State initial;

    public List<Transitions> transitions;

    public Automatas(String type){
        this.type = type;

        alphabets = new ArrayList<>();
        alphabets.add(String.valueOf((char) 955));

        states = new ArrayList<>();
        transitions = new ArrayList<>();
    }
}
