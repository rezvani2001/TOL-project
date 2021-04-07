package GUI;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Main;
import logic.processData.State;

public class TransitionEdit extends Stage {
    private TextField inputName;
    private ComboBox<State> statesForStart;
    private ComboBox<State> statesForEnd;
    private ComboBox<String> alphabets;
    private VBox mainPane;

    public TransitionEdit() {
        this.makeScene();
    }

    private void makeScene() {
        makeMainBody();
        Scene scene = new Scene(this.mainPane, 800, 800);
        this.setScene(scene);
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }

    private void makeMainBody() {
        this.mainPane = new VBox(10);
        this.makeNamePart();
        this.makeStartStatePart();
        this.makeEndStatePart();
        this.makeTransitionLabelPart();
        this.makeApplyButton();
    }

    private void makeApplyButton() {
        Button applyButton = new Button("Apply");
        this.mainPane.getChildren().add(applyButton);
    }

    private void makeNamePart() {
        Label nameLabel = new Label("Name :");
        this.inputName = new TextField();
        this.addNewPart(nameLabel, this.inputName);
    }

    private void makeTransitionLabelPart() {
        Label transitionLabel = new Label("Alphabet :");
        this.alphabets = new ComboBox<>((ObservableList<String>) Main.automatas.alphabets);
        this.addNewPart(transitionLabel, this.alphabets);
    }

    private void makeStartStatePart() {
        Label transitionStartPoint = new Label("From :");
        this.statesForStart = new ComboBox<>((ObservableList<State>) Main.automatas.states);
        this.addNewPart(transitionStartPoint, this.statesForStart);
    }

    private void makeEndStatePart() {
        Label transitionEndPoint = new Label("To :");
        this.statesForEnd = new ComboBox<>((ObservableList<State>) Main.automatas.states);
        this.addNewPart(transitionEndPoint, this.statesForEnd);
    }

    private void addNewPart(Label titleLabel, TextField inputText) {
        HBox handler = new HBox(10);
        handler.getChildren().addAll(titleLabel, inputText);
        this.mainPane.getChildren().add(handler);
    }

    private <T> void addNewPart(Label titleLabel, ComboBox<T> input) {
        HBox handler = new HBox(10);
        handler.getChildren().addAll(titleLabel, input);
        this.mainPane.getChildren().add(handler);
    }
}
