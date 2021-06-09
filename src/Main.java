import com.sun.webkit.graphics.GraphicsDecoder;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

enum TileOccupation {
    WHITE, BLACK, EMPTY;
}


public class Main extends Application {
    Integer width = 800;
    Integer height = 1000;
    Integer radius = 40;
    Canvas canvas;
    GraphicsContext graphicsContext;
    BoardController<TileOccupation> boardController = new BoardController<TileOccupation>(TileOccupation.EMPTY,10,10, radius);
    Boolean isWhite = true;
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        this.canvas = new Canvas();
        boardController.setCell(4,4,TileOccupation.BLACK);
        boardController.setCell(5,4,TileOccupation.WHITE);
        boardController.setCell(4,5,TileOccupation.WHITE);
        boardController.setCell(5,5,TileOccupation.BLACK);
        canvas.setHeight(height);

        canvas.setWidth(width);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHEAT);

        drawGrid();

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Double doubleX = mouseEvent.getX();
                Double doubleY = mouseEvent.getY();
                Integer index = resolveClick(doubleX.intValue(), doubleY.intValue());
                Integer[] pos = boardController.indexToPos(index);
                TileOccupation t = (isWhite) ? TileOccupation.WHITE : TileOccupation.BLACK;
                if (GameController.checkMove(boardController,t,pos)){
                    boardController.setCell(index,t);
                    isWhite = !isWhite;
                    drawGrid();
                }
            }
        });
        pane.getChildren().add(canvas);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public Integer resolveClick(Integer x, Integer y){
        Integer[] pos = new Integer[]{x,y};
        Integer[][] cells = boardController.getCellsPos();
        Integer closestIndex = 0;
        Integer distance = MathToolkit.euclidDistance(pos, cells[closestIndex]);
        for (int i = 0; i < cells.length; i++){
            Integer nDistance = MathToolkit.euclidDistance(pos, cells[i]);
            if (nDistance < distance){
                closestIndex = i;
                distance = nDistance;
            }
        }
        return closestIndex;
    }


    public void drawGrid(){
        graphicsContext.setFill(Color.FORESTGREEN);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        Integer[][] cells = boardController.getCellsPos();
        for (int i = 0; i < cells.length; i++){
            drawHexagon(cells[i], radius, Color.GREEN,boardController.getCell(i));
            graphicsContext.setStroke(Color.BLUE);
            Integer[] pos = boardController.indexToPos(i);
            String msg = "("+String.valueOf(pos[1])+","+String.valueOf(pos[0])+")";
            graphicsContext.strokeText(msg,cells[i][0],cells[i][1]);
        }
    }

    public void drawHexagon(Integer[] centerPos, Integer radius, Paint stroke, TileOccupation isFill){
        graphicsContext.setStroke(stroke);

        Double bearing = Math.PI/6;
        Integer[][] points = new Integer[6][];
        for (int i = 0; i < 6; i++){
            points[i] = posFromPoint(centerPos, radius,bearing);
            bearing = bearing +( Math.PI/3);
        }
        double[] xs = new double[points.length];
        double[] ys = new double[points.length];
        for (int b = 0; b < xs.length; b++){
            xs[b] = points[b][0];
            ys[b] = points[b][1];
        }
        if (isFill == TileOccupation.EMPTY) {
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillPolygon(xs, ys, 6);
        }
        else if (isFill == TileOccupation.BLACK){
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillPolygon(xs, ys, 6);
        }
        else if (isFill == TileOccupation.WHITE){
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillPolygon(xs, ys, 6);
        }

        graphicsContext.strokePolygon(xs,ys,6);

    }

    public Integer[] posFromPoint(Integer[] point, Integer radius, Double bearing){
        Double x = radius * Math.cos(bearing) + point[0];
        Double y = radius * Math.sin(bearing) + point[1];
        return new Integer[]{x.intValue(),y.intValue()};
    }

    public static void main(String[] args){
        launch(args);
    }
}
