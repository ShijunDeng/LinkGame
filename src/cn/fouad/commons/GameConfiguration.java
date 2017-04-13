package cn.fouad.commons;

public class GameConfiguration {

	private int rowNums;
	private int columnNums;
	private Point beginPoint;
	private long perGrade;
	private long limitedTime;
	public final int MODELNUM = 5;

	public GameConfiguration(int rowNums, int columnNums, Point beginPoint,
			long perGrade, long limitedTime) {
		super();
		this.rowNums = rowNums;
		this.columnNums = columnNums;
		this.beginPoint = beginPoint;
		this.perGrade = perGrade;
		this.limitedTime = limitedTime;
	}

	public int getRowNums() {
		return rowNums;
	}

	public int getColumnNums() {
		return columnNums;
	}

	public int getBeginPieceX() {
		return beginPoint.getX();
	}

	public int getBeginPieceY() {
		return beginPoint.getY();
	}

	public Point getBeginPoint() {
		return beginPoint;
	}

	public long getPerGrade() {
		return perGrade;
	}

	public long getLimitedTime() {
		return limitedTime;
	}
}
