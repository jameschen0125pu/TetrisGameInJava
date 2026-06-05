import java.awt.Color;

// Tetromino 抽象類：所有方塊的共同父類
// 展示 OOP：封裝 (fields 為 protected/私有，透過方法操作)、繼承 (子類 implements 特定 shapes)、多型 (Board 與 UI 使用 Tetromino 型別而不需知道具體類別)
public abstract class Tetromino {
    // 4x4 matrix representation for rotation states
    // shapes[rotation][row][col]：每個 rotation 代表一個 4x4 的形狀矩陣
    protected int[][][] shapes; // shapes[rotation][row][col]
    protected int rotation; // 目前旋轉狀態索引 (例如 0..3)
    protected int row, col; // 在 Board 上的基準位置（4x4 box 的左上角位置）
    protected Color color; // 方塊顏色

    // 建構子：初始化位置與旋轉狀態
    public Tetromino() {
        rotation = 0;
        row = 0;
        col = 3; // 預設出生位置靠近 10 欄板面的中間
    }

    // 取得方塊顏色（封裝欄位讀取）
    public Color getColor() {
        return color;
    }

    // 取得目前旋轉狀態的 4x4 矩陣
    public int[][] getShape() {
        return shapes[rotation];
    }

    // 向右旋轉（順時針），使用 shapes 長度做模運算以循環
    public void rotateCW() {
        rotation = (rotation + 1) % shapes.length;
    }

    // 向左旋轉（逆時針）
    public void rotateCCW() {
        rotation = (rotation - 1 + shapes.length) % shapes.length;
    }

    // 設定方塊在 Board 上的位置（row, col 是 4x4 box 的左上角）
    public void setPosition(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    // 用於建立同型的新實例（工廠式方法）。Board spawn 時會呼叫此方法。
    public abstract Tetromino createNew();
}