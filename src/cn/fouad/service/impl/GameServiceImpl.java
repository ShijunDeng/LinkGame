package cn.fouad.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.fouad.commons.GameConfiguration;
import cn.fouad.commons.LinkInfo;
import cn.fouad.commons.Piece;
import cn.fouad.commons.Point;
import cn.fouad.service.GameModel;
import cn.fouad.service.GameService;

public class GameServiceImpl implements GameService {
	Piece[][] pieces = null;
	private GameConfiguration config;
	private GameModel gameModel;
	private long grade = 0;

	public GameServiceImpl(GameConfiguration config) {
		this.config = config;
	}

	public void start() {
		this.gameModel = createGameModel(config);
		this.pieces = gameModel.createRealPieces(config);
	}

	public GameModel createGameModel(GameConfiguration config) {
		Random random = new Random();
		switch (random.nextInt(config.MODELNUM)) {
		case 0:
			return new SimpleGameModelA();
		case 1:
			return new SimpleGameModelB();
		case 2:
			return new SimpleGameModelC();
		case 3:
			return new SimpleGameModelD();
		case 4:
			return new TraditionalModel();
		default:
			return null;
		}
	}

	public Piece[][] getPieces() {
		return this.pieces;
	}

	public Piece getPiece(int xIndex, int yIndex) {
		if (xIndex < 0 || yIndex < 0) {
			return null;
		}
		if (xIndex > config.getRowNums() - 1
				|| yIndex > config.getColumnNums() - 1) {
			return null;
		}
		return this.pieces[xIndex][yIndex];
	}

	public LinkInfo link(Piece aPiece, Piece bPiece) {
		Point aPieceBeginPoint = aPiece.getBeginPoint();
		Point bPieceBeginPoint = bPiece.getBeginPoint();
		if (aPieceBeginPoint.equals(bPieceBeginPoint)) {
			return null;
		}
		if (aPiece.HasSameImage(bPiece) == false) {
			return null;
		}
		if (aPieceBeginPoint.getX() > bPieceBeginPoint.getX())
			return link(bPiece, aPiece);

		int commonImageWidth = gameModel.getCommonImageWidth();
		int commonImageHeight = gameModel.getCommonImageHeight();

		if (aPieceBeginPoint.inSameXline(bPieceBeginPoint)) {
			if (isXBlock(aPiece, bPiece, commonImageWidth) == false) {
				return new LinkInfo(aPiece, bPiece);
			}
		}

		if (aPieceBeginPoint.inSameYline(bPieceBeginPoint)) {
			if (isYBlock(aPiece, bPiece, commonImageHeight) == false) {
				return new LinkInfo(aPiece, bPiece);
			}
		}

		Piece cornerPiece = getCornerPiece(aPiece, bPiece, commonImageWidth,
				commonImageHeight);
		if (cornerPiece != null) {
			return new LinkInfo(aPiece, cornerPiece, bPiece);
		}
		Map<Piece, Piece> ret = getLinkPieces(aPiece, bPiece, commonImageWidth,
				commonImageHeight);
		if (ret.size() > 0)
			return getShortCut(aPiece, bPiece, ret, getDistance(aPiece, bPiece));
		return null;
	}

	Piece getCornerPiece(Piece aPiece, Piece bPiece, int xStep, int yStep) {
		if (aPiece.getBeginX() > bPiece.getBeginX()) {
			return getCornerPiece(bPiece, aPiece, xStep, yStep);
		}
		List<Piece> aPieceUpChannel = null;
		List<Piece> aPieceDownChannel = null;
		List<Piece> bPieceUpChannel = null;
		List<Piece> bPieceDownChannel = null;
		List<Piece> aPieceRightChannel = getRightChannel(aPiece,
				bPiece.getBeginX(), xStep);
		List<Piece> bPieceLeftChannel = getLeftChannel(bPiece,
				aPiece.getBeginX(), xStep);
		Piece result = null;
		if (bPiece.getBeginPoint().isRightUp(aPiece.getBeginPoint())) {
			aPieceUpChannel = getUpChannel(aPiece, bPiece.getBeginY(), yStep);
			bPieceDownChannel = getDownChannel(bPiece, aPiece.getBeginY(),
					yStep);
			result = getCrossPoint(aPieceUpChannel, bPieceLeftChannel);
			return result == null ? getCrossPoint(aPieceRightChannel,
					bPieceDownChannel) : result;
		}
		if (bPiece.getBeginPoint().isRightDown(aPiece.getBeginPoint())) {
			aPieceDownChannel = getDownChannel(aPiece, bPiece.getBeginY(),
					yStep);
			bPieceUpChannel = getUpChannel(bPiece, aPiece.getBeginY(), yStep);
			result = getCrossPoint(aPieceRightChannel, bPieceUpChannel);
			return result == null ? getCrossPoint(aPieceDownChannel,
					bPieceLeftChannel) : result;
		}
		return null;
	}

	private LinkInfo getShortCut(Piece aPiece, Piece bPiece,
			Map<Piece, Piece> linkPieces, int distance) {
		List<LinkInfo> infos = new ArrayList<LinkInfo>();
		for (Object key : linkPieces.keySet()) {
			infos.add(new LinkInfo(aPiece, (Piece) key, linkPieces
					.get((Piece) key), bPiece));
		}
		return getShortCut(infos, distance);
	}

	private LinkInfo getShortCut(List<LinkInfo> infos, int distance) {
		LinkInfo result = null;
		int tempDistance = 0;
		for (int i = 0; i < infos.size(); i++) {
			int adistance = totalDistance(infos.get(i).getPieces());
			LinkInfo info = infos.get(i);
			;
			if (i == 0) {
				result = info;
				tempDistance = adistance - distance;
			}
			if (adistance - distance < tempDistance) {
				result = info;
				tempDistance = adistance - distance;
			}
		}
		return result;
	}

	private int totalDistance(List<Piece> pieces) {
		int result = 0;
		for (int i = 0; i < pieces.size() - 1; i++) {
			result += getDistance(pieces.get(i), pieces.get(i + 1));
		}
		return result;
	}

	private int getDistance(Piece aPiece, Piece bPiece) {
		return Math.abs(aPiece.getBeginX() - bPiece.getBeginX())
				+ Math.abs(aPiece.getBeginY() - bPiece.getBeginY());
	}

	Map<Piece, Piece> getLinkPieces(Piece aPiece, Piece bPiece, int xStep,
			int yStep) {
		Map<Piece, Piece> result = new HashMap<Piece, Piece>();  
		int maxWidth = gameModel.getCommonImageWidth()
				* (config.getRowNums() + 2) + config.getBeginPieceX();
		int maxHeight = gameModel.getCommonImageHeight()
				* (config.getColumnNums() + 2) + config.getBeginPieceY();

		List<Piece> aPieceUpChannel = getUpChannel(aPiece,
				config.getBeginPieceY() - 2 * gameModel.getCommonImageHeight(),
				yStep);
		List<Piece> aPieceDownChannel = getDownChannel(aPiece, maxHeight, yStep);
		List<Piece> aPieceLeftChannel = getLeftChannel(aPiece, 0, xStep);
		List<Piece> aPieceRightChannel = getRightChannel(aPiece, maxWidth,
				xStep);
		List<Piece> bPieceUpChannel = getUpChannel(bPiece,
				config.getBeginPieceY() - 2 * gameModel.getCommonImageHeight(),
				yStep);
		List<Piece> bPieceDownChannel = getDownChannel(bPiece, maxHeight, yStep);
		List<Piece> bPieceLeftChannel = getLeftChannel(bPiece, 0, xStep);
		List<Piece> bPieceRightChannel = getRightChannel(bPiece, maxWidth,
				xStep);
		if (bPiece.getBeginPoint().isLeftUp(aPiece.getBeginPoint())
				|| bPiece.getBeginPoint().isLeftDown(aPiece.getBeginPoint())) {
			return getLinkPieces(bPiece, aPiece, xStep, yStep);
		}

		if (bPiece.getBeginPoint().inSameXline(bPiece.getBeginPoint())) {
			result.putAll(getXLinkPieces(aPieceUpChannel, bPieceUpChannel,
					yStep));
			result.putAll(getXLinkPieces(aPieceDownChannel, bPieceDownChannel,
					yStep));
		}

		if (bPiece.getBeginPoint().inSameYline(bPiece.getBeginPoint())) {
			result.putAll(getYLinkPieces(aPieceLeftChannel, bPieceLeftChannel,
					xStep));
			result.putAll(getYLinkPieces(aPieceRightChannel,
					bPieceRightChannel, xStep));
		}
		if (bPiece.getBeginPoint().isRightUp(aPiece.getBeginPoint())) {
			result.putAll(getXLinkPieces(aPieceUpChannel, bPieceDownChannel,
					xStep));
			result.putAll(getYLinkPieces(aPieceRightChannel, bPieceLeftChannel,
					yStep));
			result.putAll(getXLinkPieces(aPieceUpChannel, bPieceUpChannel,
					xStep));
			result.putAll(getXLinkPieces(aPieceDownChannel, bPieceDownChannel,
					xStep));
			result.putAll(getYLinkPieces(aPieceLeftChannel, bPieceLeftChannel,
					yStep));
			result.putAll(getYLinkPieces(aPieceRightChannel,
					bPieceRightChannel, yStep));
		}
		if (bPiece.getBeginPoint().isRightDown(aPiece.getBeginPoint())) {
			result.putAll(getXLinkPieces(aPieceDownChannel, bPieceUpChannel,
					xStep));
			result.putAll(getYLinkPieces(aPieceRightChannel, bPieceLeftChannel,
					yStep));
			result.putAll(getXLinkPieces(aPieceUpChannel, bPieceUpChannel,
					xStep));
			result.putAll(getXLinkPieces(aPieceDownChannel, bPieceDownChannel,
					xStep));
			result.putAll(getYLinkPieces(aPieceLeftChannel, bPieceLeftChannel,
					yStep));
			result.putAll(getYLinkPieces(aPieceRightChannel,
					bPieceRightChannel, yStep));
		}
		return result;
	}

	Map<Piece, Piece> getXLinkPieces(List<Piece> aChannel,
			List<Piece> bChannel, int step) {
		Map<Piece, Piece> result = new HashMap<Piece, Piece>();
		for (Piece pieceOfaChannel : aChannel) {
			for (Piece pieceOfbChannel : bChannel) {
				if (pieceOfaChannel.getBeginY() == pieceOfbChannel.getBeginY()
						&& isXBlock(pieceOfaChannel, pieceOfbChannel, step) == false) {
					result.put(pieceOfaChannel, pieceOfbChannel);
				}
			}
		}
		return result;
	}

	Map<Piece, Piece> getYLinkPieces(List<Piece> aChannel,
			List<Piece> bChannel, int step) {
		Map<Piece, Piece> result = new HashMap<Piece, Piece>();
		for (Piece pieceOfaChannel : aChannel) {
			for (Piece pieceOfbChannel : bChannel) {
				if (pieceOfaChannel.getBeginX() == pieceOfbChannel.getBeginX()
						&& isYBlock(pieceOfaChannel, pieceOfbChannel, step) == false) {
					result.put(pieceOfaChannel, pieceOfbChannel);
				}
			}
		}
		return result;
	}

	Piece getCrossPoint(List<Piece> aChannel, List<Piece> bChannel) {
		for (Piece pieceOfaChannel : aChannel) {
			for (Piece pieceOfbChannel : bChannel) {
				if (pieceOfaChannel.getBeginPoint().equals(
						pieceOfbChannel.getBeginPoint())) {
					return pieceOfaChannel;
				}
			}
		}
		return null;
	}

	boolean isXBlock(Piece aPiece, Piece bPiece, int step) {
		if (bPiece.getBeginPoint().isLeft(aPiece.getBeginPoint()))
			return isXBlock(bPiece, aPiece, step);
		for (int i = aPiece.getBeginX() + step; i < bPiece.getBeginX(); i += step) {
			if (this.hasImage(i, bPiece.getBeginY())) {
				return true;
			}
		}
		return false;
	}

	boolean isYBlock(Piece aPiece, Piece bPiece, int step) {
		if (bPiece.getBeginPoint().isUp(aPiece.getBeginPoint()))
			return isYBlock(bPiece, aPiece, step);
		for (int i = aPiece.getBeginY() + step; i < bPiece.getBeginY(); i += step) {
			if (this.hasImage(bPiece.getBeginX(), i))
				return true;
		}
		return false;
	}

	public List<Piece> getUpChannel(Piece piece, int ylimit, int step) {
		List<Piece> channel = new ArrayList<Piece>();
		int x = piece.getBeginX();
		for (int i = piece.getBeginY() - step; i >= ylimit; i -= step) {
			Piece findPiece = findPiece(x, i);
			if (hasImage(x, i)) {
				return channel;
			}
			if (findPiece == null) {
				findPiece = new Piece();
				findPiece.setBeginPoint(new Point(x, i));
			}
			channel.add(findPiece);
		}
		return channel;
	}

	public List<Piece> getDownChannel(Piece piece, int ylimit, int step) {
		List<Piece> channel = new ArrayList<Piece>();
		int x = piece.getBeginX();
		for (int i = piece.getBeginY() + step; i <= ylimit; i += step) {
			Piece findPiece = findPiece(x, i);
			if (hasImage(x, i)) {
				return channel;
			}
			if (findPiece == null) {
				findPiece = new Piece();
				findPiece.setBeginPoint(new Point(x, i));
			}
			channel.add(findPiece);
		}
		return channel;
	}

	public List<Piece> getLeftChannel(Piece piece, int xlimit, int step) {
		List<Piece> channel = new ArrayList<Piece>();
		int y = piece.getBeginY();
		for (int i = piece.getBeginX() - step; i >= xlimit; i -= step) {
			Piece findPiece = findPiece(i, y);
			if (hasImage(i, y)) {
				return channel;
			}
			if (findPiece == null) {
				findPiece = new Piece();
				findPiece.setBeginPoint(new Point(i, y));
			}
			channel.add(findPiece);
		}
		return channel;
	}

	public List<Piece> getRightChannel(Piece piece, int xlimit, int step) {
		List<Piece> channel = new ArrayList<Piece>();
		int y = piece.getBeginY();
		for (int i = piece.getBeginX() + step; i <= xlimit; i += step) {
			Piece findPiece = findPiece(i, y);
			if (hasImage(i, y)) {
				return channel;
			}
			if (findPiece == null) {
				findPiece = new Piece();
				findPiece.setBeginPoint(new Point(i, y));
			}
			channel.add(findPiece);
		}
		return channel;
	}

	public boolean empty() {
		for (int i = 0; i < this.pieces.length; i++) {
			for (int j = 0; j < this.pieces[i].length; j++) {
				if (this.pieces[i][j] != null
						&& this.pieces[i][j].getImage() != null)
					return false;
			}
		}
		return true;
	}

	public boolean hasPiece(int x, int y) {
		return findPiece(x, y) != null;
	}

	public boolean hasImage(int x, int y) {
		Piece piece = findPiece(x, y);
		if (piece == null) {
			return false;
		}

		return piece.getImage() != null;
	}

	public Point getIndexPoint(int relativeX, int relativeY, int pieceSide) {
		if (pieceSide < 0)
			return null;
		return new Point(getXIndex(relativeX, pieceSide), getYIndex(relativeY,
				pieceSide));
	}

	public int getXIndex(int relativeX, int pieceWidth) {
		if (pieceWidth < 0)
			return -1;
		return (int) relativeX / pieceWidth;
	}

	public int getYIndex(int relativeY, int pieceHeight) {
		if (pieceHeight < 0)
			return -1;
		return (int) relativeY / pieceHeight;
	}

	public Piece findPiece(int x, int y) {
		if (this.gameModel == null) {
			return null;
		}
		int relativeX = x - this.config.getBeginPieceX();
		int relativeY = y - this.config.getBeginPieceY();
		if (relativeX < 0 || relativeY < 0) {
			return null;
		}
		int xIndex = getXIndex(relativeX, gameModel.getCommonImageWidth());
		int yIndex = getYIndex(relativeY, gameModel.getCommonImageHeight());
		if (xIndex < 0 || yIndex < 0) {
			return null;
		}
		if (xIndex > config.getRowNums() - 1
				|| yIndex > config.getColumnNums() - 1) {
			return null;
		}
		return this.pieces[xIndex][yIndex];
	}

	public GameModel getGameModel() {
		return gameModel;
	}

	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

	public long countGrade() {
		this.grade += this.config.getPerGrade();
		return this.grade;
	}

	public long getGrade() {
		return grade;
	}

	public void setGrade(long grade) {
		this.grade = grade;
	}

}
