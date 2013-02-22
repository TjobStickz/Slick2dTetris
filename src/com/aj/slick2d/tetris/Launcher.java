/**
 * 
 */
package com.aj.slick2d.tetris;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import com.aj.slick2d.tetris.core.Game;
import com.aj.slick2d.tetris.global.Globals;

/**
 * Game launcher.
 * 
 * @author BETON
 */
public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Game());

			app.setAlwaysRender(true);
			app.setDisplayMode(Globals.RESOLUTION_WIDTH,
					Globals.RESOLUTION_HEIGHT, false);
			app.setTargetFrameRate(100);

			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
