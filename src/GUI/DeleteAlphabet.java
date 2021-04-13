package GUI;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Main;

public class DeleteAlphabet extends Stage {
    private final VBox mainPain;
    private ComboBox<String> currentAlphabets;

    public DeleteAlphabet() {
        this.mainPain = new VBox(10);
        this.startStage();
    }

    private void startStage() {
        this.makeAlphabetChooser();
        this.makeDeleteButton();
        Scene scene = new Scene(this.mainPain, 200, 100);
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }

    private void makeAlphabetChooser() {
        HBox hBox = new HBox(10);
        Label textLabel = new Label("Pleas select alphabet to delete : ");
        this.currentAlphabets = new ComboBox<>(FXCollections.observableArrayList(Main.automatas.alphabets));
        hBox.getChildren().addAll(textLabel, currentAlphabets);
        this.mainPain.getChildren().add(hBox);
    }

    private void makeDeleteButton() {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnMouseClicked(event -> {
            Main.automatas.alphabets.remove(this.currentAlphabets.getValue());
            this.close();
        });
        this.mainPain.getChildren().add(deleteButton);
    }
}
