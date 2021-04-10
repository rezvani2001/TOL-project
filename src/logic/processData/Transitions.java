package logic.processData;

import GUI.Draw;
import GUI.Shapes.ArrowToItSelf;
import GUI.Shapes.RegularArrow;
import GUI.TransitionAddOrEdit;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import logic.Main;

public class Transitions {
    public String name;

    public String label;

    public State start;
    public State end;

    public boolean isLoop = false;

    public AnchorPane uiTR;

    public void transitionPane() {
        AnchorPane transitionPane;


        if (this.isLoop) {
            transitionPane = new ArrowToItSelf(this.start.centerX,
                    this.start.centerY, this.label, this.name).pane;

            transitionPane.setOnMouseClicked(event -> Platform.runLater(() -> {
                if (event.getButton() == MouseButton.PRIMARY)
                    new TransitionAddOrEdit(this);
                else if (event.getButton() == MouseButton.SECONDARY) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete transition " +
                            this.name + "?", ButtonType.YES, ButtonType.NO)
                            .showAndWait().ifPresent(buttonType -> {
                        if (buttonType == ButtonType.YES) {
                            Main.automatas.transitions.remove(this);
                            this.start.inputTR.remove(this);

                            Draw.pane.getChildren().remove(this.uiTR);
                        }
                    });
                }
            }));

            Platform.runLater(() -> {
                Draw.pane.getChildren().addAll(transitionPane);

                AnchorPane.setLeftAnchor(transitionPane, this.start.centerX * 6);
                AnchorPane.setTopAnchor(transitionPane, this.start.centerY * 6 - 6);
            });
        } else {
            transitionPane = new RegularArrow(
                    this.label, this.name,
                    this.start.centerX, this.start.centerY,
                    this.end.centerX, this.end.centerY
            ).pane;

            transitionPane.getChildren().get(0).setOnMouseClicked(event -> {
                Platform.runLater(() -> {
                    if (event.getButton() == MouseButton.PRIMARY)
                        new TransitionAddOrEdit(this);
                    else if (event.getButton() == MouseButton.SECONDARY) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete transition "
                                + this.name + "?", ButtonType.YES, ButtonType.NO)
                                .showAndWait().ifPresent(buttonType -> {
                            if (buttonType == ButtonType.YES) {
                                Main.automatas.transitions.remove(this);
                                this.start.outputTR.remove(this);
                                this.end.inputTR.remove(this);

                                Draw.pane.getChildren().remove(this.uiTR);
                            }
                        });
                    }
                });
            });


            Platform.runLater(() -> {
                Draw.pane.getChildren().addAll(transitionPane);

                if (this.start.centerY < this.end.centerY &&
                        this.start.centerX > this.end.centerX) {

                    AnchorPane.setLeftAnchor(transitionPane, this.end.centerX * 6 + 60);
                    AnchorPane.setTopAnchor(transitionPane, this.end.centerY * 6 + 30);

                } else {
                    AnchorPane.setLeftAnchor(transitionPane, this.start.centerX * 6 + 60);
                    AnchorPane.setTopAnchor(transitionPane, this.start.centerY * 6 + 30);
                }
            });
        }
        this.uiTR = transitionPane;

    }
}
