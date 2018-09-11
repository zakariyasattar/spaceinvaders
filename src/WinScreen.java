/*
    Screen implementation that models a game
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class WinScreen extends Screen
{
	public WinScreen(GameState s, int w, int h) {
		super(s, w, h);
	}

	public void render(Graphics2D g) {
		g.setFont(new Font("Circular", Font.BOLD, 60));
		g.setColor(Color.magenta);

		g.drawString("You Won !!!", 220, 175);
		
		g.setFont(new Font("Circular", Font.BOLD, 30));
		g.setColor(Color.white);
		
		g.drawString("Play Again (Press Enter)", 275, 400);
		g.drawString("Quit (Press Q)", 275, 450);
	}

	public void update() {

	}

	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_ENTER) {
			GameScreen.lives = 3;
			state.switchToGameScreen();
		}
		else if(code == KeyEvent.VK_Q) {
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void mousePressed(Point2D p)
	{
	}
	public void mouseReleased(Point2D p)
	{
	}
	public void mouseMoved(Point2D p)
	{
	}
	public void mouseDragged(Point2D p)
	{
	}
}