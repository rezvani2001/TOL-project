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
import logic.processData.Automatas;
import logic.processData.State;
import logic.processData.Transitions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class Draw extends Application {
    public static AnchorPane pane = new AnchorPane();

    @Override
    public void start(Stage primaryStage) {

        Main.automatas = new Automatas("new");
        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();

        Button selectInput = new Button("Select File");
        selectInput.setId("SelectInputFileButton");
        selectInput.setTooltip(new Tooltip("Select Input XML File"));

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        AtomicBoolean inputIsOpen = new AtomicBoolean(false);

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

                        Menu.getStage().close();

                        save();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "this file format is not supported").showAndWait();
                    }
                }
            } else {
                stage.setAlwaysOnTop(true);
                stage.requestFocus();
            }
        });

        Button menuButton = new Button("Menu");
        menuButton.setOnMouseClicked(event -> new Menu(selectInput, primaryStage));
        buttonBox.getChildren().addAll(menuButton);

        String cssFilePath = "GUI/CssFiles/MainPageStyle.css";
        scene.getStylesheets().add(cssFilePath);
        primaryStage.setTitle("TOL Project");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void save() {
        new Thread(() -> {
            try {
                FileWriter file = new FileWriter("output.xml");

                BufferedWriter buffer = new BufferedWriter(file);

                buffer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                buffer.write("\t<Automata type=\"" + Main.automatas.type + "\">\n");
                /*
                alphabets
                 */
                buffer.write("\t\t<Alphabets numberOfAlphabets=\"" + Main.automatas.alphabets.size() + "\">\n");
                for (String alphabet : Main.automatas.alphabets) {
                    buffer.write("\t\t\t<alphabet letter=\"" + alphabet + "\"/>\n");
                }
                buffer.write("\t\t</Alphabets>\n");
                /*
                states
                 */
                buffer.write("\t\t<States numberOfStates=\"" + Main.automatas.states.size() + "\">\n");
                List<State> finals = new ArrayList<>();
                for (State state : Main.automatas.states) {
                    buffer.write("\t\t\t<state name=\"" + state.name + "\" positionX=\"" + state.centerX +
                            "\" positionY=\"" + state.centerY + "\"/>\n");
                    if (state.isFinal) finals.add(state);
                }

                buffer.write("\t\t\t<initialState name=\"" + Main.automatas.initial+ "\"/>\n");
                buffer.write("\t\t\t<FinalStates numberOfFinalStates=\"" + finals.size() + "\">\n");
                for (State finalState : finals){
                    buffer.write("\t\t\t\t<finalState name=\"" + finalState.name + "\"/>\n");
                }
                buffer.write("\t\t\t</FinalStates>\n");

                buffer.write("\t\t</States>\n");

                /*
                transitions
                 */
                buffer.write("\t\t<Transitions numberOfTrans=\"" + Main.automatas.transitions.size() + "\">\n");
                for (Transitions transition : Main.automatas.transitions) {
                    buffer.write("\t\t\t<transition name=\"" + transition.name + "\" source=\"" +
                            transition.start + "\" destination=\"" + transition.end + "\" label=\"" +
                            transition.label + "\"/>\n");
                }
                buffer.write("\t\t</Transitions>\n");

                buffer.write("\t</Automata>\n");
                buffer.flush();
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}


