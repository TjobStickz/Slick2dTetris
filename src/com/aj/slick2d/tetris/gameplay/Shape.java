/**
 * 
 */
package com.aj.slick2d.tetris.gameplay;

/**
 * Represents shape controlled by player. Shape knows its own coordinates
 * 
 * @author BETON
 */
public class Shape {
	private final Tetrominos tetromino;
	private int currentCoordIndex;

	/**
	 * Constructor. By default shape is set to 'NoShape' (which means single
	 * dot).
	 */
	public Shape(Tetrominos tetromino) {
		this.tetromino = tetromino;
		this.currentCoordIndex = 0;
	}

	private Pair[] getCurrentCoords() {
		return tetromino.getCoords()[currentCoordIndex];
	}

	/**
	 * @param index
	 * @return Relative x coordinate for given block index (remember that
	 *         {@link #NUM_OF_BLOCKS}-1 is max index!).
	 */
	public int x(int index) {
		return getCurrentCoords()[index].getX();
	}

	/**
	 * @param index
	 * @return Relative y coordinate for given block index (remember that
	 *         {@link #NUM_OF_BLOCKS}-1 is max index!).
	 */
	public int y(int index) {
		return getCurrentCoords()[index].getY();
	}

	/**
	 * @return New shape which has same tetromino but is rotated 90 degrees left
	 */
	public Shape rotateLeft() {
		Shape result = new Shape(tetromino);
		int maxCoordIndex = getNumberOfCombinations();
		int newCoordIndex = (currentCoordIndex + 1) % maxCoordIndex;
		result.currentCoordIndex = newCoordIndex;
		return result;
	}

	/**
	 * @return New shape which has same tetromino but is rotated 90 degrees
	 *         right
	 */
	public Shape rotateRight() {
		Shape result = new Shape(tetromino);
		int maxCoordIndex = getNumberOfCombinations();
		int newCoordIndex = (currentCoordIndex - 1 + maxCoordIndex)
				% maxCoordIndex;
		result.currentCoordIndex = newCoordIndex;
		return result;
	}

	private int getNumberOfCombinations() {
		return tetromino.getCoords().length;
	}
}
