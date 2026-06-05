import java.awt.Color;

// T 型方塊
public class TetrominoT extends Tetromino {
    public TetrominoT() {
        color = Color.MAGENTA;
        // 定義 4 個旋轉狀態（0/90/180/270 度）
        shapes = new int[][][] {
            {
                {0,1,0,0},
                {1,1,1,0},
                {0,0,0,0},
                {0,0,0,0}
            },
            {
                {0,1,0,0},
                {0,1,1,0},
                {0,1,0,0},
                {0,0,0,0}
            },
            {
                {0,0,0,0},
                {1,1,1,0},
                {0,1,0,0},
                {0,0,0,0}
            },
            {
                {0,1,0,0},
                {1,1,0,0},
                {0,1,0,0},
                {0,0,0,0}
            }
        };
    }

    @Override
    public Tetromino createNew() {
        return new TetrominoT();
    }
}