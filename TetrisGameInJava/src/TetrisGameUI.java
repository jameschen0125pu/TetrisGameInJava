import java.awt.BorderLayout;

import javax.swing.JFrame;

// TetrisGameUI 繼承自 JFrame：負責建立視窗並把 GamePanel 放進來（UI 組裝）
public class TetrisGameUI extends javax.swing.JFrame {
    public TetrisGameUI() {
        this.setTitle("Mini Tetris - OOP Demo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // create and add the game panel
        GamePanel gp = new GamePanel(); // GamePanel 處理繪製與輸入
        this.add(gp, BorderLayout.CENTER);
        this.pack();
        // 禁止調整大小以保持格子顯示一致
        this.setResizable(false);
        this.setLocationRelativeTo(null); // 視窗置中
        this.setVisible(true);    
    }


}