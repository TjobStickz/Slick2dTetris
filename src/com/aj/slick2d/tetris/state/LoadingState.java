/**
 * 
 */
package com.aj.slick2d.tetris.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.aj.slick2d.tetris.global.Globals;

/**
 * State for loading resources.
 * 
 * @author BETON
 */
public class LoadingState extends BasicGameState {
	private static final int COLOR_CHANGE_INTERVAL = 100;

	private int id;

	private int distanceFromBottom = 100;
	private String pressEnter = "Press ENTER";

	private int leftFromInterval = 100;
	private int direction = -1;

	/**
	 * 
	 */
	public LoadingState(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO load resources here
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		Color color = g.getColor();

		if (leftFromInterval < 0) {
			color.a = color.a + (direction * 0.1f);
			leftFromInterval = COLOR_CHANGE_INTERVAL;
		}

		if (color.a >= 1.0f || color.a <= 0.0f) {
			direction = -direction;
		}

		g.setColor(color);

		g.drawString(pressEnter,
				(container.getWidth() - g.getFont().getWidth(pressEnter)) / 2,
				container.getHeight() - distanceFromBottom);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		leftFromInterval -= delta;

		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			input.clearControlPressedRecord();
			input.clearKeyPressedRecord();
			input.clearMousePressedRecord();
			game.enterState(Globals.MENU_STATE, new FadeOutTransition(),
					new FadeInTransition());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		return id;
	}
}
