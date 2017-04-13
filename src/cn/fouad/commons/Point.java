package cn.fouad.commons;

 
public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point point = (Point) obj;
			return point.getX() == this.getX() && point.getY() == this.getY();
		}
		return false;
	}
	public boolean isLeft(Point point) {
		return point.getX() > this.getX() && point.getY() == this.getY();

	}

	
	public boolean isRight(Point point) {
		return point.getX() < this.getX() && point.getY() == this.getY();
	}

	
	public boolean isUp(Point point) {
		return point.getX() == this.getX() && point.getY() > this.getY();
	}

	
	public boolean isDown(Point point) {
		return point.getX() == this.getX() && point.getY() < this.getY();

	}


	public boolean isLeftUp(Point point) {
		return point.getX() > this.getX() && point.getY() > this.getY();
	}

	
	public boolean isLeftDown(Point point) {
		return point.getX() > this.getX() && point.getY() < this.getY();
	}

	
	public boolean isRightUp(Point point) {
		return point.getX() < this.getX() && point.getY() > this.getY();
	}

	
	public boolean isRightDown(Point point) {
		return point.getX() < this.getX() && point.getY() < this.getY();

	}

	public boolean inSameXline(Point point) {
		return point.getY() == this.getY();
	}

	public boolean inSameYline(Point point) {
		return point.getX() == this.getX();
	}
}
