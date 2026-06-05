import java.awt.Color;

// S 型方塊（Z 的鏡像）
public class TetrominoS extends Tetromino {
    public TetrominoS() {
        color = Color.GREEN;
        // S 型有兩個旋轉狀態（水平與垂直）
        shapes = new int[][][] {
            {
                {0,1,1,0},
                {1,1,0,0},
                {0,0,0,0},
                {0,0,0,0}
            },
            {
                {0,1,0,0},
                {0,1,1,0},
                {0,0,1,0},
                {0,0,0,0}
            }
        };
    }

    @Override
    public Tetromino createNew() {
        return new TetrominoS();
    }
}
