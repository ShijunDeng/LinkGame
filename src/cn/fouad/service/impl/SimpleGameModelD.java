package cn.fouad.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.fouad.commons.GameConfiguration;
import cn.fouad.commons.Piece;
import cn.fouad.commons.Point;
import cn.fouad.service.GameModel;

public class SimpleGameModelD extends GameModel {
	@Override
	public List<Piece> createLogicPieces(GameConfiguration config,
			Piece[][] pieces) {
		List<Piece> logicPieces = new ArrayList<Piece>();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				pieces[i][j] = new Piece(new Point(i, j));
				if (i % 2 == 0 || j % 2 == 0) {
					logicPieces.add(pieces[i][j]);
				}
			}
		}
		return logicPieces;
	}

}
