package logic;

import logic.processData.Automatas;
import logic.processData.State;
import logic.processData.Transitions;
import logic.readXML.Alphabets.MainAlphabet;
import logic.readXML.Automatas.*;
import logic.readXML.States.MainFinalState;
import logic.readXML.States.MainState;
import logic.readXML.Transitions.MainTransition;

import java.io.File;
import java.util.Collections;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {
    public static Automatas automatas;


    public static void main(File file) {
        Automata automata;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Automata.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            automata = (Automata) jaxbUnmarshaller.unmarshal(file);

            processData(automata);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    /*
    indexing base data to a more understandable way
     */
    public static void processData(Automata automata) {
        Automatas destiny = new Automatas(automata.type);

        for (MainAlphabet alphabet : automata.Alphabets.alphabet) {
            destiny.alphabets.add(alphabet.letter);
        }

        for (MainState state : automata.States.state) {
            State newState = new State();

            newState.name = state.name;
            newState.centerX = state.positionX;
            newState.centerY = state.positionY;

            if (state.name.equals(automata.States.initialState.name)) {
                newState.isInitial = true;
                destiny.initial = newState;
            }

            for (MainFinalState finalState : automata.States.FinalStates.finalState) {
                if (finalState.name.equals(newState.name)) {
                    newState.isFinal = true;
                    automata.States.FinalStates.finalState.remove(finalState);
                    break;
                }
            }
            destiny.states.add(newState);
        }

        for (MainTransition transition : automata.Transitions.transition) {
            Transitions newTransition = new Transitions();

            newTransition.name = transition.name;
            newTransition.label = transition.label;
            Collections.addAll(newTransition.alphabet, transition.label.split(","));

            if (transition.source.equals(transition.destination)) {
                newTransition.isLoop = true;
                for (State state : destiny.states) {
                    if (state.name.equals(transition.source)) {
                        state.inputTR.add(newTransition);
                        state.hasLoop = true;
                        newTransition.start = newTransition.end = state;
                        break;
                    }
                }
            } else {
                boolean findFinal = false;
                boolean findStart = false;
                for (State state : destiny.states) {
                    if (!findFinal) {
                        if (state.name.equals(transition.destination)) {
                            state.inputTR.add(newTransition);
                            newTransition.end = state;
                            findFinal = true;
                        }
                    }
                    if (!findStart) {
                        if (state.name.equals(transition.source)) {
                            state.outputTR.add(newTransition);
                            newTransition.start = state;
                            findStart = true;
                        }
                    }
                    if (findFinal && findStart) break;
                }
            }
            destiny.transitions.add(newTransition);
        }

        Main.automatas = destiny;
    }

}  