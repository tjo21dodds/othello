import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BoardController {
    private TileOccupation[][] data;
    public TileOccupation getCell(int x, int y){
        return this.data[y][x];
    }
    public void setCell(int x, int y, TileOccupation val){
        this.data[y][x] = val;
    }

    public TileOccupation[][] getData(){
        return this.data;
    }

    public Integer getHeight(){
        return this.data.length;
    }
    public Integer getWidth(){
        return this.data[0].length;
    }
    public BoardController(TileOccupation defaultCell, int width, int height){
        this.data = new TileOccupation[height][];
        for (int i = 0; i< height; i++){
            data[i] = new TileOccupation[width];
            for (int a = 0; a<width; a++){
                data[i][a] = defaultCell;
            }
        }
    }



}
