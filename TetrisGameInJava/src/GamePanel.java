import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.Preferences; // 用來儲存最高分
// 主遊戲面板：負責渲染和使用者輸入與遊戲循環
public class GamePanel extends JPanel implements ActionListener {
    // game state
    private Board board; // 遊戲邏輯由 Board 管理
    // timer for game loop
    private Timer timer; // 控制自動下落的 Swing Timer
    private int cellSize = 24; // 每格像素大小
    private long startTime; // 新增：記錄遊戲開始時間（毫秒），用來計算經過秒數

    // Preferences 節點，用來儲存最高分（不需要額外檔案）
    private Preferences prefs = Preferences.userNodeForPackage(GamePanel.class);
    private static final String PREF_HIGH_SCORE = "tetris.highscore";

    public GamePanel() {
        setPreferredSize(new Dimension(10*cellSize, 20*cellSize));
        setBackground(Color.BLACK);
        board = new Board();

        // 設定遊戲開始時間
        startTime = System.currentTimeMillis();

        // 設定每 500ms 自動下落一次
        timer = new Timer(500, this);
        timer.start();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        board.moveCurrentLeft(); break;
                    case KeyEvent.VK_RIGHT:
                        board.moveCurrentRight(); break;
                    case KeyEvent.VK_DOWN:
                        board.moveCurrentDown(); break;
                    case KeyEvent.VK_UP:
                        board.rotateCurrent(); break;
                }
                repaint();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 若遊戲已結束則停下 Timer，並顯示對話框讓玩家選擇重玩或離開
        if (board.isGameOver()) {
            timer.stop();
            // 使用 invokeLater 確保對話框在 EDT 上顯示
            SwingUtilities.invokeLater(() -> {
                // 取得本次分數與最高分
                int currentScore = board.getScore();
                int highScore = prefs.getInt(PREF_HIGH_SCORE, 0);
                boolean isNewHigh = false;
                if (currentScore > highScore) {
                    highScore = currentScore;
                    prefs.putInt(PREF_HIGH_SCORE, highScore); // 儲存新的最高分
                    isNewHigh = true;
                }
                // 準備顯示訊息（包含本次分數與最高分）
                String msg = "遊戲結束！\n\n本次分數: " + currentScore + "\n最高分: " + highScore;
                if (isNewHigh) msg += "\n\n恭喜！你創下新高分！";
                msg += "\n\n要重新遊玩嗎？";

                int option = JOptionPane.showConfirmDialog(
                    GamePanel.this,
                    msg,
                    "遊戲結束",
                    JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION) {
                    // 玩家選擇重玩：重置 Board、重設開始時間並重新啟動 Timer
                    board.clearAll();
                    startTime = System.currentTimeMillis();
                    timer.start();
                } else {
                    // 玩家選擇離開：關閉包含本面板的視窗（取得 ancestor JFrame 並 dispose）
                    java.awt.Window w = SwingUtilities.getWindowAncestor(GamePanel.this);
                    if (w != null) w.dispose();
                }
            });
            return; // 不進行下一步下落
        }

        board.moveCurrentDown();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color[][] grid = board.getGrid();
        // draw locked blocks
        for (int r=0;r<board.getRows();r++) for (int c=0;c<board.getCols();c++) {
            if (grid[r][c]!=null) drawCell(g, r, c, grid[r][c]);
        }
        // draw current
        Tetromino t = board.getCurrent();
        if (t!=null) {
            int[][] shape = t.getShape();
            for (int r=0;r<4;r++) for (int c=0;c<4;c++) if (shape[r][c]==1) {
                int br = t.getRow()+r;
                int bc = t.getCol()+c;
                drawCell(g, br, bc, t.getColor());
            }
        }
        // grid lines
        g.setColor(Color.DARK_GRAY);
        for (int r=0;r<=board.getRows();r++) g.drawLine(0, r*cellSize, board.getCols()*cellSize, r*cellSize);
        for (int c=0;c<=board.getCols();c++) g.drawLine(c*cellSize, 0, c*cellSize, board.getRows()*cellSize);

        // 顯示分數與經過秒數（新增）
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        // 取出目前分數
        int score = board.getScore();
        // 計算經過秒數（取整數秒）
        long elapsedMillis = System.currentTimeMillis() - startTime;
        long elapsedSec = elapsedMillis / 1000;
        // 在畫面左上角繪製資訊（留一點邊界）
        String info = "Score: " + score + "  Time: " + elapsedSec + "s";
        g.drawString(info, 4, 12);
    }

    private void drawCell(Graphics g, int row, int col, Color color) {
        if (row<0) return;
        int x = col * cellSize;
        int y = row * cellSize;
        g.setColor(color);
        g.fillRect(x+1, y+1, cellSize-2, cellSize-2);
        g.setColor(color.brighter());
        g.drawRect(x+1, y+1, cellSize-2, cellSize-2);
    }
}