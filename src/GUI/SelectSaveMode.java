package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class SelectSaveMode extends Stage {
    private BorderPane root;

    public SelectSaveMode() {
        this.makeScene();
    }

    private void makeScene() {
        root = new BorderPane();
        this.makeButtons();
        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add("GUI/CssFiles/SaveModeStyle.css");
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }

    private void makeButtons() {
        HBox hBox = new HBox(10);
        Button saveNewFile = new Button("New File");
        Button saveExistingFile = new Button("Brows...");
        saveExistingFile.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("just xml files", "*.xml"));
            File handler = fileChooser.showOpenDialog(stage);
            stage.setOnCloseRequest(event1 -> {
                if (handler != null) {
                    Draw.save(handler);
                    this.close();
                }
            });
        });

        saveNewFile.setOnMouseClicked(event -> {
            Draw.save(null);
            this.close();
        });

        hBox.getChildren().addAll(saveExistingFile, saveNewFile);
        root.setCenter(hBox);
    }
}
