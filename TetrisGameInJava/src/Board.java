import java.awt.Color;
import java.util.Random;

// Board 負責遊戲狀態：維護格子、處理當前方塊、碰撞檢查、消行
public class Board {
    private int rows = 20; // 行數
    private int cols = 10; // 欄數
    private Color[][] grid; // null 表示空格，否則該位置有顏色表示已鎖定的方塊
    private Tetromino current; // 目前正在下落的方塊（多型）
    private Random rnd = new Random();
    private int score = 0; // 新增：遊戲分數，消行時累積
    private boolean gameOver = false; // 新增：遊戲是否結束的旗標

    // 建構子：初始化格子並產生第一個方塊
    public Board() {
        grid = new Color[rows][cols];
        spawnNew();
    }

    // spawnNew：隨機選一種方塊並放在頂端
    public void spawnNew() {
        // 方塊池（多型）：陣列內為不同子類，回傳的是 Tetromino 類型
        // 新增 S, Z, J 類別以補齊 7 種標準 Tetromino（I, O, T, S, Z, J, L）
        Tetromino[] pool = new Tetromino[] {
            new TetrominoI(), new TetrominoO(), new TetrominoT(), new TetrominoL(),
            new TetrominoS(), new TetrominoZ(), new TetrominoJ()
        };
        // 隨機挑一種並透過 createNew 建立實例（示範工廠方法與多型）
        current = pool[rnd.nextInt(pool.length)].createNew();
        current.setPosition(0, 3);
        // 若剛產生就發生衝突（例如板面已滿），則代表遊戲結束
        if (!canPlace(current, current.getRow(), current.getCol())) {
            gameOver = true; // 設定遊戲結束旗標，UI 會顯示對話框處理
            return;
        }
        gameOver = false; // 若成功產生則清除 gameOver 狀態
    }

    // clearAll：清除整個格子（遊戲重置）
    public void clearAll() {
        for (int r=0;r<rows;r++) for (int c=0;c<cols;c++) grid[r][c]=null;
        score = 0; // 清除時一併重置分數（代表遊戲重新開始）
        gameOver = false; // 也重置 gameOver 狀態
    }

    // canPlace：檢查 tetromino 的 4x4 模板是否可以放在 targetRow/targetCol（不碰撞且不出界）
    public boolean canPlace(Tetromino t, int targetRow, int targetCol) {
        int[][] shape = t.getShape();
        for (int r=0;r<4;r++) for (int c=0;c<4;c++) {
            if (shape[r][c]==1) {
                int br = targetRow + r;
                int bc = targetCol + c;
                // 如果出界或已被佔用，則不能放置
                if (br<0 || bc<0 || bc>=cols || br>=rows) return false;
                if (grid[br][bc]!=null) return false;
            }
        }
        return true;
    }

    // 嘗試向下移動當前方塊；若無法移動，則鎖定方塊並處理消行，接著 spawn 新方塊
    public boolean moveCurrentDown() {
        if (canPlace(current, current.getRow()+1, current.getCol())) {
            current.setPosition(current.getRow()+1, current.getCol());
            return true; // 成功下移
        } else {
            // 無法下移，將當前方塊鎖定到 grid 上
            lockCurrent();
            // 檢查並清除滿行
            clearLines();
            // 產生新方塊
            spawnNew();
            return false;
        }
    }

    // 向左移動（在移動前先檢查是否可放）
    public void moveCurrentLeft() {
        if (canPlace(current, current.getRow(), current.getCol()-1)) {
            current.setPosition(current.getRow(), current.getCol()-1);
        }
    }
    // 向右移動
    public void moveCurrentRight() {
        if (canPlace(current, current.getRow(), current.getCol()+1)) {
            current.setPosition(current.getRow(), current.getCol()+1);
        }
    }
    // 旋轉方塊：先嘗試旋轉，若旋轉後與現有格子衝突則還原（簡單的牆內調整策略）
    public void rotateCurrent() {
        current.rotateCW();
        if (!canPlace(current, current.getRow(), current.getCol())) {
            current.rotateCCW();
        }
    }

    // lockCurrent：將當前方塊的每個實際方塊格寫入 grid（把落下的方塊變成固定方塊）
    private void lockCurrent() {
        int[][] shape = current.getShape();
        for (int r=0;r<4;r++) for (int c=0;c<4;c++) if (shape[r][c]==1) {
            int br = current.getRow()+r;
            int bc = current.getCol()+c;
            if (br>=0 && br<rows && bc>=0 && bc<cols) grid[br][bc] = current.getColor();
        }
    }

    // clearLines：從底部向上檢查每一行是否已滿，若滿則向下移動上面的行
    private void clearLines() {
        int removedThisCall = 0; // 新增：記錄這一次 lock 後總共清除的行數
        for (int r=rows-1;r>=0;r--) {
            boolean full = true;
            for (int c=0;c<cols;c++) if (grid[r][c]==null) { full=false; break; }
            if (full) {
                // shift down：把上方每一行往下搬一格
                for (int rr=r; rr>0; rr--) for (int c=0;c<cols;c++) grid[rr][c]=grid[rr-1][c];
                for (int c=0;c<cols;c++) grid[0][c]=null;
                removedThisCall++;
                r++; // 重新檢查同一個索引（因為已被上方資料覆蓋）
            }
        }
        if (removedThisCall>0) {
            // 簡單計分規則：每清一行 +100 分；可依需求調整（例如連殺加乘）
            score += removedThisCall * 100;
        }
    }

    // 供 UI 使用的 getter
    public Color[][] getGrid() { return grid; }
    public Tetromino getCurrent() { return current; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getScore() { return score; } // 新增：取得目前分數
    public boolean isGameOver() { return gameOver; } // 新增：檢查遊戲是否結束
}