package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.processData.State;

public class CircleEdit extends Stage {
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

    public CircleEdit(State state) {
        this.state = state;
        this.mainPane = new VBox(10);
        this.makeScene();
        this.setTitle(String.format("Edit State %s", this.state.name));
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
        Button saveButton = new Button("Apply");
        VBox.setMargin(saveButton, new Insets(20, 0, 0, 0));
        saveButton.setOnMouseClicked(event -> {
            String editedName = this.inputName.getText();
            double editedCenterX = Double.parseDouble(this.inputCenterX.getText());
            double editedCenterY = Double.parseDouble(this.inputCenterY.getText());
            boolean editedIsFinalState = this.isFinalState.isSelected();
            boolean editedIsInitialState = this.isInitialState.isSelected();
            this.state = new State(editedIsFinalState, editedIsInitialState, editedName, editedCenterX, editedCenterY);
            this.close();
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
        this.inputCenterX = new TextField(String.valueOf(this.state.centerX));
        this.mainPane.getChildren().add(this.addNewPart(titleCenterX, this.inputCenterX));
    }

    private void makeCenterY() {
        Label titleCenterY = new Label("CenterY :");
        this.inputCenterY = new TextField(String.valueOf(this.state.centerY));
        this.mainPane.getChildren().add(this.addNewPart(titleCenterY, this.inputCenterY));
    }

    private void makeName() {
        Label titleName = new Label("Name :");
        this.inputName = new TextField(this.state.name);
        this.mainPane.getChildren().add(this.addNewPart(titleName, this.inputName));
    }

    private void makeIsFinal() {
        this.isFinalState = new CheckBox("IsFinal");
        this.isFinalState.setSelected(this.state.isFinal);
        this.mainPane.getChildren().add(this.addNewPart(this.isFinalState));
    }

    private void makeIsInitial() {
        this.isInitialState = new CheckBox("IsInitial");
        this.isInitialState.setSelected(this.state.isInitial);
        this.mainPane.getChildren().add(this.addNewPart(this.isInitialState));
    }

    private HBox addNewPart(Label label, TextField textInput) {
        HBox pane = new HBox(10);
        pane.getChildren().addAll(label, textInput);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private HBox addNewPart(CheckBox checkBox) {
        HBox pane = new HBox(10);
        pane.getChildren().add(checkBox);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}
