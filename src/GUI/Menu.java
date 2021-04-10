package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Stage {
    private VBox mainPane;

    public Menu(Button selectFileButton) {
        this.makeMainBodyAndShow(selectFileButton);
    }

    public void makeMainBodyAndShow(Button selectFileButton) {
        this.mainPane = new VBox(20);
        this.addButtonToMenu(selectFileButton);
        this.makeAddNewAlphabetButton();
        this.makeAddNewStateButton();
        this.makeAddNewTransitionButton();
        this.makeDeleteAlphabetButton();
        Scene scene = new Scene(this.mainPane, 300, 600);
        this.setScene(scene);
        this.setTitle("Menu");
        this.show();
    }

    private void makeAddNewAlphabetButton() {
        Button addNewAlphabet = new Button("Add Alphabet");
        this.mainPane.getChildren().add(addNewAlphabet);
    }

    private void makeAddNewStateButton() {
        Button addNewState = new Button("Add State");
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

    private void addButtonToMenu(Button button) {
        this.mainPane.getChildren().add(button);
    }
}
