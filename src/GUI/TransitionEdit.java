package GUI;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
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
import logic.processData.Transitions;

public class TransitionEdit extends Stage {
    private final Transitions selectedTransition;
    private TextField inputName;
    private ComboBox<State> statesForStart;
    private ComboBox<State> statesForEnd;
    private ComboBox<String> alphabets;
    private VBox mainPane;

    public TransitionEdit(Transitions selectedTransition) {
        this.selectedTransition = selectedTransition;
        this.makeScene();
    }

    private void makeScene() {
        makeMainBody();
        Scene scene = new Scene(this.mainPane, 450, 300);
        String cssFilePath = "GUI/CssFiles/TransitionEditPageStyle.css";
        scene.getStylesheets().add(cssFilePath);
        this.setScene(scene);
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle(String.format("Edit Transition %s", this.selectedTransition.name));
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
        Button applyButton = new Button("Apply");
        applyButton.setOnMouseClicked(event -> {
            selectedTransition.start.outputTR.remove(selectedTransition);
            selectedTransition.end.inputTR.remove(selectedTransition);

            this.selectedTransition.start = this.statesForStart.getValue();
            this.selectedTransition.end = this.statesForEnd.getValue();
            this.selectedTransition.label = this.alphabets.getValue();
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

            this.close();
        });
        this.mainPane.getChildren().add(applyButton);
    }

    private void makeNamePart() {
        Label nameLabel = new Label("Name :");
        this.inputName = new TextField(this.selectedTransition.name);
        this.addNewPart(nameLabel, this.inputName);
    }

    private void makeTransitionLabelPart() {
        Label transitionLabel = new Label("Alphabet :");
        this.alphabets = new ComboBox<>(FXCollections.observableArrayList(Main.automatas.alphabets));
        this.alphabets.getSelectionModel().select(Main.automatas.alphabets.indexOf(this.selectedTransition.label));
        this.addNewPart(transitionLabel, this.alphabets);
    }

    private void makeStartStatePart() {
        Label transitionStartPoint = new Label("From :");
        this.statesForStart = new ComboBox<>(FXCollections.observableArrayList(Main.automatas.states));
        this.statesForStart.getSelectionModel().select(Main.automatas.states.indexOf(this.selectedTransition.start));
        this.addNewPart(transitionStartPoint, this.statesForStart);
    }

    private void makeEndStatePart() {
        Label transitionEndPoint = new Label("To :");
        this.statesForEnd = new ComboBox<>(FXCollections.observableArrayList(Main.automatas.states));
        this.statesForEnd.getSelectionModel().select(Main.automatas.states.indexOf(this.selectedTransition.end));
        this.addNewPart(transitionEndPoint, this.statesForEnd);
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
}
