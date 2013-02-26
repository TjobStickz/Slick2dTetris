/**
 * 
 */
package com.aj.slick2d.tetris.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import com.aj.slick2d.tetris.global.Globals;

/**
 * Represents game board.
 * 
 * @author BETON
 */
public class Board {
	private List<Color[]> lines = new ArrayList<>();
	private Shape movingShape;
	private int movingX;
	private int movingY;

	public Board() {
		for (int i = 0; i < (Globals.BOARD_VISIBLE_ROWS + Globals.BOARD_HIDDEN_ROWS); i++) {
			Color[] row = new Color[Globals.BOARD_COLS];
			for (int k = 0; k < row.length; k++) {
				row[k] = null;
			}
			lines.add(row);
		}
		movingShape = null;
		movingX = -1;
		movingY = -1;
	}

	/**
	 * @param row
	 *            row (remember that there are hidden rows!)
	 * @param col
	 *            column
	 * @param state
	 *            state of cell to set
	 */
	public void setCellState(int row, int col, Color color) {
		Color[] line = lines.get(row);
		line[col] = color;
	}

	/**
	 * @param row
	 *            row (remember that there are hidden rows!)
	 * @param col
	 *            column
	 * @return state of cell with given coordinates
	 */
	public Color getCellState(int row, int col) {
		Color[] line = lines.get(row);
		return line[col];
	}

	private void clearMovingShape() {
		if (movingShape != null) {
			for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
				int x = movingX + movingShape.x(i);
				int y = movingY + movingShape.y(i);
				setCellState(y, x, null);
			}
		}
	}

	private void putMovingShape() {
		if (movingShape != null) {
			for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
				int x = movingX + movingShape.x(i);
				int y = movingY + movingShape.y(i);
				setCellState(y, x, movingShape.getColor());
			}
		}
	}

	public void setMovingShape(Shape shape) {
		clearMovingShape();

		movingShape = shape;
		movingX = Globals.SHAPE_START_COL;
		movingY = Globals.SHAPE_START_ROW;

		putMovingShape();
	}

	public void rotateRight() {
		clearMovingShape();
		movingShape = movingShape.rotateRight();
		putMovingShape();
	}

	public void rotateLeft() {
		clearMovingShape();
		movingShape = movingShape.rotateLeft();
		putMovingShape();
	}
}
