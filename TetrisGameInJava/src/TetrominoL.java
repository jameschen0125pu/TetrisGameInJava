import java.awt.Color;

// L 型方塊
public class TetrominoL extends Tetromino {
    public TetrominoL() {
        color = Color.ORANGE;
        // 定義 4 個旋轉狀態
        shapes = new int[][][] {
            {
                {0,0,1,0},
                {1,1,1,0},
                {0,0,0,0},
                {0,0,0,0}
            },
            {
                {0,1,0,0},
                {0,1,0,0},
                {0,1,1,0},
                {0,0,0,0}
            },
            {
                {0,0,0,0},
                {1,1,1,0},
                {1,0,0,0},
                {0,0,0,0}
            },
            {
                {1,1,0,0},
                {0,1,0,0},
                {0,1,0,0},
                {0,0,0,0}
            }
        };
    }

    @Override
    public Tetromino createNew() {
        return new TetrominoL();
    }
}