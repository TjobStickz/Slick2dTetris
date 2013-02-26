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
	private int movingCol;
	private int movingRow;

	public Board() {
		for (int i = 0; i < (Globals.BOARD_VISIBLE_ROWS + Globals.BOARD_HIDDEN_ROWS); i++) {
			Color[] row = new Color[Globals.BOARD_COLS];
			for (int k = 0; k < row.length; k++) {
				row[k] = null;
			}
			lines.add(row);
		}
		movingShape = null;
		movingCol = -1;
		movingRow = -1;
	}

	/**
	 * @param row
	 *            row (remember that there are hidden rows!)
	 * @param col
	 *            column
	 * @param state
	 *            state of cell to set
	 */
	private void setCellState(int row, int col, Color color) {
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
				int col = movingCol + movingShape.x(i);
				int row = movingRow + movingShape.y(i);
				setCellState(row, col, null);
			}
		}
	}

	private void putMovingShape() {
		if (movingShape != null) {
			for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
				int col = movingCol + movingShape.x(i);
				int row = movingRow + movingShape.y(i);
				setCellState(row, col, movingShape.getColor());
			}
		}
	}

	public void setMovingShape(Shape shape) {
		if (shape != null) {
			clearMovingShape();
		}

		movingShape = shape;
		movingCol = Globals.SHAPE_START_COL;
		movingRow = Globals.SHAPE_START_ROW;

		putMovingShape();
	}

	/**
	 * @param direction
	 *            Direction in which we are trying to move player-controlled
	 *            piece.
	 * @return {@code true} if movement was succesful, {@code false} otherwise
	 */
	public boolean tryMove(MovementDirections direction) {
		clearMovingShape();

		int col = movingCol;
		int row = movingRow;
		if (MovementDirections.DOWN == direction) {
			row--;
		} else if (MovementDirections.LEFT == direction) {
			col--;
		} else if (MovementDirections.RIGHT == direction) {
			col++;
		} else {
			throw new IllegalArgumentException("Unknown direction!");
		}

		if (canPut(movingShape, row, col)) {
			movingCol = col;
			movingRow = row;
			putMovingShape();
			return true;
		}
		putMovingShape();
		return false;
	}

	/**
	 * @return {@code true} if rotating right was succesful, {@code false}
	 *         otherwise
	 */
	public boolean tryRotateRight() {
		clearMovingShape();
		Shape rotated = movingShape.rotateRight();
		if (canPut(rotated, movingRow, movingCol)) {
			movingShape = rotated;
			putMovingShape();
			return true;
		}
		putMovingShape();
		return false;
	}

	/**
	 * @return {@code true} if rotating left was succesful, {@code false}
	 *         otherwise
	 */
	public boolean tryRotateLeft() {
		clearMovingShape();
		Shape rotated = movingShape.rotateLeft();
		if (canPut(rotated, movingRow, movingCol)) {
			movingShape = rotated;
			putMovingShape();
			return true;
		}
		putMovingShape();
		return false;
	}

	private boolean canPut(Shape shape, int row, int col) {
		for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
			int targetCol = col + shape.x(i);
			int targetRow = row + shape.y(i);

			if (targetCol < 0 || targetCol >= Globals.BOARD_COLS) {
				return false;
			}
			if (targetRow < 0
					|| targetRow >= (Globals.BOARD_VISIBLE_ROWS + Globals.BOARD_HIDDEN_ROWS)) {
				return false;
			}

			if (lines.get(targetRow)[targetCol] != null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @return {@code true} if game is over, {@code false} otherwise
	 */
	public boolean over() {
		clearMovingShape();
		boolean over = false;

		Color[] firstHiddenRow = lines.get(Globals.BOARD_VISIBLE_ROWS);
		for (int i = 0; i < firstHiddenRow.length; i++) {
			if (firstHiddenRow[i] != null) {
				over = true;
				break;
			}
		}

		putMovingShape();
		return over;
	}

	/**
	 * Adds empty line on top;
	 */
	private void addEmptyLine() {
		lines.add(new Color[Globals.BOARD_COLS]);
	}

	/**
	 * @return number of lines eliminated (eliminates only full lines)
	 */
	public int tryEliminateLines() {
		List<Color[]> toRemove = new ArrayList<Color[]>();

		for (Color[] line : lines) {
			boolean full = true;
			for (int i = 0; i < line.length; i++) {
				if (line[i] == null) {
					full = false;
					break;
				}
			}
			if (full) {
				toRemove.add(line);
			}
		}

		for (Color[] line : toRemove) {
			lines.remove(line);
			addEmptyLine();
		}

		return toRemove.size();
	}
}
