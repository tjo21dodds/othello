import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BoardController<T> {
    private T[] data;
    private Integer[][] cells;
    private Integer radius;
    private Integer width;

    public T getCell(int x, int y){
        return this.data[y*width + x];
    }
    public T getCell(int index) { return this.data[index];}
    public void setCell(int x, int y, T val){
        this.data[y*width + x] = val;
    }
    public void setCell(int index, T val){
        this.data[index] = val;
    }

    public Integer[][] getCellsPos(){
        return this.cells;
    }

    public Integer[] indexToPos(Integer index){
        Integer x = index%width;
        Integer[] a = new Integer[]{x, (index-x)/width};
        return a;
    }

    public boolean isInsideBound(Integer[] a){
        return a[1]*width + a[0] < data.length;
    }

    public Integer getHeight(){
        return this.data.length/width;
    }
    public Integer getWidth(){
        return this.width;
    }
    public BoardController(T defaultCell, int width, int height, int radius){
        this.data = (T[]) new Object[height*width];
        this.width = width;
        for (int i = 0; i< height; i++){
            for (int a = 0; a<width; a++){
                data[i*width + a] = defaultCell;
            }
        }
        this.radius = radius;
        Double adj = radius * Math.cos(Math.PI/3);
        Double opp = radius * Math.sin(Math.PI/6);
        Integer adjInt = adj.intValue();
        Integer oppInt = opp.intValue();
        this.cells = new Integer[height*width][];
        Integer index = 0;
        for (int row = 0; row < height; row++){
            Integer offset = row /2;
            if (row % 2 == 0){
                index = addRow(false,width, new Integer[]{100,100+(offset*(2*(oppInt+radius)))},row, index);
            }
            else{
                index = addRow(true,width, new Integer[]{100,100+(offset*(2*(oppInt+radius)))},row, index);
            }
        }
    }
    private Integer addRow(Boolean isOdd, Integer length, Integer[] initPos, Integer height, Integer index){
        Double adj = radius * Math.cos(Math.PI/6);
        Double opp = radius * Math.sin(Math.PI/6);
        Integer adjInt = adj.intValue();
        Integer oppInt = opp.intValue();
        for (int i = 0; i < length; i++){
            if (!isOdd){
                Integer x = ((2*adjInt)*i) + initPos[0];
                Integer y = initPos[1];
                Integer[] pos = new Integer[]{x,y};
                this.cells[index] = pos;
            }
            else{
                Integer x = ((2*adjInt)*i) +adjInt+ initPos[0];
                Integer y = initPos[1]+oppInt+radius;
                Integer[] pos = new Integer[]{x,y};
                this.cells[index] = pos;

            }
            index = index + 1;
        }
        return index;
    }



}
