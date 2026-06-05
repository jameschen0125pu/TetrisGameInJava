import java.awt.Color;

// O 型方塊（正方形），旋轉後形狀不變
public class TetrominoO extends Tetromino {
    public TetrominoO() {
        color = Color.YELLOW;
        // 只有一個旋轉狀態（旋轉後形狀相同）
        shapes = new int[][][] {
            {
                {0,1,1,0},
                {0,1,1,0},
                {0,0,0,0},
                {0,0,0,0}
            }
        };
    }

    @Override
    public Tetromino createNew() {
        return new TetrominoO();
    }
}