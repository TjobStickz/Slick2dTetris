/**
 * 
 */
package com.aj.slick2d.tetris.gameplay;

/**
 * <p>
 * Enum for substates of 'game' state.
 * </p>
 * <p>
 * Possible states:
 * </p>
 * <ul>
 * <li>COUNT_DOWN: only possible at the end of the game. Counts down from 3 to 0
 * and then switches to DRAW_NEXT. Can also switch to PAUSE.</li>
 * <li>DRAW_NEXT: draw next shape and, switch to MOVE.</li>
 * <li>MOVE: allows player to move his piece and also handles constant dropping
 * of piece row by row. Can switch to: DROP, PAUSE.</li>
 * <li>DROP: occurs if player's piece can't drop any lower or if player chooses
 * to drop piece down. Can switch to: DRAW_NEXT, OVER</li>
 * <li>OVER: last substate of game. It means that game is lost. Player can
 * return to main menu now.</li>
 * <li>PAUSE: game is frozen. When game is unpaused, it returns to state it had
 * before switching to PAUSE</li>
 * </ul>
 * 
 * @author BETON
 */
public enum GameSubStates {
	COUNT_DOWN, DRAW_NEXT, MOVE, DROP, OVER, PAUSE
}
