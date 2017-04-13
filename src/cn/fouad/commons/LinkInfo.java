package cn.fouad.commons;

import java.util.ArrayList;
import java.util.List;

public class LinkInfo {

	List<Piece> pieces = new ArrayList<Piece>();

	public LinkInfo(Piece... aPieces) {
		for (Piece piece : aPieces) {
			this.pieces.add(piece);
		}
	}

	public List<Piece> getPieces() {
		return pieces;
	}

}
