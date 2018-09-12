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
	private int initCounter = 0;
	int x = 20;
	int y = 80;
	private int count = 0;
	int masterPos = 0;
	public static boolean canTakeDamage = true;
	public static boolean canGetBarrier = true;

	private boolean[] dead;
	private int pos = 0;
	int explodeTimer = 0;
	boolean canCount = false;
	int barrier = (int) (Math.random() * 3) + 1;
	boolean godmode = false;

	Sound killInvader = new Sound("invaderkilled.wav");
	Sound song = new Sound("spaceinvadersmusic.wav");
	Sound explosion = new Sound("explosion.wav");
	Sound shoot = new Sound("shoot.wav");

	public GameScreen(GameState s, int w, int h) {
		super(s, w, h);
		initGame();
	}

	public static void canDraw(boolean b) {
		canGetBarrier = b;
	}
	
	public static void canTakeDamage(boolean b) {
		canTakeDamage = b;
	}

	//initialize our variables before the next game begins
	public void initGame() {
		godmode = false;
		int xPos = 60;
		barrier = (int) (Math.random() * 3) + 1;
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
				if(outer < 2) {
					aliens.add(new Alien(x, y, 37, 25, false, false));
				}
				else if(outer == 2) {
					aliens.add(new AlienC(x, y, 37, 25, false, false));
				}
				else if(outer > 2) {
					aliens.add(new OtherAlien(x, y, 37, 25, false, false));
				}

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
		initArray();
		score = 0;

		for(int h = 0; h < aliens.size(); h++) {
			if(aliens.get(h).isMaster()) {
				masterPos = h;
				break;
			}
		}

		x = 20;
		y = 80;
	}

	//render all the game objects in the game
	public void render(Graphics2D g) {

		for(Barrier b : barriers) {
			b.render(g);
		}
		for(Laser l : lasers) {
			l.render(g);
		}
		for(Alien a: aliens) {
			a.render(g);
		}
		for(PowerUp p : powerups) {
			p.render(g);
		}

		player.render(g);

		g.setFont(new Font("Circular", Font.BOLD, 20));
		g.setColor(Color.white);

		g.drawString("score: " + score, 660, 50);
		g.drawString("lives:   " + lives, 660, 75);
	}
	public void initArray() {
		for(int j = 0; j < count; j++) {
			int k = (int) (Math.random() * count) + 0;
			if(!dead[k]) {
				randomNums[j] = k;
			}
		}
	}


	//update all the game objects in the game
	public void update() {
		Alien aliend = aliens.get(masterPos);
		Laser laser1 = aliend.shoot();
		if(laser1 != null) {
			lasers.add(laser1);
		}
		powerUpCountDown--;
		initCounter++;

		for(boolean x : dead) {
			if(!x) {
				count++;

			}
		}

		if(!song.isPlaying()) {
			song.play();
		}

		if(initCounter == 100) {
			initArray();
			initCounter = 0;
		}



		for(int i = 0; i < aliens.size(); i++) {
			Alien a = aliens.get(i);
			a.update();
			if(randomNums[i] < 10) {
				a = aliens.get(randomNums[i]);

				if(!dead[randomNums[i]]) {
					Laser l = a.shoot();
					if(l != null) {
						lasers.add(l);
					}
				}

			}


		}


		for(int j = 0; j < lasers.size(); j++) {
			Laser l = lasers.get(j);
			l.update();
		}

		for(int k = lasers.size() - 1; k >= 0; k--) {
			if(lasers.size() != 0) {
				Laser laser = lasers.get(k);
				for(int l = 0; l < aliens.size(); l++) {
					Alien alien = aliens.get(l);

					if(score >= 35) {
						laser.setSpeed(8);
					}



					if(alien.intersects(laser) && laser.getDirection() == 1 && alien.isMaster() == true) { 
						lasers.remove(k);
						killInvader.play();
						if(score == 35) {
							alienDamageCounter++;
							if(alienDamageCounter >= 3 && score == 35) {
								winGame();
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

					if(player.intersects(alien) && laser.getDirection() == -1) { 
						gameOver();
					}
				}

				for(int b = barriers.size() - 1; b >= 0; b--) {
					Barrier barrier = barriers.get(b);
					if(barrier.intersects(laser) && canTakeDamage) {
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

		}

		if(powerUpCountDown <= 0) {
			powerUpCountDown = (int) (Math.random() * 1000) + 500;
			int randX = (int) (Math.random() * 650) + 200;
			if(barrier == 1) {
				powerups.add(new PowerUp(randX,  20, 20, 20, "life"));
				barrier = (int) (Math.random() * 3) + 1;

			}
			
			else if(barrier == 2 && canGetBarrier){
				powerups.add(new PowerUp(randX,  20, 20, 20, "barrier"));
				barrier = (int) (Math.random() * 3) + 1;

			}
			else if(barrier == 3) {
				powerups.add(new PowerUp(randX,  20, 20, 20, "star"));
				barrier = (int) (Math.random() * 3) + 1;
			}
			else {
				powerups.add(new PowerUp(randX,  20, 20, 20, "star"));
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
				barrierDamage.clear();
				powerups.remove(a);
				int xPos = 60;
				for(int barrier = 0; barrier < 4; barrier++) {
					barrierDamage.add(0);
					barriers.add(new Barrier(xPos, 600, 72, 54, 2));
					xPos += 200;
				}
				for(Barrier b : barriers) {
					b.update();
				}
			}
			else if(player.intersects(powerup) && powerup.getType().equals("star")) { 
				powerups.remove(a);
				godmode = true;

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
		count = 0;
	}

	public void winGame() {
		initGame();

		state.switchToWinScreen();

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
			if(!godmode) {
				Laser l  = player.shoot();
				if(l != null) {
					lasers.add(l);
					shoot.play();
				}
			}
			else {
				Laser l  = player.shoot();
				if(l != null) {
					lasers.add(l);
					shoot.play();
					lasers.add(l);
					shoot.play();
					lasers.add(l);
					shoot.play();
					lasers.add(l);
					shoot.play();
					lasers.add(l);
					shoot.play();
					lasers.add(l);
					shoot.play();
					lasers.add(l);
					shoot.play();
					lasers.add(l);
					shoot.play();
					lasers.add(l);
					shoot.play();

				}
				godmode = false;
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