/**
 * 
 */
package com.aj.slick2d.tetris.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.aj.slick2d.tetris.global.Globals;
import com.aj.slick2d.tetris.state.GameState;
import com.aj.slick2d.tetris.state.LoadingState;
import com.aj.slick2d.tetris.state.MenuState;

/**
 * @author BETON
 */
public class Game extends StateBasedGame {

	/**
	 * @param title
	 */
	public Game() {
		super(Globals.GAME_TITLE + " " + Globals.GAME_VERSION);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new LoadingState(Globals.LOADING_STATE));
		addState(new MenuState(Globals.MENU_STATE));
		addState(new GameState(Globals.GAME_STATE));
	}
}
