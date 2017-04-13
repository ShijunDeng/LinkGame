package cn.fouad.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import cn.fouad.commons.LinkInfo;
import cn.fouad.commons.Piece;
import cn.fouad.service.impl.GameServiceImpl;
import cn.fouad.timer.GameTimer;
import cn.fouad.utils.ImageUtils;

/**
 * Game panel
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GameServiceImpl gameService;

	private Piece selectPiece;
	private LinkInfo linkInfo;
	private BufferedImage gameoverImage;

	public void setLinkInfo(LinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}

	public GamePanel(GameServiceImpl gameService) {
		this.setBackground(new Color(55, 77, 118));
		this.setBorder(new EtchedBorder());
		this.gameService = gameService;
	}

	public void setOverImage(BufferedImage gameoverImage) {
		this.gameoverImage = gameoverImage;
	}

	public BufferedImage getOverImage() {
		return gameoverImage;
	}

	public void setSelectPiece(Piece piece) {
		this.selectPiece = piece;
	}

	public void paint(Graphics g) {
		try {
			g.drawImage(ImageUtils.getBackgroundImageIcon().getImage(), 0, 0,
					getWidth(), getHeight(), null);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		Piece[][] pieces = gameService.getPieces();
		if (pieces != null) {
			for (int i = 0; i < pieces.length; i++) {
				for (int j = 0; j < pieces[i].length; j++) {
					if (pieces[i][j] != null && pieces[i][j].getImage() != null) {
						// System.out.println(pieces[i][j].getBeginX());
						g.drawImage(pieces[i][j].getImage(),
								pieces[i][j].getBeginX(),
								pieces[i][j].getBeginY(), null);
					}
				}
			}
		}
		if (this.selectPiece != null) {
			try {
				g.drawImage(ImageUtils.getImage("images/selected.gif"),
						this.selectPiece.getBeginX(),
						this.selectPiece.getBeginY(), null);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (this.linkInfo != null) {
			drawLine(this.linkInfo, g);
			this.linkInfo = null;
		}
		if (this.gameoverImage != null) {
			g.drawImage(this.gameoverImage, 0, 0, null);
		}
	}

	private void drawLine(LinkInfo linkInfo, Graphics g) {
		List<Piece> pieces = linkInfo.getPieces();
		int xStep = this.gameService.getGameModel().getCommonImageWidth();
		int yStep = this.gameService.getGameModel().getCommonImageHeight();
		for (int i = 0; i < pieces.size() - 1; i++) {
			Piece currentPiece = pieces.get(i);
			Piece nextPiece = pieces.get(i + 1);
			Graphics2D dg = ((Graphics2D) g);
			dg.setStroke(new BasicStroke(3.0F));
			dg.setColor(Color.RED);
			dg.drawLine(currentPiece.getBeginX() + xStep / 2,
					currentPiece.getBeginY() + yStep / 2, nextPiece.getBeginX()
							+ xStep / 2, nextPiece.getBeginY() + yStep / 2);
		}
		GameTimer.setGameTime(GameTimer.getGameTime() + 5);
	}

}
