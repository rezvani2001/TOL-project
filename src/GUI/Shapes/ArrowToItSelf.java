package GUI.Shapes;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class ArrowToItSelf {
    /*
     * array labels
     */
    public String label;

    /*
     * the arrow name that specifies the arrow
     */
    public String name;

    /*
     * the centerX of state circle
     */
    public double x;

    /*
     * the centerY of state circle
     */
    public double y;

    /*
     * the attribute that contains arrays GUI body
     */
    public AnchorPane pane;

    public ArrowToItSelf(double x, double y, String label, String name){
        this.x = x;
        this.y = y;
        this.label = label;
        this.name = name;
        pane = arrayToItSelf();
    }

    /*
     * a method to generate array body and store it in pane field
     */
    private AnchorPane arrayToItSelf (){
        AnchorPane anchor = new AnchorPane();

        Path path = new Path();

        MoveTo moveTo = new MoveTo();

        moveTo.setX(0);
        moveTo.setY(0);

        QuadCurveTo first = new QuadCurveTo();
        first.setControlY(0);
        first.setControlX(-25);
        first.setX(-25);
        first.setY(-25);


        QuadCurveTo sec = new QuadCurveTo();
        sec.setControlX(-25);
        sec.setControlY(-50);
        sec.setX(0);
        sec.setY(-50);

        QuadCurveTo third = new QuadCurveTo();
        third.setControlX(25);
        third.setControlY(-50);
        third.setX(25);
        third.setY(-25);


        QuadCurveTo forth = new QuadCurveTo();
        forth.setControlX(25);
        forth.setControlY(0);
        forth.setX(0);
        forth.setY(0);

        LineTo firstLine = new LineTo();
        firstLine.setX(5);
        firstLine.setY(-5);

        MoveTo move = new MoveTo();
        move.setY(0);
        move.setX(0);

        LineTo secondLine = new LineTo();
        secondLine.setY(5);
        secondLine.setX(5);

        path.getElements().add(moveTo);
        path.getElements().add(first);
        path.getElements().add(sec);
        path.getElements().add(third);
        path.getElements().add(forth);
        path.getElements().add(firstLine);
        path.getElements().add(move);
        path.getElements().add(secondLine);


        path.setStrokeWidth(2);
        path.setStroke(Color.BLACK);

        Label label = new Label(this.label);

        label.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; " +
                "-fx-border-width: 1; -fx-border-color: red");

        path.setStroke(Color.BLACK);
        path.setStrokeWidth(2);

        anchor.getChildren().add(path);
        anchor.getChildren().add(label);


        AnchorPane.setLeftAnchor(path , 0.0);
        AnchorPane.setTopAnchor(path , -45.0);

        AnchorPane.setLeftAnchor(label , 25 - 2.5 * this.label.length());
        AnchorPane.setTopAnchor(label , -27.5);

        return anchor;
    }
}
