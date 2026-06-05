import java.awt.Color;

// Z 型方塊（S 的鏡像）
public class TetrominoZ extends Tetromino {
    public TetrominoZ() {
        color = Color.RED;
        // Z 型有兩個旋轉狀態
        shapes = new int[][][] {
            {
                {1,1,0,0},
                {0,1,1,0},
                {0,0,0,0},
                {0,0,0,0}
            },
            {
                {0,0,1,0},
                {0,1,1,0},
                {0,1,0,0},
                {0,0,0,0}
            }
        };
    }

    @Override
    public Tetromino createNew() {
        return new TetrominoZ();
    }
}
