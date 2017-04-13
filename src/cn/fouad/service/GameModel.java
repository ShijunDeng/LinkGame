package cn.fouad.service;

import java.awt.image.BufferedImage;
import java.util.List;

import cn.fouad.commons.GameConfiguration;
import cn.fouad.commons.Piece;
import cn.fouad.commons.Point;
import cn.fouad.exception.GameException;
import cn.fouad.utils.ImageUtils;

public abstract class GameModel {
	private int commonImageWidth;
	private int commonImageHeight;

	public Piece[][] createRealPieces(GameConfiguration config) {
		Piece[][] result = new Piece[config.getRowNums()][config
				.getColumnNums()];
		List<Piece> logicPieces = createLogicPieces(config, result);
		List<BufferedImage> pieceImages = getPieceImages(logicPieces.size());
		this.commonImageWidth = pieceImages.get(0).getWidth();
		this.commonImageHeight = pieceImages.get(0).getHeight();
		int beginX = config.getBeginPoint().getX();
		int beginY = config.getBeginPoint().getY();
		for (int i = 0; i < logicPieces.size(); i++) {
			Point indexPoint = logicPieces.get(i).getIndexPoint();

			result[indexPoint.getX()][indexPoint.getY()] = new Piece(new Point(
					indexPoint.getX() * this.commonImageWidth + beginX,
					indexPoint.getY() * this.commonImageHeight + beginY),
					indexPoint, pieceImages.get(i));
		}
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				if (result[i][j].getBeginPoint() == null) {
					result[i][j] = new Piece(new Point(result[i][j]
							.getIndexPoint().getX()
							* this.commonImageWidth
							+ beginX, result[i][j].getIndexPoint().getY()
							* this.commonImageHeight + beginY),
							new Point(i, j), this.commonImageWidth,
							this.commonImageHeight);
				}
			}
		}
		return result;
	}

	abstract public List<Piece> createLogicPieces(GameConfiguration config,
			Piece[][] pieces);

	public List<BufferedImage> getPieceImages(int num) {
		try {
			return ImageUtils.getPieceImages(num);
		} catch (GameException e) {
			throw new RuntimeException(e);
		}
	}

	public int getCommonImageWidth() {
		return commonImageWidth;
	}

	public int getCommonImageHeight() {
		return commonImageHeight;
	}
}
