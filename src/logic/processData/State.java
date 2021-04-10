package logic.processData;

import GUI.StateEdit;
import GUI.Draw;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import logic.Main;

import java.util.ArrayList;
import java.util.List;

public class State {
    public boolean isFinal = false;
    public boolean isInitial = false;

    public boolean hasLoop = false;

    public String name;

    public double centerX;
    public double centerY;

    public AnchorPane UIState;

    public List<Transitions> inputTR = new ArrayList<>();
    public List<Transitions> outputTR = new ArrayList<>();

    public State(boolean isFinal, boolean isInitial, String name, double centerX, double centerY) {
        this.isFinal = isFinal;
        this.isInitial = isInitial;
        this.name = name;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public State() {

    }

    public AnchorPane statePane() {
        AnchorPane circlePane = new AnchorPane();

        circlePane.setOnMouseClicked(event1 -> {
            if (event1.getButton() == MouseButton.PRIMARY) {
                String title = String.format("Edit State %s", this.name);
                Platform.runLater(() -> new StateEdit(this, title));
            } else if (event1.getButton() == MouseButton.SECONDARY) {
                new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete state " + this.name + "?", ButtonType.YES, ButtonType.NO)
                        .showAndWait().ifPresent(buttonType -> {
                    if (buttonType == ButtonType.YES) {

                        Main.automatas.states.remove(this);

                        Platform.runLater(() -> {
                            Draw.pane.getChildren().remove(circlePane);
                        });

                        for (Transitions tr : this.inputTR) {
                            Main.automatas.transitions.remove(tr);
                            tr.start.outputTR.remove(tr);
                            Platform.runLater(() -> Draw.pane.getChildren().remove(tr.uiTR));
                        }

                        for (Transitions tr : this.outputTR) {
                            Main.automatas.transitions.remove(tr);
                            tr.end.inputTR.remove(tr);
                            Platform.runLater(() -> Draw.pane.getChildren().remove(tr.uiTR));
                        }
                    }
                });
            }
        });

        Circle circle = new Circle(this.centerX, this.centerY, 26);

        if (this.isInitial && this.isFinal) {
            Circle bigCircle = new Circle(this.centerX, this.centerY, 26);

            bigCircle.setStyle("-fx-fill: lightgray; -fx-stroke: red; -fx-stroke-width: 4");
            circlePane.getChildren().add(bigCircle);

            circle.setStyle("-fx-fill: lightgray; -fx-stroke: blue; -fx-stroke-width: 3");
            circle.setRadius(23);

            AnchorPane.setTopAnchor(bigCircle, 0.0);
            AnchorPane.setLeftAnchor(bigCircle, 0.0);

        } else if (this.isFinal) {
            circle.setStyle("-fx-fill: lightgray; -fx-stroke: red; -fx-stroke-width: 4");
        } else if (this.isInitial) {
            circle.setStyle("-fx-fill: lightgray; -fx-stroke: blue; -fx-stroke-width: 3");
            circle.setRadius(27);
        } else {
            circle.setStyle("-fx-fill: lightgray; -fx-stroke: black; -fx-stroke-width: 2");
            circle.setRadius(28);
        }

        Label label = new Label(this.name);
        circlePane.getChildren().addAll(circle, label);

        if (this.isFinal && this.isInitial) {

            AnchorPane.setTopAnchor(circle, 3.5);
            AnchorPane.setLeftAnchor(circle, 3.5);

        } else {
            AnchorPane.setLeftAnchor(circle, 0.0);
            AnchorPane.setTopAnchor(circle, 0.0);
        }

        AnchorPane.setTopAnchor(label, 19.5);
        AnchorPane.setLeftAnchor(label, 27 - label.getText().length() * 2.5);

        Platform.runLater(() -> {
            AnchorPane.setTopAnchor(circlePane, this.centerY * 6);
            AnchorPane.setLeftAnchor(circlePane, this.centerX * 6);
        });

        this.UIState = circlePane;
        return circlePane;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
