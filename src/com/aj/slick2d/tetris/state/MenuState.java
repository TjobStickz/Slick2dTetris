/**
 * 
 */
package com.aj.slick2d.tetris.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.aj.slick2d.tetris.global.Globals;
import com.aj.slick2d.tetris.menu.MenuEntry;

/**
 * State with main menu.
 * 
 * @author BETON
 */
public class MenuState extends BasicGameState {
	/**
	 * Gap between menu entries.
	 */
	private static final int TEXT_GAP = 10;

	private int id;

	private int selectedEntryIndex;
	private Color standardColor;
	private Color selectedColor;

	public MenuState(int id) {
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
		selectedEntryIndex = 0;
		standardColor = Color.white;
		selectedColor = Color.red;
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
		paintMenuEntries(container, g);
	}

	private void paintMenuEntries(GameContainer container, Graphics g) {
		int allEntriesHeight = calculateAllEntriesHeight(g.getFont());
		int startY = getStartY(allEntriesHeight, container);

		for (int i = 0; i < MenuEntry.values().length; i++) {
			MenuEntry entry = MenuEntry.values()[i];
			int x = getXForEntry(entry, g.getFont(), container);
			int y = startY
					+ (i * (g.getFont().getHeight(entry.getText()) + TEXT_GAP));

			if (MenuEntry.values()[selectedEntryIndex] == entry) {
				g.setColor(selectedColor);
			} else {
				g.setColor(standardColor);
			}

			g.drawString(entry.getText(), x, y);
		}
	}

	private int calculateAllEntriesHeight(Font font) {
		int height = 0;

		int entriesCount = MenuEntry.values().length;
		for (int i = 0; i < entriesCount; i++) {
			height += font.getHeight(MenuEntry.values()[i].getText());
			if (i != (entriesCount - 1)) {
				height += TEXT_GAP;
			}
		}

		return height;
	}

	private int getXForEntry(MenuEntry entry, Font font, GameContainer container) {
		return (container.getWidth() - font.getWidth(entry.getText())) / 2;
	}

	private int getStartY(int allEntriesHeight, GameContainer container) {
		return (container.getHeight() - allEntriesHeight) / 2;
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
		if (input.isKeyPressed(Input.KEY_UP)) {
			selectedEntryIndex = (selectedEntryIndex - 1 + MenuEntry.values().length)
					% MenuEntry.values().length;
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			selectedEntryIndex = (selectedEntryIndex + 1)
					% MenuEntry.values().length;
		} else if (input.isKeyPressed(Input.KEY_ENTER)) {
			MenuEntry selectedEntry = MenuEntry.values()[selectedEntryIndex];
			if (MenuEntry.START == selectedEntry) {
				input.clearControlPressedRecord();
				input.clearKeyPressedRecord();
				input.clearMousePressedRecord();
				game.enterState(Globals.GAME_STATE, new FadeOutTransition(),
						new FadeInTransition());
			} else if (MenuEntry.HIGHSCORES == selectedEntry) {

			} else if (MenuEntry.EXIT == selectedEntry) {
				System.exit(0);
			}
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
