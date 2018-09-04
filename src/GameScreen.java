import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class GameScreen extends Screen
{
	//variables that represent the different GameObjects in the game
	private Image img = ImageLoader.loadCompatibleImage("sprites/explosion.png");
	static ArrayList<Alien> aliens;
	private ArrayList<Laser> lasers;
	public static ArrayList<Barrier> barriers;
	private ArrayList<Integer> barrierDamage;
	private Player player;
	private ArrayList<PowerUp> powerups;
	private int powerUpCountDown = (int) (Math.random() * 1000) + 500;
	private int alienDamageCounter;
	private int playerDamageCounter;
	static int lives = 3;
	private int score;
	private int[] randomNums;
	private int timer = 0;
	int x = 20;
	int y = 80;
	private int jCount = 0;
	private boolean[] dead;
	private int pos = 0;
	int explodeTimer = 0;
	boolean canCount = false;
	boolean barrier = false;

	Sound killInvader = new Sound("invaderkilled.wav");
	Sound song = new Sound("spaceinvadersmusic.wav");
	Sound explosion = new Sound("explosion.wav");
	Sound shoot = new Sound("shoot.wav");

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
		int xPos = 60;
		song.play();
		
		barriers = new ArrayList<Barrier>();
		barrierDamage = new ArrayList<Integer>();
		aliens = new ArrayList<Alien>();
		lasers = new ArrayList<Laser>();
		powerups = new ArrayList<PowerUp>();
		player = new Player(width/2 - 23, height - 24, 45, 24);


		for(int outer = 0; outer < 5; outer++) {
			y += 55;
			for(int inner = 0; inner < 7; inner++) {
				aliens.add(new Alien(x, y, 37, 25, false, false));
				x+=55;
			}
			x = 20;
		}
		
		for(int barrier = 0; barrier < 4; barrier++) {
			barrierDamage.add(0);
			barriers.add(new Barrier(xPos, 475, 72, 54, 2));
			xPos+= 200;
		}

		aliens.add(new MasterAlien(185, 75, 37, 25, true));
		randomNums = new int[aliens.size()];
		dead = new boolean[aliens.size()];
		for(int j = 0; j < 3; j++) {
			int k = (int) (Math.random() * aliens.size()) + 0;
			if(!dead[k]) {
				randomNums[j] = k;
			}
		}
		score = 0;

		x = 20;
		y = 80;
		//barrierDamageCounter++;
	}

	//render all the game objects in the game
	public void render(Graphics2D g) {

		for(Laser l : lasers) {
			l.render(g);
		}
		for(Alien a: aliens) {
			a.render(g);
		}
		for(PowerUp p : powerups) {
			p.render(g);
		}
		for(Barrier b : barriers) {
			b.render(g);
		}

		player.render(g);

		g.setFont(new Font("Circular", Font.BOLD, 20));
		g.setColor(Color.white);

		g.drawString("score: " + score, 660, 50);
		g.drawString("lives:   " + lives, 660, 75);
	}


	//update all the game objects in the game
	public void update() {

		powerUpCountDown--;

		if(!song.isPlaying()) {
			song.play();
		}


		for(int i = 0; i < aliens.size(); i++) {
			timer++;
			if(timer == 2) {
				timer = 0;
			}
			Alien a = aliens.get(i);
			a.update();
			if(randomNums[i] < aliens.size()) {
				a = aliens.get(randomNums[i]);
				Laser l = a.shoot();
				if(l !=null)
					lasers.add(l);
			}


		}

		for(int j = 0; j < lasers.size(); j++) {
			Laser l = lasers.get(j);
			l.update();
		}

		for(int k = lasers.size() - 1; k >= 0; k--) {
			Laser laser = lasers.get(k);
			for(int l = 0; l < aliens.size(); l++) {
				Alien alien = aliens.get(l);
				jCount = l;

				if(score >= 35) {
					laser.setSpeed(8);
				}

				if(alien.intersects(laser) && laser.getDirection() == 1 && alien.isMaster() == true) { 
					lasers.remove(k);
					killInvader.play();
					if(score == 35) {
						alienDamageCounter++;
						if(alienDamageCounter >= 3 && score == 35) {
							state.switchToWinScreen();
							explosion.play();
						}
					}
					break;
				}

				else if(alien.intersects(laser) && laser.getDirection() == 1 && !dead[l]) { 
					canCount = true;
					score++;
					killInvader.play();
					alien.setExplode(true, l);
					dead[l] = true;
					lasers.remove(k);
					break;
				}
				else if(alien.intersects(laser) && laser.getDirection() == 1 && dead[l]){
					break;
				}
			}

			for(int b = barriers.size() - 1; b >= 0; b--) {
				Barrier barrier = barriers.get(b);
				if(barrier.intersects(laser)) {
					lasers.remove(k);
					barrierDamage.set(b, barrierDamage.get(b)+1);
					if(barrierDamage.get(b) == 1) {
						barrier.setImage(ImageLoader.loadCompatibleImage("sprites/firsthitbarrier.png"));
					}
					else if(barrierDamage.get(b) == 2) {
						barrier.setImage(ImageLoader.loadCompatibleImage("sprites/secondhitbarrier.png"));
					}
					else if(barrierDamage.get(b) == 3) {
						barriers.remove(b);
						barrierDamage.remove(b);
					}
				}
			}
			if(player.intersects(laser) && laser.getDirection() == -1 && laser.isMaster()) { 
				lives -= 2;
				lasers.remove(k);
				if(lives <= 0) {
					gameOver();
				}
				break;
			}

			else if(player.intersects(laser) && laser.getDirection() == -1) { 
				playerDamageCounter++;
				lasers.remove(laser);
				if(playerDamageCounter >= 1) {
					lives--; 
					playerDamageCounter = 0;
					if(lives <= 0) {
						gameOver();
					}
				}
				break;
			}

		}



		if(powerUpCountDown <= 0) {
			powerUpCountDown = (int) (Math.random() * 1000) + 500;
			int randX = (int) (Math.random() * 730) + 20;
			if(!barrier) {
				powerups.add(new PowerUp(randX,  20, 20, 20, "life"));
				barrier = true;
			}
			else {
				powerups.add(new PowerUp(randX,  20, 20, 20, "barrier"));
				barrier = false;
			}
			
		}
		

		for(int a = powerups.size() - 1; a >= 0; a--) {
			PowerUp powerup = powerups.get(a);
			powerup.update();
			if(player.intersects(powerup) && powerup.getType().equals("life")) { 
				powerups.remove(a);
				lives++;
			}
			else if(player.intersects(powerup) && powerup.getType().equals("barrier")) {
				barriers.clear();
				int xPos = 60;
				for(int barrier = 0; barrier < 4; barrier++) {
					barrierDamage.add(0);
					barriers.add(new Barrier(xPos, 475, 72, 54, 2));
					xPos+= 200;
				}
				
			}
		}


		pos++;
		if(dead[pos] == true) {
		}

		else {
			if(dead[pos] == false && aliens.get(pos).getBounds().y == player.getBounds().height) {

				gameOver();
			}

		}

		for(int b = 0; b < barriers.size(); b++) {
			barriers.get(b).update();
		}
		pos = 0;
		player.update();
	}

	//handles key press events
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_Q) {
			state.switchToWelcomeScreen();
		}
		else if(code == KeyEvent.VK_LEFT)
			player.setMovingLeft(true);
		else if(code == KeyEvent.VK_RIGHT)
			player.setMovingRight(true);
		else if(code == KeyEvent.VK_SPACE) {

			Laser l  = player.shoot();
			if(l != null) {
				lasers.add(l);
				shoot.play();
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