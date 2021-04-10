package GUI;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Main;
import logic.processData.State;
import logic.processData.Transitions;

import java.util.ArrayList;

public class TransitionAddOrEdit extends Stage {
    private Transitions selectedTransition;
    private TextField inputName;
    private ComboBox<State> statesForStartComboBox;
    private ComboBox<State> statesForEndComboBox;
    private ComboBox<String> alphabetsComboBox;
    private VBox mainPane;

    public TransitionAddOrEdit(String title) {
        this.makeScene(title);
    }

    public TransitionAddOrEdit(String title, Transitions selectedTransition) {
        this.selectedTransition = selectedTransition;
        this.makeScene(title);
    }

    private void makeScene(String title) {
        makeMainBody();
        Scene scene = new Scene(this.mainPane, 450, 300);
        String cssFilePath = "GUI/CssFiles/TransitionEditPageStyle.css";
        scene.getStylesheets().add(cssFilePath);
        this.setScene(scene);
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle(title);
        this.show();
    }

    private void makeMainBody() {
        this.mainPane = new VBox(10);
        mainPane.setAlignment(Pos.CENTER);
        this.makeNamePart();
        this.makeStartStatePart();
        this.makeEndStatePart();
        this.makeTransitionLabelPart();
        this.makeApplyButton();
    }

    private void makeApplyButton() {
        Button button = new Button();
        button.setText(this.selectedTransition == null ? "Add" : "Apply");
        button.setOnMouseClicked(event -> {
            if (this.selectedTransition != null)
                this.buttonActionForEditMode();
            else
                this.buttonActionForAddMode();
            this.close();
        });
        this.mainPane.getChildren().add(button);
    }

    private void makeNamePart() {
        Label nameLabel = new Label("Name :");
        if (this.selectedTransition != null)
            this.inputName = new TextField(this.selectedTransition.name);
        else
            this.inputName = new TextField();
        this.addNewPart(nameLabel, this.inputName);
    }

    private void makeTransitionLabelPart() {
        Label transitionLabel = new Label("Alphabet :");
        if (Main.automatas.alphabets != null)
            this.alphabetsComboBox = new ComboBox<>(FXCollections.observableArrayList(Main.automatas.alphabets));
        if (this.selectedTransition != null)
            this.alphabetsComboBox.getSelectionModel().select(Main.automatas.alphabets.indexOf(this.selectedTransition.label));
        this.addNewPart(transitionLabel, this.alphabetsComboBox);
    }

    private void makeStartStatePart() {
        Label transitionStartPoint = new Label("From :");
        this.statesForStartComboBox = new ComboBox<>(FXCollections.observableArrayList(Main.automatas.states));
        if (this.selectedTransition != null)
            this.statesForStartComboBox.getSelectionModel().select(Main.automatas.states.indexOf(this.selectedTransition.start));
        this.addNewPart(transitionStartPoint, this.statesForStartComboBox);
    }

    private void makeEndStatePart() {
        Label transitionEndPoint = new Label("To :");
        this.statesForEndComboBox = new ComboBox<>(FXCollections.observableArrayList(Main.automatas.states));
        if (this.selectedTransition != null)
            this.statesForEndComboBox.getSelectionModel().select(Main.automatas.states.indexOf(this.selectedTransition.end));
        this.addNewPart(transitionEndPoint, this.statesForEndComboBox);
    }

    private void addNewPart(Label titleLabel, TextField inputText) {
        HBox handler = new HBox(10);
        handler.setAlignment(Pos.CENTER);
        handler.getChildren().addAll(titleLabel, inputText);
        this.mainPane.getChildren().add(handler);
    }

    private <T> void addNewPart(Label titleLabel, ComboBox<T> input) {
        HBox handler = new HBox(10);
        handler.setAlignment(Pos.CENTER);
        handler.getChildren().addAll(titleLabel, input);
        this.mainPane.getChildren().add(handler);
    }

    private void buttonActionForEditMode() {
        selectedTransition.start.outputTR.remove(selectedTransition);
        selectedTransition.end.inputTR.remove(selectedTransition);

        this.selectedTransition.start = this.statesForStartComboBox.getValue();
        this.selectedTransition.end = this.statesForEndComboBox.getValue();
        this.selectedTransition.label = this.alphabetsComboBox.getValue();
        this.selectedTransition.name = this.inputName.getText();

        if (selectedTransition.start == selectedTransition.end) {
            selectedTransition.isLoop = true;

            selectedTransition.start.inputTR.add(selectedTransition);

        } else {
            selectedTransition.isLoop = false;

            selectedTransition.end.inputTR.add(selectedTransition);
            selectedTransition.start.outputTR.add(selectedTransition);
        }

        Draw.pane.getChildren().remove(selectedTransition.uiTR);
        selectedTransition.transitionPane();
    }

    private void buttonActionForAddMode() {
        Transitions transition = new Transitions();
        transition.start = this.statesForStartComboBox.getValue();
        transition.end = this.statesForEndComboBox.getValue();
        transition.name = this.inputName.getText();
        transition.isLoop = transition.start == transition.end;
        transition.alphabet = new ArrayList<String>();
        transition.alphabet.add(this.alphabetsComboBox.getValue());
        // TODO restore "a" with a alphabet from existing alphabets

        Main.automatas.transitions.add(transition);

        transition.start.outputTR.add(transition);
        transition.end.inputTR.add(transition);

        transition.transitionPane();
    }
}
