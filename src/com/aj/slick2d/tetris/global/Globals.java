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

	public static final int RESOLUTION_WIDTH = 1024;
	public static final int RESOLUTION_HEIGHT = 768;

	// STATES
	public static final int LOADING_STATE = 0;
	public static final int MENU_STATE = 1;
	public static final int GAME_STATE = 2;
	public static final int OPTIONS_STATE = 3;
	public static final int HIGHSCORES_STATE = 4;

	// GAME SUB-STATES
	public static final int GAME_NEXT = 0;
	public static final int GAME_MOVE = 1;
	public static final int GAME_DROP = 2;
	public static final int GAME_OVER = 3;
	public static final int GAME_PAUSE = 4;

	// BOARD CELL
	public static final int CELL_SIZE = 32;

	// BOARD dimensions
	public static final int BOARD_COLS = 10;
	public static final int BOARD_VISIBLE_ROWS = 20;
	public static final int BOARD_HIDDEN_ROWS = 2;
	public static final int BOARD_HEIGHT = BOARD_VISIBLE_ROWS * CELL_SIZE;
	public static final int BOARD_WIDTH = BOARD_COLS * CELL_SIZE;
	public static final int BOARD_OFFSET = 100;

	// SHAPE properties
	public static final int SHAPE_NUM_OF_BLOCKS = 4;
	public static final int SHAPE_DIMENSIONS = 2;
	public static final int SHAPE_START_ROW = 10;
	public static final int SHAPE_START_COL = 3;

	private Globals() {
		// can't instantiate globals class.
	}
}
