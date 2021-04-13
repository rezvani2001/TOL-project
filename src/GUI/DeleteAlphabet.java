package GUI;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
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
        this.mainPain = new VBox(30);
        this.mainPain.setAlignment(Pos.CENTER);
        this.startStage();
    }

    private void startStage() {
        this.makeAlphabetChooser();
        this.makeDeleteButton();
        Scene scene = new Scene(this.mainPain, 300, 200);
        scene.getStylesheets().add("GUI/CssFiles/DeleteAlphabetStyle.css");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle("Delete Alphabet");
        this.show();
    }

    private void makeAlphabetChooser() {
        HBox hBox = new HBox(15);
        hBox.setAlignment(Pos.CENTER);
        Label textLabel = new Label("ALPHABET : ");
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
