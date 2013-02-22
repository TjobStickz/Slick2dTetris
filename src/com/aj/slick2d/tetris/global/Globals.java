/**
 * 
 */
package com.aj.slick2d.tetris.global;

/**
 * @author BETON
 */
public final class Globals {
	public static final String GAME_TITLE = "Slick2d TETRIS";
	public static final String GAME_VERSION = "v0.1";

	// STATES
	public static final int LOADING_STATE = 0;
	public static final int MENU_STATE = 1;
	public static final int GAME_STATE = 2;
	public static final int SCORES_STATE = 3;

	// GAME SUB-STATES
	public static final int GAME_NEXT = 0;
	public static final int GAME_MOVE = 1;
	public static final int GAME_DROP = 2;
	public static final int GAME_OVER = 3;

	private Globals() {
		// can't instantiate globals class.
	}
}
