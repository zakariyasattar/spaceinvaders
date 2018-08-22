/*
    Screen implementation that models a game
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class GameScreen extends Screen
{
	//variables that represent the different GameObjects in the game
	private ArrayList<Alien> aliens;
	private ArrayList<Laser> lasers;
	private Player player;
	private ArrayList<PowerUp> powerups;
	private int powerUpCountDown = (int) (Math.random() + 30) * 140;
	private int alienDamageCounter;
	private int playerDamageCounter;
	private int lives = 3;
	private int score;

	//this class inherits the following final variables (so you can't change them!)
	//
	//  GameState state;    //variable that lets you switch to another screen
	//  int width, height;  //the width and height of this screen

	public GameScreen(GameState s, int w, int h) {
		super(s, w, h);
		initGame();
	}

	//initialize our variables before the next game begins
	public void initGame() {
		int x = 20;
		int y = 100;
		aliens = new ArrayList<Alien>();
		lasers = new ArrayList<Laser>();
		powerups = new ArrayList<PowerUp>();

		player = new Player(width/2 - 23, height - 24, 45, 24);
		
		for(int outer = 0; outer < 7; outer++) {
			y += 25;
			for(int inner = 0; inner < 5; inner++) {
				aliens.add(new Alien(x, y, 37, 25));
				x+=45;
			}
			x = 20;
		}
		score = 0;
	}

	//render all the game objects in the game
	public void render(Graphics2D g) {
		for(Laser l : lasers) {
			l.render(g);
		}
		for(Alien a: aliens) {
			a.render(g);
		}
		player.render(g);
		
		g.setFont(new Font("Playbill", Font.BOLD, 20));
		g.setColor(Color.white);
		
		g.drawString("score: " + score, 660, 50);
		g.drawString("lives:  " + lives, 660, 75);
	}

	//update all the game objects in the game
	public void update() {
		for(int i = 0; i < aliens.size(); i++) {
			Alien a = aliens.get(i);
			a.update();
			Laser l = a.shoot();
			if(l!=null)
				lasers.add(l);
		}

		for(int j = 0; j < lasers.size(); j++) {
			Laser l = lasers.get(j);
			l.update();
		}
		
		for(int k = lasers.size() - 1; k > 0; k--) {
			Laser laser = lasers.get(k);
			for(int l = 0; l < aliens.size(); l++) {
				Alien alien = aliens.get(l);
				if(alien.intersects(laser) && laser.getDirection() == 1) { 
					System.out.println("alien");
					lasers.remove(k);
					alienDamageCounter++;
					score++;
					
					if(alienDamageCounter == 3) {
						aliens.remove(l);
						break;
					}
					
				}
				
				if(player.intersects(laser) && laser.getDirection() == -1) { 
					System.out.println("player");
					playerDamageCounter++;
					lasers.remove(laser);
					System.out.println(playerDamageCounter);
					if(playerDamageCounter == 2) {
						System.out.println(playerDamageCounter);
						lives--;
						playerDamageCounter = 0;
						System.out.println("lives:" + lives);
						if(lives <= 0) {
							System.out.println("Game Over");
							gameOver();
						}
					}
					break;
				}
			}
		}
		if(powerUpCountDown <= 0) {
			powerUpCountDown = (int) (Math.random() + 30) * 140;
			PowerUp powerup = new PowerUp(30,40, 4, 12 );
			powerups.add(powerup);
		}
		
		for(int i = powerups.size() - 1; i > 0; i--) {
			PowerUp powerup = powerups.get(i);
			powerup.update();
			if(player.intersects(powerup)) { 
				powerups.remove(i);
				lives++;
			}
		}
		player.update();
	}
	//handles key press events
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_Q)
			state.switchToWelcomeScreen();
		else if(code == KeyEvent.VK_LEFT)
			player.setMovingLeft(true);
		else if(code == KeyEvent.VK_RIGHT)
			player.setMovingRight(true);
		else if(code == KeyEvent.VK_SPACE) {
			Laser l  = player.shoot();
			if(l != null) {
				lasers.add(l);
			}
		}
	}

	//handles key released events
	public void keyReleased(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_LEFT)
			player.setMovingRight(false);
		if(code == KeyEvent.VK_RIGHT)
			player.setMovingLeft(false);
	}

	//should be called when the game is over
	public void gameOver() {
		//sets up the next game
		initGame();

		//switch to the game over screen
		state.switchToGameOverScreen();
	}

	//implement these methods if your player can use the mouse
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