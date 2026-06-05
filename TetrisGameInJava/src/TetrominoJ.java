import java.awt.Color;

// J 型方塊（L 型的鏡像）
public class TetrominoJ extends Tetromino {
    public TetrominoJ() {
        color = Color.BLUE;
        shapes = new int[][][] {
            {
                {1,0,0,0},
                {1,1,1,0},
                {0,0,0,0},
                {0,0,0,0}
            },
            {
                {0,1,1,0},
                {0,1,0,0},
                {0,1,0,0},
                {0,0,0,0}
            },
            {
                {0,0,0,0},
                {1,1,1,0},
                {0,0,1,0},
                {0,0,0,0}
            },
            {
                {0,1,0,0},
                {0,1,0,0},
                {1,1,0,0},
                {0,0,0,0}
            }
        };
    }

    @Override
    public Tetromino createNew() {
        return new TetrominoJ();
    }
}
