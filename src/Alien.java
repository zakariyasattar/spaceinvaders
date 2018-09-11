/*
    This class represents a basic alien in the game
 */

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class Alien extends GameObject {

	private Image image = ImageLoader.loadCompatibleImage("sprites/alienA1.png");
	private int updateCounter = 0;
	private int shootCountDown = (int) ((Math.random() * 600) + 30);
	boolean edge = false;
	private boolean master = false;
	boolean explosion = false;
	int alienPos = 0;
	private int timer = 0;
	int explodeTimer = 0;
	int explodeCounter = 0;
	public static boolean shouldRemove = false;

	public Alien(int x, int y, int w, int h, boolean master, boolean explosion) {
		super(x, y, w, h);
		master = false;
		this.explosion = explosion;
	}
	public boolean isMaster() {
		return master;
	}

	

	public void update() {

		//keep track of how many times this Alien has been updated
		updateCounter++;
		explodeCounter++;
		
		if(timer == 20)
			image = ImageLoader.loadCompatibleImage("sprites/alienA2.png");
		timer++;
		if(timer > 40) {
			timer = 0;
			image = ImageLoader.loadCompatibleImage("sprites/alienA1.png");
		}

		if(getBounds().x < 755 && edge == false) {
			getBounds().x += 0.5;
			if(getBounds().x == 755) {
				edge = true;
				getBounds().y += getBounds().height*1.125;
			}
		}
		else if(getBounds().x > 0 && edge == true) {
			getBounds().x -= 0.5;
			if(getBounds().x == 0) {
				edge = false;
				getBounds().y += getBounds().height*1.125;
			}
		}

		if(shootCountDown > 0) {
			shootCountDown--;
		}
		

	}
	public void setExplode (boolean explode, int pos) {
		explosion = explode;
		alienPos = pos;
	}
	public Laser shoot() {
		if(shootCountDown <= 0) {
			shootCountDown = (int) ((Math.random() * 600) + 30);
			return new Laser((int)getBounds().x, (int)(getBounds().height+getBounds().y), 4, 12, -1, ImageLoader.loadCompatibleImage("sprites/alienWiggleLaser1.png"), false);
		}
		else
			return null;
	}
	
	public void setImage(Image img) {
		image = img;
	}


	//draw the image represented by the alien
	public void render(Graphics2D g) {
		if(!explosion) {
				g.drawImage(image,
						(int)getBounds().x,
						(int)getBounds().y,
						(int)getBounds().width,
						(int)getBounds().height,
						null);
		}
		else if(explosion){
			if(explodeTimer != 20) {
				explodeTimer++;
				g.drawImage(ImageLoader.loadCompatibleImage("sprites/explosion.png"),
						(int)getBounds().x,
						(int)getBounds().y,
						(int)getBounds().width,
						(int)getBounds().height,
						null);
				
			}
			else if(explodeTimer == 20) {
				shouldRemove = true;
			}
		}
		

	}

}