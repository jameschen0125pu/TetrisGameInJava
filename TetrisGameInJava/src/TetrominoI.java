import java.awt.Color;

// I 型方塊（四連直線）
public class TetrominoI extends Tetromino {
    public TetrominoI() {
        // 設定顏色與旋轉形狀陣列（此處只定義 2 個獨特旋轉狀態）
        color = Color.CYAN;
        shapes = new int[][][] {
            {
                {0,0,0,0},
                {1,1,1,1},
                {0,0,0,0},
                {0,0,0,0}
            },
            {
                {0,0,1,0},
                {0,0,1,0},
                {0,0,1,0},
                {0,0,1,0}
            }
        };
    }

    // 回傳同型新物件，方便 Board spawn 新方塊（示範多型/工廠方法）
    @Override
    public Tetromino createNew() {
        return new TetrominoI();
    }
}