/**
 * 
 */
package com.aj.slick2d.tetris.gameplay;

import java.util.Random;

import com.aj.slick2d.tetris.global.Globals;

/**
 * Represents shape controlled by player. This class not only represents shape,
 * but also provides means for handling of rotation and drawing next shape. Does
 * not paint anything.
 * 
 * @author BETON
 */
public class Shape {
	/**
	 * static table containing start relative coordinates for each type of
	 * Tetromino
	 */
	private static final int[][][] COORDS_TABLE = new int[][][] {
			{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
			{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } },
			{ { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
			{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } },
			{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
			{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } },
			{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
			{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } };

	/**
	 * Enum with all possible shapes. 'NoShape' is fake shape for init phase.
	 */
	enum Tetrominoes {
		NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape
	};

	/**
	 * just to know what type of piece we have
	 */
	private Tetrominoes tetromino;

	/**
	 * relative coordinates of blocks which build up shape
	 */
	private int coords[][] = new int[Globals.SHAPE_NUM_OF_BLOCKS][Globals.SHAPE_DIMENSIONS];

	/**
	 * Constructor. By default shape is set to 'NoShape' (which means single
	 * dot).
	 */
	public Shape() {
		setShape(Tetrominoes.NoShape);
	}

	/**
	 * Sets given shape using constants from {@link #COORDS_TABLE}.
	 * 
	 * @param shape
	 */
	public void setShape(Tetrominoes shape) {
		for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
			for (int j = 0; j < Globals.SHAPE_DIMENSIONS; ++j) {
				coords[i][j] = COORDS_TABLE[shape.ordinal()][i][j];
			}
		}
		tetromino = shape;
	}

	private void setX(int index, int x) {
		coords[index][0] = x;
	}

	private void setY(int index, int y) {
		coords[index][1] = y;
	}

	/**
	 * @param index
	 * @return Relative x coordinate for given block index (remember that
	 *         {@link #NUM_OF_BLOCKS}-1 is max index!).
	 */
	public int x(int index) {
		return coords[index][0];
	}

	/**
	 * @param index
	 * @return Relative y coordinate for given block index (remember that
	 *         {@link #NUM_OF_BLOCKS}-1 is max index!).
	 */
	public int y(int index) {
		return coords[index][1];
	}

	/**
	 * @return enum value representing shape.
	 */
	public Tetrominoes getShape() {
		return tetromino;
	}

	/**
	 * Used to draw new shape for player.
	 */
	public void setRandomShape() {
		Random r = new Random();
		Tetrominoes[] tetrominoes = Tetrominoes.values();
		// draw random Tetromino, but not 'NoShape'
		int x = Math.abs(r.nextInt()) % (tetrominoes.length - 1) + 1;
		setShape(tetrominoes[x]);
	}

	/**
	 * @return minimum relative X coordinate of this shape's block
	 */
	public int minX() {
		int m = coords[0][0];
		for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
			m = Math.min(m, coords[i][0]);
		}
		return m;
	}

	/**
	 * @return minimum relative Y coordinate of this shape's block
	 */
	public int minY() {
		int m = coords[0][1];
		for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	/**
	 * @return maximum relative X coordinate of this shape's block
	 */
	public int maxX() {
		int m = coords[0][0];
		for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
			m = Math.max(m, coords[i][0]);
		}
		return m;
	}

	/**
	 * @return maximum relative Y coordinate of this shape's block
	 */
	public int maxY() {
		int m = coords[0][1];
		for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; i++) {
			m = Math.max(m, coords[i][1]);
		}
		return m;
	}

	/**
	 * @return New shape which has same tetromino but is rotated 90 degrees left
	 */
	public Shape rotateLeft() {
		if (tetromino == Tetrominoes.SquareShape)
			return this;

		Shape result = new Shape();
		result.tetromino = tetromino;

		for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; ++i) {
			result.setX(i, y(i));
			result.setY(i, -x(i));
		}
		return result;
	}

	/**
	 * @return New shape which has same tetromino but is rotated 90 degrees
	 *         right
	 */
	public Shape rotateRight() {
		if (tetromino == Tetrominoes.SquareShape)
			return this;

		Shape result = new Shape();
		result.tetromino = tetromino;

		for (int i = 0; i < Globals.SHAPE_NUM_OF_BLOCKS; ++i) {
			result.setX(i, -y(i));
			result.setY(i, x(i));
		}
		return result;
	}
}
