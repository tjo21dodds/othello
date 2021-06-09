import java.util.ArrayList;

public class GameController {

    public static Integer[] nextVal(Double[] dir, Integer[] start, Integer i){
        return MathToolkit.sumVector(start, MathToolkit.scale(i+1,dir));
    }

    public static Boolean checkLine(BoardController<TileOccupation> boardController, TileOccupation tileOccupation, Double[] dir, Integer[] start){
        if (tileOccupation == TileOccupation.EMPTY){
            return false;
        }
        else{
            ArrayList<Integer[]> cellsToChange = new ArrayList<>();
            for (int i = 1; boardController.isInsideBound(nextVal(dir,start,i)); i++){
                Integer[] newPos = nextVal(dir,start,i);
                TileOccupation t = boardController.getCell(newPos[0],newPos[1]);
                if (t == TileOccupation.EMPTY){
                    return false;
                }
                else if (t == tileOccupation){
                    if (cellsToChange.size() == 0){
                        return false;
                    }
                    else{
                        for (Integer[] cell: cellsToChange){
                            boardController.setCell(cell[0],cell[1], tileOccupation);
                        }
                        return true;
                    }
                }
                else{
                    cellsToChange.add(newPos);
                }
            }
        }
        return false;
    }
    public static Boolean checkMove(BoardController<TileOccupation> boardController, TileOccupation tileOccupation, Integer[] pos){
        Double[][] dirs = new Double[][]{ new Double[]{-1.0,0.0}, new Double[]{-0.5,-1.0}, new Double[]{0.5,-1.0}, new Double[]{1.0,0.0}, new Double[]{1.0,0.5},new Double[]{1.0,0.5}};
        boolean returnValue = false;
        for (Double[] dir :dirs){
            if (checkLine(boardController, tileOccupation, dir, pos)){
                returnValue = true;
            }
        }
        return returnValue;
    }
}
