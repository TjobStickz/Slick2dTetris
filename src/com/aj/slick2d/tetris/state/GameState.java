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

import com.aj.slick2d.tetris.gameplay.Board;
import com.aj.slick2d.tetris.gameplay.GameSubStates;
import com.aj.slick2d.tetris.gameplay.MovementDirections;
import com.aj.slick2d.tetris.gameplay.Shape;
import com.aj.slick2d.tetris.gameplay.TetrominoRandomizer;
import com.aj.slick2d.tetris.global.Globals;

/**
 * @author BETON
 * 
 */
public class GameState extends BasicGameState {
	private int id;
	private Board board;
	private GameSubStates subState;

	// used as universal timer to trigger events
	private int timeLeft;

	// countdown
	private int countDownStepsLeft;

	public GameState(int id) {
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
		board = new Board();
		subState = GameSubStates.COUNT_DOWN;
		countDownStepsLeft = Globals.COUNTDOWN_STEPS;
		timeLeft = Globals.COUNTDOWN_STEP;
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
		renderBoardBorders(container, g);
		renderBoardContent(container, g);
		if (subState == GameSubStates.COUNT_DOWN) {
			renderCountDownCounter(container, g);
		} else if (subState == GameSubStates.OVER) {
			renderGameOverMessage(container, g);
		}
	}

	private void renderGameOverMessage(GameContainer container, Graphics g) {
		String countDownText = "GAME OVER! Press ENTER to return to main menu.";
		g.drawString(countDownText, (container.getWidth() - g.getFont()
				.getWidth(countDownText)) / 2, (container.getHeight() - g
				.getFont().getHeight(countDownText)) / 2);
	}

	private void renderCountDownCounter(GameContainer container, Graphics g) {
		String countDownText = countDownStepsLeft + "";
		g.drawString(countDownText, (container.getWidth() - g.getFont()
				.getWidth(countDownText)) / 2, (container.getHeight() - g
				.getFont().getHeight(countDownText)) / 2);
	}

	private void renderBoardContent(GameContainer container, Graphics g) {
		int startX = Globals.BOARD_OFFSET;
		int startY = container.getHeight()
				- ((container.getHeight() - Globals.BOARD_HEIGHT) / 2);

		for (int i = 0; i < Globals.BOARD_VISIBLE_ROWS; i++) {
			for (int j = 0; j < Globals.BOARD_COLS; j++) {
				Color cellColor = board.getCellState(i, j);
				if (cellColor != null) {
					int x = startX + (j * Globals.CELL_SIZE);
					int y = startY - ((i + 1) * Globals.CELL_SIZE);
					paintCell(g, x, y, cellColor);
				}
			}
		}
	}

	private void paintCell(Graphics g, int x, int y, Color color) {
		Color oldColor = g.getColor();
		Color cellFilling = color;
		Color cellBorder = Color.black;

		g.setColor(cellBorder);
		g.drawRoundRect(x, y, Globals.CELL_SIZE, Globals.CELL_SIZE, 4);

		g.setColor(cellFilling);
		g.fillRoundRect(x + 1, y + 1, Globals.CELL_SIZE - 1,
				Globals.CELL_SIZE - 1, 4);

		g.setColor(oldColor);
	}

	private void renderBoardBorders(GameContainer container, Graphics g) {
		Color borderColor = Color.blue;
		Color oldColor = g.getColor();

		int x = Globals.BOARD_OFFSET - 1;
		int y = (container.getHeight() - Globals.BOARD_HEIGHT) / 2 - 1;

		g.setColor(borderColor);
		g.drawRect(x, y, Globals.BOARD_WIDTH + 2, Globals.BOARD_HEIGHT + 2);
		g.setColor(oldColor);
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
		Input input = container.getInput();

		// in PAUSE and OVER timeLeft is frozen
		if (subState != GameSubStates.PAUSE && subState != GameSubStates.OVER) {
			timeLeft -= delta;
		}

		if (subState == GameSubStates.COUNT_DOWN) {
			if (timeLeft < 0) {
				countDownStepsLeft--;
				timeLeft = Globals.COUNTDOWN_STEP;
			}
			if (countDownStepsLeft == 0) {
				subState = GameSubStates.DRAW_NEXT;
				timeLeft = Globals.DRAW_TIMER;
			}
		} else if (subState == GameSubStates.DRAW_NEXT) {
			if (timeLeft < 0) {
				Shape shape = TetrominoRandomizer.generateRandomShape();
				board.setMovingShape(shape);
				subState = GameSubStates.MOVE;
				timeLeft = Globals.DROP_TIMER;
			}
		} else if (subState == GameSubStates.MOVE) {
			if (input.isKeyPressed(Input.KEY_UP)) {
				board.tryRotateRight();
			} else if (input.isKeyPressed(Input.KEY_DOWN)) {
				board.tryRotateLeft();
			} else if (input.isKeyPressed(Input.KEY_LEFT)) {
				board.tryMove(MovementDirections.LEFT);
			} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
				board.tryMove(MovementDirections.RIGHT);
			} else if (input.isKeyPressed(Input.KEY_SPACE)) {
				boolean can = false;
				do {
					can = board.tryMove(MovementDirections.DOWN);
				} while (can);
				timeLeft = -1;
			} else if (input.isKeyPressed(Input.KEY_P)) {
				// TODO: switch pause on
				// TODO: cache current state
			}

			if (timeLeft < 0) {
				if (board.tryMove(MovementDirections.DOWN)) {
					timeLeft = Globals.DROP_TIMER;
				} else {
					subState = GameSubStates.DROP;
				}
			}
		} else if (subState == GameSubStates.DROP) {
			board.setMovingShape(null);
			int linesEliminated = board.tryEliminateLines();
			// TODO: count score
			if (board.over()) {
				subState = GameSubStates.OVER;
			} else {
				subState = GameSubStates.DRAW_NEXT;
				timeLeft = Globals.DRAW_TIMER;
			}
		} else if (subState == GameSubStates.OVER) {
			if (input.isKeyPressed(Input.KEY_ENTER)) {
				game.enterState(Globals.MENU_STATE, new FadeOutTransition(),
						new FadeInTransition());
			}
		} else if (subState == GameSubStates.PAUSE) {
			if (input.isKeyPressed(Input.KEY_P)) {
				// TODO: restore previous state
			}
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		board = new Board();
		subState = GameSubStates.COUNT_DOWN;
		countDownStepsLeft = Globals.COUNTDOWN_STEPS;
		timeLeft = Globals.COUNTDOWN_STEP;
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
