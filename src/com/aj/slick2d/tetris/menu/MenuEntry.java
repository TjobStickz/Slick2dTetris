/**
 * 
 */
package com.aj.slick2d.tetris.menu;

/**
 * Menu entry enum.
 * 
 * @author BETON
 */
public enum MenuEntry {
	START("START"), OPTIONS("OPTIONS"), HIGHSCORES("HIGHSCORES"), EXIT("EXIT");

	private String text;

	MenuEntry(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
