import com.sun.org.apache.xpath.internal.operations.Mult;
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

import java.net.Inet4Address;
import java.net.InetAddress;

enum TileOccupation {
    WHITE, BLACK, EMPTY;
}


public class Main extends Application {
    Integer width = 800;
    Integer height = 1000;
    Integer cellWidth = 40;
    Integer cellHeight = 40;
    Integer[] cellStart = new Integer[]{40,40};
    Canvas canvas;
    MultiplayerController multiplayerController;
    MultiplayerController multiplayerController2;
    GraphicsContext graphicsContext;
    BoardController boardController = new BoardController(TileOccupation.EMPTY,10,10);
    Boolean isWhite = true;
    Boolean isAI = false;
    Boolean isMultiplayer = true;
    TileOccupation playerColor = TileOccupation.WHITE;
    @Override
    public void start(Stage stage) throws Exception {
        if (this.isMultiplayer){

        }
        Pane pane = new Pane();
        this.canvas = new Canvas();
        boardController.setCell(4,4,TileOccupation.BLACK);
        boardController.setCell(5,4,TileOccupation.WHITE);
        boardController.setCell(4,5,TileOccupation.WHITE);
        boardController.setCell(5,5,TileOccupation.BLACK);

        canvas.setHeight(height);
        canvas.setWidth(width);

        graphicsContext = canvas.getGraphicsContext2D();

        drawGrid(Color.DARKGREEN);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Double doubleX = mouseEvent.getX();
                Double doubleY = mouseEvent.getY();
                Integer[] index = resolveClick(doubleX.intValue(), doubleY.intValue());
                TileOccupation[][] data = (TileOccupation[][]) boardController.getData();
                if (GameController.isInsideBound((TileOccupation[][]) data, index)) {
                    TileOccupation t = (isWhite) ? TileOccupation.WHITE : TileOccupation.BLACK;
                    if (GameController.checkMove(boardController, t, index)) {
                        System.out.println(index[0]);
                        System.out.println(index[1]);
                        boardController.setCell(index[0], index[1], t);
                        isWhite = !isWhite;
                        drawGrid(Color.GREEN);
                        if (isAI){
                            //Gets AI Move
                            isWhite = !isWhite;
                            drawGrid(Color.GREEN);
                        }
                        else if (isMultiplayer){
                            // Gets Other players move
                            isWhite = !isWhite;
                            drawGrid(Color.GREEN);
                        }
                    }
                }
            }
        });
        multiplayerController = new MultiplayerController(true,(Inet4Address) Inet4Address.getByName("176.58.104.92"),1700);
        pane.getChildren().add(canvas);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public Integer[] resolveClick(Integer x, Integer y){
        Integer[] pos = new Integer[]{x,y};
        Integer[] reducedPos = new Integer[]{(x-cellStart[0])/cellWidth, (y-cellStart[1])/cellHeight};
        return reducedPos;
    }


    public void drawGrid(Paint paint){
        graphicsContext.setFill(Color.LIMEGREEN);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        graphicsContext.setStroke(paint);
        for (int x = 0; x< boardController.getWidth(); x++){
            for (int y = 0; y< boardController.getHeight(); y++){
                int xStart = cellStart[0] + (x*cellWidth);
                int yStart = cellStart[1] + (y*cellHeight);
                graphicsContext.strokeRect(xStart, yStart,cellWidth, cellHeight);
                if (boardController.getCell(x,y) != TileOccupation.EMPTY){
                    if (boardController.getCell(x,y) == TileOccupation.BLACK){
                        graphicsContext.setFill(Color.BLACK);
                        graphicsContext.fillOval(xStart,yStart,cellWidth,cellHeight);
                    }
                    else {
                        graphicsContext.setFill(Color.WHITE);
                        graphicsContext.fillOval(xStart,yStart,cellWidth,cellHeight);
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
