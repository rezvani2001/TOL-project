package GUI;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
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

public class StateAddOrEdit extends Stage {
    // the clicked circle state and the main pane of this stage
    private State state;
    private final VBox mainPane;

    // TextArea for centerXPart of this stage
    TextField inputCenterX;

    // TextArea for centerYPart of this stage
    TextField inputCenterY;

    // TextArea for name of this stage
    TextField inputName;

    // CheckBox for isFinal part of this stage
    CheckBox isFinalState;

    // CheckBox for isInitial part of this stage
    CheckBox isInitialState;

    public StateAddOrEdit(String title) {
        this.mainPane = new VBox(10);
        this.makeScene();
        this.setTitle(title);
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    public StateAddOrEdit(State state, String title) {
        this.state = state;
        this.mainPane = new VBox(10);
        this.makeScene();
        this.setTitle(title);
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

    private void makeScene() {
        this.makeMainBody();
        this.makeSaveButton();
        this.mainPane.setAlignment(Pos.CENTER);
        String cssFilePath = "GUI/CssFiles/CircleEditPageStyle.css";
        Scene scene = new Scene(this.mainPane, 400, 350);
        scene.getStylesheets().add(cssFilePath);
        this.setScene(scene);
    }

    private void makeSaveButton() {
        Button saveButton = new Button();
        if (this.state == null)
            saveButton.setText("Add");
        else
            saveButton.setText("Apply");
        VBox.setMargin(saveButton, new Insets(20, 0, 0, 0));
        saveButton.setOnMouseClicked(event -> {
            if (this.state != null) {
                this.buttonActionForEditMode();
            } else {
                this.buttonActionForAddMode();
            }
        });
        this.mainPane.getChildren().add(saveButton);
    }

    private void makeMainBody() {
        this.makeName();
        this.makeCenterX();
        this.makeCenterY();
        this.makeIsFinal();
        this.makeIsInitial();
    }

    private void makeCenterX() {
        Label titleCenterX = new Label("CenterX :");
        if (this.state != null)
            this.inputCenterX = new TextField(String.valueOf((int) this.state.centerX));
        else
            this.inputCenterX = new TextField();
        this.inputCenterX.setAccessibleText("number field");
        this.mainPane.getChildren().add(this.addNewPart(titleCenterX, this.inputCenterX));
    }

    private void makeCenterY() {
        Label titleCenterY = new Label("CenterY :");
        if (this.state != null)
            this.inputCenterY = new TextField(String.valueOf((int) this.state.centerY));
        else
            this.inputCenterY = new TextField();
        this.inputCenterY.setAccessibleText("number field");
        this.mainPane.getChildren().add(this.addNewPart(titleCenterY, this.inputCenterY));
    }

    private void makeName() {
        Label titleName = new Label("Name :");
        if (this.state != null)
            this.inputName = new TextField(this.state.name);
        else
            this.inputName = new TextField();
        this.inputName.setAccessibleText("text field");
        this.mainPane.getChildren().add(this.addNewPart(titleName, this.inputName));
    }

    private void makeIsFinal() {
        this.isFinalState = new CheckBox("IsFinal");
        if (this.state != null)
            this.isFinalState.setSelected(this.state.isFinal);
        this.mainPane.getChildren().add(this.addNewPart(this.isFinalState));
    }

    private void makeIsInitial() {
        this.isInitialState = new CheckBox("IsInitial");
        if (this.state != null)
            this.isInitialState.setSelected(this.state.isInitial);
        this.mainPane.getChildren().add(this.addNewPart(this.isInitialState));
    }

    private HBox addNewPart(Label label, TextField textInput) {
        HBox pane = new HBox(10);

        if (textInput.getAccessibleText().equals("number field")) {
            textInput.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    textInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }

        pane.getChildren().addAll(label, textInput);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private void buttonActionForEditMode() {
        this.state.isInitial = this.isInitialState.isSelected();
        this.state.isFinal = this.isFinalState.isSelected();
        this.state.centerX = Double.parseDouble(this.inputCenterX.getText());
        this.state.centerY = Double.parseDouble(this.inputCenterY.getText());
        this.state.name = this.inputName.getText();

        if (isInitialState.isSelected()){
            if (Main.automatas.initial != null){
                Main.automatas.initial.isInitial = false;
                Draw.pane.getChildren().remove(Main.automatas.initial.UIState);
                Draw.pane.getChildren().add(Main.automatas.initial.statePane());
            }
            Main.automatas.initial = this.state;
        }

        new Thread(() -> {
            for (Transitions transitions : state.inputTR) {
                Platform.runLater(() -> {
                    Draw.pane.getChildren().remove(transitions.uiTR);
                    transitions.transitionPane();
                });
            }

            for (Transitions transitions : state.outputTR) {
                Platform.runLater(() -> {
                    Draw.pane.getChildren().remove(transitions.uiTR);
                    transitions.transitionPane();
                });
            }

            Platform.runLater(() -> {
                Draw.pane.getChildren().remove(state.UIState);
                Draw.pane.getChildren().add(state.statePane());
            });
        }).start();
        this.close();
    }

    private void buttonActionForAddMode() {
        if (this.inputCenterX.getText().length() == 0) {
            makeAlert("Please enter X value!");
        } else if (this.inputCenterY.getText().length() == 0) {
            makeAlert("Please enter Y value!");
        } else if (this.inputName.getText().length() == 0) {
            makeAlert("Please enter name!");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "State added successfully!", ButtonType.OK);
            alert.showAndWait();

            this.state = new State(this.isFinalState.isSelected(), this.isInitialState.isSelected(),
                    this.inputName.getText(), Double.parseDouble(this.inputCenterX.getText()),
                    Double.parseDouble(this.inputCenterY.getText()));

            if (isInitialState.isSelected()){
                if (Main.automatas.initial != null){
                    Main.automatas.initial.isInitial = false;
                    Draw.pane.getChildren().remove(Main.automatas.initial.UIState);
                    Draw.pane.getChildren().add(Main.automatas.initial.statePane());
                }
                Main.automatas.initial = this.state;
            }

            Main.automatas.states.add(this.state);
            Draw.pane.getChildren().add(this.state.statePane());
            this.close();
            Menu.getStage().close();
        }
    }

    private void makeAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        alert.showAndWait();
    }

    private HBox addNewPart(CheckBox checkBox) {
        HBox pane = new HBox(10);
        pane.getChildren().add(checkBox);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}
