/*
    Screen implementation that models a game
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class InstructionScreen extends Screen
{
	private static Image keys = ImageLoader.loadCompatibleImage("sprites/keys.jpeg");
	private static Image spacebar = ImageLoader.loadCompatibleImage("sprites/spacebar.jpg");
	private static Image master = ImageLoader.loadCompatibleImage("sprites/mysteryShip.png");
	private static Image powerUp = ImageLoader.loadCompatibleImage("sprites/powerup.png");
	private static Image barrier = ImageLoader.loadCompatibleImage("sprites/barrier.png");
	
	private int counter = 0;
	
	public InstructionScreen(GameState s, int w, int h) {
		super(s, w, h);
	}

	public void render(Graphics2D g) {
		
		g.setFont(new Font("Circular", Font.BOLD, 60));
		g.setColor(Color.CYAN);

		g.drawString("Instructions", 135, 85);
		
		
		g.setFont(new Font("Circular", Font.BOLD, 20));
		g.setColor(Color.green);
		
		g.drawString("Move With:", 275, 145);
		g.drawImage(keys, 295, 170, null);
		
		g.drawString("Shoot With:", 275, 333);
		g.drawImage(spacebar, 295, 345, null);
		
		g.setFont(new Font("Circular", Font.BOLD, 25));
		g.setColor(Color.white);
		
		g.drawString("After all aliens are dead, hit          3 times to win", 170, 470);
		g.drawImage(master, 490, 450, null);
		
		g.drawString("Collect      To Gain A Life", 300, 500);
		g.drawImage(powerUp, 395, 480, 20, 20, null);
		
		g.drawString("Collect      To Replenish Barriers", 300, 525);
		g.drawImage(barrier, 395, 510, 20, 20, null);
		
		g.setFont(new Font("Circular", Font.BOLD, 35));
		g.setColor(Color.yellow);
		
		if(counter < 40)
			g.drawString("Press Enter To Play!!", 250, 575);
		counter++;
		if(counter > 70)
			counter = 0;
		
		
	}

	public void update() {
//		try {
//			TimeUnit.SECONDS.sleep(10);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		state.switchToGameScreen();
	}

	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_ENTER) {
			state.switchToGameScreen();
			
		}
	}

	public void keyReleased(KeyEvent e){
		
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