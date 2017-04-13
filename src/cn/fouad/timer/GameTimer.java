package cn.fouad.timer;

import java.io.IOException;
import java.util.TimerTask;

import javax.swing.JLabel;

import cn.fouad.UI.GamePanel;
import cn.fouad.utils.ImageUtils;

/**
 * Timer
 */
public class GameTimer extends TimerTask {

	private long time;
	private GamePanel gamePanel;

	private static long gameTime;

	public static long getGameTime() {
		return gameTime;
	}

	public static void setGameTime(long gameTime) {
		GameTimer.gameTime = gameTime;
	}

	private JLabel timeLabel;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public GameTimer(GamePanel gamePanel, long gameTime, JLabel timeLabel) {
		this.time = 0;
		this.gamePanel = gamePanel;
		GameTimer.gameTime = gameTime;
		this.timeLabel = timeLabel;
	}

	public void run() {

		if (GameTimer.gameTime - this.time <= 0) {

			try {
				this.gamePanel.setOverImage(ImageUtils
						.getImage("images/lose.jpg"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			this.cancel();
			this.gamePanel.repaint();
		}
		this.timeLabel.setText(String.valueOf(GameTimer.gameTime - this.time));
		this.timeLabel.repaint();
		this.time += 1;
	}

}
