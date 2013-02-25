/**
 * 
 */
package com.aj.slick2d.tetris.gameplay;

import java.util.Random;

/**
 * Used to generate random tetrominos.
 * 
 * @author BETON
 */
public final class TetrominoRandomizer {
	private TetrominoRandomizer() {
		// only static methods inside = no instantiation.
	}

	public static Shape generateRandomShape() {
		Tetrominos[] values = Tetrominos.values();
		Random r = new Random();
		return new Shape(values[r.nextInt(values.length)]);
	}
}
