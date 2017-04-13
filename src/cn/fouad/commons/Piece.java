package cn.fouad.commons;

import java.awt.image.BufferedImage;

import cn.fouad.utils.ImageUtils;

public class Piece {
	private Point beginPoint;
	private Point endPoint;
	private Point indexPoint;
	private BufferedImage image;

	public Piece(Point beginPoint, Point indexPoint, BufferedImage image) {
		this.beginPoint = beginPoint;
		this.endPoint = new Point(beginPoint.getX() + image.getWidth(),
				beginPoint.getY() + image.getHeight());
		this.indexPoint = indexPoint;
		this.image = image;
	}

	public Piece(Point beginPoint, Point indexPoint, int xStep, int yStep) {
		this.beginPoint = beginPoint;
		this.endPoint = new Point(beginPoint.getX() + xStep, beginPoint.getY()
				+ yStep);
		this.indexPoint = indexPoint;
		this.image = null;
	}

	public Piece() {

	}

	public Piece(Point indexPoint) {
		super();
		this.beginPoint = null;
		this.endPoint = null;
		this.indexPoint = indexPoint;
		this.image = null;
	}

	public int getBeginX() {
		return getBeginPoint().getX();
	}

	public int getBeginY() {
		return getBeginPoint().getY();
	}

	public int getEndX() {
		return getEndPoint().getX();
	}

	public int getEndY() {
		return getEndPoint().getY();
	}

	public int getXIndex() {
		return getIndexPoint().getX();
	}

	public int getYIndex() {
		return getIndexPoint().getY();
	}

	public Point getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(Point beginPosition) {
		this.beginPoint = beginPosition;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPosition) {
		this.endPoint = endPosition;
	}

	public Point getIndexPoint() {
		return indexPoint;
	}

	public void setIndexPoint(Point indexPosition) {
		this.indexPoint = indexPosition;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean HasSameImage(Piece piece) {
		return ImageUtils.eauals(image, piece.getImage());
	}

}
