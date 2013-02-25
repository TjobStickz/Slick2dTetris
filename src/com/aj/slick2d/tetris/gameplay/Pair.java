/**
 * 
 */
package com.aj.slick2d.tetris.gameplay;

/**
 * Represents coordinates pair.
 * 
 * @author BETON
 */
public class Pair {
	private final int x;
	private final int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
