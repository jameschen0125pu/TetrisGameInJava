import javax.swing.*;
import java.awt.*;

public class TetrisGameMain {

	public static void main(String[] args) {
		// 使用 SwingUtilities.invokeLater 將 UI 啟動放到 EDT（Event Dispatch Thread）中，確保 Swing 的執行緒安全
		SwingUtilities.invokeLater(() -> {
			// Test Git Only
			new TetrisGameUI(); // 建立並顯示主要遊戲視窗
		});
	}

}