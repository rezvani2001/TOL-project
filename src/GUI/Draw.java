package GUI;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import logic.*;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.processData.State;
import logic.processData.Transitions;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;


public class Draw extends Application {
    public static AnchorPane pane = new AnchorPane();

    @Override
    public void start(Stage primaryStage) {

        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();

        Button selectInput = new Button("Select File");
        selectInput.setId("SelectInputFileButton");
        selectInput.setTooltip(new Tooltip("Select Input XML File"));

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        AtomicBoolean inputIsOpen = new AtomicBoolean(false);

        buttonBox.getChildren().addAll(selectInput);
        borderPane.setTop(buttonBox);

        FileChooser filePath = new FileChooser();
        filePath.setTitle("select file");

        BorderPane.setMargin(buttonBox, new Insets(20));

        pane.setId("WorkSpacePane");

        BorderPane.setMargin(pane, new Insets(0, 20, 20, 20));
        borderPane.setCenter(pane);


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);

        selectInput.setOnAction(event -> {
            if (!inputIsOpen.get()) {
                inputIsOpen.set(true);

                File selectedFile = filePath.showOpenDialog(stage);

                inputIsOpen.set(false);

                if (selectedFile != null) {

                    if (selectedFile.getPath().contains(".xml")) {
                        Thread thread = new Thread(() -> Main.main(selectedFile));

                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pane = new AnchorPane();
                        pane.setId("WorkSpacePane");
                        borderPane.setCenter(pane);

                        BorderPane.setMargin(pane, new Insets(0, 20, 20, 20));
                        borderPane.setCenter(pane);

                        thread = new Thread(() -> {
                            for (State state : Main.automatas.states) {
                                Platform.runLater(() -> pane.getChildren().add(state.statePane()));
                            }

                            for (Transitions transition : Main.automatas.transitions) {
                                transition.transitionPane();
                            }
                        });
                        thread.start();

                    } else {
                        new Alert(Alert.AlertType.ERROR, "this file format is not supported").showAndWait();
                    }
                }
            } else {
                stage.setAlwaysOnTop(true);
                stage.requestFocus();
            }
        });

        String cssFilePath = "GUI/CssFiles/MainPageStyle.css";
        scene.getStylesheets().add(cssFilePath);
        primaryStage.setTitle("TOL Project");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}


