package cn.fouad.service;

import cn.fouad.commons.LinkInfo;
import cn.fouad.commons.Piece;

public interface GameService {

	void start();

	Piece[][] getPieces();

	Piece findPiece(int x, int y);

	LinkInfo link(Piece aPiece, Piece bPiece);

	boolean empty();

}
