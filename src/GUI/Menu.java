package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Menu extends Stage {
    private VBox mainPane;
    private final Stage mainPage;
    private static Stage thisStage;

    public Menu(Button selectFileButton, Stage mainPage) {
        this.mainPage = mainPage;
        this.makeMainBodyAndShow(selectFileButton);
    }

    public void makeMainBodyAndShow(Button selectFileButton) {
        this.mainPane = new VBox(20);
        this.mainPane.setAlignment(Pos.CENTER);
        this.addButtonToMenu(selectFileButton);
        this.makeAddNewAlphabetButton();
        this.makeAddNewStateButton();
        this.makeAddNewTransitionButton();
        this.makeDeleteAlphabetButton();
        this.makeExitButton();
        Scene scene = new Scene(this.mainPane, 300, 500);
        scene.getStylesheets().add("GUI/CssFiles/MenuPageStyle.css");
        this.setScene(scene);
        this.setTitle("Menu");
        this.initModality(Modality.APPLICATION_MODAL);
        thisStage = this;
        this.show();
    }

    private void makeAddNewAlphabetButton() {
        Button addNewAlphabet = new Button("Add Alphabet");
        this.mainPane.getChildren().add(addNewAlphabet);
    }

    private void makeAddNewStateButton() {
        Button addNewState = new Button("Add State");
        addNewState.setOnMouseClicked(event -> {
            String title = "Add New State";
            new StateEdit(title);
        });
        this.mainPane.getChildren().add(addNewState);
    }

    private void makeAddNewTransitionButton() {
        Button addNewTransition = new Button("Add Transition");
        this.mainPane.getChildren().add(addNewTransition);
    }

    private void makeDeleteAlphabetButton() {
        Button deleteAlphabet = new Button("Delete Alphabet");
        this.mainPane.getChildren().add(deleteAlphabet);
    }

    private void makeExitButton() {
        Button exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(event -> {
            this.close();
            this.mainPage.close();
        });
        this.mainPane.getChildren().add(exitButton);
    }

    private void addButtonToMenu(Button button) {
        this.mainPane.getChildren().add(button);
    }

    public static Stage getStage() {
        return thisStage;
    }
}
