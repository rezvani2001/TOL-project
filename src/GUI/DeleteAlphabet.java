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

enum StageMode {
    ADD, DELETE
}

public class DeleteAlphabet extends Stage {
    private final VBox mainPain;
    private ComboBox<String> currentAlphabets;
    private StageMode stageMode;
    private TextField alphabetNameInput;

    public DeleteAlphabet(StageMode stageMode) {
        this.mainPain = new VBox(30);
        this.mainPain.setAlignment(Pos.CENTER);
        this.stageMode = stageMode;
        this.startStage();
    }

    private void startStage() {
        if (this.stageMode == StageMode.DELETE)
            this.makeAlphabetChooser();
        else
            this.makeTextField();
        this.makeDeleteButton();
        Scene scene = new Scene(this.mainPain, 400, 200);
        scene.getStylesheets().add("GUI/CssFiles/DeleteAlphabetStyle.css");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(this.stageMode == StageMode.DELETE ? "Delete Alphabet" : "Add Alphabet");
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

    private void makeTextField() {
        HBox hBox = new HBox(15);
        hBox.setAlignment(Pos.CENTER);
        Label textLabel = new Label("ALPHABET : ");
        this.alphabetNameInput = new TextField();
        hBox.getChildren().addAll(textLabel, alphabetNameInput);
        this.mainPain.getChildren().add(hBox);
    }

    private void makeDeleteButton() {
        Button deleteButton = new Button(this.stageMode == StageMode.DELETE ? "DELETE" : "ADD");
        if (this.stageMode == StageMode.DELETE)
            deleteButton.setOnMouseClicked(event -> {
                Main.automatas.alphabets.remove(this.currentAlphabets.getValue());
                this.close();
            });
        else
            deleteButton.setOnMouseClicked(event -> {
                Main.automatas.alphabets.add(this.alphabetNameInput.getText());
                this.close();
            });
        this.mainPain.getChildren().add(deleteButton);
    }
}
