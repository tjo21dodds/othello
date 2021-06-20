import java.util.ArrayList;

public class GameController {

    public static Integer[] nextVal(Integer[] dir, Integer[] start, Integer i) {
        return MathToolkit.sumVector(start, MathToolkit.scale(i, dir));
    }

    public static boolean isInsideBound(TileOccupation[][] data,Integer[] a){
        return (a[0] < data[0].length && a[1] < data.length && a[0] >= 0 && a[1] >= 0);
    }
    public static Boolean checkLine(TileOccupation[][] board, TileOccupation tileOccupation, Integer[] dir, Integer[] start) {
        if (tileOccupation == TileOccupation.EMPTY) {
            return false;
        } else {
            ArrayList<Integer[]> cellsToChange = new ArrayList<>();
            for (int i = 1; isInsideBound(board,nextVal(dir, start, i)); i++) {
                Integer[] newPos = nextVal(dir, start, i);
                TileOccupation t = board[newPos[1]][newPos[0]];
                if (t == TileOccupation.EMPTY) {
                    return false;
                } else if (t == tileOccupation) {
                    if (cellsToChange.size() == 0) {
                        return false;
                    } else {
                        for (Integer[] cell : cellsToChange) {
                            board[cell[1]][cell[0]] = tileOccupation;
                        }
                        return true;
                    }
                } else {
                    cellsToChange.add(newPos);
                }
            }
        }
        return false;
    }

    public static Boolean checkMove(TileOccupation[][] board, TileOccupation tileOccupation, Integer[] pos) {
        Integer[][] dirs = new Integer[][]{new Integer[]{-1, 0}, new Integer[]{-1, -1}, new Integer[]{0, -1}, new Integer[]{1, -1}, new Integer[]{1, 0}, new Integer[]{1, 1}, new Integer[]{0, 1}, new Integer[]{-1, 1}};
        if (board[pos[1]][pos[0]] != TileOccupation.EMPTY) {
            return false;
        }
        boolean returnValue = false;
        for (Integer[] dir : dirs) {
            if (checkLine(board, tileOccupation, dir, pos)) {
                returnValue = true;
            }
        }
        return returnValue;
    }
    public static Boolean checkMove(BoardController boardController, TileOccupation tileOccupation, Integer[] pos){
        return checkMove(boardController.getData(),tileOccupation,pos);
    }
}
